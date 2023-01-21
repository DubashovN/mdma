package ru.filit.mdma.service;

import java.util.List;
import ru.filit.mdma.web.dto.AccessDto;
import ru.filit.mdma.web.dto.ClientDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ClientLevelDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.dto.ContactDto;


public interface ClientService {

  List<ClientDto> findClient(ClientSearchDto clientSearchDto, List<AccessDto> access,
      String crMUserName, List<AccessDto> accessAudit);

  ClientLevelDto getClientLevel(ClientIdDto clientIdDto, List<AccessDto> access,
      String crMUserName, List<AccessDto> accessAudit);

  List<ContactDto> getContact(ClientIdDto clientIdDto, List<AccessDto> access,
      String crMUserName, List<AccessDto> accessAudit);

  ContactDto saveContact(ContactDto contactDto, List<AccessDto> access,
      String crMUserName, List<AccessDto> accessAudit);

}
