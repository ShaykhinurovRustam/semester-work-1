<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:mainLayout jsFiles="questionAddingViewScript.js,titleInputWatcher.js">

    <h1>Вопросы</h1>

    <button id="#btn_add_question" type="button" class="btn btn-secondary">Задать вопрос</button>
    <div id="#form_add_question" class="bg-dark rounded-5 bg-opacity-25 border-3 w-50 mx-auto border-dark p-2" style="display: none">
        <p>Задайте свой вопрос</p>
        <form method="post" action="${pageContext.request.contextPath}/questions/add_question">
            <input type="text" class="w-25 mx-auto form-control" name="title" required placeholder="Вопрос">
            <p id="#titleForInputWatcher" class="text-white bg-dark border-1 rounded-2 w-25 mx-auto border-white">0/20</p>
            <input type="text" class="w-25 mx-auto form-control" name="description" placeholder="Описание">
            <input title="submit" type="submit" class="m-2 btn btn-light border-dark border-1">
        </form>
    </div>

    <h4>Все вопросы</h4>
    <hr>

    <c:forEach items="${questionsList}" var="question" varStatus="loop">
        <layout:question question="${question}" individual="${false}" editing="${false}" username="${usernames[loop.index]}">
            
        </layout:question>
        <hr>
    </c:forEach>

</layout:mainLayout>
