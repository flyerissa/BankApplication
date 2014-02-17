package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.BankService;

//4th exercise
public class Client {
    private String name;
    private String surname;
    private Gender gender;
    private Account activeAccount;
    private String email;
    private String fullName;
    private String phone;
    private double overdraft;

    public String getFullName() {
        return fullName;
    }

    public void setFullName() {
        this.fullName = name + " " + surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (BankService.validateEmail(email)) {
            this.email = email;
        }

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (BankService.validatePhone(phone)) {
            this.phone = phone;
        }
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }


    public Client(String fullName, Gender gender) {
        this.fullName = fullName;
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", activeAccount=" + activeAccount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (gender != client.gender) return false;
        if (!name.equals(client.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account addAccount(String accountType, double sum, double overdraft) {

        if (accountType.equals("C")) {
            activeAccount = new CheckingAccount(sum, overdraft);
        } else if (accountType.equals("S")) {
            activeAccount = new SavingAccount(sum);
        } else
            System.out.println("Enter C  - for checking account or S - for saving");

        return activeAccount;
    }

    public void getClientSalutation() {
        switch (gender) {
            case MALE:
                System.out.println("MR " + name);
                break;
            case FEMALE:
                System.out.println("Ms " + name);
                break;
        }

    }


}
