package ru.filit.mdma.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.filit.mdma.web.dto.AccountNumberDto;
import ru.filit.mdma.web.dto.ClientIdDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.dto.OperationSearchDto;

@Mapper
public abstract class DtoMapper {

  public static final DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

  public abstract ClientSearchDto idDtoToSearchDto(ClientIdDto clientIdDto);

  @Mapping(target = "quantity", constant = "3")
  public abstract OperationSearchDto accountNumberToOperationSearch(
      AccountNumberDto accountNumberDto);

}
