<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:mainLayout>

    <layout:answer answer="${answer}" editing="${true}">
        <form method="post" class="border-1 border-dark p-2">
            <input type="text" name="newText" class="w-25 mx-auto form-control" placeholder="Новый текст" required>
            <input type="submit" class="btn btn-dark border-1 border-white">
        </form>
    </layout:answer>

</layout:mainLayout>
