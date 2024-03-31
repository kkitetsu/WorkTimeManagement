package com.example.todo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.todo.controller.WorkTimeManagementController;
import com.example.todo.entity.EmployeesEntity;
import com.example.todo.form.LoginRequest;
import com.example.todo.service.EmployeesInfoService;

@WebMvcTest(WorkTimeManagementController.class)
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeesInfoService employeesInfoService; // Mock the service layer

    @Test
    public void testUserLogin_Success() throws Exception {
        // Mock login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin_id(1);
        loginRequest.setLogin_pw("password");

        // Mock service layer response
        EmployeesEntity employee = new EmployeesEntity();
        employee.setFirstname("John");
        when(employeesInfoService.login(any(LoginRequest.class))).thenReturn(Collections.singletonList(employee));

        // Perform HTTP POST request to /login endpoint
        mockMvc.perform(post("/login")
               .param("login_id", "1")
               .param("login_pw", "password"))
               .andExpect(status().isOk())
               .andExpect(view().name("userMyPage"))
               .andExpect(model().attribute("userName", "John"));
        
        // Verify interactions
        verify(employeesInfoService).login(any(LoginRequest.class));
    }

    // Add more integration tests for other scenarios as needed
}
