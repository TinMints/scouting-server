package net.tinmints.scounting.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import net.tinmints.scounting.server.model.ScoutData;

public class Dao {

	private static Dao dao = new Dao();
	private String insert = "INSERT INTO Input ( [Team], "
			+ "[Auton Start Middle], "
			+ "[Auton Baseline], "
			+ "[Auton Low Fuel], "
			+ "[Auton High Fuel], "
			+ "[Auton Gear], "
			+ "[Autoton Rotor Turns], "
			+ "[Scores Fuel Low], "
			+ "[Scores Fuel High], "
			+ "[Fuel Floor], "
			+ "[Fuel Station], "
			+ "[Triggers Hopper], "
			+ "[Gets Gear], "
			+ "[Rotor Turns], "
			+ "[Lifts], "
			+ "[Offense], "
			+ "[Defense], "
			+ "[Breakdown Or no Show], "
			+ "[Recovers], "
			+ "[Field Score], "
			+ "[Scouter], "
			+ "[Match], "
			+ "[Fouls], "
			+ "[Deleiver Gear], "
			+ "[Pick up Gear],"
			+ "[Pilot]) VALUES (";
	
	private String select = "SELECT ID FROM Input WHERE [Match]=";
	private String andWhere = " AND [Team]=";

	private String selectData = "SELECT * FROM Input WHERE [Match]=";
	
	public String insertData(ScoutData data) throws Exception {
		
		String code = null;
		
		Connection con = DriverManager.getConnection("jdbc:odbc:scouting4575");
		//Connection con = DriverManager.getConnection("Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=C:/Scouting/Steam Scouting Database.accdb");
		Statement state = con.createStatement();
		
		String query = select + data.getMatchNumber() + andWhere + data.getTeamNumber();
		
		ResultSet result = state.executeQuery(query);
		int id = -1;
		if(result.next()) {
			id = result.getInt("ID");
		}
		
		String comm = null;
		
		result.close();
		state.close();
		
		state = con.createStatement();
		
		if(id==-1) {
		
			comm = insert + data.getTeamNumber() + "," + convert(data.isAutoStartInMiddle()) + "," + convert(data.isAutoCrossedLine()) + "," + 
				convert(data.isAutoLowBoiler()) + "," + convert(data.isAutoHighBoiler()) + "," + convert(data.isAutoMadePeg()) + "," + data.getAutoRotors() + "," +
				data.getTeleLowBoiler() + "," + data.getTeleHighBoiler() + "," + data.getTeleFuelFloor() + "," + data.getTeleFuelStation() + "," +
				data.getTeleHopperTrigger() + "," + data.getTeleGearsGot() + "," + data.getTeleRotors() + "," + convert(data.isTeleLifts()) + "," +
				convert(data.isTeleOffense()) + "," + convert(data.isTeleDefense()) + "," + convert(data.isTeleBreakdown()) + "," + convert(data.isTeleRecover()) + "," +
				data.getScore() + ",'" + data.getRecorder() + "'," + data.getMatchNumber() + "," + data.getFouls() + "," + data.getTeleGearsDeliverd() + "," + 
				data.getTeleGearsPickedUp() + "," + data.getTelePilotRate() + ")";
			code = "inserted";
		//System.out.println(comm);
		} else {
			code = "updated";
			comm = getUpdateStatement(data, id);
			//System.out.println(comm);
		}
		
		if(comm!=null) {
			state.execute(comm);
		}
		
		state.close();
		con.close();
		return code;
   }
	
   public ScoutData getScoutData(int team, int match) throws Exception {
		ScoutData data = null;
		
		Connection con = DriverManager.getConnection("jdbc:odbc:scouting4575");
		//Connection con = DriverManager.getConnection("Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=C:/Scouting/Steam Scouting Database.accdb");
		Statement state = con.createStatement();
		
		String query = selectData + match + andWhere + team;
		
		ResultSet result = state.executeQuery(query);
		int id = -1;
		if(result.next()) {
			data = new ScoutData();
			data.setTeamNumber(result.getInt("Team"));
			data.setScore(result.getInt("Field Score"));
			data.setRecorder(result.getString("Scouter"));
			data.setMatchNumber(result.getInt("Match"));
			data.setFouls(result.getInt("Fouls"));
			
			data.setAutoCrossedLine(convertBack(result.getInt("Auton Baseline")));
			data.setAutoHighBoiler(convertBack(result.getInt("Auton High Fuel")));
			data.setAutoLowBoiler(convertBack(result.getInt("Auton Low Fuel")));
			data.setAutoMadePeg(convertBack(result.getInt("Auton Gear")));
			data.setAutoRotors(result.getInt("Autoton Rotor Turns"));
			data.setAutoStartInMiddle(convertBack(result.getInt("Auton Start Middle")));
			
			data.setTeleBreakdown(convertBack(result.getInt("Breakdown Or no Show")));
			data.setTeleDefense(convertBack(result.getInt("Defense")));
			data.setTeleFuelFloor(result.getInt("Fuel Floor"));
			data.setTeleFuelStation(result.getInt("Fuel Station"));
			data.setTeleGearsDeliverd(result.getInt("Deleiver Gear"));
			data.setTeleGearsGot(result.getInt("Gets Gear"));
			data.setTeleGearsPickedUp(result.getInt("Pick up Gear"));
			data.setTeleHighBoiler(result.getInt("Scores Fuel High"));
			data.setTeleHopperTrigger(result.getInt("Triggers Hopper"));
			data.setTeleLifts(convertBack(result.getInt("Lifts")));
			data.setTeleLowBoiler(result.getInt("Scores Fuel Low"));
			data.setTeleOffense(convertBack(result.getInt("Offense")));
			data.setTelePilotRate(result.getInt("Pilot"));
			data.setTeleRecover(convertBack(result.getInt("Recovers")));
			data.setTeleRotors(result.getInt("Rotor Turns"));
		}

		result.close();
		state.close();
		con.close();
		
		return data;
		
   }
	
	private int convert(boolean b) {
		if(b) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private boolean convertBack(int i) {
		if(i==1) 
			return true;
		return false;
	}
	
	public static Dao newInstance() {
		return dao;
	}
	
	private Dao() {
		try {
			 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getUpdateStatement(ScoutData data, int id) {
		String update = "UPDATE Input SET "
				+ "[Team]=" + data.getTeamNumber()
				+ ",[Auton Start Middle]=" + convert(data.isAutoStartInMiddle())
				+ ",[Auton Baseline]=" + convert(data.isAutoCrossedLine())
				+ ",[Auton Low Fuel]=" + convert(data.isAutoLowBoiler())
				+ ",[Auton High Fuel]=" + convert(data.isAutoHighBoiler())
				+ ",[Auton Gear]=" + convert(data.isAutoMadePeg())
				+ ",[Autoton Rotor Turns]=" + data.getAutoRotors()
				+ ",[Scores Fuel Low]=" + data.getTeleLowBoiler()
				+ ",[Scores Fuel High]=" + data.getTeleHighBoiler()
				+ ",[Fuel Floor]=" + data.getTeleFuelFloor()
				+ ",[Fuel Station]=" + data.getTeleFuelStation()
				+ ",[Triggers Hopper]=" + data.getTeleHopperTrigger()
				+ ",[Gets Gear]=" + data.getTeleGearsGot()
				+ ",[Rotor Turns]=" + data.getTeleRotors()
				+ ",[Lifts]=" + convert(data.isTeleLifts())
				+ ",[Offense]=" + convert(data.isTeleOffense())
				+ ",[Defense]=" + convert(data.isTeleDefense())
				+ ",[Breakdown Or no Show]="  + convert(data.isTeleBreakdown())
				+ ",[Recovers]=" + convert(data.isTeleRecover())
				+ ",[Field Score]=" + data.getScore()
				+ ",[Scouter]='" + data.getRecorder() + "'"
				+ ",[Match]=" + data.getMatchNumber()
				+ ",[Fouls]=" + data.getFouls()
				+ ",[Deleiver Gear]=" + data.getTeleGearsDeliverd()
				+ ",[Pick up Gear]=" + data.getTeleGearsPickedUp()
				+ ",[Pilot]=" + data.getTelePilotRate()
				+ " WHERE ID=" + id;
		
		return update;
	}
}
