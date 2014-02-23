package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.Validate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//4th exercise
public class Client implements Comparable {
    //private String name;
    //private String surname;
    private Integer id;
    private Gender gender;
    private Account activeAccount;
    private String email;
    private String fullName;
    private String phone;
    private double overdraft;
    private String city;
    private Set<Account> accounts = new HashSet<Account>();
    private Double balance;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    private Bank bank;


    public void setId(Integer id) {
        this.id = id;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getBalance() {
        Double result = 0.00;
        for (Account account : accounts) {
            result += account.getBalance();
        }
        return result;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (Validate.validateEmail(email)) {
            this.email = email;
        }

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (Validate.validatePhone(phone)) {
            this.phone = phone;
        }
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }


    @Override
    public String toString() {
        return "Client{" +
                "name='" + fullName + '\'' +
                ", activeAccount=" + activeAccount +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (!fullName.equals(client.fullName)) return false;
        if (gender != client.gender) return false;
        if (id != null ? !id.equals(client.id) : client.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + gender.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    /*public Account addAccount(String accountType, double sum, double overdraft) {

        if (accountType.equals("C")) {
            activeAccount = new CheckingAccount(sum, overdraft);
        } else if (accountType.equals("S")) {
            activeAccount = new SavingAccount(sum);
        } else
            System.out.println("Enter C  - for checking account or S - for saving");

        return activeAccount;
    }
*/
    public void addAccountToSet(Account account) {
        if (accounts.size() > 0) {
            for (Account existingAccount : accounts) {
                if (existingAccount.equals(account)) {
                    System.out.println("There is an account in the set already!");
                } else {
                    accounts.add(account);
                }
            }
        } else accounts.add(account);
    }

    public void getClientSalutation() {
        switch (gender) {
            case MALE:
                System.out.println("MR " + fullName);
                break;
            case FEMALE:
                System.out.println("Ms " + fullName);
                break;
        }

    }

    @Override
    public int compareTo(Object o) {
        Client that = (Client) o;
        return this.getBalance().compareTo(that.getBalance());
    }
}
