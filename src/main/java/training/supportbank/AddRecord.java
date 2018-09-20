package training.supportbank;

import java.util.HashMap;

public class AddRecord {
	private String name;
	private String date;
	private String narrative;
	private Float amount;

	public AddRecord (String name, String date, String narrative, String amount) {
		super();
		this.name = name;
		this.date = date;
		this.narrative = narrative;
		this.amount = Float.parseFloat(amount);
	}
	
	public static HashMap AddRecord(String name, String date, String narrative, String amount) {
		return null;
		
	}
	

}
