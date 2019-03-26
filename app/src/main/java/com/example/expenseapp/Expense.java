package com.example.expenseapp;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {
    String expensesname;
    int expenseAmount;
    String category;
    String date;
    String key;


    public Expense() {
    }

    public String getExpensesname() {
        return expensesname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setExpensesname(String expensesname) {
        this.expensesname = expensesname;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expensesname='" + expensesname + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", key='" + key + '\'' +
                '}';
    }

    public Expense(String expensesname, int expenseAmount, String category, String date, String key) {
        this.expensesname = expensesname;
        this.expenseAmount = expenseAmount;
        this.category = category;
        this.date = date;
        this.key = key;
    }
}