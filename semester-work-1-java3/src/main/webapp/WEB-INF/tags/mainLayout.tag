<%@tag description="main layout for other pages" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<%@attribute name="jsFiles"%>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value="/static/css/styles.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.min.css"/>">
    <script defer src="<c:url value="/static/js/authViewScript.js"/>"></script>
    <c:forTokens items="${jsFiles}" delims="," var="file">
        <script defer src="<c:url value="/static/js/${file}" />"></script>
    </c:forTokens>
</head>
<body class="text-center">
<header class="p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 text-white">Главная</a></li>
                <c:if test="${empty userId}">
                    <li><a id="#link_auth" href="#" class="nav-link px-2 text-white">Войти</a></li>
                    <li><a href="${pageContext.request.contextPath}/registration" class="nav-link px-2 text-white">Регистрация</a></li>
                </c:if>
                <c:if test="${not empty userId}">
                    <li><a href="${pageContext.request.contextPath}/profile" class="nav-link px-2 text-white">Профиль</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="nav-link px-2 text-white">Выйти</a></li>
                    <li><a href="${pageContext.request.contextPath}/questions" class="nav-link px-2 text-white">Вопросы</a></li>
                </c:if>
                <c:if test="${role == \"admin\"}">
                    <li><a href="${pageContext.request.contextPath}/panel" class="nav-link px-2 text-white">Панель</a></li>
                </c:if>
                <li><a href="${pageContext.request.contextPath}/users" class="nav-link px-2 text-white">Рейтинг</a></li>
            </ul>
            <div class="text-end px-2">
                <p>
                    ${name}
                </p>
            </div>
        </div>
    </div>
</header>
<div class="b-example-divider"></div>
<div id="#auth_form" style="display: none">
    <layout:authForm />
</div>
<div id="#main" class="w-75 mx-auto mt-3">
    <jsp:doBody/>
</div>
</body>

</html>
