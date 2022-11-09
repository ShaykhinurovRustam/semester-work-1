<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:mainLayout>
    <h1>Пользователи</h1>
    <hr>
    <c:forEach items="${userList}" var="item">
        <layout:profile user="${item}">
            <a href="${pageContext.request.contextPath}/panel/edit?id=${item.id}" class="btn btn-secondary">Редактировать рейтинг</a>
            <c:choose>
                <c:when test="${item.isBanned()}">
                    <a href="${pageContext.request.contextPath}/panel/unban?id=${item.id}" class="btn btn-secondary">Разблокировать пользователя</a>
                </c:when>
                <c:when test="${!item.isBanned()}">
                    <a href="${pageContext.request.contextPath}/panel/ban?id=${item.id}" class="btn btn-secondary">Заблокировать пользователя</a>
                </c:when>
            </c:choose>
        </layout:profile>
        <hr>
    </c:forEach>
</layout:mainLayout>
