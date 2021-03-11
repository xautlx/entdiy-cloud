-- 超级租户，用于关联做租户管理权限的用户
INSERT INTO sys_tenant(id, tenant_code, tenant_name, remark, account_non_locked)
VALUES (1, 'root', '管理租户', '系统内置超级管理租户，请勿删除', 1);

-- admin/123456
INSERT INTO auth_user(id, account_name, password, tenant_id, account_non_locked)
VALUES (1, 'admin', '$2a$10$1UgtvvSBWVfIU29RQRFlsOWaDJZRJg6cf2xN0zhAL0timcZ4leAw2', 1,1);

INSERT INTO sys_dict(id, code, name, element_type, tenant_id)
VALUES (1, 'accountNonLocked', '账户启用状态', 60, 1);
INSERT INTO sys_dict_item(id, dict_id, dict_key, simple_text_value, element_type, sort_order, tenant_id)
VALUES (1, 1, 'true', '启用', 60, 10,1);
INSERT INTO sys_dict_item(id, dict_id, dict_key, simple_text_value, element_type, sort_order, tenant_id)
VALUES (2, 1, 'false', '停用', 60, 20,1);
