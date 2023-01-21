package ru.filit.mdma.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.filit.mdma.model.entity.Account;
import ru.filit.mdma.repository.AccountRepository;
import ru.filit.mdma.web.dto.AccountDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.exception.InvalidDataException;
import ru.filit.mdma.web.mapping.DtoMapper;


@Service
public class AccountService {

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public List<AccountDto> getAccount(ClientIdDto clientIdDto) {
    String id = clientIdDto.getId();
    if (id == null || id.isBlank()) {
      throw new InvalidDataException("Отсутствует идентификатор клиента");
    }
    List<Account> accounts = accountRepository.findAccounts(id);
    return accounts.stream()
        .map(DtoMapper.INSTANCE::accountToAccountDto)
        .collect(Collectors.toList());
  }

}
