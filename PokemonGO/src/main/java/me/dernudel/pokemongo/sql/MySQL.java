package me.dernudel.pokemongo.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MySQL {
	Connection con;
	
	public MySQL() {
	}
	
	public boolean connect(String host, String port, String database, String user, String password) {
		boolean bool = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            System.err.println("MySQL-Driver not found");
            return false;
        }
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true");
            if(!(con.isClosed())) {
                System.out.println("Successfully connected to MySQL");
                return createTables();
            }
        } catch(SQLException e) {
            System.out.println("Cannot connect to MySQL; ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
		return bool;
    }
	
	public void registerUser(String uuid) {
		String sql = "INSERT INTO `users`(user) VALUES " +
                "('"+uuid+"') WHERE pokemon IS NULL;";
    	try {
    		con.createStatement().executeUpdate(sql);
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void close() {
	    try {
	        if(con != null && (!(con.isClosed()))) {
	            con.close();
	        }
	    } catch(SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private boolean createTables() {
    	try {
    		con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `users` " +
			"(user VARCHAR(100) NOT NULL, " +
    		"pokemon VARCHAR(255) DEFAULT = '0,'," +
			"CONSTRAINT user_uID PRIMARY KEY (user)" +
			");");

		return true;
   		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
    }
	
	public void doUpdate(String uuid, int pokemon) {
		String s;
		s = getPkmnAsString(uuid);
		if (!s.contains(String.valueOf(pokemon))) s  += pokemon + ",";
		String sql = "UPDATE `users` SET pokemon='" + s + "' WHERE user='" + uuid +"';";
    	try {
    		con.createStatement().executeUpdate(sql);
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    }

	private String getPkmnAsString(String uuid) {
        String s = "0,";
    	String sql = "SELECT * FROM `users` WHERE user='" + uuid + "';";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	s = rs.getString("pokemon");
            }
        } catch(SQLException e) {
        	e.printStackTrace();
        }
        return s;
	}
	
	public ArrayList<Integer> getPkmn(String uuid) {
		ArrayList<Integer> l = new ArrayList<>();
		String p = getPkmnAsString(uuid);
		if (p.equals(null)) return l;
		if (!p.contains(",")) return l;
		String[] ps = p.substring(2).split(",");
		if (ps.length <= 0) return l;
		for (String pss : ps) {
			try {
				if (!ps.equals("0")) l.add(Integer.parseInt(pss));
			} catch (NumberFormatException ec) {}
		}
		return l;
	}
	
	public ResultSet doQuery(String sql) {
        try {
            return con.createStatement().executeQuery(sql);
        } catch(SQLException e) {
        	e.printStackTrace();
        }
        return null;
    }
	

}
