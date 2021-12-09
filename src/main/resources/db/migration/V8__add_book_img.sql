ALTER TABLE IF EXISTS book
ADD COLUMN filename varchar(255) NOT NULL DEFAULT('default.jpg');