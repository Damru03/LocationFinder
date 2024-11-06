package com.example.locationfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.locationfinder.LocationEntity;
import com.example.locationfinder.LocationDao;

import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAddress, editTextLatitude, editTextLongitude;
    private TextView textViewResult;
    private LocationDatabase database;
    private LocationDao locationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI components
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        textViewResult = findViewById(R.id.textViewResult);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonQuery = findViewById(R.id.buttonQuery);

        // Initialize the database
        database = Room.databaseBuilder(getApplicationContext(),
                LocationDatabase.class, "location_database").build();
        locationDao = database.locationDao();

        // Add Location
        buttonAdd.setOnClickListener(v -> Executors.newSingleThreadExecutor().execute(() -> {
            String address = editTextAddress.getText().toString();
            double latitude = Double.parseDouble(editTextLatitude.getText().toString());
            double longitude = Double.parseDouble(editTextLongitude.getText().toString());
            LocationEntity location = new LocationEntity(address, latitude, longitude);
            locationDao.insertLocation(location);
        }));

        // Update Location
        buttonUpdate.setOnClickListener(v -> Executors.newSingleThreadExecutor().execute(() -> {
            String address = editTextAddress.getText().toString();
            double latitude = Double.parseDouble(editTextLatitude.getText().toString());
            double longitude = Double.parseDouble(editTextLongitude.getText().toString());
            LocationEntity location = new LocationEntity(address, latitude, longitude);
            location.setId(1); // Example ID, use a real ID from a query in a real app
            locationDao.updateLocation(location);
        }));

        // Delete Location
        buttonDelete.setOnClickListener(v -> Executors.newSingleThreadExecutor().execute(() -> {
            String address = editTextAddress.getText().toString();
            LocationEntity location = locationDao.getLocationByAddress(address);
            if (location != null) {
                locationDao.deleteLocation(location);
            }
        }));

        // Query Location
        buttonQuery.setOnClickListener(v -> Executors.newSingleThreadExecutor().execute(() -> {
            String address = editTextAddress.getText().toString();
            LocationEntity location = locationDao.getLocationByAddress(address);
            runOnUiThread(() -> {
                if (location != null) {
                    textViewResult.setText("Latitude: " + location.getLatitude() +
                            ", Longitude: " + location.getLongitude());
                } else {
                    textViewResult.setText("Location not found");
                }
            });
        }));
    }
}
