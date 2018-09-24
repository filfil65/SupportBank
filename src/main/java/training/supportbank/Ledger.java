package training.supportbank;

import java.util.HashMap;

public class Ledger {
	private HashMap<String, Account> ledger;
	
	//Constructor
	public Ledger() {
		ledger = new HashMap<String, Account>();
	}
	
	//Put new transaction
	public void put(Transaction transaction) {
		if(ledger.containsKey(transaction.getTo())) {
			ledger.get(transaction.getTo()).addTransaction(transaction);
		}
		else {
    		Account newAcc = new Account(transaction.getTo());
    		//add transaction
    		newAcc.addTransaction(transaction);
    		// add to ledger
    		ledger.put(transaction.getTo(), newAcc);
		}
		
    	if(ledger.containsKey(transaction.getFrom())) { // if True, add transaction
    		// add transaction to From account
    		ledger.get(transaction.getFrom()).addTransaction(transaction);
    	}
    	else {
    		//create an account
    		Account newAcc = new Account(transaction.getFrom());
    		//add transaction
    		newAcc.addTransaction(transaction);
    		// add to ledger
    		ledger.put(transaction.getFrom(), newAcc);
    	}
	}
	public ListAll
}
