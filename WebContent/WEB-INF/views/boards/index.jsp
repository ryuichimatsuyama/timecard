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
<div style="padding: 10px; margin-bottom: 10px; border: 1px solid #333333;">

                      <p> <b> 松山さん</b></p>
<p>日付</p>
                        <p>メッセージ</p>
</div>

    </c:param>
</c:import>