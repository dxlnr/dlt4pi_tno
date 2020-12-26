/*
 an auto-generated user wrapper template for the service 'createApplicantInfo'
*/

package evidentia.wrappers;

public class createApplicantInfoWRP extends createApplicantInfoETBWRP {

	@Override
	public void run(){
		if (mode.equals("-")) {
			out1="TEMP/applicantInfo.json";
		}
		else {
			System.out.println("unrecognized mode for createApplicantInfo");
		}
	}
}