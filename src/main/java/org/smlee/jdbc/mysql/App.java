package org.smlee.jdbc.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.zaxxer.hikari.HikariDataSource;

public class App {

	private JdbcTemplate template = null;

	public App() {

	}

	private DataSource getDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");

		dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/mysql");
		dataSource.setUsername("root");
		dataSource.setPassword("1q2w3e4r");
		return dataSource;
	}

	public void run() {
		template = new JdbcTemplate(getDataSource());

		String sql = "select PersonID , LastName, FirstName, Address, City from Persons";

		System.out.println("app : " + this);

		// lambda style
		List<Person> personList = template.query(sql, (rs, arg1) -> {
			System.out.println("template lambda : " + this);

			Person p = new Person();
			p.setPersonID(rs.getString("PersonID"));
			p.setLastName(rs.getString("LastName"));
			p.setFirstName(rs.getString("FirstName"));
			p.setAddress(rs.getString("Address"));
			p.setCity(rs.getString("City"));
			return p;
		});

		// lambda style
		personList.forEach(p -> {
			System.out.println("list lambda : " + this);
			System.out.println(p.getPersonID());
			System.out.println(p.getLastName());
			System.out.println(p.getFirstName());
			System.out.println(p.getAddress());
			System.out.println(p.getCity());
		});
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");

		App app = new App();
		app.run();
	}

}
