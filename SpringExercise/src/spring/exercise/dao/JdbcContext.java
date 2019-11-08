package spring.exercise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	//DataSource Ÿ�� ���� DI ���� �� �ְ� �غ��صд�.
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//jdbcContext Ŭ���� ������ �Ű����Ƿ� �̸��� �׿� �°� �����ߴ�.
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
