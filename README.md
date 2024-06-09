Booking Service
This service can be used to book tickets from one station to another and it also provides some additional functionalities

Below are the supported APIs :

Purchase Ticket Endpoint:
Method: POST URL: /api/ticket/purchaseTicket

Description: Allows users to purchase a train ticket. It accepts a JSON payload containing details such as the origin, destination, and user information.

Get Receipt Endpoint:
Method: GET URL: /api/ticket/users/{userId}/ticketReceipt

Description: Retrieves the receipt for a specific user's ticket purchase. It expects the user's email or identifier as a path variable.

Get Users by Section Endpoint:
Method: GET URL: /api/ticket/usersBySection

Parameters: section (Query Parameter)

Description: Retrieves a map of users and their allocated seats within a specified train section. It expects the section identifier as a query parameter.

Remove User Endpoint:
Method: DELETE URL: /api/ticket/users/{userId}/remove

Description: Removes a user from the train. It expects the user's email or identifier as a path variable.

Modify User Seat Endpoint:
Method: PUT URL: /api/ticket/users/{userId}/modifySeat

Description: Modifies a user's allocated seat on the train. It expects the user's email or identifier as a path variable and the new seat information in the request body.
