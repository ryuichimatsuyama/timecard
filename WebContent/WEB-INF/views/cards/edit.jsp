<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
		<h2>承認と編集ページ</h2>

		<form method="POST" action="<c:url value='/approvals/create'/>">
<c:if test="${errors!=null }">
	<div id="flush_error">
		入力内容にエラーがあります。<br />
		<c:forEach var="error" items="${errors }">
・<c:out value="${error }" />
			<br />
		</c:forEach>
	</div>
</c:if>
<label for="start_time">出勤時間</label>
<br />
<input type="time" name="start_time" value="${card.start}" />
<br />
<br />
<label for="end_time">退勤時間</label>
<br />
<input type="time" name="end_time" value="${card.end }" />
<br />
<br />




<input type="hidden" name="_token" value="${_token}" />
<button type="submit">承認</button>
		</form>

		<p>
			<a href="<c:url value='/approvals/index'/>">一覧に戻る</a>
		</p>
	</c:param>
</c:import>
