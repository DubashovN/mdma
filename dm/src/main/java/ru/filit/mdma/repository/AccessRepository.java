package ru.filit.mdma.repository;

import java.util.List;
import ru.filit.mdma.model.entity.Access;


public interface AccessRepository {

  List<Access> getAccessForRole(String role, String version);

}
