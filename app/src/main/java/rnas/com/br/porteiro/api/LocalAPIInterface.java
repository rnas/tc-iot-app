package rnas.com.br.porteiro.api;

/**
 * Created by rnas on 03/10/17.
 */

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rnas.com.br.porteiro.model.SuccessResponse;

public interface LocalAPIInterface {

    @GET("/open")
    Call<SuccessResponse> openDoor();

}
