package com.pontus.debttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<DebtCard> mDebtCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");

        super.onCreate(savedInstanceState);
        //this.getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);

        FloatingActionButton fabAddNew = findViewById(R.id.fab_new);
        fabAddNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addNewDebtItem();
            }
        });

        initDebtCards();
    }

    private void addNewDebtItem()
    {
        Intent intent = new Intent(this, AddNewDebtActivity.class);
        startActivity(intent);
    }

    private void initDebtCards()
    {
        Log.d(TAG, "initDebtCards");

        //TODO: Change to load locally stored items
        mDebtCards.add(new DebtCard("John Doe", "Food", "50 SEK"));
        mDebtCards.add(new DebtCard("Jane Doe", "Wine", "70 SEK"));
        mDebtCards.add(new DebtCard("Anders Andersson", "Painting supplies", "600 SEK"));

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
