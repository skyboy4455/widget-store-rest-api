package com.acme.widgets.orders

import com.acme.widgets.PurchaseOrderService

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PurchaseOrderController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    PurchaseOrderService purchaseOrderService
    //====================================================================================

    @Transactional
    def updatePurchaseOrder(){

        Map skuCountMap = [:]
        println request.JSON
        PurchaseOrder purchaseOrder = PurchaseOrder.findById(Long.valueOf((String)params?.orderNum))

        if(purchaseOrder) {
            request.JSON.each {

                skuCountMap[it?.sku?.trim()?.toUpperCase()] = Integer.valueOf((String) it?.quantity)
            }
            def errors = purchaseOrderService.updatePurchaseOrder(purchaseOrder, skuCountMap)

            if (!errors) {

                respond view: 'show', purchaseOrder
            } else {
                render view:'/errors/invalidInput'
            }
        }else{
            render view:'/notFound'
        }

    }

    def createInventory(){

        println request.JSON
        PurchaseOrder purchaseOrder = new PurchaseOrder().save()
        respond view: 'show', purchaseOrder
    }

    //====================================================================================

    def index(Integer max) {
        params.max = Math.min(max ?: 30, 100)
        respond PurchaseOrder.list(params), model:[orderCount: PurchaseOrder.count()]
    }

    def show(PurchaseOrder purchaseOrder) {
        respond purchaseOrder
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
