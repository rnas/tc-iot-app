package rnas.com.br.porteiro;

import android.graphics.PointF;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rnas.com.br.porteiro.api.LocalAPIClient;
import rnas.com.br.porteiro.api.LocalAPIInterface;
import rnas.com.br.porteiro.api.RemoteAPIClient;
import rnas.com.br.porteiro.api.RemoteAPIInterface;

public class MainActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    LocalAPIInterface localAPIInterface;
    RemoteAPIInterface remoteAPIInterface;

    private QRCodeReaderView qrCodeReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : check and ask for permissions


        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(5000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();

        localAPIInterface = LocalAPIClient.getClient().create(LocalAPIInterface.class);
        remoteAPIInterface = RemoteAPIClient.getClient().create(RemoteAPIInterface.class);

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

        qrCodeReaderView.stopCamera();


        Call remote = remoteAPIInterface.checkHash(text);
        remote.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {

                    openDoor();

                    Log.i("WOW", "success");
                    showToast("tentando abrir porta");
                } else {
                    Log.i("WOW", "error");
                    showToast("não autorizado");
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public void openDoor() {
        Toast.makeText(this, getString(R.string.authorized), Toast.LENGTH_LONG).show();

        Call call = localAPIInterface.openDoor();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {
                    showToast("abrindo porta");
                    Log.i("WOW", "success");
                } else {
                    showToast("não autorizado");
                    Log.i("WOW", "error");
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                showToast("server error");
            }
        });
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }
}
