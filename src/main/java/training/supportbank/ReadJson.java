package training.supportbank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReadJson {
	Gson gson = new Gson();

	int one = gson.fromJson("1", int.class);
//	Integer one = gson.fromJson("1", Integer.class);
//	Long one = gson.fromJson("1", Long.class);
//	Boolean false = gson.fromJson("false", Boolean.class);
//	String str = gson.fromJson("\"abc\"", String.class);
//	String[] anotherStr = gson.fromJson("[\"abc\"]", String[].class);
//	
//    Gson gson = gsonBuilder.create();
	System.out.println
    
    JsonObject jsonObject = new JsonParser().parse("{\"name\": \"John\"}").getAsJsonObject();
    System.out.println(jsonObject.get("name").getAsString()); //John
}
