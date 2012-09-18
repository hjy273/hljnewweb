insert into DICTIONARY_ASSORTMENT (ASSORTMENT_ID, SUBJECTION, ASSORTMENT_DESC)
values ('cablelevel', 'cablelevel', '光缆级别');
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (1, '1', '一干', 'cablelevel', 1);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (2, '2', '接入', 'cablelevel', 3);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (3, '3', '汇聚', 'cablelevel', 4);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (4, '4', '骨干', 'cablelevel', 2);
commit;
insert into DICTIONARY_ASSORTMENT (ASSORTMENT_ID, SUBJECTION, ASSORTMENT_DESC)
values ('layingmethod', 'layingmethod', '敷设方式');
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (5, '1', '架空', 'layingmethod', 1);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (6, '2', '管道', 'layingmethod', 2);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (7, '3', '室内', 'layingmethod', 3);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (8, '4', '槽道', 'layingmethod', 4);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (9, '5', '直埋', 'layingmethod', 5);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (10, '6', '沿墙', 'layingmethod', 6);
commit;
insert into DICTIONARY_ASSORTMENT (ASSORTMENT_ID, SUBJECTION, ASSORTMENT_DESC)
values ('lp_trouble_type', 'lp_trouble_type', '故障类型');
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (11, '1', '外力', 'lp_trouble_type', 1);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (12, '2', '人为', 'lp_trouble_type', 2);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (13, '3', '常规', 'lp_trouble_type', 3);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (14, '4', '工程', 'lp_trouble_type', 4);
insert into DICTIONARY_FORMITEM (ID, CODE, LABLE, ASSORTMENT_ID, SORT)
values (15, '5', '其他', 'lp_trouble_type', 5);
commit;