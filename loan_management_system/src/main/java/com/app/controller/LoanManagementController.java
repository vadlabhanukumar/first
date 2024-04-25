package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.HomeLoanNotFoundException;
import com.app.exception.UsernameNotFoundException;
import com.app.model.LoanManagementDetails;
import com.app.model.LoanTrackerResponse;
import com.app.model.User;
import com.app.service.LoanManagementService;


/*
 * author @bhanu
 */

@RestController
public class LoanManagementController {
	
	@Autowired
	private LoanManagementService loanManagementService;
	
   /* get user details by usrName */
	@GetMapping("/user/{userName}")
	public ResponseEntity<User> getUserByUserName(@PathVariable String userName) throws UsernameNotFoundException {
		User user = loanManagementService.getUserDetails(userName);
		return ResponseEntity.ok(user);
	}
    
	
	/* apply for loan*/
	@PostMapping("/apply")
	public int applyForHomeLoan(@RequestBody LoanManagementDetails homeLoan) throws HomeLoanNotFoundException {
		
		int loanNumber=loanManagementService.applyForHomeLoan( homeLoan);
		return loanNumber;
	
		
		 
	}
	
	/*admin login */
	@GetMapping("/userORAdmin/{userName}/{password}")
	public List<LoanManagementDetails> getDetails(@PathVariable String userName,@PathVariable String password) throws UsernameNotFoundException
	{
		List<LoanManagementDetails> loanListObj=loanManagementService.getDetails(userName,password);
		
		return loanListObj;
		
	}
	/* pay montly emi*/
	@PutMapping("/update/{monthlyPayment}/{loanNumber}")
	public LoanManagementDetails updateMontlyPayment(@PathVariable Double monthlyPayment,@PathVariable int loanNumber) throws HomeLoanNotFoundException
	{
		LoanManagementDetails loanManagementDetails=loanManagementService.updateMontlyPayment(monthlyPayment, loanNumber);
		return loanManagementDetails;
	}
	
	
	/* loan tracker */
	@GetMapping(value="/tracker/{userName}/{password}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoanTrackerResponse> getLoanTracker(@PathVariable String userName, @PathVariable
			String password) throws UsernameNotFoundException {
		LoanTrackerResponse loanManagementDetails=loanManagementService.getLoanTracker(userName,password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(loanManagementDetails, headers, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userName}/{password}")
	public LoanManagementDetails getUserDetails(@PathVariable String userName,@PathVariable String password) throws UsernameNotFoundException
	{
		LoanManagementDetails loanManagementDetails=loanManagementService.getUserDetails(userName,password);
		return loanManagementDetails;
	}
	
	
	
}
