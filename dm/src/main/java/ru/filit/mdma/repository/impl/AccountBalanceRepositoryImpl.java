package ru.filit.mdma.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.model.entity.AccountBalance;
import ru.filit.mdma.repository.AccountBalanceRepository;
import ru.filit.mdma.service.EntityRepo;

@Repository
@Slf4j
public class AccountBalanceRepositoryImpl extends AbstractYmlRepository
    implements AccountBalanceRepository {

  @Value(value = "balances.yml")
  private String fileName;

  @Value(value = "${dm.repo.location}")
  private String filePath;

  private final EntityRepo entityRepo;

  private final List<AccountBalance> accountBalances = new CopyOnWriteArrayList<>();

  public AccountBalanceRepositoryImpl(EntityRepo entityRepo) {
    this.entityRepo = entityRepo;
  }

  @PostConstruct
  public void init() {
    final List<AccountBalance> accountBalancesList =
        entityRepo.readList(getFile(filePath, fileName), new TypeReference<>() {
        });
    accountBalances.addAll(accountBalancesList);
  }

  @Override
  public List<AccountBalance> getAccountBalance(String accountNumber) {
    return accountBalances.stream()
        .filter(accountBalance -> accountBalance.getAccountNumber().equals(accountNumber))
        .collect(Collectors.toList());
  }

  @Override
  public List<AccountBalance> getAccountBalanceForPeriod(String accountNumber, Long from, Long to) {
    return accountBalances.stream()
        .filter(accountBalance -> accountBalance.getAccountNumber().equals(accountNumber)
            && accountBalance.getBalanceDate().compareTo(from) >= 0
            && accountBalance.getBalanceDate().compareTo(to) <= 0)
        .collect(Collectors.toList());
  }
}
