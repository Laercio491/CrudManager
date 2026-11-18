
package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ModelException;
import model.Instrutor;
import model.dao.DAOFactory;
import model.dao.InstrutorDAO;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        if (action.equals("/logout")) {

            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

        String cnh = req.getParameter("cnh");
        String password = req.getParameter("password");

        if (cnh == null || cnh.isBlank() ||
                password == null || password.isBlank()) {

            resp.sendRedirect(req.getContextPath() + "/login?erro=true");
            return;
        }

        InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

        Instrutor instrutor = null;

        try {
            instrutor = dao.findByCNH(cnh);
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (instrutor == null) {
            resp.sendRedirect(req.getContextPath() + "/login?erro=true");
            return;
        }

        if (PasswordEncryptor.checkPassword(
                password, instrutor.getPassword())) {

            HttpSession session = req.getSession();
            session.setAttribute("instrutor_logged", instrutor);

            resp.sendRedirect(req.getContextPath() + "/");

            System.out.println("Instrutor logado com sucesso: "
                    + instrutor.getNome());

        } else {
            resp.sendRedirect(req.getContextPath() + "/login?erro=true");
        }
    }
}
