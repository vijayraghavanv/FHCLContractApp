package org.fhcl.utils;

public class OtherTerms
{
    private String disputeRaiseTime;

    private String MUCBillSettlementTime;

    private String noticePeriod;

    private String interestRate;

    private String gracePeriod;

    public String getDisputeRaiseTime ()
    {
        return disputeRaiseTime;
    }

    public void setDisputeRaiseTime (String disputeRaiseTime)
    {
        this.disputeRaiseTime = disputeRaiseTime;
    }

    public String getMUCBillSettlementTime ()
    {
        return MUCBillSettlementTime;
    }

    public void setMUCBillSettlementTime (String MUCBillSettlementTime)
    {
        this.MUCBillSettlementTime = MUCBillSettlementTime;
    }

    public String getNoticePeriod ()
    {
        return noticePeriod;
    }

    public void setNoticePeriod (String noticePeriod)
    {
        this.noticePeriod = noticePeriod;
    }

    public String getInterestRate ()
    {
        return interestRate;
    }

    public void setInterestRate (String interestRate)
    {
        this.interestRate = interestRate;
    }

    public String getGracePeriod ()
    {
        return gracePeriod;
    }

    public void setGracePeriod (String gracePeriod)
    {
        this.gracePeriod = gracePeriod;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [disputeRaiseTime = "+disputeRaiseTime+", MUCBillSettlementTime = "+MUCBillSettlementTime+", noticePeriod = "+noticePeriod+", interestRate = "+interestRate+", gracePeriod = "+gracePeriod+"]";
    }
}


