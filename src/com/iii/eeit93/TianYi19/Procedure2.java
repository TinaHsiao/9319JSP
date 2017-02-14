package com.iii.eeit93.TianYi19;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

public class Procedure2 {

	private String connUrl = "jdbc:sqlserver://192.168.56.117:2466;databaseName=HOMEWORK";
	
	public static void main(String[] args) {
		Procedure2 prd = new Procedure2();
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
			CallableStatement cstmt = conn.prepareCall("{CALL GEN_SEATS2(?,?,?)}");
			cstmt.setString(1,ptime);
			cstmt.setInt(2, movie);
			cstmt.setString(3,roomid);
			
			cstmt.execute();
			Thread.sleep(3000);//暫停三秒
//拖時間，讓java與SQLServer間連線不會太快關閉，以完成資料新增，避免新增到一半時程式碼已跑下面造成連線關閉
			
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
