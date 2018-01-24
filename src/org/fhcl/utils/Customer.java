package org.fhcl.utils;
public class Customer
{
    private String customerName;

    private String customerRepresentative;

    private String customerAddress;

    private String customerID;

    public String getCustomerName ()
    {
        return customerName;
    }

    public void setCustomerName (String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerRepresentative ()
    {
        return customerRepresentative;
    }

    public void setCustomerRepresentative (String customerRepresentative)
    {
        this.customerRepresentative = customerRepresentative;
    }

    public String getCustomerAddress ()
    {
        return customerAddress;
    }

    public void setCustomerAddress (String customerAddress)
    {
        this.customerAddress = customerAddress;
    }

    public String getCustomerID ()
    {
        return customerID;
    }

    public void setCustomerID (String customerID)
    {
        this.customerID = customerID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [customerName = "+customerName+", customerRepresentative = "+customerRepresentative+", customerAddress = "+customerAddress+", customerID = "+customerID+"]";
    }
}


