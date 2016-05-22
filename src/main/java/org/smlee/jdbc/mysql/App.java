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
		List<Person> personList = template.query(sql, new RowMapper<Person>() {
			public Person mapRow(ResultSet rs, int arg1) throws SQLException {
				Person p = new Person();
				p.setPersonID( rs.getString("PersonID"));
				p.setLastName( rs.getString("LastName"));
				p.setFirstName( rs.getString("FirstName"));
				p.setAddress( rs.getString("Address"));
				p.setCity( rs.getString("City"));
				return p;
			}
		});

		Consumer<? super Person> action = new Consumer<Person>() {

			public void accept(Person p) {
				// TODO Auto-generated method stub
				System.out.println( p.getPersonID() );
				System.out.println( p.getLastName() );
				System.out.println( p.getFirstName() );
				System.out.println( p.getAddress() );
				System.out.println( p.getCity() );
				
			}
		};
		personList.forEach(action);
		
		
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");

		App app = new App();
		app.run();
	}

}
