#Create databases
CREATE DATABASE recipes_dev;
CREATE DATABASE recipes_prd;

#Create users
CREATE USER 'recipes_dev_user'@'localhost' IDENTIFIED BY 'recipes_dev';
CREATE USER 'recipes_prd_user'@'localhost' IDENTIFIED BY 'recipes_prd';
CREATE USER 'recipes_dev_user'@'%' IDENTIFIED BY 'recipes_dev';
CREATE USER 'recipes_prd_user'@'%' IDENTIFIED BY 'recipes_prd';

#Assign permissions
GRANT SELECT ON recipes_dev.* TO 'recipes_dev_user'@'localhost';
GRANT INSERT ON recipes_dev.* TO 'recipes_dev_user'@'localhost';
GRANT DELETE ON recipes_dev.* TO 'recipes_dev_user'@'localhost';
GRANT UPDATE ON recipes_dev.* TO 'recipes_dev_user'@'localhost';

GRANT SELECT ON recipes_prd.* TO 'recipes_prd_user'@'localhost';
GRANT INSERT ON recipes_prd.* TO 'recipes_prd_user'@'localhost';
GRANT DELETE ON recipes_prd.* TO 'recipes_prd_user'@'localhost';
GRANT UPDATE ON recipes_prd.* TO 'recipes_prd_user'@'localhost';

GRANT SELECT ON recipes_dev.* TO 'recipes_dev_user'@'%';
GRANT INSERT ON recipes_dev.* TO 'recipes_dev_user'@'%';
GRANT DELETE ON recipes_dev.* TO 'recipes_dev_user'@'%';
GRANT UPDATE ON recipes_dev.* TO 'recipes_dev_user'@'%';

GRANT SELECT ON recipes_prd.* TO 'recipes_prd_user'@'%';
GRANT INSERT ON recipes_prd.* TO 'recipes_prd_user'@'%';
GRANT DELETE ON recipes_prd.* TO 'recipes_prd_user'@'%';
GRANT UPDATE ON recipes_prd.* TO 'recipes_prd_user'@'%';