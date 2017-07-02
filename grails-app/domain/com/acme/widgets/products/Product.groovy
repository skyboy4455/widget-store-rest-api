package com.acme.widgets.products

class Product {

    String sku
    ProductInfo productInfo


    static constraints = {

        sku blank:false, unique: true
        productInfo nullable: true
    }

    static mapping = {
        tablePerHierarchy false
    }
}
