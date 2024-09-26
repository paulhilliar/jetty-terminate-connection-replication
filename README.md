# Replicate Jetty terminate connection query
https://github.com/jetty/jetty.project/issues/12305

To run Jetty, import the project into IntelliJ and launch JettyTestServer 

Then hit the endpoint: 
```
curl -v http://localhost:80/terminate
```

On the OpenResty side, launch OpenResty using:
```
docker run -p 80:80 -v $PWD/nginx/conf.d:/etc/nginx/conf.d openresty/openresty:alpine
```

Then hit that same endpoint:
```
curl -v http://localhost:80/terminate
```


You see the same in both cases (other than header differences).  This is giving the same behaviour.
```
* Request completely sent off
< HTTP/1.1 200 OK
< Server: Jetty(12.0.13)
< Date: Thu, 26 Sep 2024 18:36:04 GMT
< foo: bar
< Transfer-Encoding: chunked
< 
response body line 1
* transfer closed with outstanding read data remaining
* Closing connection
curl: (18) transfer closed with outstanding read data remaining
```