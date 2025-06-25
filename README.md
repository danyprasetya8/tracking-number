## How to run

### Using terminal

- Make sure mongodb and redis are running or available (if using cloud)
- Execute command below, adjust mongo uri and redis host/port accordingly
- To simulate running multiple instance, run in multiple terminals and add properties `--server.port=8081`

``mvn spring-boot:run -Dspring-boot.run.arguments="--spring.data.mongodb.uri=mongodb://localhost:27017/logistic --spring.data.redis.host=localhost --spring.data.redis.port=6379 --server.port=8081"``

### Using Intellij

- Open TrackingNumberApplication class
- Click play button
- Adjust environment variable in Run/Debug Configuration
  - `MONGO_URI=mongodb://localhost:27017/logistic`
  - `REDIS_HOST=localhost`
  - `REDIS_PORT=6379`

## Documentation
Local: http://localhost:8080/swagger-ui/index.html

Production: https://tracking-number-production-fb94.up.railway.app/swagger-ui/index.html

## How to test
- Create customers using `POST /api/v1/customer`
  - Sample request body ``{
      "name": "RedBox Logistics",
      "slug": "redbox-logistics",
      "code": "RL"
    }``
- Generate next tracking number using `GET /api/v1/next-tracking-number`
  - Sample valid query param value
    - origin_country_id -> MY
    - destination_country_id  -> ID
    - weight -> 1.234
    - created_at -> 2025-06-24T18:07:29
    - customer_id -> uuid can be get from get all customers API
    - customer_name -> RedBox Logistics
    - customer_slug -> redbox-logistics
- Check created tracking number with API `GET /api/v1/tracking-number`

## Concurrency test
- Run multiple app
  - mvn spring-boot:run -Dspring-boot.run.arguments="--spring.data.mongodb.uri=mongodb://localhost:27017/logistic --spring.data.redis.host=localhost --spring.data.redis.port=6379 --server.port=8080"
  - mvn spring-boot:run -Dspring-boot.run.arguments="--spring.data.mongodb.uri=mongodb://localhost:27017/logistic --spring.data.redis.host=localhost --spring.data.redis.port=6379 --server.port=8081"
  - mvn spring-boot:run -Dspring-boot.run.arguments="--spring.data.mongodb.uri=mongodb://localhost:27017/logistic --spring.data.redis.host=localhost --spring.data.redis.port=6379 --server.port=8082"
- Run simple async fetch script on src/main/resources/script.js

## Strategy
- Leverage Redis INCR feature to make tracking number generation fast, can handle concurrent request, and horizontally scalable
- Tracking number format
  - `TX {customerCode} {ddMMyy} {0-padded sequence}`
- Redis key would be `TX {customerCode} {ddMMyy}`
- Redis is making sure the process is atomic so it won't be any problem when the app scale to multiple instances
- For extra security layer, tracking numbers are made as ID which by default has unique index making it impossible to have duplicate entry