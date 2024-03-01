DROP TABLE IF EXISTS TBL_USER cascade;

CREATE TABLE TBL_USER (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  birthday DATE DEFAULT NULL
);

DROP TABLE IF EXISTS SUBSCRIPTION_TABLE cascade;

CREATE TABLE SUBSCRIPTION_TABLE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT NOT NULL,
  start_date DATE DEFAULT NULL,
  foreign key (user_id) references TBL_USER(id)
);

INSERT INTO TBL_USER (first_name, last_name, birthday) VALUES
  ('Lokesh', 'Gupta', '2022-12-31'),
  ('Deja', 'Vu', '2022-12-31'),
  ('Caption', 'America', '2022-12-31');