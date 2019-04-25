package com.banking.chestnut.commonservices;

import com.banking.chestnut.models.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Integer id);
}
