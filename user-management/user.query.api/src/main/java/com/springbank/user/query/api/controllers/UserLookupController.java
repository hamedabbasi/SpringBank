package com.springbank.user.query.api.controllers;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindAllUsersQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
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
@RequestMapping(path = "/api/v1/userLookup")
public class UserLookupController {

    private final QueryGateway queryGateway;

    @Autowired
    public UserLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        try {
            FindAllUsersQuery query = new FindAllUsersQuery();
            UserLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            if(response == null || response.getUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            String safeErrorMessage = "Failed to complete get all users request!";
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable(value = "id") String id) {
        try {
            FindUserByIdQuery query = new FindUserByIdQuery(id);
            UserLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            if(response == null || response.getUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            String safeErrorMessage = "Failed to complete get user by ID request!";
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byFilter/{filter}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> searchUsersByFilter(@PathVariable(value = "filter") String filter) {
        try {
            SearchUsersQuery query = new SearchUsersQuery(filter);
            UserLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            if(response == null || response.getUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            String safeErrorMessage = "Failed to complete user search request!";
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
