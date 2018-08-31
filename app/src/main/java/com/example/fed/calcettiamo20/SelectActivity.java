package com.example.fed.calcettiamo20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import com.example.fed.calcettiamo20.SoccerDB.SoccerDBEntry;

import java.util.ArrayList;
import java.util.List;



public class SelectActivity extends AppCompatActivity {

    private List<String> soccers;
    private List<String> addresses;
    private List<String> prices;
    private ArrayAdapter<String> arrayAdapter;
    private String soccerName;
    private String address;
    private String price;
    private boolean switchState = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        updateSoccers();

        //adattiamo dinamicamente i dati presenti nell'arraylist in una listview
        arrayAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,soccers
                        );
        ListView listView = findViewById(R.id.listview1);
        listView.setAdapter(arrayAdapter);

        //evento associato allo switch button: permette di salvare lo stato dello switch in una variabile
        Switch mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchState = isChecked;
            }
        });




        //evento associato al click di un elemento della listview
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (switchState == false) { /*modalità seleziona: il click di un elemento permette di inviare dati in bundle
                                                  verso una nuova activity*/
                        Intent buildMapsIntent = new Intent();
                        buildMapsIntent.setClass(SelectActivity.this, MapsActivity.class);
                        buildMapsIntent.putExtra("name", soccers.get(i));
                        buildMapsIntent.putExtra("address", addresses.get(i));
                        buildMapsIntent.putExtra("price", prices.get(i));
                        startActivity(buildMapsIntent);
                    }else { // modalità cancella: permette di eliminare un elemento dalla lista, e quindi dal db.


                        String[] whereArgs = new String[] { soccers.get(i)};
                        MainActivity.db.delete(SoccerDBEntry.TABLE_NAME,"name=?", whereArgs);
                        finish();
                        startActivity(getIntent());

                    }

                }
            });












    }

    //metodo che, interfacciandosi col db sqlite,permette di estrapolarne i dati e accomodarli in una lista
    private void updateSoccers() {
        String[] projection = {SoccerDBEntry.COLUMN_NAME_NAME, SoccerDBEntry.COLUMN_NAME_ADDRESS, SoccerDBEntry.COLUMN_NAME_PRICE};
        String sortOrder = SoccerDBEntry.COLUMN_NAME_NAME + " ASC";

        //inizializzo il cursore che itererà i valori nella tabella
        Cursor cursor = MainActivity.db.query(SoccerDBEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);

        List<String> newSoccers = new ArrayList<String>();
        List<String> newAddresses  = new ArrayList<String>();
        List<String> newPrices = new ArrayList<String>();

        //iterazione tuple tabella
        while(cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(SoccerDBEntry.COLUMN_NAME_NAME);
            soccerName = cursor.getString(columnIndex);

            int columnIndex2 = cursor.getColumnIndex(SoccerDBEntry.COLUMN_NAME_ADDRESS);
            address = cursor.getString(columnIndex2);

            int columnIndex3 = cursor.getColumnIndex(SoccerDBEntry.COLUMN_NAME_PRICE);
            price = cursor.getString(columnIndex3);



            //accomodamento valori in liste
            newSoccers.add(soccerName);
            newAddresses.add(address);
            newPrices.add(price);

        }
        cursor.close();

        this.soccers = newSoccers;
        this.addresses = newAddresses;
        this.prices = newPrices;


        if(arrayAdapter != null) {
            arrayAdapter.clear();
            arrayAdapter.addAll(this.soccers);



        }
    }

}
