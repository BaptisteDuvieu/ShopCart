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

        products = new ArrayList<Product>();
        // do some processing here...
        products.add(new Product( reference, nom, prix));

        HttpSession session = request.getSession();
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
            htmlResponse += "<h2>Liste des produits:</h2>";

            List<Product> productsSession = (List<Product>) session.getAttribute("products");

            if(null != productsSession)
            {
                htmlResponse += "<ul>";
                for (Product p : products) {
                    htmlResponse += "<li>" + p.getReference() + " | " + p.getNom() + " | " + p.getPrix() + "</li>";
                }
                htmlResponse += "</ul>";
            }

            htmlResponse += "<hr/>";
        } else {
            // no session
        }


        htmlResponse += "</body></html>";

        // return response
        writer.println(htmlResponse);
    }


    public void destroy() {
    }
}