package com.acme.widgets.orders

class Order {

    //Order Id - Use default database id for this application
    Integer orderNumber

    static hasMany = [items: OrderItem]

    static constraints = {

        orderNumber nullable: false, unique: true
        items nullable: true
    }

    static mapping = {
        items lazy: false
    }


}
