package org.evidentia;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractEvent;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.json.JSONObject;

public class EventListenerSA {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	public static void main(String[] args) throws Exception {
		List<String> services = Arrays.asList(new String[] { "infer", "cppCheck" });
		String username = "sa";

		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);

		// load a CCP
		Path networkConfigPath = Paths.get("..", "..", "evidentia-network", "connection-sa.json");
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

			contract.addContractListener(new Consumer<ContractEvent>() {

				@Override
				public void accept(ContractEvent t) {
					JSONObject service = new JSONObject(new String(t.getPayload().get()));
					String serviceName = (String) service.get("serviceName");
					String params = (String) service.get("params");
					System.out.println(
							"************************ Service Event *******************************************************");
					System.out.println("Service name: " + serviceName);
					System.out.println("Params: " + params);
					System.out.println("Target User: " + service.get("target"));

					if (services.contains(serviceName)) {
						System.out.println("I will execute the service");
						System.out.println("Updating Target for " + serviceName + params);
						try {
							contract.submitTransaction("updateServiceExecutionTarget", serviceName, params);
						} catch (ContractException | TimeoutException | InterruptedException e1) {
							e1.printStackTrace();
						}
						System.out.println("Updated Target for " + serviceName + params);
						System.out.println("Updating Response for " + serviceName + params);
						try {
							contract.submitTransaction("updateServiceExecutionResponse", serviceName, params,
									 "EVIDENCE", "RESPONSE");
						} catch (ContractException | TimeoutException | InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("Updated Response for " + serviceName + params);
					} else {
						System.out.println("I can't execute the service");
					}

				}
			}, "serviceExecution");
			Thread.currentThread().join();
		}
	}

}
