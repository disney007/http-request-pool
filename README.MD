## What the application can do?
* Can be used to mock http response based on rules
* Test webhook
## How to run
### Start services
docker and docker-compose required
* `mvn clean install` - create docker images
* `docker-compose -f docker-required-services.yml up` - start mongo database
*  `docker-compose up` - start application servers `management` and `serve`

Note:
* `management` server is running on port 9001 to for rules setup and request history management
* `serve` server is running on port 9000 to accept user http requests
### Set url to return mocked response
Send following POST request to `http://localhost:9001/api/rules/create`
```json
    {
    	"path": "/test",
    	"method": "post",
    	"condition": "$body.value == \"abc\"",
    	"returnHttpCode":200,
    	"returnJson": "{\"abc\":123}"
    }
```
- `path`: set rule to which path
- `method`: the http method e.g `post`, `get`, `head` ..., `*` for any method
- `condition` if the user request matches current rule, then `returnHttpCode` and `returnJson` will be given to that request
- `returnHttpCode` the http code returned to the matched user request
- `returnJson` the http body returned to the matched user request, it can be normal string too
### Accept user requests
Send following POST request to `http://localhost:9000/test` 
```json
    {
        "value": "abc"
    } 
```
The the http status `200` and following json will be returned since condition matched for "value" equals "abc" in body
```json
    {
        "abc": 123
    }
``` 
### Fetch user requests
Send get request to `http://localhost:9001/api/requestHistory/query?path=/test&method=POST&pageIndex=0&pageSize=50`
```json
    {
        "items": [
            {
                "id": "a15bdd9f-b51a-46c6-b3ae-81269c566a6f",
                "path": "/test",
                "method": "POST",
                "request": {
                    "path": "/test",
                    "method": "POST",
                    "body": "{\n\"value\": \"abc\"\n}",
                    "headers": {
                        "content-length": "18",
                        "postman-token": "157b182e-0ddf-43ed-a690-f9065c011a3d",
                        "host": "localhost:9000",
                        "connection": "keep-alive",
                        "content-type": "application/json",
                        "cache-control": "no-cache",
                        "accept-encoding": "gzip, deflate",
                        "accept": "*/*",
                        "user-agent": "PostmanRuntime/7.20.1"
                    },
                    "cookies": {},
                    "queryParameters": {}
                },
                "response": {
                    "body": {
                        "abc": 123
                    },
                    "status": 200
                },
                "createdAt": 1577478254718
            }
        ],
        "totalCount": 1,
        "pageIndex": 0,
        "pageSize": 50
    }
```
## How to set rule condition
Conditions are written in javascript, and the value of last statement should be boolean or string "true" or "false"
#### Check in body as json
```javascript
$body.x == 'string value' || $body.y == 123
```
#### Check in body as string
```javascript
$bodyString === `hello world` || $bodyString.indexOf('hello') >= 0; 
```
#### Check in http header
```javascript
$headers.someVariable === 'abcdefg12345' && $headers['user-agent'].toLocaleString().indexOf('postman') > 0 
```
#### Check in query parameters in path
e.g. http://localhost:9000/test?pageIndex=0&pageSize=50&search=abc
```javascript
$queryParams.pageIndex >= 0 && $queryParams.pageSize == 50 && $queryParams.search === 'abc'
```
if parameter is an array e.g. http://localhost:9000/test?value=1&value=2
```javascript
$queryParams.value[0] === 1 // the value is an array
``` 
#### Check path and method
```javascript
$path === '/test' && $method === 'POST'
```
#### Use if/else 
```javascript
if ($queryParams.pageIndex >= 0 && $body.x === 'somevalue') {
    true;
} else {
    false;
}
```
#### Use wrap to function
```
function doCheck(){
    if ($queryParams.pageIndex >= 0 && $body.x === 'somevalue') {
        return true;
    } else {
        return false;
    }
}
doCheck();
```
## API
Check `RuleController` and `RequestHistoryController` for more apis
