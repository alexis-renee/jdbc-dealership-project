package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (vin, make, model, year, color, odometer, price, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setString(5, vehicle.getColor());
            preparedStatement.setInt(6, vehicle.getOdometer());
            preparedStatement.setDouble(7, vehicle.getPrice());
            preparedStatement.setString(8, vehicle.getVehicleType());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeVehicle(String VIN) {
        String sql = "DELETE FROM vehicle WHERE vin = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, VIN);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting the vehicle from inventory: " + e.getMessage());
            e.printStackTrace();
        }


        public List<Vehicle> searchByPriceRange ( double minPrice, double maxPrice){
            List<Vehicle> vehicles = new ArrayList<>();
            String sql = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDouble(1, minPrice);
                stmt.setDouble(2, maxPrice);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Vehicle vehicle = new Vehicle();
                        vehicle.setVin(rs.getString("vin"));
                        vehicle.setMake(rs.getString("make"));
                        vehicle.setModel(rs.getString("model"));
                        vehicle.setYear(rs.getInt("year"));
                        vehicle.setColor(rs.getString("color"));
                        vehicle.setVehicleType(rs.getString("type"));
                        vehicle.setOdometer(rs.getInt("odometer"));
                        vehicle.setPrice(rs.getDouble("price"));
                        vehicles.add(vehicle);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        public List<Vehicle> searchByMakeModel (String make, String model){
            List<Vehicle> vehicles = new ArrayList<>();

            String query = "SELECT * FROM vehicles WHERE make = ? AND model = ?";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
            ) {
                statement.setString(1, make);
                statement.setString(2, model);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String vin = resultSet.getString("vin");
                        int year = resultSet.getInt("year");
                        boolean sold = resultSet.getBoolean("sold");
                        String color = resultSet.getString("color");
                        int odometer = resultSet.getInt("odometer");
                        double price = resultSet.getDouble("price");
                        String type = resultSet.getString("type");

                        Vehicle vehicle = new Vehicle(vin, year, sold, color, type, odometer, price);
                        vehicles.add(vehicle);


                    }
                }
            } catch (SQLException e) {
                System.err.println("Error executing SQL query: " + e.getMessage());
            }

        }
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, minYear);
            stmt.setInt(2, maxYear);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = new Vehicle(
                            rs.getString("vin"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getBoolean("isSold"),
                            rs.getString("color"),
                            rs.getString("type"),
                            rs.getInt("odometer"),
                            rs.getDouble("price")
                    );
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return vehicles;
    }

    public List<Vehicle> searchByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE color = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, color);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String vin = rs.getString("vin");
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                boolean sold = rs.getBoolean("sold");
                String vehicleColor = rs.getString("color");
                String type = rs.getString("type");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");

                Vehicle vehicle = new Vehicle(vin, make, model, year, sold, vehicleColor, type, odometer, price);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider proper logging
        }

        return vehicles;

    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {

            List<Vehicle> vehicles = new ArrayList<>();
            String sql = "SELECT vin, make, model, year, color, type, mileage, price FROM vehicles WHERE mileage BETWEEN ? AND ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, minMileage);
                ps.setInt(2, maxMileage);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String vin = rs.getString("vin");
                        String make = rs.getString("make");
                        String model = rs.getString("model");
                        int year = rs.getInt("year");
                        String color = rs.getString("color");
                        String type = rs.getString("type");
                        int mileage = rs.getInt("mileage");
                        double price = rs.getDouble("price");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
               
            }

            return vehicles;
        }


        public List<Vehicle> searchByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE type = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String vin = rs.getString("vin");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    int year = rs.getInt("year");
                    boolean sold = rs.getBoolean("sold");
                    String color = rs.getString("color");
                    int odometer = rs.getInt("odometer");
                    double price = rs.getDouble("price");

                    Vehicle vehicle = new Vehicle(vin, make, model, year, sold, color, type, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
           throw as a custom exception, etc.)
        }

        return vehicles;
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}

