#!/bin/bash

function one_line_pem {
    echo "`awk 'NF {sub(/\\n/, ""); printf "%s\\\\\\\n",$0;}' $1`"
}

function json_ccp {
    local PP=$(one_line_pem $6)
    local CP=$(one_line_pem $7)
    sed -e "s/\${ORG}/$1/" \
        -e "s/\${org}/$2/" \
        -e "s/\${P0PORT}/$3/" \
        -e "s/\${P1PORT}/$4/" \
        -e "s/\${CAPORT}/$5/" \
        -e "s#\${PEERPEM}#$PP#" \
        -e "s#\${CAPEM}#$CP#" \
        ccp-template.json
}

function yaml_ccp {
    local PP=$(one_line_pem $6)
    local CP=$(one_line_pem $7)
    sed -e "s/\${ORG}/$1/" \
        -e "s/\${org}/$2/" \
        -e "s/\${P0PORT}/$3/" \
        -e "s/\${P1PORT}/$4/" \
        -e "s/\${CAPORT}/$5/" \
        -e "s#\${PEERPEM}#$PP#" \
        -e "s#\${CAPEM}#$CP#" \
        ccp-template.yaml | sed -e $'s/\\\\n/\\\n        /g'
}

ORG=Requester
org=requester
P0PORT=7051
P1PORT=8051
CAPORT=7054
PEERPEM=crypto-config/peerOrganizations/requester.evidentia.net/tlsca/tlsca.requester.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/requester.evidentia.net/ca/ca.requester.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-requester.json

ORG=ImmigrationDepartment
org=ind
P0PORT=9051
P1PORT=10051
CAPORT=8054
PEERPEM=crypto-config/peerOrganizations/ind.evidentia.net/tlsca/tlsca.ind.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/ind.evidentia.net/ca/ca.ind.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-ind.json

ORG=Coordinator
org=coord
P0PORT=11051
P1PORT=12051
CAPORT=9054
PEERPEM=crypto-config/peerOrganizations/coord.evidentia.net/tlsca/tlsca.coord.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/coord.evidentia.net/ca/ca.coord.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-coord.json

ORG=University
org=uni
P0PORT=13051
P1PORT=14051
CAPORT=10054
PEERPEM=crypto-config/peerOrganizations/uni.evidentia.net/tlsca/tlsca.uni.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/uni.evidentia.net/ca/ca.uni.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-uni.json

ORG=Bank
org=bank
P0PORT=15051
P1PORT=16051
CAPORT=11054
PEERPEM=crypto-config/peerOrganizations/bank.evidentia.net/tlsca/tlsca.bank.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/bank.evidentia.net/ca/ca.bank.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-bank.json

ORG=Employer
org=employer
P0PORT=17051
P1PORT=18051
CAPORT=12054
PEERPEM=crypto-config/peerOrganizations/employer.evidentia.net/tlsca/tlsca.employer.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/employer.evidentia.net/ca/ca.employer.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-employer.json

ORG=BorderControl
org=bc
P0PORT=19051
P1PORT=20051
CAPORT=13054
PEERPEM=crypto-config/peerOrganizations/bc.evidentia.net/tlsca/tlsca.bc.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/bc.evidentia.net/ca/ca.bc.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-bc.json

ORG=Police
org=police
P0PORT=21051
P1PORT=22051
CAPORT=14054
PEERPEM=crypto-config/peerOrganizations/police.evidentia.net/tlsca/tlsca.police.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/police.evidentia.net/ca/ca.police.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-police.json

ORG=MedicalAssociation
org=medicine
P0PORT=23051
P1PORT=24051
CAPORT=15054
PEERPEM=crypto-config/peerOrganizations/medicine.evidentia.net/tlsca/tlsca.medicine.evidentia.net-cert.pem
CAPEM=crypto-config/peerOrganizations/medicine.evidentia.net/ca/ca.medicine.evidentia.net-cert.pem

echo "$(json_ccp $ORG $org $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM)" > connection-profiles/connection-medicine.json
