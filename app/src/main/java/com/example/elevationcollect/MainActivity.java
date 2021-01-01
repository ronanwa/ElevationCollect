package com.example.elevationcollect;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.FileWriter;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
        private TextView textViewAltitude;
        private TextView textViewAccelerator;
        private SensorManager mSensorManager;
        private Sensor pressureSensor;
        private Sensor acceleratorSensor;
        private SensorEventListener pressureEventListener;
        private SensorEventListener acceleratorEventListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                BottomNavigationView navView = findViewById(R.id.nav_view);
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                  R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                  .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(navView, navController);

                textViewAltitude = findViewById(R.id.textViewAltitude);
                textViewAltitude.setText("0");
                textViewAccelerator = findViewById(R.id.textViewAccelerator);
                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                pressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                acceleratorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                if (pressureSensor == null) {
                        Toast.makeText(this, "This device has no pressure sensor", Toast.LENGTH_SHORT).show();
                        finish();
                }
                pressureEventListener = new SensorEventListener() {
                        DecimalFormat decimalFormat = new DecimalFormat("0.0");
                        @Override
                        public void onSensorChanged(SensorEvent event) {
                                if (Math.abs(Float.parseFloat(textViewAltitude.getText().toString()) - SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, event.values[0])) > 0.3  ){
                                        textViewAltitude.setText(decimalFormat.format(SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, event.values[0])) + "");
                                }
                        }
                        @Override
                        public void onAccuracyChanged(Sensor sensor, int accuracy) {

                        }
                };

                if (acceleratorSensor == null) {
                        Toast.makeText(this, "This device has no accelerator sensor", Toast.LENGTH_SHORT).show();
                        finish();
                }
                acceleratorEventListener = new SensorEventListener() {
                        @Override
                        public void onSensorChanged(SensorEvent event) {
                                textViewAccelerator.setText("x-axis: " + event.values[0] + "\ny-axis: " + event.values[1] + "\nz-axis: " + event.values[0]);
                        }

                        @Override
                        public void onAccuracyChanged(Sensor sensor, int accuracy) {

                        }
                };
        }


        @Override
        protected void onResume() {
                super.onResume();
                mSensorManager.registerListener(pressureEventListener, pressureSensor, mSensorManager.SENSOR_DELAY_FASTEST);
                mSensorManager.registerListener(acceleratorEventListener, acceleratorSensor, mSensorManager.SENSOR_DELAY_FASTEST);
        }

        @Override
        protected void onPause() {
                super.onPause();
                mSensorManager.unregisterListener(pressureEventListener);
                mSensorManager.unregisterListener(acceleratorEventListener);
        }


//        private List<Sensor> deviceSensors;
//                deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//                printSensors();
//        protected void printSensors(){
//                textView.setText(deviceSensors.size()+"");
//                for(Sensor sensor : deviceSensors){
//                        textView.setText(textView.getText()+"\n"+sensor.getName());
//                }
//        }

        protected void recordAltitude(Float meters){

        }
}