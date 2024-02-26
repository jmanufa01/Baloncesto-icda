package controller;
import repository.ModeloDatos;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

import static constants.AppConstants.ERROR;

public class VotosReset extends HttpServlet {
    private ModeloDatos bd;
    private final Logger logger = Logger.getLogger(VotosReset.class.getName());

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            bd.resetVotos();
            res.sendRedirect(res.encodeRedirectURL("index.html"));
            logger.info("Votos reseteados");
        }catch (Exception e){
            logger.info(ERROR + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
