package ru.filit.mdma.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.model.entity.User;
import ru.filit.mdma.util.EntityRepo;

@Repository
@Slf4j
public class UserRepository {

  @Getter
  private final List<User> users = new ArrayList<>();

  private final EntityRepo entityRepo;

  @Value(value = "datafiles")
  private String filePath;

  @Value(value = "users.yml")
  private String fileName;

  @Autowired
  public UserRepository(EntityRepo entityRepo) {
    this.entityRepo = entityRepo;
  }

  @PostConstruct
  public void init() {
    users.addAll(entityRepo.readList(getFilePath(), new TypeReference<>() {
    }));
    if (users.isEmpty()) {
      log.info("No available users from users.yml");
    }
  }

  private String getFilePath() {
    File file = new File(filePath, fileName);
    if (!file.isFile()) {
      log.info("Error on accessing file {}/{}", filePath, fileName);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users file doesn't exist");
    }
    return file.getPath();
  }
}
