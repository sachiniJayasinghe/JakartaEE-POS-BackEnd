CREATE TABLE customer
(
    cusID      VARCHAR(50) PRIMARY KEY,
    cusName    VARCHAR(255),
    cusAddress VARCHAR(255),
    cusSalary  DECIMAL(10, 2)
);


CREATE TABLE Items (
                       code VARCHAR(50) PRIMARY KEY,
                       name VARCHAR(255),
                       price DECIMAL(10, 2),
                       qty INT
);

CREATE TABLE orders (
                        orderID VARCHAR(50) PRIMARY KEY,
                        orderDate DATE,
                        cusID VARCHAR(10),
                        FOREIGN KEY (cusID) REFERENCES customer(cusID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Order_Detail (
                              code VARCHAR(50),
                              orderID VARCHAR(50),
                              qty INT,
                              price DECIMAL(10, 2),
                              PRIMARY KEY (code, orderID),
                              FOREIGN KEY (code) REFERENCES Items(code) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (orderID) REFERENCES orders(orderID) ON DELETE CASCADE ON UPDATE CASCADE
);
