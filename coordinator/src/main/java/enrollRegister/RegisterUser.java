/*
SPDX-License-Identifier: Apache-2.0
*/

package enrollRegister;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

public class RegisterUser {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	public static void main(String[] args) throws Exception {
		String username = "coord";
		String adminUsername = "admin_coord";
		String msp = "CoordinatorMSP";
		String caPort = "9054";
		String orgname = "coord";
		String affiliationName = "org1";

		// Create a CA client for interacting with the CA.
		Properties props = new Properties();
		props.put("pemFile", "src/main/resources/ca." + orgname + ".evidentia.net-cert.pem");
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:" + caPort +"", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Create a wallet for managing identities
		Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));

		// Check to see if we've already enrolled the user.
		boolean userExists = wallet.exists(username);
		if (userExists) {
			System.out.println("An identity for the user \"" + username + "\" already exists in the wallet");
			return;
		}

		userExists = wallet.exists(adminUsername);
		if (!userExists) {
			System.out.println("\"" + adminUsername + "\" needs to be enrolled and added to the wallet first");
			return;
		}

		Identity adminIdentity = wallet.get(adminUsername);
		User admin = new User() {

			@Override
			public String getName() {
				return adminUsername;
			}

			@Override
			public Set<String> getRoles() {
				return null;
			}

			@Override
			public String getAccount() {
				return null;
			}

			@Override
			public String getAffiliation() {
				return "" + affiliationName + ".department1";
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return adminIdentity.getCertificate();
					}
				};
			}

			@Override
			public String getMspId() {
				return msp;
			}

		};

		// Register the user, enroll the user, and import the new identity into the wallet.
		RegistrationRequest registrationRequest = new RegistrationRequest(username);
		registrationRequest.setAffiliation("" + affiliationName +".department1");
		registrationRequest.setEnrollmentID(username);
		String enrollmentSecret = caClient.register(registrationRequest, admin);
		Enrollment enrollment = caClient.enroll(username, enrollmentSecret);
		Identity user = Identity.createIdentity(msp, enrollment.getCert(), enrollment.getKey());
		wallet.put(username, user);
		System.out.println("Successfully enrolled user \"" + username + "\" and imported it into the wallet");
	}

}
