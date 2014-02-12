package com.luxoft.bankapp.domain.bank;

public class Client {
	private String name;
    private Gender gender;


	private Account activeAccount;

    public Client(String name, Gender gender){
        this.name = name;
        this.gender = gender;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account addAccount(String accountType, double sum, double overdraft){

        if(accountType.equals("C")){

            //account2.setOverdraft(initialOverdraft);
            activeAccount = new CheckingAccount(sum, overdraft);
            //accounts.add(account2);
        }
        else if (accountType.equals("S")){

            activeAccount = new SavingAccount(sum);
            //accounts.add(account3);
        }
        else
            System.out.println("Enter C  - for checking account or S - for saving");

        return activeAccount;
    }

    public void getClientSalutation(){
        switch (gender){
            case MALE:
                System.out.println("MR " + name);
                break;
            case FEMALE:
                System.out.println("Ms " + name);
                break;
        }

    }

}