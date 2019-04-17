package com.banking.chestnut.helper;

import com.banking.chestnut.models.Banks;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.dto.AccountNumber;

import java.math.BigInteger;

public class AccountNumberHelper {

    private static final int ASCII_DIFFERENT = -55;
    private static final int IBAN_REQUIRED_LENGTH = 28;
    private static final BigInteger MINUEND = new BigInteger("98");
    private static final BigInteger MOD_FACTOR = new BigInteger("97");

    public static AccountNumber generateAccountNumber(Client client, String accountNumber){
        AccountNumber number = new AccountNumber();
        Banks bank = client.getBankId();
        String bankCountry = bank.getCountry();
        String controlSum;

        if (bankCountry.length() == 0){
            bankCountry = "PL";
        }

        String countryCodeInNumber = "";
        for (char ch : bankCountry.toCharArray()){
            int asciiCode = ch;
            asciiCode += AccountNumberHelper.ASCII_DIFFERENT;
            countryCodeInNumber += asciiCode;
        }


        String iban = bank.getSwift() + accountNumber + countryCodeInNumber + "00";
        BigInteger ibanNumber = new BigInteger(iban);
        BigInteger mod = ibanNumber.mod(MOD_FACTOR);

        int subtract = MINUEND.subtract(mod).intValue();
        if (subtract >= 10){
            controlSum = "" + subtract;
        } else {
            controlSum = "0" + subtract;
        }

        number.setNumber(controlSum + bank.getSwift() + accountNumber);
        number.setIban(bankCountry + number.getNumber());
        return number;
    }

    public static boolean checkIban(String iban){

        if (iban.length() != IBAN_REQUIRED_LENGTH){
            return false;
        }

        String country = iban.substring(0, 2);
        String controlSum = iban.substring(2, 4);

        String countryCodeInNumber = "";
        for (char ch : country.toCharArray()){
            int asciiCode = ch;
            asciiCode += AccountNumberHelper.ASCII_DIFFERENT;
            countryCodeInNumber += asciiCode;
        }


        String tmpIban = iban.substring(4, iban.length()) + countryCodeInNumber + controlSum;
        BigInteger ibanNumber = new BigInteger(tmpIban);
        int mod = ibanNumber.mod(MOD_FACTOR).intValue();

        if (mod == 1){
            return true;
        } else {
            return false;
        }
    }

}
