<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員　一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>トーク</th>
                </tr>
                <c:forEach var="message" items="${messages}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${message.code}" /></td>
                        <td><c:out value="${message.name}" /></td>
                        <td>
                                    <a href="<c:url value='/messages/show?id=${message.id}' />">トーク</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


    </c:param>
</c:import>