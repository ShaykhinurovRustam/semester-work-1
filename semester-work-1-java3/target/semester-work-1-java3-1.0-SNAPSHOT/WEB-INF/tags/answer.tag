<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" description="answer layout" %>

<%@attribute name="answer" required="true" type="ru.kpfu.itis.java3.semesterwork1.entity.Answer" %>
<%@attribute name="editing" required="true" type="java.lang.Boolean" %>
<%@attribute name="username" type="java.lang.String" required="false" %>

<div class="container bg-light p-3 text-black rounded-5 border-5">

    <c:if test="${answer.best}">
        <p class="bg-success opacity-50 w-25 rounded-5 mx-auto">Лучший ответ</p>
    </c:if>
    <c:if test="${not empty editing}">
        <c:if test="${!editing}">
            <c:if test="${userId eq answer.userId}">
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/questions/answer_delete?answerId=${answer.id}">Удалить</a>
            </c:if>
            <c:if test="${userId eq answer.userId}">
                <a class="btn btn-warning" href="${pageContext.request.contextPath}/questions/answer_edit?answerId=${answer.id}">Редактировать</a>
            </c:if>
        </c:if>
    </c:if>
    <h2>${answer.text}</h2>
    <c:if test="${not empty username}">
        <p>Author: ${username}</p>
    </c:if>
    <small class="opacity-50">Likes: ${answer.likes}</small>
    <c:if test="${!editing}">
        <a href="${pageContext.request.contextPath}/questions/add_like?answerId=${answer.id}&questionId=${answer.question}" class="btn" style="color: #ff2400">Like</a>
    </c:if>
    <jsp:doBody />

</div>
<hr>
