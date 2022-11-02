mysql design
##############hotel_package（包裹）###############
CREATE TABLE IF NOT EXISTS hotel_package(
id  INT UNSIGNED AUTO_INCREMENT,
package_id VARCHAR(16) NOT NULL,
package_status VARCHAR(32) NOT NULL,
owner_id VARCHAR(32) NOT NULL,
owner_type VARCHAR(64) NOT NULL,
receive_id  VARCHAR(32) NOT NULL,
receive_type   VARCHAR(64) NOT NULL,
receive_date DATE,
check_out_date  DATE,
PRIMARY KEY (id )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

######hotel_customer(客户信息表)#####
CREATE TABLE IF NOT EXISTS hotel_customer(
id  INT UNSIGNED AUTO_INCREMENT,
customer_id VARCHAR(16) NOT NULL,
customer_name VARCHAR(100) NOT NULL,
customer_status VARCHAR(32) NOT NULL,
check_in_date  DATE,
check_out_date  DATE,
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


###########柜子（存放包裹）#############
CREATE TABLE IF NOT EXISTS hotel_cabinet(
id  INT UNSIGNED AUTO_INCREMENT,
cabinet_id VARCHAR(16) NOT NULL,
size VARCHAR(32) NOT NULL,
cabinet_status VARCHAR(32) NOT NULL,
number  VARCHAR(32) NOT NULL,
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;