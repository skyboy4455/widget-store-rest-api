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

    @Transactional
    def createInventory(){

        println request.JSON

        Map<String,Integer> skuCountMap = [:]

        request.JSON.each {

            skuCountMap[it?.sku?.trim()?.toUpperCase()] = Integer.valueOf((String) it?.quantity)
        }


        def errors = purchaseOrderService.createNewOrder(skuCountMap)

        if (!errors) {

            respond view: 'show', PurchaseOrder.last()
        } else {
            render view:'/errors/invalidInput'
        }

    }

    //====================================================================================

    def index(Integer max) {

        //TODO: Add functionality to lookup purchase order that contains SKU
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

        render view: "/errors/unsupportedHttpVerb"
    }
}
