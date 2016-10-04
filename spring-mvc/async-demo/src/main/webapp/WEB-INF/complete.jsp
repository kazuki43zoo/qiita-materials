<%@ page import="com.example.component.Console" %>
<% Console.println("Called complete.jsp"); %>
<% Console.println(request.getDispatcherType()); %>

<html>
<body>
<h2>Processing is complete !</h2>
<p>Accept timestamp is ${acceptedTime}</p>
<p>Complete timestamp is ${completedTime}</p>
<p><a href="${pageContext.request.contextPath}/">Go to Top</a></p>
</body>
</html>