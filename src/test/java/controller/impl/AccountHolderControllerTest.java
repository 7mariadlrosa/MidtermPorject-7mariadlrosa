package controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.dto.users.AccountHolderDto;
import enums.Role;
import model.Admin;
import model.Roles;

import repository.AccountRepository;
import repository.AdminRepository;
import repository.RolesRepository;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private AdminRepository adminRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        Admin admin1= new Admin("Laura de la Rosa", "mami", "voyyloenceuntro");
        adminRepository.save(admin1);
        rolesRepository.save(new Roles(Role.ADMIN, admin1));
    }

    @AfterEach
    void tearDown() {
        rolesRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    void create_ValidDtoWithAdminLogged_newAccHolder() throws Exception {
        AccountHolderDto accountHolderDTO = new AccountHolderDto();
        accountHolderDTO.setName("Ignacio");
        accountHolderDTO.setUsername("ifmarente");
        accountHolderDTO.setPassword("papi");
        accountHolderDTO.setDateOfBirth(LocalDate.of(1964,02,14));
        accountHolderDTO.setPrimaryAddress("C/Marianista Cubillo, 3.");
        accountHolderDTO.setMailingAddress("C/Desengaño, 5.");
        String body = objectMapper.writeValueAsString(accountHolderDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/admin/create-account-holder")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("mami")
                                .password("voyyloencuentro")
                                .roles("ADMIN"))
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ignacio"));
    }
}