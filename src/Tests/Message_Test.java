package Tests;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import QuickConnect.Function;
import QuickConnect.FunctionUser;
import QuickConnect.User;

public class Message_Test {
	static User u = new User();
	static Function f = new Function(u);
	static FunctionUser fu = new FunctionUser(f);
	
	public static void main(String[] args) throws SQLException, IOException {
		u.setUserID(2);
		
		fu.con().update("DELETE FROM messages WHERE user_id=2 AND receiver_id=1");
		
		for(int i= 1; i<10; i++){
			fu.sendMessage("BeskedTest"+i+" ", 1);
		}
		
		System.out.println(test());
	}
	
	public static String test(){
	      String result = "";
	      try {
			for(int i = 0; i < fu.getAllMessages(1).size(); i++){
			      for(int j = 0; j < fu.getAllMessages(1).get(i).size(); j++){
			          result += fu.getAllMessages(1).get(i).get(j);
			      }
			      result += "\n";
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      return result;
	  }

}
