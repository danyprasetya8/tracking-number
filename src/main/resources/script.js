// Adjust Customer ID accordingly
const requests = [];

for (let i = 0; i < 100; i++) {
  requests.push(
    fetch('http://localhost:8080/api/v1/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2025-06-24T18%3A07%3A29&customer_name=name&customer_slug=slug&customer_id=<REPLACE_WITH_CUSTOMER_ID>')
  )
  requests.push(
    fetch('http://localhost:8081/api/v1/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2025-06-24T18%3A07%3A29&customer_name=name&customer_slug=slug&customer_id=<REPLACE_WITH_CUSTOMER_ID>')
  )
  requests.push(
    fetch('http://localhost:8082/api/v1/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2025-06-24T18%3A07%3A29&customer_name=name&customer_slug=slug&customer_id=<REPLACE_WITH_CUSTOMER_ID>')
  )
}

Promise.all(requests)
  .then(() => console.log('All requests done'));