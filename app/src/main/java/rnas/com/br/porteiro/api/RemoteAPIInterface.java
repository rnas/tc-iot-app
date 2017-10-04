package rnas.com.br.porteiro.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rnas.com.br.porteiro.model.SuccessResponse;

/**
 * Created by rnas on 03/10/17.
 */

public interface RemoteAPIInterface {

    @GET("/API/hash/{hash}")
    Call<SuccessResponse> checkHash(@Path("hash") String hash);

}
