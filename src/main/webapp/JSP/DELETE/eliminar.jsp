<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${context}/INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Eliminar</title>
    </head>
    <body>
        <h1>Aves que se van a eliminar</h1>
        <span>${error}</span>
        <table class="table">
            <tr>
                <th>Especie</th>
                <th>Lugar</th>
                <th>Fecha</th>
            </tr>
            <c:forEach var="ave" items="${aves}">
                <tr>
                    <td><c:out value="${ave.especie}"/></td>
                    <td><c:out value="${ave.lugar}"/></td>
                    <td><c:out value="${ave.fecha}"/></td>
                </tr>
            </c:forEach>
        </table>
        <form action="Delete" method="post">
            <div>
                <input type="submit" name="buttons" value="Confirmar"/>
                <input type="submit" name="buttons" value="Volver"/>
            </div>
        </form>
    </body>
</html>
