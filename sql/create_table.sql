-- 创建库
create database if not exists api;

-- 切换库
use api;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '用户' collate = utf8mb4_unicode_ci;



-- 接口信息表
create table if not exists interface_info
(
    id             bigint                             not null auto_increment primary key comment 'id',
    name           varchar(256)                       not null comment '名称',
    description    text                               null comment '描述',
    url            varchar(512)                       not null comment '接口地址',
    requestHeader  text                               null comment '请求头',
    responseHeader text                               null comment '响应头',
    status         int      default 0                 not null comment '接口状态：0-关闭，1-开启',
    method         varchar(256)                       not null comment '请求类型',
    createUser     bigint                             not null comment '创建人',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除'
) comment '接口信息';

insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('孙烨磊', 'cSOk', 'www.kamilah-heaney.net', '黄思聪', '郭昊强', '0', '谭瑞霖', 6574);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('张语堂', '4L5', 'www.alvin-spencer.net', '孟思聪', '董绍辉', '0', '薛俊驰', 29779);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('姜嘉熙', '2qB', 'www.gregoria-braun.org', '孙熠彤', '赵果', '0', '萧擎宇', 178);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('许果', 'Jn8', 'www.keenan-kohler.net', '秦晋鹏', '石立轩', '0', '孙明哲', 53336);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('覃烨华', 'GR', 'www.curt-hane.name', '马昊焱', '钟旭尧', '0', '钟昊焱', 1758);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('田鸿煊', 'S2e', 'www.terrence-rippin.net', '郝黎昕', '高智辉', '0', '冯博涛', 9061418991);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('段展鹏', 'I2wb', 'www.ashley-connelly.com', '韦鹭洋', '谢鑫鹏', '0', '邱嘉懿', 49770);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('贺晟睿', 'Bj', 'www.cathrine-hermiston.net', '孟修杰', '冯雪松', '0', '许凯瑞', 30719605);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('廖梓晨', 'SV', 'www.edison-roberts.info', '卢伟泽', '梁雪松', '0', '韩旭尧', 8152);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('孙子默', '97Pnb', 'www.veola-block.net', '石皓轩', '宋伟宸', '0', '方健柏', 16820);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('黎子骞', 'YF', 'www.lacey-ankunding.io', '孙烨霖', '阎智辉', '0', '苏聪健', 6827376976);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('武鹏涛', 'XcpOZ', 'www.rod-swift.com', '林皓轩', '莫晟睿', '0', '萧晟睿', 748352);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('孔晋鹏', 'uAbl', 'www.pilar-nolan.biz', '吕志泽', '邓文轩', '0', '万智辉', 390556);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('陈懿轩', '65O', 'www.brad-kertzmann.org', '蒋嘉熙', '雷立辉', '0', '叶文', 238486);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('冯梓晨', 'Di9qk', 'www.benton-kling.co', '江金鑫', '顾钰轩', '0', '孟鹭洋', 842);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('宋鹏', 'jbO', 'www.roxanna-leannon.name', '雷煜祺', '韦明杰', '0', '陶嘉熙', 1944128212);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('田昊然', 'FM6En', 'www.norman-runolfsson.info', '方雨泽', '范子默', '0', '龚伟祺', 38869);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('宋荣轩', 'qgS', 'www.tambra-huels.io', '田弘文', '蒋建辉', '0', '阎鑫磊', 87);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('蒋智渊', 'LI', 'www.lavern-nitzsche.biz', '廖鹏飞', '白天磊', '0', '白思', 8787642076);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values ('蔡雨泽', 'ru5', 'www.miss-green.co', '侯子轩', '谢鹏涛', '0', '秦昊焱', 482);
