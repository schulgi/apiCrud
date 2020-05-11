package com.example.apicrud;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoActivity extends AppCompatActivity {
            @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_auto);



            Button btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
            Button btn_guardar = (Button) findViewById(R.id.btn_guardar);
            final EditText txtMarca = (EditText) findViewById(R.id.txtact_marca);
            final EditText txtModelo = (EditText) findViewById(R.id.txtact_modelo);


                btn_guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        crearAut(txtMarca.getText().toString(),txtModelo.getText().toString());

                    }
                });


                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AutoActivity.this, MainActivity.class);

                        startActivity(intent);

                        //Toast.makeText(getApplicationContext(), "Pagina principal", Toast.LENGTH_LONG);
                    }
                });
        }

    public void Cancelar(){
        Intent intent = new Intent(AutoActivity.this, MainActivity.class);

        Toast.makeText(getApplicationContext(), "Main", Toast.LENGTH_LONG).show();

        startActivity(intent);
    }

    public final void crearAut(String marca, String modelo){
        AutoService autoService = ApiClient.getAutoService();
        Auto auto= new Auto();
        auto.setMarca(marca);
        auto.setModelo(modelo);

        Call<ResponseBody> http_create = autoService.addAuto(marca,modelo);

        http_create.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Se creo el auto.", Toast.LENGTH_LONG).show();
                Cancelar();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AutoActivity.this, "No se pudo crear el auto", Toast.LENGTH_LONG).show();
            }

        });
}}