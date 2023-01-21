package ru.filit.oas.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.oas.dm.web.controller.AccessApi;
import ru.filit.oas.dm.web.dto.AccessDto;
import ru.filit.oas.dm.web.dto.AccessRequestDto;
import ru.filit.oas.web.exception.ClientNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccessController implements AccessApi {

    private static final String MANAGER_ROLE = "MANAGER";

    @Override
    public ResponseEntity<List<AccessDto>> getAccess(AccessRequestDto body) {
        if (body.getRole().equals(MANAGER_ROLE)) {
            throw new ClientNotFoundException("For this role client is not present");
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "For this role client is not present");
        }
        List<AccessDto> accessDtoList = new ArrayList<>();
        accessDtoList.add((new AccessDto()).entity("client").property("id"));
        accessDtoList.add((new AccessDto()).entity("client").property("lastname"));
        return ResponseEntity.ok(accessDtoList);
    }

}
