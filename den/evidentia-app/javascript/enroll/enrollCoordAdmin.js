/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const FabricCAServices = require('fabric-ca-client');
const { FileSystemWallet, X509WalletMixin } = require('fabric-network');
const fs = require('fs');
const path = require('path');

const ccpPath = path.resolve(__dirname, '..', '..', '..', 'evidentia-network', 'connection-profiles','connection-coord.json');
const ccpJSON = fs.readFileSync(ccpPath, 'utf8');
const ccp = JSON.parse(ccpJSON);

async function main() {
    try {

        // Create a new CA client for interacting with the CA.
        const caInfo = ccp.certificateAuthorities['ca.coord.evidentia.net'];
        const caTLSCACerts = caInfo.tlsCACerts.pem;
        const ca = new FabricCAServices(caInfo.url, { trustedRoots: caTLSCACerts, verify: false }, caInfo.caName);

        // Create a new file system based wallet for managing identities.
        const walletPath = path.join(process.cwd(), '../wallet');
        const wallet = new FileSystemWallet(walletPath);
        console.log(`Wallet path: ${walletPath}`);

        // Check to see if we've already enrolled the admin user.
        const adminExists = await wallet.exists('admin_coord');
        if (adminExists) {
            console.log('An identity for the admin user "admin_coord" already exists in the wallet');
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        const enrollment = await ca.enroll({ enrollmentID: 'admin_coord', enrollmentSecret: 'adminpw' });
        const identity = X509WalletMixin.createIdentity('CoordinatorMSP', enrollment.certificate, enrollment.key.toBytes());
        await wallet.import('admin_coord', identity);
        console.log('Successfully enrolled admin user "admin_coord" and imported it into the wallet');

    } catch (error) {
        console.error(`Failed to enroll admin user "admin_coord": ${error}`);
        process.exit(1);
    }
}

main();
