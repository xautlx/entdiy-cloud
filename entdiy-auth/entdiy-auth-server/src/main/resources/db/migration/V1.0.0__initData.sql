create table if not exists oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

-- dev/test123 主要用于开发调试用途，请勿在生产环境执行
-- insert into oauth_client_details(client_id,client_secret,scope,authorized_grant_types,access_token_validity)
-- values ('dev','$2a$10$1o7RdZbMDkZXbMtUVMB4.OiFkyLAX1hfVyQ3bG/8y1nqGMzkzKoJK','read,write','client_credentials,password,implicit,refresh_token,authorization_code',-1);

-- root/test123
insert into oauth_client_details(client_id, client_secret, scope, authorized_grant_types)
values ('test', '$2a$10$1o7RdZbMDkZXbMtUVMB4.OiFkyLAX1hfVyQ3bG/8y1nqGMzkzKoJK', 'read,write',
        'client_credentials,password,implicit,refresh_token,authorization_code');

-- web-admin/open
insert into oauth_client_details(client_id, client_secret, scope, authorized_grant_types)
values ('web-admin', '$2a$10$AT6eWJo3CLskfzBI9XDO8OVaJ03r9yKDiU0ZMLnlT0mlxR9IkVMGC', 'read,write',
        'client_credentials,password,implicit,refresh_token,authorization_code');

-- 添加mdm服务模块client认证数据：entdiy-mdm/test123
insert into oauth_client_details(client_id, client_secret, scope, authorized_grant_types)
values ('entdiy-mdm', '$2a$10$1o7RdZbMDkZXbMtUVMB4.OiFkyLAX1hfVyQ3bG/8y1nqGMzkzKoJK', 'read,write',
        'client_credentials,password,implicit,refresh_token,authorization_code');
