<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="../layout/app.jsp">
    <c:param name="content">
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

                                <h2>                         <c:out value="${name.name}" />
                                 </h2>

            <form method="POST" action="<c:url value='/messages/create'/>">
<textarea name="message" rows="10" cols="50">${message.message}</textarea>

<br />
<br />
<input type="hidden" name="_token" value="${_token}" />
<button type="submit">送信</button>

        </form>
                        <c:forEach var="message" items="${messages}" varStatus="status">
                        <c:choose>
                                <c:when test="${ message.send.name==sessionScope.login_employee.name}">
                        <div align="right">
                       <b> <c:out value="${message.send.name}" /></b>
                        <fmt:formatDate value="${message.created_at}" pattern="yyyy-MM-dd HH:mm"/>
                        <p><c:out value="${message.message}" /></p>
</div></c:when><c:otherwise>                        <div align="left">
                        <b><c:out value="${message.send.name}" /></b>
                        <fmt:formatDate value="${message.created_at}" pattern="yyyy-MM-dd HH:mm"/>
                        <p><c:out value="${message.message}" /></p></div>
</c:otherwise></c:choose>
                </c:forEach>


    </c:param>
</c:import>