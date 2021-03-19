<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${card != null}">
                <h2>上司選択ページ</h2>
                <form method="POST" action="<c:url value='/card/end' />">
<label for="boss">上司を選択</label>
<br />
<select name="boss">
	<c:forEach var="relation" items="${relations}">
		<option value="${relation.boss.id}" <c:if test="${relation.boss.id==boss.id }"><c:out value="selected"/></c:if>><c:out value="${relation.boss.name}" />
	</c:forEach>
</select>
<br />
<br />

<label for="end">退勤時間</label>
<br />
<input type="time" name="end" value="${card.end}" />
<br />
<br />


<input type="hidden" name="_token" value="${_token}" />
<button type="submit">退勤</button>

                </form>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/index.html' />">一覧に戻る</a></p>
    </c:param>
</c:import>
