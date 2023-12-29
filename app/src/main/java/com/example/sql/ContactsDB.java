package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactsDB {
    public final String KEY_ROWID = "_id";
    public final String KEY_ROWNAME = "person_name";
    public final String KEY_ROWCELL = "_cell";
    private final String DATABASE_NAME = "ContactsDB";
    private final String DATABASE_TABLE = "ContactsTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB(Context context)
    {
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sqlCode = "CREATE TABLE "+DATABASE_TABLE+"( "+KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_ROWNAME+" TEXT NOT NULL,"
                    +KEY_ROWCELL+" TEXT NOT NULL);";
            sqLiteDatabase.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public ContactsDB open() throws SQLException
    {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String name, String cell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWNAME, name);
        cv.put(KEY_ROWCELL, cell);

        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData()
    {
        String []columns = new String[]{KEY_ROWID,
                KEY_ROWNAME,
                KEY_ROWCELL};
        Cursor c = ourDatabase.query(
                DATABASE_TABLE,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        String result = "";
        int iRowId = c.getColumnIndex(KEY_ROWID);
        int iRowName = c.getColumnIndex(KEY_ROWNAME);
        int iRowCell = c.getColumnIndex(KEY_ROWCELL);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            result = result+c.getString(iRowId)+": "+
                    c.getString(iRowName)+" "+
                    c.getString(iRowCell)+"\n";
        }
        c.close();
        return result;
    }

    public long deleteEntry(String rowId)
    {
        return ourDatabase.delete(
                DATABASE_TABLE,
                KEY_ROWID+"=?",
                new String[]{rowId}
        );
    }

    public long updateEntry(String rowId, String name, String cell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWNAME, name);
        cv.put(KEY_ROWCELL, cell);

        return ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID+"=?", new String[]{rowId});
    }

}
