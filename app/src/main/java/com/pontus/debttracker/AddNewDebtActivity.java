package com.pontus.debttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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

    public void onComposeAction(MenuItem mi)
    {
        TextInputLayout etName = findViewById(R.id.et_Name);
        TextInputLayout etDesc = findViewById(R.id.et_desc);
        TextInputLayout etSum = findViewById(R.id.et_sum);

        DateFormat dateFormat = DateFormat.getDateInstance();
        String date = dateFormat.format(new Date());

        Bundle extras = new Bundle();


        extras.putString("name", Objects.requireNonNull(etName.getEditText()).getText().toString());
        extras.putString("desc", Objects.requireNonNull(etDesc.getEditText()).getText().toString());
        extras.putString("sum", Objects.requireNonNull(etSum.getEditText()).getText().toString());
        extras.putString("date", date);

        Intent intent = new Intent();
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        finish();
    }

}
