package rnas.com.br.porteiro.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by rnas on 03/10/17.
 */

public class SuccessResponse {

    @SerializedName("success")
    public boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
