package ru.clevertec.reflection.task.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.reflection.task.aspect.annotation.DeleteClient;
import ru.clevertec.reflection.task.aspect.annotation.GetClient;
import ru.clevertec.reflection.task.aspect.annotation.SaveClient;
import ru.clevertec.reflection.task.aspect.annotation.UpdateClient;
import ru.clevertec.reflection.task.dao.postgresql.JDBCPostgreSQLConnection;
import ru.clevertec.reflection.task.entity.dto.ClientDto;
import ru.clevertec.reflection.task.entity.model.Client;
import ru.clevertec.reflection.task.repository.ClientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {

    private static final Logger logger = LoggerFactory.getLogger(ClientRepositoryImpl.class);

    @Override
    @SaveClient
    public Client save(Client client) throws Exception {
        Connection connection = null;
        Integer clientId = 0;
        String insertTableSQL = "INSERT INTO clients"
                + "(first_name, last_name, email, telephone, birthday, registration_date) " + "VALUES "
                + "('" + client.getFirstName() + "', "
                + "'" + client.getLastName() + "', "
                + "'" + client.getEmail() + "', "
                + "'" + client.getTelephone() + "', "
                + "'" + client.getBirthday() + "', "
                + "'" + client.getRegistrationDate() + "') "
                + "RETURNING id";
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                clientId = Integer.parseInt(rs.getString("id"));
                client.setId(clientId.longValue());
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return client;
    }

    @Override
    public List<Client> getAll() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatterForLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Connection connection = null;
        List<Client> clients = new ArrayList<>();
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "SELECT * from clients";
            PreparedStatement pstmt = connection.prepareStatement(selectTableSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long clientId = Long.parseLong(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String birthdayString = rs.getString("birthday");
                LocalDate birthday = LocalDate.parse(birthdayString, formatterForLocalDate);
                String registrationTimeString = rs.getString("registration_date");
                LocalDateTime registrationDate = LocalDateTime.parse(registrationTimeString, formatter);
                Client client = new Client(clientId, firstName, lastName, email, telephone, birthday, registrationDate);
                clients.add(client);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return clients;
    }

    @Override
    @GetClient
    public Client getById(Long id) throws Exception {
        logger.info("Get client from DB");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatterForLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Connection connection = null;
        Client client = new Client();
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            String selectTableSQL = "SELECT * from clients where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long clientId = Long.parseLong(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String birthdayString = rs.getString("birthday");
                LocalDate birthday = LocalDate.parse(birthdayString, formatterForLocalDate);
                String registrationTimeString = rs.getString("registration_date");
                LocalDateTime registrationDate = LocalDateTime.parse(registrationTimeString, formatter);
                client.setId(clientId);
                client.setFirstName(firstName);
                client.setLastName(lastName);
                client.setEmail(email);
                client.setTelephone(telephone);
                client.setBirthday(birthday);
                client.setRegistrationDate(registrationDate);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return client;
    }

    @Override
    @DeleteClient
    public void delete(Long id) {
        String deleteTableSQL = "DELETE FROM clients WHERE id = " + id;
        executeStatement(deleteTableSQL);
    }

    @Override
    @UpdateClient
    public void update(Long id, ClientDto client) {
        String updateClientSQL = "UPDATE clients SET "
                + "first_name =" + "'" + client.getFirstName() + "',"
                + "last_name =" + "'" + client.getLastName() + "',"
                + "email =" + "'" + client.getEmail() + "',"
                + "telephone =" + "'" + client.getTelephone() + "',"
                + "birthday =" + "'" + client.getBirthday() + "',"
                + "registration_date =" + "'" + client.getRegistrationDate() + "'"
                + "WHERE id = " + id;
        executeStatement(updateClientSQL);
    }

    public void executeStatement(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = JDBCPostgreSQLConnection.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}