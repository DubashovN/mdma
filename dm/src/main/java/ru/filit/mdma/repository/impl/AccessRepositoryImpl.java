package ru.filit.mdma.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.model.entity.Access;
import ru.filit.mdma.repository.AccessRepository;
import ru.filit.mdma.service.EntityRepo;


@Repository
@Slf4j
public class AccessRepositoryImpl extends AbstractYmlRepository implements AccessRepository {

  @Value(value = "access")
  private String fileName;

  @Value(value = ".yml")
  private String fileType;

  @Value(value = "${dm.repo.location}")
  private String filePath;

  private final EntityRepo entityRepo;

  public AccessRepositoryImpl(EntityRepo entityRepo) {
    this.entityRepo = entityRepo;
  }

  @Override
  public List<Access> getAccessForRole(String role, String version) {
    List<Access> accesses = entityRepo.readList(
        getFile(filePath, fileName + version + fileType), new TypeReference<>() {
        });
    if (accesses == null) {
      accesses = Collections.emptyList();
    }
    return accesses.stream()
        .filter(access -> access.getRole().equals(role))
        .collect(Collectors.toList());
  }
}
