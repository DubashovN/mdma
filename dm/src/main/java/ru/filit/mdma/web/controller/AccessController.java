package ru.filit.mdma.web.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.service.AccessService;
import ru.filit.mdma.web.dto.AccessDto;
import ru.filit.mdma.web.dto.AccessRequestDto;

@RestController
@AllArgsConstructor
public class AccessController implements AccessApi {

  private final AccessService accessService;

  @Override
  public ResponseEntity<List<AccessDto>> getAccess(AccessRequestDto body) {
    List<AccessDto> accessDtoList = accessService.getAccess(body);
    return ResponseEntity.ok(accessDtoList);
  }
}