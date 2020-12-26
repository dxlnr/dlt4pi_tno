/*
 an auto-generated user wrapper template for the service 'foreign_national_is_prepared_to_cooperate_in_a_medical_examination'
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

public class foreign_national_is_prepared_to_cooperate_in_a_medical_examinationWRP extends foreign_national_is_prepared_to_cooperate_in_a_medical_examinationETBWRP {

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
				System.out.println(jsonobject.get("medicineAffiliation"));
				if (jsonobject.get("firstName").toString().equals("Daniel") && jsonobject.get("lastName").toString().equals("Illner") && jsonobject.get("medicineAffiliation").toString().equals("Dr. Gertz Wohlhabend")){
					out2="true";
				} 
				else if(jsonobject.get("firstName").toString().equals("Max") && jsonobject.get("lastName").toString().equals("Muselmann") && jsonobject.get("medicineAffiliation").toString().equals("Dr. Gertz Wohlhabend")){
					out2="true";
				}
				else if(jsonobject.get("firstName").toString().equals("Karl-Heinz") && jsonobject.get("lastName").toString().equals("Heinrich") && jsonobject.get("medicineAffiliation").toString().equals("Dr. Med. Jeremiah Rueckenkrumm")){
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
			System.out.println("unrecognized mode for foreign_national_is_prepared_to_cooperate_in_a_medical_examination");
		}
	}
}
