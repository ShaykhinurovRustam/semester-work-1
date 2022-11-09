<%@tag pageEncoding="UTF-8" description="layout for profile page" %>
<%@attribute name="user" required="true" type="ru.kpfu.itis.java3.semesterwork1.entity.User" %>

<style>
    .img {
        display: block;
        width: 100px;
        height: 100px;
        border-radius: 50%;
        margin-left: auto;
        margin-right: auto;
        padding: 10px;
    }
</style>
<div class="w-50 mx-auto">
    <div class="card" style="border-radius: 15px;">
        <div class="card-body p-4">
            <div class="d-flex text-black">
                <div class="flex-grow-1 ms-3">
                    <img class="img" src="/images/${sessionScope.get("userId")}.png">
                    <a><form method="post">
                        <input type="text" id="avatar" name="avatar" placeholder="Вставьте URL">
                        <button type="submit" class="btn btn-light">Обновить аватар</button>
                    </form>
                    </a>
                    <h5 class="mb-1">${user.username}</h5>
                    <div class="d-flex justify-content-start rounded-3 p-2 mb-2"
                         style="background-color: #efefef;">
                        <div>
                            <p class="small text-muted mb-1">Рейтинг</p>
                            <p class="mb-0">${user.rating}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:doBody />
    </div>
</div>
