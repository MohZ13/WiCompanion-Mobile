package mohz.m13.wicompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Mohil Zalavadiya on 29-09-2017.
 */

class WiCompDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "WiCompanionDB";
    private static final int VERSION = 1;

    private static final String TABLE_IP_POOL = "IP_pool";

    WiCompDBHandler(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "create table if not exists IP_pool (ip_address TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void addIPAddress(String ipAddress) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ip_address", ipAddress);

        db.insert(TABLE_IP_POOL, null, values);
        db.close();
    }

    boolean isDistinctIPAddress(String ipAddress) {
        ArrayList<String> list = getAllIPAddress();
        return !list.contains(ipAddress);
    }

    ArrayList<String> getAllIPAddress() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IP_POOL;
        Cursor c = db.rawQuery(selectQuery, null);

        while (c.moveToNext()) {
            list.add(c.getString(0));
        }
        c.close();
        db.close();

        return list;
    }
}
