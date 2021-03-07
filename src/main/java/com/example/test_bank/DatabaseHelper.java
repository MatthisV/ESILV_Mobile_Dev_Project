package com.example.test_bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String account_table = "ACCOUNT_TABLE";
    public static final String account_name = "ACCOUNT_NAME";
    public static final String account_iban = "ACCOUNT_IBAN";
    public static final String account_amount = "ACCOUNT_AMOUNT";
    public static final String account_currency = "ACCOUNT_CURRENCY";
    public static final String deleteTableStatement="DROP TABLE IF EXISTS "+account_table;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "accounts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String createTableStatement= "CREATE TABLE " + account_table + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + account_name + " TEXT, " + account_iban + " TEXT, " + account_amount + " TEXT, " + account_currency + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(deleteTableStatement);
    onCreate(db);
    }

    public boolean addData(JSONArray jsonArray) throws JSONException {

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+account_table+";");
        this.onCreate(db);
        ContentValues cv=new ContentValues();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject account = jsonArray.getJSONObject(i);
            String iban = account.getString("iban");
            String name = account.getString("account_name");
            String balance = account.getString("amount");
            String currency = account.getString("currency");

            cv.put(account_iban,iban);
            cv.put(account_name,name);
            cv.put(account_amount,balance);
            cv.put(account_currency,currency);
            long insert= db.insert(account_table,null,cv);


        }

        return true;
    }

    public account_model getAccount(String wanted_iban){

     String queryString="SELECT * FROM "+account_table+" WHERE "+account_iban+" = "+"'"+wanted_iban+"'";

     SQLiteDatabase db=this.getReadableDatabase();

        List<account_model> accountlist=new ArrayList<>();

     Cursor cursor=db.rawQuery(queryString,null);

     if (cursor.moveToFirst()){
         int accountId=cursor.getInt(0);
         String accountName=cursor.getString(1);
         String accountIban=cursor.getString(2);
         String accountAmount=cursor.getString(3);
         String accountCurrency=cursor.getString(4);
         account_model wantedAccount= new account_model(accountId,accountIban,accountName,accountCurrency,accountAmount);
         accountlist.add(wantedAccount);
         cursor.close();
         db.close();
     }
     else {
         account_model returnAccount=new account_model();
         return returnAccount;
     }


     account_model returnAccount = accountlist.get(0);
     return returnAccount;




    }
}
