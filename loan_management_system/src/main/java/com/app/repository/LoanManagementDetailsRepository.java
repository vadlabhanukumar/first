package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.LoanManagementDetails;
import com.app.model.User;

@Repository
public interface LoanManagementDetailsRepository extends JpaRepository<LoanManagementDetails,Integer>{

	//LoanManagementDetails findByUser(User user);





}
