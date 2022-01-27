var Web3 = require('web3')
var Tx = require('ethereumjs-tx')

var abiDecoder = require('abi-decoder');
const restify = require('restify');


var web3 = new Web3(new Web3.providers.HttpProvider("https://rinkeby.infura.io/v3/35e27357521343c08e354365a33b6193"));


// ABI -- spetial JSON for interract with ethereum and ERC20 token
var standardAbi = [{
  "constant": true,
  "inputs": [],
  "name": "name",
  "outputs": [{
    "name": "",
    "type": "bytes32"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [],
  "name": "stop",
  "outputs": [],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "guy",
    "type": "address"
  }, {
    "name": "wad",
    "type": "uint256"
  }],
  "name": "approve",
  "outputs": [{
    "name": "",
    "type": "bool"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "owner_",
    "type": "address"
  }],
  "name": "setOwner",
  "outputs": [],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [],
  "name": "totalSupply",
  "outputs": [{
    "name": "",
    "type": "uint256"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "src",
    "type": "address"
  }, {
    "name": "dst",
    "type": "address"
  }, {
    "name": "wad",
    "type": "uint256"
  }],
  "name": "transferFrom",
  "outputs": [{
    "name": "",
    "type": "bool"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [],
  "name": "decimals",
  "outputs": [{
    "name": "",
    "type": "uint256"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "dst",
    "type": "address"
  }, {
    "name": "wad",
    "type": "uint128"
  }],
  "name": "push",
  "outputs": [{
    "name": "",
    "type": "bool"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "name_",
    "type": "bytes32"
  }],
  "name": "setName",
  "outputs": [],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "wad",
    "type": "uint128"
  }],
  "name": "mint",
  "outputs": [],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [{
    "name": "src",
    "type": "address"
  }],
  "name": "balanceOf",
  "outputs": [{
    "name": "",
    "type": "uint256"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [],
  "name": "stopped",
  "outputs": [{
    "name": "",
    "type": "bool"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "authority_",
    "type": "address"
  }],
  "name": "setAuthority",
  "outputs": [],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "src",
    "type": "address"
  }, {
    "name": "wad",
    "type": "uint128"
  }],
  "name": "pull",
  "outputs": [{
    "name": "",
    "type": "bool"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [],
  "name": "owner",
  "outputs": [{
    "name": "",
    "type": "address"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "wad",
    "type": "uint128"
  }],
  "name": "burn",
  "outputs": [],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [],
  "name": "symbol",
  "outputs": [{
    "name": "",
    "type": "bytes32"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [{
    "name": "dst",
    "type": "address"
  }, {
    "name": "wad",
    "type": "uint256"
  }],
  "name": "transfer",
  "outputs": [{
    "name": "",
    "type": "bool"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": false,
  "inputs": [],
  "name": "start",
  "outputs": [],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [],
  "name": "authority",
  "outputs": [{
    "name": "",
    "type": "address"
  }],
  "payable": false,
  "type": "function"
}, {
  "constant": true,
  "inputs": [{
    "name": "src",
    "type": "address"
  }, {
    "name": "guy",
    "type": "address"
  }],
  "name": "allowance",
  "outputs": [{
    "name": "",
    "type": "uint256"
  }],
  "payable": false,
  "type": "function"
}, {
  "inputs": [{
    "name": "symbol_",
    "type": "bytes32"
  }],
  "payable": false,
  "type": "constructor"
}, {
  "anonymous": true,
  "inputs": [{
    "indexed": true,
    "name": "sig",
    "type": "bytes4"
  }, {
    "indexed": true,
    "name": "guy",
    "type": "address"
  }, {
    "indexed": true,
    "name": "foo",
    "type": "bytes32"
  }, {
    "indexed": true,
    "name": "bar",
    "type": "bytes32"
  }, {
    "indexed": false,
    "name": "wad",
    "type": "uint256"
  }, {
    "indexed": false,
    "name": "fax",
    "type": "bytes"
  }],
  "name": "LogNote",
  "type": "event"
}, {
  "anonymous": false,
  "inputs": [{
    "indexed": true,
    "name": "authority",
    "type": "address"
  }],
  "name": "LogSetAuthority",
  "type": "event"
}, {
  "anonymous": false,
  "inputs": [{
    "indexed": true,
    "name": "owner",
    "type": "address"
  }],
  "name": "LogSetOwner",
  "type": "event"
}, {
  "anonymous": false,
  "inputs": [{
    "indexed": true,
    "name": "from",
    "type": "address"
  }, {
    "indexed": true,
    "name": "to",
    "type": "address"
  }, {
    "indexed": false,
    "name": "value",
    "type": "uint256"
  }],
  "name": "Transfer",
  "type": "event"
}, {
  "anonymous": false,
  "inputs": [{
    "indexed": true,
    "name": "owner",
    "type": "address"
  }, {
    "indexed": true,
    "name": "spender",
    "type": "address"
  }, {
    "indexed": false,
    "name": "value",
    "type": "uint256"
  }],
  "name": "Approval",
  "type": "event"
}]

//this is temporary way to make async ethereum transactions 
var count = 135;

/////////////// ---------------------------   Balance    --------------------------- ///////////////
async function getERC20Balance(address, standardAbi) {
  try {
    var tokenContract, decimals, balance, name, symbol, adjustedBalance

    var contractAddress = "0x01be23585060835e02b77ef475b0cc51aa1e0709"

    contract = new web3.eth.Contract(standardAbi, contractAddress)

    balance = await contract.methods.balanceOf(address).call()
    decimals = await contract.methods.decimals().call()
    balance = balance / Math.pow(10, decimals);

    console.log("[INFO]: success getBalance requst");

    return new Balance(balance.toString());
  } catch (e) {
    console.log("[ERROR]: " + e);
    return "wrong address"
  }
}

/////////////// ---------------------------   Transaction    --------------------------- ///////////////
async function sendERC20(transaction, standardAbi) {
  try {
    var contractAddress = "0x01be23585060835e02b77ef475b0cc51aa1e0709"

    var privateKey = new Buffer.from(transaction.privateKey, 'hex')
    var amount = web3.utils.toHex(parseFloat(transaction.quantity + "e18"))

    // get transaction count, later will used as nonce
    var localCount = await (web3.eth.getTransactionCount(transaction.from))

    var contract = new web3.eth.Contract(standardAbi, contractAddress, {
      from: transaction.from
    })

    var rawTransaction = {
      "from": transaction.from,
      "gasPrice": web3.utils.toHex(2 * 1e9),
      "gasLimit": web3.utils.toHex(210000),
      "to": contractAddress,
      "value": "0x0",
      "data": contract.methods.transfer(transaction.to, amount).encodeABI(),
      "nonce": web3.utils.toHex(localCount)
    }
    var txTransaction = new Tx(rawTransaction)

    txTransaction.sign(privateKey)

    transactionСontent = await web3.eth.sendSignedTransaction('0x' + txTransaction.serialize().toString('hex'))
    console.log("[INFO]: success transaction");
    return new Transaction(
      transactionСontent.transactionHash,
      transaction.to,
      transaction.from,
      transaction.quantity,
      transactionСontent.logs[0].blockNumber)
  } catch (e) {

    console.log("[ERROR]: " + e);
    return "transaction failed"
  }
}

/////////////// ---------------------------   Account    --------------------------- //////////////
async function createAdress() {
  try {
    account = await web3.eth.accounts.create();

    console.log("[INFO]: adress was create succesfull");
    return account;
  } catch (e) {

    console.log("[ERROR]: + e");
    return "error"
  }
}

/////////////// ---------------------------   Transactions List    --------------------------- //////////////
async function getERC20TransactionsByAddress(address, fromBlock, standardAbi) {
  tokenContractAddress = "0x01be23585060835e02b77ef475b0cc51aa1e0709"
  tokenDecimals = 18;

  // initialize the ethereum client
  const currentBlockNumber = await web3.eth.getBlockNumber();

  const contract = new web3.eth.Contract(standardAbi, tokenContractAddress);

  const transferEvents = await contract.getPastEvents("Transfer", {
    fromBlock,
    filter: {
      isError: 0,
      txreceipt_status: 1
    },
    topics: [
      web3.utils.sha3("Transfer(address,address,uint256)"),
      null,
      web3.utils.padLeft(address, 64)
    ]
  });

  console.log("[INFO]: succesfull get transactionsList request");

  return transferEvents
    .sort((evOne, evTwo) => evOne.blockNumber - evTwo.blockNumber)
    .map(({
      blockNumber,
      transactionHash,
      returnValues
    }) => {
      return new Transaction(transactionHash, returnValues.to, returnValues.from, (returnValues.value * Math.pow(10, -tokenDecimals)), blockNumber);
    });
}

/////////////// ---------------------------   GET Transactions   --------------------------- //////////////
async function getERC20Transaction(hash, standardAbi) {
  tokenContractAddress = "0x01be23585060835e02b77ef475b0cc51aa1e0709"

  const currentBlockNumber = await web3.eth.getBlockNumber();
  const contract = new web3.eth.Contract(standardAbi, tokenContractAddress);

  const transfer = await web3.eth.getTransaction(hash)

  abiDecoder.addABI(standardAbi);
  const decodedData = abiDecoder.decodeMethod(transfer.input);

  var to = decodedData.params[0].value;
  var amount = decodedData.params[1].value * Math.pow(10, -18);

  console.log("[INFO]: succesfull get transaction request");
  return new Transaction(transfer.hash, to, transfer.from, amount, transfer.blockNumber);
}

/////////////// ---------------------------   Ethereum Transaction    --------------------------- ///////////////
async function sendETH(transaction, localCount, standardAbi) {
  try {

    var privateKey = new Buffer.from(transaction.privateKey, 'hex')
    var amount = web3.utils.toHex(parseFloat(transaction.quantity + "e18"))

    chainID = await web3.eth.getChainId()

    var rawTransaction = {
      "from": transaction.from,
      "nonce": web3.utils.toHex(localCount),
      "gasPrice": web3.utils.toHex(2 * 1e9),
      "gasLimit": web3.utils.toHex(210000),
      "to": transaction.to,
      "value": amount,
      "chainId": chainID
    }
    var txTransaction = new Tx(rawTransaction)

    txTransaction.sign(privateKey)

    transactionСontent = await web3.eth.sendSignedTransaction('0x' + txTransaction.serialize().toString('hex'))

    console.log("[INFO]: success transaction");
    return new Transaction(
      transactionСontent.transactionHash,
      transaction.to,
      transaction.from,
      transaction.quantity,
      transactionСontent.blockNumber)
  } catch (e) {

    console.log("[ERROR]: " + e);
    return "transaction failed"
  }
}

/////////////// ---------------------------   Classes    --------------------------- ///////////////
class Balance {
  constructor(balance) {
    this.amount = balance;
  }
}

class Transaction {
  constructor(transactionHash, to, from, amount, blockNumber) {
    this.transactionHash = transactionHash;
    this.to = to;
    this.from = from;
    this.amount = amount;
    this.blockNumber = blockNumber;
  }
}

/////////////// ---------------------------   Create REST    --------------------------- ///////////////
var server = restify.createServer();
server.use(restify.plugins.bodyParser({
  mapParams: true
}));


// POST transaction request
server.post('/transactions', function(req, res, next) {
  console.log(req.body);
  sendERC20(req.body, standardAbi).then(returnedStatus => {
    res.send(returnedStatus);
  });
  return next();
});

// POST ethereum transaction request
server.post('/transactions/eth', function(req, res, next) {
  console.log(req.body);
  localCount = count;
  count++;
  sendETH(req.body, localCount, standardAbi).then(returnedStatus => {
    res.send(returnedStatus);
  });
  return next();
});

// GET transactions list
server.get('/transactions/list/:address/:block', function(req, res, next) {

  getERC20TransactionsByAddress(req.params.address, req.params.block, standardAbi).then(transactionsList => {
    res.send(transactionsList);
    return next();
  })
});

// GET transaction
server.get('/transaction/:hash', function(req, res, next) {

  getERC20Transaction(req.params.hash, standardAbi).then(transaction => {
    res.send(transaction);
    return next();
  })
});

// GET balance request
server.get('/balance/:address', function(req, res, next) {

  getERC20Balance(req.params.address, standardAbi).then(balance => {
    if (balance == "wrong address") {
      res.send("wrong address!")
    } else {
      res.send(balance);
    }
  })

  return next();
});

// GET create adress
server.get('/address', function(req, res, next) {
  createAdress().then(result => {
    res.send(result)
  });
  return next();
});


server.listen(8280, '127.0.0.1', function() {
  console.log('%s listening at %s', server.name, server.url);
});
