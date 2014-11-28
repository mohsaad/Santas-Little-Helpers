<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
  <head>

    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://getbootstrap.com/favicon.ico">

    <title>Theme Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Bootstrap theme -->
    <link href="http://getbootstrap.com/dist/css/bootstrap-theme.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="http://getbootstrap.com/examples/theme/theme.css" rel="stylesheet">
    <script src="js/theme_files/ie-emulation-modes-warning.js"></script><style type="text/css"></style>
    <script src="js/main.js"></script>
        <script src="js/snowstorm.js"></script>

    <script src="http://vestride.github.io/Shuffle/dist/jquery.shuffle.modernizr.js"></script>
  </head>

  <body role="document" style="background: #6b92b9; display: block;">
<script type="text/rocketscript" data-rocketsrc="js/snowstorm.js" data-rocketoptimized="true"></script>
  <div class="row">
    <div class="col-md-1 col-md-offset-0"></div>

     <div class="col-sm-3 person">
      <div class="list-group a">
        <h1>Personality</h1>
        <c:forEach var="trait" items="${traits}" varStatus="index">
           <a href="#" class="list-group-item ${index}"> ${trait} </a>
        </c:forEach>
      </div>
    </div>

      <div class="col-sm-3 person" >
        <div class="list-group b">
          <h1> Interests </h1>
          <c:forEach var="topic" items="${topics}" varStatus="index">
          	<a href="#" class="list-group-item ${index}">${topic}</a>
          </c:forEach>c:forEach>          
        </div>
      </div><!-- /.col-sm-4 -->
   
    <!-- Amazon items -->
      <div class="col-sm-4 c">
        <div class="list-group">
          <h1>Recommended Items</h1>
          
           <a href="#" class="list-group-item 37 active">
            <h4 class="list-group-item-heading">Item 1</h4>
            <p class="list-group-item-text">link 1</p>
          </a> <!-- link 1 -->
         
            <a href="#" class="list-group-item 08">
              <h4 class="list-group-item-heading">Item 2</h4>
              <p class="list-group-item-text">link 2</p>
            </a>
          
          <a href="#" class="list-group-item 06">
              <h4 class="list-group-item-heading">Item 3</h4>
              <p class="list-group-item-text">Link 3</p>
            </a>       
        </div>
      </div><!-- /.col-sm-4 -->

    </div>

  </body>

</html>