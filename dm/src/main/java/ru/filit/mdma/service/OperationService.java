package ru.filit.mdma.service;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.filit.mdma.model.ClientLevel;
import ru.filit.mdma.model.entity.Account;
import ru.filit.mdma.model.entity.Account.StatusEnum;
import ru.filit.mdma.model.entity.Account.TypeEnum;
import ru.filit.mdma.model.entity.AccountBalance;
import ru.filit.mdma.model.entity.Operation;
import ru.filit.mdma.repository.AccountBalanceRepository;
import ru.filit.mdma.repository.AccountRepository;
import ru.filit.mdma.repository.ClientRepository;
import ru.filit.mdma.repository.OperationRepository;
import ru.filit.mdma.web.DateTimeUtil;
import ru.filit.mdma.web.dto.AccountNumberDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ClientLevelDto;
import ru.filit.mdma.web.dto.CurrentBalanceDto;
import ru.filit.mdma.web.dto.LoanPaymentDto;
import ru.filit.mdma.web.dto.OperationDto;
import ru.filit.mdma.web.dto.OperationSearchDto;
import ru.filit.mdma.web.exception.InvalidDataException;
import ru.filit.mdma.web.mapping.CommonMapperImpl;
import ru.filit.mdma.web.mapping.DtoMapper;


@Service
@AllArgsConstructor
public class OperationService {

  private final ClientRepository clientRepository;
  private final OperationRepository operationRepository;
  private final AccountRepository accountRepository;
  private final AccountBalanceRepository accountBalanceRepository;
  private final CommonMapperImpl commonMapper;

  public List<OperationDto> getAccountOperations(OperationSearchDto operationSearchDto) {
    String accountNumber = checkAccountNumber(operationSearchDto);
    final List<Operation> operations = operationRepository.getOperations(accountNumber,
        commonMapper.asInt(operationSearchDto.getQuantity()));
    return operations.stream()
        .map(DtoMapper.INSTANCE::operationToOperationDto)
        .collect(Collectors.toList());
  }

  public CurrentBalanceDto getAccountBalance(AccountNumberDto accountNumberDto) {
    String accountNumber = checkAccountNumber(accountNumberDto);
    BigDecimal balancePerDay = getBalancePerDay(LocalDate.now(), accountNumber);
    CurrentBalanceDto currentBalanceDto = new CurrentBalanceDto();
    currentBalanceDto.setBalanceAmount(commonMapper.asDto(balancePerDay));
    return currentBalanceDto;
  }

  public LoanPaymentDto getLoanPayment(AccountNumberDto accountNumberDto) {
    String accountNumber = checkAccountNumber(accountNumberDto);
    Account account = accountRepository.getAccount(accountNumber);
    if (!account.getType().equals(TypeEnum.OVERDRAFT)) {
      throw new InvalidDataException("Provided account number is not overdraft");
    }
    LocalDate start = commonMapper.asDateTime(account.getOpenDate()).toLocalDate();
    LocalDate end = LocalDate.now();
    Map<LocalDate, BigDecimal> negativeBalances = new HashMap<>();
    for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
      BigDecimal perDay = getBalancePerDay(date, accountNumber);
      if (perDay.signum() < 0) {
        negativeBalances.put(date, perDay);
      }
    }
    BigDecimal result = BigDecimal.valueOf(0);
    for (Map.Entry<LocalDate, BigDecimal> date : negativeBalances.entrySet()) {
      LocalDate loanDate = DateTimeUtil.getLoanPaymentDaySkipWeekends(date.getKey(),
          account.getDeferment());
      if (negativeBalances.containsKey(loanDate)) {
        BigDecimal loanDatePayment = negativeBalances.get(loanDate);
        BigDecimal datePayment = date.getValue();
        BigDecimal payment;
        if (loanDatePayment.compareTo(datePayment) <= 0) {
          payment = datePayment;
        } else {
          payment = loanDatePayment;
        }
        result = result.add(getLoanAmount(payment));
      }
    }
    LoanPaymentDto loanPaymentDto = new LoanPaymentDto();
    loanPaymentDto.setAmount(commonMapper.asDto(result));
    return loanPaymentDto;
  }

  public ClientLevelDto getClientLevel(ClientIdDto clientIdDto) {
    String clientId = clientIdDto.getId();
    if (clientId == null || clientId.isBlank()) {
      throw new InvalidDataException("Отсутствует id клиента");
    }
    clientRepository.getClientById(clientId);
    List<Account> accounts = accountRepository.findAccounts(clientId)
        .stream()
        .filter(account -> account.getStatus().equals(StatusEnum.ACTIVE))
        .collect(Collectors.toList());
    Map<String, BigDecimal> avgPerAccount = new HashMap<>();
    accounts.forEach(account -> {
      String accountNumber = account.getNumber();
      BigDecimal avg = getAvgDailyBalance(account);
      avgPerAccount.put(accountNumber, avg);
    });
    Optional<Entry<String, BigDecimal>> max = avgPerAccount.entrySet()
        .stream()
        .max(Entry.comparingByValue());

    if (max.isEmpty()) {
      throw new InvalidDataException("У клиента нет активных счетов");
    }
    Entry<String, BigDecimal> entry = max.get();
    ClientLevel clientLevel = ClientLevel.fromAvgDailyBalance(entry.getValue());
    ClientLevelDto clientLevelDto = new ClientLevelDto();
    clientLevelDto.setAccountNumber(entry.getKey());
    clientLevelDto.setLevel(clientLevel.getValue());
    clientLevelDto.setAvgBalance(commonMapper.asDto(entry.getValue()));
    return clientLevelDto;
  }

  private BigDecimal getLoanAmount(BigDecimal input) {
    BigDecimal deferment = BigDecimal.valueOf(0.0007);
    return input.abs().multiply(deferment);
  }

  private BigDecimal getBalancePerDay(LocalDate date, String accountNumber) {
    LocalDateTime from = LocalDateTime.of(date.withDayOfMonth(1), LocalTime.MIN);
    LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
    List<Operation> operations = operationRepository.getOperationsByPeriod(accountNumber,
        commonMapper.asEpochSeconds(from), commonMapper.asEpochSeconds(to));
    BigDecimal amount = getAccountBalanceBeginOfMonth(date, accountNumber);
    for (Operation operation : operations) {
      switch (operation.getType()) {
        case EXPENSE:
          amount = amount.subtract(operation.getAmount());
          break;
        case RECEIPT:
          amount = amount.add(operation.getAmount());
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + operation.getType());
      }
    }
    return amount;
  }

  private BigDecimal getAccountBalanceBeginOfMonth(LocalDate date, String accountNumber) {
    LocalDateTime fromBeginOfDay = LocalDateTime.of(date.withDayOfMonth(1), LocalTime.MIN);
    LocalDateTime fromEndOfDay = LocalDateTime.of(date.withDayOfMonth(1), LocalTime.MAX);
    List<AccountBalance> balances = accountBalanceRepository
        .getAccountBalanceForPeriod(accountNumber, commonMapper.asEpochSeconds(fromBeginOfDay),
            commonMapper.asEpochSeconds(fromEndOfDay));
    if (balances.size() > 1) {
      throw new InvalidDataException("Несколько записей баланса на начало месяца");
    }
    BigDecimal balance = BigDecimal.valueOf(0);
    return balances.isEmpty() ? balance : balances.get(0).getAmount();
  }

  private BigDecimal getAvgDailyBalance(Account account) {
    String accountNumber = account.getNumber();
    LocalDate openDate = commonMapper.asDateTime(account.getOpenDate()).toLocalDate();
    LocalDate start = DateTimeUtil.getStartPeriod(openDate);
    LocalDate end = LocalDate.now();
    BigDecimal adb = BigDecimal.valueOf(0);
    for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
      BigDecimal perDay = getBalancePerDay(date, accountNumber);
      adb = adb.add(perDay);
    }
    long daysBetween = ChronoUnit.DAYS.between(start, end);
    return adb.divide(BigDecimal.valueOf(daysBetween), HALF_UP);
  }

  private String checkAccountNumber(Object account) {
    String accountNumber = null;
    if (account instanceof AccountNumberDto) {
      accountNumber = ((AccountNumberDto) account).getAccountNumber();
    }
    if (account instanceof OperationSearchDto) {
      accountNumber = ((OperationSearchDto) account).getAccountNumber();
    }
    if (accountNumber == null || accountNumber.isBlank()) {
      throw new InvalidDataException("Отсутствует номер счета");
    }
    return accountNumber;
  }
}
