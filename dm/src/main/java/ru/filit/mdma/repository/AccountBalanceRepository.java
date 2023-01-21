package ru.filit.mdma.repository;

import java.util.List;
import ru.filit.mdma.model.entity.AccountBalance;

public interface AccountBalanceRepository {

  List<AccountBalance> getAccountBalance(String accountNumber);

  List<AccountBalance> getAccountBalanceForPeriod(String accountNumber, Long from, Long to);
}
