<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${context}/INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Aviso Eliminar</title>
    </head>
    <body>
        <h1>Información sobre el borrado de registros</h1>
        <span>${mensaje}</span>
        <form action="${context}BackController" method="post">
            <input type="submit" name="buttons" value="Inicio"/>
        </form>
    </body>
</html>
