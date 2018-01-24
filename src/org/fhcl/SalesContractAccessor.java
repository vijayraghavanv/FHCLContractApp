package org.fhcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fhcl.common.SampleOrg;
import org.fhcl.common.SampleStore;
import org.fhcl.common.SampleUser;
import org.fhcl.utils.ContractException;
import org.hyperledger.fabric.protos.peer.Query;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static java.lang.String.format;

public class SalesContractAccessor {
    private final Log logger = LogFactory.getLog(this.getClass());
    Set<Peer> peers = new HashSet<>();
    File sampleStoreFile = null;
    SampleOrg sampleOrg = null;
    SampleUser admin = null;
    private SampleStore sampleStore;
    private HFClient fhclClient = null;
    private String orgName = null;
    private String mspID = null;
    private String peerName = null;
    private String peerLocation = null;
    private String userName = null;
    private String userMSP = null;
    private String userOrg = null;
    private String keyFilePath = null;
    private String certPath = null;
    private String orderer = null;
    private String ordererLocation = null;
    private String channelName = null;
    private String chaincodeName = null;

    public SalesContractAccessor(String orgName, String mspID, String userName, String userOrg, String userMSP, String certFile, String keyFilePath) throws ContractException {
        sampleStoreFile = new File(System.getProperty("java.io.tmpdir") + "/SalesContractAccessor.properties");
        out(sampleStoreFile.getAbsolutePath());
        if (sampleStoreFile.exists()) { //For testing start fresh
            sampleStoreFile.delete();
        }
        sampleStore = new SampleStore(sampleStoreFile);

        SampleOrg sampleOrg = new SampleOrg(orgName, mspID);
        sampleOrg.addPeerLocation(orgName, "grpc://129.152.132.229:7051");
        setOrgName(orgName);
        setMspID(mspID);
        setUserName(userName);
        setUserMSP(userMSP);
        setUserOrg(userOrg);
        setCertPath(certFile);
        setKeyFilePath(keyFilePath);
        try {
            this.fhclClient = HFClient.createNewInstance();
            this.fhclClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
            admin = sampleStore.getMember(getUserName(), getUserOrg(), getUserMSP(),
                    Util.findFileSk(Paths.get(getKeyFilePath()).toFile()), Paths.get(getCertPath()).toFile());
            if (admin == null) {
                throw new ContractException("Admin object is null. Please check if it has been created correctly");
            }
            sampleOrg.setAdmin(admin);
            fhclClient.setUserContext(sampleOrg.getAdmin());

        } catch (CryptoException e) {
            throw new ContractException("Crypto Exception: " + e.getMessage());
        } catch (InvalidArgumentException e) {
            throw new ContractException("Invalid arguments found: " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ContractException("Invalid or Illegal Access found: " + e.getMessage());
        } catch (InstantiationException e) {
            throw new ContractException("Instantiation Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new ContractException("Class Not found: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new ContractException("Method Not Found: " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ContractException("Target problem : " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new ContractException("Algorithm not found :" + e.getMessage());
        } catch (IOException e) {
            throw new ContractException("IO Exception: " + e.getMessage());
        } catch (NoSuchProviderException e) {
            throw new ContractException("Provider not found: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new ContractException("Invalid Key spec: " + e.getMessage());
        }

    }

    private static void out(String format, Object... args) {
        System.err.flush();
        System.out.flush();
        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();
    }

    public static void main(String[] args) throws IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, CryptoException {
//        SalesContractAccessor test = new SalesContractAccessor("cts", "cts");
//        test.test();
    }

    public String getChaincodeName() {
        return chaincodeName;
    }

    public void setChaincodeName(String chaincodeName) {
        this.chaincodeName = chaincodeName;
    }

    public void addPeer(String peerName, String peerLocation) throws ContractException {
        try {
            if (peerName == null) {
                throw new ContractException("Unable to add to peers - Peer name is null");
            }
            if (peerLocation == null) {
                throw new ContractException("Unable to add to peers - Peer location is null");
            }
            Peer peer = this.fhclClient.newPeer(peerName, peerLocation);
            peers.add(peer);
        } catch (InvalidArgumentException e) {
            throw new ContractException("Unable to add peer due to invalid argument " + e.getMessage());
        }
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getOrdererLocation() {
        return ordererLocation;
    }

    public void setOrdererLocation(String ordererLocation) {
        this.ordererLocation = ordererLocation;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getKeyFilePath() {
        return keyFilePath;
    }

    public void setKeyFilePath(String keyFilePath) {
        this.keyFilePath = keyFilePath;
    }

    public String getUserMSP() {
        return userMSP;
    }

    public void setUserMSP(String userMSP) {
        this.userMSP = userMSP;
    }

    public String getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(String userOrg) {
        this.userOrg = userOrg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPeerName() {
        return peerName;
    }

    public void setPeerName(String peerName) {
        this.peerName = peerName;
    }

    public String getPeerLocation() {
        return peerLocation;
    }

    public void setPeerLocation(String peerLocation) {
        this.peerLocation = peerLocation;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMspID() {
        return mspID;
    }

    public void setMspID(String mspID) {
        this.mspID = mspID;
    }

    private ChaincodeID getChaincodeIDByName(String ccName, Channel channel) throws ContractException {
        Collection<Peer> peers = channel.getPeers();
        Peer next = null;

        if (peers.isEmpty()) throw new ContractException("No peers found in channel: " + channel.getName());
        Iterator<Peer> iterator = peers.iterator();
        iterator.hasNext();
        next = iterator.next(); //Since all peers have chaincode instantiated, we can query the channel with any peer that is available.
        try {
            List<Query.ChaincodeInfo> ccinfoList = channel.queryInstantiatedChaincodes(next);
            for (int i = 0; i < ccinfoList.size(); i++) {
                Query.ChaincodeInfo chaincodeInfo = ccinfoList.get(i);
                String version = chaincodeInfo.getVersion();
                if (chaincodeInfo.getName().equals(ccName)) {
                    return ChaincodeID.newBuilder().setName(chaincodeInfo.getName()).setVersion(version).build();
                }
            }
        } catch (InvalidArgumentException e) {
            throw new ContractException("Unable to get chaincode lists: " + e.getMessage());
        } catch (ProposalException e) {
            throw new ContractException(e.getMessage());
        }
        return null;

    }
    public String getContract(String contractID) throws ContractException {
        Channel channel=setup();
        String output=null;
        QueryByChaincodeRequest queryByChaincodeRequest = fhclClient.newQueryProposalRequest();
        queryByChaincodeRequest.setArgs(new String[]{"1"});
        queryByChaincodeRequest.setFcn("query");
        queryByChaincodeRequest.setChaincodeID(getChaincodeIDByName(getChaincodeName(),channel));
        Collection<ProposalResponse> queryProposals = null;
        try {
            queryProposals = channel.queryByChaincode(queryByChaincodeRequest);
        } catch (InvalidArgumentException e) {
            throw new ContractException("Invalid arguments: " + e.getMessage());
        } catch (ProposalException e) {
            throw new ContractException("Proposal Exception: " + e.getMessage());
        }
        for (ProposalResponse proposalResponse : queryProposals) {
            output = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
            out("The output from peer %s, is: %s", proposalResponse.getPeer().toString(), output);
        }
        return output;
    }
    public String getHistoryByKey(String key) throws ContractException {
        Channel channel = setup();
        ChaincodeID chaincodeID = getChaincodeIDByName(getChaincodeName(), channel);
        if (chaincodeID == null) {
            throw new ContractException("Chaincode ID is null");
        }
        TransactionProposalRequest transactionProposalRequest = fhclClient.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setFcn("history");
        transactionProposalRequest.setArgs(new String[]{key});
        transactionProposalRequest.setProposalWaitTime(120000); //set proposal wait time to 2 mins
        transactionProposalRequest.setUserContext(fhclClient.getUserContext());
        out("sending proposal request to all peers");
        try {
            Collection<ProposalResponse> invokePropResponse = channel.sendTransactionProposal(transactionProposalRequest);
            Iterator<ProposalResponse> proposalResponseIterator = invokePropResponse.iterator();
            if (proposalResponseIterator.hasNext()) {
                ProposalResponse proposalResponse = proposalResponseIterator.next();
                String s = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
                out("The output is: %s", s);
                String g=proposalResponse.getProposalResponse().getResponse().getMessage();
                return s;
            }
        } catch (ProposalException e) {
            throw new ContractException("Proposal exception : " + e.getMessage());
        } catch (InvalidArgumentException e) {
            throw new ContractException("Invalid arguments:" + e.getMessage());
        }
        return null;
    }

    public void writeContract(String json) throws ContractException {
        Channel channel = setup();
        ChaincodeID chaincodeID = getChaincodeIDByName(getChaincodeName(), channel);
        if (chaincodeID == null) {
            throw new ContractException("Chaincode ID is null");
        }
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();
        TransactionProposalRequest transactionProposalRequest = fhclClient.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setFcn("set");
        transactionProposalRequest.setArgs(new String[]{"2", json});
        transactionProposalRequest.setProposalWaitTime(120000); //set proposal wait time to 2 mins
        transactionProposalRequest.setUserContext(fhclClient.getUserContext());
        out("sending proposal request to all peers");
        try {
            Collection<ProposalResponse> invokePropResponse = channel.sendTransactionProposal(transactionProposalRequest);
            for (Iterator<ProposalResponse> proposalResponseIterator = invokePropResponse.iterator(); proposalResponseIterator.hasNext(); ) {
                ProposalResponse proposalResponse = proposalResponseIterator.next();
                if (proposalResponse.getStatus() == ChaincodeResponse.Status.SUCCESS) {
                    out("Successful transaction proposal response Txid: %s from peer %s", proposalResponse.getTransactionID(), proposalResponse.getPeer().getName());
                    successful.add(proposalResponse);
                } else {
                    out("Failure transaction proposal response Txid: %s from peer %s", proposalResponse.getTransactionID(), proposalResponse.getPeer().getName());
                    failed.add(proposalResponse);
                }
            }
            out("Received %d transaction proposal responses. Successful+verified: %d . Failed: %d",
                    invokePropResponse.size(), successful.size(), failed.size());
            if (failed.size() > 0) {
                ProposalResponse firstTransactionProposalResponse = failed.iterator().next();

                throw new ContractException(format("Not enough endorsers for invoke :%d endorser error:%s. Was verified:%b",
                         firstTransactionProposalResponse.getStatus().getStatus(), firstTransactionProposalResponse.getMessage(), firstTransactionProposalResponse.isVerified()));

            }
            out("Successfully received transaction proposal responses.");

            ////////////////////////////
            // Send transaction to orderer

            if (fhclClient.getUserContext() != null) {
                 channel.sendTransaction(successful, fhclClient.getUserContext());
            }
            else
             channel.sendTransaction(successful);
        } catch (ProposalException e) {
            throw new ContractException("Proposal Exception" + e.getMessage());
        } catch (InvalidArgumentException e) {
            throw new ContractException("Invalid Arguments" + e.getMessage());
        }


    }

    private void checkNulls() throws ContractException {
        if (peerName == null) {
            throw new ContractException("Set Peer Name prior to writing contract");
        }
        if (peerLocation == null) {
            throw new ContractException("Set Peer Location prior to writing contract. Should be of format: grpc://ip-add:portno e.g - grpc://129.152.132.80:7051");

        }
        if (userName == null) {
            throw new ContractException("User Name cannot be null");
        }
        if (userOrg == null) {
            throw new ContractException("User org has to be set");
        }
        if (userMSP == null) {
            throw new ContractException("User MSP has to be set.");
        }
        if (keyFilePath == null) {
            throw new ContractException("Please provide correct keyfile path . E.g. /home/keyfiles or /Users/keyfiles.This location should contain the key file ending with _sk");
        }
        if (certPath == null) {
            throw new ContractException("Certificate path has to be set. This should point to the peer's admin certificate and should have extension .pem");
        }
        if (channelName == null) {
            throw new ContractException("Please provide channel name. This will be the channel that would be queried");
        }
        if (orderer == null) {
            throw new ContractException("Orderer name has to be set");
        }
        if (ordererLocation == null) {
            throw new ContractException("Please set orderer url. Format is: grpc://IP_ADDR:PORT");
        }
        if (chaincodeName == null) {
            throw new ContractException("Please specifiy chaincode Name. This is the chaincode that will be used for this scope");
        }


    }

    private Channel setup() throws ContractException {
        checkNulls();
        SampleUser admin = null;
        Peer peer = null;
        Channel channel = null;
        sampleOrg = new SampleOrg(getOrgName(), getMspID());
        sampleOrg.addPeerLocation(getPeerName(), getPeerLocation());


        try {
            peer = fhclClient.newPeer(getPeerName(), getPeerLocation());
            Set<String> channels = fhclClient.queryChannels(peer);
            if (channels == null) {
                throw new ContractException("FATAL: No channels retrieved");
            }
            if (!channels.contains(getChannelName())) {
                StringBuilder channelNames = new StringBuilder();
                for (String channelName : channels) {
                    channelNames.append(channelName);
                    channelNames.append(" ");
                    out("Channel Name: %s", channelName);
                }
                throw new ContractException("Channel not available. Channels available are: " + channelNames.toString());
            }
            channel = fhclClient.newChannel(getChannelName());
            if (peers.isEmpty()) throw new ContractException("No peers found");
            for (Iterator iterator = peers.iterator(); iterator.hasNext(); ) {
                Peer next = (Peer) iterator.next();
                channel.addPeer(next);
            }
            channel.addOrderer(fhclClient.newOrderer(getOrderer(), getOrdererLocation()));
            channel.initialize();
        } catch (InvalidArgumentException e) {
            throw new ContractException("FATAL: Unable to set user context for fhclclient" + e.getMessage());
        } catch (ProposalException e) {
            throw new ContractException("FATAL: Unable to retrieve channels from client" + e.getMessage());
        } catch (TransactionException e) {
            throw new ContractException("FATAL: Unable to initialize channel" + e.getMessage());
        }
        return channel;
    }

    private void test() {
        HFClient hfClient = null;
        try {
            hfClient = HFClient.createNewInstance();
            hfClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
            File sampleStoreFile = new File(System.getProperty("java.io.tmpdir") + "/SalesContractAccessor.properties");
            out(sampleStoreFile.getAbsolutePath());
            if (sampleStoreFile.exists()) { //For testing start fresh
                sampleStoreFile.delete();
            }
            sampleStore = new SampleStore(sampleStoreFile);
            SampleOrg sampleOrg = new SampleOrg("cts", "cts");
            sampleOrg.addPeerLocation("cts", "grpc://129.152.132.80:7051");
            SampleUser admin = sampleStore.getMember("User1", "cts", "cts",
                    Util.findFileSk(Paths.get("/Users/vijay/Keyfiles/").toFile()),
                    Paths.get("/Users/vijay/Keyfiles/Admin@cts-cert.pem").toFile());

            // org.fhcl.common.SampleUser admin = sampleStore.getMember("Admin@cts","cts");
            //admin.setEnrollment(new org.fhcl.common.SampleStore.SampleStoreEnrollement());
            sampleOrg.setAdmin(admin);

            hfClient.setUserContext(sampleOrg.getAdmin());
            Peer peer = hfClient.newPeer("cts", "grpc://129.152.132.80:7051");
            Set<String> channels = hfClient.queryChannels(peer);
            Channel channel;
            for (String channelName : channels) {
                out("Channel Name: %s", channelName);
                channel = hfClient.newChannel(channelName);
                channel.addPeer(peer);
                channel.addOrderer(hfClient.newOrderer("lavazzaOrderer", "grpc://129.152.132.229:7050"));
                try {
                    channel.initialize();
                } catch (TransactionException e) {
                    e.printStackTrace();
                }
                List<Query.ChaincodeInfo> ccinfoList = channel.queryInstantiatedChaincodes(peer);
                QueryByChaincodeRequest queryByChaincodeRequest = hfClient.newQueryProposalRequest();
                queryByChaincodeRequest.setArgs(new String[]{"1"});
                queryByChaincodeRequest.setFcn("query");
                ChaincodeID chaincodeID;
                for (Query.ChaincodeInfo ccifo : ccinfoList) {
                    out("Chaincode name: %s", ccifo.getName());
                    chaincodeID = ChaincodeID.newBuilder().setName(ccifo.getName()).setVersion("0").build();
                    queryByChaincodeRequest.setChaincodeID(chaincodeID);

                }
                Collection<ProposalResponse> queryProposals = channel.queryByChaincode(queryByChaincodeRequest);
                for (ProposalResponse proposalResponse : queryProposals) {
                    String output = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
                    out("The output from peer %s, is: %s", proposalResponse.getPeer().toString(), output);
                }
            }


            out("Out here");

            //  Channel mychannel = hfClient.getChannel("mycc");
            //  out(mychannel.getPeers().toString());



            /*QueryByChaincodeRequest query=hfClient.newQueryProposalRequest();
            query.setArgs(new String[] {"query","1"});
            query.setFcn("invoke");
            query.setChaincodeID(chaincodeID);
*/


//            sampleOrg.peerLocations


        } catch (CryptoException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvalidArgumentException e) {
            logger.fatal(e.getMessage());
        } catch (ProposalException | InvalidKeySpecException | NoSuchProviderException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
        }
        //Peer peer =
        // hfClient.queryChannels()

//        hfclient.s
//        hfClient.getChannel("supplierchannel");


    }

}
