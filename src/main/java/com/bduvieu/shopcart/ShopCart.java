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

@WebServlet(name = "shopCart", value = "/my-cart")

public class ShopCart extends HttpServlet {

    private List<Product> products;
    public void init() {

        products = new ArrayList<Product>();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        response.setContentType("text/html");

        returnPage(request, response);
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



    private void returnPage(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        // get response writer
        PrintWriter writer = response.getWriter();

        // build HTML code
        // Hello
        PrintWriter out = response.getWriter();


        String htmlResponse = "<html><body>";

        htmlResponse += "<h1>My ShopCart</h1>";

        htmlResponse += "<hr/>";

        htmlResponse += "<h2>Ajouter un produit:</h2>";

        htmlResponse += "<form name=\"addProduct\" method=\"post\" action=\"my-cart\">\n" +
                "    Référence: <input type=\"text\" name=\"reference\"/> <br/>\n" +
                "    Nom: <input type=\"text\" name=\"nom\"/> <br/>\n" +
                "    Prix: <input type=\"text\" name=\"prix\"/> <br/>\n" +
                "    <input type=\"submit\" value=\"Insert\" />\n" +
                "</form>";
        htmlResponse += "<hr/>";



        HttpSession session = request.getSession(false);
        if (session != null) {


            List<Product> productsSession = (List<Product>) session.getAttribute("products");
            Integer totalCommande = 0;

            if(null != productsSession)
            {
                htmlResponse += "<h2>Liste des produits:</h2>";

                htmlResponse += "<ul>";
                for (Product p : products) {
                    htmlResponse += "<li>" + p.getReference() + " | " + p.getNom() + " | " + p.getPrix() + "</li>";
                    totalCommande += p.getPrix();
                }

                htmlResponse += "</ul>";

                int fraisDePort;

                if(totalCommande < 50) fraisDePort = 5;
                else if(totalCommande > 50 && totalCommande < 100) fraisDePort = 8;
                else fraisDePort = 0;

                if(fraisDePort > 0 ) htmlResponse += "<h2>Frais de port pour la commande : " + fraisDePort + "euros. </h2>";
                else htmlResponse += "<h2>Les frais de port sont offerts!</h2>";


                htmlResponse += "<hr/>";
            }


        }

        htmlResponse += "</body></html>";

        // return response
        writer.println(htmlResponse);
    }


    public void destroy() {
    }
}