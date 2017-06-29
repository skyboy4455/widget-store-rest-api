package com.acme.widgets

import com.acme.widgets.inventory.InventoryProduct
import com.acme.widgets.products.Widget
import com.acme.widgets.products.WidgetFinish
import com.acme.widgets.products.WidgetSize
import com.acme.widgets.products.WidgetType

class BootStrap {

    def init = { servletContext ->

        List sizes = [['small', 'S'], ['medium', 'M'], ['large', 'L'], ['xlarge', 'XL']]
        // Create default sizes
        WidgetSize widgetSize
        sizes.each {

            widgetSize = new WidgetSize(name: it[0], skuCode: it[1])
            if(!widgetSize.validate())
            {
                println "Unable to create widget widgetSize: ${it[0]}"
            }
            widgetSize.save()
        }

        // Create default widget finishes
        List finishes =[['gold', 'GOLD'], ['copper', 'COPPER'], ['steel', 'STEEL'], ['white', 'WHITE'], ['black', 'BLACK']]
        WidgetFinish widgetFinish
        finishes.each {
            widgetFinish = new WidgetFinish(name: it[0], skuCode: it[1])
            if(!widgetFinish.validate())
            {
                println "Unable to create widget widgetFinish: ${it[0]}"
            }
            widgetFinish.save()
        }

        // Create default widget types
        List types = [['base','BASE'], ['mega','MEGA'], ['ultra','ULTRA']]
        WidgetType widgetType
        types.each {
            widgetType = new WidgetType(name: it[0],skuCode: it[1])

            if(!widgetType.validate())
            {
                println "Unable to create widget type: ${it[0]}"
            }
            widgetType.save()
        }

        //Create some Widgets

        List widgetTypes = WidgetType.findAll()
        List widgetSizes = WidgetSize.findAll()
        List widgetFinishes = WidgetFinish.findAll()

        Widget widget
        Random rand = new Random()
        int max = 1000
        widgetTypes.each{ WidgetType wt ->

            widgetSizes.each{ WidgetSize ws ->

                widgetFinishes.each{ WidgetFinish wf ->
                    widget = new Widget(widgetSize: ws, widgetFinish: wf, widgetType: wt)
                    widget.generateSku()

                    if(!widget.validate()){
                        println widget.errors.getAllErrors()
                    }
                    widget.save()

                    new InventoryProduct(product: widget, count: rand.nextInt(max+1)).save()

                }
            }

        }




    }
    def destroy = {
    }
}
