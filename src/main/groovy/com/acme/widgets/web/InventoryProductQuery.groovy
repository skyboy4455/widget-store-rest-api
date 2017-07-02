package com.acme.widgets.web

/**
 * Created by chris on 6/30/2017.
 *
 *
 */
class InventoryProductQuery {

    /**
     *
     * @param params
     */
    InventoryProductQuery(def params) {

        lt = Integer.parseInt(params?.lt ?: "-1")
        gt = Integer.parseInt(params?.gt ?: "-1")
        eq = Integer.parseInt(params?.eq ?: "-1")

    }
    Integer lt
    Integer gt
    Integer eq


    /**
     * Validate that at least one query property exists
     */
    boolean hasQueryParams(){

        return (  lt >=0 || eq >=0 || gt >=0)
    }
}
