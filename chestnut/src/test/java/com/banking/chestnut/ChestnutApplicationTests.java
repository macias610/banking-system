package com.banking.chestnut;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.ror.repositories.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChestnutApplicationTests {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Test
	public void contextLoads() {
		Account account = accountRepository.findById(1).orElse(null);
		assert account == null;
	}

}
