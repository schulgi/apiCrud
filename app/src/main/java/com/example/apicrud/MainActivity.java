package com.example.apicrud;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btnAddAuto;
    Button btnGetAutoList;

    ListView list;
    ListAdapter adaptador;
    ArrayList<String> autos = new ArrayList<>();
    ArrayList<Auto> autosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autos);

        list = (ListView) findViewById(R.id.list);

        list.setAdapter(adaptador);
        this.getListadoVehiculos();

  list.setOnItemClickListener(new ListView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent appInfo = new Intent(MainActivity.this, AutoDetalle.class);

            appInfo.putExtra(Long.toString(id), autosList.get(position).getId());

            startActivity(appInfo);
        }
    });
        list.setAdapter(adaptador);
        this.getListadoVehiculos();
}


    public void newAuto(View v){

        Intent intent = new Intent(MainActivity.this, AutoActivity.class);
        startActivity(intent);
    }

    public void getListadoVehiculos(){

        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);


        Call<List<Auto>> http_call = autoService.getAutos();

        http_call.enqueue(new Callback<List<Auto>>() {
            @Override
            public void onResponse(Call<List<Auto>> call, Response<List<Auto>> response) {
                // Si el servidor responde correctamente puedo hacer uso de la respuesta esperada:
                autos.clear();

                for (Auto auto: response.body()){
                    autos.add(auto.getMarca() + " - " + auto.getModelo());

                    autosList.add(auto);
                }

                // Aviso al base adapter que cambio mi set de datos.
                // Renderizacion general de mi ListView
                ((BaseAdapter) adaptador).notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Auto>> call, Throwable t) {
                // SI el servidor o la llamada no puede ejecutarse, muestro un mensaje de eror:
                Toast.makeText(MainActivity.this,"Hubo un error con la llamada a la API", Toast.LENGTH_LONG);

            }

        });

}

    }