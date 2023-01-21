package ru.filit.oas.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.oas.crm.model.User;
import ru.filit.oas.dm.model.Access;
import ru.filit.oas.dm.model.Account;
import ru.filit.oas.dm.model.Client;
import ru.filit.oas.dm.model.Contact;
import ru.filit.oas.service.EntityService;

import java.util.List;

@RestController
public class EntityController {

    @Autowired
    private EntityService entityService;

    @PostMapping("/user/write")
    public ResponseEntity<String> writeUsers() {
        entityService.writeUsers();
        return ResponseEntity.ok("written");
    }

    @PostMapping("/user/read")
    public ResponseEntity<String> readUsers() {
        List<User> users = entityService.readUsers();
        return ResponseEntity.ok(users.get(1).getPassword());
    }

    @PostMapping("/access/write")
    public ResponseEntity<String> writeAccess() {
        entityService.writeAccess();
        return ResponseEntity.ok("written");
    }

    @PostMapping("/access/read")
    public ResponseEntity<String> readAccess() {
        List<Access> access = entityService.readAccess();
        return ResponseEntity.ok(access.get(1).getProperty());
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

    @PostMapping("/account/write")
    public ResponseEntity<String> writeAccount() {
        entityService.writeAcounts();
        return ResponseEntity.ok("written");
    }

    @PostMapping("/account/read")
    public ResponseEntity<String> readAccount() {
        List<Account> accounts = entityService.readAcounts();
        return ResponseEntity.ok(accounts.get(5).getNumber());
    }
}
