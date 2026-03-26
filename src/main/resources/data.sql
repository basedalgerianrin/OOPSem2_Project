-- Seed users for demo (plain text passwords - intentionally vulnerable)
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
INSERT INTO users (username, password, role) VALUES ('jsmith', 'demo1234', 'USER');
INSERT INTO users (username, password, role) VALUES ('alice', 'password', 'USER');
