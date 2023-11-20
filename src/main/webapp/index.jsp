<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="context" value="${request.contextPath}" scope="application"/>
<c:url var="styles" value="CSS/styles.css" scope="application"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Menú</title>
    </head>
    <body>
        <h1>Operaciones básicas con los datos de las aves</h1>
        <span>${error}</span>
        <form action="FrontController" method="post">
            <div>
                <input type="submit" name="buttons" value="(C)Crear"/>
                <input type="submit" name="buttons" value="(R)Visualizar"/>
                <input type="submit" name="buttons" value="(U)Modificar"/>
                <input type="submit" name="buttons" value="(D)Eliminar"/>
            </div>
        </form>
    </body>
</html>
