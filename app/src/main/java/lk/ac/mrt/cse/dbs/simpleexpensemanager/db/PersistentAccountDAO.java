package lk.ac.mrt.cse.dbs.simpleexpensemanager.db;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.db.Dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PersistentAccountDAO  implements AccountDAO {
    private Dbhelper dbhelper;



    @Override
    public List<String> getAccountNumbersList() {
        List<String> accNumbers = new ArrayList<String>();
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String query = "SELECT * FROM Account " ;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            String accountNo = data.getString(data.getColumnIndex("accountNo"));
            accNumbers.add(accountNo);
        }
        return accNumbers;
    };

    @Override
    public List<Account> getAccountsList() {
        List<Account> accounts = new ArrayList<Account>();
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String query = "SELECT * FROM Account";
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            String accountNo = data.getString(data.getColumnIndex("accountNo"));
            String accountHolderName = data.getString(data.getColumnIndex("accountHolderName"));
            Double balance = data.getDouble(data.getColumnIndex("balance"));
            String bankName = data.getString(data.getColumnIndex("bankName"));
            Account acc = new Account(accountNo, bankName, accountHolderName, balance);
            accounts.add(acc);
        }
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String query = "SELECT * FROM Account where accountNo="+accountNo+" ;";
        Cursor data = db.rawQuery(query, null);
        Account acc = null;
        while(data.moveToNext()){
            String accountNo2 = data.getString(data.getColumnIndex("accountNo"));
            String accountHolderName = data.getString(data.getColumnIndex("accountHolderName"));
            Double balance = data.getDouble(data.getColumnIndex("balance"));
            String bankName = data.getString(data.getColumnIndex("bankName"));
            acc = new Account(accountNo2, bankName, accountHolderName, balance);

        }
        return acc;
    }

    @Override
    public void addAccount(Account account) {

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
