package com.banking.chestnut.commonservices;

import com.banking.chestnut.models.Users;

import java.util.Optional;

public interface IUserService {
    Optional<Users> findById(Integer id);
}
