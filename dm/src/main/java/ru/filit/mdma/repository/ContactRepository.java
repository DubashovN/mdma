package ru.filit.mdma.repository;

import java.util.List;
import ru.filit.mdma.model.entity.Contact;


public interface ContactRepository {

  List<Contact> getContacts(String clientId);

  Contact saveContact(Contact contact);

}
