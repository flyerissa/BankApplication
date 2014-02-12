

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.domain.bank.Gender;


public class BankApplication {


	
	public static void main(String[] args) {

        Bank bank = new Bank("Big com.luxoft.bankapp.domain.bank.Bank");
        Client cl1 = new Client("JJ KK", Gender.MALE);
        Client cl2 = new Client("KK LL", Gender.FEMALE);
        cl1.addAccount("C", 2000);
        cl2.addAccount("S", 1000);
        //bank.addClient(cl1);
        //bank.addClient(cl2);
        modifyBank(bank);
        printBalance(bank);


		
		//initialize();
		//System.out.println(printBankReport());

	}

    public static void modifyBank(Bank bank){
        Client client3 = new Client("OOO", Gender.FEMALE);
        client3.addAccount("C", 5000);
        Account account = client3.getActiveAccount();
        account.deposit(1000);
        account.withdraw(3000);
        //bank.addClient(client3);

    }

    public static void printBalance(Bank bank){
        for(Client c : bank.getClients()){
            System.out.println(c.getActiveAccount().getBalance());
        }
    }
	
	/*public static void initialize(){
		com.luxoft.bankapp.domain.bank.Bank myBank = new com.luxoft.bankapp.domain.bank.Bank("com.luxoft.bankapp.domain.bank.Bank");
		myBank.setName("Big Bang com.luxoft.bankapp.domain.bank.Bank");
		com.luxoft.bankapp.domain.bank.Client client1 = new com.luxoft.bankapp.domain.bank.Client();
		client1.setName("John Do");
		client1.createAccount("C");
		com.luxoft.bankapp.domain.bank.Account acc1 = client1.createAccount("C");
		com.luxoft.bankapp.domain.bank.Account acc2 = client1.createAccount("S");
		
		client1.setActiveAccount(acc1);
		client1.deposit(1000);
		
		client1.setActiveAccount(acc2);
		client1.deposit(200);
				
		com.luxoft.bankapp.domain.bank.Client client2  =  new com.luxoft.bankapp.domain.bank.Client();
		client2.setName("Bob");
		com.luxoft.bankapp.domain.bank.Account acc3 = client2.createAccount("C");
		com.luxoft.bankapp.domain.bank.Account acc4 = client2.createAccount("S");
		client2.setActiveAccount(acc3);
		client2.deposit(600);
		
		client2.setActiveAccount(acc4);
		acc4.deposit(200);
		
		myBank.addClient(client1);
		myBank.addClient(client2);
		banks.add(myBank);
				
	}
	
	
	public static String printBankReport(){
		StringBuilder sb = new StringBuilder();
		for(com.luxoft.bankapp.domain.bank.Bank b : banks){
			sb.append(b.printReport() + "\n");
		}
		return sb.toString();
	}
	
	public  static void modifyBank(com.luxoft.bankapp.domain.bank.Bank bank){
		com.luxoft.bankapp.domain.bank.Client client3 = new com.luxoft.bankapp.domain.bank.Client();
		client3.setName("Genny Do");
		client3.createAccount("C");
		com.luxoft.bankapp.domain.bank.Account acc1 = client3.createAccount("C");
		com.luxoft.bankapp.domain.bank.Account acc2 = client3.createAccount("S");
		
		client3.setActiveAccount(acc1);
		client3.deposit(1000);
		client3.setActiveAccount(acc2);
		client3.deposit(3000);
		client3.setActiveAccount(acc1);
		client3.withdraw(500);
		
	}
	
	public static void test(){
		initialize();
		printBankReport();
		for (com.luxoft.bankapp.domain.bank.Bank b : banks){
			modifyBank(b);
		}
		printBankReport();
	}
  */
}
