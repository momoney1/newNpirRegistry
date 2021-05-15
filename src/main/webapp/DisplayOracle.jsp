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
    
            <td id = "1"><%= request.getAttribute("result1") %></td>
    
            <td id = "2"><%= request.getAttribute("city1") %></td>
    
            <td id = "3"><%= request.getAttribute("state1") %></td>
    
            <td id = "4"><%= request.getAttribute("phone1") %></td>
    
            <td id = "5"><%= request.getAttribute("npi1") %></td>
    
        </tr>
    
           <tr  class="t1">
    
            <td id = "6"><%= request.getAttribute("result2") %></td>
    
            <td id = "7"><%= request.getAttribute("city2") %></td>
    
            <td id = "8"><%= request.getAttribute("state2") %></td>
    
            <td id = "9"><%= request.getAttribute("phone2") %></td>
    
            <td id = "10"><%= request.getAttribute("npi2") %></td>
    
        </tr>
        
        <tr class="t1">
    
            <td id = "11"><%= request.getAttribute("result3") %></td>
    
            <td id = "12"><%= request.getAttribute("city3") %></td>
    
            <td id = "13"><%= request.getAttribute("state3") %></td>
    
            <td id = "14"><%= request.getAttribute("phone3") %></td>
    
            <td id = "15"><%= request.getAttribute("npi3") %></td>
    
    </tr>
    
    <tr class="t1">
    
            <td id = "16"><%= request.getAttribute("result4") %></td>
    
            <td id = "17"><%= request.getAttribute("city4") %></td>
    
            <td id = "18"><%= request.getAttribute("state4") %></td>
    
            <td id = "19"><%= request.getAttribute("phone4") %></td>
    
            <td id = "20"><%= request.getAttribute("npi4") %></td>
    
    </tr>
    
    <tr class="t1">
    
            <td id = "21"><%= request.getAttribute("result5") %></td>
    
            <td id = "22"><%= request.getAttribute("city5") %></td>
    
            <td id = "23"><%= request.getAttribute("state5") %></td>
    
            <td id = "24"><%= request.getAttribute("phone5") %></td>
    
            <td id = "25"><%= request.getAttribute("npi5") %></td>
    
    
    
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