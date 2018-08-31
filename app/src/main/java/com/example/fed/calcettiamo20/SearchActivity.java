package com.example.fed.calcettiamo20;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.location.Location;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ListView listview;
    private ArrayAdapter<String> adapter;
    private List<FootballPitches> fList = new ArrayList<>();

    private List<String> addresses = new ArrayList<>();
    private List<String> prices = new ArrayList<>();
    private List<String> soccers = new ArrayList<>();
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    private boolean buttonState = false;
    private int distance = 1000000;
    GPSTracker gps;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //inizializzazione classe che gestisce gps
        gps = new GPSTracker(this);

        Bundle extras = getIntent().getExtras();

        //check degli extra passati dall'intent, serve a settare la varabile booleana buttonState
        if(extras != null) {
            buttonState = extras.getBoolean("state");

        }



        //adapter per adattare dinamicamente i dati di arraylist in listview
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,soccers);
        listview=(ListView)findViewById(R.id.listview1);
        listview.setAdapter(adapter);




        //evento associato al click di un elemento della listview,permette di passare dati in bundle tramite intent esplicito
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent buildMapsIntent = new Intent();
                buildMapsIntent.setClass(SearchActivity.this, MapsActivity.class);
                buildMapsIntent.putExtra("name", soccers.get(i));
                buildMapsIntent.putExtra("address", addresses.get(i));
                buildMapsIntent.putExtra("price", prices.get(i));
                startActivity(buildMapsIntent);

                                            }

                                        });



        //Introduciamo le referenze per interfacciarci al backend firebase
        DatabaseReference ref = database.getReference().child("FootballPitches");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {//esegue una volta il metodo onDataChange
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Salvo i valori contenuti in un file json in firebase all'interno di una lista di oggetti della classe FootballPitches
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FootballPitches footballPitch = postSnapshot.getValue(FootballPitches.class);
                    if (!fList.contains(footballPitch)) {
                        fList.add(footballPitch);
                    }

                }

                Location myLocation = new Location("");

                // Controlliamo che il gps sia attivo
                if(gps.canGetLocation()) {

                    double myLat = gps.getLatitude();
                    double myLon = gps.getLongitude();



                    myLocation.setLatitude(myLat);
                    myLocation.setLongitude(myLon);


                    Toast.makeText(getApplicationContext(), "Geolocalizzato", Toast.LENGTH_LONG).show();
                } else {

                    gps.showSettingsAlert();
                }




                /* Confrontiamo la nostra posiziona acquisita con le coordinate di altri luoghi
                 ottenute importando i dati da firebase */
                for(int i=0;i<fList.size();i++){




                    List<Address>loc = null;
                    try {
                        loc = geocoder.getFromLocationName(fList.get(i).getAddress(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    double dLat = loc.get(0).getLatitude();
                    double dLon = loc.get(0).getLongitude();
                    Location destination = new Location("");
                    destination.setLatitude(dLat);
                    destination.setLongitude(dLon);

                    //verifichiamo se sia stato cliccato il pulsante per filtrare luoghi nelle vicinanze
                    if (buttonState == true) {
                        distance = 10000;
                    }

                    /*aggiungiamo i luoghi idonei(non più distanti di 10 km dalla posizione accquisita)
                      filtrandoli grazie al metodo distanceTo della classe Location*/
                    if(myLocation.distanceTo(destination) < distance) {

                        soccers.add(fList.get(i).getSoccerName());
                        addresses.add(fList.get(i).getAddress());
                        prices.add(fList.get(i).getPrice());
                    }

                }



                adapter.notifyDataSetChanged ();






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });








    }


    //metodo associato ad un button, setta lo stato a true e ricarica l'activity
    public void near(View v) {


        buttonState = true;
        Intent reload = new Intent();
        reload.setClass(SearchActivity.this, SearchActivity.class);
        reload.putExtra("state",buttonState);
        finish();
        startActivity(reload);


    }



}


