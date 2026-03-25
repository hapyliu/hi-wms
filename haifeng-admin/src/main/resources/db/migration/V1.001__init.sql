-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment '部门id',
  parent_id         bigint(20)      default 0                  comment '父部门id',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '部门名称',
  order_num         int(4)          default 0                  comment '显示顺序',
  leader            varchar(20)     default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status            char(1)         default '0'                comment '部门状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '0', '海蜂智能',   0, '海蜂', '', '', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '用户ID',
  dept_id           bigint(20)      default null               comment '部门ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  avatar            varchar(100)    default ''                 comment '头像地址',
  password          varchar(100)    default ''                 comment '密码',
  status            char(1)         default '0'                comment '账号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  pwd_update_date   datetime                                   comment '密码最后更新时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  is_api_account    char(1)         default '0'                comment 'API账号标识（0普通账号 1API账号token永不过期）',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  100, 'admin', 'admin', '00', '', '', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '管理员', 0);
insert into sys_user values(2,  100, 'apiBot', 'apiBot', '00', '', '', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '系统账号', 1);



-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '岗位ID',
  post_code     varchar(64)     not null                   comment '岗位编码',
  post_name     varchar(50)     not null                   comment '岗位名称',
  post_sort     int(4)          not null                   comment '显示顺序',
  status        char(1)         not null                   comment '状态（0正常 1停用）',
  create_by     varchar(64)     default ''                 comment '创建者',
  create_time   datetime                                   comment '创建时间',
  update_by     varchar(64)     default ''			       comment '更新者',
  update_time   datetime                                   comment '更新时间',
  remark        varchar(500)    default null               comment '备注',
  primary key (post_id)
) engine=innodb comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
-- insert into sys_post values(1, 'ceo',  '董事长',    1, '0', 'admin', sysdate(), '', null, '');
-- insert into sys_post values(2, 'se',   '项目经理',  2, '0', 'admin', sysdate(), '', null, '');
-- insert into sys_post values(3, 'hr',   '人力资源',  3, '0', 'admin', sysdate(), '', null, '');
-- insert into sys_post values(4, 'user', '普通员工',  4, '0', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '角色ID',
  role_name            varchar(30)     not null                   comment '角色名称',
  role_key             varchar(100)    not null                   comment '角色权限字符串',
  role_sort            int(4)          not null                   comment '显示顺序',
  data_scope           char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
  dept_check_strictly  tinyint(1)      default 1                  comment '部门树选择项是否关联显示',
  status               char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by            varchar(64)     default ''                 comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default null               comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '超级管理员',  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '超级管理员');
insert into sys_role values('2', '普通角色',    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '普通角色');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query             varchar(255)    default null               comment '路由参数',
  route_name        varchar(50)     default ''                 comment '路由名称',
  is_frame          int(1)          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '1', 'system',           null, '', '', 1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '2', 'monitor',          null, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, '系统监控目录');
insert into sys_menu values('3', '系统工具', '0', '3', 'tool',             null, '', '', 1, 0, 'M', '0', '0', '', 'tool',     'admin', sysdate(), '', null, '系统工具目录');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        '', '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        '', '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, '角色管理菜单');
insert into sys_menu values('102',  '菜单管理', '1',   '3', 'menu',       'system/menu/index',        '', '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, '菜单管理菜单');
insert into sys_menu values('103',  '部门管理', '1',   '4', 'dept',       'system/dept/index',        '', '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, '部门管理菜单');
insert into sys_menu values('104',  '岗位管理', '1',   '5', 'post',       'system/post/index',        '', '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, '岗位管理菜单');
insert into sys_menu values('105',  '字典管理', '1',   '6', 'dict',       'system/dict/index',        '', '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, '字典管理菜单');
insert into sys_menu values('106',  '参数设置', '1',   '7', 'config',     'system/config/index',      '', '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, '参数设置菜单');
insert into sys_menu values('107',  '通知公告', '1',   '8', 'notice',     'system/notice/index',      '', '', 1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, '通知公告菜单');
insert into sys_menu values('108',  '日志管理', '1',   '9', 'log',        '',                         '', '', 1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, '日志管理菜单');
insert into sys_menu values('109',  '在线用户', '2',   '1', 'online',     'monitor/online/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, '在线用户菜单');
insert into sys_menu values('110',  '定时任务', '2',   '2', 'job',        'monitor/job/index',        '', '', 1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, '定时任务菜单');
insert into sys_menu values('111',  '数据监控', '2',   '3', 'druid',      'monitor/druid/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', sysdate(), '', null, '数据监控菜单');
insert into sys_menu values('112',  '服务监控', '2',   '4', 'server',     'monitor/server/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, '服务监控菜单');
insert into sys_menu values('113',  '缓存监控', '2',   '5', 'cache',      'monitor/cache/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, '缓存监控菜单');
insert into sys_menu values('114',  '缓存列表', '2',   '6', 'cacheList',  'monitor/cache/list',       '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'admin', sysdate(), '', null, '缓存列表菜单');
-- insert into sys_menu values('115',  '表单构建', '3',   '1', 'build',      'tool/build/index',         '', '', 1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'admin', sysdate(), '', null, '表单构建菜单');
-- insert into sys_menu values('116',  '代码生成', '3',   '2', 'gen',        'tool/gen/index',           '', '', 1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', sysdate(), '', null, '代码生成菜单');
-- insert into sys_menu values('117',  '系统接口', '3',   '3', 'swagger',    'tool/swagger/index',       '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', sysdate(), '', null, '系统接口菜单');
-- 三级菜单
insert into sys_menu values('500',  '操作日志', '108', '1', 'operlog',    'monitor/operlog/index',    '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, '操作日志菜单');
insert into sys_menu values('501',  '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, '登录日志菜单');
-- 用户管理按钮
insert into sys_menu values('1000', '用户查询', '100', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1001', '用户新增', '100', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', '用户修改', '100', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', '用户删除', '100', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', '用户导出', '100', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', '用户导入', '100', '6',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', '重置密码', '100', '7',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- 角色管理按钮
insert into sys_menu values('1007', '角色查询', '101', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1008', '角色新增', '101', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', '角色修改', '101', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', '角色删除', '101', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', '角色导出', '101', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- 菜单管理按钮
insert into sys_menu values('1012', '菜单查询', '102', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1013', '菜单新增', '102', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', '菜单修改', '102', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', '菜单删除', '102', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- 部门管理按钮
insert into sys_menu values('1016', '部门查询', '103', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1017', '部门新增', '103', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', '部门修改', '103', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', '部门删除', '103', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- 岗位管理按钮
insert into sys_menu values('1020', '岗位查询', '104', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1021', '岗位新增', '104', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', '岗位修改', '104', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', '岗位删除', '104', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', '岗位导出', '104', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- 字典管理按钮
insert into sys_menu values('1025', '字典查询', '105', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', '字典新增', '105', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', '字典修改', '105', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', '字典删除', '105', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', '字典导出', '105', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- 参数设置按钮
insert into sys_menu values('1030', '参数查询', '106', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1031', '参数新增', '106', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', '参数修改', '106', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', '参数删除', '106', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', '参数导出', '106', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- 通知公告按钮
insert into sys_menu values('1035', '公告查询', '107', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1036', '公告新增', '107', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', '公告修改', '107', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', '公告删除', '107', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- 操作日志按钮
insert into sys_menu values('1039', '操作查询', '500', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1040', '操作删除', '500', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', '日志导出', '500', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- 登录日志按钮
insert into sys_menu values('1042', '登录查询', '501', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1043', '登录删除', '501', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', '日志导出', '501', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', '账户解锁', '501', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock',  '#', 'admin', sysdate(), '', null, '');
-- 在线用户按钮
insert into sys_menu values('1046', '在线查询', '109', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', '批量强退', '109', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', '单条强退', '109', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');
-- 定时任务按钮
insert into sys_menu values('1049', '任务查询', '110', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1050', '任务新增', '110', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', '任务修改', '110', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', '任务删除', '110', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', '状态修改', '110', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', '任务导出', '110', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '1');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '117');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');

-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment '角色ID',
  dept_id   bigint(20) not null comment '部门ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '用户ID',
  post_id   bigint(20) not null comment '岗位ID',
  primary key (user_id, post_id)
) engine=innodb comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
-- insert into sys_user_post values ('1', '1');
-- insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(200)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name         varchar(50)     default ''                 comment '操作人员',
  dept_name         varchar(50)     default ''                 comment '部门名称',
  oper_url          varchar(255)    default ''                 comment '请求URL',
  oper_ip           varchar(128)    default ''                 comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param        varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  oper_time         datetime                                   comment '操作时间',
  cost_time         bigint(20)      default 0                  comment '消耗时间',
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = '操作日志记录';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '字典主键',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '字典类型表';

insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, '用户性别列表');
insert into sys_dict_type values(2,  '菜单状态', 'sys_show_hide',       '0', 'admin', sysdate(), '', null, '菜单状态列表');
insert into sys_dict_type values(3,  '系统开关', 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, '系统开关列表');
insert into sys_dict_type values(4,  '任务状态', 'sys_job_status',      '0', 'admin', sysdate(), '', null, '任务状态列表');
insert into sys_dict_type values(5,  '任务分组', 'sys_job_group',       '0', 'admin', sysdate(), '', null, '任务分组列表');
insert into sys_dict_type values(6,  '系统是否', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, '系统是否列表');
insert into sys_dict_type values(7,  '通知类型', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, '通知类型列表');
insert into sys_dict_type values(8,  '通知状态', 'sys_notice_status',   '0', 'admin', sysdate(), '', null, '通知状态列表');
insert into sys_dict_type values(9,  '操作类型', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, '操作类型列表');
insert into sys_dict_type values(10, '系统状态', 'sys_common_status',   '0', 'admin', sysdate(), '', null, '登录状态列表');
insert into sys_dict_type values(11, '容器基础类型','container_basic_type', '0', 'admin', sysdate(), '', NULL, '容器基础类型列表');
insert into sys_dict_type values(12, '容器位置类型','container_position_type', '0', 'admin', sysdate(), '', NULL, '容器位置类型列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '字典编码',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default null               comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典数据表';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别未知');
insert into sys_dict_data values(4,  1,  '显示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '显示菜单');
insert into sys_dict_data values(5,  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '隐藏菜单');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(8,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(9,  2,  '暂停',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(10, 1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, '默认分组');
insert into sys_dict_data values(11, 2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, '系统分组');
insert into sys_dict_data values(12, 1,  '是',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '系统默认是');
insert into sys_dict_data values(13, 2,  '否',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '系统默认否');
insert into sys_dict_data values(14, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '通知');
insert into sys_dict_data values(15, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, '公告');
insert into sys_dict_data values(16, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(17, 2,  '关闭',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '关闭状态');
insert into sys_dict_data values(18, 99, '其他',     '0',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '其他操作');
insert into sys_dict_data values(19, 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '新增操作');
insert into sys_dict_data values(20, 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '修改操作');
insert into sys_dict_data values(21, 3,  '删除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '删除操作');
insert into sys_dict_data values(22, 4,  '授权',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '授权操作');
insert into sys_dict_data values(23, 5,  '导出',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导出操作');
insert into sys_dict_data values(24, 6,  '导入',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导入操作');
insert into sys_dict_data values(25, 7,  '强退',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '强退操作');
insert into sys_dict_data values(26, 8,  '生成代码', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '生成操作');
insert into sys_dict_data values(27, 9,  '清空数据', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '清空操作');
insert into sys_dict_data values(28, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(29, 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values (100, 1, '支腿货架', '0', 'container_basic_type', NULL, 'default', 'N', '0', 'admin', sysdate(), '', NULL, NULL);
insert into sys_dict_data values (101, 2, '托盘', '1', 'container_basic_type', NULL, 'default', 'N', '0', 'admin', sysdate(), '', NULL, NULL);
insert into sys_dict_data values (102, 3, '料箱', '2', 'container_basic_type', NULL, 'default', 'N', '0', 'admin', sysdate(), '', NULL, NULL);
insert into sys_dict_data values (103, 1, '库位', '0', 'container_position_type', NULL, 'default', 'N', '0', 'admin', sysdate(), '', NULL, NULL);
insert into sys_dict_data values (104, 2, '机器人', '1', 'container_position_type', NULL, 'default', 'N', '0', 'admin', sysdate(), '', NULL, NULL);
insert into sys_dict_data values (105, 3, '游离', '2', 'container_position_type', NULL, 'default', 'N', '0', 'admin', sysdate(), '', NULL, NULL);


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment '参数主键',
  config_name       varchar(100)    default ''                 comment '参数名称',
  config_key        varchar(100)    default ''                 comment '参数键名',
  config_value      varchar(500)    default ''                 comment '参数键值',
  config_type       char(1)         default 'N'                comment '系统内置（Y是 N否）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '参数配置表';

insert into sys_config values(1, '主框架页-默认皮肤样式名称',     'sys.index.skinName',               'skin-blue',     'Y', 'admin', sysdate(), '', null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config values(2, '用户管理-账号初始密码',         'sys.user.initPassword',            '123456',        'Y', 'admin', sysdate(), '', null, '初始化密码 123456' );
insert into sys_config values(3, '主框架页-侧边栏主题',           'sys.index.sideTheme',              'theme-dark',    'Y', 'admin', sysdate(), '', null, '深色主题theme-dark，浅色主题theme-light' );
insert into sys_config values(4, '账号自助-验证码开关',           'sys.account.captchaEnabled',       'true',          'Y', 'admin', sysdate(), '', null, '是否开启验证码功能（true开启，false关闭）');
insert into sys_config values(5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser',         'false',         'Y', 'admin', sysdate(), '', null, '是否开启注册用户功能（true开启，false关闭）');
insert into sys_config values(6, '用户登录-黑名单列表',           'sys.login.blackIPList',            '',              'Y', 'admin', sysdate(), '', null, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
insert into sys_config values(7, '用户管理-初始密码修改策略',     'sys.account.initPasswordModify',   '1',             'Y', 'admin', sysdate(), '', null, '0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框');
insert into sys_config values(8, '用户管理-账号密码更新周期',     'sys.account.passwordValidateDays', '0',             'Y', 'admin', sysdate(), '', null, '密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框');


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment '访问ID',
  user_name      varchar(50)    default ''                comment '用户账号',
  ipaddr         varchar(128)   default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         char(1)        default '0'               comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '访问时间',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = '系统访问记录';


-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment '任务ID',
  job_name            varchar(64)   default ''                 comment '任务名称',
  job_group           varchar(64)   default 'DEFAULT'          comment '任务组名',
  invoke_target       varchar(500)  not null                   comment '调用目标字符串',
  cron_expression     varchar(255)  default ''                 comment 'cron执行表达式',
  misfire_policy      varchar(20)   default '3'                comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  concurrent          char(1)       default '1'                comment '是否并发执行（0允许 1禁止）',
  status              char(1)       default '0'                comment '状态（0正常 1暂停）',
  create_by           varchar(64)   default ''                 comment '创建者',
  create_time         datetime                                 comment '创建时间',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新时间',
  remark              varchar(500)  default ''                 comment '备注信息',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '定时任务调度表';


-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment '任务日志ID',
  job_name            varchar(64)    not null                   comment '任务名称',
  job_group           varchar(64)    not null                   comment '任务组名',
  invoke_target       varchar(500)   not null                   comment '调用目标字符串',
  job_message         varchar(500)                              comment '日志信息',
  status              char(1)        default '0'                comment '执行状态（0正常 1失败）',
  exception_info      varchar(2000)  default ''                 comment '异常信息',
  create_time         datetime                                  comment '创建时间',
  primary key (job_log_id)
) engine=innodb comment = '定时任务调度日志表';


-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment '公告ID',
  notice_title      varchar(50)     not null                   comment '公告标题',
  notice_type       char(1)         not null                   comment '公告类型（1通知 2公告）',
  notice_content    longblob        default null               comment '公告内容',
  status            char(1)         default '0'                comment '公告状态（0正常 1关闭）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(255)    default null               comment '备注',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
-- insert into sys_notice values('1', '温馨提醒：2018-07-01 若依新版本发布啦', '2', '新版本内容', '0', 'admin', sysdate(), '', null, '管理员');
-- insert into sys_notice values('2', '维护通知：2018-07-01 若依系统凌晨维护', '1', '维护内容',   '0', 'admin', sysdate(), '', null, '管理员');


-- ----------------------------
-- 18、国际化消息表
-- ----------------------------
drop table if exists sys_i18n_message;
create table sys_i18n_message (
  message_id        bigint(20)      not null auto_increment    comment '消息ID',
  message_key       varchar(255)    not null                   comment '消息键（如：user.not.exists）',
  message_value     longtext        not null                   comment '消息值（支持参数占位符 {0}, {1} 等）',
  language_code     varchar(10)     not null default 'zh_CN'  comment '语言代码（zh_CN, en_US 等）',
  category          varchar(50)     default 'system'          comment '分类（system, error, validation, business, permission, dict）',
  status            char(1)         default '1'               comment '状态（0禁用 1启用）',
  create_by         varchar(64)     default ''                comment '创建者',
  create_time       datetime                                  comment '创建时间',
  update_by         varchar(64)     default ''                comment '更新者',
  update_time       datetime                                  comment '更新时间',
  remark            varchar(500)    default null              comment '备注',
  primary key (message_id),
  unique key uk_message_key_lang (message_key, language_code),
  key idx_language_code (language_code),
  key idx_category (category),
  key idx_status (status)
) engine=innodb auto_increment=1 comment = '国际化消息表';

-- ----------------------------
-- 库区表
-- ----------------------------
drop table if exists base_area;
CREATE TABLE `base_area` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `area_code` varchar(50) NOT NULL COMMENT '区域编码',
                             `area_name` varchar(100) NOT NULL COMMENT '区域名称',
                             `warehouse_code` varchar(50) DEFAULT NULL COMMENT '仓库编码',
                             `warehouse_name` varchar(100) DEFAULT NULL COMMENT '仓库名称',
                             `center_coordinates` varchar(100) DEFAULT NULL COMMENT '可视化中心坐标',
                             `length` varchar(20) DEFAULT NULL COMMENT '长',
                             `width` varchar(20) DEFAULT NULL COMMENT '宽',
                             `del_flag` char(1) DEFAULT '2' COMMENT '删除标志（0代表删除 2代表存在）',
                             `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_area_code` (`area_code`) USING BTREE COMMENT '区域编码唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2028352841987891202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库区表';

-- ----------------------------
-- 工作站表
-- ----------------------------
drop table if exists base_station;
CREATE TABLE `base_station` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                `station_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工作站编码',
                                `station_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工作站名称',
                                `station_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '工作站状态（0正常 1停用）',
                                `station_type` char(1) NOT NULL COMMENT '工作站类型(0在线 1离线)',
                                `del_flag` char(1) DEFAULT '2' COMMENT '删除标志（0代表删除 2代表存在）',
                                `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                                `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uk_workstation_code` (`station_code`) USING BTREE COMMENT '工作站编码唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2028394235129479171 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工作站信息表';

-- ----------------------------
-- 容器表
-- ----------------------------

CREATE TABLE `base_container` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `container_code` varchar(50) COMMENT '容器编号',
  `container_model_code` varchar(50) COMMENT '容器模型编号',
  `position_type` varchar(50) COMMENT '位置类型:字典dict_value',
  `position_code` varchar(100) COMMENT '位置编号(根据位置类型区别，编号不同，默认值-)',
  `station_code` varchar(100) COMMENT '工作站编号(位置类型为库位时，假如该库位属于工作站，显示关联工作站编号)',
  `task_code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务编号',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '容器状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='容器表';

-- ----------------------------
-- 容器模型表
-- ----------------------------

CREATE TABLE `base_container_model` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `container_model_code` varchar(50) COMMENT '容器模型编号',
    `container_model_name` varchar(100) COMMENT '容器模型名称',
    `container_basic_type` varchar(50) COMMENT '容器基础类型:字典dict_value',
    `length` int COMMENT '长(单位:mm)',
    `width` int COMMENT '宽(单位:mm)',
    `height` int COMMENT '高(单位:mm)',
    `shelf_face_count` int(1) DEFAULT 0 COMMENT '货架面数(枚举值：0，2，4,默认0)',
    `shelf_face` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '货架面(枚举值：A,B,C,D)',
    `grid_floor_count` int COMMENT '格口层数',
    `single_grid_count` int COMMENT '单层格口数',
    `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='容器模型表';

-- ----------------------------
-- 仓库表
-- ----------------------------
CREATE TABLE `base_warehouse` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_code` varchar(50) COMMENT '仓库编号',
  `warehouse_name` varchar(100) COMMENT '仓库名称',
  `warehouse_desc` varchar(200) COMMENT '仓库描述',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='仓库表';

-- ----------------------------
-- 物料分类表
-- ----------------------------
CREATE TABLE `base_material_category`  (
 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 `category_name` varchar(32) NULL COMMENT '物料分类名称',
 `category_code` varchar(32) NULL COMMENT '物料分类编码',
 `parent_id` bigint NULL COMMENT '父ID',
 `ancestors` varchar(500) NULL COMMENT '祖级列表（用逗号分隔）',
 `del_flag` char(1) NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
 `create_by` varchar(64) NULL COMMENT '创建者',
 `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_by` varchar(64) NULL COMMENT '更新者',
 `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 PRIMARY KEY (`id`)
) COMMENT = '物料分类表';
-- ----------------------------
-- 物料表
-- ----------------------------
CREATE TABLE `base_material`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `material_code` varchar(32) NOT NULL COMMENT '物料编码',
    `material_name` varchar(64) NOT NULL COMMENT '物料名称',
    `bar_code` varchar(32) NULL COMMENT '物料条码',
    `category_id` bigint NULL COMMENT '物料分类id',
    `specification` varchar(255) NULL COMMENT '规格型号',
    `unit` varchar(32) NULL COMMENT '单位',
    `length` int NULL COMMENT '长度（单位mm）',
    `height` int NULL COMMENT '高度（单位mm）',
    `width` int NULL COMMENT '宽度（单位mm）',
    `volume` int NULL COMMENT '体积（根据长宽高计算，单位立方mm）',
    `gross_weight` double NULL COMMENT '毛重（单位kg）',
    `net_weight` double NULL COMMENT '净重（单位kg）',
    `shelf_life` int NULL COMMENT '保质期(天)',
    `is_sn_managed` char(1) NULL COMMENT '是否sn(0-是1-否)',
    `is_batch_managed` char(1) NULL COMMENT '是否批次管理(0-是1-否)',
    `del_flag` char(1) NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by` varchar(64) NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(64) NULL COMMENT '更新者',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT = '物料表';
-- ----------------------------
-- 物料属性扩展表
-- ----------------------------
CREATE TABLE `base_material_attr_ext`  (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
     `material_id` bigint NULL COMMENT '物料id(关联物料表id)',
     `material_attr_name` varchar(50) NULL COMMENT '物料属性名',
     `material_attr_value` varchar(100) NULL COMMENT '物料属性值',
     `del_flag` char(1) NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
     `create_by` varchar(64) NULL COMMENT '创建者',
     `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_by` varchar(64) NULL COMMENT '更新者',
     `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`)
) COMMENT = '物料属性扩展表';
-- ----------------------------
-- 批次号表
-- ----------------------------
CREATE TABLE `base_batch_number`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `batch_number` varchar(64) NOT NULL COMMENT '批次号',
    `batch_attribute1` varchar(64) NULL COMMENT '批次属性1',
    `batch_attribute2` varchar(64) NULL COMMENT '批次属性2',
    `batch_attribute3` varchar(64) NULL COMMENT '批次属性3',
    `batch_attribute4` varchar(64) NULL COMMENT '批次属性4',
    `batch_attribute5` varchar(64) NULL COMMENT '批次属性5',
    `batch_attribute6` varchar(64) NULL COMMENT '批次属性6',
    `batch_attribute7` varchar(64) NULL COMMENT '批次属性7',
    `batch_attribute8` varchar(64) NULL COMMENT '批次属性8',
    `batch_attribute9` varchar(64) NULL COMMENT '批次属性9',
    `batch_attribute10` varchar(64) NULL COMMENT '批次属性10',
    `batch_attribute11` varchar(64) NULL COMMENT '批次属性11',
    `batch_attribute12` varchar(64) NULL COMMENT '批次属性12',
    `batch_attribute13` varchar(64) NULL COMMENT '批次属性13',
    `batch_attribute14` varchar(64) NULL COMMENT '批次属性14',
    `batch_attribute15` varchar(64) NULL COMMENT '批次属性15',
    `del_flag` char(1) NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by` varchar(64) NULL COMMENT '创建者',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(64) NULL COMMENT '更新者',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT = '批次号表';
-- ----------------------------
-- 批次属性表
-- ----------------------------
CREATE TABLE `base_batch_attribute`  (
   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
   `attr_code` varchar(64) NOT NULL COMMENT '批次属性',
   `attr_index` int NULL COMMENT '属性顺序1到15',
   `attr_type` char(1) NULL COMMENT '属性值类型:0-字符,1-数字,2-枚举,3-日期,4-日期时间',
   `attr_enum` varchar(64) NULL COMMENT '属性枚举（关联sys_dict_type表的dict_type字段）',
   `attr_required` char(1) NULL COMMENT '是否必填',
   `status` tinyint(1) NULL DEFAULT 0 COMMENT '属性状态(0:启用,1:停用)',
   `del_flag` char(1) NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
   `create_by` varchar(64) NULL COMMENT '创建者',
   `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_by` varchar(64) NULL COMMENT '更新者',
   `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) COMMENT = '批次属性表';
-- ----------------------------
-- 初始化-国际化翻译表数据
-- ----------------------------
INSERT  INTO sys_i18n_message (message_key, message_value, language_code, category, status, create_time) VALUES
    -- 字典标签
    ('dict.label.not.blank', '字典标签不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('dict.label.not.blank', 'Dictionary label cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('dict.label.length.invalid', '字典标签长度不能超过100个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dict.label.length.invalid', 'Dictionary label length cannot exceed 100 characters', 'en_US', 'validation', '1', NOW()),

    -- 字典键值
    ('dict.value.not.blank', '字典键值不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('dict.value.not.blank', 'Dictionary value cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('dict.value.length.invalid', '字典键值长度不能超过100个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dict.value.length.invalid', 'Dictionary value length cannot exceed 100 characters', 'en_US', 'validation', '1', NOW()),

    -- 字典类型
    ('dict.type.not.blank', '字典类型不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('dict.type.not.blank', 'Dictionary type cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('dict.type.length.invalid', '字典类型长度不能超过100个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dict.type.length.invalid', 'Dictionary type length cannot exceed 100 characters', 'en_US', 'validation', '1', NOW()),

    -- 样式属性
    ('dict.css.class.length.invalid', '样式属性长度不能超过100个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dict.css.class.length.invalid', 'CSS class length cannot exceed 100 characters', 'en_US', 'validation', '1', NOW()),

    -- 用户昵称
    ('user.nickname.xss', '用户昵称不能包含脚本字符', 'zh_CN', 'validation', '1', NOW()),
    ('user.nickname.xss', 'User nickname cannot contain script characters', 'en_US', 'validation', '1', NOW()),
    ('user.nickname.length.invalid', '用户昵称长度不能超过30个字符', 'zh_CN', 'validation', '1', NOW()),
    ('user.nickname.length.invalid', 'User nickname length cannot exceed 30 characters', 'en_US', 'validation', '1', NOW()),

    -- 用户账号
    ('user.username.xss', '用户账号不能包含脚本字符', 'zh_CN', 'validation', '1', NOW()),
    ('user.username.xss', 'Username cannot contain script characters', 'en_US', 'validation', '1', NOW()),
    ('user.username.not.blank', '用户账号不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('user.username.not.blank', 'Username cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('user.username.length.invalid', '用户账号长度不能超过30个字符', 'zh_CN', 'validation', '1', NOW()),
    ('user.username.length.invalid', 'Username length cannot exceed 30 characters', 'en_US', 'validation', '1', NOW()),

    -- 用户邮箱
    ('user.email.invalid', '邮箱格式不正确', 'zh_CN', 'validation', '1', NOW()),
    ('user.email.invalid', 'Invalid email format', 'en_US', 'validation', '1', NOW()),
    ('user.email.length.invalid', '邮箱长度不能超过50个字符', 'zh_CN', 'validation', '1', NOW()),
    ('user.email.length.invalid', 'Email length cannot exceed 50 characters', 'en_US', 'validation', '1', NOW()),

    -- 手机号码
    ('user.phonenumber.length.invalid', '手机号码长度不能超过11个字符', 'zh_CN', 'validation', '1', NOW()),
    ('user.phonenumber.length.invalid', 'Phone number length cannot exceed 11 characters', 'en_US', 'validation', '1', NOW()),

    -- ========== SysDept 部门验证消息 ==========
    -- 部门名称
    ('dept.name.not.blank', '部门名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('dept.name.not.blank', 'Department name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('dept.name.length.invalid', '部门名称长度不能超过30个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dept.name.length.invalid', 'Department name length cannot exceed 30 characters', 'en_US', 'validation', '1', NOW()),

    -- 显示顺序
    ('dept.order.not.null', '显示顺序不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('dept.order.not.null', 'Display order cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 联系电话
    ('dept.phone.length.invalid', '联系电话长度不能超过11个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dept.phone.length.invalid', 'Contact phone length cannot exceed 11 characters', 'en_US', 'validation', '1', NOW()),

    -- 部门邮箱
    ('dept.email.invalid', '邮箱格式不正确', 'zh_CN', 'validation', '1', NOW()),
    ('dept.email.invalid', 'Invalid email format', 'en_US', 'validation', '1', NOW()),
    ('dept.email.length.invalid', '邮箱长度不能超过50个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dept.email.length.invalid', 'Email length cannot exceed 50 characters', 'en_US', 'validation', '1', NOW()),

    -- ========== SysMenu 菜单验证消息 ==========
    -- 菜单名称
    ('menu.name.not.blank', '菜单名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('menu.name.not.blank', 'Menu name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('menu.name.length.invalid', '菜单名称长度不能超过50个字符', 'zh_CN', 'validation', '1', NOW()),
    ('menu.name.length.invalid', 'Menu name length cannot exceed 50 characters', 'en_US', 'validation', '1', NOW()),

    -- 显示顺序
    ('menu.order.not.null', '显示顺序不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('menu.order.not.null', 'Display order cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 路由地址
    ('menu.path.length.invalid', '路由地址不能超过200个字符', 'zh_CN', 'validation', '1', NOW()),
    ('menu.path.length.invalid', 'Route path cannot exceed 200 characters', 'en_US', 'validation', '1', NOW()),

    -- 组件路径
    ('menu.component.length.invalid', '组件路径不能超过200个字符', 'zh_CN', 'validation', '1', NOW()),
    ('menu.component.length.invalid', 'Component path cannot exceed 200 characters', 'en_US', 'validation', '1', NOW()),

    -- 菜单类型
    ('menu.type.not.blank', '菜单类型不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('menu.type.not.blank', 'Menu type cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 权限标识
    ('menu.perms.length.invalid', '权限标识长度不能超过100个字符', 'zh_CN', 'validation', '1', NOW()),
    ('menu.perms.length.invalid', 'Permission identifier length cannot exceed 100 characters', 'en_US', 'validation', '1', NOW()),

    -- 字典名称
    ('dict.name.not.blank', '字典名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('dict.name.not.blank', 'Dictionary name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('dict.name.length.invalid', '字典名称长度不能超过100个字符', 'zh_CN', 'validation', '1', NOW()),
    ('dict.name.length.invalid', 'Dictionary name length cannot exceed 100 characters', 'en_US', 'validation', '1', NOW()),

    -- 字典类型格式
    ('dict.type.pattern.invalid', '字典类型必须以字母开头，且只能为（小写字母，数字，下划线）', 'zh_CN', 'validation', '1', NOW()),
    ('dict.type.pattern.invalid', 'Dictionary type must start with a letter and contain only lowercase letters, numbers, and underscores', 'en_US', 'validation', '1',NOW()),

    -- 角色名称
    ('role.name.not.blank', '角色名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('role.name.not.blank', 'Role name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('role.name.length.invalid', '角色名称长度不能超过30个字符', 'zh_CN', 'validation', '1', NOW()),
    ('role.name.length.invalid', 'Role name length cannot exceed 30 characters', 'en_US', 'validation', '1', NOW()),

    -- 权限字符
    ('role.key.not.blank', '权限字符不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('role.key.not.blank', 'Permission character cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('role.key.length.invalid', '权限字符长度不能超过100个字符', 'zh_CN', 'validation', '1', NOW()),
    ('role.key.length.invalid', 'Permission character length cannot exceed 100 characters', 'en_US', 'validation', '1', NOW()),

    -- 显示顺序
    ('role.sort.not.null', '显示顺序不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('role.sort.not.null', 'Display order cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 任务名称
    ('job.name.not.blank', '任务名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('job.name.not.blank', 'Job name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('job.name.length.invalid', '任务名称不能超过64个字符', 'zh_CN', 'validation', '1', NOW()),
    ('job.name.length.invalid', 'Job name cannot exceed 64 characters', 'en_US', 'validation', '1', NOW()),

    -- 调用目标字符串
    ('job.invoke.target.not.blank', '调用目标字符串不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('job.invoke.target.not.blank', 'Invoke target string cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('job.invoke.target.length.invalid', '调用目标字符串长度不能超过500个字符', 'zh_CN', 'validation', '1', NOW()),
    ('job.invoke.target.length.invalid', 'Invoke target string length cannot exceed 500 characters', 'en_US', 'validation', '1', NOW()),

    -- Cron表达式
    ('job.cron.not.blank', 'Cron执行表达式不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('job.cron.not.blank', 'Cron expression cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('job.cron.length.invalid', 'Cron执行表达式不能超过255个字符', 'zh_CN', 'validation', '1', NOW()),
    ('job.cron.length.invalid', 'Cron expression cannot exceed 255 characters', 'en_US', 'validation', '1', NOW()),

    -- ========== 配置管理 ==========
    ('config.builtin.cannot.delete', '内置参数【{0}】不能删除', 'zh_CN', 'business', '1', NOW()),
    ('config.builtin.cannot.delete', 'Built-in parameter [{0}] cannot be deleted', 'en_US', 'business', '1', NOW()),

    -- ========== 部门管理 ==========
    ('dept.no.permission', '没有权限访问部门数据！', 'zh_CN', 'permission', '1', NOW()),
    ('dept.no.permission', 'No permission to access department data!', 'en_US', 'permission', '1', NOW()),
    ('dept.disabled.cannot.add', '部门停用，不允许新增', 'zh_CN', 'business', '1', NOW()),
    ('dept.disabled.cannot.add', 'Department is disabled, cannot add sub-department', 'en_US', 'business', '1', NOW()),

    -- ========== 岗位管理 ==========
    ('post.assigned.cannot.delete', '{0}已分配，不能删除', 'zh_CN', 'business', '1', NOW()),
    ('post.assigned.cannot.delete', '{0} has been assigned and cannot be deleted', 'en_US', 'business', '1', NOW()),

    -- ========== 用户管理 ==========
    ('user.admin.cannot.operate', '不允许操作超级管理员用户', 'zh_CN', 'permission', '1', NOW()),
    ('user.admin.cannot.operate', 'Cannot operate super administrator user', 'en_US', 'permission', '1', NOW()),
    ('user.no.permission', '没有权限访问用户数据！', 'zh_CN', 'permission', '1', NOW()),
    ('user.no.permission', 'No permission to access user data!', 'en_US', 'permission', '1', NOW()),
    ('user.import.data.empty', '导入用户数据不能为空！', 'zh_CN', 'business', '1', NOW()),
    ('user.import.data.empty', 'Import user data cannot be empty!', 'en_US', 'business', '1', NOW()),

    -- ========== 字典管理 ==========
    ('dict.type.assigned.cannot.delete', '{0}已分配，不能删除', 'zh_CN', 'business', '1', NOW()),
    ('dict.type.assigned.cannot.delete', '{0} has been assigned and cannot be deleted', 'en_US', 'business', '1', NOW()),

    -- ========== 角色管理 ==========
    ('role.admin.cannot.operate', '不允许操作超级管理员角色', 'zh_CN', 'permission', '1', NOW()),
    ('role.admin.cannot.operate', 'Cannot operate super administrator role', 'en_US', 'permission', '1', NOW()),
    ('role.no.permission', '没有权限访问角色数据！', 'zh_CN', 'permission', '1', NOW()),
    ('role.no.permission', 'No permission to access role data!', 'en_US', 'permission', '1', NOW()),
    ('role.assigned.cannot.delete', '{0}已分配，不能删除', 'zh_CN', 'business', '1', NOW()),
    ('role.assigned.cannot.delete', '{0} has been assigned and cannot be deleted', 'en_US', 'business', '1', NOW()),

    -- ========== 限流 ==========
    ('rate.limiter.exceeded', '访问过于频繁，请稍候再试', 'zh_CN', 'system', '1', NOW()),
    ('rate.limiter.exceeded', 'Access too frequent, please try again later', 'en_US', 'system', '1', NOW()),

    -- ========== 安全工具类 ==========
    ('security.get.userid.error', '获取用户ID异常', 'zh_CN', 'error', '1', NOW()),
    ('security.get.userid.error', 'Failed to get user ID', 'en_US', 'error', '1', NOW()),
    ('security.get.deptid.error', '获取部门ID异常', 'zh_CN', 'error', '1', NOW()),
    ('security.get.deptid.error', 'Failed to get department ID', 'en_US', 'error', '1', NOW()),
    ('security.get.username.error', '获取用户账户异常', 'zh_CN', 'error', '1', NOW()),
    ('security.get.username.error', 'Failed to get username', 'en_US', 'error', '1', NOW()),
    ('security.get.userinfo.error', '获取用户信息异常', 'zh_CN', 'error', '1', NOW()),
    ('security.get.userinfo.error', 'Failed to get user information', 'en_US', 'error', '1', NOW()),

    -- ========== 容器管理 ==========
    -- 验证消息
    ('container.code.not.blank', '容器编号不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('container.code.not.blank', 'container code cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('container.model.code.not.blank', '容器模型编号不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('container.model.code.not.blank', 'container model code cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('container.prefix.not.blank', '前缀不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('container.prefix.not.blank', 'prefix cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('container.start.sequence.not.null', '开始序号不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('container.start.sequence.not.null', 'start sequence cannot be null', 'en_US', 'validation', '1', NOW()),
    ('container.end.sequence.not.null', '结束序号不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('container.end.sequence.not.null', 'end sequence cannot be null', 'en_US', 'validation', '1', NOW()),
    -- 导出容器数据sheet名称
    ('container.export.sheet.name', '容器数据', 'zh_CN', 'business', '1', NOW()),
    ('container.export.sheet.name', 'Container data', 'en_US', 'business', '1', NOW()),
    -- 错误提示消息
    ('container.add.error', '新增容器{0}失败，容器编号已存在', 'zh_CN', 'error', '1', NOW()),
    ('container.add.error', 'add container {0} fail and the container number already exists', 'en_US', 'error', '1', NOW()),
    ('container.update.error', '修改容器{0}失败，容器编号已存在', 'zh_CN', 'error', '1', NOW()),
    ('container.update.error', 'update container {0} fail and the container number already exists', 'en_US', 'error', '1', NOW()),
    ('container.model.code.not.exists', '容器模型编号[{0}]不存在', 'zh_CN', 'error', '1', NOW()),
    ('container.model.code.not.exists', 'container model code[{0}] not exists', 'en_US', 'error', '1', NOW()),
    ('container.sequence.format.invalid', '开始序号和结束序号必须是 15 位数字', 'zh_CN', 'error', '1', NOW()),
    ('container.sequence.format.invalid', 'start sequence and end sequence must be 15 digits', 'en_US', 'error', '1', NOW()),
    ('container.sequence.range.invalid', '结束序号必须大于或等于开始序号', 'zh_CN', 'error', '1', NOW()),
    ('container.sequence.range.invalid', 'end sequence must be greater than or equal to start sequence', 'en_US', 'error', '1', NOW()),
    ('container.batch.code.already.exists', '以下容器编号已存在：{0}', 'zh_CN', 'error', '1', NOW()),
    ('container.batch.code.already.exists', 'the following container codes already exist: {0}', 'en_US', 'error', '1', NOW()),
    ('container.delete.location.exists', '容器{0}已关联库位,禁止删除', 'zh_CN', 'error', '1', NOW()),
    ('container.delete.location.exists', 'The container {0} is associated with a location and cannot be deleted', 'en_US', 'error', '1', NOW()),

    -- excel导出字段
    -- 容器编号
    ('container.code', '容器编号', 'zh_CN', 'business', '1', NOW()),
    ('container.code', 'Container Code', 'en_US', 'business', '1', NOW()),
    -- 位置类型
    ('position.type', '位置类型', 'zh_CN', 'business', '1', NOW()),
    ('position.type', 'Position Type', 'en_US', 'business', '1', NOW()),
    -- 位置编号
    ('position.code', '位置编号', 'zh_CN', 'business', '1', NOW()),
    ('position.code', 'Position Code', 'en_US', 'business', '1', NOW()),
    -- 工作站编号
    ('station.code', '工作站编号', 'zh_CN', 'business', '1', NOW()),
    ('station.code', 'Station Code', 'en_US', 'business', '1', NOW()),
    -- 任务编号
    ('task.code', '任务编号', 'zh_CN', 'business', '1', NOW()),
    ('task.code', 'Task Code', 'en_US', 'business', '1', NOW()),
    -- 容器状态
    ('container.status', '容器状态', 'zh_CN', 'business', '1', NOW()),
    ('container.status', 'Container Status', 'en_US', 'business', '1', NOW()),
    -- ========== 容器模型管理 ==========
    -- 验证消息
    ('container.model.name.not.blank', '容器模型名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('container.model.name.not.blank', 'container model name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('container.basic.type.not.blank', '容器基础类型不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('container.basic.type.not.blank', 'container basic type cannot be empty', 'en_US', 'validation', '1', NOW()),
    -- 导出容器模型数据sheet名称
    ('container.model.export.sheet.name', '容器模型数据', 'zh_CN', 'business', '1', NOW()),
    ('container.model.export.sheet.name', 'Container model data', 'en_US', 'business', '1', NOW()),
    -- 错误提示消息
    ('container.model.code.already.exists', '容器模型编号已存在', 'zh_CN', 'error', '1', NOW()),
    ('container.model.code.already.exists', 'container model code already exists', 'en_US', 'error', '1', NOW()),
    ('update.container.model.id.required', '修改容器模型id必传', 'zh_CN', 'error', '1', NOW()),
    ('update.container.model.id.required', 'update container model id must be required', 'en_US', 'error', '1', NOW()),
    ('update.container.model.code.forbidden', '容器模型编号禁止修改', 'zh_CN', 'error', '1', NOW()),
    ('update.container.model.code.forbidden', 'update container model code is forbidden', 'en_US', 'error', '1', NOW()),
    ('container.model.has.associated.containers', '存在关联的容器禁止删除,容器模型编号[{0}],容器模型名称[{1}]', 'zh_CN', 'error', '1', NOW()),
    ('container.model.has.associated.containers', 'associated containers cannot be deleted,Container Model code [{0}], Container Model Name [{1}]', 'en_US', 'error', '1', NOW()),
    -- excel导出字段
    -- 容器模型编号
    ('container.model.code', '容器模型编号', 'zh_CN', 'business', '1', NOW()),
    ('container.model.code', 'Container Model Code', 'en_US', 'business', '1', NOW()),
    -- 容器模型名称
    ('container.model.name', '容器模型名称', 'zh_CN', 'business', '1', NOW()),
    ('container.model.name', 'Container Model Name', 'en_US', 'business', '1', NOW()),
    -- 容器基础类型
    ('container.basic.type', '容器基础类型', 'zh_CN', 'business', '1', NOW()),
    ('container.basic.type', 'Container Basic Type', 'en_US', 'business', '1', NOW()),
    -- 长(单位:mm)
    ('length', '长(单位:mm)', 'zh_CN', 'business', '1', NOW()),
    ('length', 'Length (mm)', 'en_US', 'business', '1', NOW()),
    -- 宽(单位:mm)
    ('width', '宽(单位:mm)', 'zh_CN', 'business', '1', NOW()),
    ('width', 'Width (mm)', 'en_US', 'business', '1', NOW()),
    -- 高(单位:mm)
    ('height', '高(单位:mm)', 'zh_CN', 'business', '1', NOW()),
    ('height', 'Height (mm)', 'en_US', 'business', '1', NOW()),
    -- 货架面数
    ('shelf.face.count', '货架面数', 'zh_CN', 'business', '1', NOW()),
    ('shelf.face.count', 'Shelf Face Count', 'en_US', 'business', '1', NOW()),
    -- 货架面
    ('shelf.face', '货架面', 'zh_CN', 'business', '1', NOW()),
    ('shelf.face', 'Shelf Face', 'en_US', 'business', '1', NOW()),
    -- 格口层数
    ('grid.floor.count', '格口层数', 'zh_CN', 'business', '1', NOW()),
    ('grid.floor.count', 'Grid Floor Count', 'en_US', 'business', '1', NOW()),
    -- 单层格口数
    ('single.grid.count', '单层格口数', 'zh_CN', 'business', '1', NOW()),
    ('single.grid.count', 'Single Grid Count per Layer', 'en_US', 'business', '1', NOW()),

    -- ========== 仓库管理 ==========
    -- 验证消息
    ('warehouse.code.not.blank', '仓库编号不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('warehouse.code.not.blank', 'warehouse code cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('warehouse.name.not.blank', '仓库名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('warehouse.name.not.blank', 'warehouse name cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 导出仓库数据 sheet 名称
    ('warehouse.export.sheet.name', '仓库数据', 'zh_CN', 'business', '1', NOW()),
    ('warehouse.export.sheet.name', 'Warehouse data', 'en_US', 'business', '1', NOW()),

    -- 错误提示消息
    ('warehouse.code.already.exists', '仓库编号已存在', 'zh_CN', 'error', '1', NOW()),
    ('warehouse.code.already.exists', 'warehouse code already exists', 'en_US', 'error', '1', NOW()),
    ('update.warehouse.id.required', '修改仓库 id 必传', 'zh_CN', 'error', '1', NOW()),
    ('update.warehouse.id.required', 'update warehouse id must be required', 'en_US', 'error', '1', NOW()),
    ('warehouse.has.area.cannot.delete', '该仓库下存在关联的库区，无法删除', 'zh_CN', 'error', '1', NOW()),
    ('warehouse.has.area.cannot.delete', 'The warehouse has associated areas and cannot be deleted', 'en_US', 'error', '1', NOW()),
    ('warehouse.has.area.cannot.change.code', '该仓库下存在关联的库区，无法修改仓库编号', 'zh_CN', 'error', '1', NOW()),
    ('warehouse.has.area.cannot.change.code', 'The warehouse has associated areas and cannot change the warehouse code', 'en_US', 'error', '1', NOW()),

    -- excel 导出字段
    -- 仓库编号
    ('warehouse.code', '仓库编号', 'zh_CN', 'business', '1', NOW()),
    ('warehouse.code', 'Warehouse Code', 'en_US', 'business', '1', NOW()),
    -- 仓库名称
    ('warehouse.name', '仓库名称', 'zh_CN', 'business', '1', NOW()),
    ('warehouse.name', 'Warehouse Name', 'en_US', 'business', '1', NOW()),
    -- 仓库描述
    ('warehouse.desc', '仓库描述', 'zh_CN', 'business', '1', NOW()),
    ('warehouse.desc', 'Warehouse Description', 'en_US', 'business', '1', NOW()),


    -- 工作站编码校验
    ('station.code.not.blank', '工作站编码不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('station.code.not.blank', 'Workstation code cannot be empty', 'en_US', 'validation', '1', NOW()),
    -- 工作站编码唯一校验
    ('station.code.exists', '工作站编码已存在', 'zh_CN', 'validation', '1', NOW()),
    ('station.code.exists', 'The workstation code already exists', 'en_US', 'validation', '1', NOW()),
    -- 工作站名称校验
    ('station.name.not.blank', '工作站名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('station.name.not.blank', 'Workstation name cannot be empty', 'en_US', 'validation', '1', NOW()),
    -- 库区编码校验
    ('area.code.not.blank', '库区编码不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('area.code.not.blank', 'Area code cannot be empty', 'en_US', 'validation', '1', NOW()),
    -- 区域编码唯一校验
    ('area.code.exists', '库区编码已存在', 'zh_CN', 'validation', '1', NOW()),
    ('area.code.exists', 'The area code already exists', 'en_US', 'validation', '1', NOW()),
    -- 库区名称校验
    ('area.name.not.blank', '库区名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('area.name.not.blank', 'Area name cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- ========== 物料分类管理 ==========
    -- 验证消息
    ('material.category.code.not.blank', '物料分类编码不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.category.code.not.blank', 'Material category code cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('material.category.name.not.blank', '物料分类名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.category.name.not.blank', 'Material category name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('material.category.id.not.null', '分类 ID 不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.category.id.not.null', 'Category ID cannot be null', 'en_US', 'validation', '1', NOW()),

    -- 错误消息
    ('material.category.code.already.exists', '分类编码已存在', 'zh_CN', 'error', '1', NOW()),
    ('material.category.code.already.exists', 'Category code already exists', 'en_US', 'error', '1', NOW()),
    ('material.category.has.children', '该分类存在子分类，无法删除', 'zh_CN', 'validation', '1', NOW()),
    ('material.category.has.children', 'This category has child categories and cannot be deleted', 'en_US', 'validation', '1', NOW()),
    ('material.category.has.materials', '该分类下存在物料，无法删除', 'zh_CN', 'validation', '1', NOW()),
    ('material.category.has.materials', 'This category has materials and cannot be deleted', 'en_US', 'validation', '1', NOW()),

    -- excel 导出 sheet 名称
    ('material.category.export.sheet.name', '物料分类数据', 'zh_CN', 'business', '1', NOW()),
    ('material.category.export.sheet.name', 'Material Category Data', 'en_US', 'business', '1', NOW()),

    -- excel 导出字段
    -- 物料分类编码
    ('material.category.code', '物料分类编码', 'zh_CN', 'business', '1', NOW()),
    ('material.category.code', 'Material Category Code', 'en_US', 'business', '1', NOW()),
    -- 物料分类名称
    ('material.category.name', '物料分类名称', 'zh_CN', 'business', '1', NOW()),
    ('material.category.name', 'Material Category Name', 'en_US', 'business', '1', NOW()),

    -- ========== 物料管理 ==========
    -- 验证消息
    ('material.code.not.blank', '物料编码不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.code.not.blank', 'Material code cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('material.name.not.blank', '物料名称不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.name.not.blank', 'Material name cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('material.category.id.not.blank', '物料分类id不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.category.id.not.blank', 'Material category id cannot be empty', 'en_US', 'validation', '1', NOW()),
    ('material.id.not.null', '物料 ID 不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.id.not.null', 'Material ID cannot be null', 'en_US', 'validation', '1', NOW()),
    -- 错误消息
    ('material.code.already.exists', '物料编码已存在', 'zh_CN', 'error', '1', NOW()),
    ('material.code.already.exists', 'Material code already exists', 'en_US', 'error', '1', NOW()),
    ('material.category.id.not.exists', '物料分类id不存在', 'zh_CN', 'error', '1', NOW()),
    ('material.category.id.not.exists', 'material category id not exists', 'en_US', 'error', '1', NOW()),
    -- excel 导出 sheet 名称
    ('material.export.sheet.name', '物料数据', 'zh_CN', 'business', '1', NOW()),
    ('material.export.sheet.name', 'Material Data', 'en_US', 'business', '1', NOW()),

    -- excel 导出字段
    -- 物料编码
    ('material.code', '物料编码', 'zh_CN', 'business', '1', NOW()),
    ('material.code', 'Material Code', 'en_US', 'business', '1', NOW()),
    -- 物料名称
    ('material.name', '物料名称', 'zh_CN', 'business', '1', NOW()),
    ('material.name', 'Material Name', 'en_US', 'business', '1', NOW()),
    -- 物料条码
    ('material.barcode', '物料条码', 'zh_CN', 'business', '1', NOW()),
    ('material.barcode', 'Material Barcode', 'en_US', 'business', '1', NOW()),
    -- 规格型号
    ('material.specification', '规格型号', 'zh_CN', 'business', '1', NOW()),
    ('material.specification', 'Specification', 'en_US', 'business', '1', NOW()),
    -- 单位
    ('material.unit', '单位', 'zh_CN', 'business', '1', NOW()),
    ('material.unit', 'Unit', 'en_US', 'business', '1', NOW()),
    -- 长 (单位:mm)
    ('material.length', '长度 (mm)', 'zh_CN', 'business', '1', NOW()),
    ('material.length', 'Length (mm)', 'en_US', 'business', '1', NOW()),
    -- 高 (单位:mm)
    ('material.height', '高度 (mm)', 'zh_CN', 'business', '1', NOW()),
    ('material.height', 'Height (mm)', 'en_US', 'business', '1', NOW()),
    -- 宽 (单位:mm)
    ('material.width', '宽度 (mm)', 'zh_CN', 'business', '1', NOW()),
    ('material.width', 'Width (mm)', 'en_US', 'business', '1', NOW()),
    -- 体积 (单位 mm³)
    ('material.volume', '体积 (mm³)', 'zh_CN', 'business', '1', NOW()),
    ('material.volume', 'Volume (mm³)', 'en_US', 'business', '1', NOW()),
    -- 毛重 (单位 kg)
    ('material.gross.weight', '毛重 (kg)', 'zh_CN', 'business', '1', NOW()),
    ('material.gross.weight', 'Gross Weight (kg)', 'en_US', 'business', '1', NOW()),
    -- 净重 (单位 kg)
    ('material.net.weight', '净重 (kg)', 'zh_CN', 'business', '1', NOW()),
    ('material.net.weight', 'Net Weight (kg)', 'en_US', 'business', '1', NOW()),
    -- 保质期 (天)
    ('material.shelf.life', '保质期 (天)', 'zh_CN', 'business', '1', NOW()),
    ('material.shelf.life', 'Shelf Life (Days)', 'en_US', 'business', '1', NOW()),
    -- 是否 SN 管理
    ('material.is.sn.managed', '是否 SN 管理', 'zh_CN', 'business', '1', NOW()),
    ('material.is.sn.managed', 'Is SN Managed', 'en_US', 'business', '1', NOW()),
    -- 是否批次管理
    ('material.is.batch.managed', '是否批次管理', 'zh_CN', 'business', '1', NOW()),
    ('material.is.batch.managed', 'Is Batch Managed', 'en_US', 'business', '1', NOW()),

    -- ========== 物料属性扩展验证消息 ==========
    -- 属性名重复
    ('material.attr.name.duplicate', '同一物料下属性名不能重复', 'zh_CN', 'validation', '1', NOW()),
    ('material.attr.name.duplicate', 'Attribute name cannot be duplicate under the same material', 'en_US', 'validation', '1', NOW()),

    -- 物料 ID 不能为空
    ('material.attr.materialId.not.null', '物料 ID 不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.attr.materialId.not.null', 'Material ID cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 属性名不能为空
    ('material.attr.name.not.blank', '属性名不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.attr.name.not.blank', 'Attribute name cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 属性值不能为空
    ('material.attr.value.not.blank', '属性值不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.attr.value.not.blank', 'Attribute value cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 修改时 ID 不能为空
    ('material.attr.id.not.null', 'ID 不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('material.attr.id.not.null', 'ID cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 物料 id 不存在
    ('material.id.not.exists', '物料 id 不存在', 'zh_CN', 'validation', '1', NOW()),
    ('material.id.not.exists', 'Material ID not exists', 'en_US', 'validation', '1', NOW()),

    -- ========== 批次属性验证消息 ==========
    -- 批次属性编码重复
    ('batch.attr.code.duplicate', '批次属性编码已存在', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.code.duplicate', 'Batch attribute code already exists', 'en_US', 'validation', '1', NOW()),

    -- 属性值类型是枚举,属性枚举不能为空
    ('batch.attr.enum.required', '属性值类型是枚举,属性枚举不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.enum.required', 'The attribute value type is an enumeration, and the attribute enumeration cannot be null', 'en_US', 'validation', '1', NOW()),

    -- ID 不能为空
    ('batch.attr.id.not.null', 'ID 不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.id.not.null', 'ID cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 批次属性编码不能为空
    ('batch.attr.code.not.blank', '批次属性编码不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.code.not.blank', 'Batch attribute code cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 属性顺序不能为空
    ('batch.attr.index.not.null', '属性顺序不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.index.not.null', 'Attribute index cannot be null', 'en_US', 'validation', '1', NOW()),

    -- 属性顺序的值在1-15之间
    ('batch.attr.index.size', '属性顺序的值在1-15之间', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.index.size', 'Attribute index value is between 1-15', 'en_US', 'validation', '1', NOW()),

    -- 属性类型不能为空
    ('batch.attr.type.not.blank', '属性类型不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.type.not.blank', 'Attribute type cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 是否必填不能为空
    ('batch.attr.required.not.blank', '是否必填不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.required.not.blank', 'required cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 批次属性顺序重复
    ('batch.attr.index.duplicate', '批次属性顺序已存在', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attr.index.duplicate', 'Batch attribute index already exists', 'en_US', 'validation', '1', NOW()),

    -- 批次属性项字段校验（用于 BatchNumberCreateRequest）
    ('batch.attribute.id.not.null', '批次属性 ID 不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attribute.id.not.null', 'Batch attribute ID cannot be null', 'en_US', 'validation', '1', NOW()),

    ('batch.attribute.code.not.blank', '批次属性编码不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attribute.code.not.blank', 'Batch attribute code cannot be blank', 'en_US', 'validation', '1', NOW()),

    ('batch.attribute.index.not.null', '属性顺序不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.attribute.index.not.null', 'Attribute index cannot be null', 'en_US', 'validation', '1', NOW()),

    -- id不能为空
    ('id.not.null', 'id不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('id.not.null', 'id cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 状态不能为空
    ('status.not.null', '状态不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('status.not.null', 'status cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- ========== 批次号管理 ==========
    -- 验证消息
    ('batch.number.attributes.not.empty', '批次属性列表不能为空', 'zh_CN', 'validation', '1', NOW()),
    ('batch.number.attributes.not.empty', 'Batch attribute list cannot be empty', 'en_US', 'validation', '1', NOW()),

    -- 错误提示消息
    ('batch.number.strategy.not.found', '未找到批次号生成策略 [{0}]', 'zh_CN', 'error', '1', NOW()),
    ('batch.number.strategy.not.found', 'Batch number generation strategy [{0}] not found', 'en_US', 'error', '1', NOW()),
    ('batch.number.generation.failed', '批次号生成失败', 'zh_CN', 'error', '1', NOW()),
    ('batch.number.generation.failed', 'Batch number generation failed', 'en_US', 'error', '1', NOW()),
    ('batch.number.duplicate.validate', '批次号不能重复', 'zh_CN', 'error', '1', NOW()),
    ('batch.number.duplicate.validate', 'Batch number cannot be duplicate', 'en_US', 'error', '1', NOW()),
    ('batch.number.not.found', '批次号【{0}】不存在', 'zh_CN', 'error', '1', NOW()),
    ('batch.number.not.found', 'Batch number [{0}] not found', 'en_US', 'error', '1', NOW()),
    ('batch.attribute.required', '批次属性【{0}】为必填项', 'zh_CN', 'error', '1', NOW()),
    ('batch.attribute.required', 'Batch attribute [{0}] is required', 'en_US', 'error', '1', NOW()),
    ('batch.attribute.not.found', '批次属性【{0}】不存在', 'zh_CN', 'error', '1', NOW()),
    ('batch.attribute.not.found', 'Batch attribute [{0}] not found', 'en_US', 'error', '1', NOW()),
    ('batch.attribute.value.invalid', '批次属性【{0}】值非法,不在字典定义的枚举中', 'zh_CN', 'error', '1', NOW()),
    ('batch.attribute.value.invalid', 'Batch attribute [{0}] value is illegal and is not in the enumeration defined in the dictionary', 'en_US', 'error', '1', NOW()),
    ('point.import.empty', '导入点位数据不能为空！', 'zh_CN', 'system', '1', NOW()),
    ('point.import.empty', 'Import point data cannot be empty!', 'en_US', 'system', '1', NOW()),
    -- 点位校验
    ('point.import.success.item', '{0}、点位 {1} 导入成功', 'zh_CN', 'system', '1', NOW()),
    ('point.import.success.item', '{0}, Point {1} imported successfully', 'en_US', 'system', '1', NOW()),

    ('point.import.update.item', '{0}、点位 {1} 更新成功', 'zh_CN', 'system', '1', NOW()),
    ('point.import.update.item', '{0}, Point {1} updated successfully', 'en_US', 'system', '1', NOW()),

    ('point.import.exist.item', '{0}、点位 {1} 已存在', 'zh_CN', 'system', '1', NOW()),
    ('point.import.exist.item', '{0}, Point {1} already exists', 'en_US', 'system', '1', NOW()),

    ('point.import.fail.item', '{0}、点位 {1} 导入失败：{2}', 'zh_CN', 'system', '1', NOW()),
    ('point.import.fail.item', '{0}, Point {1} import failed: {2}', 'en_US', 'system', '1', NOW()),

    ('point.import.fail.total', '很抱歉，导入失败！共 {0} 条数据格式不正确，错误如下：', 'zh_CN', 'system', '1', NOW()),
    ('point.import.fail.total', 'Sorry, import failed! Total {0} records have format errors as follows:', 'en_US', 'system', '1', NOW()),

    ('point.import.success.total', '恭喜您，数据已全部导入成功！共 {0} 条，数据如下：', 'zh_CN', 'system', '1', NOW()),
    ('point.import.success.total', 'Congratulations, all data imported successfully! Total {0} records, data as follows:', 'en_US', 'system', '1', NOW()),
    ('location.container.location.exists', '库位编码 {0} 已经绑定容器，关系为一对一，请检查', 'zh_CN', 'error', '0', sysdate()),
    ('location.container.location.exists', 'Location code {0} is already bound to a container. The relationship is 1:1, please review', 'en_US', 'error', '0', sysdate()),
    ('location.container.container.exists', '容器编码 {0} 已经存在绑定的库位，关系为一对一，请检查', 'zh_CN', 'error', '0', sysdate()),
    ('location.container.container.exists', 'Container code {0} is already bound to a location. The relationship is 1:1, please review', 'en_US', 'error', '0', sysdate()),
    ('location.lock.exists', '库位编码 {0} 已经被锁定', 'zh_CN', 'error', '0', sysdate()),
    ('location.lock.exists', 'Location code {0} is already locked', 'en_US', 'error', '0', sysdate()),
    ('point.location.exists', '存在重复的库位和点位关联关系：点位 {0} 和库位 {1}', 'zh_CN', 'error', '0', sysdate()),
    ('point.location.exists', 'Duplicate point-location relation exists: Point {0} Location {1}', 'en_US', 'error', '0', sysdate()),

    -- 可视化区域相关国际化配置
    ('visualArea.code.not.blank', '可视化区域编码不可为空', 'zh_CN', 'system', '1', NOW()),
    ('visualArea.code.not.blank', 'Visual area code cannot be blank', 'en_US', 'system', '1', NOW()),
    ('visualArea.name.not.blank', '可视化区域名称不可为空', 'zh_CN', 'system', '1', NOW()),
    ('visualArea.name.not.blank', 'Visual area name cannot be blank', 'en_US', 'system', '1', NOW()),
    ('visualArea.code.exists', '可视化编码已存在', 'zh_CN', 'system', '1', NOW()),
    ('visualArea.code.exists', 'Visual area code already exists', 'en_US', 'system', '1', NOW()),

    -- 点位相关国际化配置
    ('point.code.supplier.exits', '点位编码及设备供应商编码已存在', 'zh_CN', 'system', '1', NOW()),
    ('point.code.supplier.exits', 'Point code and supplier code already exists', 'en_US', 'system', '1', NOW()),
    ('point.code.not.blank', '点位编码不能为空', 'zh_CN', 'system', '1', NOW()),
    ('point.code.not.blank', 'Point code cannot be blank', 'en_US', 'system', '1', NOW()),
    ('point.name.not.blank', '点位名称不能为空', 'zh_CN', 'system', '1', NOW()),
    ('point.name.not.blank', 'Point name cannot be blank', 'en_US', 'system', '1', NOW()),
    ('point.type.not.blank', '点位类型不能为空', 'zh_CN', 'system', '1', NOW()),
    ('point.type.not.blank', 'Point type cannot be blank', 'en_US', 'system', '1', NOW()),

    -- 库位相关国际化配置
    ('location.code.not.blank', '库位编码不能为空', 'zh_CN', 'system', '1', NOW()),
    ('location.code.not.blank', 'Location code cannot be blank', 'en_US', 'system', '1', NOW()),

    -- 应用相关国际化配置
    ('app.code.not.blank', '应用编码不能为空', 'zh_CN', 'system', '1', NOW()),
    ('app.code.not.blank', 'App code cannot be blank', 'en_US', 'system', '1', NOW()),
    ('app.name.not.blank', '应用名称不能为空', 'zh_CN', 'system', '1', NOW()),
    ('app.name.not.blank', 'App name cannot be blank', 'en_US', 'system', '1', NOW()),
    ('app.status.not.blank', '应用状态不能为空', 'zh_CN', 'system', '1', NOW()),
    ('app.status.not.blank', 'App status cannot be blank', 'en_US', 'system', '1', NOW()),
    ('app.code.exists', '应用编码已存在', 'zh_CN', 'system', '1', NOW()),
    ('app.code.exists', 'App code already exists', 'en_US', 'system', '1', NOW()),
    ('app.id.not.null', '修改应用 id 不可为空', 'zh_CN', 'system', '1', NOW()),
    ('app.id.not.null', 'App ID cannot be null for update', 'en_US', 'system', '1', NOW()),

    -- 锁定相关国际化配置
    ('lock.source.not.blank', '锁定源不可为空', 'zh_CN', 'system', '1', NOW()),
    ('lock.source.not.blank', 'Lock source cannot be blank', 'en_US', 'system', '1', NOW()),

    -- 关系相关国际化配置
    ('relationship.type.not.blank', '关系类型不能为空', 'zh_CN', 'system', '1', NOW()),
    ('relationship.type.not.blank', 'Relationship type cannot be blank', 'en_US', 'system', '1', NOW()),

    -- 点位库位关联相关国际化配置
    ('point.location.code.not.meanwhile.blank', '点位编码，库位编码不能同时为空', 'zh_CN', 'system', '1', NOW()),
    ('point.location.code.not.meanwhile.blank', 'Point code and location code cannot both be blank', 'en_US', 'system', '1', NOW()),

    -- 库位操作相关国际化配置
    ('location.opr.data.absence', '库位操作参数缺失', 'zh_CN', 'system', '1', NOW()),
    ('location.opr.data.absence', 'Location operation data absence', 'en_US', 'system', '1', NOW()),
    ('location.opr.fail', '库位操作失败', 'zh_CN', 'system', '1', NOW()),
    ('location.opr.fail', 'Location operation failed', 'en_US', 'system', '1', NOW()),

    -- 可视化区域相关国际化配置
    ('visualArea.id.not.null', '可视化区域 ID 不可为空', 'zh_CN', 'system', '1', NOW()),
    ('visualArea.id.not.null', 'Visual area ID cannot be null', 'en_US', 'system', '1', NOW()),

    -- 库位容器模型相关国际化配置
    ('location.allow.container.model.absence', '新增库位容器准入模型不可为空', 'zh_CN', 'system', '1', NOW()),
    ('location.allow.container.model.absence', 'Location allow container model is required', 'en_US', 'system', '1', NOW()),
    ('location.code.not.exist', '库位编码不存在', 'zh_CN', 'system', '1', NOW()),
    ('location.code.not.exist', 'Location code does not exist', 'en_US', 'system', '1', NOW()),
    ('container.code.not.exist', '容器编码不存在', 'zh_CN', 'system', '1', NOW()),
    ('container.code.not.exist', 'Container code does not exist', 'en_US', 'system', '1', NOW()),
    ('location.allow.container.model.not.match', '该库位不支持此容器，请查看库位可装容器', 'zh_CN', 'system', '1', NOW()),
    ('location.allow.container.model.not.match', 'This location does not support this container, please check location allow container model', 'en_US', 'system', '1', NOW()),
    ('location.container.exists.forbid.delete', '当前库位已有容器，禁止删除', 'zh_CN', 'location', '1', NOW()),
    ('location.container.exists.forbid.delete', 'The location already has containers and cannot be deleted', 'en_US', 'location', '1', NOW()),
    ('point.id.not.blank', '点位 ID 不能为空', 'zh_CN', 'point', '1', NOW()),
    ('point.id.not.blank', 'Point ID cannot be blank', 'en_US', 'point', '1', NOW()),
    ('location.id.not.blank', '库位 ID 不能为空', 'zh_CN', 'location', '1', NOW()),
    ('location.id.not.blank', 'Location ID cannot be blank', 'en_US', 'location', '1', NOW()),
    ('station.point.exits', '工作站已经关联该点位', 'zh_CN', 'system', '1', NOW()),
    ('station.point.exits', 'Workstation is already associated with this point', 'en_US', 'system', '1', NOW()),
    ('point.delete.location.exits', '该点位已绑定库位，请先解除绑定关系', 'zh_CN', 'system', '1', NOW()),
    ('point.delete.location.exits', 'This point is already bound to a location, please unbind the relationship first', 'en_US', 'system', '1', NOW()),
    ('location.code.exists', '库位编码已存在', 'zh_CN', 'system', '1', NOW()),
    ('location.code.exists', 'Location code already exists', 'en_US', 'system', '1', NOW());
