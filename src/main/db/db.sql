create database if not exists oj_database;

use oj_database;

drop table if exists oj_table;

create table oj_table(
    id int primary key auto_increment comment '题目的id，自增主键',
    title varchar(50) comment '题目的标题',
    level varchar(20) comment  '题目的难度',
    description varchar(4096) comment '题目描述',
    template varchar(4096) comment '编程的初始模板',
    testCode varchar(4096) comment '编程题目的测试用例'
);

drop table if exists user;

create table user(
     id int primary key auto_increment comment '用户id',
     username varchar(20) comment '用户名',
     password  varchar(4096) comment '用户密码',
     salt varchar(1024) comment '盐值'
);