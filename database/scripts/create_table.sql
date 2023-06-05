CREATE TABLE todos (
    id BIGINT auto_increment primary key,
    user_id BIGINT not null,
    title varchar(100) not null,
    done boolean not null
);

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  email VARCHAR(100) NOT NULL,
  role VARCHAR(20),
  created_at DATETIME NOT NULL,
  updated_at DATETIME
);

CREATE TABLE tstables (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL UNIQUE
);
