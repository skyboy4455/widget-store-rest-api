package com.acme.widgets

import com.acme.widgets.inventory.InventoryProduct
import com.acme.widgets.orders.PurchaseOrder
import com.acme.widgets.orders.OrderItem
import com.acme.widgets.products.Product
import com.acme.widgets.products.Widget
import com.acme.widgets.products.WidgetFinish
import com.acme.widgets.products.WidgetSize
import com.acme.widgets.products.WidgetType

class BootStrap {

    def init = { servletContext ->

        List sizes = [['small', 'S'], ['medium', 'M'], ['large', 'L'], ['xlarge', 'XL']]
        createWidgetSizes(sizes)

        // Create default widget finishes
        List finishes = [['gold', 'GOLD'], ['copper', 'COPPER'], ['steel', 'STEEL'], ['white', 'WHITE'], ['black', 'BLACK']]
        createWidgetFinishes(finishes)

        // Create default widget types
        List types = [['base', 'BASE'], ['mega', 'MEGA'], ['ultra', 'ULTRA']]
        createWidgetTypes(types)

        //Generate all possible combinations of current widgets
        generateSampleWidgets()

        // Create Some sample types, sizes and finishes for
        createWidgetSizes([["micro","U"],["nano","n",["pico","P"]]])

        createWidgetFinishes([["rust","RUST"],["water-colour","WC"]])

        createWidgetTypes([["vanilla","VAN"],["chocolate","CHOC"]])

        //Create sample Orders
        createSampleOrders()

    }

    /**
     *
     */
    private static void generateSampleWidgets() {

        List widgetTypes = WidgetType.findAll()
        List widgetSizes = WidgetSize.findAll()
        List widgetFinishes = WidgetFinish.findAll()

        Widget widget
        int sampleCount = 0
        widgetTypes.each { WidgetType wt ->

            println "Current Sample Count: ${sampleCount}"
            widgetSizes.each { WidgetSize ws ->

                widgetFinishes.each { WidgetFinish wf ->
                    widget = new Widget(widgetSize: ws, widgetFinish: wf, widgetType: wt)
                    widget.generateSku()

                    if (!widget.validate()) {
                        println widget.errors.getAllErrors()
                    }
                    widget.save()

                    new InventoryProduct(product: widget, count: sampleCount ).save()

                }

                sampleCount+=10

            }

        }
    }

    /**
     * List of list of types:
     * e.g. [['base','BASE'], ['mega','MEGA'], ['ultra','ULTRA']]
     * @param types
     */
    private static void createWidgetTypes(List types) {

        WidgetType widgetType
        types.each {
            widgetType = new WidgetType(name: it[0], skuCode: it[1])

            if (!widgetType.validate()) {
                println "Unable to create widget type: ${it[0]}"
            }
            widgetType.save()
        }
    }

    private static void createWidgetFinishes(List finishes) {
        WidgetFinish widgetFinish
        finishes.each {
            widgetFinish = new WidgetFinish(name: it[0], skuCode: it[1])
            if (!widgetFinish.validate()) {
                println "Unable to create widget widgetFinish: ${it[0]}"
            }
            widgetFinish.save()
        }
    }

    private static createWidgetSizes(List sizes) {
        // Create default sizes
        WidgetSize widgetSize
        sizes.each {

            widgetSize = new WidgetSize(name: it[0], skuCode: it[1])
            if (!widgetSize.validate()) {
                println "Unable to create widget widgetSize: ${it[0]}"
            }
            widgetSize.save()
        }
    }

    def createSampleOrders(){

        PurchaseOrder order = new PurchaseOrder().save(flush:true)

        Product sample1 = Product.findBySku("WDG-BASE-GOLD-M")
        Product sample2 = Product.findBySku("WDG-ULTRA-GOLD-M")
        Product sample3 = Product.findBySku("WDG-MEGA-GOLD-M")
        Product sample4 = Product.findBySku("WDG-BASE-COPPER-L")

        order.addToItems( new OrderItem(total: 5,sku: sample1.sku))
        order.addToItems( new OrderItem(total: 8,sku: sample2.sku))
        order.addToItems( new OrderItem(total: 10,sku: sample3.sku))
        order.addToItems( new OrderItem(total: 7,sku: sample4.sku))
        order.save(flush:true)

    }

    def destroy = {
    }

}
