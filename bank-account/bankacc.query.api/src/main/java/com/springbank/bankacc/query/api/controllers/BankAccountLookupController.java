package com.springbank.bankacc.query.api.controllers;

import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.dto.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class BankAccountLookupController {

    private final QueryGateway queryGateway;

    @Autowired
    public BankAccountLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> findAllAccounts() {
        try {
            FindAllAccountsQuery query = new FindAllAccountsQuery();
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Failed to call complete get all accounts request!";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> findAccountById(@PathVariable("id") String id) {
        try {
            FindAccountByIdQuery query = new FindAccountByIdQuery(id);
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Failed to call complete get account by ID request!";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byHolderId/{accountHolderId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> findAccountByHolderId(@PathVariable("accountHolderId") String accountHolderId) {
        try {
            FindAccountByHolderIdQuery query = new FindAccountByHolderIdQuery(accountHolderId);
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Failed to call complete get account by holder ID request!";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/withBalance/{equalityType}/{balance}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> findAccountByHolderId(@PathVariable("equalityType") EqualityType equalityType,
                                                                       @PathVariable("balance") double balance) {
        try {
            FindAccountsWithBalanceQuery query = new FindAccountsWithBalanceQuery(equalityType, balance);
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Failed to call complete get accounts with balance request!";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
