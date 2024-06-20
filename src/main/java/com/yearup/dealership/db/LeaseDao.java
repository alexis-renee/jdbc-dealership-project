package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {
       String sql = "INSERT INTO lease_contracts (contract_id, vin, lease_start_date, lease_end_date, monthly_payment) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, leaseContract.getContractId());
            preparedStatement.setString(2, leaseContract.getVin());
            preparedStatement.setDate(3, leaseContract.getLeaseStart());
            preparedStatement.setDate(4, leaseContract.getLeaseEnd());
            preparedStatement.setDouble(5, leaseContract.getMonthlyPayment());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Lease contract added successfully.");
            } else {
                System.out.println("Failed to add lease contract.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding lease contract: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
