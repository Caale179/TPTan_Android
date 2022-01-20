package fr.nantes.iut.tptan.data.repository.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.nantes.iut.tptan.TPTanApplication;
import fr.nantes.iut.tptan.data.entity.Stop;

public class SQLiteStopRepo {

    private static final String TABLE_NAME = "STOPS";

    public Stop getStop(String stopId, Context context ) {
        Stop stop = null;
        SQLiteDatabase database = ((TPTanApplication)context.getApplicationContext()).getDatabase();
        String searchValue = "%:" + stopId;
        Cursor cursor = database.query(TABLE_NAME, null, "stop_id like ?", new String[] { searchValue }, null, null, null );
        try {
            if (cursor.moveToFirst()) {
                stop = readRow(cursor);
            }
        } finally {
            cursor.close();
        }

        return stop;
    }

    @SuppressLint("Range")
    private Stop readRow(Cursor cursor ) {
        Stop stop = new Stop();

        stop.setId(cursor.getString(0).substring(cursor.getString(0).indexOf(':')));

        stop.setName(cursor.getString(1));
        stop.setDesc(cursor.getString(2));
        stop.setLat(cursor.getDouble(3));

        stop.setLng(cursor.getDouble(4));
        stop.setZoneId(cursor.getString(5));
        stop.setStopUrl(cursor.getString(6));
        stop.setLocationType(cursor.getInt(7));

        stop.setParentStation(cursor.getString(8));
        stop.setWheelChairBoarding(cursor.getInt(9));

        return stop;
    }
}