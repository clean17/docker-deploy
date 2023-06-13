INSERT INTO todos (user_id, title, done) VALUES
(1, '아침먹고 운동하기', false),
(1, '점심먹고 운동하기', false);

INSERT INTO users (username, password, email, role, created_at, updated_at) VALUES
('son', '$2a$10$XpQDY6Pht.XB1q1URDVAQej1weDXiMOneVia/3MZGYyhMcesC29Ba', 'son@gmail.com', 'USER', now(), null);

INSERT INTO tstables (name) VALUES ('hello');

commit;