package com.acme.widgets.orders

import grails.converters.JSON
import grails.test.mixin.integration.Integration
import grails.transaction.*
import static grails.web.http.HttpHeaders.*
import static org.springframework.http.HttpStatus.*
import spock.lang.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class InventoryProductControllerSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "inventory/products"() {
        when: "The home page is requested"
        def resp = restBuilder().get("$baseUrl/api/v1/inventory/products")


        then: "verify response is correct"
        resp.status == OK.value()
        resp.json.size() == 60 //60 Default Elements

    }

    void "inventory/products/valid sku"() {
        when: "The sku exists"
        def resp = restBuilder().get("$baseUrl/api/v1/inventory/products/WDG-BASE-GOLD-M")


        then: "verify response is correct"
        resp.status == OK.value()
        resp.json.sku == "WDG-BASE-GOLD-M" //60 Default Elements

    }

    void "inventory/products/valid sku (lowercase)"() {
        when: "The sku exists, but is lowercase"
        def resp = restBuilder().get("$baseUrl/api/v1/inventory/products/wdg-base-gold-m")


        then: "verify response is correct"
        resp.status == OK.value()
        resp.json.sku == "WDG-BASE-GOLD-M" //60 Default Elements

    }

    void "inventory/products/invalid sku"() {
        when: "The products with non-existent sku"
        def resp = restBuilder().get("$baseUrl/api/v1/inventory/products/WDG-XXX-XXX-XXX")


        then: "verify response is correct"
        resp.status == NOT_FOUND.value()


    }

    void "inventory/products/ update widget count"() {
        when: "The update single sku count"
        def resp = restBuilder().put("$baseUrl/api/v1/inventory/products/WDG-BASE-GOLD-M") {
            json(
                    count: 1
            )
        }


        then: "verify response is correct"
        resp.status == OK.value()


    }

    void "inventory/products/ update INVALID widget count"() {
        when: "attempts to update an invalid product"
        def resp = restBuilder().put("$baseUrl/api/v1/inventory/products/WDG-BASE-GOLD-NONE") {
            json(
                    count: 1
            )
        }


        then: "verify response is correct"
        resp.status == NOT_FOUND.value()


    }

    void "inventory/products/ batch updateswidget count"() {
        when: "The batch update valid skus"
        def resp = restBuilder().put("$baseUrl/api/v1/inventory/products") {
            json(
                    [
                            ([count: 1, sku: "WDG-BASE-GOLD-S"]),
                            ([count: 2, sku: "WDG-BASE-GOLD-M"]),
                            ([count: 3, sku: "WDG-BASE-GOLD-L"])
                    ]

            )
        }


        then: "The response is correct"
        resp.status == OK.value()


    }

    void "Test that create is disabled"(){

        when: "post method is run"
        def resp = restBuilder().post("$baseUrl/api/v1/inventory/products")

        then: "The response is correct"
        resp.status == METHOD_NOT_ALLOWED.value() //Unsupported HTTP Verb

    }

    void "Test that delete is disabled"(){

        when: "post method is run"
        def resp = restBuilder().delete("$baseUrl/api/v1/inventory/products")
        def respSku = restBuilder().delete("$baseUrl/api/v1/inventory/products/WDG-BASE-GOLD-L")

        then: "The response is correct"
        resp.status == METHOD_NOT_ALLOWED.value() //Unsupported HTTP Verb
        respSku.status == METHOD_NOT_ALLOWED.value()

    }


    RestBuilder restBuilder() {
        new RestBuilder()
    }
}
