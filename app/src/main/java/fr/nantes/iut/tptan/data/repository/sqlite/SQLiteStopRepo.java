package fr.nantes.iut.tptan.data.repository.sqlite;

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

    private Stop readRow( Cursor cursor ) {
        Stop stop = new Stop();
        stop.setId(cursor.getString(0).substring(cursor.getString(0).indexOf(':')));
        //TODO T101: Terminer la lecture du cursor
        return stop;
    }
}