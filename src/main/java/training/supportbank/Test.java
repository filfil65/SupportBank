package training.supportbank;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Test {
	
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

	private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String args[]) throws IOException, ParseException, org.json.simple.parser.ParseException {
		LOGGER.log(Level.INFO, "New run");
    	
        String file = ".\\DodgyTransactions2015.csv";
        Reader reader = Files.newBufferedReader(Paths.get(file));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        
        
// ~~~~~~~~~~~~~~~~~~~ JSON PARSING ~~~~~~~~~~~~~~~~~~~~~~~~~
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) ->
//               // Convert jsonElement to a LocalDate here...
//        );
//        Gson gson = gsonBuilder.create();
//        
//        
//        JsonObject jsonObject = new JsonParser().parse("{\"name\": \"John\"}").getAsJsonObject();
//        System.out.println(jsonObject.get("name").getAsString()); //John
        


        
        
        
        // Reading all records at once into memory
        //List<CSVRecord> csvRecords = csvParser.getRecords();
        //System.out.println(csvRecords);
               
        // CREATING EMPTY ACCOUNTS
        HashMap<String, HashMap<String, Float>> accounts = new HashMap<>();
        HashMap<String, List<String>> transactions = new HashMap<>();
        
        // ------ DATE FORMAT -------------
        String pattern = "dd/mm/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        for (CSVRecord csvRecord : csvParser) {
            // Accessing values by the names assigned to each column
        	Date date = simpleDateFormat.parse("00/00/0000");
        	try {
        		date = simpleDateFormat.parse(csvRecord.get("Date"));
        	}
        	catch (ParseException ParseE){
            	ParseE.printStackTrace();
            	LOGGER.log(Level.ERROR, "Could not get Date from: " + csvRecord.get("Date"));
            	continue;
        	}
            String from = csvRecord.get("From");
            String to = csvRecord.get("To");
            String narrative = csvRecord.get("Narrative");
        	Float amount = 0f;
            try {
            	amount = Float.parseFloat(csvRecord.get("Amount"));
            } catch (NumberFormatException nfe) {
            	nfe.printStackTrace();
            	LOGGER.log(Level.ERROR, "Could not get float from: " + csvRecord.get("Amount"));
            	continue;
            }
            
            // Looking at From - this person OWES money
            if(accounts.containsKey(from)) {
            	// Account present - take away amount
            	accounts.get(from).put("Owes",  (accounts.get(from).get("Owes")+amount));
            	accounts.get(from).put("Balance",  (accounts.get(from).get("Balance")-amount));
            	//Add transaction
            	transactions.get(from).add(date + ": Owes £"+amount+" to "+to+" for "+narrative);
            }
            else {
            	//Add new name and new hashmap list
            	HashMap<String, Float> balanceList = new HashMap<String, Float>();
        		balanceList.put("Owed", 0.00f);
        		balanceList.put("Owes", amount);
        		balanceList.put("Balance", -amount);
            	accounts.put(from,balanceList);
            	//Add new array and transaction
            	ArrayList<String> transactionList = new ArrayList<>();
            	transactionList.add(date + ": Owes £"+amount+" to "+to+" for "+narrative);
            	transactions.put(from, transactionList);
            }

            // Looking at To - this person is OWED money
            if(accounts.containsKey(to)) { // To person is present in the account list
            	// Account present - take away amount
            	accounts.get(to).put("Owed",  (accounts.get(to).get("Owed")+amount));
            	accounts.get(to).put("Balance",  (accounts.get(to).get("Balance")+amount));
            	//Add transaction
            	transactions.get(to).add(date + ": Is owed £"+amount+" by "+from+" for "+narrative);
            }
            else {  // To person is NOT present in the account list
            	//Add new name and new hashmap list
            	HashMap<String, Float> balanceList = new HashMap<String, Float>();
        		balanceList.put("Owed", amount);
        		balanceList.put("Owes", 0.00f);
        		balanceList.put("Balance", amount);
            	accounts.put(to,balanceList);
            	//Add new array and transaction
            	ArrayList<String> transactionList = new ArrayList<>();
            	transactionList.add(date + ": Is owed £"+amount+" by "+from+" for "+narrative);
            	transactions.put(to, transactionList);
            }
        } //End of For Loop

        
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
    				LOGGER.log(Level.WARN, "No accout with name: " + userEntry);
    				userEntry = "List Account";
    			}
    		}
        } //End of While Loop
        
        //String filePath = "C:\\Users\\fgajewski\\Desktop\\Java Training\\SupportBank\\jsonTest.json";
        String filePath = "C:\\Users\\fgajewski\\Desktop\\Java Training\\SupportBank\\Transactions2013.json";

//        try {
//			// read the json file
//			FileReader jsonReader = new FileReader(filePath);
//
//			JSONParser jsonParser = new JSONParser();
//			//JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonReader);
//			JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonReader);
//
//			for (Object o : jsonArray) {
//				JSONObject jsonObject = (JSONObject) o;
//				// get a String from the JSON object
//				String date = (String) jsonObject.get("date");
//				String from = (String) jsonObject.get("fromAccount");
//				String to = (String) jsonObject.get("toAccount");
//				String narrative = (String) jsonObject.get("narrative");
//				Double amount = (Double) jsonObject.get("amount");
//				System.out.println("------------");
//				System.out.println("Date is: " + date);
//				System.out.println("Date is: " + from);
//				System.out.println("Date is: " + to);
//				System.out.println("Date is: " + narrative);
//				System.out.println("Date is: " + amount+"\n");
//				
//				
//			}
//
////			// get a String from the JSON object
////			String firstName = (String) jsonObject.get("firstname");
////			System.out.println("The first name is: " + firstName);
////
////			// get a number from the JSON object
////			long id =  (long) jsonObject.get("id");
////			System.out.println("The id is: " + id);
////
////			// get an array from the JSON object
////			JSONArray lang= (JSONArray) jsonObject.get("languages");
////			
////			// take the elements of the json array
////			for(int i=0; i<lang.size(); i++){
////				System.out.println("The " + i + " element of the array: "+lang.get(i));
////			}
////			Iterator i = lang.iterator();
////
////			// take each value from the json array separately
////			while (i.hasNext()) {
////				JSONObject innerObj = (JSONObject) i.next();
////				System.out.println("language "+ innerObj.get("lang") + 
////						" with level " + innerObj.get("knowledge"));
////			}
////			// handle a structure into the json object
////			JSONObject structure = (JSONObject) jsonObject.get("job");
////			System.out.println("Into job structure, name: " + structure.get("name"));
//        } catch (FileNotFoundException ex) {
//        	ex.printStackTrace();
//        } catch (IOException ex) {
//        	ex.printStackTrace();
//        } catch (NullPointerException ex) {
//        	ex.printStackTrace();
//        }


        
// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ GSON @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
        
        JsonReader jsonReader = new JsonReader(new FileReader("Transactions2013.json"));
//
        jsonReader.beginArray();
        jsonReader.beginObject();
//
        while (jsonReader.hasNext()) {
        	String name = jsonReader.nextName();
            System.out.println(name);
            if(name.equals("date")) {
            	System.out.println(jsonReader.nextString());
            } if(name.equals("fromAccount")) {
            	System.out.println(jsonReader.nextString());
            } if(name.equals("toAccount")) {
            	System.out.println(jsonReader.nextString());
            } if(name.equals("narrative")) {
            	System.out.println(jsonReader.nextString());
            } if(name.equals("amount")) {
            	System.out.println(jsonReader.nextDouble());
            }
            
            
            //jsonReader.beginObject();

            //while() {
            	
            }

      
        System.out.println("hi");

//        	String name = jsonReader.nextName();
//        	if (name.equals("descriptor")) {
//        		readApp(jsonReader);
//
//        	}
//        }
////
//        jsonReader.endObject();
//        jsonReader.close();
//    		
//    }
//    
//
//    // Have a sequence of objects
//    
//    
//    public static void readApp(JsonReader jsonReader) throws IOException{
//    	jsonReader.beginObject();
//    	while (jsonReader.hasNext()) {
//    		String name = jsonReader.nextName();
//    		System.out.println(name);
//    		if (name.contains("app")){
//    			jsonReader.beginObject();
//    			while (jsonReader.hasNext()) {
//    				String n = jsonReader.nextName();
//    				if (n.equals("name")){
//    					System.out.println(jsonReader.nextString());
//    				}
//    				if (n.equals("age")){
//    					System.out.println(jsonReader.nextInt());
//    				}
//    				if (n.equals("messages")){
//    					jsonReader.beginArray();
//    					while  (jsonReader.hasNext()) {
//    						System.out.println(jsonReader.nextString());
//    					}
//    					jsonReader.endArray();
//    				}
//    			}
//    			jsonReader.endObject();
//    		}
//
//    	}
//    	jsonReader.endObject();
//    }
        
    }


    
    
//    public List<Message> readJsonStream(InputStream in) throws IOException {
//        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
//        try {
//          return readMessagesArray(reader);
//        } finally {
//          reader.close();
//        }
//      }
//
//      public List<Message> readMessagesArray(JsonReader reader) throws IOException {
//        List<Message> messages = new ArrayList<Message>();
//
//        reader.beginArray();
//        while (reader.hasNext()) {
//          messages.add(readMessage(reader));
//        }
//        reader.endArray();
//        return messages;
//      }
//
//      public Message readMessage(JsonReader reader) throws IOException {
//        long id = -1;
//        String text = null;
//        User user = null;
//        List<Double> geo = null;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//          String name = reader.nextName();
//          if (name.equals("id")) {
//            id = reader.nextLong();
//          } else if (name.equals("text")) {
//            text = reader.nextString();
//          } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
//            geo = readDoublesArray(reader);
//          } else if (name.equals("user")) {
//            user = readUser(reader);
//          } else {
//            reader.skipValue();
//          }
//        }
//        reader.endObject();
//        return new Message(id, text, user, geo);
//      }
//
//      public List<Double> readDoublesArray(JsonReader reader) throws IOException {
//        List<Double> doubles = new ArrayList<Double>();
//
//        reader.beginArray();
//        while (reader.hasNext()) {
//          doubles.add(reader.nextDouble());
//        }
//        reader.endArray();
//        return doubles;
//      }
//
//      public User readUser(JsonReader reader) throws IOException {
//        String username = null;
//        int followersCount = -1;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//          String name = reader.nextName();
//          if (name.equals("name")) {
//            username = reader.nextString();
//          } else if (name.equals("followers_count")) {
//            followersCount = reader.nextInt();
//          } else {
//            reader.skipValue();
//          }
//        }
//        reader.endObject();
//        return new User(username, followersCount);
//      }
}

