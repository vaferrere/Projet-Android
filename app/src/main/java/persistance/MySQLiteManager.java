package persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistance.FeedReaderContract.*;

/**
 * Created by fabiengauthe on 06/03/2016.
 */
public class MySQLiteManager {

    private FeedReaderDbHelper mdbHelper;
    private SQLiteDatabase db;

    public MySQLiteManager(Context context)
    {
        mdbHelper =  new FeedReaderDbHelper(context);
    }

    public List<String> getAllThemes()
    {
        db = mdbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.tTheme.COLUMN_NAME_THEME,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedReaderContract.tQuestion._ID + " ASC";

        Cursor c = db.query(
                FeedReaderContract.tTheme.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        List<String> lesThemes = new ArrayList<String>();

        while (c.isAfterLast() == false)
        {
            lesThemes.add(c.getString(c.getColumnIndexOrThrow(tTheme.COLUMN_NAME_THEME)));
            c.moveToNext();
        }
        return lesThemes;
    }

    public long insertTheme(String theme)
    {
        db = mdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tTheme.COLUMN_NAME_THEME, theme);

        long newRowId;
        newRowId = db.insert(
                tTheme.TABLE_NAME,
                tTheme._ID, //Column de la table qui peut etre null si on ne met rien dans la map
                values);
        return newRowId;
    }

    public List<String> getReponses(long idQuestion)
    {
        db = mdbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                tReponse._ID,
                tReponse.COLUMN_NAME_REPONSE,
                tReponse.COLUMN_NAME_QUESTION_ID,
        };

        Cursor c = db.query(
                tReponse.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                tReponse.COLUMN_NAME_QUESTION_ID + " = " + idQuestion ,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();
        List<String> lesReponse = new ArrayList<String>();

        while (c.isAfterLast() == false)
        {
            lesReponse.add(c.getString(c.getColumnIndexOrThrow(tReponse.COLUMN_NAME_REPONSE)));
            c.moveToNext();
        }
        return lesReponse;
    }

    public long insertReponse(String reponse, long idQuestion)
    {
        db = mdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tReponse.COLUMN_NAME_REPONSE, reponse);
        values.put(tReponse.COLUMN_NAME_QUESTION_ID, idQuestion);

        long newRowId;
        newRowId = db.insert(
                tReponse.TABLE_NAME,
                tReponse._ID, //Column de la table qui peut etre null si on ne met rien dans la map
                values);
        return newRowId;
    }

    public List<String> getQuestions(long idTheme)
    {
        db = mdbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                tQuestion._ID,
                tQuestion.COLUMN_NAME_QUESTION,
                tQuestion.COLUMN_NAME_REPONSE_VRAI_ID,
                tQuestion.COLUMN_NAME_THEME_ID,
        };

        Cursor c = db.query(
                tQuestion.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                tQuestion.COLUMN_NAME_THEME_ID + " = " + idTheme,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();
        List<String> lesQuestions = new ArrayList<String>();

        while (c.isAfterLast() == false)
        {
            lesQuestions.add(c.getString(c.getColumnIndexOrThrow(tQuestion.COLUMN_NAME_QUESTION)));
            c.moveToNext();
        }
        return lesQuestions;
    }

    public long insertQuestion(String question, long idReponse, long idTheme)
    {
        db = mdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tQuestion.COLUMN_NAME_QUESTION, question);
        values.put(tQuestion.COLUMN_NAME_REPONSE_VRAI_ID, idReponse);
        values.put(tQuestion.COLUMN_NAME_THEME_ID, idTheme);

        long newRowId;
        newRowId = db.insert(
                tQuestion.TABLE_NAME,
                tQuestion._ID, //Column de la table qui peut etre null si on ne met rien dans la map
                values);
        return newRowId;
    }
}
