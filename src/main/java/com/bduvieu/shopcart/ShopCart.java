package com.bduvieu.shopcart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "shopCart", value = "/shop")

public class ShopCart extends HttpServlet {

    //show the page to handle the request
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        //function to handle the delete request
        handleGetRequests(request);

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        // return redirection
        writer.println("<script>window.location.replace('/ShopCart-1.0-SNAPSHOT/')</script>");
    }

    //handle the get request to delete a product
    private void handleGetRequests(HttpServletRequest request) {
        String refProductToBeDelete = request.getParameter("deleteReference");

        //if exist
        if( refProductToBeDelete != null ){
            HttpSession session = request.getSession();
            List<Product> productsSession = (List<Product>) session.getAttribute("products");

            //if there is a product
            if(null != productsSession)
            {
                //delete the reference of product
                productsSession.removeIf((Product p) -> refProductToBeDelete.equals(p.getReference()));

                //if product cart empty delete the session
                if(productsSession.isEmpty()) session.invalidate();
                //else it is saved to session
                else session.setAttribute("products", productsSession);
            }
        }
    }

    // handle the post request
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        // read form fields
        String reference = request.getParameter("reference");
        String nom = request.getParameter("nom");
        Integer prix = Integer.parseInt(request.getParameter("prix"));

        // get the sessions
        HttpSession session = request.getSession();
        List<Product> productsSession = (List<Product>) session.getAttribute("products");

        // if not exist create the list
        if(productsSession == null ) productsSession = new ArrayList<Product>();

        //add the product
        productsSession.add(new Product( reference, nom, prix));

        //set to the session
        session.setAttribute("products", productsSession);

        //redirection to the main page
        doGet(request, response);
    }

    //show the shipping fee based on the product session
    public static String returnShippingFee( HttpServletRequest request ){
        int fraisDePort, totalCommande = ShopCart.returnPrice(request);

        //logic around the fee
        if(totalCommande < 50) fraisDePort = 5;
        else if(totalCommande > 50 && totalCommande < 100) fraisDePort = 8;
        else fraisDePort = 0;

        //return the message to be shown
        if(fraisDePort > 0 ) return "Frais de port pour la commande : " + fraisDePort + "euros.";
        else return "Les frais de port sont offerts!";
    }

    //return if there is a product in the cart
    public static boolean hasProduct( HttpServletRequest request ){
        HttpSession session = request.getSession(false);
        String htmlResponse = "";
        Integer totalCommande = 0;

        if (session != null) {
            List<Product> productsSession = (List<Product>) session.getAttribute("products");

            return null != productsSession;
        }
        return false;
    }

    //return the list of product formatted in table html
    public static String returnListProducts( HttpServletRequest request ){
        HttpSession session = request.getSession(false);
        String htmlResponse = "";

        if (session != null) {
            List<Product> productsSession = (List<Product>) session.getAttribute("products");

            //if there is product inside
            if(null != productsSession)
            {
                //for each product
                for (Product p : productsSession) {
                    htmlResponse += "<tr>"
                            + "<th scope=\"row\">" + p.getReference()  + "</th> "
                            + "<td>" + p.getNom()        + "</td>"
                            + "<td>" +p.getPrix()       + "</td>"
                            + "<td>" + "<a href='shop?deleteReference=" + p.getReference()  + "'><button type=\"button\" class=\"btn btn-secondary\">Retirer</button></a>"+ "</td>"
                            + "</tr>";
                }
            }
            return htmlResponse;
        }
        return "";
    }

    //return the price of the cart
    public static int returnPrice( HttpServletRequest request ){
        HttpSession session = request.getSession(false);
        int totalCommande = 0;

        if (session != null) {
            List<Product> productsSession = (List<Product>) session.getAttribute("products");

            //add the price for each product
            if(null != productsSession) { for (Product p : productsSession) { totalCommande += p.getPrix(); } }
        }
        return totalCommande;
    }
    public void init() { }
    public void destroy() { }
}