package apple.iquizz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class ListeJoueurActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private ArrayList<String> mArrayAdapter = new ArrayList<String>();
    private RecyclerView lv_recherche_joueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_joueur);

        final String[] éléments = {"bonjour","aurevoir","hey"};


        final RecyclerAdapter adapteur = new RecyclerAdapter(éléments);




        lv_recherche_joueur = (RecyclerView)  findViewById(R.id.rv_recherche_joueur);
        lv_recherche_joueur.setLayoutManager(new LinearLayoutManager(this));
        lv_recherche_joueur.setAdapter(adapteur);


        Context context = getApplicationContext();
        set_bluetooth();


    }

    private void set_bluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }

        }
        else {
        }
    }



}
