package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user")
public class User {
    @Id
    private String id;
    @NotEmpty(message = "First name is mandatory")
    private String firstname;
    @NotEmpty(message = "Last name is mandatory")
    private String lastname;
    @Email(message = "Please provide a valid email address")
    private String emailAddress;
    @Valid
    @NotNull(message = "Please provide account credentials")
    private Account account;
}
