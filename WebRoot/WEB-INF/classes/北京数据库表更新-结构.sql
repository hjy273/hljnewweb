----更新日期 
--日期  更新人  说明

--10-03-24 张会军 代维单位人员表
alter table CONTRACTORPERSON add issendsms VARCHAR2(2);
comment on column CONTRACTORPERSON.issendsms is '是否可以接收短信';
--10-03-24 张会军  巡检设备表
alter table TERMINALINFO add HOLDER VARCHAR2(100);
comment on column TERMINALINFO.holder  is '设备持有人';

-- 2010-3-18：杨隽：添加会议地点
ALTER TABLE NOTICE_CLOB ADD (MEET_ADDRESS VARCHAR2(500));

--故障模块 start 
   --20100312 fjj  在处理过程中增加 字段 到达机房xy坐标，找到故障点xy坐标，撤离现场xy坐标  
   alter table LP_TROUBLE_PROCESS add arrive_gps_x FLOAT;
   alter table LP_TROUBLE_PROCESS add arrive_gps_y FLOAT;
   alter table LP_TROUBLE_PROCESS add find_trouble_gps_x FLOAT;
   alter table LP_TROUBLE_PROCESS add find_trouble_gps_y FLOAT;
   alter table LP_TROUBLE_PROCESS add return_gps_x FLOAT;
   alter table LP_TROUBLE_PROCESS add return_gps_y FLOAT;
--故障模块 end 

-- 10-03-27 附件增加资源是否有效 
alter table ANNEX_ADD_ONE add is_usable VARCHAR2(2) default 1;
comment on column ANNEX_ADD_ONE.is_usable is '1: 表示资源有效，通过审核；0表示资源无效没有审核';
-- 2010-3-25：杨隽：添加流程历史信息中的处理动作说明
ALTER TABLE PROCESS_HISTORY_INFO ADD(PROCESS_ACTION VARCHAR2(1024));
-- 2010-3-27：杨隽：添加流程历史信息中的任务输出流
ALTER TABLE PROCESS_HISTORY_INFO ADD(TASK_OUT_COME VARCHAR2(1024));





--20100327 割接反馈提交时限
alter table lp_cut add (deadline Date);
alter table lp_cut_feedback add (creator varchar2(20), create_time date);
alter table lp_cut_acceptance add (creator varchar2(20), create_time date);

--100320 演练模块

alter table lp_drilltask add drill_type varchar2(5);


--20100221春节前验收问题修改
alter table lp_drilltask modify LOCALE varchar2(500);
alter table lp_drillplan add (BEGINTIME date,ENDTIME date,ADDRESS varchar2(500),equipment_NUMBER integer);
alter table lp_drillplan modify (SCENARIO varchar2(500),REMARK varchar2(500));
alter table lp_drillsummary add (equipment_NUMBER integer);
alter table lp_drilltask add (deadline date);
alter table lp_drillplan add (deadline date);

--2010-03-17用于临时保存
alter table lp_drilltask add (save_flag varchar2(1));



alter table lp_safeguard_scheme add (equipment_NUMBER integer);
alter table lp_safeguard_sum add (equipment_NUMBER integer,STARTTIME date,ENDTIME date);

--2010年2月10日问题12条


--add on 20100306
alter table lp_safeguard_task add(deadline date);
alter table lp_safeguard_scheme add(deadline date);


--10-03-20 fjj 技术维护
--20100114 在测试计划中的备纤信息表增加列标记数据是否录入
alter table LP_TEST_PLAN_LINE add state varchar2(2);
     
--20100114 在测试计划中的接地电阻信息表增加列标记数据是否录入
alter table LP_TEST_PLAN_STATION add state varchar2(2);
     
-- 20100116  接地电阻录入数据信息表 增加列是否合格is_eligible
alter table LP_TEST_STATION_DATA add is_eligible varchar2(2);
     
--   20100222 纤芯录入数据信息表  增加列是否保存is_save
alter table LP_TEST_CHIP_DATA add is_save varchar2(5);
       
-- 20100222 问题中继段列表  增加列原因分析reason、计划解决措施test_method、计划解决时间 test_time
alter table LP_TEST_PROBLEM add reason varchar2(1024);
alter table LP_TEST_PROBLEM add test_method varchar2(1024);
alter table LP_TEST_PROBLEM add test_time date;



--另： pipeline表中加了一个字段ASSETNO VARCHAR2(50)

--2010年3月10日修改，将计划验收日期提出来放到申请表中
--alter table lp_acceptance_cable add PLAN_ACCEPTANCE_TIME DATE;
--alter table lp_acceptance_pipe add PLAN_ACCEPTANCE_TIME DATE;
--alter table lp_acceptance_apply add PLAN_ACCEPTANCE_TIME DATE;

--2010年3月10日修改，增加管道和光缆表中的验收通过字段
alter table lp_acceptance_cable add ISPASSED VARCHAR2(1);
alter table lp_acceptance_pipe add ISPASSED VARCHAR2(1);

--2010年3月12日修改，增加管道和光缆表中的代维字段
alter table lp_acceptance_cable add CONTRACTORID varchar2(32);
alter table lp_acceptance_pipe add CONTRACTORID varchar2(32);

--2010年3月17日修改，增加管道和光缆表中的工程期数
alter table lp_acceptance_cable add ISSUENUMBER VARCHAR2(100);
alter table lp_acceptance_pipe add ISSUENUMBER VARCHAR2(100);
--alter table repeatersection add ISSUENUMBER VARCHAR2(10);
--alter table pipeline add ISSUENUMBER VARCHAR2(10);
--alter table pipeline and ASSETNO VARCHAR2(50);

alter table pipeline add ISCHECKOUT VARCHAR2(2);
--2010年3月23日修改，增加管道属性
alter table lp_acceptance_pipe add PIPE_TYPE VARCHAR2(12);


-- 两个字段多余（朱枫）
alter table repeatersection drop column issuenumber
alter table pipeline drop column issuenumber

--20100402用于保障任务临时保存
alter table lp_safeguard_task add save_flag varchar2(1) default '0';

alter table process_history_info modify object_type varchar(30);

-- 2010-4-1：杨隽：添加通知中的短信发送信息
ALTER TABLE NOTICE_CLOB ADD (SEND_METHOD VARCHAR2(3));
ALTER TABLE NOTICE_CLOB ADD (SM_BEGIN_DATE DATE);
ALTER TABLE NOTICE_CLOB ADD (SM_END_DATE DATE);
ALTER TABLE NOTICE_CLOB ADD (SEND_INTERVAL_TYPE VARCHAR2(3));
ALTER TABLE NOTICE_CLOB ADD (SEND_TIME_SPACE VARCHAR2(5));
-- 2010-4-1：杨隽：添加通知中的前一次会议信息
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_ADDRESS VARCHAR2(500));
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_PERSON VARCHAR2(2048));
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_TIME DATE);
ALTER TABLE NOTICE_CLOB ADD (OLD_MEET_END_TIME DATE);
ALTER TABLE NOTICE_CLOB ADD (IS_CANCELED VARCHAR2(3) DEFAULT '0');


alter table USERINFO modify PHONE VARCHAR2(1100);

-- 2010-4-9：杨隽：变更定时发送短信的发送对象编号字段长度
ALTER TABLE SEND_SM_JOB_INFO MODIFY (SEND_OBJECT_ID VARCHAR2(1024));
-- 2010-4-12：杨隽：添加定时发送短信的发送对象信息说明
ALTER TABLE SEND_SM_JOB_INFO ADD (SEND_OBJECT_NAME VARCHAR2(1024));
-- 2010-4-14：杨隽：添加定时发送短信的周期规则信息说明
ALTER TABLE SEND_SM_JOB_INFO ADD (SEND_CYCLE_RULE VARCHAR2(512));
-- 2010-4-17：杨隽：添加定时发送短信的周期触发条件信息
ALTER TABLE SEND_SM_JOB_INFO ADD (CRON_EXPRESSION_STRING VARCHAR2(512));


--2010-04-17 zhj  修改字典表增加区域字段
alter table DICTIONARY_FORMITEM add RegionID VARCHAR2(10);
comment on column DICTIONARY_FORMITEM.RegionID  is '区域';
update dictionary_formitem set regionid='110000';
commit;
-- 2010-04-17 修改字典表id 类型
alter table DICTIONARY_FORMITEM modify ID varchar2(12);
create sequence SEQ_DICTIONARY_FORMITEM_ID;

-- Create/Recreate primary, unique and foreign key constraints 
alter table DICTIONARY_FORMITEM  add constraint pk_dictonary_fromitem primary key (ID);

  
-- 2010-4-17：杨隽：添加定时发送短信的周期触发条件信息
ALTER TABLE SEND_SM_JOB_INFO ADD (CRON_EXPRESSION_STRING VARCHAR2(512));

--工程管理增加字段
alter table lp_remedy add mtotalfee float;

--更新问卷调查--调查结果
alter table quest_issue_result modify score varchar2(500);
--4月21日 zhj 特巡计划巡回任务中增加巡回时段
alter table LP_SPECIAL_CIRCUIT add Start_Time CHAR(5);
alter table LP_SPECIAL_CIRCUIT add end_time CHAR(5);
comment on column LP_SPECIAL_CIRCUIT.START_TIME  is '巡回时段--开始时间';
comment on column LP_SPECIAL_CIRCUIT.END_TIME   is '巡回时段--结束时间';

--备份路由描述
alter table repeatersection add route_desc varchar2(500);
update repeatersection set route_desc = place;
commit;
update repeatersection t set t.place='1' where t.place like '%房山%';
update repeatersection t set t.place='2' where t.place like '%门头沟%';
update repeatersection t set t.place='3' where t.place like '%延庆%';
update repeatersection t set t.place='4' where t.place like '%昌平%';
update repeatersection t set t.place='5' where t.place like '%怀柔%';
update repeatersection t set t.place='6' where t.place like '%密云%';
update repeatersection t set t.place='7' where t.place like '%顺义%';
update repeatersection t set t.place='8' where t.place like '%通州%';
update repeatersection t set t.place='9' where t.place like '%大兴%';
update repeatersection t set t.place='10' where t.place like '%平谷%';
commit;


-- 技术维护年计划增加每年默认测试次数  20100426 by fjj
 alter table lp_test_year_plan add test_times varchar2(5);
 
 -- 问卷发布添加发布人和发布时间
 alter table quest_issue add (creator varchar2(20),create_date date);
 
 --20100429 fjj 费用增加扣减原因
  alter table LP_EXPENSE_MONTH add remark varchar2(1024);
  alter table LP_EXPENSE_AFFIRM add remark varchar2(1024);
 
-- 2010-04-29：杨隽：添加故障指标的完成状态和城域网抢修超时次数列
ALTER TABLE LP_TROUBLE_GUIDE_MONTH ADD (FINISH_STATE VARCHAR2(5) DEFAULT '1');
ALTER TABLE LP_TROUBLE_GUIDE_MONTH ADD (CITY_AREA_OUT_STANDARD_NUMBER NUMBER DEFAULT 0);
-- 2010-05-06 张会军  中继段表中增加光缆废除状态字段
alter table REPEATERSECTION add scrapstate VARCHAR2(10) default 'false';
comment on column REPEATERSECTION.scrapstate  is '光缆作废状态';

-- 20100112 北京后台统计系统添加特巡计划统计 START
ALTER TABLE TEMP_PATROL ADD (  PLANID VARCHAR2(40));


alter table lp_acceptance_apply drop column PLAN_ACCEPTANCE_TIME;
alter table lp_acceptance_cable add PLAN_ACCEPTANCE_TIME date;
alter table lp_acceptance_pipe add PLAN_ACCEPTANCE_TIME date;

--保障模块增加是否为全网保障 
alter table lp_safeguard_scheme add is_all VARCHAR2(2);
comment on column lp_safeguard_scheme.is_all is '是否全网保障，1是0否';
 
 
-- 用户资料添加在职状态和离职时间属性 2010-5-6 张亚辉
alter table contractorperson add(leave_time date,conditions varchar2(4));
alter table CONTRACTORPERSON modify conditions default 0;--在职状态 0表示在职 1：表示离职
update CONTRACTORPERSON set conditions=0;
commit;
ALTER TABLE ANNEX_ADD_ONE ADD (STATE  VARCHAR2(2 BYTE)   DEFAULT 0);

--在ANNEX_ADD_ONE表中添加使用状态属性state 2010-6-12 张亚辉
COMMENT ON COLUMN ANNEX_ADD_ONE.STATE IS '默认值为0；表示正常，1表示被删除；该字段表示附件是否被删除。';

-- 2010-6-22 杨隽 添加割接和故障的取消人和取消时间信息
ALTER TABLE LP_TROUBLE_INFO ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE
);

ALTER TABLE LP_CUT ADD(
CANCEL_USER_ID VARCHAR2(20),
CANCEL_TIME DATE
);

ALTER TABLE LP_CUT MODIFY (STATE VARCHAR2(5));

-- 2010-6-28 杨隽 添加故障反馈的“是否报案”，“报案说明”，“是否报险”信息列
ALTER TABLE LP_TROUBLE_REPLY ADD(
IS_REPORT_CASE VARCHAR2(5) DEFAULT '',
REPORT_CASE_COMMENT VARCHAR2(1024) DEFAULT '',
IS_REPORT_DANGER VARCHAR2(5) DEFAULT ''
);

-- 2010-6-29 杨隽 添加反馈时限数据列
ALTER TABLE LP_TROUBLE_INFO ADD(
REPLY_DEADLINE VARCHAR2(20) DEFAULT ''
);

-- 2010-7-12 杨隽 修改反馈时限数据列的数据类型为DATE类型
-- 执行该语句前请执行下面的语句
-- UPDATE LP_TROUBLE_INFO SET REPLY_DEADLINE=NULL ;
-- COMMIT;
ALTER TABLE LP_TROUBLE_INFO MODIFY (REPLY_DEADLINE DATE);

-- 2010-7-29 杨隽 添加取消原因列
ALTER TABLE LP_CUT ADD (CANCEL_REASON VARCHAR2(1024));
ALTER TABLE LP_SENDTASK ADD (CANCEL_REASON VARCHAR2(1024));
ALTER TABLE LP_TROUBLE_INFO ADD (CANCEL_REASON VARCHAR2(1024));

-- Add/modify columns 
alter table LP_ACCEPTANCE_CABLE add PLAN_ACCEPTANCE_TIME2 date;

-- 2010-8-5 杨隽 添加取消信息
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

--modify 报案信息内容长度
alter table LP_TROUBLE_REPLY modify report_case_comment VARCHAR2(2048);

--管道验收申请增加备注2，3
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
'任务路由ID';

comment on column lp_special_taskpoint.point_id is
'巡检点ID';

*/
--pointinfo 表增加备注
alter table POINTINFO add remark VARCHAR2(500);

--日常考核添加考核人
alter table lp_appraise_daily add APPRAISER varchar2(20);

--11-04-13薛元宏 增加  维护故障距离修正值
alter table LP_TROUBLE_GUIDE_MONTH add revise_maintenance_length NUMBER(8,2)DEFAULT 0 ;
comment on column LP_TROUBLE_GUIDE_MONTH.revise_maintenance_length is '维护故障距离修正值';
--增加任务指派说明。
alter table LP_ACCEPTANCE_APPLY add remark VARCHAR2(1024);
--11-04-18薛元宏  增加数值型的缺省值 0
alter table LP_TROUBLE_GUIDE_MONTH modify MAINTENANCE_LENGTH default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_NORM_TIMES default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_DARE_TIMES default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_NORM_TIME default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_DARE_TIME default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify INTERDICTION_TIME default 0;
alter table LP_TROUBLE_GUIDE_MONTH modify REVISE_INTERDICTION_TIME default 0;

--派单反馈考核增加是否日常考核过标记位
alter table lp_sendtask_check add exam_flag varchar2(2) default '0';
 ALTER TABLE LP_APPRAISE_RESULT ADD (Confirm_result  VARCHAR2(2));
ALTER TABLE LP_APPRAISE_RESULT RENAME COLUMN APPRAISE_MONTH TO APPRAISE_time;
-- 2011-05-05 张亚辉 更改验收交维光缆表中铺设方式字段的长度
ALTER TABLE LP_ACCEPTANCE_CABLE MODIFY(LAYING_METHOD VARCHAR2(20 BYTE));

alter table mainmodulemenu add businesstype VARCHAR2(2);
--2011-07-28 张亚辉
 ALTER TABLE LP_APPRAISE_RESULT ADD (Confirm_result  VARCHAR2(2));
--2011-08-02 张亚辉 更改linetype的数据长度
ALTER TABLE SUBLINEINFO MODIFY(LINETYPE VARCHAR2(4 BYTE)); 
 
 
--2011-08-11 杨隽 修改SUBLINECABLESEGMENT的CABLESEGMENTID列数据长度
ALTER TABLE SUBLINECABLESEGMENT MODIFY(CABLESEGMENTID VARCHAR2(12));

--2011-08-22 杨隽 修改POINTINFO的ISFOCUS的默认值
ALTER TABLE POINTINFO MODIFY(ISFOCUS VARCHAR2(2) DEFAULT '0');

--2011-08-22 杨隽 修改REPEATERSECTION的LAYTYPE字段长度
ALTER TABLE REPEATERSECTION MODIFY(LAYTYPE VARCHAR2(30));

--2011-08-22 杨隽 在DATUM_INFO中添加状态列
ALTER TABLE DATUM_INFO ADD(STATE VARCHAR2(5) DEFAULT '0');

--2011-12-07 张亚辉 在repeatersection中添加MIS号和2个备注
ALTER TABLE REPEATERSECTION ADD (MIS  VARCHAR2(12));
ALTER TABLE REPEATERSECTION ADD (remark2  VARCHAR2(240 BYTE));
ALTER TABLE REPEATERSECTION ADD (remark3  VARCHAR2(240 BYTE));
