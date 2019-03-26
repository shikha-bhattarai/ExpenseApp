package com.example.expenseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    public ExpenseAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){ //if no view to re-use then inflate a new one
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_db, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.expenseNameDB = (TextView) convertView.findViewById(R.id.expenseNameDB);
            viewHolder.expenseAmountDB = (TextView) convertView.findViewById(R.id.expenseAmountDB);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.expenseNameDB.setText("Expense: "+expense.expensesname);
        viewHolder.expenseAmountDB.setText("$ "+expense.expenseAmount);

        return convertView;
    }

    private static class ViewHolder{
        TextView expenseNameDB;
        TextView expenseAmountDB;
    }
}
