package com.bankowosc.lokata.repositories;

import com.bankowosc.lokata.models.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Accounts, Long> { //Dla klasy Accounts, która ma id o typie Long

}
