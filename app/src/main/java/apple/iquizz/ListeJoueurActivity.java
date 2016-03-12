package apple.iquizz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListeJoueurActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BroadcastReceiver mReceiver;
    private ArrayList<String> mArrayAdapter = new ArrayList<>();
    private RecyclerView lv_recherche_joueur;
    private TextView emptyView;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> elements = new ArrayList<>();
    private List<String> 
    private Button boutton_retour;
    private UUID uuid ;
    private String appName = "iQuizz" ;
    private AcceptThread mAcceptThread ;
    private ConnectThread mConnectThread ;
    private BluetoothServerSocket mmServerSocket;

    private Button boutton_mode_serveur;
    private Button boutton_refresh ;
    private RecyclerAdapter adapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_joueur);
        Context context = getApplicationContext();
        boutton_retour = (Button) findViewById(R.id.bt_retour_recherche);
        boutton_refresh = (Button) findViewById(R.id.bt_recherche_refresh);
        boutton_mode_serveur = (Button) findViewById(R.id.bt_mode_serveur);
        creationRecyclerView();



        set_bluetooth();
        uuid = (new DeviceUuidFactory(getApplicationContext())).getDeviceUuid();
        mAcceptThread = new AcceptThread();


        if (boutton_mode_serveur != null){
            boutton_mode_serveur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(discoverableIntent);
                    while(mmServerSocket==null){
                        mAcceptThread.run();
                    }



                }
            });
        }

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

        lv_recherche_joueur.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        LinearLayout layout_selectionne = (LinearLayout) view  ;
                        TextView element_selectionne =  (TextView) layout_selectionne.getChildAt(0);
                        Log.i("test2", "je suis la vue : " + element_selectionne.getText() + " à la position : " + String.valueOf(position));
                        mConnectThread = new ConnectThread();
                    }
                })
        );


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

        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
        if (mAcceptThread != null){
            mAcceptThread.cancel();
        }
        super.onDestroy();
    }

    private class AcceptThread extends Thread {

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                if (uuid != null){
                    tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(appName,uuid);
                }
                else{
                    Log.i("uuid","UUID est null");
                }

            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)

        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
}
