
CREATE TABLE IF NOT EXISTS authors (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

create table IF NOT EXISTS books(
  id SERIAL PRIMARY KEY,
  name varchar(100) not null,
  price int,
  author_id int,
  FOREIGN KEY (author_id) REFERENCES authors (id)
);
