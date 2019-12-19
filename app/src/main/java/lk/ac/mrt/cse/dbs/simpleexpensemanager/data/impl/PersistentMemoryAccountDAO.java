package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentMemoryAccountDAO implements AccountDAO {
    private DBHelper db_helper;

    public PersistentMemoryAccountDAO(Context cont){
        this.db_helper = new DBHelper(cont);
    }

    @Override
    public List<String> getAccountNumbersList() {
        ArrayList<Account> accounts = this.db_helper.get_accounts();
        ArrayList<String> acc_numbers = new ArrayList<>();
        if(accounts.size() !=0 ) {
            for (Account acc : accounts) {
                acc_numbers.add(acc.getAccountNo());
            }
        }
        return acc_numbers;
    }

    @Override
    public List<Account> getAccountsList() {
        return this.db_helper.get_accounts();
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return this.db_helper.get_account(accountNo);
    }

    @Override
    public void addAccount(Account account) {
        this.db_helper.insert_account(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account acc = this.db_helper.get_account(accountNo);

        switch (expenseType) {
            case EXPENSE:
                acc.setBalance(acc.getBalance() - amount);
                break;
            case INCOME:
                acc.setBalance(acc.getBalance() + amount);
                break;
        }

        if (acc.getBalance() >= 0){
            this.db_helper.update_account(acc);
        }
        else{
            throw new InvalidAccountException("Not enough balance");
        }
    }
}
