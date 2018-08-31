package com.example.fed.calcettiamo20;

import android.provider.BaseColumns;



public class SoccerDB {

    private SoccerDB() {}

    public static class SoccerDBEntry implements BaseColumns {
        public static final String TABLE_NAME = "soccer";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_TIME = "timestamp";
    }
}
