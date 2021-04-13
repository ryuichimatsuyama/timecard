<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="message">メッセージ</label><br />
<textarea name="message" rows="10" cols="50">${board.message}</textarea>
<br /><br />
<label for="image">写真</label><br />
    <input type="file" name="file" value="${board.file }" />
    <br/>
<br/>



<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>
