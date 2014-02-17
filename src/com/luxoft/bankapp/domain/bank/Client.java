package com.luxoft.bankapp.domain.bank;

//4th exercise
public class Client {
    private String name;
    private Gender gender;
    private Account activeAccount;

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
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

   /* public static void main(String[] args) {
        Client cl1 = new Client("JJ KK", Gender.MALE);
        Client cl2 = new Client("JJ KK", Gender.FEMALE);
        Bank bank = new Bank();
        if(cl1.equals(cl2)){
            System.out.println("equals");
        }
        else System.out.println("false");

    }
*/
}
