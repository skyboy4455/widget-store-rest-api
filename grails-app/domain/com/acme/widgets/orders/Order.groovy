package com.acme.widgets.orders

class Order {

    //Order Id - Use default database id for this application
    Long orderId
    Date dateCreated
    Date lastUpdated

    static transients = ['orderId']

    static hasMany = [items: OrderItem]

    static constraints = {

        items nullable: true
    }

    static mapping = {
        items fetch: 'eager'
    }

    /**
     * Using database generated ID as orderId
     * @return
     */
    Long getOrderId(){
        return id
    }
}
