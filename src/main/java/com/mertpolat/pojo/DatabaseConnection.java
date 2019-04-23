package com.mertpolat.pojo;

import java.sql.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;

public class DatabaseConnection {

	public static Connection connection = null;

	static {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:Credit.sqlite");

			createTable();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void createTable() {
		try {
			Statement statement = connection.createStatement();
		      statement.setQueryTimeout(30);

			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users(userId INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + " tc TEXT NOT NULL,\r\n"
					+ " name TEXT ,\r\n" + " surname TEXT ,\r\n" + " monthlyIncome INTEGER ,\r\n" + " telephone TEXT ,\r\n"
					+ " customerLimit  INTEGER ,\r\n" + " creditStatus  BOOLEAN ,\r\n" + " table_constraint)");
			
			//dumy data 
			connection.prepareStatement("INSERT INTO Users(userId,tc,name,surname,monthlyIncome,telephone,customerLimit,creditStatus) VALUES(1,'12345678900','mert','polat',5000,'05350000000',1000,1)");
            
			statement.close();
			//connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

        public static PreparedStatement preparedStatement = null;

        //----------------------

        public static void Add(User user){           
          //int userId=1;
        	try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:Credit.sqlite");
                preparedStatement = connection.prepareStatement("INSERT INTO Users(userId,tc,name,surname,monthlyIncome,telephone,customerLimit,creditStatus) VALUES(?,?,?,?,?,?,?,?)");
              
                //preparedStatement.setInt(1,1);
                preparedStatement.setString(2,user.getTc());
                preparedStatement.setString(3,user.getName());
                preparedStatement.setString(4,user.getSurname());
                preparedStatement.setInt(5,user.getMonthlyIncome());
                preparedStatement.setString(6,user.getTelephone());
                preparedStatement.setInt(7,user.getLimit());
                preparedStatement.setBoolean(8, user.isCreditStatus()); 
                
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
	}
	
