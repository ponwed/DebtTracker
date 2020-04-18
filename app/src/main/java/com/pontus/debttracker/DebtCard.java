package com.pontus.debttracker;

public class DebtCard
{
    public String debtor;
    public String description;
    public String debt;

    public DebtCard(String debtor, String description, String debt)
    {
        this.debtor = debtor;
        this.description = description;
        this.debt = debt;
    }
}
