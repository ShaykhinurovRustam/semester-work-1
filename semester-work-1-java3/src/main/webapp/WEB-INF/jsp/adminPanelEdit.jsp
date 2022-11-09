<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:mainLayout>
    <h1>Редактирование рейтинга</h1>
    <layout:profile user="${user}">
        <form action="${pageContext.request.contextPath}/panel/edit?id=${user.id}" method="post">
            <input type="text" class="w-25 mx-auto form-control" name="newRating" required placeholder="Запишите новый рейтинг">
            <input type="submit" class="btn border-1 border-dark" name="submit">
        </form>
    </layout:profile>

</layout:mainLayout>
