package com.example.locationfinder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.locationfinder.LocationEntity;  // Adjust to your actual package name

import java.util.List;

@Dao
public interface LocationDao {

    // Method to insert a location
    @Insert
    void insertLocation(LocationEntity location);

    // Method to update a location
    @Update
    void updateLocation(LocationEntity location);

    // Method to delete a location
    @Delete
    void deleteLocation(LocationEntity location);

    // Method to retrieve a location by address
    @Query("SELECT * FROM location_table WHERE address = :address LIMIT 1")
    LocationEntity getLocationByAddress(String address);

    // Method to retrieve all locations (if needed)
    @Query("SELECT * FROM location_table")
    List<LocationEntity> getAllLocations();
}
