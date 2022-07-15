package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Size(min = 2, message = "username must be at least 2 characters")
    private String username;
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;
    @NotNull(message = "Specify at least 1 user role")
    private List<Role> roles;
}
