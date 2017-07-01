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

    def createInventory(){

        render(view: '/errors/unsupportedHttpVerb')
    }

    def deleteInventory(){
        render(view: '/errors/unsupportedHttpVerb')
    }

    @Transactional
    def updateInventory(){

        println request.getJSON().toString()
        //TODO: Implement
        // Parse JSON pass to InventoryProductService
        //

        //Errors - 404 (ResourceNotFound) or 400 (InvalidInput)

        render( view: "successInventoryUpdate")

    }


    def findBySku(){
        println params.sku
        respond InventoryProduct.findByProduct(Product.findBySku(params.sku))
    }

    def query(){
        //TODO: Implement Query based on Count
        // lessThan
        // Greater than
        // Equal
        render view:"index", model:[ inventoryProductList: InventoryProduct.list(), inventoryProductCount: InventoryProduct.count()]
    }


}
