package spring.exercise.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장 기능 지정
@ContextConfiguration(locations = "/test-applicationContext.xml") //테스트 컨텍스트가 자동으
public class UserDaoTest {
	
	private UserDao dao; //@Autowired가 없다
	
	private User user1;
	private User user2;
	private User user3;
	
	
	public static void main(String[] args) {
		JUnitCore.main("spring.exercise.dao.UserDaoTest");
	}
	
	@Before
	public void setUp() {
		
		this.user1 = new User("gyumee", "박성철", "springno1");
		this.user2 = new User("leegw700", "이길원", "springno2");
		this.user3 = new User("bumjin", "박범진", "springno3");
		
		dao = new UserDao();
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb?characterEncoding=UTF-8&serverTimezone=UTC", "root", "system", true);
		
		dao.setDataSource(dataSource);
		
	}

	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		
	}
	
	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException {

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
		
	}
	
	@Test
	public void selectAll() throws SQLException, ClassNotFoundException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		
		assertThat(dao.selectAll().size(), is(3));
		
	}
	
//	@Test
//	public void remove() throws SQLException, ClassNotFoundException {
//		
//		dao.deleteAll();
//		assertThat(dao.getCount(), is(0));
//		
//		dao.add(user1);
//		assertThat(dao.getCount(), is(1));
//		
//		dao.remove(user1);
//		assertThat(dao.getCount(), is(0));
//		
//	}
}
