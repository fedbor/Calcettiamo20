package com.example.fed.calcettiamo20;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat;
    private double lon;
    private List<Address> location;
    private String address;
    private String fieldName;
    private String price;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //preleviamo i dati in bundle passati tramite intent
        Bundle extras = getIntent().getExtras();


        fieldName = extras.getString("name");
        address = extras.getString("address");
        price = extras.getString("price");

        // leghiamo la variabile tv della classe textview con la view identificata da pricetext e impostiamo il testo da mostrare
        TextView tv = findViewById(R.id.priceText);
        tv.setText("Prezzo:" + price + "€");


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //inizializzazione geocoder
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());


        try {
            location = geocoder.getFromLocationName(address, 1); //tale metodo permette di ottenere
        } catch (IOException e) {                                           // info relative all'indirizzo passato in input
            e.printStackTrace();
        }
        //fra le varie info ottenute ci interessano latitudine e longitudine associate all'indirizzo
        lat = location.get(0).getLatitude();
        lon = location.get(0).getLongitude();


        // aggiungiamo un marker per le coordinate trovate e zoommiamo
        LatLng coordinate = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(coordinate).title(fieldName));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17.0f));


    }


    //metodo legato ad un button
    public void sendInvitations(View v) {

        EditText editText = findViewById(R.id.input_date);
        date = editText.getText().toString();
        editText.setText("");

        //invio un intent implicita con del testo
        Intent send = new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.setType("text/plain");

        send.putExtra(Intent.EXTRA_TEXT, "Ciao sei stato convocato per una partita di calcetto! Appuntamento: "+ date + " al campo "
                + fieldName + " in "
                + address + ". Il prezzo è di "
                + price + "€.                   *Inviato tramite Calcettiamo app*");

        // try to launch the intent
        if (send.resolveActivity(getPackageManager()) != null) {
            startActivity(send);

        }


    }
}