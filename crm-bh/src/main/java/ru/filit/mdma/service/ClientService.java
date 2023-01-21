package ru.filit.mdma.service;

import java.util.List;
import ru.filit.mdma.web.dto.AccountDto;
import ru.filit.mdma.web.dto.AccountNumberDto;
import ru.filit.mdma.web.dto.ClientDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ClientLevelDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.dto.ContactDto;
import ru.filit.mdma.web.dto.LoanPaymentDto;
import ru.filit.mdma.web.dto.OperationDto;

public interface ClientService {

  ClientDto getClient(ClientIdDto clientIdDto);

  List<ClientDto> findClient(ClientSearchDto clientSearchDto);

  List<ContactDto> getContact(ClientIdDto clientIdDto);

  ClientLevelDto getClientLevel(ClientIdDto clientIdDto);

  List<AccountDto> getAccount(ClientIdDto clientIdDto);

  List<OperationDto> getAccountOperations(AccountNumberDto accountNumberDto);

  ContactDto saveContact(ContactDto contactDto);

  LoanPaymentDto getLoanPayment(AccountNumberDto accountNumberDto);
}
