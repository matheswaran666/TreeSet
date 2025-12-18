package TreeSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
public class Mysql {
	Connection con = null;
	
	public Mysql() throws SQLException{
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/scoreBoard","root","Krish6@");
	}
	
	public void retreiveData(MyTreeSet tree) {
		try {
			String query = "Select * from score_board";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int score = rs.getInt("Score");
				int jerseyNumber = rs.getInt("Jersey_number");
				String playerName = rs.getString("score_board");
				tree.add(new Player(playerName,score,jerseyNumber));
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
		
	public void storeData(String name ,int score) {
		try {
			String query = "INSERT INTO score_board (Player_name, Score) VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, score);
			ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
		}
	}

	public void deleteData(String name ,int score) {
		try {
			String query = "DELETE FROM score_board WHERE Player_name = ? AND Score = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, score);
			ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
		}
	}


}
