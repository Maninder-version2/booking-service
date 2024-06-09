# Booking Service </br>
This service can be used to book tickets from one station to another and it also provides some additional functionalities. </br>



## Below are the supported APIs : </br>

### Purchase Ticket Endpoint:
Method: POST </br>
URL: _/api/ticket/purchaseTicket_ </br>
Description: Allows users to purchase a train ticket. It accepts a JSON payload containing details such as the origin, destination, and user information. </br>
</br>

### Get Receipt Endpoint:
Method: GET </br>
URL: _/api/ticket/users/{userId}/ticketReceipt_ </br>
Description: Retrieves the receipt for a specific user's ticket purchase. It expects the user's email or identifier as a path variable. </br>
</br>

### Get Users by Section Endpoint:
Method: GET </br>
URL: _/api/ticket/usersBySection_ </br>
Parameters: section (Query Parameter) </br>
Description: Retrieves a map of users and their allocated seats within a specified train section. It expects the section identifier as a query parameter. </br>
</br>

### Remove User Endpoint:
Method: DELETE </br>
URL: _/api/ticket/users/{userId}/remove_ </br>
Description: Removes a user from the train. It expects the user's email or identifier as a path variable. </br>
</br>

### Modify User Seat Endpoint:
Method: PUT </br>
URL: _/api/ticket/users/{userId}/modifySeat_ </br>
Description: Modifies a user's allocated seat on the train. It expects the user's email or identifier as a path variable and the new seat information in the request body. </br>
