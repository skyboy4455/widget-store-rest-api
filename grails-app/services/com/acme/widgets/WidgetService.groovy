package com.acme.widgets

import com.acme.widgets.products.Widget
import com.acme.widgets.products.WidgetFinish
import com.acme.widgets.products.WidgetSize
import com.acme.widgets.products.WidgetType
import com.acme.widgets.web.WidgetQuery
import grails.transaction.Transactional

@Transactional
class WidgetService {

    List<Widget> findAllWidgetsFromQuery(WidgetQuery widgetQuery) {

        List<Widget> widgets

        WidgetFinish widgetFinish
        WidgetSize widgetSize
        WidgetType widgetType

        widgetType = widgetQuery?.type ? WidgetType.findByName(widgetQuery?.type) : null
        widgetSize = widgetQuery?.size ? WidgetSize.findByName(widgetQuery?.size) : null
        widgetFinish = widgetQuery?.finish ? WidgetFinish.findByName(widgetQuery?.finish) : null


        // All Parames - List of 1
        if (widgetType && widgetSize && widgetFinish) {

//            widgets = Widget.findAllByWidgetFinishAndWidgetSizeAndWidgetType(widgetFinish, widgetSize, widgetType)
            widgets = Widget.findAllWhere(widgetFinish: widgetFinish, widgetSize: widgetSize, widgetType: widgetType)
        }
        //Size and Type
        else if (widgetType && widgetSize) {
            widgets = Widget.findAllWhere(widgetSize: widgetSize, widgetType: widgetType)
        }
        //Size and Finish
        else if (widgetFinish && widgetSize) {
            widgets = Widget.findAllWhere(widgetFinish: widgetFinish, widgetSize: widgetSize)
        }
        //Finish and Type
        else if (widgetFinish && widgetType) {
            widgets = Widget.findAllWhere(widgetFinish: widgetFinish, widgetType: widgetType)
        }

        // Type
        else if (widgetType) {
            widgets = Widget.findAllByWidgetType(widgetType);

        }
        // Size
        else if (widgetSize) {
            widgets = Widget.findAllByWidgetSize(widgetSize);

        }
        // Finish
        else if (widgetFinish) {
            widgets = Widget.findAllByWidgetFinish(widgetFinish)

        } else {
            widgets = Widget.findAll()
        }


        return widgets
    }

    Widget findBySku(String sku){

        return Widget.findBySku(sku)
    }
}
