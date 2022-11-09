<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<layout:mainLayout>

    <div class="alert alert-danger" role="alert">
        <c:if test="${not empty errorText}">
            ${errorText}
        </c:if>
    </div>

</layout:mainLayout>
