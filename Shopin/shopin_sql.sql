-- Schema
DROP DATABASE IF EXISTS `shopin`;
CREATE DATABASE `shopin`;

USE `shopin`;

CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(1500) DEFAULT NULL,
  `createdon` datetime(6) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bxknl0djxuyphb2vkcm7u0q9i` (`createdon`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `categories` (
  `id` bigint NOT NULL,
  `category` varchar(50) NOT NULL,
  `colors` varchar(100) DEFAULT NULL,
  `main` varchar(1) NOT NULL,
  `maxprice` float DEFAULT NULL,
  `sizeid` varchar(1) NOT NULL,
  `type` varchar(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(5000) NOT NULL,
  `sku` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h0hexn8iw9tkpvysmkqlpnigh` (`sku`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cart` varchar(10000) NOT NULL,
  `createdon` datetime(6) NOT NULL,
  `email` varchar(50) NOT NULL,
  `total` varchar(10) NOT NULL,
  `address_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gj1pt8hf4xedb5jfij6ja288c` (`createdon`),
  KEY `FKjm6o0lh0tgj5m2tshjbaw5moj` (`address_id`),
  CONSTRAINT `FKjm6o0lh0tgj5m2tshjbaw5moj` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NOT NULL,
  `color` varchar(1) NOT NULL,
  `discount` int NOT NULL,
  `firstpage` bit(1) NOT NULL,
  `image` varchar(6) NOT NULL,
  `live` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` float NOT NULL,
  `sizes` varchar(500) NOT NULL,
  `sku` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fhmd06dsmj6k0n90swsh8ie9g` (`sku`),
  FULLTEXT KEY `idx_products_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(150) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `roles` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('1', 'Sweatshirts', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('2', 'Jeans', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('3', 'Shirts', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('4', 'Pantyhose', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('5', 'Tights and socks', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('6', 'Skirts', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('7', 'Jackets and coats', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('8', 'Home clothes', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('9', 'Tight-fitting pants', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('10', 'Sweaters and cardigans', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('11', 'Dresses', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('12', 'Jackets', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('13', 'Jumpsuits', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('14', 'Bras', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('15', 'Tops', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('16', 'T-shirts', 'f', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('21', 'Boots', 'f', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('22', 'Short boots', 'f', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('23', 'Shoes', 'f', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('24', 'Sport shoes', 'f', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('25', 'Casual shoes', 'f', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('31', 'Jewelry', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('32', 'Watches', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('33', 'Belts', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('34', 'Scarves and shawls', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('35', 'Sunglasses', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('36', 'Glasses frames', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('37', 'Hats and caps', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('38', 'Wallets and keychains', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('46', 'Handbags', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('47', 'Shoulder bags', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('48', 'Envelope bags', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('49', 'Shopper bags', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('50', 'Backpacks', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('51', 'Messenger bags', 'f', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('71', 'Jeans', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('72', 'Sweatshirts', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('73', 'Shirts', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('74', 'Panties and boxers', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('75', 'Jackets and coats', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('76', 'Hoodies', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('77', 'Home clothes', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('78', 'Coats', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('79', 'Pants', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('80', 'Sweaters and cardigans', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('81', 'Jackets', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('82', 'Socks', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('83', 'Trainings', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('84', 'T-shirts', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('85', 'Vests', 'b', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('95', 'Boots', 'b', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('96', 'Shoes', 'b', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('97', 'Sport shoes', 'b', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('98', 'Casual shoes', 'b', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('99', 'Sneakers', 'b', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('110', 'Watches', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('111', 'Belts', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('112', 'Scarves and shawls', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('113', 'Bags', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('114', 'Sport bags', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('115', 'Sunglasses', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('116', 'Wallets and keychains', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('117', 'Hats and caps', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('118', 'Glasses frames', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('119', 'Backpacks', 'b', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('130', 'Home clothes', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('131', 'Jeans', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('132', 'Dresses', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('133', 'Jackets and coats', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('134', 'Underwear', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('135', 'Tight-fitting pants', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('136', 'Sweaters and cardigans', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('137', 'Skirts', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('138', 'Clothing sets', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('139', 'T-shirts and blouses', 'c', 'im', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('150', 'Casual shoes', 'c', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('151', 'Boots', 'c', 'in', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('160', 'Accessory', 'c', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('161', 'Bags', 'c', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('162', 'Hats and caps', 'c', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('163', 'Scarves and shawls', 'c', 'ac', '1', NULL);
INSERT INTO `shopin`.`categories` (`id`, `category`, `main`, `type`, `sizeid`, `colors`) VALUES ('164', 'Gloves', 'c', 'ac', '1', NULL);


INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (1, 5, 1, '/b/im/', 1, 'Product 1', 234.99, '0A00000001', 73, 'a', '_XL-1_XS-1_M-1_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (2, 0, 1, '/b/in/', 1, 'Product 2', 122.9, '0B00000002', 97, 'd', '_120-2_111-2_12-2_M-2_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (3, 0, 1, '/b/ac/', 1, 'Product 3', 122.9, '0B00000003', 111, 'd', '_120-2_111-2_12-2_M-2_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (4, 25, 1, '/f/im/', 1, 'Product 4', 99.99, '0C00000003', 1, 'd', '_120-2_111-2_12-2_M-2_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (5, 15, 1, '/f/in/', 1, 'Product 5', 340.02, '0D00000004', 23, 'b', '_120-3_111-3_12-3_M-3_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (6, 15, 1, '/f/ac/', 1, 'Product 6', 340.02, '0D00000005', 31, 'b', '_120-3_111-3_12-3_M-3_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (7, 0, 1, '/c/im/', 1, 'Product 7', 900.2, '0E00000005', 131, 'o', '_120-1_111-1_12-1_M-1_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (8, 0, 1, '/c/in/', 1, 'Product 8', 900.2, '0E00000006', 150, 'o', '_120-1_111-1_12-1_M-1_');
INSERT INTO `shopin`.`products` (`id`, `discount`, `firstpage`, `image`, `live`, `name`, `price`, `sku`, `category_id`, `color`, `sizes`) VALUES (9, 0, 1, '/c/ac/', 1, 'Product 9', 200.2, '0F00000006', 164, 'c', '_120-4_111-4_12-4_M-4_');

INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0A00000001');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0B00000002');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0B00000003');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0C00000003');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0D00000004');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0D00000005');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0E00000005');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0E00000006');
INSERT INTO `shopin`.`details` (`description`, `sku`) VALUES ('@Product details@ #details 1 #details 2 #details 3 #details 4 #details 5 #details 6 @Composition@ #Exterior: 100% polyester #Inserts: 87% polyester, 13% elastane', '0F00000006');