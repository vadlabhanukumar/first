package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.app.exception.HomeLoanNotFoundException;
import com.app.exception.UsernameNotFoundException;
import com.app.model.LoanManagementDetails;
import com.app.model.LoanTrackerResponse;
import com.app.model.User;

public interface LoanManagementService {

	User getUserDetails(String userName) throws UsernameNotFoundException;

	

    int applyForHomeLoan(LoanManagementDetails homeLoan) throws HomeLoanNotFoundException;

	LoanManagementDetails updateMontlyPayment(Double monthlyPayment,int loanNumber) throws HomeLoanNotFoundException;

	List<LoanManagementDetails> getAllLoanDetails() throws HomeLoanNotFoundException;

	

	int registerForLoan(User user);

	List<LoanManagementDetails> getDetails(String userORAdmin,String password) throws UsernameNotFoundException;

	LoanTrackerResponse getLoanTracker(String userName,String password) throws UsernameNotFoundException;

	LoanManagementDetails getUserDetails(String userName, String password) throws UsernameNotFoundException;
	
	

}
