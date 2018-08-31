package com.example.fed.calcettiamo20;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fed.calcettiamo20.SoccerDB.SoccerDBEntry;


public class SoccerDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "soccers.db";

    public SoccerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //crea tabella database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                        SoccerDBEntry.TABLE_NAME,
                        SoccerDBEntry._ID,
                        SoccerDBEntry.COLUMN_NAME_NAME,
                        SoccerDBEntry.COLUMN_NAME_ADDRESS,
                        SoccerDBEntry.COLUMN_NAME_PRICE,
                        SoccerDBEntry.COLUMN_NAME_TIME);

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO
    }
}
