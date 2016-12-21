<%--
  User: stefan
  Date: 20/12/2016
  Time: 13:07
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home page</title>
</head>
<body>
    <form action="/launchScrape" method="post">
        <input type="text" name="url"/>
        <button type="submit">submit</button>
    </form>
    <br/>
    <br/>

    <a href="/getHistories">get histories</a>
    <br/>
    <br/>

    <form action="/getReviews" method="post">
        <input type="text" name="shId"/>
        <input type="text" name="pageNum"/>
        <button type="submit">submit</button>
    </form>
</body>
</html>
