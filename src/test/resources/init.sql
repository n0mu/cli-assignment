
CREATE TABLE IF NOT EXISTS SERVERS (
   id VARCHAR(50) NOT NULL PRIMARY KEY,
   name VARCHAR(50) NOT NULL UNIQUE,
   description VARCHAR(200) NOT NULL,
);