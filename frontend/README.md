# Frontend README

## Overview

RUMBO Frontend is an Angular 17 single-page application with standalone components and modern TypeScript.

## Technology Stack

- **Framework**: Angular 17
- **Language**: TypeScript 5.2
- **Styling**: SCSS
- **Package Manager**: npm
- **HTTP Client**: HttpClientModule
- **Testing**: Jasmine/Karma

## Prerequisites

- Node.js 18 LTS or higher
- npm 9 or higher

## Installation

1. Install dependencies:
```bash
npm install
```

2. Configure environment variables:
```bash
cp .env.example .env
# Edit .env with your configuration
```

## Running the Application

### Development Server
```bash
npm start
```

Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Production Build
```bash
npm run build
```

The build artifacts will be stored in the `dist/` directory.

## Project Structure

```
frontend/
├── src/
│   ├── app/
│   │   ├── components/      # Reusable components
│   │   ├── pages/           # Page components
│   │   ├── services/        # HTTP and data services
│   │   ├── models/          # TypeScript interfaces
│   │   ├── guards/          # Route guards
│   │   ├── interceptors/    # HTTP interceptors
│   │   ├── app.component.*  # Root component
│   │   ├── app.routes.ts    # Route definitions
│   │   └── app.config.ts    # App configuration
│   ├── assets/              # Static files
│   ├── styles.scss          # Global styles
│   ├── main.ts              # Entry point
│   └── index.html           # HTML template
├── .env.example             # Environment variables template
├── package.json             # NPM dependencies
├── angular.json             # Angular CLI configuration
├── tsconfig.json            # TypeScript configuration
└── README.md               # This file
```

## Component Architecture

```
AppComponent
├── LayoutComponent
│   ├── HeaderComponent
│   ├── SidebarComponent
│   └── FooterComponent
└── RouterOutlet (pages)
    ├── DashboardComponent
    ├── AuthComponent
    └── UserComponent
```

## Services

- `AuthService` - Authentication and JWT token management
- `UserService` - User data operations
- `ApiService` - HTTP requests wrapper

## HTTP Interceptors

- `AuthInterceptor` - Adds JWT token to requests
- `ErrorInterceptor` - Handles API errors globally

## Route Guards

- `AuthGuard` - Protects authenticated routes
- `UnauthGuard` - Redirects logged-in users away from auth pages

## Testing

### Run Tests
```bash
npm test
```

### Run Tests with Coverage
```bash
npm run test -- --code-coverage
```

### Run E2E Tests
```bash
npm run e2e
```

## Build and Deployment

### Development Build
```bash
npm run build
```

### Production Build (Optimized)
```bash
npm run build -- --configuration production
```

### Deploy to Server
1. Build the application: `npm run build`
2. Copy `dist/rumbo-frontend` to your server
3. Configure your web server (nginx, Apache, etc.)
4. Set up environment variables on the server

## Environment Configuration

Create a `.env` file in the root directory:

```env
NG_APP_API_URL=http://localhost:5000/api/v1
NG_APP_API_TIMEOUT=30000
NG_APP_ENABLE_DEBUG=true
```

## Code Style

- Follow Angular style guide
- Use strict TypeScript checking
- Format with Prettier (if configured)
- ESLint for code quality

## Troubleshooting

### Port already in use
```bash
npm start -- --port 4300
```

### Module not found error
```bash
# Clear node_modules and reinstall
rm -rf node_modules
npm install
```

### Build errors
```bash
# Clear Angular cache
ng cache clean
npm install
npm run build
```

---
Last updated: 2026-05-04
