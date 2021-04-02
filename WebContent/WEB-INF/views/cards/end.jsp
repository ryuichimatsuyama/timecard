<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
                <h2>上司選択ページ</h2>
                <form method="POST" action="<c:url value='/cards/create' />">
<c:if test="${errors!=null }">
	<div id="flush_error">
		入力内容にエラーがあります。<br />
		<c:forEach var="error" items="${errors }">
・<c:out value="${error }" />
			<br />
		</c:forEach>
	</div>
</c:if>

<label for="boss">上司を選択</label>
<br />
<select name="boss">
	<c:forEach var="relation" items="${relations}">
		<option value="${relation.boss.id}" <c:if test="${relation.boss.id==boss.id }"><c:out value="selected"/></c:if>><c:out value="${relation.boss.name}" />
	</c:forEach>
</select>
<br />
<br />

<label for="start">出勤時間</label>
<br />
<input type="time" name="start" value="${card.start}" />
<br />
<br />


<input type="hidden" name="_token" value="${_token}" />
<c:if test="${!empty relations }">
<button type="submit">出勤</button>
</c:if>
                </form>

        <p><a href="<c:url value='/index.html' />">一覧に戻る</a></p>
    </c:param>
</c:import>
