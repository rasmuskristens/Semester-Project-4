package main;
import java.util.*;
import java.sql.*;

//This class handles interactions with the database

	public class JDBCConnect {
		
		
		private Connection connect = null;
		
		//values found on elephant Semester Project 4
		private final String url ="jdbc:postgresql://balarama.db.elephantsql.com/";
		private final String user = "wejponay";
		private final String password = "LusSGW0u3UrElFlBhNAzu_1s4Yxs91p7";
		
		
		public Connection connect() {
			// Establishing a PostgreSQL database connection
					
			try {
				this.connect = DriverManager.getConnection(url, user, password); //jdbc driver installed, otherwise no function
				}
			
			catch (Exception exception) {
				System.out.println("Connection failed");
				exception.printStackTrace();
			}
			return connect;
		}

		
//Sign Up Methods
		public void addUserToDatabase(String user,String password) { //used for Sign Up
		
		String sql = "INSERT INTO public.user(username,password) VALUES('"+user+"','"+password+"');";
		
		try {
			connect();
			Statement statement = connect.createStatement();
			statement.executeUpdate(sql);
			System.out.println("success in adding user to database");
			connect.close();
		}
		catch(SQLException e) {
			System.out.println("Error trying to add user");
			e.printStackTrace();
		}
		
	}

	public boolean isItAUser(String user){ //used in sign in to figure out if the user name already exists
		String sql ="SELECT username FROM public.user WHERE username='"+user+"'";
		
		try {
			connect();
			Statement statement = connect.createStatement();
			ResultSet rest = statement.executeQuery(sql);
			int i=0;
			while(rest.next()) {
				i++;
				if(rest.getString(i).equals(user)) {
					System.out.println("username already existing");
					connect.close();
					return false;
				}
				
			}
			connect.close();
		}
		catch(SQLException exception) {
			System.out.println("unable to read if the user is in the database");
			exception.printStackTrace();
		}
		
		return true;
		
	}

//Log In methods
	public boolean doesTheUserNameMatch(String user) { //used in log in to see if the username matches
		String sql="SELECT username FROM public.user WHERE username='"+user+"'";
		try {
			connect();
			Statement statement = connect.createStatement();
			ResultSet rest = statement.executeQuery(sql);
			int i=0;
			while(rest.next()) {
				i++;
				if(rest.getString(i).equals(user)) {
					System.out.println("username is matching");
					connect.close();
					return true;
				}
			}
			connect.close();
			
	}
		catch(SQLException exception) {
			System.out.println("unable to read in the database");
			exception.printStackTrace();
		}
		return false;
	}
	

	
	public boolean doesTheUserPasswordMatch(String user, String password) { //used to see if the password matches the username
		String sql="SELECT password FROM public.user WHERE username='"+user+"'";
		try {
			connect();
			Statement statement = connect.createStatement();
			ResultSet rest = statement.executeQuery(sql);
			int i=0;
			while(rest.next()) {
				i++;
				if(rest.getString(i).equals(password)) {
					System.out.println("password is equal");
					connect.close();
					return true;
				}
				
			}
			connect.close();
	}
		catch(SQLException exception) {
			System.out.println("unable to read in the database");
			exception.printStackTrace();
		}
		return false;
	}
	

//Random Generator Methods	
	
	public void insertAsNewObjectPV(RandomStorage generate) {
		
		try {
			int panelId = generate.getPvid();
			int areaId = generate.getAreaid();
			Timestamp time =  generate.getTime_pv();
			
			String sql = "INSERT INTO public.update_panel(time, area_id, panel_id) VALUES ('" + time + "', " + areaId+", "+ panelId + ");";
			
			PreparedStatement preStat =connect().prepareStatement(sql);
			
			preStat.execute();
			
			connect.close();
		}

		catch (SQLException e) {

			System.out.println("Error trying to insert PV");

			e.printStackTrace();
		}
	}
	
	public void insertAsNewObjectThermal(RandomStorage generate) {
		
		try {
			int panelId = generate.getId();
			 //all thermal panels are considered to be in the same area with the id 5
			Timestamp time =  generate.getTime_thermal();
			
			String sql = "INSERT INTO public.update_panel VALUES ('2021-01-01 01:00:00', 5, 1);";
			
			System.out.println("yes");
			PreparedStatement preStat =connect().prepareStatement(sql);
			
			preStat.execute();
			
			connect.close();
		}

		catch (SQLException e) {

			System.out.println("Error trying to insert Thermal");

			e.printStackTrace();
		}
	}
	
	//PV panel
	public void storeAsNewObjectPV(RandomStorage generate) {
	
			try {

				
				//String sql = "UPDATE public.pv_panel SET time=?, area_id=?,panel_id=?, current=?, volt=?, irradiance_on_surface=?, resistance=?, power=?, energy_efficiency=? WHERE updateId="+updateId+ "';";
				String sql ="UPDATE public.pv_panel	SET time=?, area_id=?,panel_id=?, current=?, volt=?, irradiance_on_surface=?, resistance=?, power=?, energy_efficiency=? WHERE update_id=1;";
				
				PreparedStatement preStat =connect().prepareStatement(sql);
		
				preStat.setTimestamp(1, generate.getTime_pv());
				
				preStat.setInt(2, generate.getAreaid());
				
				preStat.setInt(3, generate.getPvid());
				
				preStat.setDouble(4, generate.getCurrentA());
							
				preStat.setDouble(5, generate.getVoltA());
							
				preStat.setDouble(6, generate.getIrradiance_on_surface_pv());
				
				preStat.setDouble(7, generate.getResistance());
				
				preStat.setDouble(8, generate.getPower()); 
				
				preStat.setDouble(9, generate.getEnergy_efficiency_pv());
										
				preStat.executeUpdate();
			
				connect.close();
			}
			
	
			catch (SQLException e) {
	
				System.out.println("Error trying to generate object for PV");
	
				e.printStackTrace();
			}
	}
	
	//Thermal Panel
	public void storeAsNewObjectThermal(RandomStorage generate) {
		
			try {
				
				
				String sql ="UPDATE public.thermal SET time=?,panel_id=?,temp_in=?, temp_out=?, liquid_temp=?,volume=?, irradiance_on_surface=?, heat=?, power_output=?, watt=?, energy_efficiency=? WHERE area_id=5;";

				
				PreparedStatement preStat =connect().prepareStatement(sql);
		
                preStat.setTimestamp(1, generate.getTime_thermal());
				
				preStat.setInt(2, generate.getId());
				
				preStat.setDouble(3, generate.getTempI());
				
				preStat.setDouble(4, generate.getTempOA());
					
				preStat.setDouble(5, generate.getIrradiance_on_surface_thermal());
				
				preStat.setDouble(6, generate.getVolume());
				
				preStat.setDouble(7, generate.getLiquid());
				
				preStat.setDouble(8, generate.getHeat());
				
				preStat.setDouble(9, generate.getPower_out());
				
				preStat.setDouble(10, generate.getWatt());
				
				preStat.setDouble(11, generate.getEnergy_efficiency_thermal());
												
				preStat.executeUpdate();
									
				connect.close();
				}
			
		
			catch (SQLException e) {
		
				System.out.println("Error trying to generate object for Thermal");
		
				e.printStackTrace();
			}
	}	
	
//Threshold methods
	
	public void addThresholdToDatabase(double newThreshold) { //used for Sign Up
		
		String sql = "INSERT INTO public.thresholds(recording) VALUES("+newThreshold+");";
		
		try {
			connect();
			Statement statement = connect.createStatement();
			statement.executeUpdate(sql);
			System.out.println("success in adding threshold to database");
			connect.close();
		}
		catch(SQLException e) {
			System.out.println("Error trying to add threshold");
			e.printStackTrace();
		}
		
	}
	
	//manual threshold check
	public String manualSelectingThreshold (Timestamp date1, Timestamp date2, int panelId,int areaId) {
		
			String sql;
			
			if( panelId!=0) {
				
				if(areaId!=0) { //select by panelId and areaID
					sql="SELECT area_id, panel_id, SUM(energy_efficiency)/(24*DATE_PART('day', '"+ date2 +"'::timestamp - '"+ date1 +"'::timestamp)) "
							+ "FROM public.log_pv_panel WHERE time BETWEEN '"+ date1 +"' AND '" + date2 +
						"' AND panel_id="+panelId+" AND area_id="+areaId+" GROUP BY area_id, panel_id ORDER BY area_id ASC;";
					System.out.println(" select 1");
				}
				
				else { //select only by panelID
					
				sql="SELECT panel_id, SUM(energy_efficiency)/(24*DATE_PART('day', '"+ date2 +"'::timestamp - '"+ date1 +"'::timestamp)) "
						+ "FROM public.log_pv_panel WHERE time BETWEEN '"+ date1 +"' AND '" + date2 +
						"' AND panel_id="+panelId+" GROUP BY panel_id ORDER BY panel_id ASC;";
				System.out.println(" select 2");
				}
			}
			
			else if(areaId!=0) { //select only by areaID
				
				sql="SELECT area_id, SUM(energy_efficiency)/(24*DATE_PART('day', '"+ date2 +"'::timestamp - '"+ date1 +"'::timestamp)) "
						+ "FROM public.log_pv_panel WHERE time BETWEEN '"+ date1 +"' AND '" + date2 + 
						"' AND area_id="+areaId+" GROUP BY area_id ORDER BY area_id ASC;";
				System.out.println(" select 3");
			}
			
			else { //select the whole system
				
				sql="SELECT SUM(energy_efficiency)/(24*DATE_PART('day', '"+ date2 +"'::timestamp - '"+ date1 +"'::timestamp)) "
						+ "FROM public.log_pv_panel WHERE time BETWEEN '"+ date1 + "' AND '"+ date2 +"';";
				System.out.println("select 4");
			}
			
			
			
			ArrayList <Integer> panelIdArray = new ArrayList<>();
			ArrayList <Integer> areaIdArray = new ArrayList<>();
			ArrayList <Double> resultArray = new ArrayList<>();
			
			try {
				
				connect();
				Statement statement = connect.createStatement();
				ResultSet rest = statement.executeQuery(sql);
				
				while(rest.next()) {
					
					if(panelId!=0) {
						
						if(areaId!=0) { //select by panelId and areaID
				   
							areaIdArray.add(rest.getInt(1));
							panelIdArray.add(rest.getInt(2));
							resultArray.add(rest.getDouble(3));
							
							connect.close();
							return "The average efficiency for the panel "+ panelIdArray.get(0) + " of the area " + areaIdArray.get(0) + 
									" in the chosen time frame is " + resultArray.get(0) ;
									
						}
						
						else { //select only by panelID
							panelIdArray.add(rest.getInt(1));
							resultArray.add(rest.getDouble(2));
							
							connect.close();
							return "The average efficiency for the panels with ID "+ panelIdArray.get(0) + 
									" of all the areas in the chosen time frame is " + resultArray.get(0);
						}
					
					}
					
					else if(areaId!=0) { //select only by areaID
						
						areaIdArray.add(rest.getInt(1));
						resultArray.add(rest.getDouble(2));
						
						connect.close();
						return "The average efficiency of the area " + areaIdArray.get(0) + 
								"  within the chosen time frame is " + resultArray.get(0) ;
					}
					
					else { //select the whole system
						
						areaIdArray.add(rest.getInt(1));
						resultArray.add(rest.getDouble(1));
						
						connect.close();
						return "The average efficiency of the whole system within the chosen time frame is " + resultArray.get(0);
					}
												
				}
			
			}
			
			catch (SQLException e) {
		
				System.out.println("Error trying to check threshold manually");
				
				e.printStackTrace();
			}
			return null;		
	}
	
		
	//automatic threshold check
	public boolean isItAboveThreshold() {
			
			String sql = "SELECT SUM(energy_efficiency)/24 FROM public.pv_panel WHERE time >= NOW() - '1 day'::INTERVAL;";
			String sqlThreshold = "SELECT MAX(time_recorded), recording FROM thresholds  GROUP BY time_recorded  ORDER BY time_recorded ASC;";
			
			double efficiencyAvg = 0; //to save the value of the sum of the efficiency from the database
			
			try {
				connect();
				
				Statement statement = connect.createStatement();
				
				ResultSet rest = statement.executeQuery(sql);
						
				
				while(rest.next()) {
					efficiencyAvg = rest.getDouble(1); //saving value
				}
				
				ResultSet rs = statement.executeQuery(sqlThreshold);
				double threshold = 0;
				
				while(rs.next()) {
					threshold = rs.getDouble(2); //saving value
				}
				System.out.println(threshold);
				
				if(threshold > efficiencyAvg)	{ //testing if the efficiency of the panels are above or below threshold
					connect.close();
					
					return false; //if threshold is greater than efficiency return false
				}
				
				connect.close();
			}
			
			catch (SQLException e) {
		
				System.out.println("Error trying to test threshold");
		
				e.printStackTrace();
			}
			
			return true; //efficiency is above threshold
		
		}
	
}
	
		
