package com.luxoft.bankapp.domain.bank;

import java.util.ArrayList;
import java.util.List;


public class Bank  {
	private List<Client> clients = new ArrayList<Client>();
	private String name;
	
	public Bank(String name){
		this.name = name;
	}


	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Client> getClients(){
		return clients;
	}
	
	/*public void addClient(com.luxoft.bankapp.domain.bank.Client c){
		clients.add(c);
	}
/*
	@Override
	public String printReport() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + "\n");
		for(com.luxoft.bankapp.domain.bank.Client c: clients){
			sb.append("Name: " + c.getName() + "\n");
			sb.append("Balance is: " + c.getBalance() + "\n");
		}
		
		return sb.toString();
	}

    //registerEvent(EventNotificationListener e){}

*/
}
