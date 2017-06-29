package com.acme.widgets.products

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WidgetController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Widget.list(params), model:[widgetCount: Widget.count()]
    }

    def show(Widget widget) {
        respond widget
    }

    @Transactional
    def save(Widget widget) {
        if (widget == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (widget.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond widget.errors, view:'create'
            return
        }

        widget.save flush:true

        respond widget, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Widget widget) {
        if (widget == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (widget.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond widget.errors, view:'edit'
            return
        }

        widget.save flush:true

        respond widget, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Widget widget) {

        if (widget == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        widget.delete flush:true

        render status: NO_CONTENT
    }
}
