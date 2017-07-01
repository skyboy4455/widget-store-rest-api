package com.acme.widgets.orders

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OrderController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PurchaseOrder.list(params)//, model:[orderCount: PurchaseOrder.count()]
    }

    def show(PurchaseOrder order) {
        respond order
    }

    @Transactional
    def save(PurchaseOrder order) {
        if (order == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (order.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond order.errors, view:'create'
            return
        }

        order.save flush:true

        respond order, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PurchaseOrder order) {
        if (order == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (order.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond order.errors, view:'edit'
            return
        }

        order.save flush:true

        respond order, [status: OK, view:"show"]
    }

    @Transactional
    def delete(PurchaseOrder order) {

        if (order == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        order.delete flush:true

        render status: NO_CONTENT
    }
}
