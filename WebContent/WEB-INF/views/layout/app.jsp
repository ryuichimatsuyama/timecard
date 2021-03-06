<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>勤怠管理システム</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
             <div id="header_menu">
                    <h1><a href="<c:url value='/' />">勤怠管理システム</a></h1>&nbsp;&nbsp;&nbsp;
                                        <c:if test="${sessionScope.login_employee != null}">
                        <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                            <a href="<c:url value='/employees/index' />">従業員管理</a>&nbsp;
                        </c:if>
                            <a href="<c:url value='/salary/index' />">月給</a>&nbsp;
                                                        <a href="<c:url value='/relations/index' />">上司一覧</a>&nbsp;
						<a href="<c:url value='/approvals/index' />">承認待ち</a>&nbsp;
						<a href="<c:url value='/messages/index' />">DM</a>&nbsp;
						<a href="<c:url value='/boards/index' />">掲示板</a>&nbsp;

                            </c:if>

            </div>
                            <c:if test="${sessionScope.login_employee != null}">
                    <div id="employee_name">
                        <c:out value="${sessionScope.login_employee.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                </c:if>
            </div>

            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by Matsuyama Ryuichi.
            </div>
</div>
    </body>
</html>
