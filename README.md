# DLT 4 Public Innovation TNO-Challenge

##### In this README we want to provide a step-by-step instruction to set up our prototype.
##### We will first bring up the DEN, second bring up all ETB nodes and third create evidence.

Extensiv README documentation on DEN can be found here: [DEN](https://git.fortiss.org/evidentia/den/-/tree/master)
...and on ETB here: [ETB](https://git.fortiss.org/evidentia/etb/-/tree/master)

An GUI demonstration video is available on [google-drive](https://drive.google.com/file/d/1q_jo8sCQViBLeodR4XUAdtnimb6CZx-4/view?usp=sharing) or [youtube](https://youtu.be/puv0BeMMSs8).

#### 1.1 DEN: Check if all bash scripts and binaries are executables
```bash
chmod +x den/evidentia-app/den.sh
chmod +x den/evidentia-app/monitordocker.sh
chmod +x den/evidentia-network/byfn.sh
chmod +x den/evidentia-network/ccp-generate.sh
chmod +x den/evidentia-network/scripts/script.sh
chmod +x den/evidentia-network/scripts/utils.sh
chmod +x den/bin/*
```

#### 1.2 DEN: Start the network
```bash
./den/evidentia-app/den.sh -u
```

#### 2. Coordinator: delete wallet, enroll admin and register user, copy the ca certificates
```bash
rm /wallet/* # in case it is not empty
cp den/evidentia-network/crypto-config/peerOrganizations/coord.evidentia.net/ca/ca.coord.evidentia.net-cert.pem coordinator/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-coord.json coordinator/src/main/resources/
cd coordinator/
mvn compile
mvn exec:java -Dexec.mainClass="enrollRegister.EnrollAdmin"
mvn exec:java -Dexec.mainClass="enrollRegister.RegisterUser"
cd ..
```

#### 3.1 ETB: copy ca certificates

##### Bank
```bash
entity=bank
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```
Hint: keep the last 3 lines and only change entity
##### Border Control
```bash
entity=bc
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```
##### Employer
```bash
entity=employer
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```
##### Ind
```bash
entity=ind
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```
##### Medicine
```bash
entity=medicine
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```
##### Police
```bash
entity=police
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```
##### Requester
```bash
entity=requester
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```
##### Uni
```bash
entity=uni
cp den/evidentia-network/connection-profiles/connection-coord.json $entity/src/main/resources/
cp den/evidentia-network/crypto-config/peerOrganizations/$entity.evidentia.net/ca/ca.$entity.evidentia.net-cert.pem $entity/src/main/resources/
cp den/evidentia-network/connection-profiles/connection-$entity.json $entity/src/main/resources/
```

#### 3.2 ETB: compile and run administrator to generate wallet certificates
in each ETB entity (except coordinator and den):
```bash
cd entity
mvn compile
mvn exec:java -Dexec.mainClass="evidentia.Administrator"
cd ..
```

#### 3.3 ETB: copy coordinator 
in each ETB entity (except coordinator):

##### Bank
```bash
entity=bank
cp -r coordinator/wallet/* $entity/wallet/
```
#(keep the last 3 lines and only change entity)
##### Border Control
```bash
entity=bc
cp -r coordinator/wallet/* $entity/wallet/
```
##### Employer
```bash
entity=employer
cp -r coordinator/wallet/* $entity/wallet/
```
##### Ind
```bash
entity=ind
cp -r coordinator/wallet/* $entity/wallet/
```
##### Medicine
```bash
entity=medicine
cp -r coordinator/wallet/* $entity/wallet/
```
##### Police
```bash
entity=police
cp -r coordinator/wallet/* $entity/wallet/
```
##### Requester
```bash
entity=requester
cp -r coordinator/wallet/* $entity/wallet/
```
##### Uni
```bash
entity=uni
cp -r coordinator/wallet/* $entity/wallet/
```

#### 4. create evidentia .bashrc function
in .bashrc add:
```bash
function evi { mvn exec:java -Dexec.mainClass="evidentia.Evidentia" -Dexec.args="$1 $2"; }
export -f evi
```
now source .bashrc:
```bash
source ~/.bashrc
```

#### 5.1 Run the DLT: initialise ETB nodes
for each ETB entity (except coordinator)
```bash
evi -init initFiles/initSpec.txt
```

#### 5.2 Run the DLT: add services to ETB nodes
```bash
cd ENTITY/
evi -add-service initFiles/SERVICE.txt
```
replace ENTITY/ and SERVICE.txt with the following entities and services respectively:
* bank:
    * `N_foreign_national_will_owe_a_fee_for_processing_an_application.txt`
* bc:
    * `alien_has_a_facial_image_and_ten_fingerprints_taken.txt`
    * `foreign_national_has_a_valid_border_crossing_document.txt`
* employer:
    * `intra_corporate_transfer.txt`
    * `seasonal_employment.txt`
    * `work_as_a_highly_skilled_migrant.txt`
* ind:
    * `N_day_on_which_the_foreign_national_has_demonstrated_that_he_meets_all_the_conditions_of_a_prior_period_before_the_day_on_which_the_application_is_received.txt`
    * `day_on_which_the_alien_has_demonstrated_that_he_fulfils_all_the_conditions_of_temporary_residence_permit.txt`
    * `foreign_national_has_a_basic_level_of_knowledge_of_the_Dutch_language_and_Dutch_society.txt`
    * `foreign_national_has_a_valid_provisional_residence_permit.txt`
    * `residence_as_a_family_member.txt`
    * `residence_as_an_EU_Blue_Card_holder.txt`
* medicine:
    * `foreign_national_is_prepared_to_cooperate_in_a_medical_examination.txt`
* police:
    * `N_alien_constitutes_a_danger_to_public_policy_or_national_security.txt`
    * `N_alien_has_pronouncement_of_undesirability.txt`
    * `N_alien_has_a_travel_ban_or_has_been_signaled_for_the_purpose_of_refusing_entry.txt`
* requester:
    * `serviceCreateApplicantInfo.txt`
* uni:
    * `exchange.txt`
    * `study.txt`

#### 5.3 Run the DLT: add workflow to ETB nodes
```bash
cd requester/
evi -add-workflow initFiles/workflow_small_trrp.txt
```

#### 5.4 Run the DLT: replace newly generated ETB wrappers with developed ones
for each ETB entity (except the coordinator)
```bash
cp -r wrappers_template/ENTITY/wrappers/ ENTITY/src/main/java/evidentia/
```

#### 5.5 Run the DLT: compile and start the ETB nodes
for each ETB entity (except the coordinator and requester) in seperate **terminal windows**
```bash
cd ENTITY/
mvn compile
evi -show-info #Optional
evi
```

#### 5.6 Run the DLT with terminal 
##### Generate concrete and single evidence in the requester (Optional)
```bash
cd requester/
evi "-add-claim \"N_alien_has_pronouncement_of_undesirability('TEMP/applicantInfo.json',Ok)\"" #Check if applicant one has pronouncement of undesirability (return variable Ok should be true)
evi "-add-claim \"intra_corporate_transfer('TEMP/applicantInfo.json',Ok)\"" #Check if applicant one does an intra-corporate transfer at MI5 (return variable Ok should be true)
evi "-add-claim \"study('TEMP/applicantInfo.json',Ok)\""" #Check if applicant one is student (return variable Ok should be false)
evi -show-info
```
To see whether the claim was successful the output needs to be "true".
Unfortunately, we were not able to check upon the outcome in the datalog structure itself even with the very much appreciated Evidentia support.
Hint: example applicant infos are predefined in requester/TEMP/ as JSON files.

##### Generate evidence using the workflow
*ATTENTION: This process needs a lot of RAM. With my 8GB machine it was at its edge*
```bash
cd requester
evi "-add-claim \"trrp_small('TEMP/applicantInfo.json',O)\"
```

#### 5.7 Run the DLT with GUI: 
start the GUI at the requester:
```bash
mvn exec:java -Dexec.mainClass="evidentia.GUI.Launcher"
```
then follow the instructions from this [video](https://drive.google.com/file/d/1q_jo8sCQViBLeodR4XUAdtnimb6CZx-4/view?usp=sharing) or the GUI itself 






