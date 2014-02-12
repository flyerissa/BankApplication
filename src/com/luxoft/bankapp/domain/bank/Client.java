package com.luxoft.bankapp.domain.bank;

public class Client {
	private String name;
    private Gender gender;

	//private List<com.luxoft.bankapp.domain.bank.Account> accounts = new ArrayList<com.luxoft.bankapp.domain.bank.Account>();
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

    public Account addAccount(String accountType, float sum, float overdraft){

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
	//private float initialOverdraft;
	
	/** public com.luxoft.bankapp.domain.bank.Client(){
		initialOverdraft = 300;
	}
	
	public com.luxoft.bankapp.domain.bank.Client(float initialOverdraft){
		this.initialOverdraft = initialOverdraft;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActiveAccount(com.luxoft.bankapp.domain.bank.Account a){
		activeAccount = a;
	}
	
	public float getBalance(){
		float sum = 0;
		for(com.luxoft.bankapp.domain.bank.Account a : accounts){
			sum += a.getBalance();
			}
		return sum;
	}
	
	public List<com.luxoft.bankapp.domain.bank.Account> getAccounts(){
		return accounts;
	}
	
	
	
	public void deposit(float x){
		if(activeAccount != null)
			activeAccount.deposit(x);
		else
			System.out.println("Please create account at first.");
	}
	
	
	public void withdraw(float x){
		if(activeAccount != null)
			activeAccount.withdraw(x);
		else
			System.out.println("Please create account at first.");
	}
	
	
	public com.luxoft.bankapp.domain.bank.Account createAccount(String accountType){
		com.luxoft.bankapp.domain.bank.Account account = null;
		if(accountType.equals("C")){
			com.luxoft.bankapp.domain.bank.CheckingAccount account2 = new com.luxoft.bankapp.domain.bank.CheckingAccount();
			account2.setOverdraft(initialOverdraft);
			account = account2;
			accounts.add(account2);
		}
		else if (accountType.equals("S")){
			com.luxoft.bankapp.domain.bank.SavingAccount account3 = new com.luxoft.bankapp.domain.bank.SavingAccount();
			account = account3;
			accounts.add(account3);
		}
		else
			System.out.println("Enter C  - for checking account or S - for saving");
		
		return account;
	}
	
	*/
}
