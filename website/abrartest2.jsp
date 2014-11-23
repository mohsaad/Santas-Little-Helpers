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
    <script src="./theme_files/ie-emulation-modes-warning.js"></script><style type="text/css"></style>
    <script src="main.js"></script>
  </head>

  <body role="document">
  
  <nav class="navbar navbar navbar-fixed-top" role="navigation">
      <div class="container">
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav pull-right">
            <p></p>
            <a href="demo?signout=true"><button class="btn btn-lg btn-primary btn-danger" type="logout">Logout</button> </a> 
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="jumbotron">
        <h1>Hello, ${user}!</h1>
    </div>
    <!-- -->

  </body>

</html>