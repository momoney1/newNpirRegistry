<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.diakite.QueryController" %>
    <%@page import= "java.lang.System" %>    



<!DOCTYPE html PUBLIC >
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
  <h1 class="center">Doctor Details</h1>
</header>


<% QueryController q = new QueryController(); %>

<table class="tab">
    
        <tr >
    
            <th>Provider Name</th>
    
            <th>City</th>
    
            <th>State</th>
    
            <th>Phone Number</th>
    
            <th>Npi</th>
    
        </tr>
       
    
        <tr  class="t1">
    
            <td id = "1"><%= request.getAttribute("result") %></td>
    
            <td id = "2"><%= request.getAttribute("city") %></td>
    
            <td id = "3"><%= request.getAttribute("state") %></td>
    
            <td id = "4"><%= request.getAttribute("phone") %></td>
    
            <td id = "5"><%= request.getAttribute("npi") %></td>
    
        </tr>
    
           <tr  class="t1">
    
           <td id = "6"></td>
    
           <td id = "7"></td>
    
           <td id = "8"></td>
    
           <td id = "9"></td>
    
           <td id = "10"></td>
    
        </tr>
        
        <tr class="t1">
    
            <td id = "11"></td>
    
            <td id = "12"></td>
    
            <td id = "13"></td>
    
            <td id = "14"></td>
    
            <td id = "15"></td>
    
    </tr>
    
    <tr class="t1">
    
            <td id = "16"></td>
    
            <td id = "17"></td>
    
            <td id = "18"></td>
    
            <td id = "19"></td>
    
            <td id = "20"></td>
    
    </tr>
    
    <tr class="t1">
    
            <td id = "21"></td>
    
            <td id = "22"></td>
    
            <td id = "23"></td>
    
            <td id = "24"></td>
    
            <td id = "25"></td>
    
    
    
    </tr>
    
    <tr class="t1">
    
            <td id = "26"></td>
    
            <td id = "27"></td>
    
            <td id = "28"></td>
    
            <td id = "29"></td>
    
            <td id = "30"></td>
    
    </tr>
    
    <tr class="t1">
    
            <td id = "31"></td>
    
            <td id = "32"></td>
    
            <td id = "33"></td>
    
            <td id = "34"></td>
    
            <td id = "35"></td>
    
    </tr>
    
           
    </table>
<style>
@import url('https://fonts.googleapis.com/css?family=Montserrat|Open+Sans|Roboto');
    *{
     margin:0;
     padding: 0;
     outline: 0;
    }
    .filter{
     position: absolute;
     left: 0;
     top: 0;
     bottom: 0;
     
     right: 0;
     z-index: 1;
    
    }
  
    h1{
    text-align: center;
    }
    table{
     position: absolute;
     z-index: 2;
     left: 62%;
     top: 60%;
     transform: translate(-70%,-50%);
     width: 60%; 
     border-collapse: collapse;
     border-spacing: 0;
     box-shadow: 0 2px 15px rgba(64,64,64,.7);
     border-radius: 12px 12px 0 0;
     overflow: hidden;
    
    }
    td , th{
     padding: 15px 20px;
     text-align: center;
     
    
    }
    th{
     background-color: #a8a4a8;
     color: #fafafa;
     font-family: 'Open Sans',Sans-serif;
     font-weight: 200;
     text-transform: uppercase;
    
    }
    tr{
     width: 100%;
     background-color: #fafafa;
     font-family: 'Montserrat', sans-serif;
    }
    tr:nth-child(even){
     background-color: #eeeeee;
    }
    
    .hover{
      background-color:rgb(109, 1, 1);
    }
    
    .hey:hover{
      background-color: rgb(190, 186, 186);
    }
    
    #btn{
      background-color: red;
    }
        
</style>
</body>
</html>