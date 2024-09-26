# jetty-terminate-connection-replication


# Launch OpenResty
docker run -p 80:80 -v $PWD/nginx/conf.d:/etc/nginx/conf.d openresty/openresty:alpine


Same endpoint for both setups: curl -v http://localhost:80/terminate
