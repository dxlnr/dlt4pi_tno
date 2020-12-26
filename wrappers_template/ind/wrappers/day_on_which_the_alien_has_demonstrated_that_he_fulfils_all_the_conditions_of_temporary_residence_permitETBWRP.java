/*
 an auto-generated ETB wrapper template for the service 'day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permit'
*/

package evidentia.wrappers;

import java.util.ArrayList;
import evidentia.etbDL.services.genericWRP;

public abstract class day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permitETBWRP extends genericWRP {
	//input variables declaration
	protected String in1;
	protected String in2;
	//output variables declaration
	protected String out1;
	protected String out2;

	@Override
	public void initialise(){
		serviceName = "day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permit";
		signatureStr = "21";
		modesStr = "+-";
		evidence = "ev(day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permit)";
		//input variables instantiation
		in1 = datalog2JavaStrConst(mode, argList, 1);
		in2 = datalog2JavaStrConst(mode, argList, 2);
		//output variables default instantiation
		out1 = in1;
		out2 = in2;
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
		if (pos == 2) {
			return this.out2;
		}
		return null;
	}
}