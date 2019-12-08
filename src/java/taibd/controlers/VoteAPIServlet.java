/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.ws.handler.MessageContext;
import taibd.entity.ResponseXMLWrapper;
import taibd.model.ProductDAO;
import taibd.model.User;
import taibd.model.Vote;
import taibd.model.VotePK;
import taibd.model.VotesDAO;
import taibd.utilities.JAXBUtils;

/**
 *
 * @author HOME
 */
@WebServlet(name = "VoteAPIServlet", urlPatterns = {"/api/votes"})
public class VoteAPIServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int productId = Integer.parseInt(request.getParameter("productId"));
        int votes = Integer.parseInt(request.getParameter("votes"));
        
        ProductDAO productDAO = new ProductDAO();
        VotesDAO voteDAO = new VotesDAO();
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        ResponseXMLWrapper res = new ResponseXMLWrapper();
        boolean result = false;

        if (user != null) {
            Vote vote = voteDAO.findVoteByUsernameAndProductId(user.getUsername(), productId);
            if (vote == null) {
                Vote newVote = new Vote();
                newVote.setVotes(votes);
                newVote.setVotePK(new VotePK(user.getUsername(), productId));
                result = voteDAO.addVote(newVote);
            } else {
                vote.setVotes(votes);
                result = voteDAO.updateVote(vote);
            }
            if (result) {
                res.setSuccess (true);
            } else {
                res.setSuccess(false);
                res.setErrors("Can not add vote!");
            }
        } else {
            res.setSuccess(false);
            res.setErrors("No User authentication!");
        }
        
        try {
            String responseResult = JAXBUtils.marshal(res, ResponseXMLWrapper.class);
            response.getOutputStream().write(responseResult.getBytes(StandardCharsets.UTF_8));
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
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
