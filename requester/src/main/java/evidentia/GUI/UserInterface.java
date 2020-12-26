package evidentia.GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import evidentia.Entity;
import evidentia.Evidentia;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

//for ctno
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

public class UserInterface extends Application {

	private VBox infoBox = new VBox();
	private Evidentia evidentia = new Evidentia();
	private TextArea textArea;
	private Console console;
	private PrintStream ps;
	

	public static void main(String[] args) {
		// TODO discuss with Tewo
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Evidentia App");

		// Console
		textArea = new TextArea();
		textArea.setEditable(false);
		console = new Console(textArea);
		ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);

		// Menus
		Menu evidentia = new Menu("Evidentia");
		MenuItem evidentiaItem1 = new MenuItem("Java Doc");
		evidentiaItem1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				URI uri;
				try {
					uri = new URI("file:///Users/sellami/Documents/evidentia/target/apidocs/index.html");
					uri.normalize();
					Desktop.getDesktop().browse(uri);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		MenuItem evidentiaItem2 = new MenuItem("About...");
		evidentiaItem2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage aboutDialog = new Stage();
				aboutDialog.initModality(Modality.APPLICATION_MODAL);

				Button okButton = new Button("CLOSE");
				okButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						aboutDialog.close();
					}

				});

				VBox vBox = new VBox(new Text("About Evidentia...."), okButton);
				vBox.setSpacing(30.);
				vBox.setPadding(new Insets(5, 5, 5, 5));

				Scene dialogScene = new Scene(vBox);

				aboutDialog.setScene(dialogScene);
				aboutDialog.show();
			}
		});
		MenuItem evidentiaItem3 = new MenuItem("Exit");
		evidentiaItem3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		evidentia.getItems().addAll(evidentiaItem1, evidentiaItem2, evidentiaItem3);

		Menu entity = new Menu("Entity");
		MenuItem nodeItem1 = new MenuItem("Initialize");
		nodeItem1.setOnAction(new FileChooserEventHandler("-init", textArea, primaryStage));
		MenuItem nodeItem2 = new MenuItem("Information");
		nodeItem2.setOnAction(new CommandEventHandler("-show-info", textArea));
		MenuItem nodeItem3 = new MenuItem("Modes");
		nodeItem3.setOnAction(new CommandEventHandler("-show-modes", textArea));
		MenuItem nodeItem4 = new MenuItem("Clean");
		nodeItem4.setOnAction(new CommandEventHandler("-clean", textArea));
		MenuItem nodeItem5 = new MenuItem("Uninitialize");
		nodeItem5.setOnAction(new CommandEventHandler("-uninit", textArea));
		MenuItem nodeItem6 = new MenuItem("Set Port");
		nodeItem6.setOnAction(
				new TextInputDialogEventHandler("-set-port", textArea, "Set Port", "Enter the port number", "Port:"));
		MenuItem nodeItem7 = new MenuItem("Set Repository");
		nodeItem7.setOnAction(new DirectoryChooserEventHandler("-set-repo", textArea, primaryStage));
		MenuItem nodeItem8 = new MenuItem("Set Mode");
		nodeItem8.setOnAction(new ChoiceDialogEventHandler("-set-mode", textArea, "Set Mode", "Select the mode",
				"Mode:", new String[] { "-noDEN", "-DEN" }));
		MenuItem nodeItem9 = new MenuItem("Import...");
		nodeItem9.setOnAction(new DirectoryChooserEventHandler("-import", textArea, primaryStage));
		MenuItem nodeItem10 = new MenuItem("Export...");
		nodeItem10.setOnAction(new CommandEventHandler("-export", textArea));

		entity.getItems().addAll(nodeItem1, nodeItem2, nodeItem3, nodeItem4, nodeItem5, nodeItem6, nodeItem7, nodeItem8,
				nodeItem9, nodeItem10);

		Menu service = new Menu("Service");
		MenuItem serviceItem1 = new MenuItem("Add");
		serviceItem1.setOnAction(new FileChooserEventHandler("-add-service", textArea, primaryStage));
		MenuItem serviceItem2 = new MenuItem("Remove");
		serviceItem2.setOnAction(new TextInputDialogEventHandler("-rm-service", textArea, "Remove Service",
				"Enter the service name", "Name:"));
		service.getItems().addAll(serviceItem1, serviceItem2);

		Menu workflow = new Menu("Workflow");
		MenuItem workflowItem1 = new MenuItem("Add");
		workflowItem1.setOnAction(new FileChooserEventHandler("-add-workflow", textArea, primaryStage));
		MenuItem workflowItem2 = new MenuItem("Remove");
		workflowItem2.setOnAction(new TextInputDialogEventHandler("-rm-workflow", textArea, "Remove Workflow",
				"Enter the workflow name", "Name:"));
		workflow.getItems().addAll(workflowItem1, workflowItem2);

		Menu claim = new Menu("Claim");
		MenuItem claimItem1 = new MenuItem("Add");
		claimItem1.setOnAction(
				new TextInputDialogEventHandler("-add-claim", textArea, "Add Claim", "Enter the claim name", "Name:"));
		MenuItem claimItem2 = new MenuItem("Remove");
		claimItem2.setOnAction(
				new TextInputDialogEventHandler("-rm-claim", textArea, "Remove Claim", "Enter the claim id", "ID:"));
		claim.getItems().addAll(claimItem1, claimItem2);

		Menu help = new Menu("Help");
		MenuItem helpItem1 = new MenuItem("?");
		helpItem1.setOnAction(new CommandEventHandler("-help", textArea));
		help.getItems().add(helpItem1);

		// Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(evidentia, entity, service, workflow, claim, help);

		updateInfoBox();
		
		HBox hBox = new HBox(textArea, infoBox);

		VBox vBox = new VBox(menuBar, hBox);

		primaryStage.setScene(new Scene(vBox, 960, 600));
		textArea.setPrefHeight(3000);
		textArea.setPrefWidth(primaryStage.getScene().getWidth() * 0.66);
		
		
		primaryStage.show();
	}

	private void updateInfoBox() {
		Entity test = new Entity(true);
		Text name = new Text("Name: " + test.getName());
		Text ip = new Text("IP: " + test.getIP());
		Text port = new Text("Port: " + test.getPort());
		Text numServices = new Text("Number of services: " + test.getServicePack().toJSONObject().size());
		/*
		 * ListView<String> services = new ListView<String>();
		 * services.getItems().addAll(test.getServicePack().getServices().keySet());
		 */
		ComboBox<String> services = new ComboBox<String>();
		services.getItems().addAll(test.getServicePack().getServices().keySet());
		services.getSelectionModel().selectFirst();
		if (services.getItems().size() == 0)
			services.setDisable(true);
		Text numWorkflows = new Text("Number of workflows: " + test.getWorkflowsPack().toJSONObject().size());
		ComboBox<String> workflows = new ComboBox<String>();
		workflows.getItems().addAll(test.getWorkflowsPack().getWorkflows().keySet());
		workflows.getSelectionModel().selectFirst();
		if (workflows.getItems().size() == 0)
			workflows.setDisable(true);
		Text numClaims = new Text("Number of claims: " + test.getClaims().toJSONObject().size());
		TextFlow noDENMode = new TextFlow();
		Text noDENModeText1 = new Text("DEN Mode: ");
		Text noDENModeText2 = new Text();
		if (evidentia.isModeNoDEN()) {
			noDENModeText2.setText("OFF");
			noDENModeText2.setFill(Color.RED);
		}
		else {
			noDENModeText2.setText("ON");
			noDENModeText2.setFill(Color.GREEN);
		}
		noDENMode.getChildren().addAll(noDENModeText1, noDENModeText2);
		
		infoBox.getChildren().clear();
		infoBox.getChildren().addAll(name, ip, port, numServices, services, numWorkflows, workflows, numClaims, noDENMode);
		
		
                

		
		Text howto = new Text("\nThis is a how to generate the regular permit evidence\n1. Check in the drop down menu which evidences are needed \n2. See below which evidences are created \n3. Either first create some facts manually and then run the workflow \"trrp_small('applicantInfo.json',Output)\" \nor directly run the worklfow to automatically create all evidences on the fly");
		infoBox.getChildren().add(howto);
		
		Text jsonfilesT = new Text("\nApplicant Info available in TEMP/ folder");
		infoBox.getChildren().add(jsonfilesT);
		ComboBox<String> avail_json = new ComboBox<String>();
		File directory = new File("TEMP/");
		File[] fList = directory.listFiles();
		for (File file : fList){
            		if (file.isFile()){
            			String fileStr = file.getAbsolutePath();
            			String jsonEnding = fileStr.substring(fileStr.length() - 4);
            			if(jsonEnding.equals("json")){
            				avail_json.getItems().add(file.getName());
            			}
                	}
                }
		avail_json.getSelectionModel().selectFirst();
		infoBox.getChildren().add(avail_json);
		
		Text evidences_req_text = new Text("\nEvidences required to receive the regular permit");
		infoBox.getChildren().add(evidences_req_text);
		
		
		ComboBox<String> evidences_required = new ComboBox<String>();
		evidences_required.getItems().addAll("N_alien_has_a_travel_ban_or_has_been_signaled_for_the_purpose_of_refusing_entry",
							"N_alien_has_pronouncement_of_undesirability",
							"alien_has_a_facial_image_and_ten_fingerprints_taken",
							"N_alien_constitutes_a_danger_to_public_policy_or_national_security",
							"N_foreign_national_will_owe_a_fee_for_processing_an_application",
							"foreign_national_has_a_valid_provisional_residence_permit",
							"foreign_national_has_a_basic_level_of_knowledge_of_the_Dutch_language_and_Dutch_society",
							"foreign_national_has_a_valid_border_crossing_document",
							"foreign_national_is_prepared_to_cooperate_in_a_medical_examination");
		evidences_required.getSelectionModel().selectFirst();
		infoBox.getChildren().add(evidences_required);
		Button create_ness_claim = new Button("Create Claim");
		infoBox.getChildren().add(create_ness_claim);
		
		
		
		
		Text evidences_opt_text = new Text("\nEvidences of which one needs to be true to receive the regular permit");
		infoBox.getChildren().add(evidences_opt_text);
		
		
		ComboBox<String> evidences_opt = new ComboBox<String>();
		evidences_opt.getItems().addAll("intra_corporate_transfer",
							"seasonal_employment",
							"work_as_a_highly_skilled_migrant",
							"residence_as_a_family_member",
							"residence_as_an_EU_Blue_Card_holder",
							"study",
							"exchange");
		evidences_opt.getSelectionModel().selectFirst();
		infoBox.getChildren().add(evidences_opt);
		
		String output = evidences_opt.getSelectionModel().getSelectedItem().toString();
		//System.out.println(output);
		
		Button create_opt_claim = new Button("Create Claim");
		infoBox.getChildren().add(create_opt_claim);
		
		Text space = new Text("\nRun the entire workflow with the above selected applicant info (Caution: might take up to 20min)");
		infoBox.getChildren().add(space);
		
		Button run_wk = new Button("Run workflow");
		infoBox.getChildren().add(run_wk);
		
		
		Text applStatus = new Text("\n---------Application Status---------");
		infoBox.getChildren().add(applStatus);
		
		
		JSONArray claimsJSON = (JSONArray) test.getClaims().toJSONObject();
		//System.out.println(claimsJSON.toString());
		for (int i=0; i < claimsJSON.size(); i++) {
			JSONObject tmp1 = (JSONObject) claimsJSON.get(i);
			String workflowid = (String) tmp1.get("workFlowID");
			Text wi = new Text("Workflow: "+workflowid);
			infoBox.getChildren().add(wi);
			
			if(workflowid.equals("*serviceCL*")){
				JSONObject qclaim = (JSONObject) tmp1.get("qClaim");
				String sign = (String) qclaim.get("signature");
				if(sign.equals("21")){
					String pred = (String) qclaim.get("predicate");
					JSONArray terms = (JSONArray) qclaim.get("terms");
					
					TextFlow termFlow = new TextFlow();
					Text claimId = new Text("\t " + pred);
					Text termT = new Text("\t"+terms.get(1).toString());
					
					if (terms.get(1).toString().equals("true")){
						termT.setFill(Color.GREEN);
						termFlow.getChildren().addAll(termT, claimId);
						infoBox.getChildren().add(termFlow);
					}
					else if (terms.get(1).toString().equals("false")){
						termT.setFill(Color.RED);
						termFlow.getChildren().addAll(termT, claimId);
						infoBox.getChildren().add(termFlow);
					}
				}
			}
			
			JSONArray derivFacts = (JSONArray) tmp1.get("derivFacts");
			
			for (int j=0; j < derivFacts.size(); j++) {
				JSONObject predicate = (JSONObject) derivFacts.get(j);
				
				JSONArray terms = (JSONArray) predicate.get("terms");
				
				if(terms.size()!=2){
					continue;
				}
				TextFlow termFlow = new TextFlow();
				Text claimId = new Text("\t " + predicate.get("predicate").toString());
				//infoBox.getChildren().add(claimId);
				Text termT = new Text("\t"+terms.get(1).toString());
				//System.out.println(terms.get(1).toString());
				if (terms.get(1).toString().equals("true")){
					termT.setFill(Color.GREEN);
				}
				else if (terms.get(1).toString().equals("false")){
					termT.setFill(Color.RED);
				}
				else{
					continue;
				}
				termFlow.getChildren().addAll(termT, claimId);
				infoBox.getChildren().add(termFlow);
				
			
			}
			
		}
		
		//JSONObject tmp1 = (JSONObject) claimsJSON.get(0);
		//JSONArray derivFacts = (JSONArray) tmp1.get("derivFacts");
		//JSONObject predicate = (JSONObject) derivFacts.get(0);
		//Text claimId = new Text("this " + predicate.get("predicate").toString());
		
		
		
		
		create_ness_claim.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						
            					String command = "-add-claim";
            					String name = evidences_required.getSelectionModel().getSelectedItem().toString()+"('"+avail_json.getSelectionModel().getSelectedItem().toString()+"',"+"Ok"+")";
            					//System.out.println(command);
            					//System.out.println(name);
						textArea.clear();
						evidentia.run(new String[] { command, name });
						updateInfoBox();
					}

					});
		
		
		create_opt_claim.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						
						String command = "-add-claim";
            					String name = evidences_opt.getSelectionModel().getSelectedItem().toString()+"('"+avail_json.getSelectionModel().getSelectedItem().toString()+"',"+"Ok"+")";
            					//System.out.println(command);
            					//System.out.println(name);
						textArea.clear();
						evidentia.run(new String[] { command, name });
						updateInfoBox();
						//System.out.println(evidences_opt.getSelectionModel().getSelectedItem().toString());
					}

					});
		
		run_wk.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						
						String command = "-add-claim";
            					String name = "trrp_small"+"('"+avail_json.getSelectionModel().getSelectedItem().toString()+"',"+"Ok"+")";
            					//System.out.println(command);
            					//System.out.println(name);
						textArea.clear();
						evidentia.run(new String[] { command, name });
						updateInfoBox();
						//System.out.println(evidences_opt.getSelectionModel().getSelectedItem().toString());
					}

					});
		
		
	}

	private static class Console extends OutputStream {

		private TextArea output;

		public Console(TextArea ta) {
			this.output = ta;
		}

		@Override
		public void write(int i) throws IOException {
			output.appendText(String.valueOf((char) i));
		}
	}

	private class CommandEventHandler implements EventHandler<ActionEvent> {

		private final String command;
		private final TextArea textArea;

		public CommandEventHandler(String command, TextArea textArea) {
			this.command = command;
			this.textArea = textArea;
		}

		@Override
		public void handle(ActionEvent event) {
			try {
				textArea.clear();
				evidentia.run(new String[] { command });
				updateInfoBox();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private class FileChooserEventHandler implements EventHandler<ActionEvent> {

		private final String command;
		private final TextArea textArea;
		private final Stage primaryStage;
		private final FileChooser fileChooser = new FileChooser();

		public FileChooserEventHandler(String command, TextArea textArea, Stage primaryStage) {
			this.command = command;
			this.textArea = textArea;
			this.primaryStage = primaryStage;
		}

		@Override
		public void handle(final ActionEvent event) {
			File file = fileChooser.showOpenDialog(primaryStage);
			if (file != null) {
				try {
					textArea.clear();
					evidentia.run(new String[] { command, file.getPath() });
					updateInfoBox();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class DirectoryChooserEventHandler implements EventHandler<ActionEvent> {

		private final String command;
		private final TextArea textArea;
		private final Stage primaryStage;
		private final DirectoryChooser directoryChooser = new DirectoryChooser();

		public DirectoryChooserEventHandler(String command, TextArea textArea, Stage primaryStage) {
			this.command = command;
			this.textArea = textArea;
			this.primaryStage = primaryStage;
		}

		@Override
		public void handle(final ActionEvent event) {
			File directory = directoryChooser.showDialog(primaryStage);
			if (directory != null) {
				try {
					textArea.clear();
					evidentia.run(new String[] { command, directory.getPath() });
					updateInfoBox();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class TextInputDialogEventHandler implements EventHandler<ActionEvent> {

		private final String command;
		private final TextArea textArea;
		private final TextInputDialog dialog = new TextInputDialog("");

		public TextInputDialogEventHandler(String command, TextArea textArea, String title, String header,
				String content) {
			this.command = command;
			this.textArea = textArea;
			dialog.setTitle(title);
			dialog.setHeaderText(header);
			dialog.setContentText(content);
		}

		@Override
		public void handle(final ActionEvent event) {

			Optional<String> result = dialog.showAndWait();

			result.ifPresent(name -> {
				try {
					textArea.clear();
					evidentia.run(new String[] { command, name });
					updateInfoBox();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		}
	}

	private class ChoiceDialogEventHandler implements EventHandler<ActionEvent> {

		private final String command;
		private final TextArea textArea;
		private final ChoiceDialog<String> dialog;

		public ChoiceDialogEventHandler(String command, TextArea textArea, String title, String header, String content,
				String[] options) {
			this.command = command;
			this.textArea = textArea;
			dialog = new ChoiceDialog<String>(options[0], options);
			dialog.setTitle(title);
			dialog.setHeaderText(header);
			dialog.setContentText(content);
		}

		@Override
		public void handle(final ActionEvent event) {

			Optional<String> result = dialog.showAndWait();

			result.ifPresent(name -> {
				try {
					textArea.clear();
					evidentia.run(new String[] { command, name });
					updateInfoBox();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		}
	}
}
