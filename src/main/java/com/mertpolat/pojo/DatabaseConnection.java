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
		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void createTable() {
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users(userId INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
					+ " tc TEXT NOT NULL,\r\n" + " name TEXT ,\r\n" + " surname TEXT ,\r\n"
					+ " monthlyIncome INTEGER ,\r\n" + " telephone TEXT ,\r\n" + " customerLimit  INTEGER ,\r\n"
					+ " creditStatus  BOOLEAN ,\r\n" + " table_constraint)");

			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS Messages(messageId INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
							+ " message TEXT NOT NULL,\r\n" + " telephone TEXT NOT NULL,\r\n"
							+ " customerLimit  INTEGER ,\r\n" + " creditStatus  BOOLEAN ,\r\n" + " table_constraint)");

			// dumy data
			connection.prepareStatement(
					"INSERT INTO Users(userId,tc,name,surname,monthlyIncome,telephone,customerLimit,creditStatus) VALUES(1,'12345678900','mert','polat',5000,'05350000000',1000,1)");
			connection.prepareStatement(
					"INSERT INTO Messages(messageId,message,telephone,customerLimit,creditStatus) VALUES(1,'Test message','05394070000',5000,1)");

			statement.close();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static PreparedStatement preparedStatement = null;

	public static void AddUser(User user) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:Credit.sqlite");

			preparedStatement = connection.prepareStatement(
					"INSERT INTO Users(userId,tc,name,surname,monthlyIncome,telephone,customerLimit,creditStatus) VALUES(?,?,?,?,?,?,?,?)");

			preparedStatement.setString(2, user.getTc());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getSurname());
			preparedStatement.setInt(5, user.getMonthlyIncome());
			preparedStatement.setString(6, user.getTelephone());
			preparedStatement.setInt(7, user.getLimit());
			preparedStatement.setBoolean(8, user.isCreditStatus());

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Bu fonksiyon çalýþtýðýnda (db'ye insert atýldýðýnda) sms atmayý saðlayan
	// Trigger,Fonksiyon yazýlmalýdýr.

	public static void AddMessage(User user, String message) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:Credit.sqlite");
			preparedStatement = connection.prepareStatement(
					"INSERT INTO Messages(messageId,message,telephone,customerLimit,creditStatus) VALUES(?,?,?,?,?)");

			preparedStatement.setString(2, message);
			preparedStatement.setString(3, user.getTelephone());
			preparedStatement.setInt(4, user.getLimit());
			preparedStatement.setBoolean(5, user.isCreditStatus());

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}