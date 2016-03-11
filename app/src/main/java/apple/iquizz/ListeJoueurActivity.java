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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListeJoueurActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private ArrayList<String> mArrayAdapter = new ArrayList<String>();
    private RecyclerView lv_recherche_joueur;
    private TextView emptyView;
    private ArrayList<String> éléments = new ArrayList<String>();
    private Button boutton_retour;

    private Button boutton_refresh ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_joueur);
        Context context = getApplicationContext();
        boutton_retour = (Button) findViewById(R.id.bt_retour_recherche);
        boutton_refresh = (Button) findViewById(R.id.bt_recherche_refresh);

        if (boutton_refresh != null){
            boutton_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    set_bluetooth();
                    creationRecyclerView();
                }
            });
        }
        if (boutton_retour != null){
            boutton_retour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (éléments.size()==0){
                        set_bluetooth();
                        creationRecyclerView();
                    }else{
                        éléments.clear();
                        creationRecyclerView();
                    }

                }
            });
        }

        set_bluetooth();
        creationRecyclerView();



    }

    private void set_bluetooth(){
        éléments.clear();
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
                éléments.add(device.getName()+ "  adresse : " + device.getAddress());

            }

        }
        else {
        }
    }

    private void creationRecyclerView (){
        final RecyclerAdapter adapteur = new RecyclerAdapter(éléments);
        lv_recherche_joueur = (RecyclerView)  findViewById(R.id.rv_recherche_joueur);
        emptyView = (TextView) findViewById(R.id.empty_view);
        lv_recherche_joueur.setLayoutManager(new LinearLayoutManager(this));
        lv_recherche_joueur.setAdapter(adapteur);

        if (éléments.size() == 0){
            lv_recherche_joueur.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            lv_recherche_joueur.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }



}
