mysql design
##############hotel_package（包裹）###############
CREATE TABLE IF NOT EXISTS hotel_package(
package_id INT UNSIGNED AUTO_INCREMENT,
package_status VARCHAR(100) NOT NULL,
owner_id VARCHAR(32) NOT NULL,
owner_type VARCHAR(64) NOT NULL,
receive_id  VARCHAR(32) NOT NULL,
receive_type   VARCHAR(64) NOT NULL,
receive_date DATE,
check_out_date  DATE,
PRIMARY KEY (package_id )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

######hotel_customer(客户信息表)#####
CREATE TABLE IF NOT EXISTS hotel_customer(
customer_id INT UNSIGNED AUTO_INCREMENT,
customer_name VARCHAR(100) NOT NULL,
customer_status VARCHAR(32) NOT NULL,
check_in_date  DATE,
check_out_date  DATE,
PRIMARY KEY (customer_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;