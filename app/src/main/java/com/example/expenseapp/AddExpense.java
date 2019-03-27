package com.example.expenseapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddExpense extends MainActivity {
    Expense expense;
    EditText editTextName;
    EditText editTextAmount;
    Spinner categorySpinner;
    Button addnewExpense;
    Button cancelBtn;
    String key;
    boolean flag = false;
    String editkey;
    String ed_text;
    String ed_amount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        addnewExpense = (Button) findViewById(R.id.buttonAdd);
        cancelBtn = (Button) findViewById(R.id.buttonCancel);
        categorySpinner = (Spinner) findViewById(R.id.spinner);


        String[] arraySpinner = new String[] {
                "Groceries", "Invoice", "Transportation", "Shopping", "Rent", "Trips", "Utilities","Other"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        addnewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mRootRef = database.getReference("expenses");
                expense = new Expense();
                ed_text = editTextName.getText().toString().trim();
                ed_amount = editTextAmount.getText().toString().trim();
                if (!(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null || ed_amount.isEmpty() || ed_amount.length() == 0 || ed_amount.equals("") || ed_amount == null))
                {
                    expense.setExpenseAmount(Integer.parseInt(editTextAmount.getText().toString()));
                    expense.setExpensesname(editTextName.getText().toString());
                    expense.setCategory(categorySpinner.getSelectedItem().toString());
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd ");
                    String strDate = mdformat.format(calendar.getTime());
                    expense.setDate(strDate);
                if (flag == true) {
                    expense.setKey(editkey);
                    mRootRef.child(editkey).setValue(expense);
                    //expense.setDate();
                }

               else
                {
                    key = mRootRef.push().getKey();
                    expense.setKey(key);
                    mRootRef.child(key).setValue(expense);
                }
                    finish();
                }
                else{
                    Toast.makeText(AddExpense.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().getExtras()!=null)
        {
            Expense expobj = new Expense();
            expobj = (Expense) getIntent().getExtras().getSerializable("key");
            editTextName.setText(expobj.getExpensesname());
            editTextAmount.setText(" "+expobj.getExpenseAmount());
            flag =true;
            editkey = expobj.getKey();

        }

    }

}
