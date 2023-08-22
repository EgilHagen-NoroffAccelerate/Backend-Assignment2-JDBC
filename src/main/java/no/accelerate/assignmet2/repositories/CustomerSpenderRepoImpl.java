package no.accelerate.assignmet2.repositories;

import no.accelerate.assignmet2.dao.models.CustomerCountry;
import no.accelerate.assignmet2.dao.models.CustomerSpender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CustomerSpenderRepoImpl implements CustomerSpenderRepo{
    private final String url;
    private final String username;
    private final String password;

    public CustomerSpenderRepoImpl(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<CustomerSpender> getAll() {
        return null;
    }

    @Override
    public CustomerSpender getById(int id) {
        return null;
    }

    @Override
    public List<CustomerSpender> getByName(String name) {
        return null;
    }

    @Override
    public List<CustomerSpender> getLimit(int i, int j) {
        return null;
    }


    @Override
    public CustomerSpender getHighestSpender() {
        CustomerSpender customerSpender = null;
        String sql =
                "SELECT c.customer_id, c.first_name, c.last_name, " +
                        "MAX(i.total) AS highest_total " +
                        "FROM customer AS c " +
                        "JOIN invoice AS i ON c.customer_id = i.customer_id " +
                        "GROUP BY c.customer_id, c.first_name, c.last_name " +
                        "ORDER BY highest_total DESC LIMIT 1";
                try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                customerSpender = new CustomerSpender(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getDouble("highest_total")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Highest spender");
        return customerSpender;
    }

}
