<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

                                <h2>                         掲示板一覧
                                 </h2>
        <p><a href="<c:url value='/boards/new' />">投稿</a></p>

                        <c:forEach var="board" items="${boards}" varStatus="status">
                        <div style="padding: 10px; margin-bottom: 10px; border: 1px solid #333333;">

                      <p> <b> <c:out value="${board.employee.name}" /></b></p>
                        <fmt:formatDate value="${board.created_at}" pattern="yyyy-MM-dd HH:mm"/>
                        <p><c:out value="${board.message}" /></p>
                        <c:if test="${!empty board.file }">
                                                <p>
                                                                                <c:choose>
                                    <c:when test="${board.file != null}">
                                        <img src="https://ryuichilaos.s3-ap-northeast-1.amazonaws.com/images/${board.file}"
                                            style="width: 30%">
                                    </c:when>
                                    <c:otherwise>
                                    画像はありません。
                                    </c:otherwise>
                                </c:choose>
                                               <!--  <img src="<c:url value="/images/${board.file}"/>"></p>-->
                                                </c:if>
                        </div>
                        </c:forEach>

    </c:param>
</c:import>