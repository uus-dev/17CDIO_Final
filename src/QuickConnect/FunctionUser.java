package QuickConnect;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FunctionUser {
	private static Connector con = Function.mysql();
	
	public static int changeNickname(String nickname, int id) {
		try {
			con.update("UPDATE users set nickname=? WHERE user_id=?",new String[]{"s",nickname},new String[]{"i",""+id});
			return 0;
		} catch(SQLException e) {
			return 1;
		}
	}
	
//	public static String getNickName(int id) {
//		Vi skal lave en funktion som retunerer brugerens nickname.
//		String nickName = null;
//		try {
//			nickName = con.select("SELECT nickname FROM users WHERE user_id=?", new String[]{"i",""+id}).toString();
//		} catch(SQLException e) {
//			e.printStackTrace();
//		}
//		return nickName;
//	}
	
	public static int changePassword(int id, String oldPass, String newPass, String newPass2) throws SQLException, NoSuchAlgorithmException{
		if(Function.checkPassword(newPass)==0 && newPass.equals(newPass2)){
			con.update("update users set password=? where user_id =?", new String[]{"s",Function.md5(newPass)},new String []{"i",""+id});
			return 0;
		}else if(!newPass.equals(newPass2)){
			// newPass stemmer ikke med med newPass2 stemmer ikke
			return 1;
		}else if(Function.checkPassword(newPass)==1){
			// Password skal være 8-24
			return 2;
		}else if(Function.checkPassword(newPass)==2){
			// Password skal indeholde et tal, stort og et lille bogstav
			return 3;
		}else {
			// Passwordet må kun indeholde tegn fra ascii<32 || ascii>126
			return 4;
		}
	}
	
	public static void deleteUser(int id, String password) throws SQLException, NoSuchAlgorithmException{
		boolean bool = false;
			bool = con.check("SELECT user_id FROM users WHERE user_id=? AND password=?", new String []{"i",""+id},new String[]{"s",Function.md5(password)});
		if(bool){
			con.update("DELETE FROM users WHERE user_id=?",new String[]{"i",""+id});
		}else{
			System.out.println("Password forkert");
		}
	}
	
	public static String[] showGroups(int id) throws SQLException{
		ArrayList<String> groups=new ArrayList<String>();
		ResultSet rs=con.select("SELECT `group_name` FROM `groups` WHERE group_id IN (SELECT group_id FROM `group_members` WHERE group_members.user_id=?)",new String[][]{{"i",""+id}});
		while(rs.next()) groups.add(rs.getString("group_name"));
		return groups.toArray(new String[groups.size()]);
	}
}
