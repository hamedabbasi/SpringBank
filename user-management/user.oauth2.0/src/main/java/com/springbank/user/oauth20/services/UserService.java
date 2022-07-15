package com.springbank.user.oauth20.services;

import com.springbank.user.core.models.Account;
import com.springbank.user.core.models.User;
import com.springbank.user.oauth20.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        Account account = user.get().getAccount();

        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(account.getPassword())
                .authorities(account.getRoles())
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
