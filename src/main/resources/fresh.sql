USE fresh;

INSERT INTO manager VALUES
	('1','Manager 1'),
    ('2','Manager 2'),
    ('3','Manager 3'),
    ('4','Manager 4'),
    ('5','Manager 5');

INSERT INTO section VALUES 
	('1','3000','0','Fresh'),
    ('2','2000','1','Cooler'),
    ('3','1000','2','Frozen');

INSERT INTO seller VALUES 
	('1','Seller 1'),
    ('2','Seller 2'),
    ('3','Seller 3'),
    ('4','Seller 4'),
    ('5','Seller 5');

INSERT INTO warehouse VALUES 
	('1','10000','1'),
    ('2','15000','2'),
    ('3','30000','3'),
    ('4','5000','4'),
    ('5','50000','5');

INSERT INTO product_advertising
(product_id, description, fabrication_date, fabrication_time, product_name, seller_id, category, product_price) VALUES
	(1, 'testes', '2022-11-20', '2022-11-20 13:55:00', 'Maçã', 1, 0, 2.00),
    (2, 'testes', '2022-11-20', '2022-11-20 13:55:00', 'Banana', 2, 0, 3.00),
    (3, 'testes', '2022-10-20', '2022-10-20 13:55:00', 'Pera', 3, 0, 5.00),
    (4, 'testes', '2022-10-20', '2022-10-20 13:55:00', 'Morango', 4, 0, 5.00),
    (5, 'testes', '2022-10-20', '2022-10-20 13:55:00', 'Uva', 5, 0, 5.00),
    (6, 'testes', '2022-09-20', '2022-09-20 13:55:00', 'Queijo', 1, 1, 20.00),
    (7, 'testes', '2022-09-20', '2022-09-20 13:55:00', 'Leite Pasteurizado', 2, 1, 4.00),
    (8, 'testes', '2022-09-20', '2022-09-20 13:55:00', 'Iogurte Natural', 3, 1, 2.00),
    (9, 'testes', '2022-09-20', '2022-09-20 13:55:00', 'Manteiga', 4, 1, 8.00),
    (10, 'testes', '2022-09-20', '2022-09-20 13:55:00', 'Queijo Prato', 5, 1, 4.00),
    (11, 'testes', '2021-10-20', '2021-10-09 13:55:00', 'Polpa de Morango', 1, 2, 2.00),
    (12, 'testes', '2021-10-20', '2021-10-09 13:55:00', 'Polpa de Acerola', 2, 2, 2.00),
    (13, 'testes', '2021-10-20', '2021-10-09 13:55:00', 'Polpa de Maracujá', 3, 2, 2.00),
    (14, 'testes', '2021-10-20', '2021-10-09 13:55:00', 'Polpa de Graviola', 4, 2, 2.00),
    (15, 'testes', '2021-10-20', '2021-10-09 13:55:00', 'Polpa de Abacaxi', 5, 2, 2.00);
   
INSERT INTO buyer VALUES 
	('1', 'Buyer 1', 'buyer1@email.com'),
	('2', 'Buyer 2', 'buyer2@email.com.br'),
	('3', 'Buyer 3', 'buyer3@email.com'),
	('4', 'Buyer 4', 'buyer4@email.com.br'),
	('5', 'Buyer 5', 'buyer5@email.com');
    
INSERT INTO batch VALUES
    ('1', '10', '2023-01-15', '2022-11-30', '2022-11-30 11:43:00', '150.00', '10', '30', '1'),
    ('2', '10', '2023-02-25', '2022-11-30', '2022-11-30 11:43:00', '150.00', '50', '30', '1');

INSERT INTO shopping_cart VALUES
	('1', '2022-11-18', '0', '10', '1');
    
INSERT INTO inbound_order VALUES
	('1', '2022-11-09', '1', '1');
    
INSERT INTO inbound_order_batch_list VALUES
	('1', '1'),
	('1', '2');

INSERT INTO cart_product VALUES
	('1', '5', '1', '1');







