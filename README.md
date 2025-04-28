# cash-desk-module

Cash operations module to support deposits, withdrawals, and balance checks for multiple cashiers in BGN and EUR. Each cashier has a starting balance in both currencies.

## Prerequisites
Java 17+

Maven 3.8+

## Configuration
- config/cashiers.json - cashiers initialization
- config/transactions.json - transaction history

## Running the application

1) `mvn clean install`
2) set environment variable for API key - in powershell: `$env:API_KEY_VALUE=api_key`
3) `java -jar .\target\cash-desk-module-0.0.1.jar`

## Postman environment and collection
- cash-desk-module.postman_environment.json
- cash-desk-module.postman_collection.json