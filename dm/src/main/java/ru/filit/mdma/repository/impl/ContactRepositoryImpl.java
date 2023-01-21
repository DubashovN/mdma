package ru.filit.mdma.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.model.entity.Contact;
import ru.filit.mdma.repository.ContactRepository;
import ru.filit.mdma.service.EntityRepo;
import ru.filit.mdma.web.exception.ClientNotFoundException;


@Repository
@Slf4j
public class ContactRepositoryImpl extends AbstractYmlRepository implements ContactRepository {

  @Value(value = "contacts.yml")
  private String fileName;

  @Value(value = "${dm.repo.location}")
  private String filePath;

  private final EntityRepo entityRepo;

  private final Map<String, List<Contact>> contacts = new ConcurrentHashMap<>();

  public ContactRepositoryImpl(EntityRepo entityRepo) {
    this.entityRepo = entityRepo;
  }

  @PostConstruct
  public void init() {
    final List<Contact> contactList =
        entityRepo.readList(getFile(filePath, fileName), new TypeReference<>() {
        });

    for (Contact contact : contactList) {
      contacts.computeIfAbsent(contact.getClientId(), v -> new CopyOnWriteArrayList<>())
          .add(contact);
    }
  }

  @Override
  public List<Contact> getContacts(String clientId) {
    return contacts.get(clientId) == null ? Collections.emptyList() : contacts.get(clientId);
  }

  @Override
  public synchronized Contact saveContact(Contact contact) {
    String clientId = contact.getClientId();
    if (!contacts.containsKey(clientId)) {
      throw new ClientNotFoundException("Client not found");
    }
    List<Contact> contactsByClientId = contacts.get(clientId);
    if (contact.getId() == null) {
      contact.setId(getGeneratedId());
    } else {
      contactsByClientId.removeIf(c -> c.getId().equals(contact.getId()));
    }
    contactsByClientId.add(contact);
    try {
      entityRepo.clearContents(getFile(filePath, fileName));
    } catch (FileNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Error occurred while saving the contact");
    }
    entityRepo.writeList(getFile(filePath, fileName), contacts.values()
        .stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList()));
    return contact;
  }

  private String getGeneratedId() {
    final int min = 10_000;
    final int max = 99_999;
    Random random = new Random();
    OptionalInt optionalInt = random.ints(min, (max + 1)).findFirst();
    if (optionalInt.isPresent()) {
      return String.valueOf(optionalInt.getAsInt());
    }
    return UUID.randomUUID().toString().replace("-", "").substring(0, 5);
  }
}
