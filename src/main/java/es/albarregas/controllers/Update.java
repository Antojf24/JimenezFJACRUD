package es.albarregas.controllers;

import es.albarregas.beans.Ave;
import es.albarregas.connections.Conexion;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 * @author janto
 */
@WebServlet(name = "Update", urlPatterns = {"/Update"})
public class Update extends HttpServlet {

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

        String button = request.getParameter("buttons");

        String sql;

        Connection conection = Conexion.getConnection();
        PreparedStatement prepared;
        ResultSet resultado;
        Ave ave;
        Ave nuevaAve;

        String updates = request.getParameter("optionUpdate");

        switch (button) {
            case "Realizar":
                //Comprueba que se haya clickado en algún radio, en caso contrario
                //muestra un error. Si todo es correcto saca de la base de datos todos los datos
                //y los pasa al formulario de modificación
                if (updates != null) {
                    try {
                        sql = "SELECT * FROM aves WHERE anilla=?";
                        prepared = conection.prepareStatement(sql);
                        prepared.setString(1, updates);
                        resultado = prepared.executeQuery();
                        ave = new Ave();
                        while (resultado.next()) {
                            ave.setAnilla(resultado.getString(1));
                            ave.setEspecie(resultado.getString(2));
                            ave.setLugar(resultado.getString(3));
                            ave.setFecha(resultado.getDate(4));
                        }
                        request.getSession().setAttribute("ave", ave);
                    } catch (SQLException ex) {
                        if (ex.getErrorCode() == 1146) {
                            request.setAttribute("error", "La tabla no existe.");
                        } else {
                            request.setAttribute("error", ex);
                        }
                    } finally {
                        Conexion.closeConexion();
                    }
                    url = "JSP/UPDATE/modificar.jsp";
                } else {
                    request.setAttribute("error", "No se ha elegido ningún pájaro que cambiar.");
                    url = "JSP/UPDATE/listaModificar.jsp";
                }
                break;
            case "Actualizar":
                //Comprueba que se haya modificado algún campo, en caso de que ninguno varie
                //se redirige a la página de aviso y muestra un mensaje diciendo que no se ha modificado nada.
                //En caso de que haya algún cambio se hace un update en la tabla para actualizar el registro
                String anilla = request.getParameter("anilla");
                String especie = request.getParameter("especie");
                String lugar = request.getParameter("lugar");
                String fecha = request.getParameter("fecha");

                nuevaAve = (Ave) request.getSession().getAttribute("ave");

                if (!anilla.isEmpty() && !especie.isEmpty() && !lugar.isEmpty() && !fecha.isEmpty()) {
                    ave = new Ave();

                    DateConverter converter = new DateConverter();
                    converter.setPattern("yyyy-MM-dd");
                    ConvertUtils.register(converter, Date.class);

                    try {
                        BeanUtils.populate(ave, request.getParameterMap());
                        if (!nuevaAve.getEspecie().equals(ave.getEspecie())
                                || !nuevaAve.getLugar().equals(ave.getLugar())
                                || nuevaAve.getFecha().getTime() != ave.getFecha().getTime()) {
                            sql = "UPDATE aves SET especie=?, lugar=?, fecha=? WHERE anilla=?";
                            prepared = conection.prepareStatement(sql);
                            prepared.setString(1, ave.getEspecie());
                            prepared.setString(2, ave.getLugar());
                            prepared.setDate(3, new java.sql.Date(ave.getFecha().getTime()));
                            prepared.setString(4, ave.getAnilla());
                            prepared.executeUpdate();
                            request.setAttribute("mensaje", "Se actualizó el ave de anilla " + ave.getAnilla());
                        } else {
                            request.setAttribute("mensaje", "No se han realizado cambios sobre el registro");
                        }
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(Create.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        Conexion.closeConexion();
                    }
                    url = "JSP/UPDATE/avisoModificar.jsp";
                }else{
                    request.setAttribute("error", "Todos los campos son obligatorios");
                    url = "JSP/UPDATE/modificar.jsp";
                }
                break;
            case "Volver":
                //Invalida los atributos de sesión y vuelve al inicio
                request.getSession().invalidate();
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
