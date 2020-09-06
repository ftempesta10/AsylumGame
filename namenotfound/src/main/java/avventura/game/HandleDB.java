package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class HandleDB {
	Connection conn;
	public HandleDB() throws SQLException, Exception {
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:~/saves");
		final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS saves (player VARCHAR(30) PRIMARY KEY, day DATE)";
		Statement stm = conn.createStatement();
		stm.executeUpdate(CREATE_TABLE);
		stm.close();
	}

	public void insertionTuple(String player, Object o) throws SQLException {
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
