package apple.iquizz.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by fabiengauthe on 06/03/2016.
 */
//Classe qui va permettre la création complète de la base
public class FeedReaderDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "iquizz.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.SQL_CREATE_THEME);
        db.execSQL(FeedReaderContract.SQL_CREATE_QUESTION);
        db.execSQL(FeedReaderContract.SQL_CREATE_REPONSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(FeedReaderContract.SQL_CREATE_THEME);
        db.execSQL(FeedReaderContract.SQL_CREATE_QUESTION);
        db.execSQL(FeedReaderContract.SQL_CREATE_REPONSE);
        onCreate(db);
    }
}
