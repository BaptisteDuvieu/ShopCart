<%@ page import="com.bduvieu.shopcart.Product" %>
<%@ page import="com.bduvieu.shopcart.ShopCart" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Mon Panier Interactif</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet" >
</head>
<body>
<h1>Mon Panier Interactif</h1>
<h4>Baptiste DUVIEU</h4>
<hr/>


<main class="container">
    <div class="bg-light p-5 rounded">
        <h2>Ajouter un produit:</h2>
        <form class="form-group" name="addProduct" method="post" action="shop">
            <label for="reference">Référence: </label>
            <input class="form-control" type="text" id="reference" name="reference"/>

            <label for="nom">Nom: </label>
            <input class="form-control" type="text" id="nom" name="nom"/>

            <label for="prix">Prix: </label>
            <input class="form-control" type="text" id="prix" name="prix"/>

            <input class="btn btn-primary" type="submit" value="Insérer"/>
        </form>
    </div>
</main>



<hr/>

<% if (ShopCart.hasProduct(request)) { %>


<main class="container">
    <div class="bg-light p-5 rounded">
        <h2>Liste des produits:</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Référence</th>
                <th scope="col">Nom</th>
                <th scope="col">Prix</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
                <%= ShopCart.returnListProducts(request) %>
            </tbody>
        </table>

        <div class="alert alert-secondary" role="alert">
            <%= ShopCart.returnShippingFee(request)  %>
        </div>


    </div>
</main>




<hr/>
<% } %>


</body>
</html>
