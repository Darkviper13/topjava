<%--
  Created by IntelliJ IDEA.
  User: кр
  Date: 06.10.2019
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    function disp(id) {
        var htmlElements = document.getElementsByName('togglefield' + id);
        htmlElements.forEach(function (value) {
            value.style.display == "none" ?
                value.style.display = "block" :
                value.style.display = "none"
        })
    }
</script>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Meals</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <a style="font-size: 20px; color: #fff" href="index.html"><--Main</a>
                    <h2>Meals</h2>
                </div>
            </div>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th style="width: 22%;">Local date time</th>
                <th style="width: 22%;">Description</th>
                <th style="width: 22%;">Calories</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <form method="post" action="meals?action=save">
                    <div class="input-group">
                        <input style="width: 200px" class="form-control" type="datetime-local" name="datetime">
                        <input style="width: 150px" class="form-control" type="text" name="description"
                               value="Description">
                        <input style="width: 100px" class="form-control" type="text" name="calories" value="Calories">
                        <input style="width: 125px;height: 34px" type="submit" value="Add new meal">
                    </div>
                </form>
            </tr>
            <c:forEach items="${mealsFromDataBase}" var="meal">
                <tr style="background-color: ${meal.excess ? 'lightcoral' : 'lightgreen'}">
                    <c:set var="id" value="${meal.id}"/>
                    <form method="post" action="meals?action=edit&value=${meal.id}">
                        <td>
                            <div name="togglefield${id}">
                                    ${formatter.format(meal.dateTime)}
                            </div>

                            <fieldset style="display: none" name="togglefield${id}">
                                <label for="datetime">Local date time
                                    <input style="width: 200px" class="input-field" type="datetime-local" id="datetime"
                                           name="datetime" value="${meal.dateTime}">
                                </label>
                            </fieldset>
                        </td>
                        <td>
                            <div name="togglefield${id}">
                                    ${meal.description}
                            </div>
                            <fieldset style="display: none" name="togglefield${id}">
                                <label for="description">Description
                                    <input style="width: 150px" class="text-input" type="text" id="description"
                                           name="description" value="${meal.description}">
                                </label>
                            </fieldset>
                        </td>
                        <td>
                            <div name="togglefield${id}">
                                    ${meal.calories}
                            </div>
                            <fieldset style="display: none" name="togglefield${id}">
                                <label for="calories">Calories
                                    <input style="width: 170px" class="text-input" type="number" id="calories"
                                           name="calories" value="${meal.calories}">
                                </label>
                            </fieldset>
                        </td>
                        <td>
                            <input type="button" title="Edit" onclick="disp(${id})" value="Edit"
                                   name="togglefield${id}">
                            <fieldset style="display: none" name="togglefield${id}">
                                <input type="submit" title="Edit" onclick="disp(${id})" value="Edit">
                            </fieldset>
                    </form>
                    </td>
                    <td>
                        <button onclick="location.href='meals?action=delete&value=${id}'">Delete</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>