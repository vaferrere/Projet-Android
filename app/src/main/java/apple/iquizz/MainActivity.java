package apple.iquizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


import apple.iquizz.model.Question;
import apple.iquizz.model.Reponse;
import apple.iquizz.model.Theme;
import apple.iquizz.persistance.MySQLManager;
import apple.iquizz.view.ChallengeActivity;


public class MainActivity extends AppCompatActivity {


    private Button boutton_recherche ;

    private Button boutton_quitter ;

    private EditText pseudo ;

    private void monTest()
    {

       // MySQLiteManager ma = new MySQLiteManager(getBaseContext());

        MySQLManager ma = new MySQLManager(getBaseContext());

        //ma.insertTheme("Voyage");
        //ma.insertReponse("Poule", 1);
        //List<String> list = ma.getAllThemes();
        //ma.insertQuestion("Quelle est la capitale de l'Italie ?", 2, 2);
        //ma.insertReponse("Nevers", 2);
        //ma.insertReponse("Rome", 2);

        //List<String> list = ma.getAllThemes();

        /*for(String s : list)
        {
            Log.i("SQLite : ", s);
        */

        //ma.insertQuestion("Quelle est la capitale de Paris ?", 2, 1);
        //ma.insertReponse("16ème arrondissement", 1);
        //ma.insertReponse("Jacque Martin", 2);

        List<Theme> list = ma.getAllThemes();

        for(Theme t : list)
        {
            Log.i("ID : ", String.valueOf(t.getId()));
            Log.i("Theme : ", t.getTheme());
        }

        List<Question> listQ = ma.getQuestions(1);
        for(Question q : listQ)
        {
            Log.i("ID : ", String.valueOf(q.getId()));
            Log.i("Question : ", q.getQuestion());
            Log.i("idREponseVrai : ", String.valueOf(q.getIdReponseVrai()));
            Log.i("idTheme : ", String.valueOf(q.getIdTheme()));
        }

        List<Reponse> listR = ma.getReponses(1);
        for(Reponse r : listR)
        {
            Log.i("ID : ", String.valueOf(r.getId()));
            Log.i("Reponse : ", r.getReponse());
            Log.i("idQuestion : ", String.valueOf(r.getIdQuestion()));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boutton_quitter = (Button) findViewById(R.id.boutton_quitter);
        boutton_recherche = (Button) findViewById(R.id.boutton_recherche);
        pseudo = (EditText) findViewById(R.id.pseudo);

        boutton_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //monTest();
            }
        });

        boutton_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ListeJoueurActivity.class));
            }
        });
    }
}
