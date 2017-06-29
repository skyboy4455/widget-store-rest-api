package com.acme.widgets

import com.acme.widgets.inventory.InventoryProduct
import com.acme.widgets.products.Product
import com.acme.widgets.web.InventoryProductQuery
import grails.transaction.Transactional

@Transactional
class InventoryService {


    Map<Integer, String> updateInventoryCount(String sku, Integer inventoryCount) {

        Map<Integer, String> errors = [:]

        if (inventoryCount >= 0) {
            InventoryProduct inventoryProduct = InventoryProduct.findByProduct(Product.findBySku(sku))

            //Change inventory level
            if (inventoryProduct) {

                inventoryProduct.count = inventoryCount
                inventoryProduct.save(flush: true)

            } else {
                errors[404] = "Inventory Product - Resource Not Available"
            }
        } else {
            errors[400] = "Invalid count for Inventory Product"
        }

        return errors
    }

    List<InventoryProduct> queryInventoryProductCounts(InventoryProductQuery ipQuery) {

        List results
        if (ipQuery.validate()) {

            // Within a range
            if (ipQuery.lt >= 0 && ipQuery.gt >= 0 && ipQuery.lt >= ipQuery.gt) {

            } else if (ipQuery.lt >= 0) {

            } else if (ipQuery.gt >= 0) {

            } else if (ipQuery.eq >= 0) {

            } else if (ipQuery.sku) {
                results = InventoryProduct.findAllByProduct(Product.findBySku(ipQuery.sku))
            } else {
                //Somehow it got here, so give them nothing.
                results = []
            }

        } else {
            results = []
        }

        return results
    }
}
