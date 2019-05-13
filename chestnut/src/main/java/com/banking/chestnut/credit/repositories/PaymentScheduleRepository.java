package com.banking.chestnut.credit.repositories;

import com.banking.chestnut.models.PaymentSchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PaymentScheduleRepository extends CrudRepository<PaymentSchedule, Integer> {
    Optional<Set<PaymentSchedule>> findAllByCreditId(Integer id);

}
