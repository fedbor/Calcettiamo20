package com.example.fed.calcettiamo20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.ContentValues;
import com.example.fed.calcettiamo20.SoccerDB.SoccerDBEntry;


public class AddActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



    }

    //metodo associato a un button, tramite getText() salva la stringa passata in input in una variabile
    public void itemAdd(View view) {
        EditText editText1 = findViewById(R.id.input_name);
        String name = editText1.getText().toString();
        editText1.setText("");

        EditText editText2 = findViewById(R.id.input_address);
        String address = editText2.getText().toString();
        editText2.setText("");

        EditText editText3 = findViewById(R.id.input_price);
        String price = editText3.getText().toString();
        editText3.setText("");

        //controlla validità inserimento parametri
        if(name.isEmpty() || address.isEmpty()){
            name = "campo non valido";
            address = "";
        }





        //salva i valori aquisiti
        ContentValues values = new ContentValues();
        values.put(SoccerDBEntry.COLUMN_NAME_NAME, name);
        values.put(SoccerDBEntry.COLUMN_NAME_ADDRESS, address);
        values.put(SoccerDBEntry.COLUMN_NAME_PRICE, price);
        values.put(SoccerDBEntry.COLUMN_NAME_TIME, System.currentTimeMillis());

        //inseriscili nel db
        MainActivity.db.insert(SoccerDBEntry.TABLE_NAME, null, values);

    }
}
