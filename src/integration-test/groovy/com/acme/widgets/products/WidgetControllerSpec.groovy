package com.acme.widgets.products


import grails.test.mixin.integration.Integration
import grails.transaction.*
import static grails.web.http.HttpHeaders.*
import static org.springframework.http.HttpStatus.*
import spock.lang.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class WidgetControllerSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "successful get widgets requests"() {
        when: "Valid get requests are made"
        def resp = restBuilder().get("$baseUrl/api/v1/products/widgets")
        def sizeResp = restBuilder().get("$baseUrl/api/v1/products/widgets?size=small")
        def skuResp = restBuilder().get("$baseUrl/api/v1/products/widgets/WDG-BASE-COPPER-S")

        println skuResp.json
        then: "The response is correct"
        resp.status == OK.value()
        resp.json.size() == 60
        (sizeResp.json.size() > 0 && sizeResp.json.size() < 60)
        skuResp.json.size == "small"
        skuResp.json.type == "base"
        skuResp.json.finish == "copper"
    }

    void "failed get widgets requests"() {
        when: "Valid get requests are made"

        def resp = restBuilder().get("$baseUrl/api/v1/products/widgets/WDG-XXX-XXX-XXX")

        then: "The response is correct"
        resp.status == NOT_FOUND.value()

    }

    void "successful create custom widget types"() {

        when: "Valid create widget request"
        def resp = restBuilder().post("$baseUrl/api/v1/products/widgets") {
            json(
                    [
                            widgetType: [name: "newType", skuCode: "NT"],
                            widgetSize: [name: "newSize", skuCode: "NS"],
                            widgetFinish: [name: "newFinish", skuCode: "NF"]
                    ]
            )

        }

        println resp.json

        then: "Verify response is 200 and contents of new Widget"
        resp.status == OK.value()
        Widget.count() == 61 //New Widget was created - original is 60

    }

    RestBuilder restBuilder() {
        new RestBuilder()
    }
}
