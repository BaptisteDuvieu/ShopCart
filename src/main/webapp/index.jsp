<%@ page import="com.bduvieu.shopcart.Product" %>
<%@ page import="com.bduvieu.shopcart.ShopCart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>My ShopCart</h1>

<hr/>

<h2>Ajouter un produit:</h2>

<form name="addProduct" method="post" action="shop">
    <label for="reference">Référence: </label><br>
    <input type="text" id="reference" name="reference"/> <br/>

    <label for="nom">Nom: </label><br>
    <input type="text" id="nom" name="nom"/> <br/>

    <label for="prix">Prix: </label><br>
    <input type="text" id="prix" name="prix"/> <br/>

    <input type="submit" value="Insérer"/>
</form>

<hr/>

<% if (ShopCart.hasProduct( request ) ) { %>

<h2>Liste des produits:</h2>

<ul>
    <%= ShopCart.returnListProducts(request) %>
</ul>

<%= ShopCart.returnShippingFee( request )  %>
<hr/>
<% } %>


</body>
</html>
