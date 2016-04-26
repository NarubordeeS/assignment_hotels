# assignment_hotels
prerequisite
* install java 1.8.x
* install gradle

How to execute
* git clone
* cd assignment_hotels
* customize hotels in src/main/resources/hoteldb.csv (Optional)
* customize api key in src/main/resources/memberdb.csv (Optional)
* gradle clean bootRun
* service will be run with port 8080

How to run unit-test
* cd assignment_hotels
* gradle clean test jacoco
* cd build/reports/jacoco/html
* open index.html

API Document
  localhost:8080/api/v1/hotels/{apikey}?id={hotel_id}&city={city_name}&sort={sort_key}&direction={direction}
  * apikey is require field
  * hotel_id is optional field
  * city_name is optional field
  * sort_key is optional field which is price
  * direction is optional field which are asc, desc
  note that result is intersected between hotel_id and city_name

  Example 
    - http://localhost:8080/api/v1/hotels/AAAAA
    - http://localhost:8080/api/v1/hotels/AAAAA?id=1
    - http://localhost:8080/api/v1/hotels/AAAAA?city=Bangkok
    - http://localhost:8080/api/v1/hotels/AAAAA?city=Bangkok&id=14&sort=price&direction=asc
    - http://localhost:8080/api/v1/hotels/AAAAA?city=Bangkok&sort=price&direction=desc
