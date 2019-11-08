
package spring.exercise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	DataSource dataSource;
	private JdbcContext jdbcContext;
	
	public void setDataSource(DataSource connectionMaker) {
		this.dataSource = connectionMaker;
	}

	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	public void add(final User user) throws ClassNotFoundException, SQLException {
		this.jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
					ps.setString(1, user.getId());
					ps.setString(2, user.getName());
					ps.setString(3, user.getPassword());
					
					return ps;
				}
			}
		);
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		ps.close();
		c.close();
		
		if (user == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return user;
	}
	
	public void deleteAll() throws SQLException {
		this.jdbcContext.workWithStatementStrategy(
				new StatementStrategy() {
					public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
						PreparedStatement ps = c.prepareStatement("delete from users");
						return ps;
					}
				}
		);
	}
	
	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps =  null;
		ResultSet rs = null;
		
		try {
			c = dataSource.getConnection();
			
			ps = c.prepareStatement("select count(*) from users");
			
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) { 
			throw e; 
		} finally { 
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e2) { } }
			if (c != null) { try { c.close(); } catch (SQLException e) { } } 
		}
	}
	
	public void remove(final User user) throws SQLException {
		this.jdbcContext.workWithStatementStrategy(
				new StatementStrategy() {
					public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
						PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE id = (?)");
						ps.setString(1, user.getId());
						
						return ps;
					}
				}
		);
	}
	
	
	public List<User> selectAll() throws SQLException {
		Connection c = null;
		PreparedStatement ps =  null;
		ResultSet rs = null;
		List<User> listUser = new ArrayList<User>();
		
		try {
			c = dataSource.getConnection();
			
			ps = c.prepareStatement("SELECT * FROM users");
			
			rs = ps.executeQuery();
			
			User user = null;
			while(rs.next()) {
				user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				listUser.add(user);
			}
			
			return listUser;
			
		} catch (SQLException e) { 
			throw e; 
		} finally { 
			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
			if (c != null) { try { c.close(); } catch (SQLException e) { } } 
		}
	}
}
