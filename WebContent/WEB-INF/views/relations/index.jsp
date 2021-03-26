<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>自分の上司　一覧</h2>
        <table id="relation_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                </tr>
                <c:forEach var="relation" items="${relations}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${relation.boss.code}" /></td>
                        <td><c:out value="${relation.boss.name}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${relations_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((relations_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/relations/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/relations/new' />">新規上司の登録</a></p>

    </c:param>
</c:import>