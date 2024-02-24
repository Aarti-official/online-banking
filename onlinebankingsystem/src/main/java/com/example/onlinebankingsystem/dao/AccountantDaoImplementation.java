package com.example.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.onlinebankingsystem.databaseconnection.DatabaseConnection;
import com.example.onlinebankingsystem.entity.Accountant;
import com.example.onlinebankingsystem.entity.Customer;
import com.example.onlinebankingsystem.exception.AccountantException;
import com.example.onlinebankingsystem.exception.CustomerException;

public class AccountantDaoImplementation implements AccountantDao {

	@Override
	public Accountant LoginAccountant(String accountantUsername, String accountantPassword) throws AccountantException {

		Accountant acc = null;
		try (Connection conn = DatabaseConnection.provideconnection()) {

			PreparedStatement ps = conn.prepareStatement(
					"Select * from accountant  where accountantUsername = ? and accountantPassword = ? ");

			ps.setString(1, accountantUsername);
			ps.setString(2, accountantPassword);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String n = rs.getString("accountantUsername");
				String e = rs.getString("accountantEmail");
				String p = rs.getString("accountantPassword");

				acc = new Accountant(n, e, p);
			}

		} catch (SQLException e) {
			throw new AccountantException("Invalid Username and Password");
		}

		return acc;
	}

	@Override
	public int addCustomer(String customerName, String customerMail, String customerPassword, String customerMobile,
			String customerAddress) throws CustomerException {

		int cid = -1;

		try (Connection conn = DatabaseConnection.provideconnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"insert into customerinformation (customerName,customerMail,customerMobile,customerPassword,customerAddress) values (?,?,?,?,?)");

			ps.setString(1, customerName);
			ps.setString(2, customerMail);
			ps.setString(3, customerMobile);
			ps.setString(4, customerPassword);
			ps.setString(5, customerAddress);

			int x = ps.executeUpdate();

			if (x > 0) {

				PreparedStatement ps2 = conn.prepareStatement(
						"select cid from customerinformation where customerMail = ? and  customerMobile = ?");
				ps2.setString(1, customerMail);
				ps2.setString(2, customerMobile);

				ResultSet rs = ps2.executeQuery();

				if (rs.next()) {

					cid = rs.getInt("cid");
					System.out.println("Customer Added Successfully!!!!");
				} else {
					System.out.println("inserted data incorrect please try again ");
				}
			}
		}

		catch (Exception e) {
			System.out.println("SQL Exception!!!");
//				e.printStackTrace();
		}

		return cid;
	}

	@Override
	public String addAccount(int customerBalance, int cid) throws CustomerException {

		String message = null;
		try (Connection conn = DatabaseConnection.provideconnection()) {
			PreparedStatement ps = conn.prepareStatement("insert into account(customerBalance,cid) values (?,?)");

			ps.setInt(1, customerBalance);
			ps.setInt(2, cid);

			int x = ps.executeUpdate();

			if (x > 0) {
				System.out.println("Account Added Successfully!!!");
			} else {
				System.out.println("Account Not Added Successfully!!!");
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception!!!");
//			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String updateCustomer(int customerAccountNumber, String customerAddress) throws CustomerException {

		String message = null;

		try (Connection conn = DatabaseConnection.provideconnection()) {

			PreparedStatement ps = conn.prepareStatement(
					"update customerinformation i inner join account a on i.cid = a.cid and a.customerAccountNumber = ? set i.customerAddress = ?");

			ps.setInt(1, customerAccountNumber);
			ps.setString(2, customerAddress);
			int x = ps.executeUpdate();

			if (x > 0) {
				System.out.println("Address Updated Successfully.....");
			} else {
				System.out.println("Address not updated.....");
				System.out.println("------------------------------------------------------------------------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = e.getMessage();
		}

		return message;
	}

	@Override
	public String deleteCustomer(int customerAccountNumber) throws CustomerException {

		String message = null;

		try (Connection conn = DatabaseConnection.provideconnection()) {

			PreparedStatement ps = conn.prepareStatement(
					"delete i from customerinformation i inner join account a on i.cid = a.cid where a.customerAccountNumber = ?");

			ps.setInt(1, customerAccountNumber);

			int x = ps.executeUpdate();

			if (x > 0) {

				System.out.println("Account Deleted Successfully!!!!");
			} else {
				System.out.println("\n Deletion Failed....Account not found!!! ");
				System.out.println("--------------------------------------------------------------------");
			}

		} catch (SQLException e) {

			e.printStackTrace();
			message = e.getMessage();

		}

		return message;
	}

	@Override
	public Customer viewCustomer(String customerAccountNumber) throws CustomerException {

		Customer cus = null;

		try (Connection conn = DatabaseConnection.provideconnection()) {

			PreparedStatement ps = conn.prepareStatement(
					"select * from customerinformation i inner join account a on a.cid = i.cid where customerAccountNumber = ?");

			ps.setString(1, customerAccountNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int ac = rs.getInt("customerAccountNumber");
				String n = rs.getString("customerName");
				int b = rs.getInt("customerBalance");
				String m = rs.getString("customerPassword");
				String pw = rs.getString("customerMail");
				String mo = rs.getString("customerMobile");
				String addr = rs.getString("customerAddress");

				cus = new Customer(ac, n, b, m, pw, mo, addr);

			} else {
				throw new CustomerException("Invalid Account Number...");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return cus;
	}

	@Override
	public Customer viewAllCustomer() throws CustomerException {

		Customer cu = null;

		try (Connection conn = DatabaseConnection.provideconnection()) {
			PreparedStatement ps = conn
					.prepareStatement("select * from customerinformation i inner join account a on a.cid = i.cid ");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				int acc = rs.getInt("customerAccountNumber");
				String cn = rs.getString("customerName");
				int cb = rs.getInt("customerBalance");
				String cm = rs.getString("customerPassword");
				String cpw = rs.getString("customerMail");
				String cmo = rs.getString("customerMobile");
				String caddr = rs.getString("customerAddress");

				System.out.println(
						"*****************************Account Information************************************");
				System.out.println("Account Number: " + acc);
				System.out.println("Customer Name: " + cn);
				System.out.println("Customer Mail: " + cpw);
				System.out.println("Customer Mobile: " + cmo);
				System.out.println("Customer Password: " + cm);
				System.out.println("Customer Address:  " + caddr);
				System.out.println("Customer Balance: " + cb);
				System.out.println("************************************************************************************");

				cu = new Customer(acc, cn, cb, cm, cpw, cmo, caddr);
			}

		} catch (SQLException e) {
			throw new CustomerException("Invalid Account Number");
		}

		return cu;
	}

}
