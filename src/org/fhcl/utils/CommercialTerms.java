package org.fhcl.utils;

public class CommercialTerms
{
    private String costPerMonth;

    private String coffeeCostPerKg;

    private String costPerCupForNonFHCLBeans;

    private String teaCostPerKg;

    private String yearEndUplift;

    public String getCostPerMonth ()
    {
        return costPerMonth;
    }

    public void setCostPerMonth (String costPerMonth)
    {
        this.costPerMonth = costPerMonth;
    }

    public String getCoffeeCostPerKg ()
    {
        return coffeeCostPerKg;
    }

    public void setCoffeeCostPerKg (String coffeeCostPerKg)
    {
        this.coffeeCostPerKg = coffeeCostPerKg;
    }

    public String getCostPerCupForNonFHCLBeans ()
    {
        return costPerCupForNonFHCLBeans;
    }

    public void setCostPerCupForNonFHCLBeans (String costPerCupForNonFHCLBeans)
    {
        this.costPerCupForNonFHCLBeans = costPerCupForNonFHCLBeans;
    }

    public String getTeaCostPerKg ()
    {
        return teaCostPerKg;
    }

    public void setTeaCostPerKg (String teaCostPerKg)
    {
        this.teaCostPerKg = teaCostPerKg;
    }

    public String getYearEndUplift ()
    {
        return yearEndUplift;
    }

    public void setYearEndUplift (String yearEndUplift)
    {
        this.yearEndUplift = yearEndUplift;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [costPerMonth = "+costPerMonth+", coffeeCostPerKg = "+coffeeCostPerKg+", costPerCupForNonFHCLBeans = "+costPerCupForNonFHCLBeans+", teaCostPerKg = "+teaCostPerKg+", yearEndUplift = "+yearEndUplift+"]";
    }
}
