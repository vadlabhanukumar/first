package com.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import com.app.exception.HomeLoanNotFoundException;
import com.app.exception.UsernameNotFoundException;
import com.app.model.LoanManagementDetails;
import com.app.model.LoanTrackerResponse;
import com.app.model.User;
import com.app.repository.LoanManagementDetailsRepository;
import com.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class LoanManagementServiceImplTest {

	@Mock
	private LoanManagementDetailsRepository loanManagementDetailsRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private LoanManagementServiceImpl loanManagementService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private static final Logger logger = LoggerFactory.getLogger(LoanManagementServiceImplTest.class);

	public LoanManagementServiceImplTest() {
		this.loanManagementService = new LoanManagementServiceImpl();
		this.loanManagementDetailsRepository = mock(LoanManagementDetailsRepository.class);
		this.userRepository = mock(UserRepository.class);
	}

	@Test
	public void testGetUserDetails() throws UsernameNotFoundException {

		String userName = "testUser";
		User mockUser = new User();
		when(userRepository.findByuserName(userName)).thenReturn(Optional.of(mockUser));

		User result = loanManagementService.getUserDetails(userName);

		assert result != null;
		assert result.equals(mockUser);
	}

	@Test
	public void testApplyForHomeLoan() throws HomeLoanNotFoundException {

		LoanManagementDetails loanDetails = new LoanManagementDetails();
		loanDetails.setHomeLoanNumber(1);
		loanDetails.setCurrentRateofInterest(2.00);
		loanDetails.setLoanTenure(2);
		loanDetails.setLoanType("construction");
		loanDetails.setMontlyPayment(560000.00);
		loanDetails.setNomineeDetails("naresh");
		loanDetails.setOutstandingEMIcount(10000000.00);
		loanDetails.setPrincipalOutstandingAmount(1000000.00);
		loanDetails.setCreationDate(LocalDate.now());
		loanDetails.setTotalLoanamount(10000000.00);
		User userObj = new User();
		userObj.setId(1);
		userObj.setAddress("HYD");
		userObj.setAge(24);
		userObj.setMobileNumber("7036463761");
		userObj.setPassword("Siva@123");
		userObj.setRole("user");
		userObj.setSalary(1000.00);
		userObj.setUserName("saiduin");
		loanDetails.setUser(userObj);
		int loanNumber = 0;

		ReflectionTestUtils.setField(loanManagementService, "loanManagementDetailsRepository",
				loanManagementDetailsRepository);

		int result = loanManagementService.applyForHomeLoan(loanDetails);

		assert result > 0;
	}

	@Test
	public void testGetLoanTracker() throws UsernameNotFoundException {
		// Arrange
		String userName = "testUser";
		String password = "testPassword";
		User mockUser = new User();
		mockUser.setUserName(userName);
		mockUser.setPassword(password);
		LocalDate creationDate = LocalDate.now().minusDays(5);
		LoanManagementDetails mockLoanDetails = new LoanManagementDetails();
		mockLoanDetails.setUser(mockUser);
		mockLoanDetails.setCreationDate(creationDate);
		List<LoanManagementDetails> loanDetailsList = new ArrayList<>();
		loanDetailsList.add(mockLoanDetails);

		when(userRepository.findByuserName(userName)).thenReturn(Optional.of(mockUser));
		when(loanManagementDetailsRepository.findAll()).thenReturn(loanDetailsList);

		LoanTrackerResponse result = loanManagementService.getLoanTracker(userName, password);

		assert result != null;

	}

	@Test
	public void testGetUserDetailsWithPassword() throws UsernameNotFoundException {
		// Arrange
		String userName = "testUser";
		String password = "testPassword";
		User mockUser = new User();
		mockUser.setUserName(userName);
		mockUser.setPassword(password);
		LoanManagementDetails mockLoanDetails = new LoanManagementDetails();
		mockLoanDetails.setUser(mockUser);
		List<LoanManagementDetails> loanDetailsList = new ArrayList<>();
		loanDetailsList.add(mockLoanDetails);

		when(userRepository.findByuserName(userName)).thenReturn(Optional.of(mockUser));
		when(loanManagementDetailsRepository.findAll()).thenReturn(loanDetailsList);

		LoanManagementDetails result = loanManagementService.getUserDetails(userName, password);

		assert result != null;

	}

	@Test
	public void testUpdateMonthlyPayment() throws HomeLoanNotFoundException {

		int loanNumber = 123;
		Double monthlyPayment = 1000.0;
		LoanManagementDetails existingLoan = new LoanManagementDetails();
		existingLoan.setUser(new User());
		when(loanManagementDetailsRepository.findById(loanNumber)).thenReturn(Optional.of(existingLoan));
		LoanManagementDetails result = loanManagementService.updateMontlyPayment(monthlyPayment, loanNumber);
		assertNotNull(result);

	}

	@Test
	public void testGetUserDetails1() throws UsernameNotFoundException {
		// Arrange
		String userName = "exampleUser";
		String password = "examplePassword";

		// Mock the behavior of the userRepository
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		when(userRepository.findByuserName(userName)).thenReturn(Optional.of(user));
		LoanManagementDetails loanManagementDetails = new LoanManagementDetails();
		loanManagementDetails.setUser(user);
		List<LoanManagementDetails> loanList = new ArrayList<>();
		loanList.add(loanManagementDetails);
		when(loanManagementDetailsRepository.findAll()).thenReturn(loanList);
		LoanManagementDetails result = loanManagementService.getUserDetails(userName, password);
		assertNotNull(result);
		assertEquals(user, result.getUser());

	}

	@Test
	public void testGetLoanTracker1() throws UsernameNotFoundException {

		String userName = "exampleUser";
		String password = "examplePassword";
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		when(userRepository.findByuserName(userName)).thenReturn(Optional.of(user));
		LoanManagementDetails loanManagementDetails = new LoanManagementDetails();
		loanManagementDetails.setUser(user);
		loanManagementDetails.setCreationDate(LocalDate.now()); // Set a creation date for testing
		List<LoanManagementDetails> loanList = new ArrayList<>();
		loanList.add(loanManagementDetails);
		when(loanManagementDetailsRepository.findAll()).thenReturn(loanList);
		LoanTrackerResponse result = loanManagementService.getLoanTracker(userName, password);
		assertNotNull(result);
		assertNotNull(result.getLoanManagementDetails());
		assertNotNull(result.getStatus());

	}

	
	@Test
	public void testGetDetails_Admin() throws UsernameNotFoundException {

		String userOrAdmin = "admin";
		String password = "adminPassword";
		User adminUser = new User();
		adminUser.setRole("admin");
		adminUser.setPassword(password);
		when(userRepository.findByuserName(userOrAdmin)).thenReturn(Optional.of(adminUser));
		List<LoanManagementDetails> loanDetailsList = new ArrayList<>();
		when(loanManagementDetailsRepository.findAll()).thenReturn(loanDetailsList);
		List<LoanManagementDetails> result = loanManagementService.getDetails(userOrAdmin, password);
		assertNotNull(result);
		assertEquals(loanDetailsList, result);

	}

	@Test
	public void testGetDetails_User() throws UsernameNotFoundException {

		String userOrAdmin = "regularUser";
		String password = "userPassword";
		User regularUser = new User();
		regularUser.setRole("user");
		regularUser.setPassword(password);
		when(userRepository.findByuserName(userOrAdmin)).thenReturn(Optional.of(regularUser));
		List<LoanManagementDetails> result = loanManagementService.getDetails(userOrAdmin, password);
		assertNotNull(result);

	}

	@Test
	public void testGetAllLoanDetails() throws HomeLoanNotFoundException {

		List<LoanManagementDetails> mockLoanDetails = Arrays.asList(new LoanManagementDetails(),
				new LoanManagementDetails());
		when(loanManagementDetailsRepository.findAll()).thenReturn(mockLoanDetails);
		List<LoanManagementDetails> result = loanManagementService.getAllLoanDetails();
		assertNotNull(result);
		assertEquals(mockLoanDetails, result);

	}

	@Test
	public void testRegisterForLoan() {

		User mockUser = new User();
		mockUser.setId(123);
		when(userRepository.save(any(User.class))).thenReturn(mockUser);

		int result = loanManagementService.registerForLoan(mockUser);

		assertEquals(mockUser.getId(), result);

	}

	@Test
	public void testGetUserDetailsWithException() {

		String userName = "testUser";
		Mockito.when(userRepository.findByuserName(userName)).thenThrow(new RuntimeException("exception"));
		assertThrows(UsernameNotFoundException.class, () -> {
			loanManagementService.getUserDetails(userName);
		});
	}

	@Test
	public void testapplyForHomeLoanForEligibleUser() throws HomeLoanNotFoundException {

		LoanManagementDetails homeLoan = createEligibleHomeLoan();
		when(loanManagementDetailsRepository.save(any(LoanManagementDetails.class))).thenReturn(homeLoan);
		int value = loanManagementService.applyForHomeLoan(homeLoan);
		assertNotNull(value);
	}

	@Test
	public void testapplyForHomeLoanForIneligibleUser() throws HomeLoanNotFoundException {

		LoanManagementDetails homeLoan = createIneligibleHomeLoan();
		when(loanManagementDetailsRepository.save(any(LoanManagementDetails.class))).thenReturn(homeLoan);
		int value = loanManagementService.applyForHomeLoan(homeLoan);
		assertNotNull(value);

	}

	
	@Test
	public void testApplyForHomeLoanWithException() {
	    LoanManagementDetails homeLoan = createHomeLoanWithException();

	    assertThrows(HomeLoanNotFoundException.class, () -> {
	        loanManagementService.applyForHomeLoan(homeLoan);
	    });
	}
	  
	 
	private LoanManagementDetails createEligibleHomeLoan() {
		LoanManagementDetails homeLoan = new LoanManagementDetails();
		User user = new User();
		user.setSalary(25000000.00);
		user.setAge(30);
		homeLoan.setUser(user);
		homeLoan.setTotalLoanamount(1000000.00);
		homeLoan.setLoanTenure(12);
		return homeLoan;
	}

	private LoanManagementDetails createIneligibleHomeLoan() {
		LoanManagementDetails homeLoan = new LoanManagementDetails();
		User user = new User();
		user.setSalary(20000000.00);
		user.setAge(40);
		homeLoan.setUser(user);
		homeLoan.setTotalLoanamount(1000000.00);
		homeLoan.setLoanTenure(12);
		return homeLoan;
	}

	private LoanManagementDetails createHomeLoanWithException() {
		LoanManagementDetails homeLoan = new LoanManagementDetails();
		User user = new User();
		homeLoan.setUser(user);
		return homeLoan;
	}
	
	@Test
    public void testUpdateMontlyPayment() throws HomeLoanNotFoundException {
       
        int loanNumber = 123;
        Double monthlyPayment = 5000.0; 
        LoanManagementDetails existingLoan = createMockLoan(); 
        when(loanManagementDetailsRepository.findById(loanNumber)).thenReturn(Optional.of(existingLoan));
        LoanManagementDetails result = loanManagementService.updateMontlyPayment(monthlyPayment, loanNumber);
        assertNotNull(result);
        
    }

    @Test
    public void testUpdateMontlyPaymentWithException() {
       
        int loanNumber = 123;
        Double monthlyPayment = 5000.0;
        when(loanManagementDetailsRepository.findById(loanNumber)).thenReturn(Optional.empty());
        assertThrows(HomeLoanNotFoundException.class, () -> {
        	loanManagementService.updateMontlyPayment(monthlyPayment, loanNumber);
        });

       
    }
    
    private LoanManagementDetails createMockLoan() {
        LoanManagementDetails mockLoan = mock(LoanManagementDetails.class);


        when(mockLoan.getHomeLoanNumber()).thenReturn(1);
        when(mockLoan.getCurrentRateofInterest()).thenReturn(1.5);
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(101);
        when(mockUser.getUserName()).thenReturn("mockUser");
        when(mockLoan.getUser()).thenReturn(mockUser);

        return mockLoan;
    }
    
    @Test
    public void testGetAllLoanDetailsWithException() {
        
        when(loanManagementDetailsRepository.findAll()).thenThrow(new RuntimeException("Simulated exception"));
        assertThrows(HomeLoanNotFoundException.class, () -> {
        	loanManagementService.getAllLoanDetails();
        });

        
    }

    @Test
    public void testGetDetailsWithException() {
       
        String userORAdmin = "adminUser";
        String password = "adminPassword";
        when(userRepository.findByuserName(userORAdmin)).thenThrow(new RuntimeException("exception"));

        assertThrows(UsernameNotFoundException.class, () -> {
        	loanManagementService.getDetails(userORAdmin, password);
        });

       
    }
    
    @Test
    public void testGetLoanTrackerWithException() {
        
        String userName = "testUser";
        String password = "testPassword";
        when(userRepository.findByuserName(userName)).thenThrow(new RuntimeException("Simulated exception"));
        assertThrows(UsernameNotFoundException.class, () -> {
        	loanManagementService.getLoanTracker(userName, password);
        });

       
    }

    @Test
    public void testGetLoanTrackerWithLoanCreationCompleted() throws UsernameNotFoundException {
        
        String userName = "testUser";
        String password = "testPassword";
        User mockUser = new User();
        mockUser.setUserName(userName);
        mockUser.setPassword(password);

        LoanManagementDetails mockLoan = new LoanManagementDetails();
        mockLoan.setUser(mockUser);
        mockLoan.setCreationDate(LocalDate.now().minusWeeks(3));

        List<LoanManagementDetails> loanObj = new ArrayList<>();
        loanObj.add(mockLoan);

        when(userRepository.findByuserName(userName)).thenReturn(Optional.of(mockUser));
        when(loanManagementDetailsRepository.findAll()).thenReturn(loanObj);
        LoanTrackerResponse result = loanManagementService.getLoanTracker(userName, password);
        assertNotNull(result);
        assertEquals("Home loan creation completed", result.getStatus());
    }
    
    @Test
    public void testGetUserDetailsWithException1() {
        
        String userName = "testUser";
        String password = "testPassword";
        when(userRepository.findByuserName(userName)).thenThrow(new RuntimeException("exception"));
        assertThrows(UsernameNotFoundException.class, () -> {
        	loanManagementService.getUserDetails(userName, password);
        });

       
    }
    
    @Test
    public void testGetLoanTrackerLoanCreationCompleted() throws UsernameNotFoundException {
       
        String userName = "testUser";
        String password = "testPassword";
        LocalDate threeWeeksAgo = LocalDate.now().minusWeeks(3);

        User mockUser = new User();
        mockUser.setUserName(userName);
        mockUser.setPassword(password);

        LoanManagementDetails mockLoan = new LoanManagementDetails();
        mockLoan.setUser(mockUser);
        mockLoan.setCreationDate(threeWeeksAgo);

        List<LoanManagementDetails> loanObj = new ArrayList<>();
        loanObj.add(mockLoan);

        when(userRepository.findByuserName(userName)).thenReturn(Optional.of(mockUser));
        when(loanManagementDetailsRepository.findAll()).thenReturn(loanObj);
        LoanTrackerResponse result = loanManagementService.getLoanTracker(userName, password);
        assertNotNull(result);
        assertEquals("Home loan creation completed", result.getStatus());
    }

    @Test
    public void testGetLoanTrackerLoanUnderVerification() throws UsernameNotFoundException {
      
        String userName = "testUser";
        String password = "testPassword";
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

        User mockUser = new User();
        mockUser.setUserName(userName);
        mockUser.setPassword(password);

        LoanManagementDetails mockLoan = new LoanManagementDetails();
        mockLoan.setUser(mockUser);
        mockLoan.setCreationDate(oneWeekAgo);

        List<LoanManagementDetails> loanObj = new ArrayList<>();
        loanObj.add(mockLoan);

        when(userRepository.findByuserName(userName)).thenReturn(Optional.of(mockUser));
        when(loanManagementDetailsRepository.findAll()).thenReturn(loanObj);
        LoanTrackerResponse result = loanManagementService.getLoanTracker(userName, password);
        assertNotNull(result);
       
    }

    @Test
    public void testGetLoanTrackerHomeLoanApproved() throws UsernameNotFoundException {
       
        String userName = "testUser";
        String password = "testPassword";
        LocalDate today = LocalDate.now();

        User mockUser = new User();
        mockUser.setUserName(userName);
        mockUser.setPassword(password);

        LoanManagementDetails mockLoan = new LoanManagementDetails();
        mockLoan.setUser(mockUser);
        mockLoan.setCreationDate(today);

        List<LoanManagementDetails> loanObj = new ArrayList<>();
        loanObj.add(mockLoan);

        when(userRepository.findByuserName(userName)).thenReturn(Optional.of(mockUser));
        when(loanManagementDetailsRepository.findAll()).thenReturn(loanObj);
        LoanTrackerResponse result = loanManagementService.getLoanTracker(userName, password);
        assertNotNull(result);
        
    }

    
}
