package com.telephonyManagerTest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button boton;
    TextView texto;
    String info;
    String strPhoneType = "";

    static final int PERMISSION_READ_STATE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.boton);
        texto = findViewById(R.id.textView);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PermissionChek = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);

                if (PermissionChek == PackageManager.PERMISSION_GRANTED) {

                    MyTelephonyManager();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
                }
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case PERMISSION_READ_STATE:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyTelephonyManager();
                } else {
                    Toast.makeText(getApplicationContext(), "requiere permiisos par esta app", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void MyTelephonyManager() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = manager.getPhoneType();

        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strPhoneType = "CDMA";
                break;

            case (TelephonyManager.PHONE_TYPE_GSM):
                strPhoneType = "GSM";
                break;

            case (TelephonyManager.PHONE_TYPE_NONE):
                strPhoneType = "NONE";
                break;
        }

        boolean isRoaming = manager.isNetworkRoaming();

        String PhoneType = strPhoneType;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String IMEInumber = manager.getDeviceId();
        String suscriberId = manager.getDeviceId();
        String SIMSerialNumber = manager.getSimSerialNumber();
        String networkCountryISO = manager.getNetworkCountryIso();
        String SIMcountrISO = manager.getNetworkCountryIso();
        String softwareVersion = manager.getDeviceSoftwareVersion();
        String voiceMailNumber = manager.getVoiceMailNumber();
        String linia = manager.getLine1Number() + " " + manager.getSubscriberId();


        info = "Phone Details: \n";
        info += "\n Phone Network Type: "+PhoneType;
        info += "\n Imei number: "+IMEInumber;
        info += "\n Suscriber Id: "+suscriberId;
        info += "\n Sim serial number: "+SIMSerialNumber;
        info += "\n networ country iso "+networkCountryISO;
        info += "\n sim country iso: "+SIMcountrISO;
        info += "\n Software version: "+softwareVersion;
        info += "\n Voice mail number: "+voiceMailNumber;
        info += "\n isRoaming: "+isRoaming;
        info += "\n linia: "+linia;

        texto.setText(info);
    }
}
