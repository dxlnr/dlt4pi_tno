/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const { FileSystemWallet, Gateway, X509WalletMixin } = require('fabric-network');
const path = require('path');

const ccpPath = path.resolve(__dirname, '..', '..', 'evidentia-network', 'connection-clientUser.json');

async function main() {
    var args = process.argv.slice(2);
    if(args.length < 2) {
        console.log('Wrong number of arguments - Identity username and admin username are missing')
        process.exit(1);
    }
    if(args.length > 2) {
        console.log('Wrong number of arguments - Requires 2 argument')        
        process.exit(1);
    }
    
    const username = args[0]
    const admin = args[1]
    try {

        // Create a new file system based wallet for managing identities.
        const walletPath = path.join(process.cwd(), 'wallet');
        const wallet = new FileSystemWallet(walletPath);
        console.log(`Wallet path: ${walletPath}`);

        // Check to see if we've already enrolled the admin user.
        const adminExists = await wallet.exists(admin);
        if (!adminExists) {
            console.log('An identity for the admin user "admin_clientUser" does not exist in the wallet');
            console.log('Run the enrollAdmin.js application before retrying');
            return;
        }

        // Create a new gateway for connecting to our peer node.
        const gateway = new Gateway();
        await gateway.connect(ccpPath, { wallet, identity: admin, discovery: { enabled: true, asLocalhost: true } });

        // Get the CA client object from the gateway for interacting with the CA.
        const ca = gateway.getClient().getCertificateAuthority();
        const adminIdentity = gateway.getCurrentIdentity();
        const idService = ca.newIdentityService();
        await idService.delete(username, adminIdentity)
        await wallet.delete(username)
        console.log('Successfully removed user', username, 'and deleted from the wallet');

    } catch (error) {
        console.error(`Failed to register user "${username}": ${error}`);
        process.exit(1);
    }
}

main();
