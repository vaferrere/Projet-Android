package apple.iquizz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListeJoueurActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BroadcastReceiver mReceiver;
    private ArrayList<String> mArrayAdapter = new ArrayList<>();
    private RecyclerView lv_recherche_joueur;
    private TextView emptyView;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> elements = new ArrayList<>();
    private Button boutton_retour;


    private Button boutton_refresh ;
    private RecyclerAdapter adapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_joueur);
        Context context = getApplicationContext();
        boutton_retour = (Button) findViewById(R.id.bt_retour_recherche);
        boutton_refresh = (Button) findViewById(R.id.bt_recherche_refresh);
        creationRecyclerView();

        if (boutton_refresh != null){
            boutton_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBluetoothAdapter.cancelDiscovery();
                    set_bluetooth();
                }
            });
        }
        if (boutton_retour != null){
            boutton_retour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        set_bluetooth();



    }

    private void set_bluetooth(){
        elements.clear();
        adapteur.notifyDataSetChanged();
        isEmpty();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
        }

        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        /*Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());


            }

        }
        else {
        }*/


        // Create a BroadcastReceiver for ACTION_FOUND
       mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    String device_name = device.getName() + "  adresse : " + device.getAddress();
                    if ( !elements.contains(device_name)){
                        elements.add(device_name);
                    }
                    adapteur.notifyDataSetChanged();
                }
                isEmpty();
            }
        };
// Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        mBluetoothAdapter.startDiscovery();



    }

    private void creationRecyclerView (){
        adapteur = new RecyclerAdapter(elements);
        lv_recherche_joueur = (RecyclerView)  findViewById(R.id.rv_recherche_joueur);
        emptyView = (TextView) findViewById(R.id.empty_view);
        lv_recherche_joueur.setLayoutManager(new LinearLayoutManager(this));
        lv_recherche_joueur.setAdapter(adapteur);


    }

    private void isEmpty(){
        if (elements.isEmpty()){
            lv_recherche_joueur.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            lv_recherche_joueur.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
    }


}
