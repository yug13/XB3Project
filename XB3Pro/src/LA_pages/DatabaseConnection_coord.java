package LA_pages;
import java.sql.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import geocoder.*;

public class DatabaseConnection_coord {

	public static Connection Initialize() throws SQLException {
		
		String url = "jdbc:mysql://localhost:3306/test";
		String username = "root";
		String password = "120800";
		
		Connection connection = DriverManager.getConnection(url, username, password);
		if (connection != null)
			System.out.println("Connected");
		return connection;
	}
	
	public static business_coord[] IndustryFilter(Connection con, String search) throws SQLException {
		Statement stmt1 = null;
		Statement stmt2 = null;
		String query = "SELECT * from test.testlist WHERE Industry_Tag LIKE '%" + search + "%'";
		String count_query = "SELECT COUNT(*) from test.testlist WHERE Industry_Tag LIKE '%" + search + "%'";
		
		stmt2 = con.createStatement();
		ResultSet nRows = stmt2.executeQuery(count_query);
		nRows.next();
		//System.out.println(nRows.getInt("COUNT(*)"));
		business_coord[] BusinessArray = new business_coord[nRows.getInt("COUNT(*)")];
		
		stmt1 = con.createStatement();
		ResultSet rs = stmt1.executeQuery(query);
		int count = 0;
		while(rs.next()) {
			String name = rs.getString("doing_business_as");
			String number = rs.getString("Phone1");
			String location = rs.getString("Business_Address");
			String city = rs.getString("City");
			String coord = rs.getString("Longitude");
			/*Pattern p = Pattern.compile("([0-9a-zA-Z\\s]+)(#[0-9]+)?");
			Matcher m = p.matcher(location);
			//location = location.replaceAll(".\\d", "");*/
			String tag = rs.getString("Industry_Tag");
			Map<String, Double> coords;
			//m.find();
			double lat = Double.parseDouble((coord.split(","))[0].substring(1));
			double lon = Double.parseDouble((coord.split(","))[1].substring(0,((coord.split(",")[1]).length() - 1)));
			
			
			//coords = OpenStreetMapUtils.getInstance().getCoordinates(m[0]);
	        
			business_coord temp = new business_coord(name, number, location, tag, lat, lon);
			BusinessArray[count] = temp;
			count++;
		}
		
		return BusinessArray;
	}
}
