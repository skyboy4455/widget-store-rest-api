package com.acme.widgets.inventory

import com.acme.widgets.InventoryService
import com.acme.widgets.products.Product
import com.acme.widgets.web.InventoryProductQuery
import grails.transaction.Transactional

@Transactional(readOnly = true)
class InventoryProductController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    InventoryService inventoryService

    def index(Integer max) {

        println "X = ${params?.x}"
        params.max = Math.min(max ?: 10, 100)
        respond InventoryProduct.list(params), model: [inventoryProductCount: InventoryProduct.count()]
    }

    def createInventory() {

        render(view: '/errors/unsupportedHttpVerb')
    }

    def deleteInventory() {
        render(view: '/errors/unsupportedHttpVerb')
    }

    @Transactional
    def updateInventory() {


        String sku = params?.sku?.trim()?.toUpperCase()
        println "Req JSON: " + request?.JSON?.count
        InventoryProduct inventoryProduct = InventoryProduct.findByProduct(Product.findBySku(sku?.trim()?.toUpperCase()))

        if (inventoryProduct) {
            def errors = inventoryService.updateInventoryCount(sku, Integer.valueOf(request?.JSON?.count))

            if (errors) {
                respond(view: "/errors/invalidInput")
            } else {
                respond view: "show", inventoryProduct
            }
        } else {
            render(view: "/errors/unknownResource")
        }


    }


    def findBySku() {
        String sku = params?.sku?.trim()?.toUpperCase()
        println sku
        InventoryProduct inventoryProduct = InventoryProduct.findByProduct(Product.findBySku(sku?.trim()?.toUpperCase()))
        respond view: "show", inventoryProduct
    }

    def query() {

        InventoryProductQuery ipQuery = new InventoryProductQuery(params)

        List results = inventoryService.queryInventoryProductCounts(ipQuery)

        println results
        render view: "index", model: [inventoryProductList: results, inventoryProductCount: results.size()]
    }


}
