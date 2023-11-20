package es.albarregas.controllers;

import es.albarregas.beans.Ave;
import es.albarregas.connections.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author janto
 */
@WebServlet(name = "FrontController", urlPatterns = {"/FrontController"})
public class FrontController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";

        Connection conection = Conexion.getConnection();
        Statement sentencia;
        ResultSet resultado;
        Ave ave;
        List<Ave> aves;

        String sql;

        String option = request.getParameter("buttons");

        switch (option) {
            case "(C)Crear":
                //Redirige hacia la página de creación
                url = "JSP/CREATE/crear.jsp";
                break;
            case "(R)Visualizar":
                //Saca de la base de datos todos los registros y los manda a la página de visualización,
                //tambien comprueba que haya datos dentro, sino muestra un error
                try {
                    sql = "SELECT * FROM aves";
                    sentencia = conection.createStatement();
                    resultado = sentencia.executeQuery(sql);
                    aves = new ArrayList<>();
                    while (resultado.next()) {
                        ave = new Ave();
                        ave.setAnilla(resultado.getString(1));
                        ave.setEspecie(resultado.getString(2));
                        ave.setLugar(resultado.getString(3));
                        ave.setFecha(resultado.getDate(4));
                        aves.add(ave);
                    }
                    if (!aves.isEmpty()) {
                        request.setAttribute("aves", aves);
                        url = "JSP/READ/visualize.jsp";
                    } else {
                        request.setAttribute("error", "No existen datos guardados.");
                    }
                } catch (SQLException e) {
                    if (e.getErrorCode() == 1146) {
                        request.setAttribute("error", "La tabla no existe.");
                    } else {
                        request.setAttribute("error", "Ha ocurrido un error al acceder a la tabla.");
                    }
                } finally {
                    Conexion.closeConexion();
                }
                break;
            case "(U)Modificar":
            case "(D)Eliminar":
                //Tanto para modificar como para eliminar sacamos de la base de datos todas las anillas y
                //especies y redirige hacia la página seleccionada.
                try {
                    sql = "SELECT anilla, especie FROM aves";
                    sentencia = conection.createStatement();
                    resultado = sentencia.executeQuery(sql);
                    aves = new ArrayList<>();
                    while (resultado.next()) {
                        ave = new Ave();
                        ave.setAnilla(resultado.getString(1));
                        ave.setEspecie(resultado.getString(2));
                        aves.add(ave);
                    }
                    if (!aves.isEmpty()) {
                        request.getSession().setAttribute("aves", aves);
                        if(option.equals("(U)Modificar")){
                            url = "JSP/UPDATE/listaModificar.jsp";
                        }else if(option.equals("(D)Eliminar")){
                            url = "JSP/DELETE/listaEliminar.jsp";
                        }
                    } else {
                        request.setAttribute("error", "No existen datos guardados.");
                    }
                } catch (SQLException e) {
                    if (e.getErrorCode() == 1146) {
                        request.setAttribute("error", "La tabla no existe.");
                    } else {
                        request.setAttribute("error", "Ha ocurrido un error al acceder a la tabla.");
                    }
                } finally {
                    Conexion.closeConexion();
                }
                break;
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
