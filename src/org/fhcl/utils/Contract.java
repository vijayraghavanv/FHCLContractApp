package org.fhcl.utils;

public class Contract
{
    private OtherTerms OtherTerms;

    private Customer Customer;

    private String validityInYears;

    private String contractDate;

    private String FHCLZonalManagerName;


    public String getFHCLBranchLocation() {
        return FHCLBranchLocation;
    }

    public void setFHCLBranchLocation(String FHCLBranchLocation) {
        this.FHCLBranchLocation = FHCLBranchLocation;
    }

    private String FHCLBranchLocation;

    private String FHCLZonalManagerID;

    private String salesManager;

    private String contractID;

    private CommercialTerms CommercialTerms;

    private CoffeeMachine[] CoffeeMachine;


    public OtherTerms getOtherTerms ()
    {
        return OtherTerms;
    }

    public void setOtherTerms (OtherTerms OtherTerms)
    {
        this.OtherTerms = OtherTerms;
    }

    public Customer getCustomer ()
    {
        return Customer;
    }

    public void setCustomer (Customer Customer)
    {
        this.Customer = Customer;
    }

    public String getValidityInYears ()
    {
        return validityInYears;
    }

    public void setValidityInYears (String validityInYears)
    {
        this.validityInYears = validityInYears;
    }

    public String getContractDate ()
    {
        return contractDate;
    }

    public void setContractDate (String contractDate)
    {
        this.contractDate = contractDate;
    }

    public String getFHCLZonalManagerName ()
    {
        return FHCLZonalManagerName;
    }

    public void setFHCLZonalManagerName (String FHCLZonalManagerName)
    {
        this.FHCLZonalManagerName = FHCLZonalManagerName;
    }

    public String getFHCLZonalManagerID ()
    {
        return FHCLZonalManagerID;
    }

    public void setFHCLZonalManagerID (String FHCLZonalManagerID)
    {
        this.FHCLZonalManagerID = FHCLZonalManagerID;
    }

    public String getSalesManager ()
    {
        return salesManager;
    }

    public void setSalesManager (String salesManager)
    {
        this.salesManager = salesManager;
    }

    public String getContractID ()
    {
        return contractID;
    }

    public void setContractID (String contractID)
    {
        this.contractID = contractID;
    }

    public CommercialTerms getCommercialTerms ()
    {
        return CommercialTerms;
    }

    public void setCommercialTerms (CommercialTerms CommercialTerms)
    {
        this.CommercialTerms = CommercialTerms;
    }

    public CoffeeMachine[] getCoffeeMachine ()
    {
        return CoffeeMachine;
    }

    public void setCoffeeMachine (CoffeeMachine[] CoffeeMachine)
    {
        this.CoffeeMachine = CoffeeMachine;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [OtherTerms = "+OtherTerms+", Customer = "+Customer+", validityInYears = "+validityInYears+", contractDate = "+contractDate+", FHCLZonalManagerName = "+FHCLZonalManagerName+", FHCLZonalManagerID = "+FHCLZonalManagerID+", salesManager = "+salesManager+", contractID = "+contractID+", CommercialTerms = "+CommercialTerms+", CoffeeMachine = "+CoffeeMachine+"]";
    }
}


