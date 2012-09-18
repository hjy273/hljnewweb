--更新空间字段触发器

DROP TRIGGER HIDDANGER_UPDATE_GEOLOC;

CREATE OR REPLACE TRIGGER hiddanger_UPDATE_GEOLOC
   BEFORE INSERT
   ON BJUSER.LP_HIDDANGER_REGIST    FOR EACH ROW
BEGIN
   :new.geoloc :=
      MDSYS.SDO_GEOMETRY (2001,
                          8307,
                          NULL,
                          MDSYS.SDO_ELEM_INFO_ARRAY (1, 1, 1),
                          MDSYS.SDO_ORDINATE_ARRAY (:new.x, :new.y));
END hiddanger_UPDATE_GEOLOC;
/


隐患与盯防计划关联表
create table LP_HIDDANGER_PLAN  (
   ID                   VARCHAR2(12),
   HIDDANGER_ID         VARCHAR2(12),
   PLAN_ID              VARCHAR2(12),
   CREATE_TIME          DATE
);

终止计划表
create table LP_HIDDANGER_ENDPLAN  (
   ID                   VARCHAR2(12),
   PLAN_ID              VARCHAR2(12),
   END_TYPE             VARCHAR2(1),
   REASON               VARCHAR2(1024)，
   CREATER              VARCHAR2(30)
);

增加了一个菜单
insert into sonmenu values('220202','手持设备登记'，'2202','/WebApp/hiddangerAction.do?method=terminalRegistResult','','','11,12,21,22')

REGIST表删除了一个字段
alter table LP_HIDDANGER_REGIST drop column ISWATCHCOMPLETE

REGIST表新增了一个字段
alter table LP_HIDDANGER_REGIST add TERMINALSTATE VARCHAR2(1)
