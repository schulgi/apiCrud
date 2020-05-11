package com.example.apicrud;
import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface AutoService {

    /**
     * Definicion de ruta para GET
     */
    String API_ROUTE_READ= "app/api/read";
    String API_ROUTE_READID= "app/api/read/{id}";
    String API_ROUTE_CREATE= "app/api/create";
    String API_ROUTE_UPDATE= "app/api/update/{id}";
    String API_ROUTE_DELETE= "app/api/delete/{id}";
    /**
     * Metodo abstracto para utilizar HTTP.GET
     * @return
     */
    @GET(API_ROUTE_READ)
    Call<List<Auto>> getAutos();

    @GET(API_ROUTE_READID)
    Call<Auto> getAuto(@Path("id") String id);

    @POST(API_ROUTE_CREATE)
    Call<ResponseBody> addAuto(@Path("Marca") String marca, @Path("Modelo") String modelo);

    @PUT(API_ROUTE_UPDATE)
    Call<Auto> updateAuto(@Path("id") String id,@Path("marca")String marca, @Path("modelo") String modelo);

    @DELETE(API_ROUTE_DELETE)
    Call<Auto> deleteAuto(@Path("id") String id);


}
