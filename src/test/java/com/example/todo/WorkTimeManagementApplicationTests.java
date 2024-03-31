package com.example.todo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;

import com.example.todo.controller.WorkTimeManagementController;

@SpringBootTest
class WorkTimeManagementApplicationTests {

	@Test
	void testCreatePage() {
		// Create instance of your controller
        WorkTimeManagementController controller = new WorkTimeManagementController();

        // Create mock objects for Model and HttpSession
        Model model = mock(Model.class);
        MockHttpSession session = new MockHttpSession();

        // Call the method to be tested
        String result = controller.displaycreatepage(model, session);

        // Verify that the method returns the expected view name
        assertEquals("createAccount", result);

	}
	
	@Test 
	void testLogout() {
		// Create instance of your controller
		WorkTimeManagementController controller = new WorkTimeManagementController();

        // Create mock objects for Model and HttpSession
        Model model = mock(Model.class);
        MockHttpSession session = new MockHttpSession();

        // Call the method to be tested
        String result = controller.displayhome(model, session);

        // Verify that the method returns the expected view name
        assertEquals("home", result);

        // Verify that the session is invalidated
        assertTrue(session.isInvalid());
	}

}
