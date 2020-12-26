#!/bin/bash
#
# Copyright IBM Corp All Rights Reserved
#
# SPDX-License-Identifier: Apache-2.0
#
# Exit on first error
set -e

COMPOSE_FILE_EXPLORER=../explorer/docker-compose.yaml

while getopts "uedh" option
do
case "${option}"
in
e) EXPLORER=true;;
u) NETWORK_UP=true;;
d) NETWORK_DOWN=true;;
h) HELP=true;;
esac
done

function printHelp() {
  echo "Usage: "
  echo "  den.sh [-u] [-d]"
  echo "    -u 'up' - bring up the network"
  echo "    -d 'down' - clear the network"
  echo "  den.sh -h (print this message)"
}

function networkUp()  {
  # don't rewrite paths for Windows Git Bash users
  export MSYS_NO_PATHCONV=1
  starttime=$(date +%s)
  CC_SRC_LANGUAGE=go
  CC_RUNTIME_LANGUAGE=golang
  CC_SRC_PATH=github.com/chaincode/evidentia
  CC_SRC_LANGUAGE=`echo "$CC_SRC_LANGUAGE" | tr [:upper:] [:lower:]`


  # clean the keystore
  rm -rf ./hfc-key-store

  # launch network; create channel and join peer to channel
  cd ../evidentia-network
  echo y | ./byfn.sh down
  echo y | ./byfn.sh up -a -n -s couchdb

  CONFIG_ROOT=/opt/gopath/src/github.com/hyperledger/fabric/peer
  ORG1_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/requester.evidentia.net/users/Admin@requester.evidentia.net/msp
  ORG1_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/requester.evidentia.net/peers/peer0.requester.evidentia.net/tls/ca.crt

  ORG2_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/ind.evidentia.net/users/Admin@ind.evidentia.net/msp
  ORG2_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/ind.evidentia.net/peers/peer0.ind.evidentia.net/tls/ca.crt

  ORG3_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/coord.evidentia.net/users/Admin@coord.evidentia.net/msp
  ORG3_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/coord.evidentia.net/peers/peer0.coord.evidentia.net/tls/ca.crt

  ORG4_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/uni.evidentia.net/users/Admin@uni.evidentia.net/msp
  ORG4_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/uni.evidentia.net/peers/peer0.uni.evidentia.net/tls/ca.crt

  ORG5_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/bank.evidentia.net/users/Admin@bank.evidentia.net/msp
  ORG5_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/bank.evidentia.net/peers/peer0.bank.evidentia.net/tls/ca.crt

  ORG6_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/employer.evidentia.net/users/Admin@employer.evidentia.net/msp
  ORG6_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/employer.evidentia.net/peers/peer0.employer.evidentia.net/tls/ca.crt

  ORG7_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/bc.evidentia.net/users/Admin@bc.evidentia.net/msp
  ORG7_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/bc.evidentia.net/peers/peer0.bc.evidentia.net/tls/ca.crt

  ORG8_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/police.evidentia.net/users/Admin@police.evidentia.net/msp
  ORG8_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/police.evidentia.net/peers/peer0.police.evidentia.net/tls/ca.crt

  ORG9_MSPCONFIGPATH=${CONFIG_ROOT}/crypto/peerOrganizations/medicine.evidentia.net/users/Admin@medicine.evidentia.net/msp
  ORG9_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/peerOrganizations/medicine.evidentia.net/peers/peer0.medicine.evidentia.net/tls/ca.crt

  ORDERER_TLS_ROOTCERT_FILE=${CONFIG_ROOT}/crypto/ordererOrganizations/evidentia.net/orderers/orderer.evidentia.net/msp/tlscacerts/tlsca.evidentia.net-cert.pem
  set -x

  echo "Installing smart contract on peer0.requester.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=RequesterMSP \
    -e CORE_PEER_ADDRESS=peer0.requester.evidentia.net:7051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG1_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"

  echo "Installing smart contract on peer0.ind.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=ImmigrationDepartmentMSP \
    -e CORE_PEER_ADDRESS=peer0.ind.evidentia.net:9051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG2_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG2_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"

  echo "Installing smart contract on peer0.coord.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=CoordinatorMSP \
    -e CORE_PEER_ADDRESS=peer0.coord.evidentia.net:11051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG3_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG3_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"

  echo "Installing smart contract on peer0.uni.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=UniversityMSP \
    -e CORE_PEER_ADDRESS=peer0.uni.evidentia.net:13051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG4_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG4_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"

  echo "Installing smart contract on peer0.bank.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=BankMSP \
    -e CORE_PEER_ADDRESS=peer0.bank.evidentia.net:15051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG5_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG5_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"

  echo "Installing smart contract on peer0.employer.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=EmployerMSP \
    -e CORE_PEER_ADDRESS=peer0.employer.evidentia.net:17051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG6_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG6_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"

  echo "Installing smart contract on peer0.bc.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=BorderControlMSP \
    -e CORE_PEER_ADDRESS=peer0.bc.evidentia.net:19051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG7_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG7_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"


  echo "Installing smart contract on peer0.police.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=PoliceMSP \
    -e CORE_PEER_ADDRESS=peer0.police.evidentia.net:21051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG8_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG8_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"


  echo "Installing smart contract on peer0.medicine.evidentia.net"
  docker exec \
    -e CORE_PEER_LOCALMSPID=MedicalAssociationMSP \
    -e CORE_PEER_ADDRESS=peer0.medicine.evidentia.net:23051 \
    -e CORE_PEER_MSPCONFIGPATH=${ORG9_MSPCONFIGPATH} \
    -e CORE_PEER_TLS_ROOTCERT_FILE=${ORG9_TLS_ROOTCERT_FILE} \
    cli \
    peer chaincode install \
      -n evidentia \
      -v 1.0 \
      -p "$CC_SRC_PATH" \
      -l "$CC_RUNTIME_LANGUAGE"


  echo "Instantiating smart contract on evidentiachannel"
  docker exec \
    -e CORE_PEER_LOCALMSPID=RequesterMSP \
    -e CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH} \
    cli \
    peer chaincode instantiate \
      -o orderer.evidentia.net:7050 \
      -C evidentiachannel \
      -n evidentia \
      -l "$CC_RUNTIME_LANGUAGE" \
      -v 1.0 \
      -c '{"Args":[]}' \
      -P "AND('RequesterMSP.member','ImmigrationDepartmentMSP.member','CoordinatorMSP.member', 'UniversityMSP.member', 'BankMSP.member', 'EmployerMSP.member', 'BorderControlMSP.member', 'PoliceMSP.member', 'MedicalAssociationMSP.member')" \
      --tls \
      --cafile ${ORDERER_TLS_ROOTCERT_FILE} \
      --peerAddresses peer0.requester.evidentia.net:7051 \
      --tlsRootCertFiles ${ORG1_TLS_ROOTCERT_FILE}

  echo "Waiting for instantiation request to be committed ..."
  sleep 10

  echo "Submitting initLedger transaction to smart contract on evidentiachannel"
  echo "The transaction is sent to the 7 peers with the chaincode installed so that chaincode is built before receiving the following requests"
  docker exec \
    -e CORE_PEER_LOCALMSPID=RequesterMSP \
    -e CORE_PEER_MSPCONFIGPATH=${ORG1_MSPCONFIGPATH} \
    cli \
    peer chaincode invoke \
      -o orderer.evidentia.net:7050 \
      -C evidentiachannel \
      -n evidentia \
      -c '{"function":"initLedger","Args":[]}' \
      --waitForEvent \
      --tls \
      --cafile ${ORDERER_TLS_ROOTCERT_FILE} \
      --peerAddresses peer0.requester.evidentia.net:7051 \
      --peerAddresses peer0.ind.evidentia.net:9051 \
      --peerAddresses peer0.coord.evidentia.net:11051 \
      --peerAddresses peer0.uni.evidentia.net:13051 \
      --peerAddresses peer0.bank.evidentia.net:15051 \
      --peerAddresses peer0.employer.evidentia.net:17051 \
      --peerAddresses peer0.bc.evidentia.net:19051 \
      --peerAddresses peer0.police.evidentia.net:21051 \
      --peerAddresses peer0.medicine.evidentia.net:23051 \
      --tlsRootCertFiles ${ORG1_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG2_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG3_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG4_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG5_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG6_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG7_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG8_TLS_ROOTCERT_FILE} \
      --tlsRootCertFiles ${ORG9_TLS_ROOTCERT_FILE}
  set +x

cat <<EOF

  Total setup execution time : $(($(date +%s) - starttime)) secs ...

  ###### NETWORK IS UP. You might now use it for Immigration. #######

EOF
}



function networkDown() {
  ./stopFabric.sh
}

FLAG=false

if [ "${NETWORK_UP}" ]; then
  networkUp
  FLAG=true
fi

if [ "${EXPLORER}" ]; then
  startExplorer
  FLAG=true
fi

if [ "${NETWORK_DOWN}" ]; then
  networkDown
  FLAG=true
fi

if [ "${HELP}" ]; then
  printHelp
  FLAG=true
  exit 1;
fi

if [ "${FLAG}" == false ]; then
  printHelp
  exit 1;
fi
