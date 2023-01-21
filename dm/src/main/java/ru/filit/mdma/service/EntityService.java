package ru.filit.mdma.service;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.filit.mdma.model.entity.Client;
import ru.filit.mdma.model.entity.Contact;

@Service
public class EntityService {

  private static final Logger log = LoggerFactory.getLogger(EntityService.class);
  private static final String FILEEXT = ".yml";
  private static final String CLIENT_FILENAME = "clients";
  private static final String CONTACT_FILENAME = "contacts";

  // ENV VAR with location for db yml files
  @Value("${dm.repo.location}")
  private String location;

  private final EntityRepo entityRepo;

  public EntityService(EntityRepo entityRepo) {
    this.entityRepo = entityRepo;
  }

  public void writeClients() {
    String filename = location + CLIENT_FILENAME + FILEEXT;
    entityRepo.writeList(filename, generateClients1());

    filename = location + CLIENT_FILENAME + "2" + FILEEXT;
    entityRepo.writeList(filename, generateClients2());
  }

  public List<Client> readClients() {
    String filename = location + CLIENT_FILENAME + FILEEXT;
    return entityRepo.readList(filename, new TypeReference<List<Client>>() {
    });
  }

  public void writeContacts() {
    String filename = location + CONTACT_FILENAME + FILEEXT;
    entityRepo.writeList(filename, generateContacts1());

    filename = location + CONTACT_FILENAME + "2" + FILEEXT;
    entityRepo.writeList(filename, generateContacts2());
  }

  public List<Contact> readContacts() {
    String filename = location + CONTACT_FILENAME + FILEEXT;
    return entityRepo.readList(filename, new TypeReference<List<Contact>>() {
    });
  }

  private List<Client> generateClients1() {
    List<Client> cc = new ArrayList<>();

    cc.add(
        (new Client()).id("73489").lastname("Литвинов").firstname("Михаил").patronymic("Георгиевич")
            .birthDate(-193622400L).passportSeries("4503").passportNumber("836409")
            .inn("770395379271")
            .address("Москва, ул. Академика Бочвара, 5 к2, кв 14"));
    cc.add(
        (new Client()).id("80302").lastname("Анисимов").firstname("Арсений").patronymic("Давидович")
            .birthDate(434937600L).passportSeries("4507").passportNumber("611323")
            .inn("771169595448")
            .address("Москва, ул. Санникова, 9 к1, кв 6"));
    cc.add((new Client()).id("15828").lastname("Зайцев").firstname("Борис").patronymic("Даниилович")
        .birthDate(370310400L).passportSeries("2812").passportNumber("578948").inn("695049937846")
        .address("Тверь, ул. Достоевского, 21"));
    cc.add(
        (new Client()).id("68709").lastname("Федотов").firstname("Михаил").patronymic("Михайлович")
            .birthDate(899942400L).passportSeries("2814").passportNumber("344131")
            .inn("695057414056")
            .address("Тверь, ул. Трёхсвятская, 31/32, кв 5"));
    cc.add((new Client()).id("59804").lastname("Исаев").firstname("Платон").patronymic("Ильич")
        .birthDate(714700800L).passportSeries("2907").passportNumber("832627").inn("402925197508")
        .address("Калуга, ул. Веры Андриановой, 26, кв 12"));
    cc.add(
        (new Client()).id("76628").lastname("Анисимов").firstname("Даниэль").patronymic("Сергеевич")
            .birthDate(572313600L).passportSeries("2951").passportNumber("409759")
            .inn("402189588276")
            .address("Калуга, ул. Хрустальная, 44 к4, кв 4"));
    cc.add((new Client()).id("41301").lastname("Беляева").firstname("Анна").patronymic("Руслановна")
        .birthDate(498441600L).passportSeries("4517").passportNumber("637230").inn("770221614164")
        .address("Москва, ул. Строителей, 5 к5, кв 21"));
    cc.add((new Client()).id("68025").lastname("Гусев").firstname("Марк").patronymic("Михайлович")
        .birthDate(572313600L).passportSeries("4511").passportNumber("625338").inn("770252183418")
        .address("Москва, ул. Винокурова, 24 к1, кв 25"));
    cc.add(
        (new Client()).id("21685").lastname("Новикова").firstname("Ирина").patronymic("Георгиевна")
            .birthDate(714700800L).passportSeries("2002").passportNumber("367037")
            .inn("366626878306")
            .address("Воронеж, ул. Киевская, 29"));
    cc.add(
        (new Client()).id("17325").lastname("Харитонов").firstname("Олег").patronymic("Андреевич")
            .birthDate(190080000L).passportSeries("2038").passportNumber("493040")
            .inn("362514844896")
            .address("Воронеж, ул. Урицкого, 56, кв 17"));

    return cc;
  }

  private List<Client> generateClients2() {
    List<Client> cc = new ArrayList<>();

    cc.add((new Client()).id("53280").lastname("Касаткин").firstname("Александр")
        .patronymic("Максимович")
        .birthDate(202003200L).passportSeries("4501").passportNumber("635084").inn("770159072206")
        .address("Москва, ул. , кв 14"));
    cc.add(
        (new Client()).id("59114").lastname("Романов").firstname("Рустам").patronymic("Михайлович")
            .birthDate(371347200L).passportSeries("4502").passportNumber("495882")
            .inn("770286193973")
            .address("Москва, ул. , кв 6"));
    cc.add(
        (new Client()).id("11027").lastname("Козлов").firstname("Станислав").patronymic("Маркович")
            .birthDate(523584000L).passportSeries("4503").passportNumber("829868")
            .inn("770333681964")
            .address("Москва, ул. , 21"));
    cc.add((new Client()).id("98904").lastname("Матвеева").firstname("Анна").patronymic("Павловна")
        .birthDate(-165628800L).passportSeries("4605").passportNumber("158622").inn("500776896407")
        .address("Дмитров, ул. , кв 6"));
    cc.add((new Client()).id("70216").lastname("Козлов").firstname("Савелий")
        .patronymic("Владимирович")
        .birthDate(371347200L).passportSeries("4609").passportNumber("580023").inn("501050529345")
        .address("Дубна, ул. , 21"));
    cc.add((new Client()).id("19292").lastname("Свешников").firstname("Сергей")
        .patronymic("Николаевич")
        .birthDate(-57196800L).passportSeries("2811").passportNumber("682170").inn("690798497451")
        .address("Тверь, ул. , 21"));
    cc.add(
        (new Client()).id("33434").lastname("Козлов").firstname("Алексей").patronymic("Дмитриевич")
            .birthDate(854582400L).passportSeries("2821").passportNumber("327337")
            .inn("690306329348")
            .address("Тверь, ул. , 21"));
    cc.add((new Client()).id("55420").lastname("Суханова").firstname("Карина")
        .patronymic("Александровна")
        .birthDate(371347200L).passportSeries("1712").passportNumber("823819").inn("332734492085")
        .address("Владимир, ул. , 21"));
    cc.add((new Client()).id("52433").lastname("Анисимова").firstname("Варвара")
        .patronymic("Платоновна")
        .birthDate(772675200L).passportSeries("1712").passportNumber("279721").inn("332703917514")
        .address("Владимир, ул. , 21"));

    return cc;
  }

  private List<Contact> generateContacts1() {
    List<Contact> cc = new ArrayList<>();

    cc.add((new Contact()).id("74914").clientId("73489").type(Contact.TypeEnum.PHONE)
        .value("+79170041937"));
    cc.add((new Contact()).id("77615").clientId("73489").type(Contact.TypeEnum.EMAIL)
        .value("mglitvinov@mail.ru"));
    cc.add((new Contact()).id("34028").clientId("80302").type(Contact.TypeEnum.PHONE)
        .value("+79079256329"));
    cc.add((new Contact()).id("42906").clientId("80302").type(Contact.TypeEnum.PHONE)
        .value("+79633505208"));
    cc.add((new Contact()).id("45152").clientId("80302").type(Contact.TypeEnum.EMAIL)
        .value("anisimov.ad@mail.ru"));
    cc.add((new Contact()).id("24943").clientId("15828").type(Contact.TypeEnum.PHONE)
        .value("+79171940597"));
    cc.add((new Contact()).id("53583").clientId("15828").type(Contact.TypeEnum.EMAIL)
        .value("b.zaycev@yandex.ru"));
    cc.add((new Contact()).id("48762").clientId("68709").type(Contact.TypeEnum.PHONE)
        .value("+79277362567"));
    cc.add((new Contact()).id("47542").clientId("68709").type(Contact.TypeEnum.EMAIL)
        .value("fedot@list.ru"));
    cc.add((new Contact()).id("14174").clientId("59804").type(Contact.TypeEnum.PHONE)
        .value("+79060318219"));
    cc.add((new Contact()).id("40294").clientId("59804").type(Contact.TypeEnum.EMAIL)
        .value("isaev.pi@mail.ru"));
    cc.add((new Contact()).id("11744").clientId("76628").type(Contact.TypeEnum.PHONE)
        .value("+79052854934"));
    cc.add((new Contact()).id("79174").clientId("76628").type(Contact.TypeEnum.EMAIL)
        .value("danisimov@gmail.com"));
    cc.add((new Contact()).id("27243").clientId("41301").type(Contact.TypeEnum.PHONE)
        .value("+79174747172"));
    cc.add((new Contact()).id("94748").clientId("41301").type(Contact.TypeEnum.EMAIL)
        .value("belanna@mail.ru"));
    cc.add((new Contact()).id("35531").clientId("68025").type(Contact.TypeEnum.PHONE)
        .value("+79178340326"));
    cc.add((new Contact()).id("29603").clientId("68025").type(Contact.TypeEnum.EMAIL)
        .value("gusev.mm@yandex.ru"));
    cc.add((new Contact()).id("62592").clientId("21685").type(Contact.TypeEnum.PHONE)
        .value("+79271344482"));
    cc.add((new Contact()).id("46919").clientId("17325").type(Contact.TypeEnum.PHONE)
        .value("+79062947627"));
    cc.add((new Contact()).id("21065").clientId("17325").type(Contact.TypeEnum.EMAIL)
        .value("oleg2000@mail.ru"));

    return cc;
  }

  private List<Contact> generateContacts2() {
    List<Contact> cc = new ArrayList<>();

    cc.add((new Contact()).id("91495").clientId("53280").type(Contact.TypeEnum.PHONE)
        .value("+79177217042"));
    cc.add((new Contact()).id("66187").clientId("53280").type(Contact.TypeEnum.EMAIL)
        .value("amkasatkin@mail.ru"));
    cc.add((new Contact()).id("53018").clientId("59114").type(Contact.TypeEnum.PHONE)
        .value("+79633505208"));
    cc.add((new Contact()).id("31217").clientId("11027").type(Contact.TypeEnum.PHONE)
        .value("+79271501840"));
    cc.add((new Contact()).id("67179").clientId("11027").type(Contact.TypeEnum.PHONE)
        .value("+79170088581"));
    cc.add((new Contact()).id("47791").clientId("11027").type(Contact.TypeEnum.EMAIL)
        .value("kozlov.sm@mail.ru"));
    cc.add((new Contact()).id("37859").clientId("98904").type(Contact.TypeEnum.PHONE)
        .value("+79050081177"));
    cc.add((new Contact()).id("46852").clientId("98904").type(Contact.TypeEnum.EMAIL)
        .value("amatveeva@mail.ru"));
    cc.add((new Contact()).id("74333").clientId("70216").type(Contact.TypeEnum.PHONE)
        .value("+79068771623"));
    cc.add((new Contact()).id("61176").clientId("70216").type(Contact.TypeEnum.EMAIL)
        .value("kozlovs@gmail.com"));
    cc.add((new Contact()).id("67527").clientId("19292").type(Contact.TypeEnum.PHONE)
        .value("+79177498344"));
    cc.add((new Contact()).id("49324").clientId("19292").type(Contact.TypeEnum.EMAIL)
        .value("sveshnik9@yandex.ru"));
    cc.add((new Contact()).id("94342").clientId("33434").type(Contact.TypeEnum.PHONE)
        .value("+79626636324"));
    cc.add((new Contact()).id("49262").clientId("33434").type(Contact.TypeEnum.EMAIL)
        .value("adkozlov@mail.ru"));
    cc.add((new Contact()).id("25284").clientId("55420").type(Contact.TypeEnum.PHONE)
        .value("+79270375184"));
    cc.add((new Contact()).id("16272").clientId("55420").type(Contact.TypeEnum.EMAIL)
        .value("such.ka@gmail.com"));
    cc.add((new Contact()).id("92057").clientId("52433").type(Contact.TypeEnum.PHONE)
        .value("+79175755339"));
    cc.add((new Contact()).id("27311").clientId("52433").type(Contact.TypeEnum.EMAIL)
        .value("vanisimova@list.ru"));

    return cc;
  }
}
