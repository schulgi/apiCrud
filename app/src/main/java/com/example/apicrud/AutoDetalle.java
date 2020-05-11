package com.example.apicrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AutoDetalle  extends AppCompatActivity {

Auto auto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autodetalle);

        Bundle bundle = getIntent().getExtras();

        String id = null;
        id = bundle.getString(id);

        Toast.makeText(AutoDetalle.this, id +"este id", Toast.LENGTH_LONG).show();

         final EditText text_marca = (EditText) findViewById(R.id.txtMarca);
         final EditText text_modelo = (EditText) findViewById(R.id.txtModelo);
        Button btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
        Button btn_eliminar = (Button) findViewById(R.id.btn_eliminar);
        Button btn_Cancelar = (Button) findViewById(R.id.btn_cancelar);

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutoDetalle.this, MainActivity.class);

                startActivity(intent);

            }
        });
        final String Id = id;
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAuto(Id,text_marca.getText().toString(),text_modelo.getText().toString()); //update auto
                Intent intent = new Intent(AutoDetalle.this, MainActivity.class);

                startActivity(intent);
            }
        });
        AutoService autoService = ApiClient.getAutoService();

        Call<Auto> http_call = autoService.getAuto(id);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Auto auto = response.body();

                TextView txtId = (TextView) findViewById(R.id.txtId);
                txtId.setText(auto.getId());
                TextView txtMarca = (TextView) findViewById(R.id.txtMarca);
                txtMarca.setText(auto.getMarca());
                TextView txtModelo = (TextView) findViewById(R.id.txtModelo);
                txtModelo.setText(auto.getModelo());

            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(AutoDetalle.this, "NO SE PUDO CONCRETAR LA PETICION", Toast.LENGTH_LONG);
            }
        });


    }
    public void getVehiculo(String iD){

        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);


        final Call<Auto> http_call = autoService.getAuto(iD);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                // Si el servidor responde correctamente puedo hacer uso de la respuesta esperada

            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(AutoDetalle.this,"Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
            }

        });

    }

    public final void updateAuto(String id,String marca, String modelo) {
        AutoService autoService = ApiClient.getAutoService();
        Auto auto = (Auto) autoService.getAuto(id);
        auto.setMarca(marca);
        auto.setModelo(modelo);

        Call<Auto> http_create = autoService.updateAuto(id, marca, modelo);

        http_create.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Toast.makeText(getApplicationContext(), "Se actualizo el auto.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(AutoDetalle.this, "No se pudo actualizar el auto", Toast.LENGTH_LONG).show();
            }

        });
    }
    public final void deleteAuto(String id) {
        AutoService autoService = ApiClient.getAutoService();

        Call<Auto> http_delete = autoService.deleteAuto(id);

        http_delete.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Toast.makeText(getApplicationContext(), "Se elimino el auto.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(AutoDetalle.this, "No se pudo eliminar el auto", Toast.LENGTH_LONG).show();
            }

        });
    }
}

