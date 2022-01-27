# wrapper-chainlink

Wrapper for using Chainlink API and simplifying interaction with Chainlink blockchain (LINK coin).

## Intro

Chainlink is a decentralized oracle network that provides real-world data to smart contracts on the blockchain. Smart contracts are pre-specified agreements on the blockchain that evaluate information and automatically execute when certain conditions are met. LINK tokens are the digital asset token used to pay for services on the network.
The Chainlink uses LINK coins as payment for the work.
**LINK** tokens are used to pay node operators for retrieving data for smart contracts and also for deposits placed by node operators as required by contract creators.

The LINK token is an ERC677 token that inherits functionality from the ERC20 token standard and allows token transfers to contain a data payload. 
Read more about the [ERC677 transferAndCall token standard](https://github.com/ethereum/EIPs/issues/677)

## Useful links:

- [Detailed description of the chainlink blockchain, its purpose and functionality](https://www.gemini.com/cryptopedia/what-is-chainlink-and-how-does-it-work)
- [Chainlink official documentation](https://docs.chain.link/docs)
- [Service for monitoring actions in Rinkeby testnet chainlink](https://rinkeby.etherscan.io/)
- [Ethereum documentation](https://github.com/ethereum/wiki/wiki)


### ERC20 token:

In fact, a Chainlink is not a separate cryptocurrency, but an Ethereum-dependent token that is used as payments for smart contracts. Therefore, we must interact not with the **Chainlink** node, but with the **Ethereum** node

- [About ERC20](https://ethereum.org/en/developers/docs/standards/tokens/erc-20/)

### For developers:

Since today the official Chainlink API does not have the necessary tools for such actions as: *transactions, search for transactions by hashes, creating an account*, therefore we wrote a custom API using the [web3.js](https://web3js.readthedocs.io/en/v1.3.0/) library

The [Infura](https://infura.io/) service was used to communicate with the blockchain. The web3 library establishes a connection to the node using a special unique link.
Further, using the built-in asynchronous methods of the library, we implemented communication with the Ethereum blockchain, which made it possible for us to implement the operations we needed to work with both the Chainlink and Ethereum blockchains using a special json called ABI.
JS-Chainlink-api is located in the project along the following path: `/wrapper-chainlink/src/main/resources/wrapper/chainlink`

However, you do not need to understand it unnecessarily, since its launch is configured automatically using **Docker**.To start the project, you just need to execute the following commands in the cmd: 

```
docker build -t wraper-chainlink .
docker run -ti -p $PORT:$PORT wraper-chainlink
```