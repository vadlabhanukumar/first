package com.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.exception.HomeLoanNotFoundException;
import com.app.exception.UsernameNotFoundException;
import com.app.model.LoanManagementDetails;
import com.app.model.LoanTrackerResponse;
import com.app.model.User;
import com.app.service.LoanManagementService;


@ExtendWith(MockitoExtension.class)
public class LoanManagementControllerTest {

    @Mock
    private LoanManagementService loanManagementService;

    @InjectMocks
    private LoanManagementController loanManagementController;
    
    public LoanManagementControllerTest() {
        this.loanManagementController = new LoanManagementController();
        this.loanManagementService = mock(LoanManagementService.class);
        
    }


    @Test
    public void testGetUserByUserName() throws UsernameNotFoundException {
      
        String userName = "testUser";
        User mockUser = new User();
        mockUser.setUserName(userName);
        when(loanManagementService.getUserDetails(userName)).thenReturn(mockUser);

     
        ResponseEntity<User> response = loanManagementController.getUserByUserName(userName);

   
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getUserName().equals(userName);
    }

    

   

    @Test
    public void testGetLoanTracker() throws UsernameNotFoundException {
       
        String userName = "testUser";
        String password = "testPassword";
        LoanTrackerResponse mockResponse = new LoanTrackerResponse();
        when(loanManagementService.getLoanTracker(userName, password)).thenReturn(mockResponse);

        
        ResponseEntity<LoanTrackerResponse> response = loanManagementController.getLoanTracker(userName, password);

        
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().equals(mockResponse);
    }
    
   
    @Test
    public void testApplyForHomeLoan() throws HomeLoanNotFoundException {
       
        LoanManagementDetails homeLoan = new LoanManagementDetails();
        int expectedLoanNumber = 123;
        when(loanManagementService.applyForHomeLoan(homeLoan)).thenReturn(expectedLoanNumber);

        int result = loanManagementController.applyForHomeLoan(homeLoan);

        assertEquals(expectedLoanNumber, result);
        
    }
       
    @Test
    public void testGetDetails_UserFound() throws UsernameNotFoundException {
       
        String userName = "admin";
        String password = "adminPassword";
        List<LoanManagementDetails> mockLoanList = Arrays.asList(new LoanManagementDetails(), new LoanManagementDetails());
        when(loanManagementService.getDetails(userName, password)).thenReturn(mockLoanList);
        List<LoanManagementDetails> result = loanManagementController.getDetails(userName, password);
        assertNotNull(result);
        assertEquals(mockLoanList, result);
  
    }

  
    
    @Test
    public void testGetUserDetails_UserFound() throws UsernameNotFoundException {
        
        String userName = "testUser";
        String password = "testPassword";
        LoanManagementDetails mockDetails = new LoanManagementDetails();
        when(loanManagementService.getUserDetails(userName, password)).thenReturn(mockDetails);
        LoanManagementDetails result = loanManagementController.getUserDetails(userName, password);
        assertNotNull(result);
        assertEquals(mockDetails, result);
        
    }

    @Test
    public void testGetUserDetails_UserNotFound() throws UsernameNotFoundException {
        
        String userName = "nonexistentUser";
        String password = "somePassword";
        when(loanManagementService.getUserDetails(userName, password)).thenReturn(null);
        LoanManagementDetails result = loanManagementController.getUserDetails(userName, password);
        assertNull(result);
        
    }
    
    @Test
    public void testUpdateMontlyPayment_Success() throws HomeLoanNotFoundException {
       
        Double monthlyPayment = 1000.0;
        int loanNumber = 123;
        LoanManagementDetails mockDetails = new LoanManagementDetails();
        when(loanManagementService.updateMontlyPayment(monthlyPayment, loanNumber)).thenReturn(mockDetails);
        LoanManagementDetails result = loanManagementController.updateMontlyPayment(monthlyPayment, loanNumber);
        assertNotNull(result);
        assertEquals(mockDetails, result);
        
    }

    @Test
    public void testUpdateMontlyPayment_Failure() throws HomeLoanNotFoundException {
        
        Double monthlyPayment = 1000.0;
        int loanNumber = 123;
        when(loanManagementService.updateMontlyPayment(monthlyPayment, loanNumber)).thenReturn(null);
        LoanManagementDetails result = loanManagementController.updateMontlyPayment(monthlyPayment, loanNumber);
        assertNull(result);
       
    }
    
   
   
}
