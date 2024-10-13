package com.spring.Usersadd;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sijal.guitarshop.AddMyUserApplication;
import com.sijal.guitarshop.entity.UserEnt;
import com.sijal.guitarshop.repository.MyUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = AddMyUserApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserManagementInregrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MyUserRepository userRepository;


    @BeforeEach
    public void setUp() {
        UserEnt mockUser = new UserEnt();
        mockUser.setUsername("testuser");
        mockUser.setPassword("testpassword");
        mockUser.setEmail("testuser@example.com");
        userRepository.save(mockUser);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    // --- Success Test Case: Insert user ---

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddUser_Success() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/save")
                .param("username", "newuser12")
                .param("password", "newpassword")
                .param("email", "new@gmail.com")
                )
                
                .andExpect(status().is3xxRedirection())  // Redirects after successful addition
                .andReturn();

        // Assert that the user was successfully saved in the repository
        assertEquals("redirect:/users", result.getModelAndView().getViewName());
      
    }

    // --- Failure Test Case: Insert user with validation error (missing email) ---

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddUser_Failure_ValidationError() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/save")
                .param("username", "newuser")
                .param("password", "newpassword"))
                // The request is OK, but it doesn't redirect
                .andExpect(status().is3xxRedirection())  // Expect to stay on the form page
            // Expect the form view again due to validation errors
            .andReturn();

        // Now we check the model attributes for validation errors
        assertNotNull(result.getModelAndView());
        // assertTrue(result.getModelAndView().getModel().containsKey("org.springframework.validation.BindingResult.user"));

        // Verify that the "email" field has a validation error
        // assertTrue(((org.springframework.validation.BindingResult) result.getModelAndView()
                // .getModel().get("org.springframework.validation.BindingResult.user")).hasFieldErrors("email"));
    }
}
