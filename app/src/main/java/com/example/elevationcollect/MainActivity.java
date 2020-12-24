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

import java.util.List;

public class MainActivity extends AppCompatActivity {
        private TextView textView;
        private SensorManager mSensorManager;
//        private List<Sensor> deviceSensors;
        private Sensor gyroscopeSensor;
        private SensorEventListener gyroscopeEventListener;
        private float fastest_x;
        private float fastest_y;
        private float fastest_z;
        private float fastest_neg_x;
        private float fastest_neg_y;
        private float fastest_neg_z;

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

                textView = findViewById(R.id.textViewSensors);
                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                gyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                fastest_x = 0;
                fastest_y = 0;
                fastest_z = 0;
                fastest_neg_x = 0;
                fastest_neg_y = 0;
                fastest_neg_z = 0;
                if (gyroscopeSensor == null){
                        Toast.makeText(this, "This device has no Gyroscope", Toast.LENGTH_SHORT).show();
                        finish();
                }
                gyroscopeEventListener = new SensorEventListener() {
                        @Override
                        public void onSensorChanged(SensorEvent event) {
                                if (event.values[0] > fastest_x){
                                        fastest_x = event.values[0];
                                }

                                if (event.values[1] > fastest_y){
                                        fastest_y = event.values[1];
                                }
                                if (event.values[2] > fastest_z){
                                        fastest_z = event.values[2];
                                }
                                if (event.values[0] < fastest_neg_x){
                                        fastest_neg_x = event.values[0];
                                }
                                if (event.values[1] < fastest_neg_y){
                                        fastest_neg_y = event.values[1];
                                }
                                if (event.values[2] < fastest_neg_z){
                                        fastest_neg_z = event.values[2];
                                }

                                textView.setText("Fastest X: " + fastest_x + "\n" + "Slowest X:" + fastest_neg_x + "\n" +
                                  "Fastest Y: "+ fastest_y + "\n" + "Slowest Y:" + fastest_neg_y + "\n" +
                                  "Fastest Z: " + fastest_z + "\n" + "Slowest Z:" + fastest_neg_z);
                        
                        }

                        @Override
                        public void onAccuracyChanged(Sensor sensor, int accuracy) {

                        }
                };
//                deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//                printSensors();
        }

//        protected void printSensors(){
//                textView.setText(deviceSensors.size()+"");
//                for(Sensor sensor : deviceSensors){
//                        textView.setText(textView.getText()+"\n"+sensor.getName());
//                }
//        }

        @Override
        protected void onResume() {
                super.onResume();
                mSensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, mSensorManager.SENSOR_DELAY_FASTEST);
        }

        @Override
        protected void onPause() {
                super.onPause();
                mSensorManager.unregisterListener(gyroscopeEventListener);
        }


}