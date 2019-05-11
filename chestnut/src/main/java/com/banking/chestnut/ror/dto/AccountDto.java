package com.banking.chestnut.ror.dto;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.models.ClientInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@Data
public class AccountDto implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("number_client_account")
    private String numberClientAccount;

    @JsonProperty("number_banking_account")
    private String numberBankingAccount;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("is_blocked")
    private Boolean isBlocked;

    @JsonProperty("type")
    private String type;

    @JsonProperty("owner")
    private ClientAccountDto owner;

    @JsonProperty("agent")
    private ClientAccountDto agent;

    @JsonProperty("available_amount")
    private Long availableAmount;

    @JsonProperty("locked_amount")
    private Long lockedAmount;

    public AccountDto(Account account) {
        assignClientData(account.getClientId().getClientInfoId());
        assignAccountData(account, account.getInfoId());
        if(account.getAgentId() != null)
            assignAgentData(account.getAgentId().getClientInfoId());
    }

    public void assignClientData(ClientInfo clientInfo){
        this.owner = new ModelMapper().map(clientInfo, ClientAccountDto.class);
    }

    public void assignAgentData(ClientInfo clientInfo){
        this.agent = new ModelMapper().map(clientInfo, ClientAccountDto.class);
    }

    public void assignAccountData(Account account, AccountInfo accountInfo){
        this.id = account.getId();
        this.currency = account.getCurrency();
        this.iban = account.getIban();
        this.numberClientAccount = account.getNumberClientAccount();
        this.numberBankingAccount = account.getNumberBankingAccount();
        this.isActive = account.getIsActive();
        this.isBlocked = account.getIsBlocked();
        this.type = account.getType();
        this.lockedAmount = accountInfo.getLockedAmount();
        this.availableAmount = accountInfo.getAvailableAmount();
    }


}
