# Requirements for Precious Metals Trading System

This document outlines the functional, non-functional, and technical requirements for the Precious Metals Trading System.

---

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