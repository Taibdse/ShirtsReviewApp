/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.webservice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import taibd.entity.ResponseXMLWrapper;
import taibd.model.ProductDAO;
import taibd.model.User;
import taibd.model.Vote;
import taibd.model.VotePK;
import taibd.model.VotesDAO;

/**
 * REST Web Service
 *
 * @author HOME
 */
@Path("abc")
public class VotesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RatingsResource
     */
    public VotesResource() {
    }
    
    @Resource 
    private WebServiceContext ctx;

    /**
     * Retrieves representation of an instance of
     * taibd.webservice.RatingsResource
     *
     * @return an instance of java.lang.String
     */
    @PUT
    @Produces(MediaType.APPLICATION_XML)
    public ResponseXMLWrapper voteProduct(@QueryParam("productId") int productId,
            @QueryParam("votes") int votes) {
        ProductDAO productDAO = new ProductDAO();
        VotesDAO voteDAO = new VotesDAO();
        HttpServletRequest req = (HttpServletRequest) ctx.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        if(req != null){
            System.out.println("!null");
        } else {
            System.out.println("null");
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        ResponseXMLWrapper res = new ResponseXMLWrapper();
        boolean result = false;

        if (user != null) {
            Vote vote = voteDAO.findVoteByUsernameAndProductId(user.getUsername(), productId);
            if (vote == null) {
                Vote newVote = new Vote();
                newVote.setVotes(votes);
                newVote.setVotePK(new VotePK(user.getUsername(), productId));
                result = voteDAO.addVote(vote);

            } else {
                vote.setVotes(votes);
                result = voteDAO.updateVote(vote);
            }
            if (result) {
                res.setSuccess(true);
            } else {
                res.setSuccess(false);
                res.setErrors("Can not add vote!");
            }
        } else {
            res.setSuccess(false);
            res.setErrors("No User authentication!");
        }
        return res;
    }
}
