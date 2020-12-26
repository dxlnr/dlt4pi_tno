package evidentia;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.net.*;
import java.io.*;
import java.util.*;

import java.util.Date;


public class ctno_gui{
    public ctno_gui() {

	System.out.println("This is the constructor");



    }
    public void run(String args[]){
	try{
		JSONParser parser = new JSONParser();
		File file = new File("TEMP/applicantInfo.json");
        	Object object = parser.parse(new FileReader(file));
		JSONObject jsonobject = (JSONObject) object; 
		System.out.println(jsonobject.get("firstName"));
	}
	catch(Exception e){
		e.printStackTrace();
	}

	Date date= new Date();
        long time = date.getTime();
        System.out.println("Time in Milliseconds: " + time);


    }
    public static void main(String args[]) {
        
        ctno_gui gui = new ctno_gui();
        gui.run(args);
        
    }

}
