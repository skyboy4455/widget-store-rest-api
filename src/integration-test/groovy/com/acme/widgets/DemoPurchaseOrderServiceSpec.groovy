package com.acme.widgets

import com.acme.widgets.orders.OrderItem
import com.acme.widgets.orders.PurchaseOrder
import grails.test.mixin.integration.Integration
import grails.transaction.*
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.*

@Integration
@Rollback
class DemoPurchaseOrderServiceSpec extends Specification {

    @Autowired
    PurchaseOrderService purchaseOrderService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Enough Inventory"() {

        when: "Standard Update"

        Long orderNumber=1L
        Integer requestedCount=2

        Map result = purchaseOrderService.updatePurchaseOrder(orderNumber, ["WDG-ULTRA-GOLD-M":requestedCount])
        PurchaseOrder po = PurchaseOrder.findById(orderNumber)
        OrderItem orderItem = po?.items?.find{ it.sku = "WDG-ULTRA-GOLD-M"}

        then: "No errors returned"
        result.isEmpty()
        orderItem.total == requestedCount

    }

    void "Test Not Enough Inventory"() {

        when: "test something"

        Map result = purchaseOrderService.updatePurchaseOrder(1L, ["WDG-ULTRA-GOLD-M": 10000])
        then: "fix me"
        !result.isEmpty()
        result.keySet().contains("WDG-ULTRA-GOLD-M")
        result["WDG-ULTRA-GOLD-M"] == "po.error.not.enough.inventory"
    }

    void "Test Add New Item"() {

        when: "test something"
        Long orderNumber=1L

        Map result = purchaseOrderService.updatePurchaseOrder(orderNumber, ["WDG-ULTRA-GOLD-XL": 1])

        then: "Verify New Item Not Supported"

        !result.isEmpty()



    }

    void "Test Verify Inventory for Create"(){

        when: "Has Enough Inventory"
        boolean hasSmallInventory = purchaseOrderService.verifyInventoryForCreate(["WDG-ULTRA-GOLD-XL": 1])
        boolean hasMassiveInventory = purchaseOrderService.verifyInventoryForCreate(["WDG-ULTRA-GOLD-XL": 100000])
        boolean hasMixedMassiveInventory = purchaseOrderService.verifyInventoryForCreate(["WDG-ULTRA-GOLD-XL": 100000, "WDG-BASE-GOLD-L": 1])

        Map<String,Integer> testMap = ["WDG-ULTRA-GOLD-XL": Integer.valueOf(1), "WDG-BASE-GOLD-L": Integer.valueOf(5)]
        def errors = purchaseOrderService.createNewOrder(testMap)

        def failTestErrors = purchaseOrderService.createNewOrder(["BAD-WIDGET-SKU": 1])

        then: "Return true"
        hasSmallInventory
        !hasMassiveInventory
        !hasMixedMassiveInventory
        PurchaseOrder.count() == 2 //Original plus new one
        !failTestErrors.isEmpty() //Errors reported
        errors.isEmpty()


    }
}
