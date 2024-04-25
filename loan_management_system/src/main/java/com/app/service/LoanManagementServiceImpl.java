package com.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exception.HomeLoanNotFoundException;
import com.app.exception.UsernameNotFoundException;
import com.app.model.LoanManagementDetails;
import com.app.model.LoanTrackerResponse;
import com.app.model.User;
import com.app.repository.LoanManagementDetailsRepository;
import com.app.repository.UserRepository;


@Service
public class LoanManagementServiceImpl implements LoanManagementService{

	 private static final Logger logger = LoggerFactory.getLogger(LoanManagementServiceImpl.class);
	
     @Autowired
     private LoanManagementDetailsRepository loanManagementDetailsRepository;
     
     @Autowired
     private UserRepository userRepository;
	
	
	public User getUserDetails(String userName) throws UsernameNotFoundException {
		 try {
	           
	            return userRepository.findByuserName(userName).orElse(null);
	        } catch (Exception e) {
	            logger.error("Error occurred while retrieving user details for userName: {}", userName, e);
	            throw new UsernameNotFoundException("Failed to retrieve user details for userName: " + userName);
	        }
	    }
	

	

	@Override
	public int applyForHomeLoan(LoanManagementDetails homeLoan) throws HomeLoanNotFoundException {
		try
		{
		
		if(homeLoan.getUser().getSalary()>=24000000 && homeLoan.getUser().getAge()<=35)
		{
			
		homeLoan.setCurrentRateofInterest(1.00);
		homeLoan.setPrincipalOutstandingAmount(homeLoan.getTotalLoanamount());
		 Double monthlyInterestRate = Math.round(homeLoan.getTotalLoanamount()* 0.02 * 2) / 2.0;
		 Double totalInterestRate=monthlyInterestRate * homeLoan.getLoanTenure();
		 homeLoan.setOutstandingEMIcount(homeLoan.getTotalLoanamount()+totalInterestRate);
		 Double montlyPayment= (homeLoan.getTotalLoanamount()+totalInterestRate) /homeLoan.getLoanTenure();
		 int decimalPlaces = 2;
		 double roundedValue = roundToDecimalPlaces(montlyPayment, decimalPlaces);
		 homeLoan.setMontlyPayment(roundedValue);
		loanManagementDetailsRepository.save(homeLoan);
		}
		else
		{
			
			homeLoan.setCurrentRateofInterest(2.00);
			homeLoan.setPrincipalOutstandingAmount(homeLoan.getTotalLoanamount());
			 Double monthlyInterestRate = homeLoan.getTotalLoanamount()*0.02;
			 Double totalInterestRate=monthlyInterestRate * homeLoan.getLoanTenure();
			 homeLoan.setOutstandingEMIcount(homeLoan.getTotalLoanamount()+totalInterestRate);
			 Double montlyPayment= (homeLoan.getTotalLoanamount()+totalInterestRate) /homeLoan.getLoanTenure();
			 int decimalPlaces = 2;
			 double roundedValue = roundToDecimalPlaces(montlyPayment, decimalPlaces);
			 homeLoan.setMontlyPayment(roundedValue);
			loanManagementDetailsRepository.save(homeLoan);
			
		}
		return homeLoan.getHomeLoanNumber();
		}catch (Exception e) {
			logger.error("Error occurred while retrieving Loan  details : {}");
            throw new HomeLoanNotFoundException("Failed to retrieve user details for userName:");
	
		}
		
	}

	private double roundToDecimalPlaces(Double montlyPayment, int decimalPlaces) {
		double scale = Math.pow(10, decimalPlaces);
        return Math.round(montlyPayment * scale) / scale;
	}

	@Override
	public LoanManagementDetails updateMontlyPayment(Double monthlyPayment,int loanNumber) throws HomeLoanNotFoundException {
		
		try {
		LoanManagementDetails loanManagementDetails=new LoanManagementDetails();
		LoanManagementDetails loanManagementDetailsObj=new LoanManagementDetails();
		

			loanManagementDetails = loanManagementDetailsRepository.findById(loanNumber).get();
			loanManagementDetailsObj.setHomeLoanNumber(loanManagementDetails.getHomeLoanNumber());
			loanManagementDetailsObj.setCurrentRateofInterest(loanManagementDetails.getCurrentRateofInterest());
			loanManagementDetailsObj.setLoanTenure(loanManagementDetails.getLoanTenure());
			loanManagementDetailsObj.setLoanType(loanManagementDetails.getLoanType());
			loanManagementDetailsObj.setMontlyPayment(loanManagementDetails.getMontlyPayment());
			loanManagementDetailsObj.setNomineeDetails(loanManagementDetails.getNomineeDetails());
			loanManagementDetailsObj.setTotalLoanamount(loanManagementDetails.getTotalLoanamount());
			User user=new User();
			user.setId(loanManagementDetails.getUser().getId());
			user.setUserName(loanManagementDetails.getUser().getUserName());
			user.setAddress(loanManagementDetails.getUser().getAddress());
			user.setMobileNumber(loanManagementDetails.getUser().getMobileNumber());
			user.setPassword(loanManagementDetails.getUser().getPassword());
			user.setRole(loanManagementDetails.getUser().getRole());
			user.setSalary(loanManagementDetails.getUser().getSalary());
			user.setAge(loanManagementDetails.getUser().getAge());
			loanManagementDetailsObj.setUser(user);
			loanManagementDetailsObj.setCreationDate(loanManagementDetails.getCreationDate());
			 if (loanManagementDetails.getOutstandingEMIcount() != null) {
			loanManagementDetailsObj.setOutstandingEMIcount(loanManagementDetails.getOutstandingEMIcount()- monthlyPayment);
			 
			Double monthlyInterestRate = loanManagementDetails.getTotalLoanamount()*0.02;
			Double monthlyamountpay= loanManagementDetails.getTotalLoanamount()/loanManagementDetails.getLoanTenure();
			Double principalOutStandingAmount= loanManagementDetails.getPrincipalOutstandingAmount()-(monthlyamountpay+monthlyInterestRate);
			 
			int decimalPlaces = 2;
			 double roundedValue = roundToDecimalPlaces(principalOutStandingAmount, decimalPlaces);
			 
			loanManagementDetailsObj.setPrincipalOutstandingAmount(roundedValue);
			 }
			loanManagementDetailsRepository.save(loanManagementDetailsObj);
	
		return loanManagementDetailsObj;
		}catch (Exception e) {
			logger.error("Error occurred while retrieving Loan  details : {}");
            throw new HomeLoanNotFoundException("Failed to retrieve user details for userName:");
	
		}
		
		
	}

	@Override
	public List<LoanManagementDetails> getAllLoanDetails() throws HomeLoanNotFoundException {
		try {
		return loanManagementDetailsRepository.findAll();
	 } catch (Exception e) {
		 logger.error("Error occurred while retrieving Loan  details : {}");
         throw new HomeLoanNotFoundException("Failed to retrieve user details for userName:");
     }
	
	}

	@Override
	public int registerForLoan(User user) {
		
		userRepository.save(user);
		return user.getId();
		
		
	}

	@Override
	public List<LoanManagementDetails> getDetails(String userORAdmin,String password) throws UsernameNotFoundException {
		List<LoanManagementDetails> loanObj=new ArrayList<>();
		try {
		User user=userRepository.findByuserName(userORAdmin).orElse(null);
		if(user != null &&user.getRole().equalsIgnoreCase("admin")&& user.getPassword().equalsIgnoreCase(password))
		{
			 loanObj=loanManagementDetailsRepository.findAll();
			 return loanObj;
			
		}
		} catch (Exception e) {
            logger.error("Error occurred while retrieving user details for userName: {}",userORAdmin, e);
            throw new UsernameNotFoundException("Failed to retrieve user details for userName: " + userORAdmin);
        }
		return loanObj;
		
		
		
	}

	public LoanTrackerResponse getLoanTracker(String userName, String password) throws UsernameNotFoundException {
		try {
	    User user = userRepository.findByuserName(userName).orElse(null);
	    List<LoanManagementDetails> loanObj = loanManagementDetailsRepository.findAll();
	    LoanManagementDetails loanManagementDetails = loanObj.stream()
	            .filter(loan -> loan.getUser().equals(user))
	            .filter(loan -> loan.getUser().getPassword().equals(password))
	            .findFirst()
	            .orElse(null);
	    LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
	    LocalDate oneWeeksAgo = LocalDate.now().minusWeeks(1);

	    String status;
	    if (loanManagementDetails.getCreationDate().isAfter(twoWeeksAgo)) {
	        status = "Home loan creation will be completed after required approvals";
	    } else if (loanManagementDetails.getCreationDate().isBefore(twoWeeksAgo)) {
	        status = "Home loan creation completed";
	    } else if (loanManagementDetails.getCreationDate().isAfter(oneWeeksAgo)) {
	        status = "Home loan creation is under verification";
	    } else {
	        status = "Home loan approved";
	    }

	    return new LoanTrackerResponse(loanManagementDetails, status);
	} catch (Exception e) {
        logger.error("Error occurred while retrieving user details for userName: {}", userName, e);
        throw new UsernameNotFoundException("Failed to retrieve user details for userName: " + userName);
    }
	}

	@Override
	public LoanManagementDetails getUserDetails(String userName, String password) throws UsernameNotFoundException {
		try {
		 User user = userRepository.findByuserName(userName).orElse(null);
		    List<LoanManagementDetails> loanObj = loanManagementDetailsRepository.findAll();
		    LoanManagementDetails loanManagementDetails = loanObj.stream()
		            .filter(loan -> loan.getUser().equals(user))
		            .filter(loan -> loan.getUser().getPassword().equals(password))
		            .findFirst()
		            .orElse(null);
			return loanManagementDetails;
	} catch (Exception e) {
        logger.error("Error occurred while retrieving user details for userName: {}", userName, e);
        throw new UsernameNotFoundException("Failed to retrieve user details for userName: " + userName);
    }
	}
}
