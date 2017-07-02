package com.acme.widgets.products

import com.acme.widgets.WidgetService
import com.acme.widgets.web.WidgetCreateInfo
import com.acme.widgets.web.WidgetQuery

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WidgetController {

    WidgetService widgetService
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def findBySku(){

        Widget widget = widgetService.findBySku(params?.sku?:'')

        if(!widget){
           render(view: 'noWidgetFound', model:[sku:params?.sku])
        }
        else{
            respond widget
        }

    }

    def deleteWidget(){

        render(view: '/errors/unsupportedHttpVerb')
    }

    def query(){

        WidgetQuery widgetQuery= new WidgetQuery(params)
        respond view: "index" ,widgetService.findAllWidgetsFromQuery(widgetQuery)

    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Widget.list(params), model:[widgetCount: Widget.count()]
    }

    def show(Widget widget) {
        respond widget
    }

    @Transactional
    def createWidget(){

        println request.JSON

        WidgetCreateInfo widgetCreateInfo = new WidgetCreateInfo(
                widgetTypeName: request?.JSON?.widgetType?.name,
                widgetTypeSkuCode: request?.JSON?.widgetType?.skuCode,

                widgetFinishName: request?.JSON?.widgetFinish?.name,
                widgetFinishSkuCode: request?.JSON?.widgetFinish?.skuCode,

                widgetSizeName: request?.JSON?.widgetSize?.name,
                widgetSizeSkuCode: request?.JSON?.widgetSize?.skuCode

        )
        Widget widget = widgetService.createWidget(widgetCreateInfo)


        respond view:"show", widget
    }


}
