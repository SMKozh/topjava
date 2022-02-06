<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="post" action="meals">
    <input type="hidden" name="mealid" id="id" value="${meal.id}">
    <table width="100%" cellspacing="0" cellpadding="4">
        <tr>
            <td>DateTime : </td>
            <td><input type="datetime-local" name="datetime"
                value="<javatime:format pattern="yyyy-MM-dd'T'HH:mm" value="${meal.dateTime}" />" /></td>
        </tr>
        <tr>
            <td width="150">Description : </td>
            <td><input
                    type="text" name="description"
                    value="<c:out value="${meal.description}" />" /></td>
        </tr>
        <tr>
            <td>Calories : </td>
            <td><input
                    type="text"  name="calories"
                    value="<c:out value="${meal.calories}" />" /></td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Save" />
                <button type="button" onClick="location.href='meals?action=listMeal'">Cancel</button>
            </td>
            <td>
            </td>
        </tr>
    </table>
</form>
</body>
</html>