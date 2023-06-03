CREATE TABLE todo (
    id BIGINT auto_increment primary key,
    userId BIGINT not null,
    title varchar(50) not null,
    checked bool not null
);

CREATE TABLE user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  email VARCHAR(20) NOT NULL,
  role VARCHAR(20),
  createdAt DATETIME NOT NULL,
  updatedAt DATETIME,
);
