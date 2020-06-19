package com.example.projekt3;

import android.provider.BaseColumns;

public class Tables {
    private Tables(){}
    public static final class Gear implements BaseColumns
    {
        public static final String TABLE_NAME ="rzeczy";
        public static final String COLUMN_ID="_id";
        public static final String COLUMN_NAME="nazwa";
        public static final String COLUMN_COLOR ="kolor";
        public static final String COLUMN_TYPE ="typ";
        public static final String COLUMN_URL ="url";

    }

}
