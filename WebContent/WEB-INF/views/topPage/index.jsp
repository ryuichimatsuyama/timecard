<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
            <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2>勤怠管理システムへようこそ</h2>
                <h3>【自分の勤怠　一覧】</h3>
        <table id="card_list">
            <tbody>
                <tr>
                    <th class="card_name">氏名</th>
                    <th class="card_date">日付</th>
                    <th class="card_start">出勤</th>
                    <th class="card_end">退勤</th>
                    <th class="card_break">休憩</th>
                    <th class="card_work_minutes">合計</th>

                    <th class="card_action">操作</th>
                </tr>
                <c:forEach var="card" items="${cards}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="card_name"><c:out value="${card.employee.name}" /></td>
                        <td class="card_date"><fmt:formatDate value='${card.work_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="card_start">${card.start}</td>
                        <td class="card_end">${card.end}</td>
                        <td class="card_break">${card.employee.break_time }</td>
                        <td class="card_work_minutes">${card.work_minutes}</td>

<c:choose>
<c:when test="${empty card.end }">
                        <td class="card_action"><a href="<c:url value='/card/boss?id=${card.id}' />">退勤</a></td>
                        </c:when>
                        <c:otherwise>
                                                <td class="card_action"><a href="<c:url value='/card/edit?id=${card.id}' />">編集</a></td>

                        </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${cards_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((cards_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <p><a href="<c:url value='/cards/create' />">出勤</a></p>
    </c:param>
</c:import>
