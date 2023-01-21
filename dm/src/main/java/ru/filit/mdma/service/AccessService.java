package ru.filit.mdma.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.model.entity.Access;
import ru.filit.mdma.repository.AccessRepository;
import ru.filit.mdma.web.dto.AccessDto;
import ru.filit.mdma.web.dto.AccessRequestDto;
import ru.filit.mdma.web.mapping.DtoMapper;


@Service
@AllArgsConstructor
public class AccessService {

  private final AccessRepository accessRepository;

  public List<AccessDto> getAccess(AccessRequestDto body) {
    if (body.getRole() == null || body.getRole().isBlank()
        || body.getVersion() == null || body.getVersion().isBlank()) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN, "Role is not determined");
    }
    List<Access> accessForRole = accessRepository
        .getAccessForRole(body.getRole(), body.getVersion());
    return accessForRole.stream()
        .map(DtoMapper.INSTANCE::accessToAccessDto)
        .collect(Collectors.toList());
  }
}
