package training.supportbank;

import java.util.Date;

public class Transaction {
	private Date date;
	private String from;
	private String to;
	private String narrative;
	private Float amount;
	
	//Constructor
	public Transaction(Date date, String from, String to, String narrative, Float amount) {
		this.date = date;
		this.from = from;
		this.to = to;
		this.narrative = narrative;
		this.amount = amount;
	}
	
	// Getters
	public Date getDate() {
		return date;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public String getNarrative() {
		return narrative;
	}
	public Float getAmount() {
		return amount;
	}
	public String getTransactionEntry() {
		return (date + ": £" + amount + " From " + from + " to " + to + " for " + narrative);
	}

}
