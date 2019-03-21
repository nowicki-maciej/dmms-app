# User management

##Api

```
GET `/users/current` - returns full data of logged user
PUT `/users/current` - updates currently logged user

GET `/users` - returns list of all users (ADMIN)
GET `/users/{userId}` - returns full data of given user (ADMIN)
POST `/users` - creates new user (ADMIN)
PUT `/users` - updates given user (ADMIN)
DELETE `/users/{userId}` - deletes given user (ADMIN)
```