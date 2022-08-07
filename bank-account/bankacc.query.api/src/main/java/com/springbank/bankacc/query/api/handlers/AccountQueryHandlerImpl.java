package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.dto.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        Optional<BankAccount> account = accountRepository.findById(query.getId());
        AccountLookupResponse response = account.isPresent()
                ? new AccountLookupResponse("Bank Account Successfully Returned!", account.get())
                : new AccountLookupResponse("No Bank Account Found for ID - " + query.getId());
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountByHolderId(FindAccountByHolderIdQuery query) {
        Optional<BankAccount> account = accountRepository.findByAccountHolderId(query.getAccountHolderId());
        AccountLookupResponse response = account.isPresent()
                ? new AccountLookupResponse("Bank Account Successfully Returned!", account.get())
                : new AccountLookupResponse("No Bank Account Found for Holder ID - " + query.getAccountHolderId());
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccountIterator = accountRepository.findAll();

        if (!bankAccountIterator.iterator().hasNext())
            return new AccountLookupResponse("No Bank Accounts were Found!");

        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccountIterator.forEach(i -> bankAccounts.add(i));
        int count = bankAccounts.size();

        return new AccountLookupResponse("Successfully Returned " + count + " Bank Account(s)!", bankAccounts);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountsWithBalance(FindAccountsWithBalanceQuery query) {
        List<BankAccount> bankAccounts = query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());

        AccountLookupResponse response = bankAccounts != null && bankAccounts.size() > 0
                ? new AccountLookupResponse("Successfully Returned " + bankAccounts.size() + "Bank Account(s)!", bankAccounts)
                : new AccountLookupResponse("No Bank Accounts were Found!");

        return response;
    }
}
