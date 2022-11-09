<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:mainLayout>

    <h1>Registration page</h1>

    <div>
        <form action="${pageContext.request.contextPath}/registration" method="post">
            <div class="mb-3">
                <label for="inputUsername" class="form-label">Имя</label>
                <input type="text" name="username" class="form-control" id="inputUsername" required>
                <div class="form-text">Имя должно быть уникальным</div>
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">Пароль</label>
                <input type="password" name="password" class="form-control" required id="exampleInputPassword1">
            </div>
            <button type="submit" class="btn btn-dark">Зарегестрироваться</button>
        </form>
    </div>
</layout:mainLayout>
