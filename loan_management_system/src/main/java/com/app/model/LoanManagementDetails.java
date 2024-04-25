package com.app.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="loanDetails")
public class LoanManagementDetails {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int homeLoanNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	
	@Column(name="loanType")
	private String loanType;
	
	@Column(name="nomineeDetails")
	private String nomineeDetails;
	
	@Column(name="totalLoanamount")
	private Double totalLoanamount;
	
	@Column(name="montlyPayment")
	private Double montlyPayment;
	
	@Column(name="loanTenure")
	private int loanTenure;
	
	@Column(name="currentRateofInterest")
	private Double currentRateofInterest;
	
	@Column(name="principalOutstandingAmount")
	private Double principalOutstandingAmount;
	
	@Column(name="oustandEMIcount")
	private Double outstandingEMIcount;
	
	@Column(name="creationDate")
	private LocalDate creationDate;

	public int getHomeLoanNumber() {
		return homeLoanNumber;
	}

	public void setHomeLoanNumber(int homeLoanNumber) {
		this.homeLoanNumber = homeLoanNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getNomineeDetails() {
		return nomineeDetails;
	}

	public void setNomineeDetails(String nomineeDetails) {
		this.nomineeDetails = nomineeDetails;
	}

	public Double getTotalLoanamount() {
		return totalLoanamount;
	}

	public void setTotalLoanamount(Double totalLoanamount) {
		this.totalLoanamount = totalLoanamount;
	}

	public Double getMontlyPayment() {
		return montlyPayment;
	}

	public void setMontlyPayment(Double montlyPayment) {
		this.montlyPayment = montlyPayment;
	}

	public int getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(int loanTenure) {
		this.loanTenure = loanTenure;
	}

	public Double getCurrentRateofInterest() {
		return currentRateofInterest;
	}

	public void setCurrentRateofInterest(Double currentRateofInterest) {
		this.currentRateofInterest = currentRateofInterest;
	}

	public Double getPrincipalOutstandingAmount() {
		return principalOutstandingAmount;
	}

	public void setPrincipalOutstandingAmount(Double principalOutstandingAmount) {
		this.principalOutstandingAmount = principalOutstandingAmount;
	}

	public Double getOutstandingEMIcount() {
		return outstandingEMIcount;
	}

	public void setOutstandingEMIcount(Double outstandingEMIcount) {
		this.outstandingEMIcount = outstandingEMIcount;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationDate, currentRateofInterest, homeLoanNumber, loanTenure, loanType, montlyPayment,
				nomineeDetails, outstandingEMIcount, principalOutstandingAmount, totalLoanamount, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanManagementDetails other = (LoanManagementDetails) obj;
		return Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(currentRateofInterest, other.currentRateofInterest)
				&& homeLoanNumber == other.homeLoanNumber && loanTenure == other.loanTenure
				&& Objects.equals(loanType, other.loanType) && Objects.equals(montlyPayment, other.montlyPayment)
				&& Objects.equals(nomineeDetails, other.nomineeDetails)
				&& Objects.equals(outstandingEMIcount, other.outstandingEMIcount)
				&& Objects.equals(principalOutstandingAmount, other.principalOutstandingAmount)
				&& Objects.equals(totalLoanamount, other.totalLoanamount) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "LoanManagementDetails [homeLoanNumber=" + homeLoanNumber + ", user=" + user + ", loanType=" + loanType
				+ ", nomineeDetails=" + nomineeDetails + ", totalLoanamount=" + totalLoanamount + ", montlyPayment="
				+ montlyPayment + ", loanTenure=" + loanTenure + ", currentRateofInterest=" + currentRateofInterest
				+ ", principalOutstandingAmount=" + principalOutstandingAmount + ", outstandingEMIcount="
				+ outstandingEMIcount + ", creationDate=" + creationDate + "]";
	}

	public LoanManagementDetails(int homeLoanNumber, User user, String loanType, String nomineeDetails,
			Double totalLoanamount, Double montlyPayment, int loanTenure, Double currentRateofInterest,
			Double principalOutstandingAmount, Double outstandingEMIcount, LocalDate creationDate) {
		super();
		this.homeLoanNumber = homeLoanNumber;
		this.user = user;
		this.loanType = loanType;
		this.nomineeDetails = nomineeDetails;
		this.totalLoanamount = totalLoanamount;
		this.montlyPayment = montlyPayment;
		this.loanTenure = loanTenure;
		this.currentRateofInterest = currentRateofInterest;
		this.principalOutstandingAmount = principalOutstandingAmount;
		this.outstandingEMIcount = outstandingEMIcount;
		this.creationDate = creationDate;
	}

	public LoanManagementDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

}	