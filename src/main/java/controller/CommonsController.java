
package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.Instrutor;
import model.ModelException;
import model.dao.DAOFactory;
import model.dao.InstrutorDAO;

public class CommonsController {

    public static void listInstrutores(HttpServletRequest req) {
        InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

        List<Instrutor> listInstrutores = null;

        try {
            listInstrutores = dao.listAll();
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (listInstrutores != null)
            req.setAttribute("instrutores", listInstrutores);
    }
}
