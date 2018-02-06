package com.madar.madardemo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 8/29/2017.
 */

public class BankAccountInfo {

    @SerializedName("BankID")
    private int bankId;

    @SerializedName("Account_Number")
    private String accountNumber;

    @SerializedName("AccountHolderName")
    private String accountHolderName;

    public BankAccountInfo(int bankId, String accountNumber, String accountHolderName) {
        this.bankId = bankId;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
}
