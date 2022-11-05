The simplest mock server with built-in GCP App Engine deployment option.
=================

# API:

Mapping:

 - GET /mappings - Receive list of all mappings

 - GET /mappings/{id} - Receive mapping by id

 - POST /mappings - Add new mapping

 - PUT /mappings/{id} - Update mapping by id

 - DELETE /mappings/{id} - Delete mapping by id

Match:
	
 - GET /match/{url} - Receive mocking response for GET request with provided URL

 - POST /match/{url} - Receive mocking response for POST request with provided URL

# Entity

- Mapping:
Java:
 ```java
public class Mapping {

    private String url;
    private String method;
    private int responseCode;
    private String body;
    private String contentType;
}    
```
JSON:
```json
{"url": "/item/1", "method":"GET", "responseCode": 200, "body": "{\"item\": 1}", "contentType": "application/json"}
```

# Examples

Add new mapping

 > curl --header "Content-Type: application/json"
  --request POST
  --data '{"url": "/item/1", "method":"GET", "responseCode": 200, "body": "{\\"item\\": 1}", "contentType": "application.json"}'
  `${SERVER_URL}`/mappings/

Receive mocked response

>  curl `${SERVER_URL}`/match/item/1

Delete mapping

>  curl --request DELETE `${SERVER_URL}`/mappings/`${MAPPING_ID}`
