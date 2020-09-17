package heart.wiite.com.heartrate;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends Activity {
	TextView mHeartTextView;
	SensorManager mSensorManager;
	Sensor mStepCount;
	int mCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		init();
	}

	public void init() {
		mHeartTextView = (TextView) this.findViewById(R.id.tvtitle);

		mSensorManager = ((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		mStepCount = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		mSensorManager.registerListener(sensorLisener, mStepCount, 0);

	}

	private SensorEventListener sensorLisener = new SensorEventListener() {

		public void onAccuracyChanged(Sensor paramAnonymousSensor,
				int paramAnonymousInt) {

		}

		public void onSensorChanged(SensorEvent paramAnonymousSensorEvent) {
			mCount = (int) paramAnonymousSensorEvent.values[0];
			mHeartTextView.setText("步数："+mCount);
			
		}
	};

	protected void unRegisterSensor() {
		if (mSensorManager != null) {
			mSensorManager.unregisterListener(sensorLisener);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterSensor();

	}
	
	
}
