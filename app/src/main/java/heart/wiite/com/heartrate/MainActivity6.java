package heart.wiite.com.heartrate;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity6 extends Activity implements SensorEventListener {
    TextView mHeartTextView;

    private SensorManager sensorManager2;
    public static float mems_data[] = new float[]{0, 0, 0, 0, 0};
    private int rate = 0;
    private int low = 0;
    Sensor sensor2;
    private int high = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        init();
    }

    public void init() {
        mHeartTextView = (TextView) this.findViewById(R.id.tvtitle);
        sensorManager2 = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor2 = sensorManager2
                .getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
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
                rate = (int) mems_data[0];
                low = rate & 0x000000FF;
                high = (rate >> 8) & 0x000000FF;
                mHeartTextView.setText(" 低血压：" + low + " 高血压：" + high);
                Log.d("qs_heartRate", " 低血压：" + low + " 高血压：" + high);
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
