# Banking Application - Full Stack Project

A complete microservices-based banking application with Spring Boot backend and React TypeScript frontend.

## 🏗️ Architecture

### Backend Microservices (Spring Boot + Spring Cloud)

1. **EurekaDiscoveryService** (Port 8761)
   - Service registry and discovery
   - All services register here for dynamic discovery

2. **Gateway** (Port 8098)
   - API Gateway using Spring Cloud Gateway
   - Single entry point for all client requests
   - Routes requests to appropriate microservices

3. **CompteService** (Port 8095)
   - Account management service
   - MySQL database: `CompteBDD`
   - CRUD operations for bank accounts

4. **TransactionService** (Port 8096)
   - Transaction processing service
   - MySQL database: `TransactionBDD`
   - Handles money transfers between accounts
   - Uses OpenFeign to communicate with CompteService

5. **ReportingService** (Port 8097)
   - Reporting and analytics service
   - Provides currency exchange rates
   - No database (uses external APIs)

### Frontend (React + TypeScript + Tailwind CSS)

- **Port**: 3000
- Modern, responsive UI
- Full CRUD operations for accounts
- Money transfer functionality
- Currency exchange rate viewer

## 🚀 Running the Application

### Prerequisites
- Java 17
- Maven
- Node.js 16+
- MySQL (XAMPP)

### Start Backend Services

```bash
# 1. Start Eureka Discovery Service
cd EurekaDiscoveryService
./mvnw spring-boot:run

# 2. Start Gateway
cd Gateway
./mvnw spring-boot:run

# 3. Start CompteService
cd CompteService
./mvnw spring-boot:run

# 4. Start TransactionService
cd TransactionService
./mvnw spring-boot:run

# 5. Start ReportingService
cd ReportingService
./mvnw spring-boot:run
```

### Start Frontend

```bash
cd banking-frontend
npm install
npm start
```

## 📊 Database Setup

MySQL databases (via XAMPP):
- `CompteBDD` - Stores account information
- `TransactionBDD` - Stores transaction history

Connection:
- Host: localhost:3306
- Username: root
- Password: (empty)

## 🌐 Access Points

- **Frontend**: http://localhost:3000
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8098
- **Accounts API**: http://localhost:8098/comptes
- **Transactions API**: http://localhost:8098/transactions
- **Reporting API**: http://localhost:8098/reporting

## 📡 API Endpoints

### Accounts (CompteService)
- `GET /comptes` - Get all accounts
- `GET /comptes/{id}` - Get account by ID
- `POST /comptes` - Create new account
- `PUT /comptes/{id}` - Update account
- `DELETE /comptes/{id}` - Delete account

### Transactions (TransactionService)
- `POST /transactions/transfer?src={id}&dest={id}&amount={amount}` - Transfer money

### Reporting (ReportingService)
- `GET /reporting/api/rate?from={currency}&to={currency}` - Get exchange rate

## 🛠️ Technology Stack

### Backend
- Spring Boot 4.0.0
- Spring Cloud 2025.1.0
- Spring Data JPA
- MySQL
- Netflix Eureka
- Spring Cloud Gateway
- OpenFeign
- Lombok

### Frontend
- React 19
- TypeScript
- Tailwind CSS
- Axios
- Create React App

## 📁 Project Structure

```
Banking Application/
├── EurekaDiscoveryService/     # Service registry
├── Gateway/                     # API Gateway
├── CompteService/              # Account service
├── TransactionService/         # Transaction service
├── ReportingService/           # Reporting service
├── banking-frontend/           # React frontend
└── pom.xml                     # Parent POM
```

## 🎯 Features

✅ Account Management (Create, Read, Update, Delete)
✅ Money Transfers between accounts
✅ Real-time balance updates
✅ Currency exchange rates
✅ Service discovery and load balancing
✅ Centralized API Gateway
✅ Microservices architecture
✅ Responsive modern UI

## 🔧 Development

### Backend
- Each service is independently deployable
- Services communicate via REST APIs
- Eureka handles service discovery
- Gateway provides unified API endpoint

### Frontend
- Component-based architecture
- TypeScript for type safety
- Tailwind CSS for styling
- Axios for API communication

## 📝 Notes

- All backend services must be running before starting the frontend
- MySQL must be running (XAMPP)
- Databases are auto-created by Hibernate
- Frontend proxies API calls through the Gateway

## 🐛 Troubleshooting

**Services not registering with Eureka:**
- Wait 30 seconds for registration
- Check Eureka dashboard at http://localhost:8761

**Database connection errors:**
- Ensure MySQL is running (XAMPP)
- Check database credentials in application.properties

**CORS errors:**
- Ensure Gateway is running
- Frontend uses proxy configuration

## 📄 License

MIT
