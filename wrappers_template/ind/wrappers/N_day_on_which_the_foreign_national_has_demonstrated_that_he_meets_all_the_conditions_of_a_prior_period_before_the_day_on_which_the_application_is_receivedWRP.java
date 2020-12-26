/*
 an auto-generated user wrapper template for the service 'N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_received'
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

import java.util.Date;

public class N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_receivedWRP extends N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_receivedETBWRP {

	@Override
	public void run(){
		if (mode.equals("++-")) {
			//do something
			try{
				Date date= new Date();
				long time = date.getTime();
			    	System.out.println("Time Stamp Now: " + time);
				System.out.println(in1);
				JSONParser parser = new JSONParser();
				File file = new File(in1);
                		Object object = parser.parse(new FileReader(file));
                		JSONObject jsonobject = (JSONObject) object;
                		//System.out.println("Time Stamp application received: " + jsonobject.get("timeStamp"));
				//System.out.println(jsonobject.get("applicationId"));
				
				if (time > Long.parseLong(in2)){
					System.out.println("Time Stamp application received: " + in2);
					out3="true";
				} else if(time > (Long) jsonobject.get("timeStamp")){
					System.out.println("Time Stamp from requester id accepted: " + jsonobject.get("timeStamp"));
					out3="true";
				} else{
					System.out.println("Time Stamp not accepted. Day on which the requester proves to fulfill all conditions lies before the time stamp he submitted the applcation.");
					out3="false";
				}

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else {
			System.out.println("unrecognized mode for N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_received");
		}
	}
}


