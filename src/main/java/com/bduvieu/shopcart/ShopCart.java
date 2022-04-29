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

    private List<Product> products;
    public void init() {
        products = new ArrayList<Product>();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        handleGetRequests(request);

        response.setContentType("text/html");

        PrintWriter writer = response.getWriter();

        // return response
        writer.println("<script>window.location.replace('/ShopCart-1.0-SNAPSHOT/')</script>");
    }

    private void handleGetRequests(HttpServletRequest request) {
        String refProductToBeDelete = request.getParameter("deleteReference");

        if( refProductToBeDelete != null ){
            HttpSession session = request.getSession();
            List<Product> products = (List<Product>) session.getAttribute("products");
            List<Product> productsSession = (List<Product>) session.getAttribute("products");

            if(null != productsSession)
            {
                products.removeIf((Product p) -> refProductToBeDelete.equals(p.getReference()));

                if(products.isEmpty()) session.invalidate();
                else session.setAttribute("products", products);
            }
        }

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // read form fields
        String reference = request.getParameter("reference");
        String nom = request.getParameter("nom");
        Integer prix = Integer.parseInt(request.getParameter("prix"));

        // do some processing here...
        HttpSession session = request.getSession();
        List<Product> productsSession = (List<Product>) session.getAttribute("products");
        if(productsSession == null ) products = new ArrayList<Product>();

        products.add(new Product( reference, nom, prix));

        session.setAttribute("products", products);

        doGet(request, response);
    }



    public static String returnShippingFee( HttpServletRequest request ){
        int fraisDePort;
        int totalCommande = ShopCart.returnPrice(request);
        if(totalCommande < 50) fraisDePort = 5;
        else if(totalCommande > 50 && totalCommande < 100) fraisDePort = 8;
        else fraisDePort = 0;

        if(fraisDePort > 0 ) return "<h2>Frais de port pour la commande : " + fraisDePort + "euros. </h2>";
        else return "<h2>Les frais de port sont offerts!</h2>";
    }

    public static boolean hasProduct( HttpServletRequest request ){
        HttpSession session = request.getSession(false);
        String htmlResponse = "";

        if (session != null) {

            List<Product> productsSession = (List<Product>) session.getAttribute("products");
            Integer totalCommande = 0;

            if (null != productsSession) {
                return true;
            }
        }
        return false;
    }

    public static String returnListProducts( HttpServletRequest request ){
        HttpSession session = request.getSession(false);
        String htmlResponse = "";

        if (session != null) {

            List<Product> productsSession = (List<Product>) session.getAttribute("products");
            Integer totalCommande = 0;

            if(null != productsSession)
            {

                for (Product p : productsSession) {

                    htmlResponse += "<li>"  + p.getReference()  + " | "
                            + p.getNom()        + " | "
                            + p.getPrix()       + " | "
                            + "<a href='shop?deleteReference=" + p.getReference()  + "'>Supprimer : " + p.getReference() + "</a></li>";

                    totalCommande += p.getPrix();
                }
            }
            return htmlResponse;
        }
        return "";
    }

    public static int returnPrice( HttpServletRequest request ){
        HttpSession session = request.getSession(false);
        int totalCommande = 0;

        if (session != null) {
            List<Product> productsSession = (List<Product>) session.getAttribute("products");

            if(null != productsSession) { for (Product p : productsSession) { totalCommande += p.getPrix(); } }

        }
        return totalCommande;
    }


    public void destroy() {
    }
}