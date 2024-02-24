package com.example.onlinebankingsystem.dao;

import com.example.onlinebankingsystem.entity.Customer;
import com.example.onlinebankingsystem.exception.CustomerException;

public interface CustomerDao {
	
	/*methods always start with smallletters*/

	public Customer loginCustomer(String customerUserName,String customerPassword,int customerAccountNumber) throws CustomerException; 
	
	public int viewBalance(int customerAccountNumber) throws CustomerException;  
		
	public int deposite(int customerAccountNumber,int amount ) throws CustomerException;

	public int withdraw (int customerAccountNumber, int amount) throws CustomerException;
	
	public int transferMoney(int customerAccountNumber, int amount, int customerAccountNumber2) throws CustomerException;
}
