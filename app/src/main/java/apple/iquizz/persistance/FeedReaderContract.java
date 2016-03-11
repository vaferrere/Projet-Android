package apple.iquizz.persistance;

import android.provider.BaseColumns;

/**
 * Created by fabiengauthe on 06/03/2016.
 */

//Classe qui contient la définition de la base de donnée
// Comprend donc la définition de toutes les tables
public final class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class tTheme implements BaseColumns {
        public static final String TABLE_NAME = "tTheme";

        public static final String COLUMN_NAME_THEME_ID = "idTheme";
        public static final String COLUMN_NAME_THEME = "theme";
    }

    public static abstract class tQuestion implements BaseColumns {
        public static final String TABLE_NAME = "tQuestion";

        public static final String COLUMN_NAME_QUESTION_ID = "idQuestion";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_REPONSE_VRAI_ID = "idReponse";
        public static final String COLUMN_NAME_THEME_ID = "idTheme";
    }

    public static abstract class tReponse implements BaseColumns {
        public static final String TABLE_NAME = "tReponse";

        public static final String COLUMN_NAME_REPONSE_ID = "idReponse";
        public static final String COLUMN_NAME_REPONSE = "reponse";
        public static final String COLUMN_NAME_QUESTION_ID = "idQuestion";
    }


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //Définition de la création de la table
    public static final String SQL_CREATE_THEME =
            "CREATE TABLE " + tTheme.TABLE_NAME + " (" +
                    tTheme._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    tTheme.COLUMN_NAME_THEME + TEXT_TYPE +
                    " )";
    //Définition de la suppression de la table
    public static final String SQL_DELETE_THEME =
            "DROP TABLE IF EXISTS " + tTheme.TABLE_NAME;


    public static final String SQL_CREATE_QUESTION =
            "CREATE TABLE " + tQuestion.TABLE_NAME + " (" +
                    tQuestion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    tQuestion.COLUMN_NAME_QUESTION + TEXT_TYPE + COMMA_SEP +
                    tQuestion.COLUMN_NAME_REPONSE_VRAI_ID + " INTEGER" + COMMA_SEP +
                    tQuestion.COLUMN_NAME_THEME_ID + " INTEGER" + COMMA_SEP +
                    " FOREIGN KEY ("+tQuestion.COLUMN_NAME_REPONSE_VRAI_ID +") REFERENCES " +tReponse.TABLE_NAME+"("+tReponse._ID+")"+
                    " FOREIGN KEY ("+tQuestion.COLUMN_NAME_THEME_ID+") REFERENCES " +tTheme.TABLE_NAME+"("+tTheme._ID+")"+
                    " )";

    public static final String SQL_DELETE_QUESTION =
            "DROP TABLE IF EXISTS " + tQuestion.TABLE_NAME;


    public static final String SQL_CREATE_REPONSE =
            "CREATE TABLE " + tReponse.TABLE_NAME + " (" +
                    tReponse._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    tReponse.COLUMN_NAME_REPONSE + TEXT_TYPE + COMMA_SEP +
                    tReponse.COLUMN_NAME_QUESTION_ID + " INTEGER" + COMMA_SEP +
                    " FOREIGN KEY ("+tReponse.COLUMN_NAME_QUESTION_ID+") REFERENCES " +tQuestion.TABLE_NAME+"("+tQuestion._ID+")"+
                    " )";

    public static final String SQL_DELETE_REPONSE =
            "DROP TABLE IF EXISTS " + tReponse.TABLE_NAME;
}


