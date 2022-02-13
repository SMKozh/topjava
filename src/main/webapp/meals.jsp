<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <form method="get" action="meals?action=filter">
        <input type="hidden" name="action" value="filter">
        <table>
            <tr>
                <th width="200">От даты (включая)</th>
                <th width="200">До даты (включая)</th>
                <th width="200">От времени (включая)</th>
                <th width="200">До времени (исключая)</th>
            </tr>
            <tr>
                <th><input type="date" value="${meal.dateTime}" name="startDate"></th>
                <th><input type="date" value="${meal.dateTime}" name="endDate"></th>
                <th><input type="time" value="${meal.dateTime}" name="startTime"></th>
                <th><input type="time" value="${meal.dateTime}" name="endTime"></th>
            </tr>
            <tr>
                <th></th>
                <th></th>
                <th></th>
                <th>
                    <button type="submit">Отфильтровать</button>
                </th>
            </tr>
        </table>
    </form>
</section>
</body>
</html>