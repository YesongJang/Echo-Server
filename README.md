# Echo Server

A modular, socket-based Echo server in Java with a pluggable handler architecture, supporting authentication, caching, computation, and command routing.

## 💡 Features

- **Proxy Server**: Handles all client requests via sockets and delegates processing through a pipeline of handlers
- **Modular Handlers**:
  - `SecurityProxyHandler`: Authenticates users with `UserTable` and `SafeTable`
  - `CacheProxyHandler`: Caches previous command results
  - `MathHandler`: Handles commands like `add 2 3` or `multiply 4 5`
  - `FirewallHandler`: Restricts access based on rules
  - `ProxyHandler`: Base handler interface for flexible routing
- **Client Interface**: `SimpleClient` connects to server and sends commands interactively
- **Extensible Design**: Easy to add new handlers like `UpperCaseHandler`, `TimeHandler`, etc.

## 📁 File Structure

📦 Echo-Server   
├── ProxyServer.java # Main server loop   
├── SimpleClient.java # Console client for testing     
├── ProxyHandler.java # Base handler interface   
├── RequestHandler.java # Request validation & dispatch     
├── SecurityProxyHandler.java # Login/logout/authentication   
├── CacheProxyHandler.java # Caching of repeated results   
├── MathHandler.java # Handles math commands   
├── CasinoHandler.java # Adds bonus logic to results   
├── FirewallHandler.java # Access control logic    
├── UserTable.java # Registered user DB   
├── SafeTable.java # Active session tracker   
├── Correspondent.java # Represents a session/client   

## 🛠 Tech Stack

- Java (Socket API)
- OOP design: Interface-based handler architecture
- In-memory user & cache management

## ▶️ How to Run

### 1. Compile all Java files

```bash
javac *.java
```

### 2. Run the server

```bash
java ProxyServer
```

### 3. In a separate terminal, run the client

```bash
java SimpleClient
```



