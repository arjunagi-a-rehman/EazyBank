package com.eazyBank.loan.services.imp;

import com.eazyBank.loan.constants.LoansConstants;
import com.eazyBank.loan.dtos.LoansDto;
import com.eazyBank.loan.exception.LoanAlreadyExistException;
import com.eazyBank.loan.exception.ResourceNotFoundException;
import com.eazyBank.loan.mapper.LoanMapper;
import com.eazyBank.loan.model.Loans;
import com.eazyBank.loan.repository.ILoansRepo;
import com.eazyBank.loan.services.ILoansServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServices implements ILoansServices {
    private ILoansRepo loansRepo;

    /**
     * @pram mobileNumber
     * @return null if Loan Already exist with provided mobile Number throws LoanAlready Exist Exception
     */
    @Override
    public void createLoan(String mobileNumber) {
        if(loansRepo.findByMobileNumber(mobileNumber).isPresent()){
            throw new LoanAlreadyExistException("The loan with mobile Number "+mobileNumber+"  Already exist");
        }
        loansRepo.save(createNewLoan(mobileNumber));
    }


    /**
     * @param mobileNumber
     * @return Loan object if found else will Throw resource not fount exception
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans=loansRepo.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Loan","mobile number",mobileNumber));
        return LoanMapper.loansToLoansDto(loans,new LoansDto());
    }


    /**
     * @param loansDto
     * @return boolean , True if updated else false
     */
    @Override
    public Boolean updateLoan(LoansDto loansDto) {
        Loans loan=loansRepo.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(()->new ResourceNotFoundException("Loan","Loan number",loansDto.getLoanNumber()));
        loansDto.setOutstandingAmount(loan.getTotalLoan()-loansDto.getAmountPaid());
        loansDto.setMobileNumber(loan.getMobileNumber());
        LoanMapper.LoansDtoToLoan(loansDto,loan);
        loansRepo.save(loan);
        return true;
    }

    /**
     * @param mobileNumber
     * @return true if deleted successfully else return false
     */
    @Override
    public Boolean deleteLoan(String mobileNumber) {
        Loans loans=loansRepo.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Loan","mobile number",mobileNumber));
        loansRepo.delete(loans);
        return true;
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans loans=new Loans();
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setMobileNumber(mobileNumber);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        return loans;
    }

}
