package training.supportbank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClassTest {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws ParseException {
		LOGGER.log(Level.INFO, "New run");
		
//// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TESTING %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//		Date date = new Date();
//		Transaction transaction1 = new Transaction(date, "Bob", "Mike A", "coffee", 10.0f);
//		ArrayList<Account> ledger = new ArrayList<Account>();
//		Account newAcc = new Account("Mike A");
//		newAcc.addTransaction(transaction1);
//		ledger.add(newAcc);
//		System.out.println(Arrays.toString(ledger.get(0).getBalanceSheet()));
//		Transaction transaction2 = new Transaction(date, "ross", "boss", "salary", 100.0f);
//		newAcc.addTransaction(transaction2);
//		
//		
//		System.out.println(Arrays.toString(ledger.get(0).getBalanceSheet()));
//		for(String x : ledger.get(0).getBalanceSheet()) {
//			System.out.println(x);
//		}
////		System.out.println(ledger.get(0).getBalanceSheet()[1]);
//		
//		for(Transaction item : ledger.get(0).getTransactionList()) {
//			System.out.println(item.getTransactionEntry());
//		}
//// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TESTING %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ MAIN @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@			

		// New list of accounts
		Ledger ledger = new Ledger();
		//HashMap<String, Account> ledger = new HashMap<>();

		// ------ DATE FORMAT -------------
		String pattern = "dd/mm/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		// Read CSV (This could be turned into a single line
		//String CSVFile = ".\\Transactions2014.csv";
        ArrayList<String> CSVfileList = new ArrayList<String>();
        CSVfileList.add()
//        (".\\Transactions2014.csv", ".\\DodgyTransactions2015.csv");
//        CSVFileList
//		
		CSVReader readFile = new CSVReader(CSVFile);
		List<CSVRecord> records = readFile.readCSV();

		for(CSVRecord record : records) {
			// Get data extracted from the records, and catch any exceptions
			Date date = simpleDateFormat.parse("00/00/0000");
			try {
				date = simpleDateFormat.parse(record.get("Date"));
			}
			catch (ParseException ParseE){
				ParseE.printStackTrace();
				LOGGER.log(Level.ERROR, "Could not get Date from: " + record.get("Date"));
				continue;
			}
			String from = record.get("From");
			String to = record.get("To");
			String narrative = record.get("Narrative");
			Float amount = 0.00f;
			try {
				amount = Float.parseFloat(record.get("Amount"));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				LOGGER.log(Level.ERROR, "Could not get float from: " + record.get("Amount"));
				continue;
			}

			// Create a transaction object
			Transaction transaction = new Transaction(date, from, to, narrative, amount);
			// Process the records
			ledger.put(transaction);
		}
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@			
//			
//			bigDecimal data type
//			Optional - private static Optional<transaction> processLice(String ...)
//			ioDate - for the XML
			
		
	}
}

