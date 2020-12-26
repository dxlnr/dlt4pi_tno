#!/bin/bash

# This script registers all users

# Exit on first error
set -e

MC_USER=mc
SA_USER=sa
COORD_USER=coord
CLIENT_USER_USER=user

cd register/
node registerCoordinator.js $COORD_USER 
node registerMCUser.js $MC_USER
node registerSAUser.js $SA_USER
node registerClientUser.js $CLIENT_USER_USER

echo '============= All users are registered successfully ============='
echo "CoordinatorOrg:" ${COORD_USER}
echo "RequesterOrg:" ${MC_USER}
echo "ConsulateOrg:" ${SA_USER}
echo "ClientUser:" ${CLIENT_USER_USER}