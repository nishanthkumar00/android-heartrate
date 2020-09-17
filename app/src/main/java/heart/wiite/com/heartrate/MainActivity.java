package heart.wiite.com.heartrate;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BODY_SENSORS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.hardware.SensorManager.SENSOR_STATUS_NO_CONTACT;

public class MainActivity extends Activity implements SensorEventListener {
    TextView mHeartTextView;

    private SensorManager sensorManager2;
    public static float mems_data[] = new float[]{0, 0, 0, 0, 0};
    private int heartRate;
    Sensor sensor2;
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        mHeartTextView = (TextView) this.findViewById(R.id.tvtitle);
        sensorManager2 = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor2 = sensorManager2
                .getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager2.registerListener(this, sensor2,
                SensorManager.SENSOR_DELAY_GAME);
        new CreateFile().generateNoteOnSD(this, "\nheartRate_qs  heart sensor registered");
    }

    private void requestPermissions() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, BODY_SENSORS,
                    ACCESS_COARSE_LOCATION, READ_PHONE_STATE, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterSensor();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        /*sensorManager2.registerListener(this, sensor2,
                SensorManager.SENSOR_DELAY_GAME);*/

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        //unRegisterSensor();
    }

    protected void unRegisterSensor() {
        if (sensorManager2 != null) {
            sensorManager2.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_HEART_RATE:
                new CreateFile().generateNoteOnSD(this, "\nheartRate_qs event accuracy " + event.accuracy);
                mems_data[0] = event.values[0];
                //mems_data[1] = event.values[1];
                //mems_data[2] = event.values[2];
                //mems_data[3] = event.values[3];
                //mems_data[4] = event.values[4];
                Log.d("qs_onSensorChanged: ", event.values.length + "");

                heartRate = (int) mems_data[0];
                mHeartTextView.setText(" HeartRate: " + heartRate);
                new CreateFile().generateNoteOnSD(this, "\nHeartRate: " + heartRate);
                Log.d("qs_heartRate", " 心率：" + mems_data[0] + "," + mems_data[1] + "," + mems_data[2] + "," + mems_data[3] + "," + mems_data[4]);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("heartRate_qs", " onAccuracyChanged");
        if (sensor.getType() == Sensor.TYPE_HEART_RATE) {
            if (accuracy == SENSOR_STATUS_NO_CONTACT) {
                new CreateFile().generateNoteOnSD(this, "\nheartRate_qs  onAccuracyChanged " + accuracy);
                Log.e("heartRate_qs", "NoContact");
            }
        }
    }

}
