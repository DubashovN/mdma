package ru.filit.oas.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.filit.oas.crm.model.Role;
import ru.filit.oas.crm.model.User;
import ru.filit.oas.dm.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityService {

    private static final Logger log = LoggerFactory.getLogger(EntityService.class);
    private static final String FILEEXT = ".yml";
    private static final String USER_FILENAME = "users";
    private static final String ACCESS_FILENAME = "access";
    private static final String CLIENT_FILENAME = "clients";
    private static final String CONTACT_FILENAME = "contacts";
    private static final String ACCOUNT_FILENAME = "accounts";

    @Autowired
    private EntityRepo entityRepo;

    final ObjectMapper objectMapper = new ObjectMapper(
            new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
    );

    public void writeUsers() {
        String filename = USER_FILENAME + FILEEXT;
        entityRepo.writeList(filename, generateUsers1());

        filename = USER_FILENAME + "2" + FILEEXT;
        entityRepo.writeList(filename, generateUsers2());
    }

    public List<User> readUsers() {
        String filename = USER_FILENAME + FILEEXT;
        return entityRepo.readList(filename, new TypeReference<List<User>>() {});
    }

    public void writeAccess() {
        String filename = ACCESS_FILENAME + "2" + FILEEXT;
        entityRepo.writeList(filename, generateAccess2());

        filename = ACCESS_FILENAME + "3" + FILEEXT;
        entityRepo.writeList(filename, generateAccess3());
    }

    public List<Access> readAccess() {
        String filename = ACCESS_FILENAME + "2" + FILEEXT;
        return entityRepo.readList(filename, new TypeReference<List<Access>>() {});
    }

    public void writeClients() {
        String filename = CLIENT_FILENAME + FILEEXT;
        entityRepo.writeList(filename, generateClients1());

        filename = CLIENT_FILENAME + "2" + FILEEXT;
        entityRepo.writeList(filename, generateClients2());
    }

    public List<Client> readClients() {
        String filename = CLIENT_FILENAME + FILEEXT;
        return entityRepo.readList(filename, new TypeReference<List<Client>>() {});
    }

    public void writeContacts() {
        String filename = CONTACT_FILENAME + FILEEXT;
        entityRepo.writeList(filename, generateContacts1());

        filename = CONTACT_FILENAME + "2" + FILEEXT;
        entityRepo.writeList(filename, generateContacts2());
    }

    public List<Contact> readContacts() {
        String filename = CONTACT_FILENAME + FILEEXT;
        return entityRepo.readList(filename, new TypeReference<List<Contact>>() {});
    }

    public void writeAcounts() {
        String filename = ACCOUNT_FILENAME + FILEEXT;
        entityRepo.writeList(filename, generateAccounts1());

        filename = ACCOUNT_FILENAME + "2" + FILEEXT;
        entityRepo.writeList(filename, generateAccounts2());
    }

    public List<Account> readAcounts() {
        String filename = ACCOUNT_FILENAME + FILEEXT;
        return entityRepo.readList(filename, new TypeReference<List<Account>>() {});
    }

    private List<User> generateUsers1() {
        List<User> uu = new ArrayList<>();
        uu.add((new User()).username("mlsidorova").role(Role.MANAGER)
                .password((new BCryptPasswordEncoder()).encode("PYx16GvL")));
        uu.add((new User()).username("agterentyeva").role(Role.SUPERVISOR)
                .password((new BCryptPasswordEncoder()).encode("ojwkFc2k")));
        return uu;
    }

    private List<User> generateUsers2() {
        List<User> uu = new ArrayList<>();
        uu.add((new User()).username("vpmoiseeva").role(Role.MANAGER)
                .password((new BCryptPasswordEncoder()).encode("54RKIjqG")));
        uu.add((new User()).username("kazaharov").role(Role.SUPERVISOR)
                .password((new BCryptPasswordEncoder()).encode("L9ixiov5")));
        return uu;
    }

    private List<Access> generateAccess2() {
        List<Access> aa = new ArrayList<>();

        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("id"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("lastname"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("firstname"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("patronymic"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("birthDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("contact").property("id"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("contact").property("clientId"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("contact").property("type"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("contact").property("shortcut"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("number"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("clientId"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("type"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("currency"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("status"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("openDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("closeDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("shortcut"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("operation").property("type"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("operation").property("accountNumber"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("operation").property("operDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("clientLevel").property("level"));

        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("id"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("lastname"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("firstname"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("patronymic"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("birthDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("inn"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("address"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("id"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("clientId"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("type"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("value"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("shortcut"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("number"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("clientId"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("type"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("currency"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("status"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("openDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("closeDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("deferment"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("shortcut"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("type"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("accountNumber"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("operDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("amount"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("description"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("currentBalance").property("balanceAmount"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("clientLevel").property("level"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("clientLevel").property("accountNumber"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("clientLevel").property("avgBalance"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("loanPayment").property("amount"));

        aa.add((new Access()).role(Role.AUDITOR.name()).entity("client").property("id"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("client").property("lastname"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("client").property("passportNum"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("id"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("clientId"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("type"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("shortcut"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("number"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("clientId"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("type"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("currency"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("status"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("openDate"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("closeDate"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("shortcut"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("type"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("accountNumber"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("operDate"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("amount"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("clientLevel").property("level"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("clientLevel").property("accountNumber"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("loanPayment").property("amount"));

        return aa;
    }

    private List<Access> generateAccess3() {
        List<Access> aa = new ArrayList<>();

        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("lastname"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("firstname"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("patronymic"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("client").property("birthDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("contact").property("type"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("contact").property("shortcut"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("type"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("currency"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("status"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("openDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("closeDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("account").property("shortcut"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("operation").property("type"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("operation").property("operDate"));
        aa.add((new Access()).role(Role.MANAGER.name()).entity("clientLevel").property("level"));

        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("lastname"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("firstname"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("patronymic"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("birthDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("inn"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("client").property("address"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("type"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("value"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("contact").property("shortcut"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("number"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("type"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("currency"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("status"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("openDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("closeDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("deferment"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("account").property("shortcut"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("type"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("accountNumber"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("operDate"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("amount"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("operation").property("description"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("currentBalance").property("balanceAmount"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("clientLevel").property("level"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("clientLevel").property("accountNumber"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("clientLevel").property("avgBalance"));
        aa.add((new Access()).role(Role.SUPERVISOR.name()).entity("loanPayment").property("amount"));

        aa.add((new Access()).role(Role.AUDITOR.name()).entity("client").property("id"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("client").property("lastname"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("client").property("passportNum"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("id"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("clientId"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("type"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("contact").property("shortcut"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("number"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("clientId"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("type"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("currency"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("status"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("openDate"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("closeDate"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("account").property("shortcut"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("type"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("accountNumber"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("operDate"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("operation").property("amount"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("clientLevel").property("level"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("clientLevel").property("accountNumber"));
        aa.add((new Access()).role(Role.AUDITOR.name()).entity("loanPayment").property("amount"));

        return aa;
    }

    private List<Client> generateClients1() {
        List<Client> cc = new ArrayList<>();

        cc.add((new Client()).id("73489").lastname("Литвинов").firstname("Михаил").patronymic("Георгиевич")
                .birthDate(-193622400000L).passportSeries("4503").passportNumber("836409").inn("770395379271")
                .address("Москва, ул. Академика Бочвара, 5 к2, кв 14"));
        cc.add((new Client()).id("80302").lastname("Анисимов").firstname("Арсений").patronymic("Давидович")
                .birthDate(434937600000L).passportSeries("4507").passportNumber("611323").inn("771169595448")
                .address("Москва, ул. Санникова, 9 к1, кв 6"));
        cc.add((new Client()).id("15828").lastname("Зайцев").firstname("Борис").patronymic("Даниилович")
                .birthDate(370310400000L).passportSeries("2812").passportNumber("578948").inn("695049937846")
                .address("Тверь, ул. Достоевского, 21"));
        cc.add((new Client()).id("68709").lastname("Федотов").firstname("Михаил").patronymic("Михайлович")
                .birthDate(899942400000L).passportSeries("2814").passportNumber("344131").inn("695057414056")
                .address("Тверь, ул. Трёхсвятская, 31/32, кв 5"));
        cc.add((new Client()).id("59804").lastname("Исаев").firstname("Платон").patronymic("Ильич")
                .birthDate(714700800000L).passportSeries("2907").passportNumber("832627").inn("402925197508")
                .address("Калуга, ул. Веры Андриановой, 26, кв 12"));
        cc.add((new Client()).id("76628").lastname("Анисимов").firstname("Даниэль").patronymic("Сергеевич")
                .birthDate(572313600000L).passportSeries("2951").passportNumber("409759").inn("402189588276")
                .address("Калуга, ул. Хрустальная, 44 к4, кв 4"));
        cc.add((new Client()).id("41301").lastname("Беляева").firstname("Анна").patronymic("Руслановна")
                .birthDate(498441600000L).passportSeries("4517").passportNumber("637230").inn("770221614164")
                .address("Москва, ул. Строителей, 5 к5, кв 21"));
        cc.add((new Client()).id("68025").lastname("Гусев").firstname("Марк").patronymic("Михайлович")
                .birthDate(572313600000L).passportSeries("4511").passportNumber("625338").inn("770252183418")
                .address("Москва, ул. Винокурова, 24 к1, кв 25"));
        cc.add((new Client()).id("21685").lastname("Новикова").firstname("Ирина").patronymic("Георгиевна")
                .birthDate(714700800000L).passportSeries("2002").passportNumber("367037").inn("366626878306")
                .address("Воронеж, ул. Киевская, 29"));
        cc.add((new Client()).id("17325").lastname("Харитонов").firstname("Олег").patronymic("Андреевич")
                .birthDate(190080000000L).passportSeries("2038").passportNumber("493040").inn("362514844896")
                .address("Воронеж, ул. Урицкого, 56, кв 17"));

        return cc;
    }

    private List<Client> generateClients2() {
        List<Client> cc = new ArrayList<>();

        cc.add((new Client()).id("53280").lastname("Касаткин").firstname("Александр").patronymic("Максимович")
                .birthDate(202003200000L).passportSeries("4501").passportNumber("635084").inn("770159072206")
                .address("Москва, ул. , кв 14"));
        cc.add((new Client()).id("59114").lastname("Романов").firstname("Рустам").patronymic("Михайлович")
                .birthDate(371347200000L).passportSeries("4502").passportNumber("495882").inn("770286193973")
                .address("Москва, ул. , кв 6"));
        cc.add((new Client()).id("11027").lastname("Козлов").firstname("Станислав").patronymic("Маркович")
                .birthDate(523584000000L).passportSeries("4503").passportNumber("829868").inn("770333681964")
                .address("Москва, ул. , 21"));
        cc.add((new Client()).id("98904").lastname("Матвеева").firstname("Анна").patronymic("Павловна")
                .birthDate(-165628800000L).passportSeries("4605").passportNumber("158622").inn("500776896407")
                .address("Дмитров, ул. , кв 6"));
        cc.add((new Client()).id("70216").lastname("Козлов").firstname("Савелий").patronymic("Владимирович")
                .birthDate(371347200000L).passportSeries("4609").passportNumber("580023").inn("501050529345")
                .address("Дубна, ул. , 21"));
        cc.add((new Client()).id("19292").lastname("Свешников").firstname("Сергей").patronymic("Николаевич")
                .birthDate(-57196800000L).passportSeries("2811").passportNumber("682170").inn("690798497451")
                .address("Тверь, ул. , 21"));
        cc.add((new Client()).id("33434").lastname("Козлов").firstname("Алексей").patronymic("Дмитриевич")
                .birthDate(854582400000L).passportSeries("2821").passportNumber("327337").inn("690306329348")
                .address("Тверь, ул. , 21"));
        cc.add((new Client()).id("55420").lastname("Суханова").firstname("Карина").patronymic("Александровна")
                .birthDate(371347200000L).passportSeries("1712").passportNumber("823819").inn("332734492085")
                .address("Владимир, ул. , 21"));
        cc.add((new Client()).id("52433").lastname("Анисимова").firstname("Варвара").patronymic("Платоновна")
                .birthDate(772675200000L).passportSeries("1712").passportNumber("279721").inn("332703917514")
                .address("Владимир, ул. , 21"));

        return cc;
    }

    private List<Contact> generateContacts1() {
        List<Contact> cc = new ArrayList<>();

        cc.add((new Contact()).id("74914").clientId("73489").type(Contact.TypeEnum.PHONE).value("+79170041937"));
        cc.add((new Contact()).id("77615").clientId("73489").type(Contact.TypeEnum.EMAIL).value("mglitvinov@mail.ru"));
        cc.add((new Contact()).id("34028").clientId("80302").type(Contact.TypeEnum.PHONE).value("+79079256329"));
        cc.add((new Contact()).id("42906").clientId("80302").type(Contact.TypeEnum.PHONE).value("+79633505208"));
        cc.add((new Contact()).id("45152").clientId("80302").type(Contact.TypeEnum.EMAIL).value("anisimov.ad@mail.ru"));
        cc.add((new Contact()).id("24943").clientId("15828").type(Contact.TypeEnum.PHONE).value("+79171940597"));
        cc.add((new Contact()).id("53583").clientId("15828").type(Contact.TypeEnum.EMAIL).value("b.zaycev@yandex.ru"));
        cc.add((new Contact()).id("48762").clientId("68709").type(Contact.TypeEnum.PHONE).value("+79277362567"));
        cc.add((new Contact()).id("47542").clientId("68709").type(Contact.TypeEnum.EMAIL).value("fedot@list.ru"));
        cc.add((new Contact()).id("14174").clientId("59804").type(Contact.TypeEnum.PHONE).value("+79060318219"));
        cc.add((new Contact()).id("40294").clientId("59804").type(Contact.TypeEnum.EMAIL).value("isaev.pi@mail.ru"));
        cc.add((new Contact()).id("11744").clientId("76628").type(Contact.TypeEnum.PHONE).value("+79052854934"));
        cc.add((new Contact()).id("79174").clientId("76628").type(Contact.TypeEnum.EMAIL).value("danisimov@gmail.com"));
        cc.add((new Contact()).id("27243").clientId("41301").type(Contact.TypeEnum.PHONE).value("+79174747172"));
        cc.add((new Contact()).id("94748").clientId("41301").type(Contact.TypeEnum.EMAIL).value("belanna@mail.ru"));
        cc.add((new Contact()).id("35531").clientId("68025").type(Contact.TypeEnum.PHONE).value("+79178340326"));
        cc.add((new Contact()).id("29603").clientId("68025").type(Contact.TypeEnum.EMAIL).value("gusev.mm@yandex.ru"));
        cc.add((new Contact()).id("62592").clientId("21685").type(Contact.TypeEnum.PHONE).value("+79271344482"));
        cc.add((new Contact()).id("46919").clientId("17325").type(Contact.TypeEnum.PHONE).value("+79062947627"));
        cc.add((new Contact()).id("21065").clientId("17325").type(Contact.TypeEnum.EMAIL).value("oleg2000@mail.ru"));

        return cc;
    }

    private List<Contact> generateContacts2() {
        List<Contact> cc = new ArrayList<>();

        cc.add((new Contact()).id("91495").clientId("53280").type(Contact.TypeEnum.PHONE).value("+79177217042"));
        cc.add((new Contact()).id("66187").clientId("53280").type(Contact.TypeEnum.EMAIL).value("amkasatkin@mail.ru"));
        cc.add((new Contact()).id("53018").clientId("59114").type(Contact.TypeEnum.PHONE).value("+79633505208"));
        cc.add((new Contact()).id("31217").clientId("11027").type(Contact.TypeEnum.PHONE).value("+79271501840"));
        cc.add((new Contact()).id("67179").clientId("11027").type(Contact.TypeEnum.PHONE).value("+79170088581"));
        cc.add((new Contact()).id("47791").clientId("11027").type(Contact.TypeEnum.EMAIL).value("kozlov.sm@mail.ru"));
        cc.add((new Contact()).id("37859").clientId("98904").type(Contact.TypeEnum.PHONE).value("+79050081177"));
        cc.add((new Contact()).id("46852").clientId("98904").type(Contact.TypeEnum.EMAIL).value("amatveeva@mail.ru"));
        cc.add((new Contact()).id("74333").clientId("70216").type(Contact.TypeEnum.PHONE).value("+79068771623"));
        cc.add((new Contact()).id("61176").clientId("70216").type(Contact.TypeEnum.EMAIL).value("kozlovs@gmail.com"));
        cc.add((new Contact()).id("67527").clientId("19292").type(Contact.TypeEnum.PHONE).value("+79177498344"));
        cc.add((new Contact()).id("49324").clientId("19292").type(Contact.TypeEnum.EMAIL).value("sveshnik9@yandex.ru"));
        cc.add((new Contact()).id("94342").clientId("33434").type(Contact.TypeEnum.PHONE).value("+79626636324"));
        cc.add((new Contact()).id("49262").clientId("33434").type(Contact.TypeEnum.EMAIL).value("adkozlov@mail.ru"));
        cc.add((new Contact()).id("25284").clientId("55420").type(Contact.TypeEnum.PHONE).value("+79270375184"));
        cc.add((new Contact()).id("16272").clientId("55420").type(Contact.TypeEnum.EMAIL).value("such.ka@gmail.com"));
        cc.add((new Contact()).id("92057").clientId("52433").type(Contact.TypeEnum.PHONE).value("+79175755339"));
        cc.add((new Contact()).id("27311").clientId("52433").type(Contact.TypeEnum.EMAIL).value("vanisimova@list.ru"));

        return cc;
    }

    private List<Account> generateAccounts1() {
        List<Account> aa = new ArrayList<>();

        aa.add((new Account()).number("40817810220135711049").clientId("74914").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.CLOSED).openDate(1610668800000L).closeDate(1623196800000L)); // "15.01.2021", "09.06.2021"
        aa.add((new Account()).number("40817810110167668404").clientId("74914").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1628640000000L)); // "11.08.2021"
        aa.add((new Account()).number("40817810670114037905").clientId("80302").type(Account.TypeEnum.OVERDRAFT)
                .status(Account.StatusEnum.ACTIVE).openDate(1621209600000L).deferment(15)); // "17.05.2021
        aa.add((new Account()).number("40817810200159961136").clientId("80302").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1625616000000L)); // "07.07.2021"
        aa.add((new Account()).number("40817810890149510582").clientId("15828").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.INACTIVE).openDate(1617580800000L)); // "05.04.2021"
        aa.add((new Account()).number("40817810840136282470").clientId("68709").type(Account.TypeEnum.TRANSIT)
                .status(Account.StatusEnum.ACTIVE).openDate(1632873600000L)); // "29.09.2021"
        aa.add((new Account()).number("40817810210178044957").clientId("59804").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1615766400000L)); // "15.03.2021"
        aa.add((new Account()).number("40817810210146304910").clientId("76628").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.CLOSED).openDate(1612742400000L).closeDate(1626134400000L)); // "08.02.2021", "13.07.2021"
        aa.add((new Account()).number("40817810730146974912").clientId("76628").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1623110400000L)); // "08.06.2021"
        aa.add((new Account()).number("40817810730125589569").clientId("41301").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.LOCKED).openDate(1633046400000L)); // "01.10.2021"
        aa.add((new Account()).number("40817810140045043101").clientId("68025").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1630022400000L)); // "27.08.2021"
        aa.add((new Account()).number("40817810740045024659").clientId("68025").type(Account.TypeEnum.OVERDRAFT)
                .status(Account.StatusEnum.ACTIVE).openDate(1626220800000L).deferment(20)); // "14.07.2021"
        aa.add((new Account()).number("40817810210146959762").clientId("68025").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1611100800000L)); // "20.01.2021"
        aa.add((new Account()).number("40817810080164847504").clientId("21685").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.INACTIVE).openDate(1635379200000L)); // "28.10.2021"
        aa.add((new Account()).number("40817810490164702182").clientId("17325").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1621814400000L)); // "24.05.2021"

        return aa;
    }

    private List<Account> generateAccounts2() {
        List<Account> aa = new ArrayList<>();

        aa.add((new Account()).number("40817810640135214330").clientId("53280").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.CLOSED).openDate(1613347200000L).closeDate(1624233600000L)); // "15.02.2021", "21.06.2021"
        aa.add((new Account()).number("40817810270135207620").clientId("53280").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1615766400000L)); // "15.03.2021"
        aa.add((new Account()).number("40817810710167584522").clientId("59114").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1618185600000L)); // "12.04.2021"
        aa.add((new Account()).number("40817810460167464720").clientId("59114").type(Account.TypeEnum.OVERDRAFT)
                .status(Account.StatusEnum.ACTIVE).openDate(1623283200000L).deferment(20)); // "10.06.2021"
        aa.add((new Account()).number("40817810400110013872").clientId("11027").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1621209600000L)); // "17.05.2021"
        aa.add((new Account()).number("40817810330110112985").clientId("11027").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1612742400000L)); // "02.08.2021"
        aa.add((new Account()).number("40817810490159321631").clientId("98904").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.INACTIVE).openDate(1625616000000L)); // "07.07.2021"
        aa.add((new Account()).number("40817810110149787014").clientId("70216").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1617580800000L)); // "05.04.2021"
        aa.add((new Account()).number("40817810370149415250").clientId("70216").type(Account.TypeEnum.OVERDRAFT)
                .status(Account.StatusEnum.ACTIVE).openDate(1627430400000L).deferment(15)); // "28.07.2021"
        aa.add((new Account()).number("40817810240136429733").clientId("19292").type(Account.TypeEnum.TRANSIT)
                .status(Account.StatusEnum.ACTIVE).openDate(1632873600000L)); // "29.09.2021"
        aa.add((new Account()).number("40817810890159602046").clientId("33434").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.ACTIVE).openDate(1625616000000L)); // "07.07.2021"
        aa.add((new Account()).number("40817810820149037470").clientId("55420").type(Account.TypeEnum.PAYMENT)
                .status(Account.StatusEnum.INACTIVE).openDate(1625529600000L)); // "07.06.2021"
        aa.add((new Account()).number("40817810990136343420").clientId("52433").type(Account.TypeEnum.TRANSIT)
                .status(Account.StatusEnum.CLOSED).openDate(1611878400000L).closeDate(1634515200000L)); // "29.01.2021", "18.10.2021"

        return aa;
    }

    private List<Operation> generateOperations1() {
        List<Operation> oo = new ArrayList<>();

        //oo.add((new Operation()).type(Operation.TypeEnum.EXPENSE).accountNumber("40817810220135711049")
        //        .operDate("").amount().description(""));


        return oo;
    }
}
