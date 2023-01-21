package ru.filit.mdma.repository;

import java.util.List;
import ru.filit.mdma.model.entity.Account;


public interface AccountRepository {

  List<Account> findAccounts(String clientId);

  Account getAccount(String accountNumber);

}
