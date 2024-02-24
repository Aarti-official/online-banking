package com.example.onlinebankingsystem.dao;

import com.example.onlinebankingsystem.entity.Accountant;
import com.example.onlinebankingsystem.entity.Customer;
import com.example.onlinebankingsystem.exception.AccountantException;
import com.example.onlinebankingsystem.exception.CustomerException;

public interface AccountantDao {
	
	public Accountant LoginAccountant(String accountantUsername , String accountantPassword) throws AccountantException;
	
	public int addCustomer(String customerName, String customerMail, String customerPassword, 
			String customerMobile,  String customerAddress )throws CustomerException;
	
	public String addAccount(int customerBalance,int cid ) throws CustomerException;
	
	public String updateCustomer(int customerAccountNumber, String customerAddressString) throws CustomerException;

	public String deleteCustomer(int customerAccountNumber ) throws CustomerException;
	
	public Customer viewCustomer(String customerAccountNumber) throws CustomerException;

	public Customer viewAllCustomer() throws CustomerException;
}
