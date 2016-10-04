<%@ page import="com.example.component.Console" trimDirectiveWhitespaces="true" %>
<% Console.println("Called timeout.jsp"); %>
<% Console.println(request.getDispatcherType()); %>

<html>
<body>
<h2>Timeout!</h2>
<p><a href="${pageContext.request.contextPath}/">Go to Top</a></p>
</body>
</html>