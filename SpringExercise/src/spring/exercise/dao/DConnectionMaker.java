package spring.exercise.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		
		//D ���� �������� ������� Connection�� �����ϴ� �ڵ�
		
		return null;
	}
	
	
}
