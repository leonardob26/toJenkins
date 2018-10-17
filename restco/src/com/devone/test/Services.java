package com.devone.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Services {
	private String URL_BASE="http://localhost:8080/restco/service";
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetServiceList() {
		URL url;
		BufferedReader reader = null;
		HttpURLConnection urlConnection = null;
	   try {
		   url = new URL(URL_BASE + "/list/false/1/9");
		   urlConnection = (HttpURLConnection) url.openConnection();
		   reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
 		    String line = reader.readLine();
 		    /*if (line!=null)
 		    	jo = new JSONObject(line);  
		   
		   
	     InputStream in = new BufferedInputStream(urlConnection.getInputStream());
	     readStream(in);*/
 		   
	   } catch(Exception e){
	   }
	   finally {
		   urlConnection.disconnect();
	   }
	     
}

	@Test
	public void testGetService() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
