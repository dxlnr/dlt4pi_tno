/*
 an auto-generated user wrapper template for the service 'N_foreign_national_will_owe_a_fee_for_processing_an_application'
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


public class N_foreign_national_will_owe_a_fee_for_processing_an_applicationWRP extends N_foreign_national_will_owe_a_fee_for_processing_an_applicationETBWRP {

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
				System.out.println(jsonobject.get("applicationId"));
				if (jsonobject.get("firstName").toString().equals("Daniel") && jsonobject.get("lastName").toString().equals("Illner") && jsonobject.get("bankId").toString().equals("3507e53")){
					out2="true";
				} else if (jsonobject.get("firstName").toString().equals("Max") && jsonobject.get("lastName").toString().equals("Muselmann") && jsonobject.get("bankId").toString().equals("238phf34")){
					out2="true";
				} else if (jsonobject.get("firstName").toString().equals("Karl-Heiz") && jsonobject.get("lastName").toString().equals("Heinrich") && jsonobject.get("bankId").toString().equals("2398y42h3")){
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
			System.out.println("unrecognized mode for N_foreign_national_will_owe_a_fee_for_processing_an_application");
		}
	}
}
