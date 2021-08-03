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


/**
 * Service class that acts as controller and handles client requests to the National Provider Identifier (NPI) records via API queries to
 * their API.  This class also fetches physician records stored in an Oracle Database schema or can optionally retrieve
 * same physician information stored in an AWS S3 bucket
 * @author Moe Diakite
 * 
 * 
 * * @param  url  an absolute URL giving the base location of the image
* @param  name the location of the image, relative to the url argument
* @return
 *
 
 */
@RestController //Annotation representing @Controller and @ResponseBody annotations.
public class QueryController {
	    
		private final String url="jdbc:oracle:thin:@acoracle.montgomerycollege.edu:15521:acoradb"; // jdbc connection url using oracle database
		private final String user= "cs270229ap";  //user credentials for database connection
		private final String password = "ap"; //user password
		
		
		/**
		 * 
		 * Method that is invoked when client attempts fetch request to NPPES external api for physician information 
		 * @param request
		 * @param response
		 * @return ModelAndView object
		 */
		@RequestMapping("/add")  //Annotation that maps this add method "add" url.
		//when user clicks submit button, this add method is invoked to handle the request.
	    public ModelAndView add(HttpServletRequest request, HttpServletResponse response)
	    {
			try { //required try  method to handle IO exceptions
				String fName = request.getParameter("field1").toString(); //fetching field1 (first name) input text via request object and transforming to string
				String lName = request.getParameter("field2").toString(); //last name text
				String city = request.getParameter("field3").toString(); //city address
				String homeState = request.getParameter("field4").toString(); //state address
				String inputJson = "";
				//"https://npiregistry.cms.hhs.gov/api?version=2.1&last_name="+ lName + "&first_name="+fName
				
				/*
				 * Forming of URL for fetching particular doctor information from NPI registry, by concatenating user input with API that allows access to NPI dat
				 */
				String u = "https://npiregistry.cms.hhs.gov/api?version=2.1&last_name="+ lName + "&first_name=" + fName + "&city=" + city + "&state=" + homeState;
				URL url = new URL(u); //to connect to the URL using java library
				HttpURLConnection con = (HttpURLConnection) url.openConnection(); //initialization of connection object
				con.setRequestMethod("GET"); //specifies that it is a Get request
				con.setRequestProperty("Content-Type", "application/json"); //returned data will be in form of JSON 
				con.setRequestProperty("Accept", "application/json");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //uses bufferedReader and inputstreamReader to read text from character inputstream
				JSONParser jParser = new JSONParser(); // initialize object to onvert to a JSON object to print data
				Object j = jParser.parse(in); //conversion of inputstream object to object
				JSONObject ezm = (JSONObject) j; //converT to JSONOBJECT
				JSONArray results = (JSONArray) ezm.get("results"); //convert JSONObject to JSONArray
				JSONObject myList = (JSONObject)results.get(0); //retrieves first element inside JSONArray object
				JSONArray addressArray = (JSONArray) myList.get("addresses"); //retrieves and stores into array all addresses used by this physician
				JSONArray identifierArray = (JSONArray) myList.get("identifiers"); //retrieves all indenfiers used by this phyisician
				JSONObject addressList1 = (JSONObject) addressArray.get(0); //retrieves the first address in the list and stores inside JSONObject
				
				JSONObject basicInfo = (JSONObject) myList.get("basic"); 
				
				//console outputs to help pinpoint exceptions and errors
				System.out.println(ezm.get("results") + " line 65");
				System.out.println(myList.get("basic") + "      this is results    line 66");
				System.out.println(addressList1.get("city")+ " here is json response line 67");
				System.out.println(addressList1.get("address_1")+ " here is json response line 68");
				System.out.println(addressList1.get("address_2")+ " here is json response line 70");
				System.out.println(addressList1.get("state")+ " here is json response line 71");
				System.out.println(addressList1.get("telephone_number")+ " here is json response line 67");
				
				//?version=2.1&last_name=${targetLname}&first_name=${targetFName}&state=${targetState}&city=${targetcity}&limit=25`
				//BodyContentHandler handler = new BodyContentHandler(); 
				
				
				File input = new File("C:\\Users\\moedi\\newEclipse-workspace\\newNpirRegistry\\src\\main\\webapp\\index.jsp"); 
			    Metadata metadata = new Metadata();
			    FileInputStream inputstream = new FileInputStream(input); //stores returned data into local file
				String name = response.getContentType();
				//System.out.println(input.getAbsolutePath() + "    this is the type of returned value    "+ fName + " "+ lName);
			    ModelAndView mv = new ModelAndView(); //used to return new view to browser
				mv.setViewName("DisplayNPI.jsp"); //view will be represented using the DisplayNPI jsp page
				mv.addObject("result",basicInfo.get("name"));
				mv.addObject("npi",myList.get("number"));
				mv.addObject("city",addressList1.get("city"));
				mv.addObject("state",addressList1.get("state"));
				mv.addObject("phone", addressList1.get("telephone_number"));
				
				return mv;
			}catch(Exception ex) {
				
			}
		return null;	//returns null in case of exception
	    }
		

		/**
		 * 
		 * Method that is invoked when client attempts to fetch physician data stored in an Oracle Database 
		 * @param request
		 * @param response
		 * @return ModelAndView object
		 */
		@RequestMapping("/database")
		public ModelAndView queryDatabase(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView modelV = new ModelAndView();
			String fName = request.getParameter("input1").toString();
			String lName = request.getParameter("input2").toString();
			String npiNumber = request.getParameter("input3").toString();
			String homeState = request.getParameter("input4").toString();
			fName = fName.toUpperCase(); //all names in the table are stored in capital letters. So to ensure the format in query request matches that of stored information
			lName = lName.toUpperCase();
			npiNumber = npiNumber.toUpperCase();
			homeState = homeState.toUpperCase();
			
			try {
				Class.forName("oracle.jdbc.OracleDriver"); //registering jdbc driver
				System.out.println("Driver is successfuly loaded!!");
				Connection con = DriverManager.getConnection(url, user, password); //to establish jdbc connection
				Statement stmt= con.createStatement();  //
				ResultSet rs= stmt.executeQuery("select NPI, FIRST_NAME, LAST_NAME, ADDRESS_STATE from NPIREGISTRY WHERE FIRST_NAME = '"+ fName + "' AND LAST_NAME = '"+ lName + "'");
				System.out.println("Connection is successful with  "+ con);
				int count = 1;
				String result, npi, city, state;
				//rs.next();
				while(rs.next()) { //loop to retrieve all returned records
					modelV.setViewName("DisplayOracle.jsp");
					modelV.addObject("result"+ count, rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
					modelV.addObject("npi" + count, rs.getString("NPI"));
					modelV.addObject("city", "null");
					modelV.addObject("state" + count, rs.getString("ADDRESS_STATE"));
					modelV.addObject("phone", "NA");  
					System.out.println(count + " counter value ");
					count++;
					if(count >= 5 ) { // loop maxes out at 5 iterations with 5 records 
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
				con.close();// closing jdbc connection rather than relying on garbage collector
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

		/**
		 * 
		 * Method invoked when user requests physician info stored in AWS S3 bucket
		 * @param request
		 * @param response
		 * @return ModelAndView object
		 */
		
		
		@SuppressWarnings("deprecation") //to ignore compiler error warnings
		@RequestMapping("/s3Select") //maps "s3Select" url to this method, which will handle the url request. this url is displayed when user clicks submit button in form field for s3 request
		public ModelAndView s3Select(HttpServletRequest request, HttpServletResponse response) throws Exception{
			final ModelAndView mv = new ModelAndView();
			//configuration of aws credentials to access s3 bucket
			 String npiBucket = "npiawsbucket";
			 String npiKey = "npidata_pfile_20050523-20210411.csv"; //key identifier for specific bucket
		     String npiDirectory = "C:\\Users\\moedi\\OneDrive\\Desktop\\s3Results.json";
		     String npiSearchQuery = "SELECT s._1, s._6, s._7, s._21, s._24 FROM S3Object s WHERE S._1 = '"+ request.getParameter("s31").toString()+"'"; //select query to s3 bucket
			
			 ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("Moe Diakite"); // This provider vends AWSCredentials from the profile configuration file for profile under Moe Diakite
		        
		        final   AmazonS3 s3Client = AmazonS3ClientBuilder.standard() //Create new instance of builder with profile credentials
		                .withCredentials(credentialsProvider)
		                .withRegion("us-east-1")
		                .build();
		        
		       final  FileWriter  writeToMoe = new FileWriter("C:\\Users\\moedi\\OneDrive\\Desktop\\s3Results.txt"); //writes returned query results to text file
		       
		        SelectObjectContentRequest queryRequest = generateBaseCSVRequest(npiBucket, npiKey, npiSearchQuery); //to filter out contents of amazonS3 object
		        final AtomicBoolean isResultComplete = new AtomicBoolean(false); //to track whether or not query to s3 returned successfully

			
			try {
				System.out.println("hello");
	            S3Object o = s3Client.getObject(npiBucket, npiKey); //Amzaons3 instance to retrieve s3 object
	            S3ObjectInputStream s3is = o.getObjectContent(); //returns the input stream containing the contents of this object.
	            FileOutputStream fos = new FileOutputStream(new File(npiDirectory)); //preparing to write npiDirectory file to json file
	            BufferedReader reader = new BufferedReader(new InputStreamReader(o.getObjectContent())); //To read file from aws s3
	            
	            String line;
	            int count = 0;
	            
	            while((line = reader.readLine()) != null && count < 10) { //loops through to read maximum 10 returned lines of text/records from bufferedreader
	            	System.out.println("content from line 69: "+ line); 
	            	count++; 
	            }
	             
			} catch(IOException e) {
				
			}
	        SelectObjectContentEvent.RecordsEvent recordEvent = new SelectObjectContentEvent.RecordsEvent(); //container for recordsEvent
	        SelectObjectContentEventStream rs;	        
	        try (OutputStream fileOutputStream = new FileOutputStream(new File (npiDirectory));
	               SelectObjectContentResult result = s3Client.selectObjectContent(queryRequest)) {
	               InputStream resultInputStream = result.getPayload().getRecordsInputStream( //getPayload method invoked to retrieve byte Array of records
	                       new SelectObjectContentEventVisitor() { //A stream of SelectObjectContentEvents, generated as the result of a call to AmazonS3.selectObjectContent(SelectObjectContentRequest). 
	                           @Override
	                           public void visit(SelectObjectContentEvent.RecordsEvent event) //must override default implementation
	                           {
	                        	   event.getPayload().flip(); //Gets the message payload as an XML source, and return buffer via flip method

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
		                               
		           					mv.setViewName("DisplayOracle.jsp"); //initialize modelView object with "DisplayOracle.jsp" to return to browswer with results
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
		/**
		 * To return filtered contents of Amazons3 object
		 * @param bucket
		 * @param key
		 * @param query
		 * @return SelectObjectContentRequest
		 */
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
