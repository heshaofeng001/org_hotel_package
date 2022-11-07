一、业务说明
1、CustomerManageFacade
    create：客户注册服务
2、PackageManageFacade
    checkInConsult：包裹寄存校验接口；
        逻辑：1、用户状态校验，用户是否入住校验，
             2、判断是否存在容积大于所有包裹容积总和的，空着的柜子
    batchQueryByCondition：用户批量查询接口
        逻辑：根据手机号、身份证等信息，查询用户信息

    checkIn： 包裹寄存接口；
        逻辑：1、校验
             2、登记包裹信息
             3、分配柜子，并保存柜子使用信息


二、部署说明
1、代码        github      https://github.com/heshaofeng001/org_hotel_package
2、mvn库       aliyun       https://maven.aliyun.com/repository/public  +  私有库
3、docker镜像  aliyun镜像    registry-vpc.cn-shanghai.aliyuncs.com
4、k8s        自建（kuboard界面化）         http://106.15.60.134/kubernetes/studentgateway/namespace/db/workload/view/Deployment/redis
5、应用服务器   aliyun的服务器1台

应用列表
org_hsf_gateway     网关服务    开放80端口，spring cloud gateway应用，通过zookeeper发现web服务
org_hotel_packages  web应用    包裹管理应用
其他应用     。。。。。。。。

PS：
1、由于就一台阿里云服务器，因此各web应用，统一通过org_hsf_gateway对外提供服务（由org_hsf_gateway统一转发HTTP请求）。
2、阿里云容器镜像服务：提供镜像构建服务，可以在线完成：代码下载、mvn编译、docker build及部署


三、测试用例说明
测试用例地址：hotel-packages-start包 ——> test目录下

CustomerTest#testCreateCustomer()：用户注册用例
PackageManageTest#testCheckInConsult()：  包裹寄存咨询用例（判断是否可以寄存，其中含：客户状态、客户未入住、及没有柜子的用例）
PackageManageTest#testCheckIn():   包裹寄存用例


测试注意事项
1、packagecheckinconsult  用户已经初始化进去，因此测试数据不能修改
2、custertest.csv 和 packagecheckin.csv 中的 用户idCardNo需要同步修改

四、数据库设计说明
hotel_customer：客户信息表（含用户入住状态信息）
hotel_cabinet：存储柜表（包裹代收时，需要分配存储柜）
hotel_package：包裹信息表
hotel_cabinet_package_relation：柜子和包裹关系表（便于柜子、用户双向查询包裹信息）
hotel_work_order：工单（由于包裹存储阶段分：包裹登记、包裹存放等环节，因此通过工单串联流程）

sql脚本
##############hotel_package（包裹）###############
CREATE TABLE IF NOT EXISTS hotel_package(
id  INT UNSIGNED AUTO_INCREMENT,
package_id VARCHAR(16) NOT NULL,
package_status VARCHAR(32) NOT NULL,
owner_id VARCHAR(32) NOT NULL,
owner_type VARCHAR(64) NOT NULL,
way_bill_id VARCHAR(64) NOT NULL,
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
id_card_no VARCHAR(32) NOT NULL,
check_in_status VARCHAR(32),
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



##########工单##########
CREATE TABLE IF NOT EXISTS hotel_work_order(
id  INT UNSIGNED AUTO_INCREMENT,
order_id VARCHAR(16) NOT NULL,
order_type VARCHAR(32) NOT NULL,
request_id VARCHAR(128) NOT NULL,
status  VARCHAR(32) NOT NULL,
ext_info  VARCHAR(4000) ,
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;