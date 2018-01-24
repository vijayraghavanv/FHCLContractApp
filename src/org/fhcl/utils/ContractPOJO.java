package org.fhcl.utils;

public class ContractPOJO {

        private Contract Contract;

        public Contract getContract ()
        {
            return Contract;
        }

        public void setContract (Contract Contract)
        {
            this.Contract = Contract;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Contract = "+Contract+"]";
        }
    }

