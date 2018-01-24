package org.fhcl.web;

import com.google.gson.Gson;
import org.fhcl.SalesContractAccessor;
import org.fhcl.utils.CoffeeMachine;
import org.fhcl.utils.ContractCreator;
import org.fhcl.utils.ContractException;
import org.fhcl.utils.ContractPOJO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FHCLServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
            response) throws javax.servlet.ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String callingProgram = request.getParameter("htmlFormName");
        if (callingProgram.equals("query")) {
            try {
                String contract = queryContractID(request);
                ContractPOJO contractPOJO = fromJSON(contract);
                buildHtml(contractPOJO, pw);
//                pw.println(contract);
            } catch (ContractException e) {
                pw.println(e.getMessage());
            }
        } else if (callingProgram.equals("Contract")) {
            try {
                String jsonStr = createJSON(request);
                pw.println(jsonStr);
                createContract(jsonStr);
                //         pw.println(queryHistoricValues("1"));
            } catch (ContractException e) {
                pw.println(e.getMessage());
            }
        }

    }

    private void buildHtml(ContractPOJO contract, PrintWriter pw) throws ContractException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'");
        String dateInString = contract.getContract().getContractDate();
        String strDate = null;
        String lastValidDate = null;
        String valueIn = "2016-01-19-09.55.00.000000";
        try {
            Date date = formatter.parse(dateInString);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            strDate = dateFormatter.format(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, Integer.parseInt(contract.getContract().getValidityInYears())); // Where n is int
            date = cal.getTime();
            lastValidDate = dateFormatter.format(date);
        } catch (ParseException e) {
            throw new ContractException("Unable to process date");
        }
        CoffeeMachine[] coffeeMachines = contract.getContract().getCoffeeMachine();

        pw.println("<!doctype html>");
        pw.println("<html lang=\"en\">");
        pw.println("<head>");
        pw.println("  <!-- Required meta tags -->");
        pw.println(" <meta charset=\"utf-8\">");
        pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
        pw.println(" <!-- Bootstrap CSS -->");
        pw.println(
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min" +
                        ".css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\"" +
                        " crossorigin=\"anonymous\">");
        pw.println("   <title>Agreement</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div class=\"container-fluid\">");
        pw.println("<h1>Contract</h1>");
        pw.println("<p class=\"lead text-justify\">");
        pw.println("This agreement is entered on " + strDate + "between M/s.Fresh " +
                           "&amp; Honest Caf&egrave; Ltd., having its registered office at 3rd Floor, Hallmark " +
                           "Towers, Plot " +
                           "no. 35 (SP), Thiru VI Ka Industrial Estate, Chennai – 600 032 (hereinafter referred to as" +
                           " the COMPANY) which expression shall unless repugnant to the context mean and include its" +
                           " Executors, Administrators, Legal Representatives and assigns represented by " + contract
                .getContract().getFHCLZonalManagerName() +
                           "-Zonal Manager Branch office at " + contract.getContract().getFHCLBranchLocation() + "of " +
                           "the First" +
                           " part and M/s." + contract.getContract().getCustomer().getCustomerName() + ", a company " +
                           "incorporated under the Company " +
                           "Act of 1956 having its Registered Office " +
                           "at " + contract.getContract().getCustomer().getCustomerAddress() + ". (hereinafter " +
                           "referred to as the" +
                           " CO-OPERATOR) represented by " + contract.getContract().getCustomer()
                .getCustomerRepresentative() + " of the second Part");
        pw.println("</p >");
        pw.println("<p class=\"lead text-justify\">");
        pw.println(
                "The COMPANY and the CO-OPERATOR have agreed to enter into an agreement for providing coffee and " +
                        "other beverages dispensed by coffee vending machines in the premises of the CO-OPERATOR as " +
                        "per terms and conditions listed below:-</p>");

        pw.println("<ol>");
        pw.println("<p class=\"lead text-justify\">");
        for (int i = 0; i < coffeeMachines.length; i++) {
            CoffeeMachine coffeeMachine = coffeeMachines[i];
            pw.println(
                    " <li>The COMPANY has installed ONE (1) No. Fully Automatic coffee vending machine) in the " +
                            "premises M/s. <b>" +
                            coffeeMachine.getInstallationCompanyName() + "</b>, in the premises of<b> " +
                            coffeeMachine.getInstallationAddress() + "</b>.of the CO-OPERATOR and " +
                            "the machine so installed will be the sole property of the COMPANY.</li>");

        }
        pw.println(
                "<li>The COMPANY will take insurance policy for the machines and purifying equipments against theft, " +
                        "fire, earth quake and electrical breakdown.</li>");
        pw.println(
                "<li>The CO-OPERATOR will provide a reasonable site free from dust, dirt heat and rodents i.e., in " +
                        "hygienic condition for installing the coffee machine with adequate provision for water inlet" +
                        " and outlet 1/2 inch pipe size) and single /three phase 15 amps electricity connection. The " +
                        "change of location of the coffee machine after installation shall be with prior approval of " +
                        "the COMPANY without any additional charges.</li>");
        pw.println(
                "<li>The CO-OPERATOR will provide all other consumables and utilities like fresh pasteurized milk, " +
                        "sugar," +
                        "water, cups, stirrers, stabilized electricity and manpower as required for the machine to " +
                        "vend and dispense premium quality coffee.</li>");
        pw.println(
                "<li>The coffee Machine is provided with a microprocessor based counter, which is able to keep count " +
                        "of " +
                        "the number of cups of coffee and other beverages, dispensed. The machine also features a " +
                        "programmable ingredient content and quantity fixation, which will be done only by the " +
                        "authorized personnel of the COMPANY. The CO-OPERATOR agrees not to tamper in any way with " +
                        "the working and mechanism of the coffee machine.</li>");
        pw.println(
                "<li>The COMPANY will supply premium quality roasted coffee beans of Indian origin etc at mutually " +
                        "agreed" +
                        " prices as given in clause 7 of this agreement. The CO-OPERATOR must replenish the stock in " +
                        "advance.</li>");
        pw.println("<li><b>THIS AGREEMENT WITNESSETH THE FOLLOWING COMMERCIAL TERMS</b></li>");
        pw.println("<p class=\"text-center\">");
        pw.println("<ol>");
        pw.println("<li>Machine Usage Charges: &#8377;" + contract.getContract().getCommercialTerms().getCostPerMonth
                () + "</li>");
        pw.println("<li>Cost of coffee (Per Kg): &#8377; " + contract.getContract().getCommercialTerms()
                .getCoffeeCostPerKg() + "</li>");
        pw.println("<li>Cost of tea (Per Kg): &#8377; " + contract.getContract().getCommercialTerms().getTeaCostPerKg
                () + "</li>");
        pw.println("<li>Cost per kg for usage of non FHCL beans : &#8377;" + contract.getContract().getCommercialTerms()
                .getCostPerCupForNonFHCLBeans() + "</li>");
        pw.println("</ol></p>");
        pw.println(
                "<li>The Company shall raise invoice for Machine Usage Charges charges (MUC) per month per machine, " +
                        "plus " +
                        "service tax and on supplies of Materials. VAT extra. The bill shall be settled within " +
                        contract.getContract().getOtherTerms().getMUCBillSettlementTime() + "days (due date) from the" +
                        " date of submission. Delay beyond" + contract.getContract().getOtherTerms()
                        .getMUCBillSettlementTime() + "days will attract a nominal " +
                        "interest of " + contract.getContract().getOtherTerms().getInterestRate() + "%</li>");
        pw.println("</p>");
        pw.println("<p class=\"lead text-justify\">");
        pw.println(
                "In the event of any dispute in Invoice, the CO-OPERATOR shall send a written communication to the " +
                        "COMPANY within" + contract.getContract().getOtherTerms().getDisputeRaiseTime() + " days from" +
                        " the date of receipt of the invoice and the dispute shall be " +
                        "resolved within 21 days from the due date.");
        pw.println(
                "In the vent of the CO-OPERATOR failing to make the payment within" + contract.getContract()
                        .getOtherTerms().getGracePeriod() + "days from the due date or " +
                        "within" + contract.getContract().getOtherTerms().getGracePeriod() + " days from the date of " +
                        "clearance of invoice dispute, if any, the COMPANY shall stop" +
                        " supply of materials to the CO-OPERATOR with immediate effect.");
        pw.println(
                "If there is delay by the CO-OPERATOR to make the outstanding payment to the COMPANY up to 30 days " +
                        "from the due date, the COMPANY shall immediately disconnect the vending machine and remove " +
                        "the same form the premises of the CO-OPERATOR");
        pw.println(
                "<li>The COMPANY reserves its right to remove the machine from the premises of the CO-OPERATOR in the" +
                        " event of violation of any of the terms of this Agreement, tampering of the Machine, etc. " +
                        "the CO-OPERATOR will not have any lien towards the machine and will hand over the machine in" +
                        " peace possession of The COMPANY.</li>");
        pw.println(
                "<li>The CO-OPERATOR should take reasonable care of the machine and responsible for damage of the " +
                        "machine" +
                        " /misplacement of spare parts and liable to be charged separately as all the parts are " +
                        "imported and expensive. The COMPANY’s Service Engineer will visit the place of installation " +
                        "periodically and check the machine (s). All service calls would be properly logged by the " +
                        "COMPANY and down time and attended to.</li>");
        pw.println(
                "<li>The consumption of coffee beans per cup is pre fixed and therefore it should tally with " +
                        "consumption " +
                        "as per meter reading subject to cross verification for mutual benefit and control. This is " +
                        "applicable for other beverages also dispensed through our machines.</li>");
        pw.println(
                "<li>Coffee beans supplied by FHCL should only be used through our coffee machines failing which FHCL" +
                        " " +
                        "will raise invoice at &#8377;" + contract.getContract().getCommercialTerms()
                        .getCostPerCupForNonFHCLBeans() + " per cup plus tax for the difference in consumption which " +
                        "can be" +
                        " gauged through meter reading.</li>");
        pw.println(
                "<li>As an ISO 9001: 2000 and HACCP certified company we take utmost care to serve safe and fresh " +
                        "beverages to our consumers, hence we insist on using food grade paper cups and stirrers for " +
                        "dispensation of beverages.</li>");
        pw.println(
                "<li>The CO-OPERATOR agrees not to deal with similar products manufactured marketed sold by any other" +
                        " " +
                        "company, firm, agencies during the period of this agreement.</li>");
        pw.println(
                "<li>The CO-OPERATOR shall not permit any outsider to inspect or operate the machine nor will he " +
                        "divulge " +
                        "the operation of the machine to any person not duly authorized by the company.</li>");
        pw.println(
                "<li>The CO-OPERATOR shall take license etc. and pay tax including relevant taxes and duties such as " +
                        "sales tax etc for the operation of the machine at his place, paid up and are up to date " +
                        "always.</li>");
        pw.println("<li>The CO-OPERATOR shall not to represent that he is an Agent of the COMPANY.</li>");
        pw.println(
                "<li>The CO-OPERATOR shall not transfer or otherwise alienate the right and obligation under this " +
                        "agreement to any other party without the specific written approval of the COMPANY.</li>");
        pw.println(
                "<li>INDEMNITY: CO-OPERATOR hereby undertakes and agrees to indemnifies and keep and hold THE COMPANY" +
                        " and" +
                        " its directors and employees indemnified and harmless from and against:<ul><li>all claims, " +
                        "proceedings, damages, losses, actions, costs and expenses arising as a consequence of any " +
                        "wrongful or negligent act of omission of CO-OPERATOR its employees or any of them,</li> " +
                        "<li>any " +
                        "breach of CO-OPERATOR 's obligations under this agreement including its employees engaged in" +
                        " providing the services or any of them in respect of products and, or, services" +
                        ".</li></ul></li>");
        pw.println(
                "<li>INJUNCTIVE RELIEF: Nothing in this Agreement bars COMPANY's right to obtain specific performance" +
                        " of " +
                        "the provisions of this Agreement and injunctive relief against threatened conduct that will " +
                        "cause the COMPANY, the Names and Marks or the COMPANY'S System loss or damage, under " +
                        "customary equity rules, including applicable rules for obtaining restraining orders and " +
                        "preliminary injunctions (subject to COMPANY's obligation to arbitrate).  CO-OPERATOR  agrees" +
                        " that the COMPANY may obtain such injunctive relief in addition to such further or other " +
                        "relief as may be available at law or in equity.  CO-OPERATOR agrees that the COMPANY will " +
                        "not be required to post a bond to obtain injunctive relief.</li>");
        pw.println(
                "<li>At the time of renewal, the price will be decided and mutually agreed upon to enhance upto " +
                        contract.getContract().getCommercialTerms().getYearEndUplift() + "% of " +
                        "its price fixed during the currency of the agreement.</li>");
        pw.println(
                "<li>All changes and or amendments to this agreement shall be in writing and duly signed by the both " +
                        "the " +
                        "parties.</li>");
        pw.println(
                "<li>The COMPANY has the right to terminate the contract after giving written notice of one month. " +
                        "This " +
                        "contract may be terminated by CO-OPERATOR without assigning any reason thereof by giving" +
                        contract.getContract().getOtherTerms().getNoticePeriod() +
                        "days notice or by paying one month MUC in lieu of prior termination.</li>");
        pw.println(
                "<li>The COMPANY may terminate this Agreement forthwith by giving notice in writing to the " +
                        "CO-OPERATOR,  " +
                        "if the CO-OPERATOR commits or permits any one or more of the following acts, without " +
                        "prejudice to any remedy the COMPANY may have against the CO-OPERATOR, which acts shall be " +
                        "treated as fundamental breaches of this Agreement:");
        pw.println(
                "<ul><li>The CO-OPERATOR at any time fails to pay within 30 days of the due date for payment, any " +
                        "amounts due and payable to the COMPANY on the dates on which such amounts are due for " +
                        "payment;</li>");
        pw.println(
                "<li>the CO-OPERATOR fails to submit to the COMPANY in a timely manner any of the accounting or usage" +
                        " " +
                        "information of the Coffee Vending Machine required to be so submitted;</li>");
        pw.println(
                "<li>the CO-OPERATOR in the reasonable opinion of the COMPANY substantially misuses or in any way " +
                        "impairs" +
                        " the goodwill associated with any of the Intellectual Property rights in a way that has the " +
                        "capacity to significantly impair the said goodwill, or takes any action to contest the " +
                        "validity or ownership of the Intellectual Property Rights;</li>");
        pw.println(
                "<li>the CO-OPERATOR purports to effect any sale, mortgage, transfer or assignment of any of the " +
                        "rights " +
                        "or licences granted other than in accordance with the terms of this Agreement;</li>");
        pw.println(
                "<li>the CO-OPERATOR fails to obtain any prior approval or consent of the COMPANY expressly required " +
                        "by " +
                        "this Agreement;</li>");
        pw.println(
                "<li>the CO-OPERATOR or any officer or employee of the CO-OPERATOR gives to the COMPANY any false or " +
                        "misleading information or makes any misrepresentation either in connection with obtaining " +
                        "this Agreement or at any time during the continuance of this Agreement in connection with " +
                        "the Business;</li>");
        pw.println("<li>the CO-OPERATOR ceases or fails to comply with any statutory licensing requirement;</li>");
        pw.println(
                "<li>Notwithstanding any of the other provisions contained within this clause, the CO-OPERATOR " +
                        "otherwise " +
                        "neglects or fails to perform or observe any of the provisions of this Agreement or commits " +
                        "any breach of its obligations, which breach if remediable is not remedied to the " +
                        "satisfaction of the COMPANY within" + contract.getContract().getOtherTerms().getNoticePeriod
                        () + " days of a notice in writing to the CO-OPERATOR " +
                        "requesting its remedy provided that the COMPANY shall not be obliged to give such notice in " +
                        "the case of a persistent breach which shall be one which has occurred more than twice in any" +
                        " 3 month period.</li></ul></li>");
        pw.println(
                "<li>This agreement is valid for" + contract.getContract().getValidityInYears() + " years from " +
                        strDate + " to" + lastValidDate + " However, the prices would" +
                        " be discussed and enhanced on mutual consent after one year.</li>");
        pw.println(
                "<li><b>Arbitration and jurisdiction</b>: Any claim, dispute or difference between the Parties shall be referred" +
                        " to the arbitration of a sole arbitrator to be appointed by FHCL. All proceedings in any " +
                        "such arbitration shall be conducted in English. The Arbitration shall take place in " +
                        "Bangalore, India and shall be governed by the Arbitration and Conciliation Act, 1996. Any " +
                        "dispute arising out of or in connection with this Agreement shall be governed by and " +
                        "construed solely in accordance with the laws of India.  The Courts in Bangalore alone shall " +
                        "have exclusive jurisdiction to entertain application if any arising out of the agreement.  " +
                        "Both the parties having fully read and understood the above terms of this proposal and " +
                        "having the requisite authority to bind them having affixed their signature, Name designation" +
                        " with date and seal</li>");

        pw.println("</li>");
        pw.println("</p>");
        pw.println("</ol>");


        pw.println("<!--Optional JavaScript-->");
        pw.println("<!--jQuery first, then Popper.js, then Bootstrap JS -->");
        pw.println(
                "<script src = \"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity = " +
                        "\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin = " +
                        "\"anonymous\" ></script >");
        pw.println(
                "<script src = \"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" " +
                        "integrity = \"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" " +
                        "crossorigin = \"anonymous\" ></script >");
        pw.println(
                "<script src = \"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity = " +
                        "\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin = " +
                        "\"anonymous\" ></script >");
        pw.println("</div >");
        pw.println("</body >");
        pw.println("</html >");


    }

    private ContractPOJO fromJSON(String json) {

        Gson gson = new Gson();
        ContractPOJO contract = gson.fromJson(json, ContractPOJO.class);
        return contract;


    }

    private String createJSON(HttpServletRequest request) throws ContractException {
        ContractPOJO contract = ContractCreator.createContract(request);
        Gson gson = new Gson();
        return gson.toJson(contract);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
            response) throws javax.servlet.ServletException, IOException {

    }

    private void createContract(String json) throws ContractException {

        SalesContractAccessor salesContractAccessor = new SalesContractAccessor("lavazza",
                                                                                "lavazza",
                                                                                "User1",
                                                                                "lavazza",
                                                                                "lavazza",
                                                                                "/Users/vijay/Keyfiles/Admin@lavazza-cert.pem",
                                                                                "/Users/vijay/Keyfiles/");
        setContractValues(salesContractAccessor);
        salesContractAccessor.writeContract(json);


    }

    private String queryHistoricValues(String key) throws ContractException {
        SalesContractAccessor salesContractAccessor = new SalesContractAccessor("lavazza",
                                                                                "lavazza",
                                                                                "User1",
                                                                                "lavazza",
                                                                                "lavazza",
                                                                                "/Users/vijay/Keyfiles/Admin@lavazza-cert.pem",
                                                                                "/Users/vijay/Keyfiles/");
        setContractValues(salesContractAccessor);
        return salesContractAccessor.getHistoryByKey(key);


    }

    private void setContractValues(SalesContractAccessor salesContractAccessor) throws ContractException {
        salesContractAccessor.setCertPath("/Users/vijay/Keyfiles/Admin@lavazza-cert.pem");
        salesContractAccessor.setChaincodeName("salescontract");
        salesContractAccessor.setChannelName("supplierchannel");
        salesContractAccessor.setOrderer("lavazzaOrderer");
        salesContractAccessor.setOrdererLocation("grpc://129.152.132.229:7050");
        salesContractAccessor.setPeerName("lavazza");
        salesContractAccessor.setPeerLocation("grpc://129.152.132.229:7051");
        salesContractAccessor.setKeyFilePath("/Users/vijay/Keyfiles/");
        salesContractAccessor.setUserName("User1");
        salesContractAccessor.setUserOrg("lavazza");
        salesContractAccessor.setUserMSP("lavazza");
        salesContractAccessor.addPeer("lavazza", "grpc://129.152.132.229:7051");
        salesContractAccessor.addPeer("cts", "grpc://129.152.132.80:7051");

    }

    private String queryContractID(HttpServletRequest request) throws ContractException {
        SalesContractAccessor salesContractAccessor = new SalesContractAccessor("lavazza",
                                                                                "lavazza",
                                                                                "User1",
                                                                                "lavazza",
                                                                                "lavazza",
                                                                                "/Users/vijay/Keyfiles/Admin@lavazza-cert.pem",
                                                                                "/Users/vijay/Keyfiles/");
        setContractValues(salesContractAccessor);
        String contractID = request.getParameter("queryID");
        if (contractID == null) {
            return null;
        }
        return salesContractAccessor.getContract(contractID);

    }

}
