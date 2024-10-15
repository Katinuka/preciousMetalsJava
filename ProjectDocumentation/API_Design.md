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