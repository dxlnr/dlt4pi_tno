/*
 an auto-generated user wrapper template for the service 'intra_corporate_transfer'
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


public class intra_corporate_transferWRP extends intra_corporate_transferETBWRP {

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

				if (jsonobject.get("firstName").toString().equals("Daniel") && jsonobject.get("lastName").toString().equals("Illner") && jsonobject.get("employerName").toString().equals("MI5")){
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
			System.out.println("unrecognized mode for intra_corporate_transfer");
		}
	}
}
