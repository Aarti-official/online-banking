package com.example.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.onlinebankingsystem.databaseconnection.DatabaseConnection;
import com.example.onlinebankingsystem.entity.Customer;
import com.example.onlinebankingsystem.exception.CustomerException;

public class CustomerDaoImplementation implements CustomerDao {

	@Override
	public Customer loginCustomer(String customerUserName, String customerPassword, int customerAccountNumber)
			throws CustomerException {

		Customer customer = null;

		try (Connection conn = DatabaseConnection.provideconnection()) {

			PreparedStatement ps = conn.prepareStatement(
					"select * from customerinformation i inner join account a on i.cid = a.cid where customerName = ? and customerPassword = ? and customerAccountNumber = ?");

			ps.setString(1, customerUserName);
			ps.setString(2, customerPassword);
			ps.setInt(3, customerAccountNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int ac = rs.getInt("customerAccountNumber");
				String cname = rs.getString("customerName");
				int cb = rs.getInt("customerBalance");
				String ce = rs.getString("customerMail");
				String cp = rs.getString("customerPassword");
				String cm = rs.getString("customerMobile");
				String cadd = rs.getString("customerAddress");

				customer = new Customer(ac, cname, cb, ce, cp, cm, cadd);

			} else {
				throw new CustomerException("Invalid Customer Id!!!!!! please try again");
			}

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		} catch (Exception e) {
			System.out.println(
					"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Invalid Customer Details~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
		}

		return customer;
	}

	@Override
	public int viewBalance(int customerAccountNumber) throws CustomerException {

		int b = -1;
		try (Connection conn = DatabaseConnection.provideconnection()) {
			PreparedStatement ps = conn
					.prepareStatement("select customerBalance from account where customerAccountNumber = ? ");

			ps.setInt(1, customerAccountNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				b = rs.getInt("customerBalance");
			}

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		return b;
	}

	@Override
	public int deposite(int customerAccountNumber, int amount) throws CustomerException {

		int b = -1;

		try (Connection conn = DatabaseConnection.provideconnection()) {

			PreparedStatement ps = conn.prepareStatement(
					"update  account set customerBalance = customerBalance+ ? where customerAccountNumber = ?");

			ps.setInt(1, amount);
			ps.setInt(2, customerAccountNumber);

			int rs = ps.executeUpdate();

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		return b;
	}

	@Override
	public int withdraw(int customerAccountNumber, int amount) throws CustomerException {

		int vb = viewBalance(customerAccountNumber);

		if (vb > amount) {
			try (Connection conn = DatabaseConnection.provideconnection()) {

				PreparedStatement ps = conn.prepareStatement(
						"update account set customerBalance = customerBalance - ? where customerAccountNumber = ?");

				ps.setInt(1, amount);
				ps.setInt(2, customerAccountNumber);

				int rs = ps.executeUpdate();

			} catch (SQLException e) {
				throw new CustomerException(e.getMessage());
			}

		} else {
			throw new CustomerException("\n~~~~~~~~~~~~~~~~~~~~~~~~Insufficient Balance!!!~~~~~~~~~~~~~~~~~~\n");
		}

		return vb + amount;
	}

	@Override
	public int transferMoney(int customerAccountNumber, int amount, int customerAccountNumber2)
			throws CustomerException {
		int vb = viewBalance(customerAccountNumber);
		
		
		if (vb>amount && checkAccount(customerAccountNumber2)) {
			
			int wit = withdraw(customerAccountNumber, amount);
			int dep = deposite(customerAccountNumber2, amount);
		}else {
			throw new CustomerException("Insufficient Balance!!!");
		}
		
		
		return 0;
	}

	private boolean checkAccount(int customerAccountNumber) {
		
		try(Connection conn = DatabaseConnection.provideconnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from account where customerAccountNumber = ?  ");
			
			ps.setInt(1, customerAccountNumber);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		return false;
	}

}
