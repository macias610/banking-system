package com.bankowosc.lokata

import com.bankowosc.lokata.models.Deposit
import com.bankowosc.lokata.services.DepositService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class DatabaseTest extends Specification {
    
    @Autowired
    private DepositService depositService
    
    def "Check if deposit in database have desired activation state"() {
      
        when:
           Deposit deposit = depositService.getDepositById(1)
        then:
            deposit.getIsActive() == true
    }
    
    def "Check if deposit is poperly fetch from database"() {
      
        when:
           Deposit deposit = depositService.getDepositById(1)
        then:
            deposit.getIsActive() == true
    }
    
}