package com.acme.widgets.orders

class PurchaseOrder {

    //PurchaseOrder Id - Use default database id for this application

    Set items = [] as Set

    static hasMany = [items: OrderItem]

    static constraints = {

        items nullable: true
    }

    static mapping = {
        items lazy: false
    }


}
