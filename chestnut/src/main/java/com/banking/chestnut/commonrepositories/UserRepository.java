package com.banking.chestnut.commonrepositories;

import com.banking.chestnut.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
}
