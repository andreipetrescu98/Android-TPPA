package tppa.lab2.onlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorsActivity extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_PERMISSION_ACCESS_LOCATION = 100;
    private static final String TAG = "SensorActivity";
    SensorManager sensorManager;

    TextView barometer;
    TextView luminosity;
    TextView proximity;
    TextView thermometer;
    TextView humidity;
    TextView gyroscope;
    TextView geolocation;

    HashMap<Integer, TextView> typeToTextViewMap = new HashMap<>();
    List<Sensor> sensors = new ArrayList<>();
    List<SensorEventListener> sensorEventListeners = new ArrayList<>();

    List<Integer> sensorTypes = new ArrayList<>();

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        getSupportActionBar().hide();
        addSensorTypes();
        setUiViews();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        for (Sensor sensor : sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            Log.i(TAG, "[=SENSOR=] " + sensor.getName());
        }

        for (final Integer sensorType : sensorTypes) {
            final Sensor sensor = sensorManager.getDefaultSensor(sensorType);

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    typeToTextViewMap.get(sensorType).setText(sensorInfo(event, sensorType));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };

            sensors.add(sensor);
            sensorEventListeners.add(sensorEventListener);
        }

        manageLocation();
    }

    private void manageLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(SensorsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_ACCESS_LOCATION);
            }
        } else {
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        }
        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 1, this);
    }

    private String sensorInfo(SensorEvent sensorEvent, Integer sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_PRESSURE:
                return String.format("Barometer: %.3f mbar", sensorEvent.values[0]);
            case Sensor.TYPE_LIGHT:
                return String.format("Luminosity: %.3f lux", sensorEvent.values[0]);
            case Sensor.TYPE_PROXIMITY:
                return String.format("Proximity: %.2f cm", sensorEvent.values[0]);
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return String.format("Temperature: %.2f *C", sensorEvent.values[0]);
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Humidity: " + sensorEvent.values[0] + " %";
            case Sensor.TYPE_GYROSCOPE:
                return "X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2];
            default:
                return "NO DATA";
        }
    }

    private void addSensorTypes() {
        sensorTypes.add(Sensor.TYPE_PRESSURE);
        sensorTypes.add(Sensor.TYPE_LIGHT);
        sensorTypes.add(Sensor.TYPE_PROXIMITY);
        sensorTypes.add(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorTypes.add(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorTypes.add(Sensor.TYPE_GYROSCOPE);
    }

    private void setUiViews() {
        barometer = findViewById(R.id.barometer_text_view);
        typeToTextViewMap.put(Sensor.TYPE_PRESSURE, barometer);

        luminosity = findViewById(R.id.luminosity_text_view);
        typeToTextViewMap.put(Sensor.TYPE_LIGHT, luminosity);

        proximity = findViewById(R.id.proximity_text_view);
        typeToTextViewMap.put(Sensor.TYPE_PROXIMITY, proximity);

        thermometer = findViewById(R.id.thermometer_text_view);
        typeToTextViewMap.put(Sensor.TYPE_AMBIENT_TEMPERATURE, thermometer);

        humidity = findViewById(R.id.humidity_text_view);
        typeToTextViewMap.put(Sensor.TYPE_RELATIVE_HUMIDITY, humidity);

        gyroscope = findViewById(R.id.gyroscope_text_view);
        typeToTextViewMap.put(Sensor.TYPE_GYROSCOPE, gyroscope);

        geolocation = findViewById(R.id.geolocation_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < sensorEventListeners.size(); i++) {
            sensorManager.registerListener(sensorEventListeners.get(i), sensors.get(i), SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (SensorEventListener sensorEventListener : sensorEventListeners) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        geolocation.setText("Geolocation: (Long: " + location.getLongitude() + ", Lat: + " + location.getLatitude() + ", Alt: " + location.getAltitude() + ")");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
