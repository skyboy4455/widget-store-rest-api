package com.acme.widgets

import com.acme.widgets.products.Widget
import com.acme.widgets.products.WidgetFinish
import com.acme.widgets.products.WidgetSize
import com.acme.widgets.products.WidgetType
import com.acme.widgets.web.WidgetCreateInfo
import com.acme.widgets.web.WidgetQuery
import grails.transaction.Transactional

@Transactional
class WidgetService {

    List<Widget> findAllWidgetsFromQuery(WidgetQuery widgetQuery) {

        List<Widget> widgets

        WidgetFinish widgetFinish
        WidgetSize widgetSize
        WidgetType widgetType



        // No query params, just return everything
        if(!widgetQuery?.type && !widgetQuery?.size && !widgetQuery?.finish ){
            return Widget.list()
        }

        widgetType = WidgetType.findByName(widgetQuery?.type)
        widgetSize = WidgetSize.findByName(widgetQuery?.size)
        widgetFinish = WidgetFinish.findByName(widgetQuery?.finish)

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
            widgets = []
        }


        return widgets
    }

    Widget findBySku(String sku) {

        return Widget.findBySku(sku)
    }

    /**
     * Map contains
     * @param widgetComponentMap
     * @return
     */
    Widget createWidget(WidgetCreateInfo widgetCreateInfo) {

        WidgetType widgetType = WidgetType.findOrSaveWhere(
                name: widgetCreateInfo.widgetTypeName,
                skuCode: widgetCreateInfo.widgetTypeSkuCode)

        WidgetSize widgetSize = WidgetSize.findOrSaveWhere(
                name: widgetCreateInfo.widgetSizeName,
                skuCode: widgetCreateInfo.widgetSizeSkuCode
        )

        WidgetFinish widgetFinish = WidgetFinish.findOrSaveWhere(
                name: widgetCreateInfo.widgetFinishName,
                skuCode: widgetCreateInfo.widgetFinishSkuCode
        )

        Widget widget = Widget.findOrCreateWhere(
                widgetType: widgetType,
                widgetFinish: widgetFinish,
                widgetSize: widgetSize
        )

        widget.generateSku()
        widget.save(flush:true)

        return widget

    }
}
