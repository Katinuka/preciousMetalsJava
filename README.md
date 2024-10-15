# Precious Metals Trading System

## Project Overview

The **Precious Metals Trading System** is a web-based application where users can buy and sell precious metals (like Gold, Silver, Platinum, and Palladium) based on live metal prices. The application tracks the user's balance in USD and the quantity of each metal they own. All transactions (buy and sell) are recorded and displayed to the user as a transaction history.

### Key Features:
1. **Live Metal Prices**: Users can view real-time metal prices.
2. **Buying Metals**: Users can purchase metals based on the live price, which deducts their USD balance and adds to their metal balance.
3. **Selling Metals**: Users can sell metals, which deducts the metal balance and adds the equivalent USD amount to their balance.
4. **Transaction History**: Users can view a history of all their transactions (buy and sell).
5. **Authentication**: The system uses authentication (user login) to allow each user to manage their account securely.
6. **Error Handling**: All actions like buying, selling, and balance updates are performed transactionally, ensuring data consistency in case of failure.

---

## Technical Overview

The Precious Metals Trading System uses the following technologies:

1. **Backend**: Implemented using **Spring Boot**, leveraging Spring Security for authentication and authorization.
2. **Frontend**: Built with **HTML, CSS, JavaScript**, using **Thymeleaf** as the template engine for dynamic rendering.
3. **Database**: The system uses **H2** right now (but will use **MySQL/PostgreSQL** soon) for storing user data, balances, and transaction history.
4. **Live Prices**: Metal prices are fetched from an external Python API and displayed in near real-time to the users.
5. **Transactions**: All transactions are handled in a **transactional** context using Spring's `@Transactional` annotation to ensure data consistency.

---

## System Workflow

1. **User Authentication**: A user must log in to view their balance and metal holdings.
2. **Live Metal Prices**: Users can view live prices of precious metals, which update periodically. 
3. **Buying Metals**: Users can buy metals using their USD balance.
4. **Selling Metals**: Users can sell metals to receive USD.
5. **Transaction History**: Users can view a history of their buy and sell transactions.
6. **Error Handling**: In case of errors, proper validation and error messages are displayed to the user.




# Requirements for Precious Metals Trading System

## Functional Requirements

1. **User Authentication**:
    - Users must be able to log in to view their balances and perform transactions.
    - A registration system may be needed if new users are allowed.

2. **User Balances**:
    - Users should be able to view their current balance in USD and the metals they own.
    - When purchasing or selling metals, their balances should update accordingly.

3. **Live Metal Prices**:
    - The system must provide up-to-date prices for precious metals (Gold, Silver, Platinum, and Palladium).
    - The prices should refresh periodically (e.g., every few seconds or minutes).

4. **Transactions**:
    - Users must be able to **buy** metals (deducting USD and adding metal).
    - Users must be able to **sell** metals (deducting metal and adding USD).
    - Transactions must be **atomic** (all or nothing), ensuring balances and transactions are updated consistently.

5. **Transaction History**:
    - Users should be able to view a history of all their transactions (buy and sell).
    - Each transaction should include the type of action (buy/sell), metal type, quantity, price, and timestamp.

---

## Non-Functional Requirements

1. **Performance**:
    - The system should handle multiple users making transactions simultaneously without significant delays.
    - Metal prices should be fetched and updated in near real-time without performance bottlenecks.

2. **Security**:
    - User data, including their balances and transaction history, should be securely stored and accessible only to authenticated users.
    - Implement proper access control to ensure that only authorized users can view or modify their data.

3. **Reliability**:
    - The system must ensure that all transactions are reliable and consistent, even in case of system failures.
    - Transactions should be **atomic** and **transactional**, meaning if any part of the transaction fails, the entire transaction should be rolled back.

4. **Scalability**:
    - The system should be designed to handle a growing number of users and increasing transaction volumes without performance degradation.

5. **Data Integrity**:
    - The system must ensure that user balances and transaction data remain consistent at all times.

---

## Technical Requirements

1. **Backend**:
    - The backend will be implemented using **Spring Boot**.
    - **JPA/Hibernate** will be used for ORM (Object Relational Mapping) and database access.
    - **MySQL/PostgreSQL** or any relational database will be used to store user information, balances, and transaction history.
    - The system will utilize **REST API** for communication between the frontend and backend.

2. **Frontend**:
    - The frontend will be built using **HTML, CSS, and JavaScript**.
    - **Thymeleaf** will be used as the template engine for dynamic rendering of data.
    - **AJAX** will be used to fetch live metal prices and update transaction history without reloading the page.

3. **Live Metal Prices**:
    - Metal prices will be fetched using an external **API** (see **app.py** in the project folder).

4. **Transactional Support**:
    - All database operations related to balance updates and transactions will be handled in a **transactional** context using Spring’s `@Transactional` annotation.
  



# API Design for Precious Metals Trading System

The API is designed as a RESTful service, allowing the frontend to communicate with the backend to perform actions such as fetching metal prices, buying/selling metals, and retrieving transaction history.

---

## 1. **GET /api/metal-prices**

- **Description**: Fetches the live prices of metals.
- **Response**: A JSON object with the current prices of metals.

#### Response Example:
```json
{
  "Gold": 1800.5,
  "Silver": 24.75,
  "Platinum": 970.25,
  "Palladium": 2400.1
}
```

## 2. **GET /api/users/current**

- **Description**: Retrieves the current user's balance and other information.
- **Response**: A JSON object with the current user's information including their balances.

#### Response Example:
```json
{
  "id": 1,
  "username": "john_doe",
  "roles": "ROLE_USER",
  "balance": {
    "USD": 50000.0,
    "Gold": 10.5,
    "Silver": 50,
    "Platinum": 10,
    "Palladium": 0
  }
}
```

## 3. **POST /buy-metal**

- **Description**: Processes the purchase of metals. Deducts the USD balance and adds the purchased metal to the user's balance.
- **Request**: A JSON object with the metal being purchased, the number of ounces, and the price per ounce.

#### Request Example:
```json
{
  "metal": "Gold",
  "ounces": 5,
  "pricePerOunce": 1800.5
}
```

- **Response**: A JSON object with the status of the transaction and the updated balance.

#### Response Example:
```json
{
  "status": "success",
  "balance": {
    "USD": 40997.5,
    "Gold": 15.5,
    "Silver": 50,
    "Platinum": 10,
    "Palladium": 0
  }
}
```

## 4. **POST /sell-metal**

- **Description**: Processes the selling of metals. Adds to the USD balance and deducts the metal from the user's balance.
- **Request**: A JSON object with the metal being sold, the number of ounces, and the price per ounce.

#### Request Example:
```json
{
  "metal": "Gold",
  "ounces": 2,
  "pricePerOunce": 1800.5
}
```

- **Response**: A JSON object with the status of the transaction and the updated balance.

#### Response Example:
```json
{
  "status": "success",
  "balance": {
    "USD": 44598.5,
    "Gold": 13.5,
    "Silver": 50,
    "Platinum": 10,
    "Palladium": 0
  }
}
```

## 5. **GET /api/transactions/current**

- **Description**: Retrieves the transaction history of the current user.
- **Response**: A JSON array containing a list of transactions (buy/sell) made by the user.

#### Response Example:
```json
[
  {
    "id": 1,
    "user": {
      "id": 1,
      "username": "john_doe",
      "roles": "ROLE_USER",
      "balance": {
        "USD": 44598.5,
        "Gold": 13.5,
        "Silver": 50,
        "Platinum": 10,
        "Palladium": 0
      }
    },
    "metal": "Gold",
    "action": "Buy",
    "ounces": 5,
    "totalPrice": 9002.5,
    "transactionTime": "2024-10-15T14:47:25.048566"
  },
  {
    "id": 2,
    "user": {
      "id": 1,
      "username": "john_doe",
      "roles": "ROLE_USER",
      "balance": {
        "USD": 44598.5,
        "Gold": 13.5,
        "Silver": 50,
        "Platinum": 10,
        "Palladium": 0
      }
    },
    "metal": "Gold",
    "action": "Sell",
    "ounces": 2,
    "totalPrice": 3601,
    "transactionTime": "2024-10-15T14:47:45.999073"
  }
]
```
