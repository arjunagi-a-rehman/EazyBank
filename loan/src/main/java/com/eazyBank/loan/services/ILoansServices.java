package com.eazyBank.loan.services;

import com.eazyBank.loan.dtos.LoansDto;
import com.eazyBank.loan.model.Loans;

import java.util.Optional;

public interface ILoansServices {
    /**
    * @pram mobileNumber
    * @return null if Loan Already exist with provided mobile Number throws LoanAlready Exist Exception
    */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber
     * @return Loan object if found else will Throw resource not fount exception
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDto
     * @return boolean , True if updated else false
     */
    Boolean updateLoan(LoansDto loansDto);

    /**
     *
     * @param mobileNumber
     * @return true if deleted successfully else return false
     */
    Boolean deleteLoan(String mobileNumber);

}
