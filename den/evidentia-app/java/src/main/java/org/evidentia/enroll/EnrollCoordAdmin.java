package org.evidentia.enroll;

import java.nio.file.Paths;
import java.util.Properties;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class EnrollCoordAdmin {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	public static void main(String[] args) throws Exception {

		// Create a CA client for interacting with the CA.
		Properties props = new Properties();
		props.put("pemFile",
			"evidentia-network/crypto-config/peerOrganizations/coord.evidentia.net/ca/ca.coord.evidentia.net-cert.pem");
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:9054", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Create a wallet for managing identities
		Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));

		// Check to see if we've already enrolled the admin user.
		boolean adminExists = wallet.exists("admin_coord");
        if (adminExists) {
            System.out.println("An identity for the admin user \"admin_coord\" already exists in the wallet");
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin_coord", "adminpw", enrollmentRequestTLS);
        Identity user = Identity.createIdentity("CoordinatorMSP", enrollment.getCert(), enrollment.getKey());
        wallet.put("admin_coord", user);
		System.out.println("Successfully enrolled user \"admin_coord\" and imported it into the wallet");
	}
}
