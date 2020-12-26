package org.evidentia;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;

public class UpdateExecutionTarget {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	public static void main(String[] args) throws Exception {
		String username = "requester";

		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);

		// load a CCP
		Path networkConfigPath = Paths.get("evidentia-network", "connection-requester.json");
		System.out.println(networkConfigPath.toAbsolutePath().toString());

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, username).networkConfig(networkConfigPath).discovery(true);

		// Check to see if we've already enrolled the user.
		boolean userExists = wallet.exists(username);
		if (!userExists) {
			System.out.println("An identity for the user \"" + username + "\" does not exist in the wallet");
			return;
		}

		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("evidentiachannel");
			Contract contract = network.getContract("evidentia");

			contract.submitTransaction("updateServiceExecutionTarget", "getVisa", "(Form, Passport, Photo, HealthBank, Ticket, Accomodation, SufficientMeans, Time)", "consulate");

			System.out.println("Transaction has been submitted");
		}
	}

}
