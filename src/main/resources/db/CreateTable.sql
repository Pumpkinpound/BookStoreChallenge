CREATE TABLE books_list_table (
    book_id INT NOT NULL AUTO_INCREMENT,
    author_name VARCHAR(80),
    book_name VARCHAR(80),
    is_recommended BOOLEAN,
    price INT NOT NULL,
    PRIMARY KEY (book_id),
    UNIQUE (book_name)
);

CREATE TABLE user_table (
    user_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(45),
    surname VARCHAR(45),
    books VARCHAR(45),
    date_of_birth VARCHAR(15),
    PRIMARY KEY (user_id),
    UNIQUE (username)
)