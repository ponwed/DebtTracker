package com.pontus.debttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        saveCards();
    }

    private void loadCards()
    {
        File file = new  File(this.getFilesDir(), "debtCardsFile");

        try
        {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //noinspection unchecked
            mDebtCards = (ArrayList<DebtCard>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private void saveCards()
    {
        File file = new File(this.getFilesDir(), "debtCardsFile");
        try
        {
                boolean created = file.createNewFile();
                if(file.setWritable(true))
                {
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(mDebtCards);
                    oos.close();
                    fos.close();
                }
                else {throw new IOException("Couldn't set writeable attribute on file");}
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void initDebtCards()
    {
        Log.d(TAG, "initDebtCards");
        loadCards();
        initRecyclerView();
    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(mDebtCards, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                mDebtCards.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
                saveCards();
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }
}
