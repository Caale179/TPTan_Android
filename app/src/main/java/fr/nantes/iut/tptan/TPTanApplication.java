package fr.nantes.iut.tptan;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import fr.nantes.iut.tptan.utils.SQLiteDatabaseFactory;

public class TPTanApplication extends Application {

    private SQLiteDatabaseFactory sqLiteDatabaseFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            sqLiteDatabaseFactory = new SQLiteDatabaseFactory();
            sqLiteDatabaseFactory.init(this, true, false);
        } catch ( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public SQLiteDatabase getDatabase() {
        return this.sqLiteDatabaseFactory.getDatabase();
    }
}
