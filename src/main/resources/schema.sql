CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL,
    isbn VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS patron (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_information VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS borrowing_record (
    id SERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    patron_id BIGINT NOT NULL,
    borrowing_date DATE NOT NULL,
    return_date DATE,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (id),
    CONSTRAINT fk_patron FOREIGN KEY (patron_id) REFERENCES patron (id)
);
