# WEX CHALLENGE

### Reference Documentation

This api was built using spring boot with spring data and spring cache api. For simplicity the database is H2 and the cache is Caffeine, that is an in-memory cache.

### Logic

When a get is performed, the api try to get the conversion rate in the treasury api, but first try to get the conversion in cache. Conversion rate of the last 6 month does not change with frequency, so caching this data is a good way to improve performance.

### Requests

It's possible to get a purchase converted to a specific currency in 3 ways:
1. By passing a currency in a request query param: `/purchage/1?currency=BRL`
2. By passing a country name in english in a request query param: `/purchage/1?country=Brazil`
3. By not passing anything. This way the api will get the locale of the request and convert to the corresponding currency.

Saving a purchase is required to specify the description. This validation is made using the validation framework of spring boot. The amount, the minimum accepted is 1.0.

### Database

The database used in this api is an H2 database and the schema is initialized with liquibase.

### Errors response

All the responses are translated for english and portuguese using resource bundle, and it's treated using ResponseEntityExceptionHandler in `GlobalAdviceHandler` class.

### Tests

For simplicity only unit tests was made.

