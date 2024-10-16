# Banking Microservice

## Overview

This project is a microservice-based banking system built using Java 17, Spring Boot 3.3.4, and WebFlux. The system is
designed to manage user accounts, perform transactions, and handle account management functionalities. It uses MySQL as
the database for storing user, customer accounts and transaction details.

## Services

- User Service: Manages user accounts and account-related operations.
    - Table [user_tbl, customer_account_tbl]
- Transaction Service: Handles money transfers, balance update , including validation and transaction processing.
    - Table [transaction_tbl]

## Features

- User Service:
    - Create User: Allows a user to create a profile.
    - Update User: Modify existing user details.
    - Get User by ID: Retrieve user details by their ID.
    - Get All Users: Retrieve a list of all users.
    - Account Management:
        - Create Account: Allows a user to create their own bank account.
        - Get Account Details: Retrieve account details using the account number.
        - Update Account Status: Update the status of an account (e.g., active, inactive).

- Transaction Service:
    - Transfer Money: Handles the actual transfer of funds between accounts with proper validations.
    - Update Balance: Update account balance.
    - Transaction Logic: Ensures accounts are active and have sufficient balance before completing transactions.

## Technologies

- Java 17
- Spring Boot 3.3.4
- WebFlux
- MySQL

## Prerequisites

- Java 17
- MySQL 8.4.3

## Configuration

    - Database Setup:
      - CREATE DATABASE user;
      - CREATE DATABASE transaction;

## Endpoints

- User Service:
    - Create User:
        - POST /user
    - Update User:
        - PUT /user
    - Get User by ID:
        - GET /user/{id}
    - Get All Users:
        - GET /user
    - Create Account:
        - POST /account
    - Get Account Details by Account Number:
        - GET /account/getAccountDetails/{accNumber}
    - Update Account Status:
        - PUT /account/status-update

- Transaction Service:
    - Transfer Money:
        - POST /transaction/transferAmount
    - Balance update:
        - POST /transaction/transferAmount