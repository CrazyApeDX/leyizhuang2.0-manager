--初始化 只运行一次

INSERT INTO td_navigation_menu(
	action_type,
	channel_id,
	icon_id,
	is_enable,
	link_url,
	name,
	parent_id,
	sort_id,
	title,
	type
) VALUES(
	'view',
	'0',
	NULL,
	TRUE,
	'/cash/return/note/list',
	'channel_cash_return_note_list',
	87,
	10,
	'退款申请列表',
	'system'
);
INSERT INTO td_navigation_menu(
	action_type,
	channel_id,
	icon_id,
	is_enable,
	link_url,
	name,
	parent_id,
	sort_id,
	title,
	type
) VALUES(
	'view',
	'0',
	NULL,
	TRUE,
	'/balance/list',
	'balance_log_list',
	43,
	4,
	'预存款变更记录',
	'system'
);
INSERT INTO td_navigation_menu(
	action_type,
	channel_id,
	icon_id,
	is_enable,
	link_url,
	name,
	parent_id,
	sort_id,
	title,
	type
) VALUES(
	'view',
	'0',
	NULL,
	TRUE,
	'/goods/inventory/log',
	'channel_inventory_log',
	26,
	6,
	'库存日志',
	'system'
);
