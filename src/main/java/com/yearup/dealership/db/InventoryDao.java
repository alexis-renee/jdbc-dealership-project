package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
       String sql = "INSERT INTO inventory (vin, dealership_id) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vin);
            preparedStatement.setInt(2, dealershipId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Vehicle added to inventory successfully.");
            } else {
                System.out.println("Failed to add vehicle to inventory.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding vehicle to inventory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeVehicleFromInventory(String vin) {
        String sql = "DELETE FROM inventory WHERE = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vin);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Vehicle deleted from inventory VIN does not exist.");
            } else {
                System.out.println("Failed to delete vehicle to inventory.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting vehicle from inventory: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
