package com.app.model;

import java.util.Objects;

public class LoanTrackerResponse {

	public LoanManagementDetails getLoanManagementDetails() {
		return loanManagementDetails;
	}

	public void setLoanManagementDetails(LoanManagementDetails loanManagementDetails) {
		this.loanManagementDetails = loanManagementDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private LoanManagementDetails loanManagementDetails;
    private String status;

    public LoanTrackerResponse(LoanManagementDetails loanManagementDetails, String status) {
        this.loanManagementDetails = loanManagementDetails;
        this.status = status;
    }

	@Override
	public int hashCode() {
		return Objects.hash(loanManagementDetails, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanTrackerResponse other = (LoanTrackerResponse) obj;
		return Objects.equals(loanManagementDetails, other.loanManagementDetails)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "LoanTrackerResponse [loanManagementDetails=" + loanManagementDetails + ", status=" + status + "]";
	}

	public LoanTrackerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
    // getters and setters

}
