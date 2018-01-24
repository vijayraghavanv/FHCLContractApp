package org.fhcl.utils;

public class CoffeeMachine
{
    private String installationAddress;

    private String installationCompanyName;

    private String coffeeMachineID;

    public String getInstallationAddress ()
    {
        return installationAddress;
    }

    public void setInstallationAddress (String installationAddress)
    {
        this.installationAddress = installationAddress;
    }

    public String getInstallationCompanyName ()
    {
        return installationCompanyName;
    }

    public void setInstallationCompanyName (String installationCompanyName)
    {
        this.installationCompanyName = installationCompanyName;
    }

    public String getCoffeeMachineID ()
    {
        return coffeeMachineID;
    }

    public void setCoffeeMachineID (String coffeeMachineID)
    {
        this.coffeeMachineID = coffeeMachineID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [installationAddress = "+installationAddress+", installationCompanyName = "+installationCompanyName+", coffeeMachineID = "+coffeeMachineID+"]";
    }
}


