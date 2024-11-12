
1. SSR and CSR


2. HTTP Method and Rest
GET
Retrieve data on a server. GET requests typically do not include a request body, as the client is not attempting to create or update data.

POST
Create new resources. POST requests typically include a request body, which is where the client specifies the attributes of the resource to be created.

PUT
Replace an existing resource with an updated version. 

PATCH
The PATCH method is used to update an existing resource. 

DELETE
The DELETE method is used to remove data from a database.


REST 

Representational State Transfer (REST) is a software architecture that imposes conditions on how an API should work.

Principles of the REST architectural style:

Client-server based
The uniform interface separates user concerns from data storage concerns. The client’s domain concerns UI and request-gathering, while the server’s domain concerns focus on data access, workload management, and security. The separation of client and server enables each to be developed and enhanced independently of the other.

Stateless operations
Request from client to server must contain all of the information necessary so that the server can understand and process it accordingly. The server can’t hold any information about the client state.

RESTful resource caching
Data within a response to a request must be labeled as cacheable or non-cacheable.

Layered system
REST allows for an architecture composed of hierarchical layers. In doing so, each component cannot see beyond the immediate layer with which they are interacting.

Code on demand
Because REST APIs download and execute code in the form of applets or scripts, there’s more client functionality. Oftentimes, a server will send back a static representation of resources in the form of XML or JSON. Servers can also send executable codes to the client when necessary.




3. JSON

JSON is a string whose format very much resembles JavaScript object literal format. You can include the same basic data types inside JSON as you can in a standard JavaScript object — strings, numbers, arrays, booleans, and other object literals. This allows you to construct a data hierarchy, like so:

json
{
  "name": "name",
  "id": 2016,
  "isActive": true,
  "address": [
    {
      "city":"Hanoi",
      ...
    },
    {
      "city":"HCM",
      ...
      
    },
  ]
}