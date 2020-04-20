package com.pontus.debttracker;

import java.io.Serializable;

class DebtCard implements Serializable
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
