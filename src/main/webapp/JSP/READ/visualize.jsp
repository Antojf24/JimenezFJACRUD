<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${context}/INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Visualizar</title>
    </head>
    <body>
        <h1>Listado de todos los avistamientos</h1>
        <table class="table">
            <tr>
                <th>Anilla</th>
                <th>Especie</th>
                <th>Lugar</th>
                <th>Fecha</th>
            </tr>
            <c:forEach var="ave" items="${aves}">
                <tr>
                    <td><c:out value="${ave.anilla}" /></td>
                    <td><c:out value="${ave.especie}" /></td>
                    <td><c:out value="${ave.lugar}" /></td>
                    <td><c:out value="${ave.fecha}" /></td>
                </tr>
            </c:forEach>
        </table>
        <form action="BackController" method="post">
            <input type="submit" name="buttons" value="Volver"/>
        </form>
    </body>
</html>
