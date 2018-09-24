package training.supportbank;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVReader {
	private String address;
	
	//Constructor
	public CSVReader(String address) {
		this.address = address;
	}

	//Methods
	
	//Spits out all of the records - List<CSVRecord>
	public List<CSVRecord> readCSV(){
		try{
			Reader reader = Files.newBufferedReader(Paths.get(address));
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withIgnoreHeaderCase()
					.withTrim());
			List<CSVRecord> csvRecords = csvParser.getRecords();
			return csvRecords;
		}
		catch (IOException ex) {
			System.out.println("Error reading CSV");
			return null;
		}
	}
}

