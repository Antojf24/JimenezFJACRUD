package es.albarregas.controllers;

import es.albarregas.beans.Ave;
import es.albarregas.connections.Conexion;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

@WebServlet(name = "Create", urlPatterns = {"/Create"})
public class Create extends HttpServlet {

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
        PreparedStatement prepared;
        String sql = "";

        Ave ave = null;
        String anilla = request.getParameter("anilla");
        String especie = request.getParameter("especie");
        String lugar = request.getParameter("lugar");
        String fecha = request.getParameter("fecha");
        
        String button = request.getParameter("buttons");

        switch(button){
            case "Crear":
                //Comprueba que no haya ningún campo vacío, en ese caso muestra un error en pantalla,
                //en caso contrario añade un nuevo registro a la tabla
                if (!anilla.isEmpty() && !especie.isEmpty() && !lugar.isEmpty() && !fecha.isEmpty()) {
                    ave = new Ave();

                    DateConverter converter = new DateConverter();
                    converter.setPattern("yyyy-MM-dd");
                    ConvertUtils.register(converter, Date.class);

                    try {
                        BeanUtils.populate(ave, request.getParameterMap());
                        sql = "INSERT INTO aves VALUES(?, ?, ?, ?)";
                        prepared = conection.prepareStatement(sql);
                        prepared.setString(1, ave.getAnilla());
                        prepared.setString(2, ave.getEspecie());
                        prepared.setString(3, ave.getLugar());
                        prepared.setDate(4, new java.sql.Date(ave.getFecha().getTime()));
                        prepared.executeUpdate();
                        url = "JSP/CREATE/avisoCreacion.jsp";
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(Create.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        if (ex.getErrorCode() == 1062) {
                            request.setAttribute("error", "La anilla ya existe en la base de datos.");
                            url = "JSP/CREATE/crear.jsp";
                        }
                    } finally {
                        Conexion.closeConexion();
                    }
                } else {
                    request.setAttribute("error", "Todos los campos son obligatorios.");
                    url = "JSP/CREATE/crear.jsp";
                }
                break;
            case "Volver":
                //Vuelve al inicio
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
