package com.example.onlinebankingsystem;

import java.util.Scanner;
import com.example.onlinebankingsystem.dao.*;
import com.example.onlinebankingsystem.entity.Accountant;
import com.example.onlinebankingsystem.entity.Customer;
import com.example.onlinebankingsystem.exception.AccountantException;
import com.example.onlinebankingsystem.exception.CustomerException;

public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		boolean f = true;

		while (f) {

			System.out.println(
					"**************************Welcome to Online Banking System************************************");
			System.out.println(
					"______________________________________________________________________________________________\n\n");

			System.out.println("1. ADMIN LOGIN PORTAL \n" + "2. CUSTOMER");

			System.out.println("Choose your option");
			int choice = sc.nextInt();
			System.out.println(
					"______________________________________________________________________________________________\n\n");

			switch (choice) {

			case 1:

				System.out.println(
						"******************************Fill Admin login creditials*************************************");

				System.out.println("Enter Username : ");
				String username = sc.next();

				System.out.println("Enter Password");
				String pass = sc.next();

				AccountantDao ad = new AccountantDaoImplementation();

				try {
					Accountant a = ad.LoginAccountant(username, pass);
					if (a == null) {
						System.out.println("\nWrong Credentials");
						System.out.println(
								"______________________________________________________________________________________________");
						break;
					}

					System.out.println("\nLogin successfully !!!!");
					System.out.println(
							"_______________________________________________________________________________________________");

					System.out.println("\n Welcome " + a.getAccountantUsername() + " to Admin Section !!!!\n");

					boolean y = true;

					while (y) {

						System.out.println(
								"-------------------------------------------------------------------------------------\n"
										+ "1.Add New Customer Account\n" + "2.Update Existing Account Details\n"
										+ "3.Delete Account\n" + "4.View Account Details\n"
										+ "5.View All Customer List\n" + "6.Logout");

						int x = sc.nextInt();

						if (x == 1) {

							System.out.println("----------------------New Account-------------------");

							System.out.println("Enter Customer Name: ");
							String cname = sc.next();

							System.out.println("Enter Account Opening Balance");
							int balance = sc.nextInt();

							System.out.println("Enter Customer Mail: ");
							String mail = sc.next();

							System.out.println("Enter Customer Mobile: ");
							String mobile = sc.next();

							System.out.println("Enter Customer password: ");
							String cpass = sc.next();

							System.out.println("Enter customer Address: ");
							String add = sc.next();

							int s1 = -1;
							try {
								s1 = ad.addCustomer(cname, mail, cpass, mobile, add);

								try {
									ad.addAccount(balance, s1);
								} catch (CustomerException e) {
									e.printStackTrace();
								}

							} catch (CustomerException e) {
								System.out.println(e.getMessage());
							}
							System.out.println("--------------------------------------------------------");
						}

						if (x == 2) {

							System.out.println("------------------Update Customer Address--------------------");
							System.out.println("Enter Customer Account Number ");
							int cusacc = sc.nextInt();

							System.out.println("Enter New Address");
							String nadd = sc.next();

							try {

								String mes = ad.updateCustomer(cusacc, nadd);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						if (x == 3) {

							System.out.println("---------------------Deletion of Account----------------------");
							System.out.println("Enter Account Number ");
							int dacc = sc.nextInt();

							String d = null;
							try {
								d = ad.deleteCustomer(dacc);
							} catch (CustomerException e) {
								e.printStackTrace();
							}

							if (d != null) {
								System.out.println(d);
							}
						}

						if (x == 4) {

							System.out.println("Enter Account Number");
							String ai = sc.next();
							System.out.println(
									"___________________________________________________________________________");
							try {

								Customer cus = ad.viewCustomer(ai);

								if (cus != null) {

									System.out.println(
											"******************************************************************");
									System.out.println(
											"-------------------Account Information----------------------------");
									System.out.println("Account Number: " + cus.getCustomerAccountNumber());
									System.out.println("Customer Name: " + cus.getCustomerName());
									System.out.println("Customer Mail: " + cus.getCustomerMail());
									System.out.println("Customer Mobile: " + cus.getCustomerMobile());
									System.out.println("Customer Password: " + cus.getCustomerPassword());
									System.out.println("Customer Address:  " + cus.getCustomerAddress());
									System.out.println("Customer Balance: " + cus.getCustomerBalance());
								} else {
									System.out.println("Account Does not Exist...............");
									System.out.println(
											"------------------------------------------------------------------------");
								}

							} catch (CustomerException e) {
								System.out.println("Account Does not Exist...............");
							}

						}

						if (x == 5) {

							try {
								System.out.println(
										"*************************************************************************");
								Customer cus = ad.viewAllCustomer();

							} catch (CustomerException e) {
								e.printStackTrace();
							}

						}

						if (x == 6) {

							System.out.println(
									"------------------------------------Account Logout Successfully------------------------------\n\n");
							y = false;
						}

					}
					break;
				}

				catch (AccountantException e) {
					System.out.println(e.getMessage());
				}

				break;

			// case 2 (Customer) starts here

			case 2:
				System.out.println(
						"<<<-------------------------------------CUSTOMER LOGIN------------------------------------>>>");
				System.out.println(
						"______________________________________________________________________________________________");

				System.out.println("Enter User Name: ");
				String uname = sc.next();

				System.out.println("Enter Password: ");
				String cpass = sc.next();

				System.out.println("Enter Account Number: ");
				int cacc = sc.nextInt();

				CustomerDao cd = new CustomerDaoImplementation();

				try {

					Customer cus = cd.loginCustomer(uname, cpass, cacc);

					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Welcome " + cus.getCustomerName()
							+ " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");

					boolean m = true;

					while (m) {

						System.out.println(
								">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n"
										+ "1.View Balance\n" + "2.Deposite Money\n" + "3.Withdraw Money\n"
										+ "4.Transfer Money\n" + "5.Logout");

						int select = sc.nextInt();

						if (select == 1) {

							System.out.println(
									"---------------------------------- Balance Section ---------------------------\n ");
							System.out.println("\nYour Balance is: " + cd.viewBalance(cacc));
							System.out.println(
									"\n_________________________________________________________________________________\n\n");
						}

						if (select == 2) {
							System.out.println(
									"---------------------------------- Deposite Section ---------------------------\n ");

							System.out.println("Enter Amount : ");
							int amt = sc.nextInt();

							cd.deposite(cacc, amt);
							System.out.println("\nYour Balance After Deposite is: " + cd.viewBalance(cacc) + "\n");
							System.out.println(
									"_________________________________________________________________________________\n\n");
						}

						if (select == 3) {
							System.out.println(
									"---------------------------------- Withdrawal Section ---------------------------\n ");
							System.out.println("Enter amount: ");
							int wamt = sc.nextInt();
							try {

								cd.withdraw(cacc, wamt);
								System.out
										.println("\nYour Balance After Withdrawal is: " + cd.viewBalance(cacc) + "\n");
								System.out.println(
										"_________________________________________________________________________________\n\n");

							} catch (CustomerException e) {
								System.out.println(e.getMessage());
							}

						}

						if (select == 4) {
							System.out.println(
									"---------------------------------- Transfer Section ---------------------------\n ");
							System.out.println("Enter Amount: ");
							int tamt = sc.nextInt();
							System.out.println("Enter Account Number To Trnasfer Money:  ");
							int tacc = sc.nextInt();

							try {
								cd.transferMoney(cacc, tamt, tacc);
								System.out.println("Amount Transfer Successfully!!!\n");
								System.out.println(
										"_________________________________________________________________________________\n\n");

							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

						}

						if (select == 5) {
							System.out.println(
									"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Account Logout Successfully ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
							System.out.println(
									"------------------------------- Thank You For Using Our Services -------------------------\n\n");
							m = false;
						}

					}
					break;

				} catch (CustomerException e) {
					System.out.println(e.getMessage());
				}

			default:
				System.out.println("Enter Valid Input!!!!\n\n");
			}

		}

	}
}
