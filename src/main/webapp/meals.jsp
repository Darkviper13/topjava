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
    function disp(form) {
        if (form.style.display == "none") {
            form.style.display = "block";
        } else {
            form.style.display = "none";
        }
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
                    <h2>Meals</h2>
                </div>
                <div class="col-sm-6">
                    <div class="search-box">
                        <div class="input-group">
                            <form id="search" name="search" method="post" action="meals?action=search">
                                <label for="date">
                                    <input type="date" id="date" name="date" class="form-control"
                                           placeholder="Search by date">
                                    <button class="input-group-addon" type="submit" form="search"><i
                                            class="material-icons">&#xE8B6;</i></button>
                                </label>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th style="width: 22%;">Local date time</th>
                <th style="width: 22%;">Description</th>
                <th>Calories</th>
                <th>Excess</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <form method="post" action="meals?action=save">
                    <div class="input-group">
                        <input style="width: 200px" class="form-control" type="datetime-local" name="datetime">
                        <input style="width: 150px" class="form-control" type="text" name="description" value="Description">
                        <input style="width: 100px" class="form-control" type="text" name="calories" value="Calories">
                        <input style="width: 125px;height: 34px" type="submit" value="Add new meal">
                    </div>
                </form>
            </tr>
            <c:forEach items="${mealsFromDataBase}" var="meal">
                <tr style="background-color: ${meal.excess ? 'lightcoral' : 'lightgreen'}">
                    <form method="post" action="meals?action=edit&value=${meal.id}">
                        <td>
                            <div id="div1${meal.id}">
                                <c:out value="${formatter.format(meal.dateTime)}"/>
                            </div>

                            <fieldset id="f1${meal.id}" style="display: none">
                                <label for="datetime">Local date time
                                    <input style="width: 200px" class="input-field" type="datetime-local" id="datetime"
                                           name="datetime" value="${meal.dateTime}">
                                </label>
                            </fieldset>
                        </td>
                        <td>
                            <div id="div2${meal.id}">
                                <c:out value="${meal.description}"/>
                            </div>
                            <fieldset id="f2${meal.id}" style="display: none">
                                <label for="description">Description
                                    <input style="width: 150px" class="text-input" type="text" id="description"
                                           name="description" value="${meal.description}">
                                </label>
                            </fieldset>
                        </td>
                        <td>
                            <div id="div3${meal.id}">
                                <c:out value="${meal.calories}"/>
                            </div>
                            <fieldset id="f3${meal.id}" style="display: none">
                                <label for="calories">Calories
                                    <input style="width: 100px" class="text-input" type="number" id="calories"
                                           name="calories" value="${meal.calories}">
                                </label>
                            </fieldset>
                        </td>
                        <td>
                            <c:out value="${meal.excess}"/>
                        </td>
                        <td>
                            <input id="editButton${meal.id}" type="button" title="Edit" onclick="
                                    disp(document.getElementById('div1${meal.id}'));
                                    disp(document.getElementById('div2${meal.id}'));
                                    disp(document.getElementById('div3${meal.id}'));
                                    disp(document.getElementById('f1${meal.id}'));
                                    disp(document.getElementById('f2${meal.id}'));
                                    disp(document.getElementById('f3${meal.id}'));
                                    disp(document.getElementById('editButton${meal.id}'));
                                    disp(document.getElementById('submitButton${meal.id}'))" value="Edit">
                            <fieldset id="submitButton${meal.id}" style="display: none">
                                <input type="submit" title="Edit" onclick="
                                        disp(document.getElementById('div1${meal.id}'));
                                        disp(document.getElementById('div2${meal.id}'));
                                        disp(document.getElementById('div3${meal.id}'));
                                        disp(document.getElementById('f1${meal.id}'));
                                        disp(document.getElementById('f2${meal.id}'));
                                        disp(document.getElementById('f3${meal.id}'));
                                        disp(document.getElementById('editButton${meal.id}'));
                                        disp(document.getElementById('submitButton${meal.id}'));"
                                       value="Edit">
                            </fieldset>
                    </form>
                    </td>
                    <td>
                        <form method="post" action="meals?action=delete&value=${meal.id}">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
