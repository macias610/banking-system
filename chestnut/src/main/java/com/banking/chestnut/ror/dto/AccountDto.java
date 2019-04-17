package com.banking.chestnut.ror.dto;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.models.ClientInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;
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

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("pesel")
    private Long pesel;

    @JsonProperty("available_amount")
    private Long availableAmount;

    @JsonProperty("locked_amount")
    private Long lockedAmount;

    public AccountDto(Account account) {
        assignClientData(account.getClientId().getClientInfoId());
        assignAccountData(account, account.getInfoId());
    }

    public void assignClientData(ClientInfo clientInfo){
        this.firstName = clientInfo.getFirstName();
        this.surname = clientInfo.getSurname();
        this.pesel = clientInfo.getPesel();
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
