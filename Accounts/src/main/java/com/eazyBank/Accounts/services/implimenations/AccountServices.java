package com.eazyBank.Accounts.services.implimenations;

import com.eazyBank.Accounts.Dtos.AccountsDto;
import com.eazyBank.Accounts.Dtos.CustomerDto;
import com.eazyBank.Accounts.constants.AccountsConstants;
import com.eazyBank.Accounts.exception.CustomerAlreadyExistsException;
import com.eazyBank.Accounts.exception.ResourceNotFoundException;
import com.eazyBank.Accounts.mappers.AccountsMapper;
import com.eazyBank.Accounts.mappers.CustomerMappers;
import com.eazyBank.Accounts.models.Accounts;
import com.eazyBank.Accounts.models.Customer;
import com.eazyBank.Accounts.repositories.IAccountsRepo;
import com.eazyBank.Accounts.repositories.ICustomerRepo;
import com.eazyBank.Accounts.services.IAccountServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServices implements IAccountServices {
    private IAccountsRepo accountsRepo;
    private ICustomerRepo customerRepo;

    /**
     * @param customerDto pass the customer dto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> existingCustomer=customerRepo.findByMobileNumber(customerDto.getMobileNumber());
        if(existingCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("the customer with "+customerDto.getMobileNumber()+" already exist");
        }
        Customer customer= CustomerMappers.mapToCustomer(customerDto,new Customer());
        customerRepo.save(customer);
        accountsRepo.save(createAccountForCustomer(customer));
    }



    private Accounts createAccountForCustomer(Customer customer){
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        Accounts accounts=new Accounts();
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setAccount_number(randomAccNumber);
        accounts.setCustomerId(customer.getCustomerId());
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        return accounts;
    }

    /**
     * @param mobileNumber find customer by mobile number
     * @return customerdto
     */
    @Override
    public CustomerDto getAccountDetails(String mobileNumber) {
        Customer customer=customerRepo.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("No customer with ","mobile Number",mobileNumber));
        Accounts accounts= accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(()->new ResourceNotFoundException("No account fount with ","Customer Id ",customer.getCustomerId().toString()));
        CustomerDto customerDto=CustomerMappers.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto to take add the data
     * @return true if updated successfully else false
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        Customer customer=customerRepo.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow(()->new ResourceNotFoundException("Customer with","mobile number",customerDto.getMobileNumber()));
        customer=CustomerMappers.mapToCustomer(customerDto,customer);
        AccountsDto accountsDto=customerDto.getAccountsDto();
        if(accountsDto!=null){
            customerRepo.save(customer);
            Accounts accounts=accountsRepo.findById(accountsDto.getAccount_number()).orElseThrow(()->new ResourceNotFoundException("The account with ","account number",accountsDto.getAccount_number().toString()));
            accounts=AccountsMapper.mapToAccounts(accountsDto,accounts);
            accountsRepo.save(accounts);
            isUpdated=true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return true if deleted successfully else false
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer=customerRepo.findByMobileNumber(mobileNumber).
                orElseThrow(()->new ResourceNotFoundException("Customer with ","Mobile number",mobileNumber));
        customerRepo.deleteById(customer.getCustomerId());
        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        return true;
    }
}
