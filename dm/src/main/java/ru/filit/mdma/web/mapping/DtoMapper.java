package ru.filit.mdma.web.mapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.apache.commons.validator.routines.EmailValidator;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.filit.mdma.model.entity.Access;
import ru.filit.mdma.model.entity.Account;
import ru.filit.mdma.model.entity.Client;
import ru.filit.mdma.model.entity.Contact;
import ru.filit.mdma.model.entity.Operation;
import ru.filit.mdma.web.dto.AccessDto;
import ru.filit.mdma.web.dto.AccountDto;
import ru.filit.mdma.web.dto.ClientDto;
import ru.filit.mdma.web.dto.ClientSearchDto;
import ru.filit.mdma.web.dto.ContactDto;
import ru.filit.mdma.web.dto.OperationDto;

@Mapper
public abstract class DtoMapper {

  public static final DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  protected String fromInstant(Long timestamp, boolean withTime, String zoneId) {
    if (timestamp == null) {
      return null;
    }
    // "Europe/Moscow" or "UTC"
    Instant instant = Instant.ofEpochSecond(timestamp);
    if (withTime) {
      LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.of(zoneId));
      return date.format(DATE_TIME_FORMATTER);
    } else {
      LocalDate date = LocalDate.ofInstant(instant, ZoneId.of(zoneId));
      return date.format(DATE_FORMATTER);
    }
  }

  protected Long toInstant(String date) {
    if (date == null) {
      return null;
    }
    LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
    LocalDateTime localDateTime = LocalDateTime.of(localDate,
        LocalTime.of(0, 0, 0, 0));
    return localDateTime.toEpochSecond(ZoneOffset.UTC);
  }

  @Mapping(target = "birthDate",
      expression = "java(fromInstant(client.getBirthDate(), false, \"UTC\"))")
  public abstract ClientDto clientToClientDto(Client client);

  @Mappings(value = {
      @Mapping(target = "birthDate",
          expression = "java(toInstant(clientSearchDto.getBirthDate()))")
  })
  public abstract Client clientSearchDtoToClient(ClientSearchDto clientSearchDto);

  public abstract ContactDto contactToContactDto(Contact contact);

  public abstract Contact contactDtoToContact(ContactDto contactDto);

  @Mappings(value = {
      @Mapping(target = "openDate",
          expression = "java(fromInstant(account.getOpenDate(), false, \"UTC\"))"),
      @Mapping(target = "closeDate",
          expression = "java(fromInstant(account.getCloseDate(), false, \"UTC\"))")
  })
  public abstract AccountDto accountToAccountDto(Account account);

  @Mapping(target = "operDate",
      expression = "java(fromInstant(operation.getOperDate(), true, \"Europe/Moscow\"))")
  public abstract OperationDto operationToOperationDto(Operation operation);

  public abstract AccessDto accessToAccessDto(Access access);

  @AfterMapping
  protected void setShortcut(Account account, @MappingTarget AccountDto accountDto) {
    String number = account.getNumber();
    if (number == null || number.length() < 4) {
      return;
    }
    accountDto.setShortcut(number.substring(number.length() - 4));
  }

  @AfterMapping
  protected void setShortcut(Contact contact, @MappingTarget ContactDto contactDto) {
    String value = contact.getValue();
    if (value == null) {
      return;
    }
    switch (contact.getType()) {
      case EMAIL:
        if (EmailValidator.getInstance().isValid(value)) {
          int fromIndex = value.lastIndexOf("@") - 1;
          contactDto.setShortcut(value.substring(fromIndex));
        }
        break;
      case PHONE:
        if (value.length() > 4) {
          contactDto.setShortcut(value.substring(value.length() - 4));
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + contact.getType());
    }
  }

  @AfterMapping
  protected void setPassport(ClientSearchDto clientSearchDto, @MappingTarget Client client) {
    if (clientSearchDto.getPassport() == null) {
      return;
    }
    String[] passport = clientSearchDto.getPassport().split(" ");
    if (passport.length == 2) {
      client.setPassportSeries(passport[0]);
      client.setPassportNumber(passport[1]);
    }
  }
}
