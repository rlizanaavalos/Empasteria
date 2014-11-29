/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.sql.*;

public class Conexion {
	private Connection con = null;

	public Conexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/empasteria","root", "");
			//con = DriverManager.getConnection("jdbc:mysql://127.8.49.2:3306/empastes","admingh5rTId", "5ENNnC_uqFgZ");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public Connection getConexion() {
		return con;
	}	
}