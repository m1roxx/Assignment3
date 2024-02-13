import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Launch {

    public static void main(String[] args) {
        // РАБОТА С БАЗОЙ ДАННЫХ POSTGRESQL ЧЕРЕЗ JDBC
        try {
            // Адрес нашей базы данных "tsn_pg_demo" на локальном компьютере (localhost)
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";

            // Создание свойств соединения с базой данных
            Properties authorization = new Properties();
            authorization.put("user", "postgres"); // Зададим имя пользователя БД
            authorization.put("password", "1234"); // Зададим пароль доступа в БД

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM smartphones");

            // Создание объекта для чтения ввода пользователя
            Scanner scanner = new Scanner(System.in);

            // Создание объекта SmartphoneManager для управления смартфонами в базе данных
            SmartphoneManager manager = new SmartphoneManager(connection);

            System.out.println("Welcome to Smartphone Manager!");

            //Отображение меню
            while (true) {
                System.out.println("Menu:");
                System.out.println("1. Add Smartphone");
                System.out.println("2. Show All Smartphones");
                System.out.println("3. Update Smartphone");
                System.out.println("4. Delete Smartphone");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    //Добавление нового смартфона
                    case 1:
                        System.out.print("Enter smartphone ID: ");
                        int newId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter smartphone brand: ");
                        String newBrand = scanner.nextLine();
                        System.out.print("Enter smartphone model: ");
                        String newModel = scanner.nextLine();
                        System.out.print("Enter smartphone price: ");
                        int newPrice = scanner.nextInt();
                        System.out.print("Enter smartphone ROM size: ");
                        int newRom = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter smartphone CPU: ");
                        String newCpu = scanner.nextLine();

                        try {
                            manager.createSmartphone(newId, newBrand, newModel, newPrice, newRom, newCpu);
                            System.out.println("Smartphone added successfully!");
                        } catch (SQLException e) {
                            System.out.println("Error adding smartphone: " + e.getMessage());
                        }
                        break;
                    //Отображение всех смартфонов
                    case 2:
                        try {
                            manager.showAllSmartphones();
                        } catch (SQLException e) {
                            System.out.println("Error displaying smartphones: " + e.getMessage());
                        }
                        break;
                    //Обновление информации о смартфоне
                    case 3:
                        System.out.print("Enter smartphone ID: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new smartphone brand: ");
                        String updateBrand = scanner.nextLine();
                        System.out.print("Enter new smartphone model: ");
                        String updateModel = scanner.nextLine();
                        System.out.print("Enter new smartphone price: ");
                        int updatePrice = scanner.nextInt();
                        System.out.print("Enter new smartphone ROM size: ");
                        int updateRom = scanner.nextInt();
                        scanner.nextLine(); 
                        System.out.print("Enter new smartphone CPU: ");
                        String updateCpu = scanner.nextLine();

                        try {
                            manager.updateSmartphone(updateId, updateBrand, updateModel, updatePrice, updateRom, updateCpu);
                            System.out.println("Smartphone updated successfully!");
                        } catch (SQLException e) {
                            System.out.println("Error updating smartphone: " + e.getMessage());
                        }
                        break;
                    //Удаление смартфона
                    case 4:
                        System.out.print("Enter smartphone ID to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            manager.deleteSmartphone(deleteId);
                            System.out.println("Smartphone deleted successfully!");
                        } catch (SQLException e) {
                            System.out.println("Error deleting smartphone: " + e.getMessage());
                        }
                        break;
                    //Завершение работы программы
                    case 5:
                        System.out.println("Exiting program.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }
}
