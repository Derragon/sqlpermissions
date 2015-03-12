package sqlpermissions.objects;

import java.sql.*;

public class SQLInterface {	
	
	static String server, username, password;
	private Connection conn = null;
	private Statement stat = null;
	
	public SQLInterface (
			String server,
			String username,
			String password) {
		
		SQLInterface.server = server;
		SQLInterface.username = username;
		SQLInterface.password = password;
	}
	
	public boolean openConnection () throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(
				SQLInterface.server,
				SQLInterface.username,
				SQLInterface.password);
		stat = conn.createStatement();
		if (conn != null && stat != null ){
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * A connection has been opened that will be closed at plug-in unload, allowing many queries to be
	 * executed without opening another connection. This should help boost performance.
	 * A local database can be queried 100 times in just over 200ms (averaging ~2ms per query.)
	 */
	public ResultSet query (String query) throws SQLException, ClassNotFoundException {
		return stat.executeQuery(query);
	}
	
	public boolean checkConnection (){
		if (conn != null && stat != null ){
			return true;
		}
		return false;
	}
}
