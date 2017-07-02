package com.acme.widgets.products

import grails.rest.Resource;
@Resource(uri='/products/widgets', formats=['json'])
class Widget extends Product {


    WidgetType widgetType
    WidgetFinish widgetFinish
    WidgetSize widgetSize

    static constraints = {


        widgetFinish nullable: false
        widgetType nullable: false
        widgetSize nullable: false

    }

    static mapping = {

        widgetType lazy: false
        widgetSize lazy: false
        widgetFinish lazy: false
    }

    String toString(){

        "${widgetType}, ${widgetFinish}, ${widgetSize}"
    }

    void generateSku(){

        if(!sku){
            sku = "WDG-${widgetType.skuCode}-${widgetFinish.skuCode}-${widgetSize.skuCode}"
        }
    }
}
