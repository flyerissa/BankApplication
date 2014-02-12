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
	

}
