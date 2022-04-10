<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form:form method="post" action="${pageContext.request.contextPath}/process" modelAttribute="pet">
    <div>
        <label for="name">이름</label>
        <form:input id="name" path="name" maxlength="10"/>
    </div>
    <div>
        <label for="owner">소유주</label>
        <form:input id="owner" path="owner" maxlength="20"/>
    </div>
    <div>
        <label for="species">종</label>
        <form:input id="species" path="species" maxlength="30"/>
    </div>
    <div>
        <form:radiobutton id="sex_m" path="sex" value="M"/>
        <label for="sex_m">수컷</label>
        <form:radiobutton id="sex_f" path="sex" value="F"/>
        <label for="sex_f">암컷</label>
    </div>
    <div>
        <label for="birth">생일</label>
        <form:input id="birth" path="birth"/>
    </div>
    <div>
        <button type="submit">등록</button>
    </div>
</form:form>

<script>
const pet = {"name":"Emma","owner":"Carl Ballard","species":"개","gender":"F","birth":"2009-01-31","death":null};

function reqListener () {
    console.log(JSON.parse(this.response));
}

const origin = location.origin;
let contextPath = origin + "${pageContext.request.contextPath}";
let contextLength = contextPath.length;
if(contextPath.charAt(contextLength - 1) === "/")
    contextPath = contextPath.substring(0, contextLength - 1);

const oReq = new XMLHttpRequest();
oReq.addEventListener("load", reqListener);
oReq.open("POST", contextPath + "/body");
oReq.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
oReq.send(JSON.stringify(pet));

const xhr = new XMLHttpRequest();
xhr.addEventListener("load", reqListener);
xhr.open("GET", contextPath + "/model?name=콜라&owner=김덕흥&species=개&gender=F&birth=2004-09-07&death=2020-11-02");
xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
xhr.send();
</script>
</body>
</html>