package com.example.elevationcollect;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
        private List<Sensor> deviceSensors;

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
                deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
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

//                // for the system's orientation sensor registered listeners
//                mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
        }

        @Override
        protected void onPause() {
                super.onPause();

//                // to stop the listener and save battery
//                mSensorManager.unregisterListener(this);
        }


}