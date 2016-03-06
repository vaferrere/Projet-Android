package apple.iquizz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private Button boutton_recherche ;

    private Button boutton_quitter ;

    private EditText pseudo ;



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
