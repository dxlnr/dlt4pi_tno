/*
 an auto-generated user wrapper template for the service 'day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permit'
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


public class day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permitWRP extends day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permitETBWRP {

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
				try{
					out2=jsonobject.get("timeStamp").toString();
				}
				catch(Exception e){
					System.out.println("No valid time stamp for fulfilling the conditions given");
					out2="false";
				}

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else {
			System.out.println("unrecognized mode for day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permit");
		}
	}
}
