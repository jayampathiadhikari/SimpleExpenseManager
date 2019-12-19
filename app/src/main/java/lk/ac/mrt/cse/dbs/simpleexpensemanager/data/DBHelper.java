package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context cont){
        super(cont,"DATABASE_NAME",null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS account_table (account_no TEXT(12) PRIMARY KEY, bank TEXT(50), account_holder TEXT(50), balance REAL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS transaction_table (date DATE, account_no TEXT(12), type TEXT(20), amount REAL, FOREIGN KEY (account_no) REFERENCES account_table (account_no))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        db.execSQL("DROP TABLE IF EXISTS account_table");
        db.execSQL("DROP TABLE IF EXISTS transaction_table");
        onCreate(db);
    }

    public boolean insert_account(Account acc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cont_values = new ContentValues();
        cont_values.put("account_no", acc.getAccountNo());
        cont_values.put("bank", acc.getBankName());
        cont_values.put("account_holder", acc.getAccountHolderName());
        cont_values.put("balance", acc.getBalance());
        long result = db.insert("account_table", null, cont_values);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insert_transaction(Transaction tr){
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");

        ContentValues cont_values = new ContentValues();
        cont_values.put("date", date_format.format(tr.getDate()));
        cont_values.put("account_no", tr.getAccountNo());
        cont_values.put("type", tr.getExpenseType().toString());
        cont_values.put("amount", tr.getAmount());

        long result = db.insert("transaction_table", null, cont_values);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean update_account(Account acc){
        /*if(type == "Expense") {
            acc.setBalance(acc.getBalance() - amount);
        }
        else{
            acc.setBalance(acc.getBalance() + amount);
        }*/
        ContentValues cont_values = new ContentValues();
        cont_values.put("balance", acc.getBalance());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update("account_table", cont_values, "account_no = ?", new String[]{acc.getAccountNo()});
        return true;
    }

    public Account get_account(String acc_no){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM account_table WHERE account_no = ?", new String[]{acc_no});

        Account acc = null;
        if(c1.getCount() != 0){
            while(c1.moveToNext()){
                String bank = c1.getString(1);
                String user = c1.getString(2);
                double balance = c1.getDouble(3);
                acc = new Account(acc_no, bank, user, balance);
            }
        }
        return acc;
    }

    public ArrayList<Account> get_accounts(){
        ArrayList<Account> accounts = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM account_table", null);
        if(c1.getCount() != 0){
            while(c1.moveToNext()){
                String acc_no = c1.getString(0);
                String bank = c1.getString(1);
                String user = c1.getString(2);
                double balance = c1.getDouble(3);
                Account acc = new Account(acc_no, bank, user, balance);
                accounts.add(acc);
            }
        }

        return accounts;
    }

    public ArrayList<Transaction> get_transactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c1 = db.rawQuery("SELECT * FROM transaction_table", null);
        if (c1.getCount() != 0){
            Date date = new Date();
            while(c1.moveToNext()){
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(c1.getString(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String acc_no = c1.getString(1);
                ExpenseType type = ExpenseType.valueOf(c1.getString(2));
                double amount = c1.getDouble(3);
                Transaction tr = new Transaction(date, acc_no, type, amount);
                transactions.add(tr);
            }
        }
        return transactions;
    }

    public ArrayList<Transaction> get_transactions(int limit){
        ArrayList<Transaction> transactions = get_transactions();
        int size = transactions.size();
        if (size > 10){
            return (ArrayList<Transaction>) transactions.subList(size - limit, size);
        }
        else{
            return transactions;
        }
        /*SQLiteDatabase db = this.getWritableDatabase();

        Cursor c1 = db.rawQuery("SELECT * FROM transaction_table LIMIT " +limit, null);
        if (c1.getCount() != 0){
            Date date = new Date();
            while(c1.moveToNext()){
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(c1.getString(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String acc_no = c1.getString(1);
                ExpenseType type = ExpenseType.valueOf(c1.getString(2));
                double amount = c1.getDouble(3);
                Transaction tr = new Transaction(date, acc_no, type, amount);
                transactions.add(tr);
            }
        }*/
//        return transactions;
    }
}
