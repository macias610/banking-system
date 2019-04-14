package com.banking.chestnut;

import com.banking.chestnut.deposit.helpers.DateHelper;
import com.banking.chestnut.deposit.helpers.OperationFactory;
import com.banking.chestnut.deposit.repositories.DepositRepository;
import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.Deposit;
import com.banking.chestnut.models.Operation;
import com.banking.chestnut.models.OperationType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.NoSuchElementException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ChestnutApplicationTests {
	
	@Test
	public void contextLoads() {
	}

}
