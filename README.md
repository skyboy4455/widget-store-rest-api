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
* Update order, to add or update product quantities ( set to 0 to remove)
* Delete the order
 


### REST API

#### Product / Widget Management

| URL   |      METHOD      |  Description | Error Response  / Message|
|----------|:-------------:|:------|-----|
| ./api/v1/products/widgets\[?type=widgetType\]\[&size=widgetSize\]\[&finish=widgetFinish\] |  GET | Returns widgets matching params | 400 (InvalidInput)|
| ./api/v1/product/widgets/\<sku\> |  GET | Returns widget based on sku | 404 (ResourceNotFound)|
| ./api/v1/product/widgets |  POST | Creates a new widget type | 400 (InvalidInput)|
| ./api/v1/product/widgets |  PUT | Update widget type not supported | 405 (UnsupportedHttpVerb)|
| ./api/v1/product/widgets |  DELETE | Delete widget type not support | 405 (UnsupportedHttpVerb)|


NOTE: Only widget products have implementation.  Open to create other product classifications

##### Examples
**XXXTitle**

_METHODXXX: \<baseUrl\>XXXURL_

**body**
 ```json
[
  
]
```

**result**
 ```json
[
  
]
```

**XXXTitle**

_METHODXXX: \<baseUrl\>XXXURL_

**body**
 ```json
[
  
]
```

**result**
 ```json
[
  
]
```

**XXXTitle**

_POST: \<baseUrl\>XXXURL_

**body**
 ```json
{
  "widgetType" : {"name": "newType", "skuCode":"NT_SKU"},
  "widgetSize" : {"name": "newSize", "skuCode":"NS_SKU"},
  "widgetFinish" : {"name": "newFinish", "skuCode":"NF_SKU"}
}
```

**result**
 ```json
[
  
]
```


#### Inventory Management
This API exposes operations on getting inventory information.  
Mimics the scenario where product availability is not held in the
same data source where

| URL   |      METHOD      |  Description |Error Response  / Message|
|----------|:-------------:|:------|-----|
| ./api/v1/inventory/products\[?lt=num\]\[&gt=num\]\[&eq=num\] |  GET | Returns all product quantities | 404 (ResourceNotFound) or 400 (InvalidInput)|
| ./api/v1/inventory/products/\<sku\> |  GET | Returns single product quantities | 404 (ResourceNotFound) or 400 (InvalidInput)|
| ./api/v1/inventory/products/\<sku\>|  PUT | Updates single product, request body has count value| 404 (ResourceNotFound) or 400 (InvalidInput)|
| ./api/v1/inventory/products/\<sku\> |  DELETE | Removing Inventory Products not supported | 405 (UnsupportedHttpVerb)|
| ./api/v1/inventory/products/\<sku\> |  POST | Creating Inventory Products not supported | 405 (UnsupportedHttpVerb)|

##### Examples

**Query Inventory by Quantities with exact count**

_GET: \<baseUrl\>/api/v1/inventory/products?eq=10_

**result**
 ```json
[
  {"count":10,"description":"n/a","sku":"WDG-BASE-GOLD-M"},
  {"count":10,"description":"n/a","sku":"WDG-BASE-COPPER-M"},
  {"count":10,"description":"n/a","sku":"WDG-BASE-STEEL-M"},
  {"count":10,"description":"n/a","sku":"WDG-BASE-WHITE-M"},
  {"count":10,"description":"n/a","sku":"WDG-BASE-BLACK-M"}
]
```
**Update Inventory Product quantity**

_PUT: \<baseUrl\>//api/v1/inventory/products/WDG-BASE-GOLD-M_

**body**
```json
{ "count": 2 }
```

**result**
```json
{"count":2, "description":"n/a", "sku":"WDG-BASE-GOLD-M"}
```


#### Order Management
| URL   |      METHOD      |  Description | Error Response  / Message|
|----------|:-------------:|:------|-----|
| ./api/v1/store/orders |  POST | Creates a new order |400 (InvalidInput)|
| ./api/v1/store/orders/\<orderId\> |  GET | Returns order information |404 (ResourceNotFound)|
| ./api/v1/store/orders\<orderId\> |  PUT | Updates an order information |404 (ResourceNotFound) or 400 (InvalidInput)|
| ./api/v1/store/orders\<orderId\> |  DELETE | Deletes an order |405 (UnsupportedHttpVerb)|

##### Examples



**Query All Purchase Orders**

_GET: \<baseUrl\>/api/v1/store/orders_

**result**
 ```json
[
  {
    "orderNum":1,
    "items":[
      {"quantity":5,"sku":"WDG-BASE-GOLD-M"},
      {"quantity":7,"sku":"WDG-BASE-COPPER-L"},
      {"quantity":10,"sku":"WDG-MEGA-GOLD-M"},{
      "quantity":8,"sku":"WDG-ULTRA-GOLD-M"}
    ]
   }
]
```

** Query Single Purchase Order**

_GET: \<baseUrl\>api/v1/store/orders/\<orderNum\>_

**result**
 ```json
{
   "orderNum":1,
   "items":[
     {"quantity":5,"sku":"WDG-BASE-GOLD-M"},
     {"quantity":7,"sku":"WDG-BASE-COPPER-L"},
     {"quantity":10,"sku":"WDG-MEGA-GOLD-M"},
     {"quantity":8,"sku":"WDG-ULTRA-GOLD-M"}
   ]
}
```


**Create Purchase Order**

_POST: \<baseUrl\>/api/v1/store/orders_

**body**
 ```json
 [
  {"quantity":1,"sku":"WDG-BASE-COPPER-L"},
  {"quantity":1,"sku":"WDG-BASE-COPPER-S"},
  {"quantity":1,"sku":"WDG-BASE-COPPER-M"}
 ]
```

**result**
 ```json
{
  "orderNum":2,
  "items":[
    {"quantity":1,"sku":"WDG-BASE-COPPER-L"},
    {"quantity":1,"sku":"WDG-BASE-COPPER-S"},
    {"quantity":1,"sku":"WDG-BASE-COPPER-M"}
  ]
}
```

**Update Purchase Order**

_PUT: \<baseUrl\>/api/v1/store/orders/1_

NOTE: Example shows operations
* Adding 3 new products
* Update WDG-MEGA-GOLD-M from 10 to 1

**body**
 ```json
[
{"quantity":5, "sku":"WDG-BASE-COPPER-L"}, 
{"quantity":1, "sku":"WDG-BASE-COPPER-L"}, 
{"quantity":1, "sku":"WDG-BASE-COPPER-L"}, 
{"quantity":1, "sku":"WDG-BASE-COPPER-L"}
 ]
```

**result**
 ```json

 {
  "orderNum":1,
  "items":[
    {"quantity":8,"sku":"WDG-ULTRA-GOLD-M"},
    {"quantity":5,"sku":"WDG-BASE-COPPER-L"},
    {"quantity":1,"sku":"WDG-BASE-COPPER-M"},
    {"quantity":1,"sku":"WDG-BASE-COPPER-S"},
    {"quantity":5,"sku":"WDG-BASE-GOLD-M"},
    {"quantity":1,"sku":"WDG-MEGA-GOLD-M"}]
 }

```


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

### Deployment

#### WAR File Deployment

Deployable WAR Archive for Tomcat 7 or greater []
#### JAR File Deployment


## ISSUES
### ISSUE: Headless, No UI for application
Currently application is a only headless REST API 

### ISSUE: Inventory management system allows negative number
Need to implement inventory increment / decrement on Purchase Ordering

### ISSUE: Add URL examples to documentation

## Enhancements
### ENH: Query Purchase Orders with SKU
Add functionality to return all POs with a specific product SKU







