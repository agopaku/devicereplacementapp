package com.s2d.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import com.s2d.bean.FiPaySledBean;
import com.s2d.utils.FileUtils;

public class FiPayDatabaseConnect
{

	static Properties prop = new Properties();
	static {
		InputStream input = null;
		try {			
			input = new FileInputStream("db.properties");

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@"+prop.getProperty("HOSTNAME")+":"+prop.getProperty("PORT")+"/"+prop.getProperty("SSID");

	//  Database credentials
	static final String USER = prop.getProperty("DB_USER");
	static final String PASS = prop.getProperty("DB_PASSWORD");

	public static ArrayList<FiPaySledBean> getDeviceList()
	{
		ArrayList<FiPaySledBean> listOfDevices = new ArrayList<>();

		try
		{
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM ATGDB_ULTA_CORE.ULTA_FIPAY_SLED_ADAPTER_CONFIG";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				FiPaySledBean beanObj = new FiPaySledBean();
				beanObj.setId(rs.getInt("ID"));
				beanObj.setDeviceName(rs.getString("DEVICE_NAME"));
				beanObj.setBasePort(rs.getInt("BASE_PORT"));
				beanObj.setTerminalPort(rs.getInt("TERMINAL_PORT"));
				beanObj.setOffset(rs.getInt("OFFSET"));
				beanObj.setHostAddress(rs.getString("FIPAY_HOSTADDRESS"));
				beanObj.setInitDebit(rs.getBoolean("INITDEBIT"));

				listOfDevices.add(beanObj);
			}
			st.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return listOfDevices;
	}

	public static void updateDeviceInfo(FiPaySledBean actualDeviceBean, FiPaySledBean replacementDeviceBean)
	{
		try
		{
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to a selected database...");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			String deleteQuery = "DELETE FROM ATGDB_ULTA_CORE.ULTA_FIPAY_SLED_ADAPTER_CONFIG " +
					"WHERE DEVICE_NAME = '"+actualDeviceBean.getDeviceName()+"'";

			String insertQuery = "INSERT INTO ATGDB_ULTA_CORE.ULTA_FIPAY_SLED_ADAPTER_CONFIG VALUES('" +
					replacementDeviceBean.getId()+"','"+replacementDeviceBean.getDeviceName()+"','"+replacementDeviceBean.getBasePort()+"','"+
					replacementDeviceBean.getTerminalPort()+"','"+replacementDeviceBean.getOffset()+"','"+replacementDeviceBean.getHostAddress()+"','"+
					(replacementDeviceBean.getInitDebit() ? 1 : 0)+"')";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			System.out.println("Deleting device "+actualDeviceBean.getDeviceName()+" details from table...");
			st.executeUpdate(deleteQuery);
			System.out.println("Successfully Deleted device "+actualDeviceBean.getDeviceName()+" details from table...");
			System.out.println("Inserting device "+replacementDeviceBean.getDeviceName()+" details into table...");
			st.executeUpdate(insertQuery);
			System.out.println("Successfully Inserted device "+replacementDeviceBean.getDeviceName()+" details into table...");

			st.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void insertDeviceInfo(FiPaySledBean newDeviceBean)
	{
		try
		{
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to a selected database...");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			String insertQuery = "INSERT INTO ATGDB_ULTA_CORE.ULTA_FIPAY_SLED_ADAPTER_CONFIG VALUES('" +
					newDeviceBean.getId()+"','"+newDeviceBean.getDeviceName()+"','"+newDeviceBean.getBasePort()+"','"+
					newDeviceBean.getTerminalPort()+"','"+newDeviceBean.getOffset()+"','"+newDeviceBean.getHostAddress()+"','"+
					(newDeviceBean.getInitDebit() ? 1 : 0)+"')";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			System.out.println("Inserting device "+newDeviceBean.getDeviceName()+" details into table...");
			st.executeUpdate(insertQuery);
			System.out.println("Successfully Inserted device "+newDeviceBean.getDeviceName()+" details into table...");

			st.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void removeDeviceInfo(FiPaySledBean deviceBean)
	{		
		try
		{
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to a selected database...");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			String deleteQuery = "DELETE FROM ATGDB_ULTA_CORE.ULTA_FIPAY_SLED_ADAPTER_CONFIG " +
					"WHERE DEVICE_NAME = '"+deviceBean.getDeviceName()+"'";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			System.out.println("Deleting device "+deviceBean.getDeviceName()+" details from table...");
			st.executeUpdate(deleteQuery);
			System.out.println("Successfully Deleted device "+deviceBean.getDeviceName()+" details from table...");

			FileUtils.addDeviceIDToDeletedDeviceInfo(deviceBean.getId(), deviceBean.getHostAddress());
			st.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}