# eShop Api
eShop is an E-Commerce app used to solve the troubles and painful torments 
of shopping in Afghanistan. The idea behind this app is to make shopping 
easier and faster where people can easily find, buy and receive their 
essential items.

eshop-API is created and developed with Spring boot v2.7.1, Java v8, 
spring security v5.7.2, H2 in memory database v2.1.214 and Oauth v4.0.0 

### Security


### REST API Endpoints
All inputs and outputs use JSON format.

```
api/login
    POST / - Login using username: (your email) and password:(your password)
    
api/users
    GET / - get list of all users -> required : admin role
    PUT / - update the logged in user information
    GET /user - information the about this logged in user
    POST /signup - add new user required: name, last-name, email, profile-picture, password
    DELETE /delete remove this logged in user
    POST /addRoleToUser - add role to the user, required : the requester must be admin, the user-email, and  role-name


api/products
    GET /pagination/{offset}/{pagesize} -> List of products
    POST / -> Add product - required : admin role
    DELETE / -> delete all products - require : admin role
    GET /{id} -> View product
    PUT /{id} -> Update product - require : admin role
    DELETE /{id} -> delete product - require : admin role
    GET /find?name={product-name} -> find all products by name
    GET /find?brand={brand-name} -> find all products by brand-name
    GET /find?category={category-name} -> find all products by category-name
    GET /find?minprice={minimum-price} -> find all products by price greater than minimun-price
    GET /find?maxprice={maximum-price} -> find all products by price smaller than maximum-price
    GET /find?maxprice={maximum-price} -> find all products by price smaller than maximum-price
    GET /find?category={category}&maxprice={maximum-price} -> find all products by category-name and by price smaller than maximum-price
    GET /find?category={category}&minprice={minimum-price} -> find all products by category-name and by price greater than minimum-price
    GET /find?category={category}&minprice={minimum-price}&brand={brand-name} -> find all products by category-name, brand-name and by price greater than minimum-price
    GET /find?category={category}&maxprice={maximum-price}&brand={brand-name} -> find all products by category-name, brand-name and by price smaller than maximum-price
    GET /find?maxprice={maximum-price}&brand={brand-name} -> find all products by price smaller than maximum-price and brand-name
    GET /find?minprice={minimum-price}&brand={brand-name} -> find all products by price greater than maximum-price and brand-name
    



api/orders      -> user must be logged in
    GET / - List of orders
    POST / - Add oders
    GET /{id} - View order
    POST /{id} - Update order
    DELETE /{id} - delete order
    GET /find?userId={user-id} -> find all orders by user-id
    GET /find?productId={product-id} -> find all orders by product-id
    GET /find?userId={user-id}&productId={product-id} -> find all orders by user-id and product-id
    GET /find?orderDate={order-date} -> find all orders by order-date
    GET /find?orderDate={order-date}& isDelivered={true or false} -> find all orders by order-date and if delivered or not
    GET /find?startDate={start-date}& endDate={end-date} -> find all orders between start-date and end-date
    GET /find?userId={user-id}&productId={product-id}&startDate={start-date}& endDate={end-date} -> find all orders by user-id, product-id between start-date and end-date
    GET /find?userId={user-id}&productId={product-id}&dateAfter={dateAfter} -> find all orders by user-id, product-id after date-after
    GET /find?startDate={start-date}& endDate={end-date}& isDelivered={true or false} -> find all orders between start-date and end-date and if delivered or not
    GET /find?beforeDate={before-date} -> find all orders by before-date
    GET /find?afterDate={after-date} -> find all orders by after-date
    

api/comments  -> user must be logged in

    GET / - List of comments
    POST / - Add comment
    GET /{id} - View comment
    PUT /{id} - Update comment
    DELETE /{id} - delete comment
    GET /products/{id} - View comments of a product
    GET /products/{id}/size - get number of comments of a product
    GET /users/{id} - View comments of a user
    POST /{commentId}/like - add a like to the comment
    POST /{commentId}/dislike - add a dislike to the comment
    
  
/api/favorites    -> user must be logged in

    GET / - List of favorite-products
    POST / - Add product to favorite list
    PUT /{id} - Update favorite-product
    DELETE /{id} - delete favorite-product

/api/statistics  -> require admin role
    GET /models - List of models summary (products, users, orders, brands, categories) 
    GET /summaryByMonth? model={model-name} - summary of the model at last 12 months

/api/v1/files 
    GET /user-image/{id} - get the specific user profile image
    GET /product-image/{id}/{side} - get the product image by id and side
    GET /brand-image/{id} - get the brand image by id 




```