--���¿ռ��ֶδ�����

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


�����붢���ƻ�������
create table LP_HIDDANGER_PLAN  (
   ID                   VARCHAR2(12),
   HIDDANGER_ID         VARCHAR2(12),
   PLAN_ID              VARCHAR2(12),
   CREATE_TIME          DATE
);

��ֹ�ƻ���
create table LP_HIDDANGER_ENDPLAN  (
   ID                   VARCHAR2(12),
   PLAN_ID              VARCHAR2(12),
   END_TYPE             VARCHAR2(1),
   REASON               VARCHAR2(1024)��
   CREATER              VARCHAR2(30)
);

������һ���˵�
insert into sonmenu values('220202','�ֳ��豸�Ǽ�'��'2202','/WebApp/hiddangerAction.do?method=terminalRegistResult','','','11,12,21,22')

REGIST��ɾ����һ���ֶ�
alter table LP_HIDDANGER_REGIST drop column ISWATCHCOMPLETE

REGIST��������һ���ֶ�
alter table LP_HIDDANGER_REGIST add TERMINALSTATE VARCHAR2(1)
