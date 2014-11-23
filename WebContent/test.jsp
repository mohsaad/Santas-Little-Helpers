<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
HELLO WORLD <BR />
${test}
<form class="form-signin" role="form" method="POST" action="#">
	<button class="btn btn-lg btn-primary btn-success" type="submit">Add</button>
</form>
<form class="form-signin" role="form" method="POST" action="#">
	<input name="clear" type="hidden" value="yes"></input>
	<button class="btn btn-lg btn-primary btn-success" type="submit">clear</button>
</form>