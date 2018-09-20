package training.supportbank;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException; 
import java.util.ArrayList; 
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	
//	public static HashMap<String, HashMap<String, Float>> AddRecord(String to, String from, String date, String narrative, String amount,  HashMap<String, HashMap<String, Float>> accounts, HashMap<String, ArrayList<String>> transactions) {
//    	//Add new name and new hashmap list
//    	HashMap<String, Float> balanceList = new HashMap<String, Float>() {{
//    		put("Owed", Float.parseFloat(amount));
//    		put("Owes", 0.00f);
//    		put("Balance", Float.parseFloat(amount));
//    	}};
//    	accounts.put(to,balanceList);
//    	//Add new array and transaction
//    	ArrayList<String> transactionList = new ArrayList<>();
//    	transactionList.add("Is owed £"+amount+" by "+from+" for "+narrative+", "+date);
//    	transactions.put(to, transactionList);
//		return accounts, transactions;
//    	
//	}

    public static void main(String args[]) throws IOException {
        // Your code here!
        System.out.println("Test!");
        

        
//        String file = "C:\\Users\\fgajewski\\Desktop\\Java Training\\SupportBank\\Transactions2014.csv";
//        //List<String[]> content = new ArrayList<>();
//        HashMap<String, List<String>> content = new HashMap<>();
//        BufferedReader br = new BufferedReader(new FileReader(file));
        
        String file = ".\\Transactions2014.csv";
        Reader reader = Files.newBufferedReader(Paths.get(file));
        //CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        // Reading all records at once into memory
        //List<CSVRecord> csvRecords = csvParser.getRecords();
        //System.out.println(csvRecords);
        

        // CREATING EMPTY ACCOUNTS
        HashMap<String, HashMap<String, Float>> accounts = new HashMap<>();
        HashMap<String, List<String>> transactions = new HashMap<>();

        for (CSVRecord csvRecord : csvParser) {
            // Accessing values by the names assigned to each column
            String date = csvRecord.get("Date");
            String from = csvRecord.get("From");
            String to = csvRecord.get("To");
            String narrative = csvRecord.get("Narrative");
            Float amount = Float.parseFloat(csvRecord.get("Amount"));
            
            // Looking at From - this person OWES money
            if(accounts.containsKey(from)) {
            	// Account present - take away amount
            	accounts.get(from).put("Owes",  (accounts.get(from).get("Owes")+amount));
            	accounts.get(from).put("Balance",  (accounts.get(from).get("Balance")-amount));
            	//Add transaction
            	transactions.get(from).add("Owes £"+amount+" to "+to+" for "+narrative+", "+date);
            }
            else {
            	//Add new name and new hashmap list
            	HashMap<String, Float> balanceList = new HashMap<String, Float>() {{
            		put("Owed", 0.00f);
            		put("Owes", amount);
            		put("Balance", amount);
            	}};
            	accounts.put(from,balanceList);
            	//Add new array and transaction
            	ArrayList<String> transactionList = new ArrayList<>();
            	transactionList.add("Owes £"+amount+" to "+to+" for "+narrative+", "+date);
            	transactions.put(from, transactionList);
            }


            // Looking at To - this person is OWED money
            if(accounts.containsKey(to)) { // To person is present in the account list
            	// Account present - take away amount
            	accounts.get(to).put("Owed",  (accounts.get(to).get("Owed")+amount));
            	accounts.get(to).put("Balance",  (accounts.get(to).get("Balance")+amount));
            	//Add transaction
            	transactions.get(to).add("Is owed £"+amount+" by "+from+" for "+narrative+", "+date);
            }
            else {  // To person is NOT present in the account list
            	//Add new name and new hashmap list
            	HashMap<String, Float> balanceList = new HashMap<String, Float>() {{
            		put("Owed", amount);
            		put("Owes", 0.00f);
            		put("Balance", amount);
            	}};
            	accounts.put(to,balanceList);
            	//Add new array and transaction
            	ArrayList<String> transactionList = new ArrayList<>();
            	transactionList.add("Is owed £"+amount+" by "+from+" for "+narrative+", "+date);
            	transactions.put(to, transactionList);
            }
            
            
            
            
           
            

//            // OBSELETE - printing each row from CSV
//            System.out.println(csvRecord);
//            System.out.println("Record No - " + csvRecord.getRecordNumber());
//            System.out.println("---------------");
//            System.out.println("Date : " + date);
//            System.out.println("From : " + from);
//            System.out.println("To : " + to);
//            System.out.println("Narrative : " + narrative);
//            System.out.println("Amount : " + amount);
//            System.out.println("---------------\n\n");
        } //End of For Loop
        
        
        
//        // print out hashMaps
//        System.out.println(accounts.entrySet());
//        
//        for (String key : transactions.keySet()) {
//        	System.out.println("--------------");
//        	System.out.println(key);
//        	System.out.println(accounts.get(key));        	
//        	transactions.get(key).stream().forEach(System.out::println);
//        	System.out.println("--------------\n\n");
//        }
//        

        
        
        

        
        
        
//        -------------WHILE LOOP ------------------
        String userEntry = "";
        while(!(userEntry.equals("exit") || userEntry.equals("Exit") || userEntry.equals("e"))) {
    		Scanner scanner = new Scanner(System.in);  // Reading from System.in
    		System.out.println("What to do?\nList All\nList Account\nExit\n>>>>  ");
    		userEntry = scanner.nextLine();
    		System.out.println("User Choice >>> " + userEntry);
    		
//    		Pattern p = Pattern.compile("(\\S+(@\\S+))");
//    		Matcher m = p.matcher(userEntry);
    		
    		if(userEntry.equals("List All")) {
    			//read accounts and balance only
    			for (String key : accounts.keySet()) {
    	        	System.out.println("--------------");
    	        	System.out.println(key);
    	        	System.out.println("Owed £" + String.format("%.02f", accounts.get(key).get("Owed")));
    	        	System.out.println("Owes £" + String.format("%.02f", accounts.get(key).get("Owes")));
    	        	System.out.println("Balance £" + String.format("%.02f", accounts.get(key).get("Balance")));
    	        	System.out.println("--------------\n");
    			}
    		}
    		
    		while(userEntry.equals("List Account")) {
    			System.out.println("Which account to display?");;
				for(String x : accounts.keySet()) {
    				System.out.println(">>> " + x);
    			}
    			userEntry = scanner.nextLine();
    			if(transactions.containsKey(userEntry)) {
    				System.out.println("\n\n" + userEntry + "\n------------");
    				System.out.println("Owed £" + String.format("%.02f", accounts.get(userEntry).get("Owed")));
    	        	System.out.println("Owes £" + String.format("%.02f", accounts.get(userEntry).get("Owes")));
    	        	System.out.println("Balance £" + String.format("%.02f", accounts.get(userEntry).get("Balance")));
    	        	System.out.println("\n--------------\n Transactions:");
    	        	for(String x : transactions.get(userEntry)) {
    	        		System.out.println(">>> " + x);
    	        	}
    	        	System.out.println("\n\n");
    			}
    			else {
    				System.out.println("No such account");
    				userEntry = "List Account";
    			}
    		}
        }
    		
    }
    

}

