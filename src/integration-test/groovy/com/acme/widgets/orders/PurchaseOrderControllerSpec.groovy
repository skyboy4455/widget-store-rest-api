package com.acme.widgets.orders


import grails.test.mixin.integration.Integration
import grails.transaction.*
import static grails.web.http.HttpHeaders.*
import static org.springframework.http.HttpStatus.*
import spock.lang.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class PurchaseOrderControllerSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "Test the get methods"() {
        when: "store/orders - return all orders"
        def resp = restBuilder().get("$baseUrl/api/v1/store/orders")
        def orderResp = restBuilder().get("$baseUrl/api/v1/store/orders/1")
        def failedResp = restBuilder().get("$baseUrl/api/v1/store/orders/2")

        then: "The response is correct"

        //index
        resp.status == OK.value()

        // specific order
        orderResp.status == OK.value()
        orderResp.json.items.size() == 4

        // non-existent
        failedResp.status == NOT_FOUND.value()

    }



    void "Test the post methods"() {
        when: "store/orders - return all orders"

        def resp = restBuilder().post("$baseUrl/api/v1/store/orders") {
            json(
                    [
                            ([quantity:1, sku:"WDG-BASE-COPPER-L"]),
                            ([quantity:1, sku:"WDG-BASE-COPPER-S"]),
                            ([quantity:1, sku:"WDG-BASE-COPPER-M"]),

                    ]
            )
        }

        then: "The response is correct"

        //index
        resp.status == OK.value()
//        resp.json.items.find{ it.sku == "WDG-BASE-COPPER-L"}.quantity == 1

    }

    void "Test the new put methods"() {
        when: "store/orders - return all orders"

        def resp = restBuilder().put("$baseUrl/api/v1/store/orders/1") {
            json(
                    [
                            ([quantity:5, sku:"WDG-BASE-COPPER-L"]),
                            ([quantity:1, sku:"WDG-BASE-COPPER-S"]),
                            ([quantity:1, sku:"WDG-BASE-COPPER-M"]),
                    ]
            )
        }

        def notEnuffResp = restBuilder().put("$baseUrl/api/v1/store/orders/1") {
            json(
                    [
                            ([quantity:99, sku:"WDG-BASE-COPPER-L"])
                    ]
            )
        }

        then: "The response is correct"

        //index
        resp.status == OK.value()
        resp.json.items.find{ it.sku == "WDG-BASE-COPPER-L"}.quantity == 5

        //Should have failed with not enough inventory
        notEnuffResp.status == BAD_REQUEST.value()

    }


    RestBuilder restBuilder() {
        new RestBuilder()
    }
}
