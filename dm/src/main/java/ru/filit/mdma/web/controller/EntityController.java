package ru.filit.mdma.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.model.entity.Client;
import ru.filit.mdma.model.entity.Contact;
import ru.filit.mdma.service.EntityService;

@RestController
public class EntityController {

  private final EntityService entityService;

  @Autowired
  public EntityController(EntityService entityService) {
    this.entityService = entityService;
  }

  @PostMapping("/client/write")
  public ResponseEntity<String> writeClients() {
    entityService.writeClients();
    return ResponseEntity.ok("written");
  }

  @PostMapping("/client/read")
  public ResponseEntity<String> readClients() {
    List<Client> clients = entityService.readClients();
    return ResponseEntity.ok(clients.get(5).getLastname());
  }

  @PostMapping("/contact/write")
  public ResponseEntity<String> writeContact() {
    entityService.writeContacts();
    return ResponseEntity.ok("written");
  }

  @PostMapping("/contact/read")
  public ResponseEntity<String> readContact() {
    List<Contact> clients = entityService.readContacts();
    return ResponseEntity.ok(clients.get(5).getValue());
  }

}