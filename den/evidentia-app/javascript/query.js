/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const { FileSystemWallet, Gateway } = require('fabric-network');
const path = require('path');

const ccpPath = path.resolve(__dirname, '..', '..', 'evidentia-network', 'connection-mc.json');

async function main() {
    var args = process.argv.slice(2);
    if(args.length < 1)
        console.log('Wrong number of arguments - Identity username is missing')
    if(args.length > 1)
        console.log('Wrong number of arguments - Requires 1 argument')
    
    var username = args[0]
    try {

        // Create a new file system based wallet for managing identities.
        const walletPath = path.join(process.cwd(), 'wallet');
        const wallet = new FileSystemWallet(walletPath);
        console.log(`Wallet path: ${walletPath}`);

        // Check to see if we've already enrolled the user.
        const userExists = await wallet.exists(username);
        if (!userExists) {
            console.log(`An identity for the user "${username}" does not exist in the wallet`);
            console.log('Run the registerUser.js application before retrying');
            return;
        }

        // Create a new gateway for connecting to our peer node.
        const gateway = new Gateway();
        await gateway.connect(ccpPath, { wallet, identity: username, discovery: { enabled: true, asLocalhost: true } });

        // Get the network (channel) our contract is deployed to.
        const network = await gateway.getNetwork('evidentiachannel');

        // Get the contract from the network.
        const contract = network.getContract('evidentia');

        // Evaluate the specified transaction.
        const result = await contract.evaluateTransaction('getAsset', 'cbmc');
        console.log(`Transaction has been evaluated, result is: ${result.toString()}`);

    } catch (error) {
        console.error(`Failed to evaluate transaction: ${error}`);
        process.exit(1);
    }
}

main();
