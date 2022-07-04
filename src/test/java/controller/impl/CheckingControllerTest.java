package controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import classes.Address;
import classes.Money;
import controller.dto.accounts.CheckingDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CheckingControllerTest {

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
    private Object checkingDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        Address address1=new Address("Calle del olvido, 45. Madrid");
        Address address2=new Address("Calle del perdon, 27. Cuenca");
        Address address3=new Address("Calle del martirio, 23. Albacete");

        AccountHolder owner1= new AccountHolder("Fernando Rado", "rader", "radero", LocalDate.of(2006, 10, 5), address3);
        AccountHolder owner2= new AccountHolder("Pepe Botijo", "elbotijero", "botijo", LocalDate.of(1960, 10, 5), address1);

        accountHolderRepository.saveAll(List.of(owner1, owner2));

        rolesRepository.save(new Roles(Role.ACCOUNT_HOLDER, owner1));
        rolesRepository.save(new Roles(Role.ACCOUNT_HOLDER, owner2));

        Admin admin1= new Admin("Emilio Botín", "botin", "santander");
        adminRepository.save(admin1);
        rolesRepository.save(new Roles(Role.ADMIN, admin1));

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
    void create_olderThan24ownerLoggedAdmin_checkAccountCreated() throws Exception {
        CheckingDto checkingAccDto=new CheckingDto();
        checkingAccDto.setPrimaryOwnerId(accountHolderRepository.findAll().get(1).getId());
        checkingAccDto.setBalance(new BigDecimal("1200"));
        checkingAccDto.setSecretKey("password");
        String body = objectMapper.writeValueAsString(checkingDto);
        MvcResult result = mockMvc.perform(
                post("/admin/create-checkAcc")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("botin")
                                .password("santander")
                                .roles("ADMIN"))
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("1200"));
    }

}