package com.pontus.debttracker;

import java.io.Serializable;

class DebtCard implements Serializable
{
    String debtor;
    String description;
    String debt;
    String date;
    boolean owed;

    DebtCard(String debtor, String description, String debt, String date, boolean owed)
    {
        this.debtor = debtor;
        this.description = description;
        this.debt = debt;
        this.date = date;
        this.owed = owed;
    }
}
