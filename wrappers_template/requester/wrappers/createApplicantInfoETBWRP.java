/*
 an auto-generated ETB wrapper template for the service 'createApplicantInfo'
*/

package evidentia.wrappers;

import java.util.ArrayList;
import evidentia.etbDL.services.genericWRP;

public abstract class createApplicantInfoETBWRP extends genericWRP {
	//input variables declaration
	protected String in1;
	//output variables declaration
	protected String out1;

	@Override
	public void initialise(){
		serviceName = "createApplicantInfo";
		signatureStr = "2";
		modesStr = "-";
		evidence = "ev(createApplicantInfo)";
		//input variables instantiation
		in1 = datalog2JavaStrConst(mode, argList, 1);
		//output variables default instantiation
		out1 = in1;
	}

	@Override
	public ArrayList<String> getListOutput(int pos) {
		return null;
	}

	@Override
	public String getStrOutput(int pos) {
		if (pos == 1) {
			return this.out1;
		}
		return null;
	}
}