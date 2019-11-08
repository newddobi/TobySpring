package spring.exercise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	//DataSource 타입 빈을 DI 받을 수 있게 준비해둔다.
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//jdbcContext 클래스 안으로 옮겼으므로 이름도 그에 맞게 수정했다.
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = this.dataSource.getConnection();
			
			ps = stmt.makePreparedStatement(c);
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null) { try { ps.close(); } catch (Exception e) { } }
			if (c != null) { try { c.close(); } catch (Exception e) { } } }
	}

	
}
