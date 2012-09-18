----�������� 
--����  ������  ˵��

--10-03-24 �Ż�� ��ά��λ��Ա��
alter table CONTRACTORPERSON add issendsms VARCHAR2(2);
comment on column CONTRACTORPERSON.issendsms is '�Ƿ���Խ��ն���';
--10-03-24 �Ż��  Ѳ���豸��
alter table TERMINALINFO add HOLDER VARCHAR2(100);
comment on column TERMINALINFO.holder  is '�豸������';

-- 2010-3-18����������ӻ���ص�
ALTER TABLE NOTICE_CLOB ADD (MEET_ADDRESS VARCHAR2(500));

--����ģ�� start 
   --20100312 fjj  �ڴ������������ �ֶ� �������xy���꣬�ҵ����ϵ�xy���꣬�����ֳ�xy����  
   alter table LP_TROUBLE_PROCESS add arrive_gps_x FLOAT;
   alter table LP_TROUBLE_PROCESS add arrive_gps_y FLOAT;
   alter table LP_TROUBLE_PROCESS add find_trouble_gps_x FLOAT;
   alter table LP_TROUBLE_PROCESS add find_trouble_gps_y FLOAT;
   alter table LP_TROUBLE_PROCESS add return_gps_x FLOAT;
   alter table LP_TROUBLE_PROCESS add return_gps_y FLOAT;
--����ģ�� end 

-- 10-03-27 ����������Դ�Ƿ���Ч 
alter table ANNEX_ADD_ONE add is_usable VARCHAR2(2) default 1;
comment on column ANNEX_ADD_ONE.is_usable is '1: ��ʾ��Դ��Ч��ͨ����ˣ�0��ʾ��Դ��Чû�����';
-- 2010-3-25�����������������ʷ��Ϣ�еĴ�����˵��
ALTER TABLE PROCESS_HISTORY_INFO ADD(PROCESS_ACTION VARCHAR2(1024));
-- 2010-3-27�����������������ʷ��Ϣ�е����������
ALTER TABLE PROCESS_HISTORY_INFO ADD(TASK_OUT_COME VARCHAR2(1024));





--20100327 ��ӷ����ύʱ��
alter table lp_cut add (deadline Date);
alter table lp_cut_feedback add (creator varchar2(20), create_time date);
alter table lp_cut_acceptance add (creator varchar2(20), create_time date);

--100320 ����ģ��

alter table lp_drilltask add drill_type varchar2(5);


--20100221����ǰ���������޸�
alter table lp_drilltask modify LOCALE varchar2(500);
alter table lp_drillplan add (BEGINTIME date,ENDTIME date,ADDRESS varchar2(500),equipment_NUMBER integer);
alter table lp_drillplan modify (SCENARIO varchar2(500),REMARK varchar2(500));
alter table lp_drillsummary add (equipment_NUMBER integer);
alter table lp_drilltask add (deadline date);
alter table lp_drillplan add (deadline date);

--2010-03-17������ʱ����
alter table lp_drilltask add (save_flag varchar2(1));



alter table lp_safeguard_scheme add (equipment_NUMBER integer);
alter table lp_safeguard_sum add (equipment_NUMBER integer,STARTTIME date,ENDTIME date);

--2010��2��10������12��


--add on 20100306
alter table lp_safeguard_task add(deadline date);
alter table lp_safeguard_scheme add(deadline date);


--10-03-20 fjj ����ά��
--20100114 �ڲ��Լƻ��еı�����Ϣ�������б�������Ƿ�¼��
alter table LP_TEST_PLAN_LINE add state varchar2(2);
     
--20100114 �ڲ��Լƻ��еĽӵص�����Ϣ�������б�������Ƿ�¼��
alter table LP_TEST_PLAN_STATION add state varchar2(2);
     
-- 20100116  �ӵص���¼��������Ϣ�� �������Ƿ�ϸ�is_eligible
alter table LP_TEST_STATION_DATA add is_eligible varchar2(2);
     
--   20100222 ��о¼��������Ϣ��  �������Ƿ񱣴�is_save
alter table LP_TEST_CHIP_DATA add is_save varchar2(5);
       
-- 20100222 �����м̶��б�  ������ԭ�����reason���ƻ������ʩtest_method���ƻ����ʱ�� test_time
alter table LP_TEST_PROBLEM add reason varchar2(1024);
alter table LP_TEST_PROBLEM add test_method varchar2(1024);
alter table LP_TEST_PROBLEM add test_time date;



--�� pipeline���м���һ���ֶ�ASSETNO VARCHAR2(50)

--2010��3��10���޸ģ����ƻ���������������ŵ��������
--alter table lp_acceptance_cable add PLAN_ACCEPTANCE_TIME DATE;
--alter table lp_acceptance_pipe add PLAN_ACCEPTANCE_TIME DATE;
--alter table lp_acceptance_apply add PLAN_ACCEPTANCE_TIME DATE;

--2010��3��10���޸ģ����ӹܵ��͹��±��е�����ͨ���ֶ�
alter table lp_acceptance_cable add ISPASSED VARCHAR2(1);
alter table lp_acceptance_pipe add ISPASSED VARCHAR2(1);

--2010��3��12���޸ģ����ӹܵ��͹��±��еĴ�ά�ֶ�
alter table lp_acceptance_cable add CONTRACTORID varchar2(32);
alter table lp_acceptance_pipe add CONTRACTORID varchar2(32);

--2010��3��17���޸ģ����ӹܵ��͹��±��еĹ�������
alter table lp_acceptance_cable add ISSUENUMBER VARCHAR2(100);
alter table lp_acceptance_pipe add ISSUENUMBER VARCHAR2(100);
--alter table repeatersection add ISSUENUMBER VARCHAR2(10);
--alter table pipeline add ISSUENUMBER VARCHAR2(10);
--alter table pipeline and ASSETNO VARCHAR2(50);

alter table pipeline add ISCHECKOUT VARCHAR2(2);
--2010��3��23���޸ģ����ӹܵ�����
alter table lp_acceptance_pipe add PIPE_TYPE VARCHAR2(12);


-- �����ֶζ��ࣨ��㣩
alter table repeatersection drop column issuenumber
alter table pipeline drop column issuenumber

--20100402���ڱ���������ʱ����
alter table lp_safeguard_task add save_flag varchar2(1) default '0';

alter table process_history_info modify object_type varchar(30);

-- 2010-4-1�����������֪ͨ�еĶ��ŷ�����Ϣ
ALTER TABLE NOTICE_CLOB ADD (SEND_METHOD VARCHAR2(3));
ALTER TABLE NOTICE_CLOB ADD (SM_BEGIN_DATE DATE);
ALTER TABLE NOTICE_CLOB ADD (SM_END_DATE DATE);
ALTER TABLE NOTICE_CLOB ADD (SEND_INTERVAL_TYPE VARCHAR2(3));
ALTER TABLE NOTICE_CLOB ADD (SEND_TIME_SPACE VARCHAR2(5));
-- 2010-4-1�����������֪ͨ�е�ǰһ�λ�����Ϣ
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_ADDRESS VARCHAR2(500));
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_PERSON VARCHAR2(2048));
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_TIME DATE);
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_END_TIME DATE);
ALTER TABLE NOTICE_CLOB ADD (IS_CANCELED VARCHAR2(3) DEFAULT '0');


alter table USERINFO modify PHONE VARCHAR2(1100);

-- 2010-4-9�������������ʱ���Ͷ��ŵķ��Ͷ������ֶγ���
ALTER TABLE SEND_SM_JOB_INFO MODIFY (SEND_OBJECT_ID VARCHAR2(1024));
-- 2010-4-12����������Ӷ�ʱ���Ͷ��ŵķ��Ͷ�����Ϣ˵��
ALTER TABLE SEND_SM_JOB_INFO ADD (SEND_OBJECT_NAME VARCHAR2(1024));
-- 2010-4-14����������Ӷ�ʱ���Ͷ��ŵ����ڹ�����Ϣ˵��
ALTER TABLE SEND_SM_JOB_INFO ADD (SEND_CYCLE_RULE VARCHAR2(512));
-- 2010-4-17����������Ӷ�ʱ���Ͷ��ŵ����ڴ���������Ϣ
ALTER TABLE SEND_SM_JOB_INFO ADD (CRON_EXPRESSION_STRING VARCHAR2(512));


--2010-04-17 zhj  �޸��ֵ�����������ֶ�
alter table DICTIONARY_FORMITEM add RegionID VARCHAR2(10);
comment on column DICTIONARY_FORMITEM.RegionID  is '����';
update dictionary_formitem set regionid='110000';
commit;
-- 2010-04-17 �޸��ֵ��id ����
alter table DICTIONARY_FORMITEM modify ID varchar2(12);
create sequence SEQ_DICTIONARY_FORMITEM_ID;

-- Create/Recreate primary, unique and foreign key constraints 
alter table DICTIONARY_FORMITEM  add constraint pk_dictonary_fromitem primary key (ID);

  
-- 2010-4-17����������Ӷ�ʱ���Ͷ��ŵ����ڴ���������Ϣ
ALTER TABLE SEND_SM_JOB_INFO ADD (CRON_EXPRESSION_STRING VARCHAR2(512));

--���̹��������ֶ�
alter table lp_remedy add mtotalfee float;

--�����ʾ����--������
alter table quest_issue_result modify score varchar2(500);
--4��21�� zhj ��Ѳ�ƻ�Ѳ������������Ѳ��ʱ��
alter table LP_SPECIAL_CIRCUIT add Start_Time CHAR(5);
alter table LP_SPECIAL_CIRCUIT add end_time CHAR(5);
comment on column LP_SPECIAL_CIRCUIT.START_TIME  is 'Ѳ��ʱ��--��ʼʱ��';
comment on column LP_SPECIAL_CIRCUIT.END_TIME   is 'Ѳ��ʱ��--����ʱ��';

--����·������
alter table repeatersection add route_desc varchar2(500);
update repeatersection set route_desc = place;
commit;
update repeatersection t set t.place='1' where t.place like '%��ɽ%';
update repeatersection t set t.place='2' where t.place like '%��ͷ��%';
update repeatersection t set t.place='3' where t.place like '%����%';
update repeatersection t set t.place='4' where t.place like '%��ƽ%';
update repeatersection t set t.place='5' where t.place like '%����%';
update repeatersection t set t.place='6' where t.place like '%����%';
update repeatersection t set t.place='7' where t.place like '%˳��%';
update repeatersection t set t.place='8' where t.place like '%ͨ��%';
update repeatersection t set t.place='9' where t.place like '%����%';
update repeatersection t set t.place='10' where t.place like '%ƽ��%';
commit;


-- ����ά����ƻ�����ÿ��Ĭ�ϲ��Դ���  20100426 by fjj
 alter table lp_test_year_plan add test_times varchar2(5);
 
 -- �ʾ�����ӷ����˺ͷ���ʱ��
 alter table quest_issue add (creator varchar2(20),create_date date);
 
 --20100429 fjj �������ӿۼ�ԭ��
  alter table LP_EXPENSE_MONTH add remark varchar2(1024);
  alter table LP_EXPENSE_AFFIRM add remark varchar2(1024);
 
-- 2010-04-29����������ӹ���ָ������״̬�ͳ��������޳�ʱ������
ALTER TABLE LP_TROUBLE_GUIDE_MONTH ADD (FINISH_STATE VARCHAR2(5) DEFAULT '1');
ALTER TABLE LP_TROUBLE_GUIDE_MONTH ADD (CITY_AREA_OUT_STANDARD_NUMBER NUMBER DEFAULT 0);
-- 2010-05-06 �Ż��  �м̶α������ӹ��·ϳ�״̬�ֶ�
alter table REPEATERSECTION add scrapstate VARCHAR2(10) default 'false';
comment on column REPEATERSECTION.scrapstate  is '��������״̬';

-- 20100112 ������̨ͳ��ϵͳ�����Ѳ�ƻ�ͳ�� START
ALTER TABLE TEMP_PATROL ADD (  PLANID VARCHAR2(40));


alter table lp_acceptance_apply drop column PLAN_ACCEPTANCE_TIME;
alter table lp_acceptance_cable add PLAN_ACCEPTANCE_TIME date;
alter table lp_acceptance_pipe add PLAN_ACCEPTANCE_TIME date;

--����ģ�������Ƿ�Ϊȫ������ 
alter table lp_safeguard_scheme add is_all VARCHAR2(2);
comment on column lp_safeguard_scheme.is_all is '�Ƿ�ȫ�����ϣ�1��0��';
 
 
-- �û����������ְ״̬����ְʱ������ 2010-5-6 ���ǻ�
alter table contractorperson add(leave_time date,conditions varchar2(4));
alter table CONTRACTORPERSON modify conditions default 0;--��ְ״̬ 0��ʾ��ְ 1����ʾ��ְ
update CONTRACTORPERSON set conditions=0;
commit;
ALTER TABLE ANNEX_ADD_ONE ADD (STATE  VARCHAR2(2 BYTE)   DEFAULT 0);

--��ANNEX_ADD_ONE�������ʹ��״̬����state 2010-6-12 ���ǻ�
COMMENT ON COLUMN ANNEX_ADD_ONE.STATE IS 'Ĭ��ֵΪ0����ʾ������1��ʾ��ɾ�������ֶα�ʾ�����Ƿ�ɾ����';

-- 2010-6-22 ���� ��Ӹ�Ӻ͹��ϵ�ȡ���˺�ȡ��ʱ����Ϣ
ALTER TABLE LP_TROUBLE_INFO ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE
);

ALTER TABLE LP_CUT ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE
);

ALTER TABLE LP_CUT MODIFY (STATE VARCHAR2(5));

-- 2010-6-28 ���� ��ӹ��Ϸ����ġ��Ƿ񱨰�����������˵���������Ƿ��ա���Ϣ��
ALTER TABLE LP_TROUBLE_REPLY ADD(
IS_REPORT_CASE VARCHAR2(5) DEFAULT '',
REPORT_CASE_COMMENT VARCHAR2(1024) DEFAULT '',
IS_REPORT_DANGER VARCHAR2(5) DEFAULT ''
);

-- 2010-6-29 ���� ��ӷ���ʱ��������
ALTER TABLE LP_TROUBLE_INFO ADD(
REPLY_DEADLINE VARCHAR2(20) DEFAULT ''
);

-- 2010-7-12 ���� �޸ķ���ʱ�������е���������ΪDATE����
-- ִ�и����ǰ��ִ����������
-- UPDATE LP_TROUBLE_INFO SET REPLY_DEADLINE=NULL ;
-- COMMIT;
ALTER TABLE LP_TROUBLE_INFO MODIFY (REPLY_DEADLINE DATE);

-- 2010-7-29 ���� ���ȡ��ԭ����
ALTER TABLE LP_CUT ADD (CANCEL_REASON VARCHAR2(1024));
ALTER TABLE LP_SENDTASK ADD (CANCEL_REASON VARCHAR2(1024));
ALTER TABLE LP_TROUBLE_INFO ADD (CANCEL_REASON VARCHAR2(1024));

-- Add/modify columns 
alter table LP_ACCEPTANCE_CABLE add PLAN_ACCEPTANCE_TIME2 date;

-- 2010-8-5 ���� ���ȡ����Ϣ
ALTER TABLE LP_ACCEPTANCE_APPLY ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024)
);

ALTER TABLE LP_DRILLTASK ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024),
DRILL_STATE VARCHAR2(5)
);

ALTER TABLE LP_HIDDANGER_REGIST ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024),
HIDE_DANGER_STATE VARCHAR2(5)
);

ALTER TABLE LP_MT_NEW ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024)
);

ALTER TABLE LP_MT_NEW MODIFY (STATE VARCHAR2(5));

----
ALTER TABLE LP_OVERHAUL ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024)
);
---
ALTER TABLE LP_REMEDY ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024)
);

ALTER TABLE LP_SAFEGUARD_TASK ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024),
SAFEGUARD_STATE VARCHAR2(5)
);

ALTER TABLE LP_TEST_PLAN ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE,
CANCEL_REASON VARCHAR2(1024)
);

--modify ������Ϣ���ݳ���
alter table LP_TROUBLE_REPLY modify report_case_comment VARCHAR2(2048);

--�ܵ������������ӱ�ע2��3
-- Add/modify columns 
alter table LP_ACCEPTANCE_PIPE add remark2 VARCHAR2(2048);
alter table LP_ACCEPTANCE_PIPE add remark3 VARCHAR2(2048);
alter table LP_ACCEPTANCE_PIPE modify issuenumber VARCHAR2(500);
-- Add/modify columns 
alter table LP_ACCEPTANCE_CABLE modify issuenumber VARCHAR2(500);
-- Add/modify columns 
alter table LP_SAFEGUARD_CON modify transact_state VARCHAR2(3);

alter table LP_ACCEPTANCE_APPLY modify name VARCHAR2(200);

/*
create table lp_special_taskpoint  (
   id                   varchar(12)                     not null,
   taskroute_id         varchar(12),
   point_id             varchar(12),
   constraint pk_lp_special_taskpoint primary key (id)
);

comment on column lp_special_taskpoint.taskroute_id is
'����·��ID';

comment on column lp_special_taskpoint.point_id is
'Ѳ���ID';

*/
--pointinfo �����ӱ�ע
alter table POINTINFO add remark VARCHAR2(500);

--�ճ�������ӿ�����
alter table lp_appraise_daily add APPRAISER varchar2(20);

--11-04-13ѦԪ�� ����  ά�����Ͼ�������ֵ
alter table LP_TROUBLE_GUIDE_MONTH add revise_maintenance_length NUMBER(8,2)DEFAULT 0 ;
comment on column LP_TROUBLE_GUIDE_MONTH.revise_maintenance_length is 'ά�����Ͼ�������ֵ';
--��������ָ��˵����
alter table LP_ACCEPTANCE_APPLY add remark VARCHAR2(1024);
--11-04-18ѦԪ��  ������ֵ�͵�ȱʡֵ 0
alter table LP_TROUBLE_GUIDE_MONTH modify MAINTENANCE_LENGTH default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_NORM_TIMES default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_DARE_TIMES default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_NORM_TIME default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_DARE_TIME default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_TIME default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify REVISE_INTERDICTION_TIME default 0;

--�ɵ��������������Ƿ��ճ����˹����λ
alter table lp_sendtask_check add exam_flag varchar2(2) default '0';
 ALTER TABLE LP_APPRAISE_RESULT ADD (Confirm_result  VARCHAR2(2));
ALTER TABLE LP_APPRAISE_RESULT RENAME COLUMN APPRAISE_MONTH TO APPRAISE_time;
-- 2011-05-05 ���ǻ� �������ս�ά���±������跽ʽ�ֶεĳ���
ALTER TABLE LP_ACCEPTANCE_CABLE MODIFY(LAYING_METHOD VARCHAR2(20 BYTE));

alter table mainmodulemenu add businesstype VARCHAR2(2);
--2011-07-28 ���ǻ�
 ALTER TABLE LP_APPRAISE_RESULT ADD (Confirm_result  VARCHAR2(2));
--2011-08-02 ���ǻ� ����linetype�����ݳ���
ALTER TABLE SUBLINEINFO MODIFY(LINETYPE VARCHAR2(4 BYTE)); 
 
 
--2011-08-11 ���� �޸�SUBLINECABLESEGMENT��CABLESEGMENTID�����ݳ���
ALTER TABLE SUBLINECABLESEGMENT MODIFY(CABLESEGMENTID VARCHAR2(12));

--2011-08-22 ���� �޸�POINTINFO��ISFOCUS��Ĭ��ֵ
ALTER TABLE POINTINFO MODIFY(ISFOCUS VARCHAR2(2) DEFAULT '0');

--2011-08-22 ���� �޸�REPEATERSECTION��LAYTYPE�ֶγ���
ALTER TABLE REPEATERSECTION MODIFY(LAYTYPE VARCHAR2(30));

--2011-08-22 ���� ��DATUM_INFO�����״̬��
ALTER TABLE DATUM_INFO ADD(STATE VARCHAR2(5) DEFAULT '0');

--2011-12-07 ���ǻ� ��repeatersection�����MIS�ź�2����ע
ALTER TABLE REPEATERSECTION ADD (MIS  VARCHAR2(12));
ALTER TABLE REPEATERSECTION ADD (remark2  VARCHAR2(240 BYTE));
ALTER TABLE REPEATERSECTION ADD (remark3  VARCHAR2(240 BYTE));
