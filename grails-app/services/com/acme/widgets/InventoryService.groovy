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
        if (ipQuery.hasQueryParams()) {

            def query
            //FIXME: Can add more conditions, but just getting some of the same ones in
            if (ipQuery.lt >= 0 && ipQuery.gt >= 0 && ipQuery.lt >= ipQuery.gt) {

                query = InventoryProduct.where{ count > ipQuery.gt && count < ipQuery.lt}
            } else if (ipQuery.lt >= 0) {

                query = InventoryProduct.where{ count < ipQuery.lt}
            } else if (ipQuery.gt >= 0) {
                query = InventoryProduct.where{ count > ipQuery.gt}

            } else if (ipQuery.eq >= 0) {
                query = InventoryProduct.where{ count == ipQuery.eq}
            } else {
                // Should never happen
                query = InventoryProduct.where{ count < 0}
            }

            results = query.list()

        } else {
            results = InventoryProduct.list()
        }

        return results
    }
}
