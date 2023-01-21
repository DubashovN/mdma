package ru.filit.mdma.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.model.entity.Account;
import ru.filit.mdma.repository.AccountRepository;
import ru.filit.mdma.service.EntityRepo;
import ru.filit.mdma.web.exception.AccountNotFoundException;


@Slf4j
@Repository
public class AccountRepositoryImpl extends AbstractYmlRepository
    implements AccountRepository {

  @Value(value = "accounts.yml")
  private String fileName;

  @Value(value = "${dm.repo.location}")
  private String filePath;

  private final EntityRepo entityRepo;

  private final List<Account> accounts = new CopyOnWriteArrayList<>();

  public AccountRepositoryImpl(EntityRepo entityRepo) {
    this.entityRepo = entityRepo;
  }

  @PostConstruct
  public void init() {
    final List<Account> accountList =
        entityRepo.readList(getFile(filePath, fileName), new TypeReference<>() {
        });
    accounts.addAll(accountList);
  }

  @Override
  public List<Account> findAccounts(String clientId) {
    return accounts.stream()
        .filter(account -> account.getClientId().equals(clientId))
        .collect(Collectors.toList());
  }

  @Override
  public Account getAccount(String accountNumber) {
    final Optional<Account> optionalAccount = accounts.stream()
        .filter(account -> account.getNumber().equals(accountNumber))
        .findFirst();
    return optionalAccount.orElseThrow(() -> new AccountNotFoundException("Account not found"));
  }
}
