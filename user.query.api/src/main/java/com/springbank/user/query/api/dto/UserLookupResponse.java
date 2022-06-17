package com.springbank.user.query.api.dto;

import com.springbank.user.core.dto.BaseResponse;
import com.springbank.user.core.models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserLookupResponse extends BaseResponse {
    List<User> users;

    public UserLookupResponse(String message) {
        super(message);
    }

    public UserLookupResponse(String message, User user) {
        super(message);
        this.users = new ArrayList<>();
        this.users.add(user);
    }

    public UserLookupResponse(User user) {
        this.users = new ArrayList<>();
        this.users.add(user);
    }

    public UserLookupResponse(List<User> users) {
        this.users = users;
    }

}
