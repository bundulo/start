package com.codecool.database;


import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String USER;
    private final String PASS;
    private final Connection connection;

    public RadioCharts(String DB_URL, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
        this.connection = getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }
    public String getMostPlayedSong(){
        try(this.connection) {
            String query = "SELECT song, SUM(times_aired) as aired FROM music_broadcast GROUP BY artist ORDER BY aired DESC LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }
    public String getMostActiveArtist(){
        try(this.connection) {
            String query = "SELECT artist, COUNT(DISTINCT song) as songs FROM music_broadcast GROUP BY artist ORDER BY songs DESC LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

}
