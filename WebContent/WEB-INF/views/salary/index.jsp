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
        <h2>【自分の月給　一覧】</h2>
        <table id="salary_list">
            <tbody>
                <tr>
                    <th class="salary_year_month">月</th>
                                        <th class="salary_days">日数(日)</th>
                                        <th class="salary_salary">月給(円)</th>
                                        <th class="salary_total_time">稼働(分)</th>
                                        <th class="salary_wage">時給(円)</th>
                </tr>
                                <c:forEach var="salary" items="${salary_list}" varStatus="status">
                    <tr class="row${status.count % 2}">
                                            <td class="salary_total_time">${salary.year_month}</td>
                                            <td class="salary_days">${salary.days }</td>
                        <td class="salary_salary">${salary.salary}</td>
                                                                    <td class="salary_total_time">${salary.total_time }</td>
                                                <td class="salary_salary"><c:out value="${sessionScope.login_employee.wage }"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:param>
</c:import>