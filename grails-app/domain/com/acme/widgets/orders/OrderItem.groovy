package com.acme.widgets.orders

import com.acme.widgets.products.Product

class OrderItem {

    String sku
    Integer total=1

    static constraints = {
        sku nullable: false
        total nullable: false, min: 1
    }
}
