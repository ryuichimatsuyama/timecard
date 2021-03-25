<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app.jsp">
	<c:param name="content">
		<h2>上司 新規登録ページ</h2>
		<form method="POST" action="<c:url value='/relations/create'/>">
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
	<c:forEach var="employee" items="${employees}">
		<option value="${employee.id}"><c:out value="${employee.name}" /></option>
	</c:forEach>
</select>
<br />
<br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>
		</form>
		<p>
			<a href="<c:url value='/relations/index'/>">一覧に戻る</a>
		</p>
	</c:param>
</c:import>