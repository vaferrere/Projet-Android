package apple.iquizz.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import apple.iquizz.model.Question;
import apple.iquizz.model.Reponse;
import apple.iquizz.model.Theme;
import apple.iquizz.persistance.FeedReaderContract.*;

/**
 * Created by fabiengauthe on 06/03/2016.
 */
public class MySQLManager {

    private FeedReaderDbHelper mdbHelper;
    private SQLiteDatabase db;

    public MySQLManager(Context context)
    {
        mdbHelper =  new FeedReaderDbHelper(context);
    }

    public List<Theme> getAllThemes()
    {
        db = mdbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                tTheme._ID,
                tTheme.COLUMN_NAME_THEME,
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
        List<Theme> lesThemes = new ArrayList<Theme>();

        while (!c.isAfterLast())
        {
            lesThemes.add(new Theme(c.getInt(c.getColumnIndexOrThrow(tTheme._ID)), c.getString(c.getColumnIndexOrThrow(tTheme.COLUMN_NAME_THEME))));
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

    public List<Reponse> getReponses(long idQuestion)
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
        List<Reponse> lesReponse = new ArrayList<Reponse>();

        while (!c.isAfterLast())
        {
            int id = c.getInt(c.getColumnIndexOrThrow(tReponse._ID));
            String rep = c.getString(c.getColumnIndexOrThrow(tReponse.COLUMN_NAME_REPONSE));
            int id_question = c.getInt(c.getColumnIndexOrThrow(tReponse.COLUMN_NAME_QUESTION_ID));

            lesReponse.add(new Reponse(id, rep, id_question));
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

    public List<Question> getQuestions(long idTheme)
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
        List<Question> lesQuestions = new ArrayList<Question>();

        while (!c.isAfterLast())
        {
            int id = c.getInt(c.getColumnIndexOrThrow(tQuestion._ID));
            String question = c.getString((c.getColumnIndexOrThrow(tQuestion.COLUMN_NAME_QUESTION)));
            int idRepVrai = c.getInt(c.getColumnIndexOrThrow((tQuestion.COLUMN_NAME_REPONSE_VRAI_ID)));
            int id_theme = c.getInt(c.getColumnIndexOrThrow(tQuestion.COLUMN_NAME_THEME_ID));

            lesQuestions.add(new Question(id, question, idRepVrai, id_theme));
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
