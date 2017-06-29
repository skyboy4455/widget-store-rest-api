package com.acme.widgets.products

class ProductInfo {

    String description

    //FUTURE: Other properties can be added in the future

    static belongsTo = Product

    static constraints = {
        description blank: false
    }
}
