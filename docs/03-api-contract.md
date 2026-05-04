# API Contract

## Base URL
`http://localhost:5000/api/v1`

## Authentication
- Method: Bearer Token (JWT)
- Header: `Authorization: Bearer <token>`

## Endpoints

### Health Check
```
GET /health
Response: 200 OK
{
  "status": "healthy",
  "timestamp": "2026-05-04T10:30:00Z"
}
```

### Authentication

#### Register
```
POST /auth/register
Body:
{
  "email": "user@example.com",
  "password": "secure_password",
  "name": "User Name"
}
Response: 201 Created
{
  "success": true,
  "data": {
    "id": "uuid",
    "email": "user@example.com",
    "name": "User Name"
  }
}
```

#### Login
```
POST /auth/login
Body:
{
  "email": "user@example.com",
  "password": "secure_password"
}
Response: 200 OK
{
  "success": true,
  "data": {
    "token": "jwt_token",
    "user": { /* user data */ }
  }
}
```

#### Logout
```
POST /auth/logout
Headers: Authorization: Bearer <token>
Response: 200 OK
{
  "success": true,
  "message": "Logged out successfully"
}
```

### Users

#### Get Current User
```
GET /users/me
Headers: Authorization: Bearer <token>
Response: 200 OK
{
  "success": true,
  "data": { /* user data */ }
}
```

#### Update Profile
```
PUT /users/me
Headers: Authorization: Bearer <token>
Body:
{
  "name": "New Name",
  "email": "newemail@example.com"
}
Response: 200 OK
{
  "success": true,
  "data": { /* updated user data */ }
}
```

## Error Codes

| Code | Message | HTTP Status |
|------|---------|-------------|
| INVALID_CREDENTIALS | Invalid email or password | 401 |
| EMAIL_EXISTS | Email already registered | 409 |
| NOT_FOUND | Resource not found | 404 |
| UNAUTHORIZED | Token missing or invalid | 401 |
| FORBIDDEN | Insufficient permissions | 403 |
| SERVER_ERROR | Internal server error | 500 |

---
Last updated: 2026-05-04
