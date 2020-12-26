#!/bin/bash

# This script enrolls all the admin users

# Exit on first error
set -e

#TODO get admin usernames from command line 

cd enroll/
node enrollCoordAdmin.js 
node enrollMCAdmin.js 
node enrollSAAdmin.js 
node enrollClientUserAdmin.js 


echo '============= All admin users were registered successfully ============='
# echo "CoordinatorOrg:" ${COORD_USER}
# echo "RequesterOrg:" ${MC_USER}
# echo "ConsulateOrg:" ${SA_USER}
# echo "ClientUser:" ${CLIENT_USER_USER}