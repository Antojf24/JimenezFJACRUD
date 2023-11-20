<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${context}/INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Modificar</title>
    </head>
    <body>
        <h1>Actualizar los datos de un ave</h1>
        <form action="Update" method="post">
            <input type="text" name="anilla" placeholder="Ej. 123" maxlength="3" value="${ave.anilla}" readonly/>
            <input type="text" name="especie" placeholder="Paloma" maxlength="20" value="${ave.especie}"/>
            <input type="text" name="lugar" placeholder="En la ciudad" maxlength="30" value="${ave.lugar}"/>
            <input type="date" name="fecha" value="${ave.fecha}"/>
            <span>${error}</span>
            <div>
                <input type="submit" name="buttons" value="Actualizar"/>
                <input type="submit" name="buttons" value="Volver"/>
            </div>
        </form>
    </body>
</html>
