package org.fhcl.utils;

import javax.servlet.http.HttpServletRequest;

public class ContractCreator {

        private static ContractPOJO contractPOJO;
        public ContractCreator(){

        }
        public static ContractPOJO createContract(HttpServletRequest request) throws ContractException {
            contractPOJO=new ContractPOJO();
            Contract contract=new Contract();
            Customer customer = new Customer();
            CoffeeMachine[] coffeeMachine=new CoffeeMachine[1];
            CommercialTerms commercialTerms=new CommercialTerms();
            OtherTerms otherTerms=new OtherTerms();
            contract.setContractID(request.getParameter("contractID"));
            if(contract.getContractID()==null){
                throw new ContractException("Contract ID cannot be null");
            }
            contract.setSalesManager(request.getParameter("salesManager"));
            if(contract.getSalesManager()==null){
                throw new ContractException("Sales Manager need to be set!!");
            }

            contract.setFHCLZonalManagerID(request.getParameter("FHCLZonalManagerID"));
            if(contract.getFHCLZonalManagerID()==null) {
                throw new ContractException("FHCL Zonal Manager ID cannot be null");
            }

            contract.setFHCLZonalManagerName(request.getParameter("FHCLZonalManagerName"));
            contract.setFHCLBranchLocation("Anna Salai");
            if(contract.getFHCLZonalManagerName()==null){
                throw new ContractException("FHCL Zonal Manager Name cannot be null");
            }
            contract.setValidityInYears(request.getParameter("validityInYears"));
            if(contract.getValidityInYears() == null){
                throw new ContractException("Validity period has to be specified");
            }
            contract.setContractDate(request.getParameter("contractDate"));
            if(contract.getContractDate()==null){
                throw new ContractException("Contract date need to be specified");
            }
            customer.setCustomerID(request.getParameter("customerID"));
            if(customer.getCustomerID()==null){
                throw new ContractException("Customer ID cannot be null");
            }
            customer.setCustomerName(request.getParameter("customerName"));
            if (customer.getCustomerName()==null){
                throw new ContractException("Customer Name needs to be entered");
            }

            customer.setCustomerRepresentative(request.getParameter("customerRepresentative"));
            if(customer.getCustomerRepresentative()==null){
                throw new ContractException("Customer Representative need to be entered");
            }
            customer.setCustomerAddress(request.getParameter("customerAddress"));
            if(customer.getCustomerAddress()==null){
                customer.setCustomerAddress("");
            }
            contract.setCustomer(customer);
            coffeeMachine[0]=new CoffeeMachine();
            coffeeMachine[0].setCoffeeMachineID(request.getParameter("coffeeMachineID"));
            if(coffeeMachine[0].getCoffeeMachineID()==null){
                throw new ContractException("Coffee Machine ID cannot be null");
            }
            coffeeMachine[0].setInstallationCompanyName(request.getParameter("installationCompanyName"));
            if(coffeeMachine[0].getInstallationCompanyName()==null){
                throw new ContractException("Installation Company Name cannot be null");
            }
            coffeeMachine[0].setInstallationAddress(request.getParameter("installationAddress"));
            if(coffeeMachine[0].getInstallationAddress()==null){
                coffeeMachine[0].setInstallationAddress("");
            }
            contract.setCoffeeMachine(coffeeMachine);
            commercialTerms.setCostPerMonth(request.getParameter("costPerMonth"));
            commercialTerms.setCoffeeCostPerKg(request.getParameter("coffeeCostPerKg"));
            commercialTerms.setTeaCostPerKg(request.getParameter("teaCostPerKg"));
            commercialTerms.setCostPerCupForNonFHCLBeans(request.getParameter("costPerCupForNonFHCLBeans"));
            commercialTerms.setYearEndUplift(request.getParameter("yearEndUplift"));
            contract.setCommercialTerms(commercialTerms);
            otherTerms.setMUCBillSettlementTime(request.getParameter("MUCBillSettlementTime"));
            otherTerms.setInterestRate(request.getParameter("interestRate"));
            otherTerms.setDisputeRaiseTime(request.getParameter("disputeRaiseTime"));
            otherTerms.setGracePeriod(request.getParameter("gracePeriod"));
            otherTerms.setNoticePeriod(request.getParameter("noticePeriod"));
            contract.setOtherTerms(otherTerms);
            contractPOJO.setContract(contract);
            return contractPOJO;

        }

    }

