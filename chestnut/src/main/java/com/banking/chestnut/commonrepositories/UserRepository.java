package com.banking.chestnut.commonrepositories;

import com.banking.chestnut.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

}
