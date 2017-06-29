package com.acme.widgets.web

/**
 * Created by chris on 6/29/2017.
 *
 *
 */
class WidgetQuery {

    WidgetQuery(def params) {

        type = params?.type ?: ""
        size = params?.size ?: ""
        finish = params?.finish ?: ""
    }
    String type
    String size
    String finish
}
