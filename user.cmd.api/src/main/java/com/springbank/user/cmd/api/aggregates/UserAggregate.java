package com.springbank.user.cmd.api.aggregates;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.cmd.api.security.PasswordEncoder;
import com.springbank.user.cmd.api.security.PasswordEncoderImpl;
import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    public UserAggregate() {
        passwordEncoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        User newUser = command.getUser();
        newUser.setId(command.getId());
        passwordEncoder = new PasswordEncoderImpl();
        String password = newUser.getAccount().getPassword();
        newUser.getAccount().setPassword(passwordEncoder.hashPassword(password));

        UserRegisteredEvent event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        User updatedUser = command.getUser();
        updatedUser.setId(command.getId());
        String password = updatedUser.getAccount().getPassword();
        updatedUser.getAccount().setPassword(passwordEncoder.hashPassword(password));

        UserUpdatedEvent event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        // Publish event to the Eventbus
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        UserRemovedEvent event = UserRemovedEvent.builder()
                .id(command.getId())
                .build();
        AggregateLifecycle.apply(event);
    }


    // Alter the state of aggregate
    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event) {
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        this.id = event.getId();
        this.user.setId(event.getId());
        //AggregateLifecycle.markDeleted();
    }

}
