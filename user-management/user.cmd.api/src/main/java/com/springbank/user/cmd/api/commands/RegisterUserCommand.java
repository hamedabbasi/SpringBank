package com.springbank.user.cmd.api.commands;

import com.springbank.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class RegisterUserCommand {

    // In order for axon to know which instance of an aggregate type should handle the
    // command message, the field carrying the aggregate identifier in the command object
    // must be annotated by @TargetAggregateIdentifier
    @TargetAggregateIdentifier
    private String id;
    @Valid
    @NotNull(message = "No user details were supplied")
    private User user;
}
