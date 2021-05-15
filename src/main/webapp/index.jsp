<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.Date" %>
     <%@page import="com.diakite.QueryController" %>
<html>
<head>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>
<meta charset="ISO-8859-1">
<title>Doctor Details</title>
</head>
<body>
       
 <header>
 
  <h1>Welcome to Moe's doctor search registry</h1>
</header>

<h2 style="text-align:center"> Retrieve from the NPPES api</h2>

 <form action="add" class = "submitForm">
	<ul class="form-style-1">
            <li>
             <input type="text" id="first" name="field1" class="field-divided" placeholder="First" />
             <input type="text" id="last" name="field2" class="field-divided" placeholder="Last" />
            </li>
             
            <li>          
            
                <input type="text" id="email" name="field3" class="field-divided" placeholder="city"  />
                <input type="text" id="question" name="field4" class="field-divided" placeholder="state" />
            </li>
           
         
            <li>
                <input type="submit" id="button" value="Submit" />
            </li>
        </ul>
         <div class= "mainBody">
</form>


<h2 style="text-align:center"> Retrieve from Moe's database</h2>

<form action="data
base" class = "submitForm">
	<ul class="form-style-1">
            <li>
             <input type="text" id="first_name" name="input1" class="field-divided" placeholder="First" />
             <input type="text" id="last_name" name="input2" class="field-divided" placeholder="Last" />
            </li>
             
            <li>          
                <input type="text" id="npi" name="input3" class="field-divided" placeholder="npi"  />
                <input type="text" id="state_name" name="input4" class="field-divided" placeholder="state" />
            </li>
           
         
            <li>
                <input type="submit" id="button" value="Submit" />
            </li>
        </ul>
         <div class= "mainBody">
</form>

<h2 style="text-align:center"> Retrieve from Moe's cloud bucket</h2>

<form action="s3Select" class = "submitForm">
	<ul class="form-style-1">
            <li>
             <input type="text" id="first" name="s31" class="field-divided" placeholder="NPI" />
             <input type="text" id="last" name="s32" class="field-divided" placeholder="name" />
            </li>
             
         
            <li>
                <input type="submit" id="button" value="Submit" />
            </li>
            
        </ul>
         <div class= "mainBody">
</form>
<style>
@import url('https://fonts.googleapis.com/css?family=Montserrat|Open+Sans|Roboto');
	h1{
	text-align: center;
	}
	form{
	text-align: center;
	}
	 <style type="text/css">
        .form-style-1 {
            margin:15px auto;
            max-width: 400px;
            padding: 20px 12px 10px 20px;
            font: 13px "Lucida Sans Unicode", "Lucida Grande", sans-serif;
        
        }
        .form-style-1 li {
            padding: 0;
            display: block;
            list-style: none;
            margin: 20px 3px 0 0;
          
        }
        .form-style-1 label{
            margin:0 0 3px 0;
            padding:0px;
            display:block;
            font-weight: bold;
        }
        .form-style-1 input[type=text], 
        .form-style-1 input[type=date],
        .form-style-1 input[type=datetime],
        .form-style-1 input[type=number],
        .form-style-1 input[type=search],
        .form-style-1 input[type=time],
        .form-style-1 input[type=url],
        .form-style-1 input[type=email],
        textarea, 
        select{
            border-radius:20px;
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            border:1px solid #BEBEBE;
            padding: 10px;
            margin:0px;
            -webkit-transition: all 0.30s ease-in-out;
            -moz-transition: all 0.30s ease-in-out;
            -ms-transition: all 0.30s ease-in-out;
            -o-transition: all 0.30s ease-in-out;
            outline: none;	
        }
        .form-style-1 input[type=text]:focus, 
        .form-style-1 input[type=date]:focus,
        .form-style-1 input[type=datetime]:focus,
        .form-style-1 input[type=number]:focus,
        .form-style-1 input[type=search]:focus,
        .form-style-1 input[type=time]:focus,
        .form-style-1 input[type=url]:focus,
        .form-style-1 input[type=email]:focus,
        .form-style-1 textarea:focus, 
        .form-style-1 select:focus{
            -moz-box-shadow: 0 0 8px #88D5E9;
            -webkit-box-shadow: 0 0 8px #88D5E9;
            box-shadow: 0 0 8px #88D5E9;
            border: 1px solid #88D5E9;
         
        }
        .form-style-1 .field-divided{
            width: 29%;
            margin: 0 2rem 0 2rem;
            padding:2em
        }
        
        .form-style-1 .field-long{
            width: 40%;
        }
        .form-style-1 .field-select{
            width: 40%;
        }
        .form-style-1 .field-textarea{
            height: 100px;
        }
        .form-style-1 input[type=submit], .form-style-1 input[type=button]{
            background: #0e1f33;
            padding: 12px 15px 12px 15px;
            border: none;
            color: #fff;
          width: 30%
          border-radius:20px;
        }
        .form-style-1 input[type=submit]:hover, .form-style-1 input[type=button]:hover{
            background: #fff;
            box-shadow:none;
            -moz-box-shadow:none;
            -webkit-box-shadow:none;
            border:1px solid black;
            color:black;
        }
        .form-style-1 .required{
            color:red;
        }
  
    
    .inputField{
      position: absolute;
      top: 10%;
    }
    .red{
      align-items: left;
      position: relative;
      top: -20%;
      padding: 0.5% 1.5% 0.5% 1.5%;
    }
    .submitForm{
    position: relative;
    left: 30px;
    top: 10px;
    pad
</style>
    </body>
    
    
    </html>