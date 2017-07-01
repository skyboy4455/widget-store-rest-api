# WidgiWalla - Widget Store REST API
Provides a REST API for a fictional Widget Store that one day hopes to expand

This application makes use of the Grails REST-API profile.  It provides examples of JSON views for rendering customs
presentations of domain class resources.

## API Features
### Widget
* Browse widgets by type, size and finish
* Create new widget types

### Inventory
* Query inventory system to determine widget availabilty based on product sku
* Query inventory based on min and max quantity levels to aid in inventory control
* Updated inventory amounts per product

#### Ordering
* Create an order of products
* Query order information
* Update order, to add or removed products
* Delete the order
 

### REST API

#### Product / Widget Management

| URL   |      METHOD      |  Description | Error Response  / Message|
|----------|:-------------:|:------|-----|
| ./products/widgets\[?type=widgetType\]\[&size=widgetSize\]\[&finish=widgetFinish\] |  GET | Returns widgets matching params | 400 (InvalidInput)|
| ./product/widgets/\<sku\> |  GET | Returns widget based on sku | 404 (ResourceNotFound)|
| ./product/widgets |  POST | Creates a new widget type | 400 (InvalidInput)|
| ./product/widgets |  PUT | Creates a new widget type | 405 (UnsupportedHttpVerb)|
| ./product/widgets |  DELETE | Creates a new widget type | 405 (UnsupportedHttpVerb)|


NOTE: Only widget products have implementation.  Open to create other product classifications

#### Inventory Management
This API exposes operations on getting inventory information.  
Mimics the scenario where product availability is not held in the
same data source where

| URL   |      METHOD      |  Description |Error Response  / Message|
|----------|:-------------:|:------|-----|
| ./inventory/product |  GET | Returns all product quantities | 404 (ResourceNotFound) or 400 (InvalidInput)|
| ./inventory/product/\<sku\> |  GET | Returns single product quantities | 404 (ResourceNotFound) or 400 (InvalidInput)|
| ./inventory/product/\<sku\>?count=amount |  PUT | Updates single product | 404 (ResourceNotFound) or 400 (InvalidInput)|
| ./inventory/product/\<sku\> |  DELETE | Removing Inventory Products not supported | 405 (UnsupportedHttpVerb)|
| ./inventory/product/\<sku\> |  POST | Creating Inventory Products not supported | 405 (UnsupportedHttpVerb)|

#### Order Management
| URL   |      METHOD      |  Description | Error Response  / Message|
|----------|:-------------:|:------|-----|
| ./order |  POST | Creates a new order |400 (InvalidInput)|
| ./order/\<orderId\> |  GET | Returns order information |404 (ResourceNotFound)|
| ./order/\<orderId\> |  PUT | Updates an order information |404 (ResourceNotFound) or 400 (InvalidInput)|
| ./order/\<orderId\> |  DELETE | Deletes an order |405 (UnsupportedHttpVerb)|

### Implementation Details

#### Domain Classes
* Products - Items that are sold via the store front
    - Product: Base class for an item in the store
    - Widget: Extends Product for customization of an item being sold in the store
    - WidgeType: Used to categorize a Widget
    - WidgetSize: Used to define a Widget's Form factor
    - WidgetFinish: Used to describe a Widget's Finish
* Inventory - Products that are stored in an inventory somewhere (e.g. warehouse, thirdparty shipper, closet, etc.)
    - InventoryProduct: Represents the product that resides in the warehouse, keeps a count in inventory
* Orders
    - Order: Keeps a list of items to be added to the order
    - OrderItem: Contains the Product being purchased and the quantity
    
#### Controllers


#### Services
##### Inventory Service
* Query inventory for available quantities  of a Product
* Increment / Decrement Product inventory count
    - Increment when stock is added
    - Decrement when Products are placed in order

##### Ordering Service
* Create orders based on URL attributes
    - Perform necessary create operations
    - Access Inventory Service to validate quantities

##### Widget Service
* Create Widget based on URL attributes
    - Create corresponding InventoryProduct object
* Lookup Widgets based on URL attributes
    - Format error responses when necessary

### Deployment Options

#### WAR File Deployment

#### JAR File Deployment


## Enhancements / ISSUES
### ISSUE: Ran out of time to implement demo GUI
Currently application is a only headless REST API 

### ISSUE: Duplicate Products in Order
This app does not consolidate order items based on sku.  Customer could have multiple products with the same sku and differing quantities.








