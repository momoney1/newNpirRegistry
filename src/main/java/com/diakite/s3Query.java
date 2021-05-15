package com.diakite;
import java.sql.*;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import static com.amazonaws.util.IOUtils.copy;
import java.io.IOException;

public class s3Query {
	private static String npiBucket = "npiawsbucket";
	private static String npiKey = "npidata_pfile_20050523-20210411.csv";
    private static String npiDirectory = "C:\\Users\\moedi\\OneDrive\\Desktop\\s3Results.json";
    private static String npiSearchQuery = "SELECT s._1, s._6, s._7, s._21, s._24 FROM S3Object s WHERE S._1 = '1285687228'";
    
    @SuppressWarnings("deprecation")
	public static void main(String[] args)throws Exception {
		 ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("Moe Diakite");
	        
	        final   AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                .withCredentials(credentialsProvider)
	                .withRegion("us-east-1")
	                .build();
	        
	       final  FileWriter  writeToMoe = new FileWriter("C:\\Users\\moedi\\OneDrive\\Desktop\\s3Results.txt");
	       
	        SelectObjectContentRequest request = generateBaseCSVRequest(npiBucket, npiKey, npiSearchQuery);
	        final AtomicBoolean isResultComplete = new AtomicBoolean(false); 
	        
	        try {
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
	            
	            byte[] read_buf = new byte[1024];
	            SelectObjectContentResult r = s3Client.selectObjectContent(request);

	            SelectObjectContentEvent.RecordsEvent records = new SelectObjectContentEvent.RecordsEvent();
	            r.getPayload().getRecordsInputStream();
	            System.out.println("meta data from line 63 s3is: "+ s3is.getHttpRequest());
	            System.out.println("Object content from line 64 is: "+ s3Client.listBuckets().toString());

	            int read_len = 0;

	            
	            s3is.close(); 
	            fos.close(); 
	        } catch (AmazonServiceException e) {
	            System.err.println(e.getErrorMessage());  
	            System.exit(1);
	        } catch (FileNotFoundException e) {
	            System.err.println(e.getMessage());
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println(e.getMessage());
	            System.exit(1);
	        }	
	        
	        SelectObjectContentEvent.RecordsEvent recordEvent = new SelectObjectContentEvent.RecordsEvent();
	        SelectObjectContentEventStream rs;	        
	        try (OutputStream fileOutputStream = new FileOutputStream(new File (npiDirectory));
	               SelectObjectContentResult result = s3Client.selectObjectContent(request)) {
	               InputStream resultInputStream = result.getPayload().getRecordsInputStream(
	                       new SelectObjectContentEventVisitor() {
	                           @Override
	                           public void visit(SelectObjectContentEvent.RecordsEvent event)
	                           {
	                        	   event.getPayload().flip();

	                        	   try {
	                        		   event.getPayload().flip();
		                        	   String val = new String(event.getPayload().array(),"UTF-8");
		                               System.out.println("Received : " + val);
		                               writeToMoe.write(val);
		                               writeToMoe.close();
		                               System.out.println("message written to file ");

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

}
