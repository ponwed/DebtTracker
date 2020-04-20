package com.pontus.debttracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class AddNewDebtActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_debt);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        populateSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_add_new, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    // Modified function from https://stackoverflow.com/a/3537085/2195880
    private static List<String> getAllCurrencyCodes()
    {
        Set<String> toret = new HashSet<String>();
        Locale[] locs = Locale.getAvailableLocales();

        for(Locale loc : locs) {
            try {
                Currency currency = Currency.getInstance( loc );

                if ( currency != null ) {
                    toret.add(currency.getCurrencyCode());
                }
            } catch(Exception exc)
            {
                // Locale not found
            }
        }

        List <String> currencyList = new ArrayList<>(toret);
        Collections.sort(currencyList);
        return currencyList;
    }

    private String getCurrencyStringForLocale()
    {
        Locale defaultLocale = Locale.getDefault();
        return Currency.getInstance(defaultLocale).getCurrencyCode();
    }

    private void populateSpinner()
    {
        List<String> spinnerArr = getAllCurrencyCodes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, spinnerArr
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner currencySpinner = findViewById(R.id.currency_spinner);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setSelection(adapter.getPosition(getCurrencyStringForLocale()));
    }

    boolean verifyFields()
    {
        TextInputLayout etName = findViewById(R.id.et_Name);
        TextInputLayout etDesc = findViewById(R.id.et_desc);
        TextInputLayout etSum = findViewById(R.id.et_sum);

        String name = Objects.requireNonNull(etName.getEditText()).getText().toString();
        String desc = Objects.requireNonNull(etDesc.getEditText()).getText().toString();
        String sum = Objects.requireNonNull(etSum.getEditText()).getText().toString();

        boolean checkPassed = true;

        if(name.equals(""))
        {
            showAlert("Missing required data", "Please provide a name");
            checkPassed = false;
        }
        if (desc.equals(""))
        {
            showAlert("Missing required data", "Please provide an description");
            checkPassed = false;
        }
        if (sum.equals(""))
        {
            showAlert("Missing required data", "Please provide the sum owed");
            checkPassed = false;
        }
        if(!sum.matches("^[0-9]*$"))
        {
            showAlert("Invalid data", "Only numbers are allowed in the sum field");
            checkPassed = false;
        }

        return checkPassed;
    }

    private void showAlert(String title, String message)
    {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void onComposeAction(MenuItem mi)
    {
        DateFormat dateFormat = DateFormat.getDateInstance();
        String date = dateFormat.format(new Date());

        TextInputLayout etName = findViewById(R.id.et_Name);
        TextInputLayout etDesc = findViewById(R.id.et_desc);
        TextInputLayout etSum = findViewById(R.id.et_sum);
        Spinner currencySpinner = findViewById(R.id.currency_spinner);

        if(verifyFields())
        {
            Bundle extras = new Bundle();
            extras.putString("name", Objects.requireNonNull(etName.getEditText()).getText().toString());
            extras.putString("desc", Objects.requireNonNull(etDesc.getEditText()).getText().toString());
            extras.putString("sum", Objects.requireNonNull(etSum.getEditText()).getText().toString() + " " + currencySpinner.getSelectedItem().toString());
            extras.putString("date", date);

            Intent intent = new Intent();
            intent.putExtras(extras);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
