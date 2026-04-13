DROP DATABASE IF EXISTS wishlistdb;

CREATE DATABASE wishlistdb
	DEFAULT CHARACTER SET utf8mb4;

USE
wishlistdb;

CREATE TABLE profile (
                         profile_id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(50) NOT NULL
);

CREATE TABLE wishlist (
                          wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(50) NOT NULL,
                          description VARCHAR(500),
                          profile_id INT NOT NULL,
                          FOREIGN KEY (profile_id) REFERENCES profile (profile_id)
                              ON DELETE CASCADE
);

CREATE TABLE wish (
                      wish_id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(50) NOT NULL,
                      description VARCHAR(500),
                      location VARCHAR(200),
                      date DATE,
                      price DECIMAL (10,2),
                      url VARCHAR(2083),
                      wishlist_id INT NOT NULL,
                      FOREIGN KEY (wishlist_id) REFERENCES wishlist (wishlist_id)
                          ON DELETE CASCADE
);

CREATE TABLE tag (
                     tag_id INT AUTO_INCREMENT PRIMARY KEY,
                     title VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE wish_tag (
                          wish_id INT NOT NULL,
                          tag_id INT NOT NULL,
                          PRIMARY KEY (wish_id, tag_id),
                          FOREIGN KEY (wish_id) REFERENCES wish (wish_id)
                              ON DELETE CASCADE,
                          FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
                              ON DELETE RESTRICT
);



