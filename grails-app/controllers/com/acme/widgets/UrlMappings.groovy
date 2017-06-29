package com.acme.widgets

class UrlMappings {

    static mappings = {


        // Mapping for Inventory Products

        get "/api/inventory/products/${sku}?" (controller: "InventoryProduct", action: "findBySku")
        put "/api/inventory/products/${sku}"(controller: "InventoryProduct", action:"updateInventory")
        post "/api/inventory/products(.$format)?"(controller: "InventoryProduct", action:"createInventory")
        delete "/api/inventory/products/$id(.$format)?"(controller: "InventoryProduct", action:"deleteInventory")
        get "/api/inventory/products(.$format)?" (controller: "InventoryProduct", action: "query")

        // For Widgets
        get "/api/products/widgets(.$format)?"(controller: "Widget", action:"query")
        get "/api/products/widgets/${sku}"(controller: "Widget", action:"findBySku")
        put "/api/products/widgets/${sku}"(controller: "Widget", action:"updateWidget")
        delete "/api/products/widgets/${sku}"(controller: "Widget", action:"deleteWidget")
        post "/inventory/products(.$format)?" (controller: "Widget", action: "createWidget")


        //Mapping for orders




        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
