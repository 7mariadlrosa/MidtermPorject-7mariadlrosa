package controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import classes.Address;
import classes.Money;
import controller.dto.accounts.OperationDto;
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

import static enums.Role.ACCOUNT_HOLDER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingRepository checkingAccRepository;
    @Autowired
    private SavingsRepository savingsAccRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingAccRepository;
    @Autowired
    private CreditCardRepository creditCardAccRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private OperationRepository operationRepository;


    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        Address address1=new Address("C/Doctor Esquerdo, 169, Madrid");
        Address address2=new Address("C/Marianista Cubillo, 1. Cádiz");
        Address address3=new Address("C/Desengaño, 23. Barcelona");

        AccountHolder owner1= new AccountHolder("María Fdez", "mariadlrosa","avemaria",LocalDate.of(1995, 10, 4), address1);
        AccountHolder owner2= new AccountHolder("Jaime de la Rosa", "jimmyjr", "radero", LocalDate.of(1993, 06, 13), address3);
        AccountHolder owner3= new AccountHolder("Alvaro Benito", "didi", "melania", LocalDate.of(1993, 12, 21), address2);

        accountHolderRepository.saveAll(List.of(owner1, owner2, owner3));

        rolesRepository.save(new Roles(ACCOUNT_HOLDER, owner1));
        rolesRepository.save(new Roles(ACCOUNT_HOLDER, owner2));
        rolesRepository.save(new Roles(ACCOUNT_HOLDER, owner3));

        Admin admin1= new Admin("Laura de la Rosa", "mami", "voyyloencuentro");
        adminRepository.save(admin1);
        rolesRepository.save(new Roles(Role.ADMIN, admin1));

        ThirdParty thirdParty1= new ThirdParty("Bad Bunny", "conejomalo");
        thirdPartyRepository.save(thirdParty1);

        Checking checking1 = new Checking(owner1, new Money(new BigDecimal("15000")), "holiholi");
        StudentChecking studentChecking1 = new StudentChecking(owner2, new Money(new BigDecimal("1000")), "cositas");
        Savings savings1 = new Savings(owner1, owner2, new Money(new BigDecimal("1000")), "jijijaja");
        savings1.setLastInterestUpdate(LocalDate.of(2019, 1, 10));
        CreditCard creditCard1 = new CreditCard(owner3, new Money(new BigDecimal("9000")));
        creditCard1.setLastInterestUpdate(LocalDate.of(2021, 1, 10));

        //NO ENTIENDO POR QUÉ DA FALLO
        checkingRepository.save(checking1);
        studentCheckingRepository.save(studentChecking1);
        savingsRepository.save(savings1);
        creditCardRepository.save(creditCard1);
    }

    @AfterEach
    void tearDown() {
        operationRepository.deleteAll();
        checkingAccRepository.deleteAll();
        studentCheckingAccRepository.deleteAll();
        savingsAccRepository.deleteAll();
        creditCardAccRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        adminRepository.deleteAll();
        rolesRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void checkBalance_validOwner_showBalance() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        MvcResult result = mockMvc.perform(
                get("/check-balance/"+accounts.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("mariadlrosa")
                                .password("avemaria")
                                .roles("ACCOUNT_HOLDER"))
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("15000"));
    }

    @Test
    public void checkBalance_Admin_showBalance() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        MvcResult result = mockMvc.perform(
                get("/check-balance/"+accounts.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("mami")
                                .password("voyyloencuentro")
                                .roles("ADMIN"))
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("15000"));
    }

    @Test
    public void transfer_correctDtoCorrectOwner_transferOk() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        OperationDto operationDto = new OperationDto();
        operationDto.setOriginAccountId(accounts.get(0).getId());
        operationDto.setDestinationAccountId(accounts.get(1).getId());
        operationDto.setAmount(new BigDecimal("1000"));
        operationDto.setName("Jaime de la Rosa");
        String body = objectMapper.writeValueAsString(operationDto);
        MvcResult result = mockMvc.perform(
                patch("/transfer")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("mariadlrosa")
                                .password("avemaria")
                                .roles("ACCOUNT_HOLDER"))
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("1000"));
    }

}