<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:mainLayout jsFiles="titleInputWatcher.js">
    
    <layout:question question="${question}" individual="${true}" editing="${true}">

        <form method="post">
            <input type="text" class="w-25 mx-auto form-control" name="title" required placeholder="Новое название вопроса">
            <p id="#titleForInputWatcher" class="text-white bg-dark border-1 rounded-2 w-25 mx-auto border-white">0/20</p>
            <input type="text" class="w-25 mx-auto form-control" name="description" placeholder="Новое описание вопроса">
            <input title="submit" type="submit" class="m-2 btn btn-light border-dark border-1">
        </form>

    </layout:question>
    
</layout:mainLayout>
