package es.albarregas.controllers;

import es.albarregas.beans.Ave;
import es.albarregas.connections.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author janto
 */
@WebServlet(name = "Delete", urlPatterns = {"/Delete"})
public class Delete extends HttpServlet {

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
        
        int count = 0;

        Connection conection = Conexion.getConnection();
        PreparedStatement prepared;
        ResultSet result;
        Ave ave;
        List<Ave> aves;

        String[] deletes = request.getParameterValues("optionDelete");

        switch (button) {
            case "Eliminar":
                //Comprueba que se haya seleccionado mínimo un checkbox, en caso contrario
                //muestra un error en pantalla. Si se ha pulsado alguno en ese caso un bucle iterará
                //e irá añadiendo a una lista todos los registros que se quieran borrar y los pasa
                //a la siguiente página
                if (deletes != null) {
                    try {
                        aves = new ArrayList<>();
                        for (String anilla : deletes) {
                            sql = "SELECT * FROM aves WHERE anilla=?";
                            prepared = conection.prepareStatement(sql);
                            prepared.setString(1, anilla);
                            result = prepared.executeQuery();
                            result.next();
                            ave = new Ave();
                            ave.setAnilla(result.getString(1));
                            ave.setEspecie(result.getString(2));
                            ave.setLugar(result.getString(3));
                            ave.setFecha(result.getDate(4));
                            aves.add(ave);
                        }
                        request.getSession().setAttribute("aves", aves);
                    } catch (SQLException ex) {
                        request.setAttribute("error", ex);
                    } finally {
                        Conexion.closeConexion();
                    }
                    url = "JSP/DELETE/eliminar.jsp";
                } else {
                    request.setAttribute("error", "No se ha elegido ningún pájaro que eliminar.");
                    url = "JSP/DELETE/listaEliminar.jsp";
                }
                break;
            case "Confirmar":
                //Recoge la lista de datos que se quieren borrar y se itera borrando uno
                //a uno todos los datos que haya en esa lista. Manda un mensaje con el número
                //de registros borrados a la siguiente página
                try {
                    aves = (List<Ave>) request.getSession().getAttribute("aves");
                    for (Ave aveDelete : aves) {
                        sql = "DELETE FROM aves WHERE anilla=?";
                        prepared = conection.prepareStatement(sql);
                        prepared.setString(1, aveDelete.getAnilla());
                        prepared.executeUpdate();
                        count++;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Conexion.closeConexion();
                }
                if(count == 1){
                    request.setAttribute("mensaje", "Se eliminó 1 registro.");
                } else{
                    request.setAttribute("mensaje", "Se eliminaron " + count + " registros.");
                }
                url = "JSP/DELETE/avisoEliminar.jsp";
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
