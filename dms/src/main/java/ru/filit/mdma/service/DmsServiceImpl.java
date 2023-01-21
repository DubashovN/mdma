package ru.filit.mdma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ru.filit.mdma.model.audit.kafka.AuditMessage;
import ru.filit.mdma.model.audit.kafka.MessageProducer;
import ru.filit.mdma.model.cache.TokenService;
import ru.filit.mdma.web.dto.AccessDto;
import ru.filit.mdma.web.dto.AccessRequestDto;
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


@Service
@AllArgsConstructor
@Slf4j
public class DmsServiceImpl implements ClientService, AccountService, AccessService {

  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private final TokenService tokenService;
  private final MessageProducer messageProducer;

  @Override
  public List<AccessDto> getAccess(AccessRequestDto accessRequestDto) {
    if (accessRequestDto.getRole() == null) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN, "Role is not determined");
    }
    RequestBuilder<List<AccessDto>, AccessRequestDto> requestBuilder = new RequestBuilder<>();
    Mono<List<AccessDto>> entity = requestBuilder
        .sendRequest("/access", accessRequestDto);
    List<AccessDto> accessDtos =  objectMapper.convertValue(entity.block(), new TypeReference<>() {
    });
    if (accessDtos == null) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Role is not determined");
    }
    return accessDtos;
  }

  @Override
  public List<AccountDto> getAccount(ClientIdDto clientIdDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit) {
    tokenService.tokenizeObject(clientIdDto, accessAudit, "client");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account",
          objectMapper.writeValueAsString(clientIdDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(clientIdDto);
    RequestBuilder<List<AccountDto>, ClientIdDto> requestBuilder = new RequestBuilder<>();
    Mono<List<AccountDto>> entity = requestBuilder
        .sendRequest("/client/account", clientIdDto);
    List<AccountDto> accountDtos = objectMapper.convertValue(entity.block(), new TypeReference<>() {
    });
    if (accountDtos == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null account");
    }

    tokenService.tokenizeObject(accountDtos, accessAudit, "account");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account",
          objectMapper.writeValueAsString(accountDtos), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }
    tokenService.tokenizeObject(accountDtos, access, "account");
    return accountDtos;
  }

  @Override
  public CurrentBalanceDto getAccountBalance(AccountNumberDto accountNumberDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit) {
    tokenService.tokenizeObject(accountNumberDto, accessAudit, "accountNumber");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account/operation",
          objectMapper.writeValueAsString(accountNumberDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(accountNumberDto);
    RequestBuilder<CurrentBalanceDto, AccountNumberDto> requestBuilder = new RequestBuilder<>();
    Mono<CurrentBalanceDto> entity = requestBuilder
        .sendRequest("/client/account/balance", accountNumberDto);
    CurrentBalanceDto currentBalance = objectMapper.convertValue(entity.block(),
        new TypeReference<>() {
        });
    if (currentBalance == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null current balance");
    }
    tokenService.tokenizeObject(currentBalance, accessAudit, "currentBalance");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account/balance",
          objectMapper.writeValueAsString(currentBalance), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }
    tokenService.tokenizeObject(currentBalance, access, "currentBalance");
    return currentBalance;
  }

  @Override
  public List<OperationDto> getAccountOperations(OperationSearchDto operationSearchDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit) {
    tokenService.tokenizeObject(operationSearchDto, accessAudit, "operationSearch");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account/operation",
          objectMapper.writeValueAsString(operationSearchDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(operationSearchDto);
    RequestBuilder<List<OperationDto>, OperationSearchDto> requestBuilder = new RequestBuilder<>();
    Mono<List<OperationDto>> entity = requestBuilder
        .sendRequest("/client/account/operation", operationSearchDto);
    List<OperationDto> operations = objectMapper.convertValue(entity.block(),
        new TypeReference<>() {
        });
    if (operations == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null operation");
    }

    tokenService.tokenizeObject(operations, accessAudit, "operation");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account/operation",
          objectMapper.writeValueAsString(operations), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }
    tokenService.tokenizeObject(operations, access, "operation");
    return operations;
  }

  @Override
  public List<ClientDto> findClient(ClientSearchDto clientSearchDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit) {
    tokenService.tokenizeObject(clientSearchDto, accessAudit, "client");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client",
          objectMapper.writeValueAsString(clientSearchDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(clientSearchDto);
    RequestBuilder<List<ClientDto>, ClientSearchDto> requestBuilder = new RequestBuilder<>();
    Mono<List<ClientDto>> entity = requestBuilder
        .sendRequest("/client", clientSearchDto);
    List<ClientDto> clientDtos = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (clientDtos == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null client");
    }

    tokenService.tokenizeObject(clientDtos, accessAudit, "client");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client",
          objectMapper.writeValueAsString(clientDtos), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.tokenizeObject(clientDtos, access, "client");
    return clientDtos;
  }

  @Override
  public ClientLevelDto getClientLevel(ClientIdDto clientIdDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit) {
    tokenService.tokenizeObject(clientIdDto, accessAudit, "client");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/level",
          objectMapper.writeValueAsString(clientIdDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(clientIdDto);
    RequestBuilder<ClientLevelDto, ClientIdDto> requestBuilder = new RequestBuilder<>();
    Mono<ClientLevelDto> entity = requestBuilder
        .sendRequest("/client/level", clientIdDto);
    ClientLevelDto clientLevelDto = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (clientLevelDto == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null client level");
    }

    tokenService.tokenizeObject(clientLevelDto, accessAudit, "clientLevel");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/level",
          objectMapper.writeValueAsString(clientLevelDto), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.tokenizeObject(clientLevelDto, access, "clientLevel");
    return clientLevelDto;
  }

  @Override
  public List<ContactDto> getContact(ClientIdDto clientIdDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit) {
    tokenService.tokenizeObject(clientIdDto, accessAudit, "client");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/contact",
          objectMapper.writeValueAsString(clientIdDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(clientIdDto);
    RequestBuilder<List<ContactDto>, ClientIdDto> requestBuilder = new RequestBuilder<>();
    Mono<List<ContactDto>> entity = requestBuilder
        .sendRequest("/client/contact", clientIdDto);
    List<ContactDto> contacts = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (contacts == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null contact");
    }

    tokenService.tokenizeObject(contacts, accessAudit, "contact");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/contact",
          objectMapper.writeValueAsString(contacts), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }
    tokenService.tokenizeObject(contacts, access, "contact");
    return contacts;
  }

  @Override
  public ContactDto saveContact(ContactDto contactDto, List<AccessDto> access,
      String crMUserName, List<AccessDto> accessAudit) {
    tokenService.tokenizeObject(contactDto, accessAudit, "contact");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/contact/save",
          objectMapper.writeValueAsString(contactDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(contactDto);
    RequestBuilder<ContactDto, ContactDto> requestBuilder = new RequestBuilder<>();
    Mono<ContactDto> entity = requestBuilder
        .sendRequest("/client/contact/save", contactDto);
    ContactDto contact = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (contact == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null contact");
    }

    tokenService.tokenizeObject(contact, accessAudit, "contact");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/contact/save",
          objectMapper.writeValueAsString(contact), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }
    tokenService.tokenizeObject(contact, access, "contact");
    return contact;
  }

  @Override
  public LoanPaymentDto getLoanPayment(AccountNumberDto accountNumberDto,
      List<AccessDto> access, String crMUserName, List<AccessDto> accessAudit) {

    tokenService.tokenizeObject(accountNumberDto, accessAudit, "accountNumber");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account/loan-payment",
          objectMapper.writeValueAsString(accountNumberDto), true));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }

    tokenService.detokenizeObject(accountNumberDto);
    RequestBuilder<LoanPaymentDto, AccountNumberDto> requestBuilder = new RequestBuilder<>();
    Mono<LoanPaymentDto> entity = requestBuilder
        .sendRequest("/client/account/loan-payment", accountNumberDto);
    LoanPaymentDto loanPayment = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (loanPayment == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Null loan payment");
    }

    tokenService.tokenizeObject(loanPayment, accessAudit, "loanPayment");
    try {
      messageProducer.sendMessage(getAuditMessage(crMUserName, "/client/account/loan-payment",
          objectMapper.writeValueAsString(loanPayment), false));
    } catch (JsonProcessingException e) {
      log.info("Failed to send message to audit");
    }
    tokenService.tokenizeObject(loanPayment, access, "loanPayment");
    return loanPayment;
  }

  private String getAuditMessage(String userName, String methodName, String body,
      boolean isRequest) {
    AuditMessage message = new AuditMessage(userName, methodName, body, isRequest);
    try {
      return objectMapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      log.warn("Exception while writing value as string to audit");
    }
    return null;
  }

  private class RequestBuilder<T, B> {

    private Mono<T> sendRequest(String uri, B body) {
      log.info("The request sent to {} with body: {}", uri, body.toString());
      return webClient
          .post()
          .uri(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(body)
          .exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
              return response.bodyToMono(new ParameterizedTypeReference<>() {
              });
            } else {
              return response.createException().flatMap(error -> {
                log.info("Text {}, status {}, ex {}",
                    error.getResponseBodyAsString(), error.getStatusCode(),
                    error.getLocalizedMessage());
                log.info("Text {}", error.toString());
                return Mono.error(
                    new ResponseStatusException(error.getStatusCode(), error.getResponseBodyAsString()));
              });
            }
          });
    }
  }
}
