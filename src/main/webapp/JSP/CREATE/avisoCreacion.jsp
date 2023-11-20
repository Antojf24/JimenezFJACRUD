<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${context}/INC/meta.jsp"/>
        <link rel="stylesheet" href="${styles}"/>
        <title>Aviso Creación</title>
    </head>
    <body>
        <h1>Información de nuevas aves</h1>
        <h3>Los datos introducidos en la base de datos son</h3>
        <table id="create">
            <tr>
                <td>Anilla:</td>
                <td>${param.anilla}</td>
            </tr>
            <tr>
                <td>Especie:</td>
                <td>${param.especie}</td>
            </tr>
            <tr>
                <td>Lugar:</td>
                <td>${param.lugar}</td>
            </tr>
            <tr>
                <td>Anilla:</td>
                <td>${param.fecha}</td>
            </tr>
        </table>
            <form action="${context}BackController" method="post">
                <input type="submit" name="buttons" value="Inicio"/>
            </form>
    </body>
</html>
