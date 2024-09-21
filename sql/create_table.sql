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
    accessKey    varchar(512)                           not null comment 'accessKey',
    secretKey    varchar(512)                           not null comment 'secretKey',
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
    sourceKey      varchar(512)                       not null comment '来源校验码',
    url            varchar(512)                       not null comment '接口地址',
    requestParams  text                               not null comment '请求参数',
    requestHeader  text                               null comment '请求头',
    responseHeader text                               null comment '响应头',
    status         int      default 0                 not null comment '接口状态：0-关闭，1-开启',
    method         varchar(256)                       not null comment '请求类型',
    createUser     bigint                             not null comment '创建人',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除'
) comment '接口信息';

-- 用户调用接口关系表
create table if not exists user_interface_info
(
    id              bigint                             not null auto_increment primary key comment 'id',
    userId          bigint                             not null comment '调用用户id',
    interfaceInfoId bigint                             not null comment '接口id',
    totalNum        int      default 0                 not null comment '总调用次数',
    leftNum         int      default 0                 not null comment '剩余调用次数',
    status          int      default 0                 not null comment '0-关闭，1-禁用',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除'
) comment '用户调用接口关系';

insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (1, '马擎苍', 'YWrda', 'G1', 'www.walton-bernier.co', 'TW7Uw', 'EI6', 'XWS', 0, '54jiO', 9750348);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (2, '赖越彬', 'B1', 'xNda', 'www.elia-schamberger.com', 'CLe', 'DGBx', 'xLRf', 0, 'iXTO', 3);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (3, '袁雨泽', '49H', 'wtVp', 'www.veola-gottlieb.net', 'QVw', 'Sp0x', 'jaTB', 0, 'px4oW', 411128);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (4, '郑鹏飞', 'C8onr', 'YNFr', 'www.tayna-wolf.co', 'Taw', 'KZyz', 'Rn', 0, 'ld79Y', 4194192);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (5, '徐风华', 'jvpZ', 'VE2Cz', 'www.whitney-schmitt.co', 'JnMZ', 'nWEF', 'NA0m', 0, 'Hoh', 821764);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (6, '丁航', 'Su0', 'aQPh', 'www.serita-kemmer.co', 'OpBU', 'Q7', '3XuH', 0, '0N', 49);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (7, '罗嘉熙', 'S8C', 'moc', 'www.bret-gerhold.org', 'LJ', 'nMu8Y', 'OUIBY', 0, 'iP', 3698297291);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (8, '胡修洁', 'rjJb0', 'JAj', 'www.bettina-conroy.com', 'dH', 'Mz', 'xTd', 0, 'RDw', 766);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (9, '吴志强', 'coB5C', 'AqE', 'www.sheryl-dickinson.net', 'R5hSh', 'lwh0', 'wYjVW', 0, 'ou', 95337834);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (10, '杜俊驰', 'nU0L', '3l', 'www.santana-lehner.io', 'jA', 'Jf', 'KOCUO', 0, 'uE', 670755884);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (11, '白君浩', '4jquh', 'glO3w', 'www.claudine-christiansen.io', 'bDo', 'uTupc', 'DeRSk', 0, 'y4', 355);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (12, '叶思聪', 'aEl', '1V', 'www.audry-kautzer.biz', 'FEB', 'SRL', 'D5Lt', 0, 'kbc', 810);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (13, '邱子轩', 'HcHv', 'EfD', 'www.liberty-barton.io', 'fPs0k', 'x1jqS', 'aiI5F', 0, '1Y', 641506638);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (14, '于鹏煊', 'fbvaM', 'vxzlO', 'www.brunilda-towne.co', 'Wdffh', 'vQ', 'XA4A', 0, 'IU', 5);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (15, '崔梓晨', 'hQ', 'OC', 'www.arturo-hills.co', 'NUjlK', 'T31', 'n7J', 0, 'F0sg', 531);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (16, '钟烨华', 'Mt', 'J1', 'www.mitchel-lang.org', 'w7XxT', 'QJ', 'aRK4O', 0, 'gL', 48);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (17, '覃晓啸', 'gjJ', 'Qh', 'www.giuseppe-goyette.com', 'rNJ', 'NRUVR', 'nD', 0, '3iG7h', 23453);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (18, '宋绍辉', 'jb', '6W', 'www.tambra-rosenbaum.name', 'FM', 'BGel', 'rAl7', 0, '4BZk0', 86121);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (19, '洪语堂', '5ytG', 'kx', 'www.laticia-bruen.io', 'IXT', 'WFMid', 'AM0O', 0, 'IiZ7', 396);
insert into `interface_info` (`id`, `name`, `description`, `sourceKey`, `url`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `method`, `createUser`) values (20, '郝哲瀚', '25uR', 'Oahsx', 'www.hilario-gusikowski.org', 'd1BMF', 'hWXa', 'ahc', 0, 'g92', 7342365);
