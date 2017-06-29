package com.acme.widgets.inventory

import com.acme.widgets.products.Product
import grails.rest.Resource

@Resource(uri='/inventory/products', formats=['json'])
class InventoryProduct {

    Product product
    Integer count=0

    static constraints = {

        product nullable: false
        count nullable: false, min: 0
    }

    static mapping = {
        product lazy: false
    }
}
