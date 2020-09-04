package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class HandleDB {
	
	public HandleDB() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:h2:/home/user/test/db");
	}
	
	public void insertionTuple(String player, Object o) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:h2:/home/user/test/db");
		PreparedStatement pstm = conn.prepareStatement(
				"INSERT INTO db"
				+ "VALUES (?, ?, ?)");
		pstm.setString(1, player);
		pstm.setObject(2, o);
		pstm.setString(3, LocalDate.now().toString());
		
		pstm.executeUpdate();
		pstm.close();	
	}
	
}
