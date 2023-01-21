package ru.filit.mdma.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.filit.mdma.model.entity.Contact;
import ru.filit.mdma.repository.ContactRepository;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ContactDto;
import ru.filit.mdma.web.exception.InvalidDataException;
import ru.filit.mdma.web.mapping.DtoMapper;

@Service
public class ContactService {

  private final ContactRepository contactRepository;

  public ContactService(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  public List<ContactDto> getContact(ClientIdDto clientIdDto) {
    String id = clientIdDto.getId();
    if (id == null || id.isBlank()) {
      throw new InvalidDataException("Отсутствует идентификатор клиента");
    }
    final List<Contact> contacts = contactRepository.getContacts(id);
    return contacts.stream()
        .map(DtoMapper.INSTANCE::contactToContactDto)
        .collect(Collectors.toList());
  }

  public ContactDto saveContact(ContactDto contactDto) {
    if (contactDto.getClientId() == null || contactDto.getClientId().isBlank()) {
      throw new InvalidDataException("Отсутствует идентификатор клиента");
    }
    Contact savedContact = contactRepository.saveContact(
        DtoMapper.INSTANCE.contactDtoToContact(contactDto));
    return DtoMapper.INSTANCE.contactToContactDto(savedContact);
  }
}
