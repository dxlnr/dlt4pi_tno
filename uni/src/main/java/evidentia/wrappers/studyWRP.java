/*
 an auto-generated user wrapper template for the service 'study'
*/

package evidentia.wrappers;

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


public class studyWRP extends studyETBWRP {

	@Override
	public void run(){
		if (mode.equals("+-")) {
			//do something
			try{
				System.out.println(in1);
				JSONParser parser = new JSONParser();
		                File file = new File(in1);
                		Object object = parser.parse(new FileReader(file));
                		JSONObject jsonobject = (JSONObject) object;
                		System.out.println(jsonobject.get("firstName"));
				System.out.println(jsonobject.get("universityName"));
				System.out.println(jsonobject.get("universityName"));
				if (jsonobject.get("firstName").toString().equals("Karl-Heinz") && jsonobject.get("lastName").toString().equals("Heinrich") && jsonobject.get("universityName").toString().equals("LLDH")){
					out2="true";
				}
				else{
					out2="false";
				}

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else {
			System.out.println("unrecognized mode for study");
		}
	}
}
