<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:mainLayout jsFiles="answerAddingViewScript.js">

    <layout:question question="${question}" individual="${true}" editing="${false}" username="${questionUsername}">

    </layout:question>

    <button id="#btn_add_answer" type="button" class="btn btn-dark">Добавить ответ</button>
    <div id="#form_add_answer" class="bg-dark bg-opacity-50 rounded-5 border-3 w-50 mx-auto border-dark p-2" style="display: none">
        <p>Написать ответ</p>
        <form method="post" class="border-1 border-dark p-2" action="${pageContext.request.contextPath}/questions/add_answer?questionId=${question.id}&userId=${userId}">
            <input type="text" class="w-25 mx-auto form-control" name="text" required placeholder="Текст ответа">
            <input title="submit" type="submit" class="btn btn-dark border-1 border-white m-2">
        </form>
    </div>
    <hr>

    <layout:answer answer="${bestAnswer}" editing="${false}" username="${usernames[loop.index]}"></layout:answer>
    <c:forEach items="${answersList}" var="answer" varStatus="loop">
        <c:if test="${!answer.best}">
            <layout:answer answer="${answer}" editing="${false}" username="${usernames[loop.index]}"></layout:answer>
        </c:if>
    </c:forEach>

</layout:mainLayout>
