<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${meal.id == null ? 'Add meal' : 'Edit meal'}</h2>
<form method="post" action="meals">
    <input type="hidden" name="mealid" id="id" value="${meal.id}">
    <table width="100%" cellspacing="0" cellpadding="4">
        <tr>
            <td>DateTime : </td>
            <td><input type="datetime-local" required="required" name="datetime"
                value="${meal.dateTime}"/></td>
        </tr>
        <tr>
            <td width="150">Description : </td>
            <td><input type="text" required="required" name="description"
                    value="${meal.description}" /></td>
        </tr>
        <tr>
            <td>Calories : </td>
            <td><input type="number" required="required" step="1" name="calories"
                    value="${meal.calories}" /></td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Save" />
                <button type="button" onClick="location.href='meals'">Cancel</button>
            </td>
            <td>
            </td>
        </tr>
    </table>
</form>
</body>
</html>