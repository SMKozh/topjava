<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<style>
  .redfont {
    color: red;
  }
  .greenfont {
    color: green;
  }
</style>
<html lang="ru">
<head>
  <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="meals?action=insert">Add Meal</a></p>
<table border="1" cellpadding="5"
       style="border-collapse: collapse; border:2px solid black;">
  <thead>
  <tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th></th>
    <th></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${meals}" var="meal">
    <tr class="${meal.excess ? 'redfont' : 'greenfont'}">
      <td><javatime:format pattern="yyyy-MM-dd HH:mm" value="${meal.dateTime}"/></td>
      <td>${meal.description}</td>
      <td>${meal.calories}</td>
      <td><a href="meals?action=edit&mealid=${meal.id}">Update</a></td>
      <td><a href="meals?action=delete&mealid=${meal.id}">Delete</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>