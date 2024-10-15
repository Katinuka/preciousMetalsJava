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
