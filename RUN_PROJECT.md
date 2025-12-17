# 🚀 Commands to Run Banking Application

## 📋 Prerequisites Checklist

Before starting, ensure you have:
- ✅ **Java 17+** installed (`java -version`)
- ✅ **Maven 3.6+** installed (`mvn -version`)
- ✅ **Node.js 16+** and npm installed (`node -version`)
- ✅ **MySQL** running (via XAMPP or standalone)
- ✅ **Git** (optional, for version control)

---

## 🗄️ Step 1: Start MySQL Database

### Option A: Using XAMPP (Recommended)
```bash
# Start XAMPP Control Panel
# Click "Start" on MySQL module
```

### Option B: Using MySQL Service (Windows)
```powershell
# Start MySQL service
net start MySQL80

# Or via Services app
services.msc
# Find MySQL80 → Right-click → Start
```

### Create Databases
```bash
# Open MySQL command line or phpMyAdmin
C:\xampp\mysql\bin\mysql.exe -u root

# Create databases
CREATE DATABASE IF NOT EXISTS CompteBDD;
CREATE DATABASE IF NOT EXISTS TransactionBDD;
SHOW DATABASES;
exit;
```

---

## 🎯 Step 2: Start Backend Services (In Order)

### Terminal 1: Eureka Discovery Service (START FIRST!)
```powershell
cd "C:\Users\Samad\Desktop\Banking Application\EurekaDiscoveryService"
./mvnw.cmd spring-boot:run

# Wait for: "Started EurekaDiscoveryServiceApplication"
# Should see: "Tomcat started on port 8761"
```

**⏱️ Wait 30 seconds** for Eureka to fully initialize before starting other services.

---

### Terminal 2: CompteService
```powershell
cd "C:\Users\Samad\Desktop\Banking Application\CompteService"
./mvnw.cmd spring-boot:run

# Wait for: "Started CompteServiceApplication"
# Should see: "Registered instance COMPTE-SERVICE"
```

---

### Terminal 3: TransactionService
```powershell
cd "C:\Users\Samad\Desktop\Banking Application\TransactionService"
./mvnw.cmd spring-boot:run

# Wait for: "Started TransactionServiceApplication"
# Should see: "Registered instance TRANSACTION-SERVICE"
```

---

### Terminal 4: ReportingService
```powershell
cd "C:\Users\Samad\Desktop\Banking Application\ReportingService"
./mvnw.cmd spring-boot:run

# Wait for: "Started ReportingServiceApplication"
# Should see: "Registered instance REPORTING-SERVICE"
```

---

### Terminal 5: Gateway (Optional - Currently not used)
```powershell
cd "C:\Users\Samad\Desktop\Banking Application\Gateway"
./mvnw.cmd spring-boot:run

# Wait for: "Started GatewayApplication"
```

---

## 🎨 Step 3: Start Frontend

### Terminal 6: React Application
```powershell
cd "C:\Users\Samad\Desktop\Banking Application\banking-frontend"

# First time only - Install dependencies
npm install

# Start development server
npm start

# Browser should open automatically at http://localhost:3000
```

---

## ✅ Step 4: Verify Everything is Running

### Check Services Status

**Open in browser:**
```
Eureka Dashboard:    http://localhost:8761
Frontend App:        http://localhost:3000
CompteService:       http://localhost:8095/comptes
TransactionService:  http://localhost:8096
ReportingService:    http://localhost:8097/api/rate?from=USD&to=EUR
```

**PowerShell verification:**
```powershell
# Test all services
Invoke-WebRequest -Uri "http://localhost:8761" -UseBasicParsing
Invoke-WebRequest -Uri "http://localhost:8095/comptes" -UseBasicParsing
Invoke-WebRequest -Uri "http://localhost:8096" -UseBasicParsing
Invoke-WebRequest -Uri "http://localhost:8097/api/rate?from=USD&to=EUR" -UseBasicParsing
Invoke-WebRequest -Uri "http://localhost:3000" -UseBasicParsing
```

---

## 🎮 Step 5: Test the Application

### 1. Create Accounts
Go to http://localhost:3000

**Account 1:**
- Currency: USD
- Balance: 1000
- Click "Create Account"

**Account 2:**
- Currency: EUR
- Balance: 500
- Click "Create Account"

**Account 3:**
- Currency: MAD
- Balance: 10000
- Click "Create Account"

### 2. Transfer Money (with Currency Conversion)
- From: Account #1 (USD)
- To: Account #2 (EUR)
- Amount: 100
- Click "Transfer"

**Expected Result:**
- Account #1: 900 USD (deducted 100)
- Account #2: ~592 EUR (added ~92 EUR converted)

### 3. Check Transaction History
Scroll down to see all transactions in the table.

### 4. Check Exchange Rate
- From: USD
- To: MAD
- Click "Get Exchange Rate"

---

## 🛑 Stop All Services

### Stop Backend Services
Press `Ctrl + C` in each terminal running a service.

### Stop Frontend
Press `Ctrl + C` in the terminal running `npm start`.

### Stop MySQL (if needed)
```powershell
# Via XAMPP: Click "Stop" on MySQL
# Or via command:
net stop MySQL80
```

---

## 🔄 Restart Services (Quick Commands)

### PowerShell Script to Start All Services
```powershell
# Save as start-all.ps1

# Start Eureka
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'C:\Users\Samad\Desktop\Banking Application\EurekaDiscoveryService'; ./mvnw.cmd spring-boot:run"

# Wait 30 seconds
Start-Sleep -Seconds 30

# Start other services
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'C:\Users\Samad\Desktop\Banking Application\CompteService'; ./mvnw.cmd spring-boot:run"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'C:\Users\Samad\Desktop\Banking Application\TransactionService'; ./mvnw.cmd spring-boot:run"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'C:\Users\Samad\Desktop\Banking Application\ReportingService'; ./mvnw.cmd spring-boot:run"

# Wait for services to start
Start-Sleep -Seconds 30

# Start frontend
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'C:\Users\Samad\Desktop\Banking Application\banking-frontend'; npm start"

Write-Host "All services starting... Check each window for status."
```

**Run the script:**
```powershell
./start-all.ps1
```

---

## 🐛 Troubleshooting

### Problem: Port Already in Use
```powershell
# Find process using port 8095 (example)
netstat -ano | findstr :8095

# Kill process by PID
taskkill /PID <PID> /F
```

### Problem: MySQL Connection Failed
```bash
# Check MySQL is running
netstat -ano | findstr :3306

# Verify databases exist
C:\xampp\mysql\bin\mysql.exe -u root -e "SHOW DATABASES;"

# Should see: CompteBDD and TransactionBDD
```

### Problem: Service Not Registering with Eureka
1. Ensure Eureka started first
2. Wait 30 seconds after Eureka starts
3. Check Eureka dashboard: http://localhost:8761
4. Restart the service if needed

### Problem: CORS Errors in Frontend
- Ensure all backend services are running
- Check browser console (F12) for specific errors
- Verify services have CORS configuration

### Problem: Maven Build Fails
```powershell
# Clean and rebuild
./mvnw.cmd clean install

# Skip tests if needed
./mvnw.cmd clean install -DskipTests
```

### Problem: npm Install Fails
```powershell
# Clear cache
npm cache clean --force

# Delete node_modules and reinstall
Remove-Item -Recurse -Force node_modules
Remove-Item package-lock.json
npm install
```

---

## 📊 Service Ports Reference

| Service | Port | URL | Status Check |
|---------|------|-----|--------------|
| Eureka Discovery | 8761 | http://localhost:8761 | Dashboard |
| CompteService | 8095 | http://localhost:8095/comptes | GET /comptes |
| TransactionService | 8096 | http://localhost:8096 | POST /transactions/transfer |
| ReportingService | 8097 | http://localhost:8097/api/rate | GET /api/rate |
| Gateway | 8098 | http://localhost:8098 | (Optional) |
| React Frontend | 3000 | http://localhost:3000 | Main App |
| MySQL | 3306 | localhost:3306 | Database |

---

## 🎓 For Presentation/Demo

### Quick Start Order (5 minutes)
```
1. Start MySQL (XAMPP)
2. Start Eureka (Terminal 1)
3. Wait 30 seconds ⏱️
4. Start CompteService (Terminal 2)
5. Start TransactionService (Terminal 3)
6. Start ReportingService (Terminal 4)
7. Wait 20 seconds ⏱️
8. Start Frontend (Terminal 5)
9. Open http://localhost:3000
```

### Demo Flow (5 minutes)
```
1. Show Eureka Dashboard (all services registered)
2. Create 2 accounts (USD and MAD)
3. Transfer money with conversion
4. Show transaction history
5. Check exchange rate
6. Show updated balances
```

---

## 📝 Development Commands

### Backend

**Clean build:**
```powershell
./mvnw.cmd clean install
```

**Run tests:**
```powershell
./mvnw.cmd test
```

**Package JAR:**
```powershell
./mvnw.cmd package
```

**Run JAR:**
```powershell
java -jar target/CompteService-0.0.1-SNAPSHOT.jar
```

### Frontend

**Install dependencies:**
```powershell
npm install
```

**Start dev server:**
```powershell
npm start
```

**Build for production:**
```powershell
npm run build
```

**Run tests:**
```powershell
npm test
```

**Check for issues:**
```powershell
npm run lint
```

---

## 🔐 Database Access

### MySQL Command Line
```bash
C:\xampp\mysql\bin\mysql.exe -u root

USE CompteBDD;
SELECT * FROM comptes;

USE TransactionBDD;
SELECT * FROM transaction;
```

### phpMyAdmin
```
http://localhost/phpmyadmin
Username: root
Password: (empty)
```

---

## 📦 Project Structure

```
Banking Application/
├── EurekaDiscoveryService/    # Port 8761 - Service Registry
├── CompteService/              # Port 8095 - Account Management
├── TransactionService/         # Port 8096 - Transactions
├── ReportingService/           # Port 8097 - Exchange Rates
├── Gateway/                    # Port 8098 - API Gateway (Optional)
├── banking-frontend/           # Port 3000 - React UI
├── DESCRIPTION_PROJET_FR.md    # French Documentation
├── OPTIMIZATIONS_BONUS.md      # Bonus Optimizations Guide
└── RUN_PROJECT.md             # This file
```

---

## ✨ Quick Reference

### Start Everything (Manual)
```powershell
# Terminal 1
cd EurekaDiscoveryService && ./mvnw.cmd spring-boot:run

# Terminal 2 (wait 30s)
cd CompteService && ./mvnw.cmd spring-boot:run

# Terminal 3
cd TransactionService && ./mvnw.cmd spring-boot:run

# Terminal 4
cd ReportingService && ./mvnw.cmd spring-boot:run

# Terminal 5 (wait 20s)
cd banking-frontend && npm start
```

### Check All Services
```powershell
# PowerShell one-liner
@(8761, 8095, 8096, 8097, 3000) | ForEach-Object { 
    try { 
        $status = (Invoke-WebRequest "http://localhost:$_" -UseBasicParsing -TimeoutSec 2).StatusCode
        Write-Host "Port $_: UP ($status)" -ForegroundColor Green
    } catch { 
        Write-Host "Port $_: DOWN" -ForegroundColor Red
    }
}
```

---

## 🎉 Success Indicators

You'll know everything is working when:

✅ Eureka Dashboard shows 3-4 registered services
✅ Frontend loads at http://localhost:3000
✅ You can create accounts
✅ You can transfer money between currencies
✅ Transaction history displays
✅ Exchange rates load
✅ No errors in browser console (F12)

---

**Your banking application is now ready to use!** 🏦💱🚀

For questions or issues, check the troubleshooting section above.
