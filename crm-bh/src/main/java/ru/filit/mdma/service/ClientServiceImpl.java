package ru.filit.mdma.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ru.filit.mdma.web.dto.AccountDto;
import ru.filit.mdma.web.dto.AccountNumberDto;
import ru.filit.mdma.web.dto.ClientDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ClientLevelDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.dto.ContactDto;
import ru.filit.mdma.web.dto.LoanPaymentDto;
import ru.filit.mdma.web.dto.OperationDto;
import ru.filit.mdma.web.dto.OperationSearchDto;
import ru.filit.mdma.web.mapping.DtoMapper;

@AllArgsConstructor
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

  private final WebClient webClient;

  private final ObjectMapper objectMapper;

  private final AuthenticationService authentication;

  @Override
  public ClientDto getClient(ClientIdDto clientIdDto) {
    ClientSearchDto clientSearchDto = DtoMapper.INSTANCE.idDtoToSearchDto(clientIdDto);
    List<ClientDto> client = findClient(clientSearchDto);
    if (client.size() != 1) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
    }
    ClientDto clientDto = client.get(0);

    List<ContactDto> contacts = getContact(clientIdDto);
    if (contacts != null && !contacts.isEmpty()) {
      clientDto.setContacts(contacts);
    }

    List<AccountDto> accounts = getAccount(clientIdDto);
    if (accounts != null && !accounts.isEmpty()) {
      clientDto.setAccounts(accounts);
    }
    return clientDto;
  }

  @Override
  public List<ClientDto> findClient(ClientSearchDto clientSearchDto) {
    if (Stream.of(clientSearchDto.getId(), clientSearchDto.getBirthDate(),
        clientSearchDto.getFirstname(), clientSearchDto.getLastname(),
        clientSearchDto.getPatronymic(), clientSearchDto.getInn(),
        clientSearchDto.getPassport())
        .allMatch(Objects::isNull)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "All fields are null");
    }
    RequestBuilder<List<ClientDto>, ClientSearchDto> requestBuilder = new RequestBuilder<>();
    Mono<List<ClientDto>> entity = requestBuilder
        .sendRequest("/client", clientSearchDto);
    return objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
  }

  @Override
  public List<ContactDto> getContact(ClientIdDto clientIdDto) {
    RequestBuilder<List<ContactDto>, ClientIdDto> requestBuilder = new RequestBuilder<>();
    Mono<List<ContactDto>> entity = requestBuilder
        .sendRequest("/client/contact", clientIdDto);
    List<ContactDto> responseEntity = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (responseEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Null value");
    }
    return responseEntity;
  }

  @Override
  public ClientLevelDto getClientLevel(ClientIdDto clientIdDto) {
    RequestBuilder<ClientLevelDto, ClientIdDto> requestBuilder = new RequestBuilder<>();
    Mono<ClientLevelDto> entity = requestBuilder
        .sendRequest("/client/level", clientIdDto);
    ClientLevelDto responseEntity = objectMapper.convertValue(entity.block(),
        new TypeReference<>() {
        });
    if (responseEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Null value");
    }
    return responseEntity;
  }

  @Override
  public List<AccountDto> getAccount(ClientIdDto clientIdDto) {
    RequestBuilder<List<AccountDto>, ClientIdDto> requestBuilder = new RequestBuilder<>();
    Mono<List<AccountDto>> entity = requestBuilder
        .sendRequest("/client/account", clientIdDto);
    List<AccountDto> responseEntity = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (responseEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Null value");
    }
    return responseEntity;
  }

  @Override
  public List<OperationDto> getAccountOperations(
      AccountNumberDto accountNumberDto) {
    OperationSearchDto operationSearchDto = DtoMapper.INSTANCE
        .accountNumberToOperationSearch(accountNumberDto);
    RequestBuilder<List<OperationDto>, OperationSearchDto> requestBuilder =
        new RequestBuilder<>();
    Mono<List<OperationDto>> entity = requestBuilder
        .sendRequest("/client/account/operation", operationSearchDto);
    List<OperationDto> responseEntity = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (responseEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Null value");
    }
    return responseEntity;
  }

  @Override
  public ContactDto saveContact(ContactDto contactDto) {
    RequestBuilder<ContactDto, ContactDto> requestBuilder = new RequestBuilder<>();
    Mono<ContactDto> entity = requestBuilder
        .sendRequest("/client/contact/save", contactDto);
    ContactDto responseEntity = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (responseEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Null value");
    }
    return responseEntity;
  }

  @Override
  public LoanPaymentDto getLoanPayment(AccountNumberDto accountNumberDto) {
    RequestBuilder<LoanPaymentDto, AccountNumberDto> requestBuilder = new RequestBuilder<>();
    Mono<LoanPaymentDto> entity = requestBuilder
        .sendRequest("/client/account/loan-payment", accountNumberDto);
    LoanPaymentDto responseEntity = objectMapper
        .convertValue(entity.block(), new TypeReference<>() {
        });
    if (responseEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Null value");
    }
    return responseEntity;
  }

  private class RequestBuilder<T, B> {

    private Mono<T> sendRequest(String uri, B body) {
      log.info("The request sent to {} with body: {}", uri, body.toString());
      return webClient
          .post()
          .uri(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .header("CRM-User-Role", authentication.getAuthRole())
          .header("CRM-User-Name", authentication.getAuthName())
          .bodyValue(body)
          .exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
              return response.bodyToMono(new ParameterizedTypeReference<>() {
              });
            } else {
              return response.createException().flatMap(error -> {
                log.info("Text {}, status {}",
                    error.getResponseBodyAsString(), error.getStatusCode());
                return Mono.error(
                    new ResponseStatusException(error.getStatusCode(), "Exception occurred on Intranet side"));
              });
            }
          });
    }
  }
}
