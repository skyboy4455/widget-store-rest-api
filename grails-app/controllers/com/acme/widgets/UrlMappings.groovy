package com.acme.widgets

class UrlMappings {

    static mappings = {


        // Mapping for Products
        //get "/inventory/products/$id(.$format)?" (controller: "InventoryProduct", action: "show")
        get "/inventory/products/${sku}?" (controller: "InventoryProduct", action: "sku")
        put "/inventory/products/$id(.$format)?"(controller: "InventoryProduct", action:"update")
        get "/inventory/products(.$format)?" (controller: "InventoryProduct", action: "index")




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
