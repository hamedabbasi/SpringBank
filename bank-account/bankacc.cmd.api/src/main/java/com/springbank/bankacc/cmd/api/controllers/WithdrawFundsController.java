package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.WithdrawFundsCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {

    private final CommandGateway commandGateway;

    @Autowired
    public WithdrawFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable("id") String id,
                                                      @Valid @RequestBody WithdrawFundsCommand command) {
        try {
            command.setId(id);
            // commandGateway.send is a computable future and .get method blocks until the future is complete.
            // use .get method in production. There is an exception that will be generated in withdraw funds command
            // and if balance is smaller than amount, withdrawal throw an illegal state exception. In this situation
            // if you use only send method, controller responses withdrawal successfully completed.
            commandGateway.send(command).get();
            return new ResponseEntity<>(new BaseResponse("Withdrawal successfully completed!"), HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Error while processing request to withdraw funds into bank account for id - " + id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
