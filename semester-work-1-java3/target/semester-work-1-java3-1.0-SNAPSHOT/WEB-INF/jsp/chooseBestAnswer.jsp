<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:mainLayout>

    <form method="post">

        <c:forEach items="${answersList}" var="answer">
            <layout:answer answer="${answer}" editing="${true}">
                <c:if test="${!answer.best}">
                    <br>
                    <span class="border-dark border-1">
                        Выбрать ответ
                        <input type="radio" value="${answer.id}" name="bestAnswer">
                    </span>
                </c:if>
            </layout:answer>
        </c:forEach>
        <input type="submit" title="choose" class="btn btn-dark">

    </form>

</layout:mainLayout>
