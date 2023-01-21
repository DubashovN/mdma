package ru.filit.mdma.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.filit.mdma.web.dto.AccessDto;
import ru.filit.mdma.web.dto.AccessRequestDto;


@Validated
@Api(value = "access")
public interface AccessApi {

  /**
   * POST /access : Запрос прав доступа
   *
   * @param accessRequestDto  (required)
   * @return Информации о правах доступа (status code 200)
   */
  @ApiOperation(value = "Запрос прав доступа", nickname = "getAccess",
      response = AccessDto.class, responseContainer = "List", tags={  })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Информации о правах доступа",
          response = AccessDto.class, responseContainer = "List")
  })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/access",
      produces = { "application/json" },
      consumes = { "application/json" }
  )
  ResponseEntity<List<AccessDto>> getAccess(@Valid @RequestBody AccessRequestDto accessRequestDto);
}
