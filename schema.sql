CREATE DATABASE IF NOT EXISTS book_db;
USE book_db;

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    isbn VARCHAR(20) UNIQUE,
    price DECIMAL(10,2),
    published_year INT
);
