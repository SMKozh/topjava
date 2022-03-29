curl -H "Content-Type: application/json" -X POST -d "{\"dateTime\":\"2022-03-30T00:05:00\",\"description\":\"new zavtrak\",\"calories\":1000}" http://localhost:8080/topjava/rest/meals

curl -H "Content-Type: application/json" -X PUT -d "{\"dateTime\":\"2022-04-05T00:05:00\",\"description\":\"Breakfast\",\"calories\":1000}" http://localhost:8080/topjava/rest/meals/100008

curl http://localhost:8080/topjava/rest/meals

curl http://localhost:8080/topjava/rest/meals/100005

curl -X DELETE http://localhost:8080/topjava/rest/meals/100006

curl "http://localhost:8080/topjava/rest/meals/between?startDate=2020-01-31&startTime=10:00:00&endDate=&endTime=20:00:00"