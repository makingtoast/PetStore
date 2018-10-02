package com.example.wanjiku.ownstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.wanjiku.ownstore.data.PetContract.PetEntry;

public class PetdbHelper extends SQLiteOpenHelper{
    public static final String LOG_TAG = PetdbHelper.class.getSimpleName();

    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "store.db";

    public PetdbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE "+ PetEntry.TABLE_NAME+"("+ PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PetEntry.COLUMN_NAME+ " TEXT NOT NULL,"+ PetEntry.COLUMN_BREED +" TEXT," + PetEntry.COLUMN_GENDER +" INTEGER NOT NULL," + PetEntry.COLUMN_WEIGHT+" INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
