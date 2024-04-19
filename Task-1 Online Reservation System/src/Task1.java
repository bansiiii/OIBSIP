import java.sql.*;
import java.util.Scanner;
import java.util.Random;

public class Task1 {
    private static final int min = 1000;
    private static final int max = 9999;

    public static class User {
        private String username;
        private String password;

        Scanner sc = new Scanner(System.in);

        public User() {}

        public String getUsername() {
            System.out.println("Enter Username : ");
            username = sc.nextLine();
            return username;
        }

        public String getPassword() {
            System.out.println("Enter Password : ");
            password = sc.nextLine();
            return password;
        }
    }

    public static class PnrRecord {
        private int pnrNumber;
        private String passengerName;
        private String trainNumber;
        private String classType;
        private String journeyDate;
        private String from;
        private String to;

        Scanner sc = new Scanner(System.in);

        public int getPnrNumber() {
            Random random = new Random();
            pnrNumber = random.nextInt(max) + min;
            return pnrNumber;
        }

        public String getPassengerName() {
            System.out.println("Enter the passenger name : ");
            passengerName = sc.nextLine();
            return passengerName;
        }

        public String getTrainNumber() {
            System.out.println("Enter the train number : ");
            trainNumber = sc.nextLine();
            return trainNumber;
        }

        public String getClassType() {
            System.out.println("Enter the class type : ");
            classType = sc.nextLine();
            return classType;
        }

        public String getJourneyDate() {
            System.out.println("Enter the Journey date as 'YYYY-MM-DD' format");
            journeyDate = sc.nextLine();
            return journeyDate;
        }

        public String getFrom() {
            System.out.println("Enter the starting place : ");
            from = sc.nextLine();
            return from;
        }

        public String getTo() {
            System.out.println("Enter the destination place :  ");
            to = sc.nextLine();
            return to;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User u1 = new User();
        String username = u1.getUsername();
        String password = u1.getPassword();

        String url = "jdbc:mysql://localhost:3306/bansi";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("User Connection Granted.\n");
                while (true) {
                    String insertQuery = "insert into reservations values (?, ?, ?, ?, ?, ?, ?)";
                    String deleteQuery = "delete from reservations where pnr_number = ?";
                    String showQuery = "select * from reservations";
                    String updateQuery = "update reservations set passenger_name=?, train_number=?, class_type=?, journey_date=?, from_location=?, to_location=? where pnr_number=?";
                    String searchQuery = "select * from reservations where pnr_number=? or passenger_name=?";

                    System.out.println("Enter the choice : ");
                    System.out.println("1. Insert Record.");
                    System.out.println("2. Delete Record.");
                    System.out.println("3. Update Record.");
                    System.out.println("4. Search Record.");
                    System.out.println("5. Show All Records.");
                    System.out.println("6. Exit.");
                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            PnrRecord p1 = new PnrRecord();
                            int pnrNumber = p1.getPnrNumber();
                            String passengerName = p1.getPassengerName();
                            String trainNumber = p1.getTrainNumber();
                            String classType = p1.getClassType();
                            String journeyDate = p1.getJourneyDate();
                            String from = p1.getFrom();
                            String to = p1.getTo();

                            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                                preparedStatement.setInt(1, pnrNumber);
                                preparedStatement.setString(2, passengerName);
                                preparedStatement.setString(3, trainNumber);
                                preparedStatement.setString(4, classType);
                                preparedStatement.setString(5, journeyDate);
                                preparedStatement.setString(6, from);
                                preparedStatement.setString(7, to);

                                int rowsAffected = preparedStatement.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Record added successfully.");
                                } else {
                                    System.out.println("No records were added.");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }
                            break;

                        case 2:
                            System.out.println("Enter the PNR number to delete the record : ");
                            int pnrNum = sc.nextInt();
                            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                                preparedStatement.setInt(1, pnrNum);
                                int rowsAffected = preparedStatement.executeUpdate();

                                if (rowsAffected > 0) {
                                    System.out.println("Record deleted successfully.");
                                } else {
                                    System.out.println("No records were deleted.");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }
                            break;

                        case 3:
                            System.out.println("Enter the PNR number to update the record : ");
                            int pnr = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            System.out.println("Enter updated passenger name: ");
                            String newName = sc.nextLine();
                            System.out.println("Enter updated train number: ");
                            String newTrainNum = sc.nextLine();
                            System.out.println("Enter updated class type: ");
                            String newClass = sc.nextLine();
                            System.out.println("Enter updated journey date: ");
                            String newJourneyDate = sc.nextLine();
                            System.out.println("Enter updated starting place: ");
                            String newFrom = sc.nextLine();
                            System.out.println("Enter updated destination place: ");
                            String newTo = sc.nextLine();

                            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                                preparedStatement.setString(1, newName);
                                preparedStatement.setString(2, newTrainNum);
                                preparedStatement.setString(3, newClass);
                                preparedStatement.setString(4, newJourneyDate);
                                preparedStatement.setString(5, newFrom);
                                preparedStatement.setString(6, newTo);
                                preparedStatement.setInt(7, pnr);

                                int rowsAffected = preparedStatement.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Record updated successfully.");
                                } else {
                                    System.out.println("No records were updated.");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }
                            break;

                        case 4:
                            System.out.println("Enter the PNR number or passenger name to search for the record : ");
                            String searchInput = sc.next();
                            try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
                                preparedStatement.setString(1, searchInput);
                                preparedStatement.setString(2, searchInput);
                                ResultSet resultSet = preparedStatement.executeQuery();

                                if (resultSet.next()) {
                                    System.out.println("Record found:");
                                    System.out.println("PNR Number: " + resultSet.getString("pnr_number"));
                                    System.out.println("Passenger Name: " + resultSet.getString("passenger_name"));
                                    System.out.println("Train Number: " + resultSet.getString("train_number"));
                                    System.out.println("Class Type: " + resultSet.getString("class_type"));
                                    System.out.println("Journey Date: " + resultSet.getString("journey_date"));
                                    System.out.println("From Location: " + resultSet.getString("from_location"));
                                    System.out.println("To Location: " + resultSet.getString("to_location"));
                                } else {
                                    System.out.println("Record not found.");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }
                            break;

                        case 5:
                            try (PreparedStatement preparedStatement = connection.prepareStatement(showQuery);
                                 ResultSet resultSet = preparedStatement.executeQuery()) {
                                System.out.println("\nAll records printing.\n");
                                while (resultSet.next()) {
                                    pnrNumber = resultSet.getInt("pnr_number");
                                    passengerName = resultSet.getString("passenger_name");
                                    trainNumber = resultSet.getString("train_number");
                                    classType = resultSet.getString("class_type");
                                    journeyDate = resultSet.getString("journey_date");
                                    String fromLocation = resultSet.getString("from_location");
                                    String toLocation = resultSet.getString("to_location");

                                    System.out.println("PNR Number: " + pnrNumber);
                                    System.out.println("Passenger Name: " + passengerName);
                                    System.out.println("Train Number: " + trainNumber);
                                    System.out.println("Class Type: " + classType);
                                    System.out.println("Journey Date: " + journeyDate);
                                    System.out.println("From Location: " + fromLocation);
                                    System.out.println("To Location: " + toLocation);
                                    System.out.println("--------------");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }
                            break;

                        case 6:
                            System.out.println("Exiting the program.\n");
                            System.exit(0);
                            break;

                        default:
                            System.out.println("Invalid Choice Entered.\n");
                    }
                }
            } catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        }

        sc.close();
    }
}
