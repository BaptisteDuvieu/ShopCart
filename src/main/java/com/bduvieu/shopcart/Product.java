package com.bduvieu.shopcart;

public class Product {

    private String reference;
    private String nom;
    private Integer prix;

    public Product( String reference,String nom,Integer prix){
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
    }

    public void setReference(String reference) { this.reference = reference;}
    public String getReference() {return reference;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return nom;}

    public void setPrix(Integer prix) {this.prix = prix;}
    public Integer getPrix() {return prix;}
}
