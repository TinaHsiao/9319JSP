package com.iii.eeit93.TianYi19;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

public class Procedure2_2 {

	private String connUrl = "jdbc:sqlserver://192.168.56.117:2466;databaseName=HOMEWORK";
	
	public static void main(String[] args) {
		Procedure2_2 prd = new Procedure2_2();
		prd.addMovieSchedule("2016-12-25 13:00",1,"A廳");
		prd.createSaleSeat("2016-12-25 13:00",1,"A廳");

	}
	
	public void addMovieSchedule(String ptime,int movie,String roomid){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(connUrl, "sa", "P@sswOrd");
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO playlist ");
			sb.append("(ptime,movie,roomid) ");
			sb.append("VALUES (?,?,?) ");
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
						
			pstmt.setString(1,ptime);
			pstmt.setInt(2, movie);
			pstmt.setString(3,roomid);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void createSaleSeat(String ptime,int movie,String roomid){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(connUrl, "sa", "P@sswOrd");
			CallableStatement cstmt = conn.prepareCall("{CALL GEN_SEATS2(?,?,?,?)}");
			cstmt.setString(1,ptime);
			cstmt.setInt(2, movie);
			cstmt.setString(3,roomid);
			cstmt.registerOutParameter(4, Types.INTEGER);	
			//接SQL回傳的參數(回傳值會到procedure跑完才會output，所以java會等待回傳才繼續執行下行程式碼)
			
			cstmt.execute();
			System.out.println(cstmt.getInt(4));	//印出更新筆數
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}

}
