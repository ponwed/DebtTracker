package com.pontus.debttracker;

class DebtCard
{
    String debtor;
    String description;
    String debt;
    String date;

    DebtCard(String debtor, String description, String debt, String date)
    {
        this.debtor = debtor;
        this.description = description;
        this.debt = debt;
        this.date = date;
    }
}
