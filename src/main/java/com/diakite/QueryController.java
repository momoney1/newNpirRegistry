package com.diakite;
import org.springframework.stereotype.Controller;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.jsoup.Jsoup;
import java.io.File;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import java.lang.Exception;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.s3.model.SelectObjectContentEvent;
import com.amazonaws.services.s3.model.SelectObjectContentEvent.RecordsEvent;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.amazonaws.internal.ServiceEndpointBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Iterator;
import com.amazonaws.util.ValidationUtils;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.net.URISyntaxException;
import java.io.FileNotFoundException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.model.SelectObjectContentEvent.RecordsEvent;
import java.io.SequenceInputStream;
import com.amazonaws.internal.ReleasableInputStream;
import java.io.InputStream;
import com.amazonaws.internal.SdkFilterInputStream;
import java.io.ByteArrayInputStream;

import com.amazonaws.services.s3.model.SelectObjectContentEvent.RecordsEvent;

import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import static com.amazonaws.util.IOUtils.copy;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import java.util.*;

@RestController
public class QueryController {
	
		private final String url="jdbc:oracle:thin:@acoracle.montgomerycollege.edu:15521:acoradb";
		private final String user= "cs270229ap";
		private final String password = "ap";
		
		
		@RequestMapping("/add")
	    public ModelAndView add(HttpServletRequest request, HttpServletResponse response)
	    {
			try {
				String fName = request.getParameter("field1").toString();
				String lName = request.getParameter("field2").toString();
				String city = request.getParameter("field3").toString();
				String homeState = request.getParameter("field4").toString();
				String inputJson = "";
				//"https://npiregistry.cms.hhs.gov/api?version=2.1&last_name="+ lName + "&first_name="+fName
				String u = "https://npiregistry.cms.hhs.gov/api?version=2.1&last_name="+ lName + "&first_name=" + fName + "&city=" + city + "&state=" + homeState;
				URL url = new URL(u);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("Accept", "application/json");
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				JSONParser jParser = new JSONParser();
				Object j = jParser.parse(in);
				JSONObject ezm = (JSONObject) j;
				JSONArray results = (JSONArray) ezm.get("results");
				JSONObject myList = (JSONObject)results.get(0);
				JSONArray addressArray = (JSONArray) myList.get("addresses");
				JSONArray identifierArray = (JSONArray) myList.get("identifiers");
				JSONObject addressList1 = (JSONObject) addressArray.get(0);
				
				JSONObject basicInfo = (JSONObject) myList.get("basic");
				
				System.out.println(ezm.get("results") + " line 65");
				System.out.println(myList.get("basic") + "      this is results    line 66");
				System.out.println(addressList1.get("city")+ " here is json response line 67");
				System.out.println(addressList1.get("address_1")+ " here is json response line 68");
				System.out.println(addressList1.get("address_2")+ " here is json response line 70");
				System.out.println(addressList1.get("state")+ " here is json response line 71");
				System.out.println(addressList1.get("telephone_number")+ " here is json response line 67");
				
				//?version=2.1&last_name=${targetLname}&first_name=${targetFName}&state=${targetState}&city=${targetcity}&limit=25`
				BodyContentHandler handler = new BodyContentHandler();
				File input = new File("C:\\Users\\moedi\\newEclipse-workspace\\newNpirRegistry\\src\\main\\webapp\\index.jsp");
			    Metadata metadata = new Metadata();
			    FileInputStream inputstream = new FileInputStream(input);
				String name = response.getContentType();
				//System.out.println(input.getAbsolutePath() + "    this is the type of returned value    "+ fName + " "+ lName);
			    ModelAndView mv = new ModelAndView();
				mv.setViewName("DisplayNPI.jsp");
				mv.addObject("result",basicInfo.get("name"));
				mv.addObject("npi",myList.get("number"));
				mv.addObject("city",addressList1.get("city"));
				mv.addObject("state",addressList1.get("state"));
				mv.addObject("phone", addressList1.get("telephone_number"));
				
				return mv;
			}catch(Exception ex) {
				
			}
		return null;	
	    }
		
		@RequestMapping("/database")
		public ModelAndView queryDatabase(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView modelV = new ModelAndView();
			String fName = request.getParameter("input1").toString();
			String lName = request.getParameter("input2").toString();
			String npiNumber = request.getParameter("input3").toString();
			String homeState = request.getParameter("input4").toString();
			fName = fName.toUpperCase();
			lName = lName.toUpperCase();
			npiNumber = npiNumber.toUpperCase();
			homeState = homeState.toUpperCase();
			
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				System.out.println("Driver is successfuly loaded!!");
				Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt= con.createStatement();  
				ResultSet rs= stmt.executeQuery("select NPI, FIRST_NAME, LAST_NAME, ADDRESS_STATE from NPIREGISTRY WHERE FIRST_NAME = '"+ fName + "' AND LAST_NAME = '"+ lName + "'");
				System.out.println("Connection is successful with  "+ con);
				int count = 1;
				String result, npi, city, state;
				//rs.next();
				while(rs.next()) {
					modelV.setViewName("DisplayOracle.jsp");
					modelV.addObject("result"+ count, rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
					modelV.addObject("npi" + count, rs.getString("NPI"));
					modelV.addObject("city", "null");
					modelV.addObject("state" + count, rs.getString("ADDRESS_STATE"));
					modelV.addObject("phone", "NA");  
					System.out.println(count + " counter value ");
					count++;
					if(count >= 5 ) {
						break;
					}
					//System.out.println(rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
				}
				System.out.println(" outside rs.next ");
				/*System.out.println("This is result returned from query ");
				System.out.println("Npi number: "+ rs.getString("NPI"));
				System.out.println("First name: "+ rs.getString("FIRST_NAME"));
				System.out.println("Last name: "+ rs.getString("LAST_NAME"));
				System.out.println("State of residence: "+ rs.getString("ADDRESS_STATE"));
				*/
				con.close();	
				return modelV;
				
			} catch (ClassNotFoundException e) {
				System.out.println("connection not made line 21");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("connection not made line 23");
				e.printStackTrace();
			} catch(Exception e) {
				System.out.println("not fetching");
				e.printStackTrace();
			}
			
			return null;
		}
		
		@SuppressWarnings("deprecation")
		@RequestMapping("/s3Select")
		public ModelAndView s3Select(HttpServletRequest request, HttpServletResponse response) throws Exception{
			final ModelAndView mv = new ModelAndView();
			 String npiBucket = "npiawsbucket";
			 String npiKey = "npidata_pfile_20050523-20210411.csv";
		     String npiDirectory = "C:\\Users\\moedi\\OneDrive\\Desktop\\s3Results.json";
		     String npiSearchQuery = "SELECT s._1, s._6, s._7, s._21, s._24 FROM S3Object s WHERE S._1 = '"+ request.getParameter("s31").toString()+"'";
			
			 ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("Moe Diakite");
		        
		        final   AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
		                .withCredentials(credentialsProvider)
		                .withRegion("us-east-1")
		                .build();
		        
		       final  FileWriter  writeToMoe = new FileWriter("C:\\Users\\moedi\\OneDrive\\Desktop\\s3Results.txt");
		       
		        SelectObjectContentRequest queryRequest = generateBaseCSVRequest(npiBucket, npiKey, npiSearchQuery);
		        final AtomicBoolean isResultComplete = new AtomicBoolean(false); 

			
			try {
				System.out.println("hello");
	            S3Object o = s3Client.getObject(npiBucket, npiKey);
	            S3ObjectInputStream s3is = o.getObjectContent(); //s3sis is the csv file
	            FileOutputStream fos = new FileOutputStream(new File(npiDirectory));
	            BufferedReader reader = new BufferedReader(new InputStreamReader(o.getObjectContent()));
	            
	            String line;
	            int count = 0;
	            
	            while((line = reader.readLine()) != null && count < 10) {
	            	System.out.println("content from line 69: "+ line);
	            	count++; 
	            }
	             
			} catch(IOException e) {
				
			}
	        SelectObjectContentEvent.RecordsEvent recordEvent = new SelectObjectContentEvent.RecordsEvent();
	        SelectObjectContentEventStream rs;	        
	        try (OutputStream fileOutputStream = new FileOutputStream(new File (npiDirectory));
	               SelectObjectContentResult result = s3Client.selectObjectContent(queryRequest)) {
	               InputStream resultInputStream = result.getPayload().getRecordsInputStream(
	                       new SelectObjectContentEventVisitor() {
	                           @Override
	                           public void visit(SelectObjectContentEvent.RecordsEvent event)
	                           {
	                        	   event.getPayload().flip();

	                        	   try {
	                        		   event.getPayload().flip();
	                        		   int count = 1;
		                        	   String val = new String(event.getPayload().array(),"UTF-8");
		                        	   String npiVal = val.substring(7, 17);
		                        	   String firstName = val.substring(val.indexOf("_6") + 5, val.indexOf("_7") - 3);
		                        	   String lastName = val.substring(val.indexOf("_7") + 5, val.indexOf("_21") - 3);
		                        	   String address = val.substring(val.indexOf("_21") + 6, val.indexOf("_24") - 3);
		                        	   String st = val.substring(val.indexOf("_24") + 6, val.indexOf("_24") + 8);
		                        	   System.out.println("Received : " + val);
		                               System.out.println("Received : " + npiVal); //26-tillquotations
		                               System.out.println("Received : " + firstName); //+4
		                               System.out.println("Received : " + lastName);
		                               System.out.println("Received : " + address);
		                               System.out.println("Received : " + st);
		                               
		           					mv.setViewName("DisplayOracle.jsp");
		        					mv.addObject("result", firstName + " " + lastName);
		        					mv.addObject("npi", npiVal);
		        					mv.addObject("city", address);
		        					mv.addObject("state", st);
		        					mv.addObject("phone", "NA"); 
		                               
		                               
		                               writeToMoe.write(val);
		                               writeToMoe.close();
		                               System.out.println("message written to file ");
		               			    
		               				mv.setViewName("DisplayNPI.jsp");
		               				

	                        	   }catch(Exception e) {
	                        		 
	                        		   System.out.println(" try catch didnt work line 128");
	                        		   e.printStackTrace();
	                        	   }

	                           }

	                           /*
	                            * An End Event informs that the request has finished successfully.
	                            */
	                           @Override
	                           public void visit(SelectObjectContentEvent.EndEvent event)
	                           {
	                               isResultComplete.set(true);
	                               System.out.println("Received End Event. Result is complete.");
	                           }
	                       }
	                       
	               );
	               System.out.println("final results line 126: "+ resultInputStream.read());
	               //copy(resultInputStream, fileOutputStream);
	           }

	           /*
	            * The End Event indicates all matching records have been transmitted.
	            * If the End Event is not received, the results may be incomplete.
	            */
	           if (!isResultComplete.get()) {
	               throw new Exception("S3 Select request was incomplete as End Event was not received."); 
	           }
	           return mv;
			
		}
		
		private static SelectObjectContentRequest generateBaseCSVRequest(String bucket, String key, String query) {
			 
	        SelectObjectContentRequest request = new SelectObjectContentRequest();
	        request.setBucketName(bucket);
	        request.setKey(key);
	        request.setExpression(query);
	        request.setExpressionType(ExpressionType.SQL);
	        
	        InputSerialization inputSerialization = new InputSerialization();
	        inputSerialization.setCsv(new CSVInput());
	        inputSerialization.setCompressionType(CompressionType.NONE);
	        request.setInputSerialization(inputSerialization);

	        OutputSerialization outputSerialization = new OutputSerialization();
	        //outputSerialization.setCsv(new CSVOutput());
	        outputSerialization.setJson(new JSONOutput());
	        request.setOutputSerialization(outputSerialization);
	        System.out.println(" still trying line 107");
	        return request;
	    }
		
		public String toString() {
			
			return "Query Controller class";
		}
		
}
