package com.example.expenseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addExpense;
    ListView listView;
    ArrayList<Expense> expensesList;
    ArrayAdapter<Expense> adapter;
    Expense myexpense;
    TextView defaultTextview;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expensesList = new ArrayList<>();
        addExpense = (FloatingActionButton) findViewById(R.id.addExpense);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddExpense.class);
                startActivity(i);
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mRootRef = database.getReference().child("expenses");
        if (adapter != null)
            adapter.notifyDataSetChanged();
        listView = findViewById(R.id.listView);
        adapter = new ExpenseAdapter(MainActivity.this, R.layout.expense_db, expensesList);
        listView.setAdapter(adapter);
        defaultTextview = findViewById(R.id.placeHolderText);
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                expensesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    myexpense = ds.getValue(Expense.class);
                    expensesList.add(myexpense);

                }
                key = dataSnapshot.getKey();
                if (!(expensesList.isEmpty())){
                    defaultTextview.setVisibility(View.INVISIBLE);

                }
                adapter.notifyDataSetChanged();
                Log.d("demo", "explist" + expensesList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mRootRef.child("expenses").child(key).removeValue();

                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayExpense.class);
                Expense expenses = expensesList.get(position);
                intent.putExtra("bundle", expenses);
                startActivity(intent);

            }
        });

    }
}
