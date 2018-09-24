package training.supportbank;

import java.util.ArrayList;
import java.util.List;

public class Account {

	private Float owed;
	private Float owes;
	private Float balance;
	private ArrayList<Transaction> transactionList;	
	private String accName;

	public Account(String accName){
		//		ArrayList<String> balanceSheet = new ArrayList<String>();
		//		ArrayList<String> transactionList = new ArrayList<String>();
		//		ArrayList<ArrayList<String>> account= new ArrayList<ArrayList<String>>();
		//		account.add(1,balanceSheet);
		//		account.add(2,transactionList);
		//		return account;
		this.owed = 0.00f;
		this.owes = 0.00f;
		this.balance = 0.00f;
		//this.balanceSheet = new ArrayList<Float>();
		this.transactionList = new ArrayList<Transaction>();
		this.accName = accName;
	}

	public void addTransaction(Transaction transaction) {
		transactionList.add(transaction);
		
		if(transaction.getTo().equals(getAccName())) { // If true then amount is added to this account - Owed
			this.owed = this.owed + transaction.getAmount();
			this.balance = this.balance + transaction.getAmount();
		}
		else { // If false then amount is taken away from this account - Owes
			this.owes = this.owes + transaction.getAmount();
			this.balance = this.balance - transaction.getAmount();
		}
	}

	public Float getOwed() {
		return owed;
	}
	
	public Float getOwes() {
		return owes;
	}
	
	public Float getBalance() {
		return balance;
	}

	public String getAccName() {
		return accName;
	}
	
	public String[] getBalanceSheet() {
		String[] balanceSheet = new String[3];
		balanceSheet[0] = "Owed £" + Float.toString(getOwed());
		balanceSheet[1] = "Owes £" + Float.toString(getOwes());
		balanceSheet[2] = "Balance £" + Float.toString(getBalance());
		return balanceSheet;
	}
	
	public ArrayList<Transaction> getTransactionList(){
		return transactionList;
	}
}

//Acount is assigned to a name and stored in a ledger
//Acount stores the (owed/owes/balance) and (transaction list) - a list of lists