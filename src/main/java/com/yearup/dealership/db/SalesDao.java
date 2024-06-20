package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesDao {
    private DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addSalesContract(SalesContract salesContract) {
        String sql = "INSERT INTO sales_contracts (contract_id, vin, sales_date, price) VALUES (?, ?, ?, ?)";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, salesContract.getContractId());
                preparedStatement.setString(2, salesContract.getVin());
                preparedStatement.setDate(3, salesContract.getSaleDate());
                preparedStatement.setDouble(4, salesContract.getPrice());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Sales contract successfully added.");
                } else {
                    System.out.println("Failed to add sales contract.");
                }

            } catch (SQLException e) {
                System.err.println("Error adding sales contract: " + e.getMessage());
                e.printStackTrace();
            }
        
}
