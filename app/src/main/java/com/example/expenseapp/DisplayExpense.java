package com.example.expenseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayExpense extends  MainActivity {

    TextView textViewName;
    TextView textViewAmount;
    TextView textViewCategory;
    TextView textViewDate;
    Expense expense;
    Button editExpenseBtn;
    String key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_expense);
        expense = new Expense();
        expense = (Expense) getIntent().getExtras().getSerializable("bundle");


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mRootRef = database.getReference().child("expenses");
        if (adapter != null)
            adapter.notifyDataSetChanged();
        listView = findViewById(R.id.listView);
        mRootRef.child(expense.getKey()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expensesList.clear();
                myexpense = dataSnapshot.getValue(Expense.class);
                expensesList.add(myexpense);
                key = dataSnapshot.getKey();
                textViewName = (TextView) findViewById(R.id.textViewName);
                textViewAmount = (TextView) findViewById(R.id.textViewAmount);
                textViewCategory = (TextView) findViewById(R.id.textViewCategory);
                textViewDate = (TextView) findViewById(R.id.textViewDate);
                textViewName.setText(myexpense.getExpensesname());
                textViewAmount.setText("$" + myexpense.getExpenseAmount());
                textViewCategory.setText(myexpense.getCategory());
                textViewDate.setText(myexpense.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        editExpenseBtn = (Button) findViewById(R.id.editExpenseBtn);
        editExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayExpense.this, AddExpense.class);
                i.putExtra("key",expense );
                startActivity(i);
            }
        });
    }
}
