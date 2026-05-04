# RUMBO

Full-stack application with Java Spring Boot backend and Angular frontend.

## Project Structure

```
RUMBO/
├── backend/          # Spring Boot application
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/         # Angular application
│   ├── src/
│   ├── package.json
│   └── ...
└── README.md
```

## Backend Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Getting Started

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

## Frontend Setup

### Prerequisites
- Node.js 18 or higher
- npm or yarn

### Getting Started

```bash
cd frontend
npm install
npm start
```

The frontend will start on `http://localhost:4200`

## Development

### Backend
- Main class: `src/main/java/com/rumbo/RumboApplication.java`
- Configuration: `src/main/resources/application.properties`

### Frontend
- Main component: `src/app/app.component.ts`
- Styles: `src/app/app.component.scss`

## Database

The backend uses H2 in-memory database by default. Access the H2 console at:
`http://localhost:8080/h2-console`

## License

MIT
