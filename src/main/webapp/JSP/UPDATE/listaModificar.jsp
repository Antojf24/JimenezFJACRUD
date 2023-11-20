<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${context}/INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Lista Modificar</title>
    </head>
    <body>
        <h1>Elige un pájaro para cambiar sus datos</h1>
        <form action="Update" method="post">
            <table class="table">
                <tr>
                    <th>Anilla</th>
                    <th>Especie</th>
                </tr>
                <c:forEach var="ave" items="${aves}">
                    <tr>
                        <td><input type="radio" name="optionUpdate" value="${ave.anilla}"/></td>
                        <td><c:out value="${ave.especie}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <span>${error}</span>
            <div>
                <input type="submit" name="buttons" value="Realizar"/>
                <input type="submit" name="buttons" value="Volver"/>
            </div>
        </form>
    </body>
</html>
