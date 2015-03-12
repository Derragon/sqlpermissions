package sqlpermissions.objects;

import sqlpermissions.objects.SQLInterface;

import java.sql.*;

public class PermissionHandler {
	
	String server, username, password;
	SQLInterface si;
	
	/*
	 * The PermissionHandler creates an SQLInterface, which is what keeps an active connection.
	 * By separating these two functions, I am able to make the plug-ins more versatile, and the code
	 * more reusable for different plug-ins (for example, the upcoming region plugins and town plugins.
	 */
	
	public PermissionHandler (
			String server,
			String username,
			String password) {
		si = new SQLInterface(server, username, password);
		try {
			if (si.openConnection()){
				System.out.println("Connection opened succesfully.");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String getID (String user){ // Self-explanatory; get the ID of the use matching it to their name.
		String id = "";
		ResultSet sr = null;
		try {
			sr = si.query("SELECT id FROM players WHERE username=\'" + user + "\'");
			if (sr.next()){
				id = sr.getString("id");
			} else {
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	/*
	 * IDs are a much faster way to get data, as it is much faster to process integer values than
	 * it is to process String (and more specifically Unicode) values. As an example,
	 * you could grab the integer of the user and then match it to other user IDs in different
	 * tables and/or databases.
	 */
	public String getNameByID (String id){
		String name = "";
		ResultSet sr = null;
		try {
			sr = si.query("SELECT username FROM players WHERE id=\'" + id + "\'");
			if (sr.next()){
				name = sr.getString("username");
			} else {
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return name;
	}
	
	/*
	 * The below functions are for internal use, as they allow for easy customization for
	 * more than just the SQLPermissions plug-in. I plan to make them customizable in the future
	 * for use in plug-ins that are similar to Towny, and other extra features for servers.
	 */
	private String[] getPermissionsByUser (String user){
		String[] permissions = {""};
		ResultSet sr = null;
		try {
			sr = si.query("SELECT permissions FROM players WHERE username=\'" + user + "\'");
			if (sr.next()){
				permissions = sr.getString("permissions").split(",");
			} else {
				return null;
			}
		} catch (SQLException | ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}
		return permissions;
	}
	
}
