<%@tag pageEncoding="UTF-8" description="form for authentication" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="w-25 mx-auto">
    <form action="${pageContext.request.contextPath}/auth" method="post">
        <h1 class="h3 mb-3 fw-normal">Войти в профиль</h1>

        <div>
            <input name="username" required type="text" placeholder="Имя" class="form-control" id="floatingInput">
            <div class="form-text">Первый раз? <a href="${pageContext.request.contextPath}/registration">Зарегестрироваться</a></div>
        </div>
        <div>
            <input name="password" required type="password" placeholder="Пароль" class="form-control" id="floatingPassword">
        </div>
        <button class="btn m-1 btn-lg btn-dark" type="submit">Войти</button><br>
        <button class="btn m-1 btn-lg btn-light border-1 border-dark" id="#btn_close" type="button">Закрыть</button>
    </form>
</div>
