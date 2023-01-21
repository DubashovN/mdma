package ru.filit.mdma.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import ru.filit.mdma.model.entity.Client;
import ru.filit.mdma.repository.ClientRepository;
import ru.filit.mdma.web.dto.ClientDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.exception.InvalidDataException;
import ru.filit.mdma.web.mapping.DtoMapper;


@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public List<ClientDto> findClients(ClientSearchDto clientSearchDto) {
    final Client clientSearch = DtoMapper.INSTANCE.clientSearchDtoToClient(clientSearchDto);
    if (clientSearch.getId() != null
        && Stream.of(clientSearch.getBirthDate(),
            clientSearch.getFirstname(), clientSearch.getLastname(),
            clientSearch.getPatronymic(), clientSearch.getInn(),
            clientSearch.getPassportNumber(), clientSearch.getPassportSeries())
            .allMatch(Objects::isNull)) {
      final Client clientById = getClientOrError(clientSearch.getId());
      return List.of(DtoMapper.INSTANCE.clientToClientDto(clientById));
    }
    if (Stream.of(clientSearch.getId(), clientSearch.getBirthDate(),
        clientSearch.getFirstname(), clientSearch.getLastname(),
        clientSearch.getPatronymic(), clientSearch.getInn(),
        clientSearch.getPassportNumber(), clientSearch.getPassportSeries())
        .allMatch(Objects::isNull)) {
      throw new InvalidDataException("Хотя бы один из атрибутов должен быть заполнен");
    }
    List<Client> result = clientRepository.findClientByParams(clientSearch);
    return result.stream()
        .map(DtoMapper.INSTANCE::clientToClientDto)
        .collect(Collectors.toList());
  }

  private Client getClientOrError(String id) {
    return clientRepository.getClientById(id);
  }
}
