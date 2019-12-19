package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentMemoryTransactionDAO implements TransactionDAO {
    private DBHelper db_helper;

    public PersistentMemoryTransactionDAO(Context cont){
        this.db_helper = new DBHelper(cont);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction tr = new Transaction(date, accountNo, expenseType, amount);
        this.db_helper.insert_transaction(tr);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return this.db_helper.get_transactions();
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        return this.db_helper.get_transactions(limit);
    }
}
