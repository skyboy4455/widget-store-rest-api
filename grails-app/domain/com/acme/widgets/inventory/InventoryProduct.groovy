package com.acme.widgets.inventory

import com.acme.widgets.products.Product
import grails.rest.Resource

@Resource(uri='/inventory/products', formats=['json'])
class InventoryProduct {

    Product product
    Integer count=0

    static constraints = {

        product nullable: false
        count nullable: false //min: 0 TODO: Currently supports negative values.  This could support backlog request.
    }

    static mapping = {
        product lazy: false
    }
}
