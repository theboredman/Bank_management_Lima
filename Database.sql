CREATE DATABASE IF NOT EXISTS bankmanagementsystem;

USE BankManagementSystem;

CREATE TABLE signup (
    formNo VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    fname VARCHAR(100),
    dob DATE,
    gender VARCHAR(10),
    email VARCHAR(100),
    maritalStatus VARCHAR(20),
    address VARCHAR(200),
    city VARCHAR(50),
    pin VARCHAR(10),
    state VARCHAR(50)
);


CREATE TABLE login (
    formno VARCHAR(20) PRIMARY KEY,
    card_number VARCHAR(20),
    pin VARCHAR(20)
);

CREATE TABLE bank (
    pin VARCHAR(4),
    date DATETIME,
    type VARCHAR(20),
    amount DECIMAL(10, 2)
);



CREATE TABLE signuptwo (
    formno VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    father VARCHAR(50),
    mother VARCHAR(50),
    dob DATE,
    address VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    pincode VARCHAR(10),
    phone VARCHAR(15),
    email VARCHAR(50)
);

CREATE TABLE signupthree (
    formno VARCHAR(50) PRIMARY KEY,
    account_type VARCHAR(50),
    card_number VARCHAR(16),
    pin VARCHAR(4),
    services_required VARCHAR(255),
    FOREIGN KEY (formno) REFERENCES signuptwo(formno)
);
