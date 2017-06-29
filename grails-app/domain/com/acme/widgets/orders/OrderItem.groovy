package com.acme.widgets.orders

import com.acme.widgets.products.Product

class OrderItem {

    Product product
    Integer quantity=1

    static constraints = {
        product nullable: false
        quantity nullable: false, min: 1
    }
}
