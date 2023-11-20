<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${context}/INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Crear</title>
    </head>
    <body>
        <h1>Añadir nuevo pájaro</h1>
        <form action="Create" method="post">
            <input type="text" name="anilla" placeholder="Ej. 123" maxlength="3" value="${param.anilla}"/>
            <input type="text" name="especie" placeholder="Paloma" maxlength="20" value="${param.especie}"/>
            <input type="text" name="lugar" placeholder="En la ciudad" maxlength="30" value="${param.lugar}"/>
            <input type="date" name="fecha" value="${param.fecha}"/>
            <span>${error}</span>
            <div>
                <input type="submit" name="buttons" value="Crear"/>
                <input type="submit" name="buttons" value="Volver"/>
            </div>
        </form>
    </body>
</html>
