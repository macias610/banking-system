package com.banking.chestnut.credit.repositories;

import com.banking.chestnut.models.PaymentSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PaymentScheduleRepository extends CrudRepository<PaymentSchedule, Integer> {
    List<PaymentSchedule> findAllByCreditId(Integer id);
    List<PaymentSchedule> findAllByIsActive(Boolean isActive);

}
