package com.acme.widgets.products

class WidgetType {

    String name
    String skuCode

    static constraints = {
        name blank: false, unique: true
        skuCode blank: false, unique: true
    }

    String toString(){
        name
    }
}
