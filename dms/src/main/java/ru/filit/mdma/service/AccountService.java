package ru.filit.mdma.service;

import java.util.List;
import ru.filit.mdma.web.dto.AccessDto;
import ru.filit.mdma.web.dto.AccountDto;
import ru.filit.mdma.web.dto.AccountNumberDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.CurrentBalanceDto;
import ru.filit.mdma.web.dto.LoanPaymentDto;
import ru.filit.mdma.web.dto.OperationDto;
import ru.filit.mdma.web.dto.OperationSearchDto;

public interface AccountService {

  List<AccountDto> getAccount(ClientIdDto clientIdDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit);

  CurrentBalanceDto getAccountBalance(AccountNumberDto accountNumberDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit);

  List<OperationDto> getAccountOperations(OperationSearchDto operationSearchDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit);

  LoanPaymentDto getLoanPayment(AccountNumberDto accountNumberDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit);
}
