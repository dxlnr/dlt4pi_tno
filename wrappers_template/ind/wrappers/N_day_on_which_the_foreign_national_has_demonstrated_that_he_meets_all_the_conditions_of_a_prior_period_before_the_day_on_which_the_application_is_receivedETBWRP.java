/*
 an auto-generated ETB wrapper template for the service 'N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_received'
*/

package evidentia.wrappers;

import java.util.ArrayList;
import evidentia.etbDL.services.genericWRP;

public abstract class N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_receivedETBWRP extends genericWRP {
	//input variables declaration
	protected String in1;
	protected String in2;
	protected String in3;
	//output variables declaration
	protected String out1;
	protected String out2;
	protected String out3;

	@Override
	public void initialise(){
		serviceName = "N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_received";
		signatureStr = "211";
		modesStr = "++-";
		evidence = "ev(N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_received)";
		//input variables instantiation
		in1 = datalog2JavaStrConst(mode, argList, 1);
		in2 = datalog2JavaStrConst(mode, argList, 2);
		in3 = datalog2JavaStrConst(mode, argList, 3);
		//output variables default instantiation
		out1 = in1;
		out2 = in2;
		out3 = in3;
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
		if (pos == 3) {
			return this.out3;
		}
		return null;
	}
}