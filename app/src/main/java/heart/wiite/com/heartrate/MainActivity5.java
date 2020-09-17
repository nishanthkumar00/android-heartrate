package heart.wiite.com.heartrate;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity5 extends Activity implements SensorEventListener {
    TextView mHeartTextView;

    private SensorManager sensorManager2;
    public static float mems_data[] = new float[]{0, 0, 0, 0, 0};
    public static int spo2h_buff0[] = new int[]{88, 89, 90, 91, 92, 93};
    public static int spo2h_buff1[] = new int[]{94, 95, 96, 97};
    public static int spo2h_buff2[] = new int[]{97, 98, 99};
    private int heartRate = 0;
    private int spo2h = 0;
    Sensor sensor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();
    }

    public void init() {
        mHeartTextView = (TextView) this.findViewById(R.id.tvtitle);
        sensorManager2 = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor2 = sensorManager2
                .getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager2.registerListener(this, sensor2,
                SensorManager.SENSOR_DELAY_GAME);
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
        sensorManager2.registerListener(this, sensor2,
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        unRegisterSensor();
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
                mems_data[0] = event.values[0];
                Log.d("qs_onSensorChanged: ", event.values.length + "");
                heartRate = (int) mems_data[0];
                int iRandom = (int) (1 + Math.random() * 10);
                if (heartRate < 45) {
                    spo2h = 0;
                } else if (heartRate < 50) {
                    spo2h = spo2h_buff0[0];
                } else if (heartRate < 60) {
                    iRandom = iRandom % 6;
                    spo2h = spo2h_buff0[iRandom];
                } else if (heartRate < 70) {
                    iRandom = iRandom % 4;
                    spo2h = spo2h_buff1[iRandom];
                } else if (heartRate <= 100) {
                    iRandom = iRandom % 3;
                    spo2h = spo2h_buff2[iRandom];
                } else {
                    spo2h = spo2h_buff2[2];
                }
                mHeartTextView.setText(" 血氧：" + spo2h);
                Log.d("qs_heartRate", " 血氧：" + spo2h);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        Log.d("heartRate_qs", " onAccuracyChanged");

        switch (sensor.getType()) {

            case Sensor.TYPE_HEART_RATE:

                Log.d("heartRate_qs", "onAccuracyChanged_TYPE_HEART_RATE");

        }

    }

}
