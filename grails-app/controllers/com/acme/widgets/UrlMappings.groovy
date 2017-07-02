package com.acme.widgets

class UrlMappings {

    static mappings = {

        // Mapping for Inventory Products

        get "/api/v1/inventory/products/${sku}?"(controller: "InventoryProduct", action: "findBySku")
        get "/api/v1/inventory/products(.$format)?"(controller: "InventoryProduct", action: "query")
        put "/api/v1/inventory/products/${sku}"(controller: "InventoryProduct", action: "updateInventory")
        post "/api/v1/inventory/products(.$format)?"(controller: "InventoryProduct", action: "createInventory")
        delete "/api/v1/inventory/products/$id(.$format)?"(controller: "InventoryProduct", action: "deleteInventory")
        delete "/api/v1/inventory/products?"(controller: "InventoryProduct", action: "deleteInventory")

        // For Widgets
        get "/api/v1/products/widgets(.$format)?"(controller: "Widget", action: "query")
        get "/api/v1/products/widgets/${sku}"(controller: "Widget", action: "findBySku")
        put "/api/v1/products/widgets/${sku}"(controller: "Widget", action: "updateWidget")
        delete "/api/v1/products/widgets/${sku}"(controller: "Widget", action: "deleteWidget")
        post "/api/v1/products/widgets?"(controller: "Widget", action: "createWidget")

        //Mapping for orders
        //FIXME: Needs to be secured to admin
        get "/api/v1/store/orders(.$format)?"(controller: "PurchaseOrder", action: "index")
        get "/api/v1/store/orders/${id}(.$format)?"(controller: "PurchaseOrder", action: "show")

        delete "/api/v1/store/orders/${id}(.$format)?"(controller: "PurchaseOrder", action: "delete")


        put "/api/v1/store/orders/${orderNum}"(controller: "PurchaseOrder", action: "updatePurchaseOrder")
        post "/api/v1/store/orders?"(controller: "PurchaseOrder", action: "createInventory")




        delete "/$controller/$id(.$format)?"(action: "delete")
        get "/$controller(.$format)?"(action: "index")
        get "/$controller/$id(.$format)?"(action: "show")
        post "/$controller(.$format)?"(action: "save")
        put "/$controller/$id(.$format)?"(action: "update")
        patch "/$controller/$id(.$format)?"(action: "patch")

        "/"(controller: 'application', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
