package com.tommavroidis.wifi3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WIFI     = 1;
    private TextView wifiStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        wifiStatus     = (TextView) findViewById(R.id.WIFI_STATUS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Name: toggleWifi
     * Description: Uses WifiManager to toggle the Wifi on/off
     *
     * @param v     The view that was clicked
     */
    public void toggleWifi(View v) {
        WifiManager wifiManager =
                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
            wifiStatus.setText(getString(R.string.wifi_toggled_off));
        } else {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            wifiManager.setWifiEnabled(true);

            if (wifiInfo != null) {
                wifiStatus.setText(getString(R.string.wifi_available,
                        wifiInfo.getMacAddress()));
            } else {
                wifiStatus.setText(getString(R.string.wifi_null));
            }
        }
    }

    /**
     * Name: connectWifiNetwork
     * Description: Opens the setting page to enable the user to turn
     *              Wifi on and connect to a specific network
     *
     * @param v     The view that was clicked
     */
    public void connectWifiNetwork(View v) {
        WifiManager wifiManager =
                (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            wifiStatus.setText(getString(R.string.wifi_already_enabled));
        } else {
            Intent wifiIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivityForResult(wifiIntent, REQUEST_WIFI);
        }
    }

    /**
     * Name: onActivityResult
     * Description: Handles the result of each new Activity/Intent call
     *              for the bluetooth, gps, and wifi
     *
     * @param requestCode A number code that denotes what activity
     *                    (Gps, Wifi, Bluetooth) to handle.
     * @param resultCode  A code that denotes the outcome of the
     *                    subsequent activity that was called.
     * @param data        Carries the result of the data from the second activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_WIFI:
                WifiManager wifi =
                        (WifiManager) getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled()) {
                    wifiStatus.setText(getString(R.string.wifi_enabled));
                } else {
                    wifiStatus.setText(getString(R.string.try_again));
                }
                break;

        }
    }


}
