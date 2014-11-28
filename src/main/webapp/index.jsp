<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>User Modeling Java Starter Application</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/watson-bootstrap-dark.css">
<link rel="stylesheet" href="css/browser-compatibility.css">
<link rel="stylesheet" href="css/watson-code.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<body>
<div class="container">
	<div class="header row">
		<div class="col-lg-3">
			<img src="images/app.png">
		</div>
		<div class="col-lg-8">
			<h2>User Modeling Java Starter Application</h2>
			<p>The User Modeling service uses linguistic analytics to
				extract a spectrum of cognitive and social characteristics from the
				text data that a person generates through text messages, tweets,
				posts, and more.</p>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-6">
			<h2>Try the service</h2>
			<div class="well" style="height: 400px; overflow: auto;" >
				<form method="post" class="form-horizontal"  action="demo">
					<fieldset>
						<div class="form-group row">
							<div class="col-lg-12">
								<textarea id="textArea" name="content" rows="16"
									placeholder="Please enter the text to analyze (minimum of 100 words)..."
									required class="form-control">${content}</textarea>
							</div>
						</div>
						<div style="margin-bottom: 0px; padding-top: 5px;"
							class="form-group row">
							<div class="col-lg-4 col-lg-push-4">
								<button type="button" onclick="window.location = './?reset=true'" class="btn btn-block">Clear</button>
							</div>
							<div class="col-lg-4 col-lg-push-4">
								<button type="submit" class="btn btn-block">Analyze</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		<div class="col-lg-6">
			<h2>Output</h2>
			<div class="row">
				<div class="col-lg-12">
					<c:if test="${not empty traits}">
						<div class="well" style="height: 400px; overflow: auto;" >
							<c:forEach var="trait" items="${traits}">
							<div class="row <c:if test='${not empty trait.title }'>flag</c:if> ">
					        	<div class="col-lg-10">
									<span>${trait.id}</span>
								</div>
					        	<div class="col-lg-2">
									<span>${trait.value}</span>
								</div>
					        </div>
					        </c:forEach>
						</div>
					</c:if>
					<c:if test="${not empty error}">
						<div class="well">
							<p style="font-weight:bold;color:red;">Error: ${error}</p>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${not empty viz}">
		<div class="row">
			<div class="col-lg-12">
				<h2>Visualization</h2>
				${viz}
			</div>
		</div>
	</c:if>
</div>
<script type="text/javascript" src="js/css_browser_selector.js"></script>
<script type="text/javascript" src="js/dummy-text.js"></script>
<c:if test="${(empty content) && (empty param.reset) }">
<script type="text/javascript">
	window.onload = function() {
		document.getElementById('textArea').value = dummy_text;
	}
</script>
</c:if>
</body>
</html>