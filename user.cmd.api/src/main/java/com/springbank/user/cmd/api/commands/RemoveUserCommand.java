package com.springbank.user.cmd.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveUserCommand {
    @TargetAggregateIdentifier
    private String id;
}
