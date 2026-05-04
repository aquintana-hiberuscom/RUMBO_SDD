# Backend README

## Overview

RUMBO Backend is a Flask-based REST API built with Python 3.9+.

## Technology Stack

- **Framework**: Flask 2.3
- **ORM**: SQLAlchemy
- **Database**: PostgreSQL
- **Authentication**: JWT (Flask-JWT-Extended)
- **API Documentation**: [Swagger/OpenAPI]
- **Testing**: pytest

## Prerequisites

- Python 3.9 or higher
- PostgreSQL 12 or higher
- pip (Python package manager)

## Installation

1. Create a virtual environment:
```bash
python3 -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. Install dependencies:
```bash
pip install -r requirements.txt
```

3. Configure environment variables:
```bash
cp .env.example .env
# Edit .env with your configuration
```

4. Initialize the database:
```bash
flask db upgrade
```

## Running the Application

### Development Mode
```bash
flask run
```

The API will be available at `http://localhost:5000`

### Production Mode
```bash
gunicorn -w 4 -b 0.0.0.0:5000 app:app
```

## Project Structure

```
backend/
├── app/
│   ├── __init__.py          # Flask app initialization
│   ├── models/              # Database models
│   ├── routes/              # API endpoints
│   ├── schemas/             # Marshmallow schemas
│   ├── services/            # Business logic
│   └── utils/               # Utility functions
├── tests/
│   ├── __init__.py
│   ├── test_auth.py
│   ├── test_users.py
│   └── conftest.py          # pytest configuration
├── .env.example             # Environment variables template
├── requirements.txt         # Python dependencies
└── README.md               # This file
```

## API Documentation

See [docs/03-api-contract.md](../docs/03-api-contract.md) for detailed API contract.

### Base URL
```
http://localhost:5000/api/v1
```

### Key Endpoints
- `GET /health` - Health check
- `POST /auth/register` - User registration
- `POST /auth/login` - User login
- `POST /auth/logout` - User logout
- `GET /users/me` - Get current user
- `PUT /users/me` - Update user profile

## Testing

Run all tests:
```bash
pytest
```

Run with coverage:
```bash
pytest --cov=app --cov-report=html
```

Run specific test file:
```bash
pytest tests/test_auth.py
```

## Database Migrations

Create a new migration:
```bash
flask db migrate -m "Description of changes"
```

Apply migrations:
```bash
flask db upgrade
```

## Logging

Logs are configured in the `.env` file. Default level is INFO.

## Troubleshooting

### Database connection error
- Ensure PostgreSQL is running
- Check DATABASE_URL in .env
- Verify database credentials

### Port already in use
```bash
# Change port in flask run command
flask run --port 5001
```

### Module not found error
- Ensure virtual environment is activated
- Run `pip install -r requirements.txt`

---
Last updated: 2026-05-04
