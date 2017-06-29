package com.acme.widgets.inventory

import com.acme.widgets.products.Product

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class InventoryProductController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        println "X = ${params?.x}"
        params.max = Math.min(max ?: 10, 100)
        respond InventoryProduct.list(params), model:[inventoryProductCount: InventoryProduct.count()]
    }

    def show(InventoryProduct inventoryProduct) {

        println "Running Show command - Inventory Product XXX"
        respond inventoryProduct
    }

    def sku(){
        println params.sku
        respond InventoryProduct.findByProduct(Product.findBySku(params.sku))
    }

    def find(){

        println "Inventory Controller Find Method ${params}. ${request.parts}"
        println "Params: ${params}"
        InventoryProduct inventoryProduct = InventoryProduct.last() //FIXME:
        respond inventoryProduct
    }

    @Transactional
    def save(InventoryProduct inventoryProduct) {
        if (inventoryProduct == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (inventoryProduct.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond inventoryProduct.errors, view:'create'
            return
        }

        inventoryProduct.save flush:true

        respond inventoryProduct, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(InventoryProduct inventoryProduct) {
        if (inventoryProduct == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (inventoryProduct.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond inventoryProduct.errors, view:'edit'
            return
        }

        inventoryProduct.save flush:true

        respond inventoryProduct, [status: OK, view:"show"]
    }

    @Transactional
    def delete(InventoryProduct inventoryProduct) {

        if (inventoryProduct == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        inventoryProduct.delete flush:true

        render status: NO_CONTENT
    }
}
