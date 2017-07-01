package com.acme.widgets.orders

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OrderItemController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond OrderItem.list(params), model:[orderItemCount: OrderItem.count()]
    }

    def show(OrderItem orderItem) {
        respond orderItem
    }

    @Transactional
    def save(OrderItem orderItem) {
        if (orderItem == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (orderItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orderItem.errors, view:'create'
            return
        }

        orderItem.save flush:true

        respond orderItem, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(OrderItem orderItem) {
        if (orderItem == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (orderItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orderItem.errors, view:'edit'
            return
        }

        orderItem.save flush:true

        respond orderItem, [status: OK, view:"show"]
    }

    @Transactional
    def delete(OrderItem orderItem) {

        if (orderItem == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        orderItem.delete flush:true

        render status: NO_CONTENT
    }
}
