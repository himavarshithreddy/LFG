create database food_guider;

use food_guider;

create table users_data(
user_id INT AUTO_INCREMENT PRIMARY KEY,
user_name varchar(255),
user_email varchar(255) NOT NULL unique,
user_password varchar(255) NOT NULL
);



create table restaurants_data(
restaurants_id int Primary key,
restaurants_name varchar(255),
restaurants_adddress varchar(255),
restaurants_description text,
restaurants_phoneNumber int unique,
restaurants_imageLink VARCHAR(255) NOT NULL,
restaurants_rating int,
south_indian bool not null,
north_indian bool not null,
spicy bool not null,
valueformoney bool not null
);



create table menuItems_data(
item_id int Primary key,
item_name varchar(255),
item_imageLink VARCHAR(255) NOT NULL,
restaurants_id int,
FOREIGN KEY (restaurants_id) REFERENCES restaurants_data(restaurants_id),
item_price int NOT NULL,
item_description text,
item_overrallRating int,
item_taseRating int,
item_healthnessRating int ,
item_WorthRating int 
);


create table reviews_data(
review_id int  AUTO_INCREMENT Primary key,
user_id int ,
item_id int ,
FOREIGN KEY (user_id) REFERENCES users_data(user_id),
FOREIGN KEY (item_id) REFERENCES menuItems_data(item_id),
review_text text
);


