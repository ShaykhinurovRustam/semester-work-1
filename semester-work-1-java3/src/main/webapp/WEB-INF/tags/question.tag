<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" description="layout for 1 question" %>

<%@attribute name="question" required="true" type="ru.kpfu.itis.java3.semesterwork1.entity.Question" %>
<%@attribute name="individual" required="true" type="java.lang.Boolean" %>
<%@attribute name="editing" required="true" type="java.lang.Boolean" %>
<%@attribute name="username" type="java.lang.String" required="false" %>

<div class="p-4 mb-2 text-white rounded-5 bg-dark">
    <c:if test="${!editing}">
        <c:if test="${userId eq question.userId}">
            <a class="btn btn-danger" href="${pageContext.request.contextPath}/questions/question_delete?questionId=${question.id}">Удалить</a>
            <a class="btn btn-warning" href="${pageContext.request.contextPath}/questions/question_edit?questionId=${question.id}">Редактировать</a>
        </c:if>
    </c:if>
    <div>
        <h1 class="display-4 fst-italic">${question.title}</h1>
        <p class="lead my-3">${question.description}</p>
        <c:if test="${not empty username}">
            <p>Автор: ${username}</p>
        </c:if>
        <c:if test="${not individual}">
            <p class="lead mb-0"><a href="${pageContext.request.contextPath}/questions/question?id=${question.id}" class="btn border-1 border-white text-white fw-bold">Открыть</a></p>
        </c:if>
        <c:if test="${individual and !editing and userId eq question.userId}">
            <a class="btn btn-light" href="${pageContext.request.contextPath}/questions/choose_best?questionId=${question.id}">Выбрать лучший ответ</a>
        </c:if>

    </div>
    <jsp:doBody />
</div>
