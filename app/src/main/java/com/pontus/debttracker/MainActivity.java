package com.pontus.debttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ADD_NEW_DEBT_ACTIVITY_REQUEST_CODE = 0;

    private ArrayList<DebtCard> mDebtCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");

        super.onCreate(savedInstanceState);
        //this.getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAddNew = findViewById(R.id.fab_new);
        fabAddNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), AddNewDebtActivity.class);
                startActivityForResult(intent, ADD_NEW_DEBT_ACTIVITY_REQUEST_CODE);
            }
        });

        initDebtCards();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NEW_DEBT_ACTIVITY_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Bundle extras = data.getExtras();
                if (extras != null)
                {
                    DebtCard debtCard = new DebtCard(
                            extras.getString("name"),
                            extras.getString("desc"),
                            extras.getString("sum"),
                            extras.getString("date"));

                    addNewDebtItem(debtCard);
                }
            }
        }
    }

    private void addNewDebtItem(DebtCard debtCard)
    {
        mDebtCards.add(debtCard);
    }

    private void initDebtCards()
    {
        Log.d(TAG, "initDebtCards");

        //TODO: Change to load locally stored items
        DateFormat dateFormat = DateFormat.getDateInstance();
        String date = dateFormat.format(new Date());
        mDebtCards.add(new DebtCard("John Doe", "Food", "50 SEK", date));
        mDebtCards.add(new DebtCard("Jane Doe", "Wine", "70 SEK", date));
        mDebtCards.add(new DebtCard("Anders Andersson", "Painting supplies", "600 SEK", date));

        initRecyclerView();
    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mDebtCards, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
