package ru.filit.mdma.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.service.AccountService;
import ru.filit.mdma.service.ClientService;
import ru.filit.mdma.service.ContactService;
import ru.filit.mdma.service.OperationService;
import ru.filit.mdma.web.dto.AccountDto;
import ru.filit.mdma.web.dto.AccountNumberDto;
import ru.filit.mdma.web.dto.ClientDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ClientLevelDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.dto.ContactDto;
import ru.filit.mdma.web.dto.CurrentBalanceDto;
import ru.filit.mdma.web.dto.LoanPaymentDto;
import ru.filit.mdma.web.dto.OperationDto;
import ru.filit.mdma.web.dto.OperationSearchDto;

/**
 * Контроллер действий с клиентами.
 */
@RestController
@RequestMapping
public class ClientApiImpl implements ClientApi {

  private final ClientService clientService;
  private final ContactService contactService;
  private final AccountService accountService;
  private final OperationService operationService;

  public ClientApiImpl(ClientService clientService,
      ContactService contactService, AccountService accountService,
      OperationService operationService) {
    this.clientService = clientService;
    this.contactService = contactService;
    this.accountService = accountService;
    this.operationService = operationService;
  }

  @Override
  public ResponseEntity<List<AccountDto>> getAccount(ClientIdDto clientIdDto) {
    List<AccountDto> account = accountService.getAccount(clientIdDto);
    return ResponseEntity.status(HttpStatus.OK).body(account);
  }

  @Override
  public ResponseEntity<CurrentBalanceDto> getAccountBalance(AccountNumberDto accountNumberDto) {
    CurrentBalanceDto balance = operationService.getAccountBalance(accountNumberDto);
    return ResponseEntity.status(HttpStatus.OK).body(balance);
  }

  @Override
  public ResponseEntity<List<OperationDto>> getAccountOperations(
      OperationSearchDto operationSearchDto) {
    List<OperationDto> operations = operationService.getAccountOperations(operationSearchDto);
    return ResponseEntity.status(HttpStatus.OK).body(operations);
  }

  @Override
  public ResponseEntity<List<ClientDto>> getClient(ClientSearchDto clientSearchDto) {
    final List<ClientDto> clients = clientService.findClients(clientSearchDto);
    return ResponseEntity.status(HttpStatus.OK).body(clients);
  }

  @Override
  public ResponseEntity<ClientLevelDto> getClientLevel(ClientIdDto clientIdDto) {
    ClientLevelDto clientLevelDto = operationService.getClientLevel(clientIdDto);
    return ResponseEntity.status(HttpStatus.OK).body(clientLevelDto);
  }

  @Override
  public ResponseEntity<List<ContactDto>> getContact(ClientIdDto clientIdDto) {
    List<ContactDto> contact = contactService.getContact(clientIdDto);
    return ResponseEntity.status(HttpStatus.OK).body(contact);
  }

  @Override
  public ResponseEntity<LoanPaymentDto> getLoanPayment(AccountNumberDto accountNumberDto) {
    LoanPaymentDto loanPaymentDto = operationService.getLoanPayment(accountNumberDto);
    return ResponseEntity.status(HttpStatus.OK).body(loanPaymentDto);
  }

  @Override
  public ResponseEntity<ContactDto> saveContact(ContactDto contactDto) {
    ContactDto contact = contactService.saveContact(contactDto);
    return ResponseEntity.status(HttpStatus.OK).body(contact);
  }
}
