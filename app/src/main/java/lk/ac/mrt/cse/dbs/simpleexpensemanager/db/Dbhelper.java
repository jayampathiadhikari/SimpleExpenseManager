package lk.ac.mrt.cse.dbs.simpleexpensemanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 2/28/2017.
 */

public class Dbhelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_table";


    public Dbhelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE Account (accountNo TEXT PRIMARY KEY, bankName TEXT,accountHolderName TEXT, balance REAL  )";
        db.execSQL(createTable);
        String createTable2 = "CREATE TABLE Transactions (  accountNo TEXT, date TEXT,accountHolderName TEXT, expenseType TEXT ,FOREIGN KEY(accountNo) REFERENCES Account(accountNo)  )";
        db.execSQL(createTable2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Account" );
        db.execSQL("DROP TABLE IF EXISTS Transactions" );
        onCreate(db);
    }


    /**
     * Returns all the data from database
     * @return
     */


    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */


    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */



    /**
     * Delete from database
     * @param id
     * @param name
     */

}


























