package ru.filit.mdma.web.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.service.ClientService;
import ru.filit.mdma.web.dto.AccountNumberDto;
import ru.filit.mdma.web.dto.ClientDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ClientLevelDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.dto.ContactDto;
import ru.filit.mdma.web.dto.LoanPaymentDto;
import ru.filit.mdma.web.dto.OperationDto;


@RestController
@RequestMapping
@AllArgsConstructor
public class ClientApiImpl implements ClientApi {

  private final ClientService clientService;

  @Override
  public ResponseEntity<List<ClientDto>> findClient(ClientSearchDto clientSearchDto) {
    List<ClientDto> client = clientService.findClient(clientSearchDto);
    return ResponseEntity.ok(client);
  }

  @Override
  public ResponseEntity<ClientDto> getClient(ClientIdDto clientIdDto) {
    ClientDto client = clientService.getClient(clientIdDto);
    return ResponseEntity.ok(client);
  }

  @Override
  public ResponseEntity<ClientLevelDto> getClientLevel(ClientIdDto clientIdDto) {
    ClientLevelDto clientLevel = clientService.getClientLevel(clientIdDto);
    return ResponseEntity.ok(clientLevel);
  }

  @Override
  public ResponseEntity<List<OperationDto>> getLastOperations(AccountNumberDto accountNumberDto) {
    List<OperationDto> operations = clientService
        .getAccountOperations(accountNumberDto);
    return ResponseEntity.ok(operations);

  }

  @Override
  public ResponseEntity<LoanPaymentDto> getLoanPayment(AccountNumberDto accountNumberDto) {
    LoanPaymentDto loanPayment = clientService.getLoanPayment(accountNumberDto);
    return ResponseEntity.ok(loanPayment);
  }

  @Override
  public ResponseEntity<ContactDto> saveContact(ContactDto contactDto) {
    ContactDto savedContact = clientService.saveContact(contactDto);
    return ResponseEntity.ok(savedContact);
  }
}
