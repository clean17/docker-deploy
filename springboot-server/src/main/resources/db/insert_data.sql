INSERT INTO todos (userId, title, done) VALUES 
(1, '아침먹고 운동하기', false),
(1, '점심먹고 운동하기', false);

INSERT INTO users (username, password, email, role, created_at, updated_at) VALUES
('son', '1234', 'son@gmail.com', 'USER', now(), now());

INSERT INTO tstables (name) VALUES ('hello');

commit;