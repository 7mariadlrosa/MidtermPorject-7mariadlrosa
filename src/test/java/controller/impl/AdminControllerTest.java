package controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import classes.Address;
import classes.Money;
import controller.dto.accounts.MoneyDto;
import controller.dto.users.AccountHolderDto;
import controller.dto.users.AdminDto;
import controller.dto.users.ThirdPartyDto;
import enums.Role;
import model.*;
import repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private UserRepository userRepository;


    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        Address address1=new Address("Calle del olvido, 45. Madrid");
        Address address2=new Address("Calle del perdon, 27. Cuenca");
        Address address3=new Address("Calle del martirio, 23. Albacete");

        AccountHolder owner1= new AccountHolder("Pepe Botijo", "elbotijero", "botijo", LocalDate.of(1960, 10, 5), address1);
        AccountHolder owner2= new AccountHolder("Fernando Rado", "rader", "radero", LocalDate.of(2002, 10, 5), address3);
        AccountHolder owner3= new AccountHolder("Rosa Melano", "melaner", "melania", LocalDate.of(1999, 10, 5), address2);

        accountHolderRepository.saveAll(List.of(owner1, owner2, owner3));

        rolesRepository.save(new Roles(Role.ACCOUNT_HOLDER, owner1));
        rolesRepository.save(new Roles(Role.ACCOUNT_HOLDER, owner2));
        rolesRepository.save(new Roles(Role.ACCOUNT_HOLDER, owner3));

        Admin admin1= new Admin("Emilio Botín", "botin", "santander");
        adminRepository.save(admin1);
        rolesRepository.save(new Roles(Role.ADMIN, admin1));

        Checking checking1 = new Checking(owner1, new Money(new BigDecimal("15000")), "tengohambre");
        StudentChecking studentChecking1 = new StudentChecking(owner2, new Money(new BigDecimal("1000")), "estudiante");
        Savings savings1 = new Savings(owner1, owner2, new Money(new BigDecimal("1000")), "perro");
        CreditCard creditCard1 = new CreditCard(owner1, new Money(new BigDecimal("9000")));

        checkingRepository.save(checking1);
        studentCheckingRepository.save(studentChecking1);
        savingsRepository.save(savings1);
        creditCardRepository.save(creditCard1);

    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        savingsRepository.deleteAll();
        creditCardRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        adminRepository.deleteAll();
        rolesRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createAdmin_CorrectDtoAdminLogged_CreateAdmin() throws Exception {
        AdminDto adminDto = new AdminDto();
        adminDto.setName("santander");
        adminDto.setUsername("santander2021");
        adminDto.setPassword("holaquease");
        String body = objectMapper.writeValueAsString(adminDto);
        MvcResult result = mockMvc.perform(
                post("/admin/create-new-admin")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("botin")
                                .password("santander")
                                .roles("ADMIN"))
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("santander2021"));
    }

    @Test
    void createThirdParty_CorrectDtoAdminLogged_CreateThirdParty() throws Exception {
        ThirdPartyDto thirdPartyDto = new ThirdPartyDto();
        thirdPartyDto.setName("clientedelBBVA");
        thirdPartyDto.setHashKey("bebeuvea");
        String body = objectMapper.writeValueAsString(thirdPartyDto);
        MvcResult result = mockMvc.perform(
                post("/admin/create-third-party")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("botin")
                                .password("santander")
                                .roles("ADMIN"))
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("clientedelBBVA"));
    }

    @Test
    void modifyBalance_OfCheckingAdminLogged_BalanceModified() throws Exception {
        MoneyDto moneyDto = new MoneyDto(new BigDecimal("15000"));
        Long accountId = checkingRepository.findAll().get(0).getId();
        String body = objectMapper.writeValueAsString(moneyDto);
        MvcResult result = mockMvc.perform(
                patch("/admin/modify-balance/"+accountId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("botin")
                                .password("santander")
                                .roles("ADMIN"))
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("15000"));
    }

    @Test
    void modifyBalance_OfStudentCheckAdminLogged_BalanceModified() throws Exception {
        MoneyDto moneyDto = new MoneyDto(new BigDecimal("15000"));
        Long accountId = studentCheckingRepository.findAll().get(0).getId();
        String body = objectMapper.writeValueAsString(moneyDto);
        MvcResult result = mockMvc.perform(
                patch("/admin/modify-balance/"+accountId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("botin")
                                .password("santander")
                                .roles("ADMIN"))
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("15000"));
    }

    @Test
    void modifyBalance_OfSavingsAdminLogged_BalanceModified() throws Exception {
        MoneyDto moneyDto = new MoneyDto(new BigDecimal("15000"));
        Long accountId = savingsRepository.findAll().get(0).getId();
        String body = objectMapper.writeValueAsString(moneyDto);
        MvcResult result = mockMvc.perform(
                patch("/admin/modify-balance/"+accountId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("botin")
                                .password("santander")
                                .roles("ADMIN"))
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("15000"));
    }

}