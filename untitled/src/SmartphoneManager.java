import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SmartphoneManager {
    private Connection connection;

    public SmartphoneManager(Connection connection) {
        this.connection = connection;
    }
    //Метод для добавления смартфона в базу данных
    public void createSmartphone(int id, String brand, String model, int price, int rom, String cpu) throws SQLException {
        String query = "INSERT INTO smartphones (id, brand, model, price, rom, cpu) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, brand);
            statement.setString(3, model);
            statement.setInt(4, price);
            statement.setInt(5, rom);
            statement.setString(6, cpu);
            statement.executeUpdate();
        }
    }
    // Метод для чтения и вывода всех смартфонов из базы данных
    public void showAllSmartphones() throws SQLException {
        System.out.println("Here are your smartphones:");
        String query = "SELECT * FROM smartphones ORDER BY id ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int price = resultSet.getInt("price");
                int rom = resultSet.getInt("rom");
                String cpu = resultSet.getString("cpu");
                System.out.println("ID: " + id + ", Brand: " + brand + ", Model: " + model + ", Price: $" + price + ", ROM: " + rom + "GB, CPU: " + cpu);
            }
        }
    }
    // Метод для обновления информации о смартфоне
    public void updateSmartphone(int id, String brand, String model, int price, int rom, String cpu) throws SQLException {
        String query = "UPDATE smartphones SET brand = ?, model = ?, price = ?, rom = ?, cpu = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.setInt(3, price);
            statement.setInt(4, rom);
            statement.setString(5, cpu);
            statement.setInt(6, id);
            statement.executeUpdate();
        }
    }
    // Метод для удаления смартфона по его ID
    public void deleteSmartphone(int id) throws SQLException {
        String query = "DELETE FROM smartphones WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
