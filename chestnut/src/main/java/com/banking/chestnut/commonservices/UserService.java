package com.banking.chestnut.commonservices;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return this.userRepository.findById(id);
    }
}
