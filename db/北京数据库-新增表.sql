---所有新增表的创建语句，按照日期顺序排列
-- 10-03-27 添加资料类型表和资料信息表--朱枫
create table DATUM_TYPE  (
   ID                   VARCHAR2(12 ),
   NAME                 VARCHAR2(300),
   constraint PK_DATUM_TYPE primary key (ID)
);
CREATE SEQUENCE SEQ_DATUM_TYPE_ID;
create table DATUM_INFO  (
   ID                   VARCHAR2(12 ),
   TYPEID               VARCHAR2(12),
   NAME                 VARCHAR2(300),
   INFO                 VARCHAR2(600),
   CONTRACTORID         VARCHAR2(50),
   REGIONID             VARCHAR2(50),
   constraint PK_DATUM_INFO primary key (ID)
);
CREATE SEQUENCE SEQ_DATUM_INFO_ID;

-- 2010-3-18：杨隽：添加流程历史保存公共信息表和序列
CREATE TABLE PROCESS_HISTORY_INFO(
	ID VARCHAR2(12),
	OBJECT_ID VARCHAR2(12),
	OBJECT_TYPE VARCHAR2(12),
	OPERATE_USER_ID VARCHAR2(20),
	NEXT_OPERATE_USER_ID VARCHAR2(500),
	HANDLED_TIME DATE,
	HANDLED_TASK_ID VARCHAR2(20),
	EXECUTION_ID VARCHAR2(50),
	CONSTRAINTS PK_PROCESS_HISTORY_INFO PRIMARY KEY  (ID)
);
CREATE SEQUENCE  SEQ_PROCESS_HISTORY_INFO_ID ; 

--创建演练任务表
create table lp_drilltask  (
   ID                   VARCHAR2(12 BYTE)               not null,
   NAME                 VARCHAR2(100 BYTE),
   Drill_LEVEL                INTEGER,
   BEGINTIME            DATE,
   ENDTIME              DATE,
   LOCALE               VARCHAR2(150 BYTE),
   DEMAND               VARCHAR2(300 BYTE),
   REMARK               VARCHAR2(300 BYTE),
   CREATOR              VARCHAR2(32 BYTE),
   CREATETIME           DATE,
   constraint PK_LP_DRILLTASK primary key (ID)
);
create sequence SEQ_LP_DRILLTASK_ID;

--创建演练方案表
create table lp_drillplan  (
   ID                   VARCHAR2(12 BYTE)               not null,
   TASK_ID              VARCHAR2(12 BYTE),
   contractor_id        VARCHAR2(10),
   PERSON_NUMBER        INTEGER,
   CAR_NUMBER           INTEGER,
   SCENARIO             VARCHAR2(300 BYTE),
   REMARK               VARCHAR2(300 BYTE),
   UNAPPROVE_NUMBER     INTEGER,
   CREATOR              VARCHAR2(32 BYTE),
   CREATETIME           DATE,
   constraint PK_LP_DRILLPLAN primary key (ID)
);
create sequence SEQ_LP_DRILLPLAN_ID;
--创建演练总结表
create table lp_drillsummary  (
   ID                   VARCHAR2(12 BYTE)               not null,
   PLAN_ID              VARCHAR2(12 BYTE),
   PERSON_NUMBER        INTEGER,
   CAR_NUMBER           INTEGER,
   SUMMARY              VARCHAR2(300 BYTE),
   UNAPPROVE_NUMBER     INTEGER,
   CREATOR              VARCHAR2(32 BYTE),
   CREATETIME           DATE,
   constraint PK_LP_DRILLSUMMARY primary key (ID)
);
create sequence SEQ_LP_DRILLSUMMARY_ID;

--演练任务与代维关系表
create table lp_drilltask_con  (
   id                   VARCHAR(12)                     not null,
   drill_id             VARCHAR(12),
   contractor_id        VARCHAR2(10),
   STATE                VARCHAR2(2 BYTE),
   jpbm_Flow_id         VARCHAR(20),
   constraint PK_LP_DRILLTASK_CON primary key (id)
);
create sequence SEQ_LP_DRILLTASK_CON_ID;

create table lp_drillplan_modify  (
   ID                   VARCHAR2(12)                    not null,
   plan_id              VARCHAR2(12),
   prev_starttime       Date,
   prev_endtime         DATE,
   next_starttime       DATE,
   next_endtime         DATE,
   modify_cause         VARCHAR2(1024),
   modify_man           VARCHAR2(50),
   modify_date          DATE,
   constraint PK_LP_DRILLPLAN_MODIFY primary key (ID)
);
CREATE SEQUENCE SEQ_LP_DRILLPLAN_MODIFY_ID;

---10-03-20 lsq 保障模块
--创建保障派单表
create table lp_safeguard_task  (
   ID                   VARCHAR(12)                     not null,
   safeguard_code       VARCHAR(15),
   safeguard_Name       VARCHAR2(100),
   start_date           DATE,
   end_date             DATE,
   safeguard_level              VARCHAR2(5),
   region               VARCHAR2(500),
   requirement          VARCHAR2(1024),
   remark               VARCHAR2(1024),
   send_date            DATE,
   sender               VARCHAR2(20),
   Region_id            VARCHAR2(6),
   constraint PK_LP_SAFEGUARD_TASK primary key (ID)
);
create sequence SEQ_LP_SAFEGUARD_TASK_ID;
--创建保障与代维关系表
create table lp_safeguard_con  (
   id                   VARCHAR(12)                     not null,
   safeguard_id         VARCHAR(12),
   contractor_id        VARCHAR2(10),
   jpbm_Flow_id         VARCHAR(20),
   transact_state       VARCHAR(2),
   constraint PK_LP_SAFEGUARD_CON primary key (id)
);
create sequence SEQ_LP_SAFEGUARD_CON_ID;
--创建保障计划表
create table lp_safeguard_scheme  (
   ID                   VARCHAR(12)                     not null,
   safeguard_id         VARCHAR(12)                     not null,
   contractor_id        VARCHAR2(10),
   plan_responder       NUMBER(3),
   special_plan_id      VARCHAR(12),
   plan_responding_unit NUMBER(3),
   remark               VARCHAR2(1024),
   auditing_num         NUMBER(2),
   maker                VARCHAR(20),
   make_date            DATE,
   constraint PK_LP_SAFEGUARD_SCHEME primary key (ID)
);
create sequence SEQ_LP_SAFEGUARD_SCHEME_ID;

--创建保障总结表
create table lp_safeguard_sum  (
   ID                   VARCHAR(12)                     not null,
   scheme_id            VARCHAR(12),
   fact_responder       NUMBER(3),
   fact_responding_unit NUMBER(3),
   sum                  VARCHAR2(2048),
   auditing_Num         NUMBER(2),
   sum_man_id           VARCHAR(20),
   sum_date             DATE,
   constraint PK_LP_SAFEGUARD_SUM primary key (ID)
);
create sequence SEQ_LP_SAFEGUARD_SUM_ID;
--保障方案涉及中继段
create table lp_safeguard_segment  (
   ID                   VARCHAR(12)                     not null,
   seqment_id           VARCHAR2(20),
   scheme_id            VARCHAR(12),
   constraint PK_LP_SAFEGUARD_SEGMENT primary key (ID)
);
create sequence SEQ_LP_SAFEGUARD_SEGMENT_ID;
create table lp_safeguard_PLAN  (
   ID                   VARCHAR2(12)                    not null,
   scheme_ID          VARCHAR2(12),
   PLAN_ID              VARCHAR2(12),
   constraint PK_LP_SAFEGUARD_PLAN primary key (ID)
);
create sequence SEQ_LP_SAFEGUARD_PLAN_ID;
create table LP_SPECIAL_ENDPLAN  (
   ID                   VARCHAR2(12)                    not null,
   PLAN_ID              VARCHAR2(12),
   END_TYPE             VARCHAR2(1),
   PREV_END_DATE        DATE,
   END_DATE             DATE,
   REASON               VARCHAR2(1024),
   CREATER              VARCHAR2(30),
   CREATE_TIME          DATE,
   constraint PK_LP_SPECIAL_ENDPLAN primary key (ID)
);
create sequence SEQ_LP_SPECIAL_ENDPLAN_ID;


--技术维护模块建表语句 20100327

--测试计划信息表
create table LP_TEST_PLAN  (
   id                   VARCHAR2(12)                    not null,
   contractor_id        VARCHAR2(12),
   creator_id           VARCHAR2(20),
   test_plan_name       VARCHAR2(1024),
   test_plan_type       VARCHAR2(5),
   test_begin_date      DATE,
   test_end_date        DATE,
   test_plan_remark     VARCHAR2(2048),
   create_time          DATE,
   approve_times        NUMBER,
   test_state           VARCHAR2(5),
   test_pid             VARCHAR2(30),
   regionid             VARCHAR2(6),
   constraint PK_LP_TEST_PLAN primary key (id)
);           
CREATE SEQUENCE SEQ_LP_TEST_PLAN_ID;
          
--测试录入数据信息表
create table LP_TEST_DATA  (
   id                   VARCHAR2(12)                    not null,
   test_plan_id         VARCHAR2(12),
   record_time          DATE,
   record_man_id        VARCHAR2(60),
   create_time          DATE,
   approve_times        NUMBER,
   state                VARCHAR(2),
   constraint PK_LP_TEST_DATA primary key (id)
);
CREATE SEQUENCE SEQ_LP_TEST_DATA_ID;

--测试计划中的备纤信息表
create table LP_TEST_PLAN_LINE  (
   id                   VARCHAR2(12)                    not null,
   test_plan_id         VARCHAR2(12),
   cableline_id         VARCHAR2(30),
   cableline_test_port  VARCHAR2(50),
   test_plan_date       DATE,
   test_man             VARCHAR2(60),
   test_remark          VARCHAR2(2048),
   constraint PK_LP_TEST_PLAN_LINE primary key (id)
);
CREATE SEQUENCE SEQ_LP_TEST_PLAN_LINE_ID;
          
--测试计划中的接地电阻信息表
create table LP_TEST_PLAN_STATION  (
   id                   VARCHAR2(12)                    not null,
   test_plan_id         VARCHAR2(12),
   test_station_id      VARCHAR2(30),
   test_plan_time       DATE,
   test_plan_man        VARCHAR2(60),
   test_remark          VARCHAR2(2048),
   create_time          DATE,
   constraint PK_LP_TEST_PLAN_STATION primary key (id)
);    
CREATE SEQUENCE SEQ_LP_TEST_PLAN_STATIONE_ID;
          

--备纤录入数据信息表
create table LP_TEST_CABLE_DATA  (
   id                   VARCHAR2(12)                    not null,
   test_data_id         VARCHAR2(12),
   test_plan_id         VARCHAR2(12),
   test_cableline_id    VARCHAR2(30),
   fact_test_port       VARCHAR2(50),
   fact_test_time       DATE,
   test_principal       VARCHAR2(50),
   test_man             VARCHAR2(100),
   test_address         VARCHAR2(512),
   test_apparatus       VARCHAR2(512),
   test_method          VARCHAR2(5),
   test_wavelength      VARCHAR2(30),
   test_refractive_index VARCHAR2(15),
   test_avg_time        VARCHAR2(5),
   test_state           VARCHAR2(5),
   create_time          DATE,
   constraint PK_LP_TEST_CABLE_DATA primary key (id)
);
CREATE SEQUENCE SEQ_LP_TEST_CABLE_DATA_ID;

--接地电阻录入数据信息表
create table LP_TEST_STATION_DATA  (
   id                   VARCHAR2(12)                    not null,
   test_data_id         VARCHAR2(12),
   test_plan_id         VARCHAR2(12),
   test_station_id      VARCHAR2(30),
   fact_test_time       DATE,
   test_address         VARCHAR2(512),
   test_weather         VARCHAR2(512),
   tester               VARCHAR2(100),
   test_apparatus       VARCHAR2(512),
   test_method          VARCHAR2(10),
   resistance_value     NUMBER(5,2),
   problem_comment      VARCHAR2(2048),
   dispose_method       VARCHAR2(2048),
   remark               VARCHAR2(2048),
   test_state           VARCHAR2(5),
   create_time          DATE,
   constraint PK_LP_TEST_STATION_DATA primary key (id)
);
CREATE SEQUENCE SEQ_LP_TEST_STATION_DATA_ID;
          
          
--问题中继段列表
create table LP_TEST_PROBLEM  (
   ID                   VARCHAR2(12),
   test_plan_id         VARCHAR2(12),
   test_cableline_id    VARCHAR2(30),
   problem_description  VARCHAR2(2048),
   problem_state        VARCHAR2(5),
   tester               VARCHAR2(100),
   process_comment      VARCHAR2(2048),
   solve_time           DATE,
   reason               VARCHAR2(1048),
   test_method          VARCHAR2(1048),
   test_time            DATE
);                  
CREATE SEQUENCE SEQ_LP_TEST_PROBLEM_ID;
          
          
--纤芯录入数据信息表
create table LP_TEST_CHIP_DATA  (
   id                   VARCHAR2(20)                    not null,
   test_cable_data_id   VARCHAR2(12),
   chip_seq             VARCHAR2(5),
   is_used              VARCHAR2(5),
   is_eligible          VARCHAR2(5),
   attenuation_constant VARCHAR2(30),
   test_remark          VARCHAR2(2048),
   create_time          DATE,
   is_save              VARCHAR2(5),
   constraint PK_LP_TEST_CHIP_DATA primary key (id)
);

CREATE SEQUENCE SEQ_LP_TEST_CHIP_DATA_ID;
          
          
--年计划
create table lp_test_year_plan  (
   id                   varchar2(12),
   plan_name            varchar2(256),
   contractor_id        varchar2(12),
   year                 VARCHAR2(10),
   creator_id           varchar2(20),
   create_time          DATE,
   state                varchar2(5)
);       
CREATE SEQUENCE SEQ_lp_test_year_plan_ID;
          
--年计划子任务
create table lp_test_year_plan_task  (
   id                   VARCHAR2(12),
   cable_level          VARCHAR2(12),
   PRE_VARY_TEST_NUM    INTEGER,
   APPLY_NUM            INTEGER,
   VARY_trunk_num       INTEGER,
   year_plan_id         VARCHAR2(12)
);         
CREATE SEQUENCE SEQ_lp_test_year_plan_task_ID;
          
--年计划子任务中继段
create table lp_test_year_plan_trunk_detail  (
   id                   VARCHAR2(12),
   trunkid              VARCHAR2(12),
   yeartask_id          VARCHAR2(12)
);
CREATE SEQUENCE SEQ_lp_test_year_plan_trunk_ID;
          
--纤芯数据分析表
create table LP_TEST_COREDATA  (
   ID                   VARCHAR2(12 BYTE)               not null,
   core_id              VARCHAR2(20),
   core_order           VARCHAR2(2),
   AB_END               VARCHAR2(2),
   BASE_STATION         VARCHAR2(200),
   REMARK               VARCHAR2(1024 CHAR),
   TEST_DATE            DATE,
   constraint PK_LP_TEST_COREDATA primary key (ID)
);
CREATE SEQUENCE SEQ_LP_TEST_COREDATA_ID;

--纤芯长度分析
create table LP_TEST_CORELENGTH  (
   ID                   VARCHAR2(12 CHAR)               not null,
   ANAYLSE_ID           VARCHAR2(12),
   refractive_index     NUMBER(8,5),
   pulse_width          NUMBER(8,2),
   core_length          NUMBER(8,2),
   is_problem           VARCHAR2(2 CHAR),
   problem_analyse      VARCHAR2(2048 CHAR),
   remark               VARCHAR2(1024 CHAR),
   constraint PK_LP_TEST_CORELENGTH primary key (ID)
);
CREATE SEQUENCE SEQ_LP_TEST_CORELENGTH_ID;
          
--衰减常数分析
create table LP_TEST_DECAYCONSTANT  (
   ANAYLSE_ID           VARCHAR2(12),
   DECAY_CONSTANT       NUMBER(8,4),
   is_standard          VARCHAR2(2),
   problem_analyse      VARCHAR2(1024),
   remark               VARCHAR2(1024),
   id                   VARCHAR2(12)
);
CREATE SEQUENCE SEQ_LP_TEST_DECAYCONSTANT_ID;
          
--成端损耗分析
create table LP_TEST_ENDWASTE  (
   ID                   VARCHAR2(12)                    not null,
   ANAYLSE_ID           VARCHAR2(12),
   end_waste            NUMBER(8,4),
   is_standard          VARCHAR2(2),
   problem_analyse      VARCHAR2(1024),
   remark               VARCHAR2(1024),
   constraint PK_LP_TEST_ENDWASTE primary key (ID)
);
CREATE SEQUENCE SEQ_LP_TEST_ENDWASTE_ID;
  
 --接头损耗分析         
create table LP_TEST_CONNECTORWASTE  (
   ID                   VARCHAR2(12)                    not null,
   ANAYLSE_ID           VARCHAR2(12),
   order_number         INTEGER,
   CONNECTOR_STATION    VARCHAR2(100),
   waste                NUMBER(8,4),
   problem_analyse      VARCHAR2(1024),
   remark               VARCHAR2(1024),
   constraint PK_LP_TEST_CONNECTORWASTE primary key (ID)
);
CREATE SEQUENCE SEQ_LP_TEST_CONNECTORWASTE_ID;
          
 --异常事件分析      
create table LP_TEST_EXCEPTIONEVENT  (
   ID                   VARCHAR2(12)                    not null,
   ANAYLSE_ID           VARCHAR2(12),
   order_number         INTEGER,
   event_station        VARCHAR2(1024),
   waste                NUMBER(8,4),
   problem_analyse      VARCHAR2(1024),
   remark               VARCHAR2(1024),
   constraint PK_LP_TEST_EXCEPTIONEVENT primary key (ID)
);        
CREATE SEQUENCE SEQ_LP_TEST_EXCEPTIONEVENT_ID;
          
   --其他分析       
create table LP_TEST_OTHERANALYSE  (
   ID                   VARCHAR2(12)                    not null,
   ANAYLSE_ID           VARCHAR2(12),
   analyse              VARCHAR2(1024),
   analyse_result       VARCHAR2(1024),
   remark               VARCHAR2(1024),
   constraint PK_LP_TEST_OTHERANALYSE primary key (ID)
);                
CREATE SEQUENCE SEQ_LP_TEST_OTHERANALYSE_ID;
          

-- 技术维护end


--验收交维
--验收交维申请信息
create table LP_ACCEPTANCE_APPLY  (
   ID                   VARCHAR2(12)                    not null,
   ASSIGN               VARCHAR2(4),
   CODE                 VARCHAR2(20),
   NAME                 VARCHAR2(50),
   APPLICANT            VARCHAR2(12),
   APPLY_DATE           DATE,
   RESOURCE_TYPE        VARCHAR2(2),
   PROCESS_INSTANCE_ID  VARCHAR2(100),
   PROCESS_STATE        VARCHAR2(4),
   constraint PK_LP_ACCEPTANCE_APPLY primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_APPLY_ID;

--验收交维分配任务
create table LP_ACCEPTANCE_TASK  (
   ID                   VARCHAR2(12)                    not null,
   APPLY_ID             VARCHAR2(12),
   CONTRACTOR_ID        VARCHAR2(12),
   TAKETASK_MAN         VARCHAR2(12),
   TAKETASK_TIME        DATE,
   ASSIGNER             VARCHAR2(12),
   ASSIGN_TIME          DATE,
   CREATER              VARCHAR2(12),
   CREATE_TIME          DATE,
   ISCOMPLETE           VARCHAR2(1),
   constraint PK_LP_ACCEPTANCE_TASK primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_TASK_ID;
--管道验收交维核准认领任务信息
create table LP_ACCEPTANCE_PTASK  (
   ID                   VARCHAR2(12)                    not null,
   TASK_ID              VARCHAR2(12),
   PIPE_ID              VARCHAR2(12),
   constraint PK_LP_ACCEPTANCE_PTASK primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_PTASK_ID;
--验收交维申请中管道信息
create table LP_ACCEPTANCE_PIPE  (
   ID                   VARCHAR2(12)                    not null,
   APPLY_ID             VARCHAR2(12),
   ASSETNO              VARCHAR2(50),
   PROJECT_NAME         VARCHAR2(500),
   PIPE_ADDRESS         VARCHAR2(500),
   PIPE_ROUTE           VARCHAR2(500),
   PIPE_LENGTH_0        VARCHAR2(12),
   PIPE_LENGTH_1        VARCHAR2(12),
   PIPE_PROPERTY        VARCHAR2(12),
   WORKING_DRAWING      VARCHAR2(12),
   MOVE_SCALE_0         VARCHAR2(12),
   MOVE_SCALE_1         VARCHAR2(12),
   BUILDER              VARCHAR2(500),
   BUILDER_PHONE        VARCHAR2(500),
   PCPM                 VARCHAR2(100),
   PLAN_ACCEPTANCE_TIME DATE,
   MAINTENANCE          VARCHAR(12),
   REMARK               VARCHAR2(2048),
   ISRECORD             VARCHAR2(1),
   constraint PK_LP_ACCEPTANCE_PIPE primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_PIPE_ID;
--光缆验收交维核准认领任务信息
create table LP_ACCEPTANCE_CTASK  (
   ID                   VARCHAR2(12)                    not null,
   TASK_ID              VARCHAR2(12),
   CABLE_ID             VARCHAR2(12),
   constraint PK_LP_ACCEPTANCE_CTASK primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_CTASK_ID;
--验收交维申请中光缆信息
create table LP_ACCEPTANCE_CABLE  (
   ID                   VARCHAR2(12)                    not null,
   APPLY_ID             VARCHAR2(12),
   SID                  VARCHAR2(50),
   CABLE_NO             VARCHAR2(50),
   A                    VARCHAR2(100),
   Z                    VARCHAR2(100),
   TRUNK                VARCHAR2(500),
   CABLE_LEVEL          VARCHAR(10),
   LAYING_METHOD        VARCHAR2(5),
   FIBERCORE_NO         VARCHAR2(100),
   CABLE_LENGTH         VARCHAR2(12),
   BUILDER              VARCHAR2(500),
   BUILDER_PHONE        VARCHAR2(500),
   PLAN_ACCEPTANCE_TIME DATE,
   PRCPM                VARCHAR2(100),
   REMARK               VARCHAR2(2048),
   ISRECORD             VARCHAR2(1),
   constraint PK_LP_ACCEPTANCE_CABLE primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_CABLE_ID;
--管道交维信息
create table LP_ACCEPTANCE_PAYPIPE  (
   ID                   VARCHAR(12)                     not null,
   PIPE_ID              VARCHAR2(12),
   TASK_ID              VARCHAR2(12),
   ACCEPTANCE_TIMES     NUMBER,
   ACCEPTANCE_DATE      DATE,
   PAY_TIME             DATE,
   BUILD_UNIT           VARCHAR2(200),
   BUILD_ACCEPTANCE     VARCHAR2(100),
   WORK_UNIT            VARCHAR2(200),
   WORK_ACCEPTANCE      VARCHAR2(100),
   SURVEILLANCE_UNIT    VARCHAR2(200),
   SURVEILLANCE_ACCEPT  VARCHAR2(100),
   MAINTENCE_UNIT       VARCHAR2(200),
   MAINTENCE_ACCEPTANCE VARCHAR2(100),
   PASSED               VARCHAR2(1),
   PASSED_TIME          DATE,
   constraint PK_LP_ACCEPTANCE_PAYPIPE primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_PAYPIPE_ID;

--管道验收结果
create table LP_ACCEPTANCE_PR  (
   ID                   VARCHAR2(12)               not null,
   PAYPIPE_ID           VARCHAR2(12),
   TIMES                NUMBER(2),
   RESULT               VARCHAR2(5),
   PLAN_DATE            DATE,
   FACT_DATE            DATE,
   REMARK               VARCHAR2(1024),
   DRAWING              VARCHAR2(50),
   IS_ELIGIBLE_0        VARCHAR2(2),
   ELIGIBLE_REASON_0    VARCHAR2(200),
   IS_ELIGIBLE_1        VARCHAR2(2),
   ELIGIBLE_REASON_1    VARCHAR2(200),
   IS_ELIGIBLE_2        VARCHAR2(2),
   ELIGIBLE_REASON_2    VARCHAR2(200),
   IS_ELIGIBLE_3        VARCHAR2(2),
   ELIGIBLE_REASON_3    VARCHAR2(200),
   constraint PK_LP_ACCEPTANCE_PR primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_PR_ID;
--光缆交维信息表
create table LP_ACCEPTANCE_PAYCABLE  (
   ID                   VARCHAR2(12)                    not null,
   CABLE_ID             VARCHAR2(12),
   TASK_ID              VARCHAR2(12),
   ACCEPTANCE_TIMES     NUMBER,
   ACCEPTANCE_DATE      DATE,
   PAY_TIME             DATE,
   PAY_TYPE             VARCHAR2(4),
   BUILD_UNIT           VARCHAR2(200),
   BUILD_ACCEPTANCE     VARCHAR2(100),
   WORK_UNIT            VARCHAR2(200),
   WORK_ACCEPTANCE      VARCHAR2(100),
   SURVEILLANCE_UNIT    VARCHAR2(200),
   SURVEILLANCE_ACCEPT  VARCHAR2(100),
   MAINTENCE_UNIT       VARCHAR2(200),
   MAINTENCE_ACCEPTANCE VARCHAR2(100),
   PASSED               VARCHAR2(4),
   PASSED_TIME          DATE,
   constraint PK_ACCEPTANCE_PAYCABLE primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_PAYCABLE_ID;

--中继段验收结果
create table LP_ACCEPTANCE_CR  (
   ID                   VARCHAR2(12)              not null,
   PAYCABLE_ID          VARCHAR2(12),
   TIMES                NUMBER(2),
   RESULT               VARCHAR2(5),
   PLAN_DATE            DATE,
   FACT_DATE            DATE,
   REMARK               VARCHAR2(1024),
   DRAWING              VARCHAR2(50),
   IS_ELIGIBLE_0        VARCHAR2(2),
   ELIGIBLE_REASON_0    VARCHAR2(200),
   IS_ELIGIBLE_1        VARCHAR2(2),
   ELIGIBLE_REASON_1    VARCHAR2(200),
   IS_ELIGIBLE_2        VARCHAR2(2),
   ELIGIBLE_REASON_2    VARCHAR2(200),
   IS_ELIGIBLE_3        VARCHAR2(2),
   ELIGIBLE_REASON_3    VARCHAR2(200),
   IS_ELIGIBLE_4        VARCHAR2(2),
   ELIGIBLE_REASON_4    VARCHAR2(200),
   IS_ELIGIBLE_5        VARCHAR2(2),
   ELIGIBLE_REASON_5    VARCHAR2(200),
   IS_ELIGIBLE_6        VARCHAR2(2),
   ELIGIBLE_REASON_6    VARCHAR2(200),
   constraint PK_LP_ACCEPTANCE_CR primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_CR_ID;

--验收交维核准信息
create table LP_ACCEPTANCE_APPROVE  (
   ID                   VARCHAR2(12)                    not null,
   APPLY_ID             VARCHAR2(12),
   APPROVER             VARCHAR2(12),
   APPROVE_TIME         DATE,
   STATE                VARCHAR2(3),
   constraint PK_LP_ACCEPTANCE_APPROVE primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_APPROVE_ID;
--验收交维分配代维单位信息
create table LP_ACCEPTANCE_CON  (
   ID                   VARCHAR2(12)                    not null,
   APPLY_ID             VARCHAR2(12),
   CONTRACTOR_ID        VARCHAR2(12),
   constraint PK_LP_ACCEPTANCE_CON primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_CON_ID;

--子流程表
CREATE TABLE LP_ACCEPTANCE_SUBFLOW
(
  ID                   VARCHAR2(12 BYTE),
  APPLYID              VARCHAR2(12 BYTE),
  CONTRACTORID         VARCHAR2(12 BYTE),
  PROCESS_INSTANCE_ID  VARCHAR2(100 BYTE),
  PROCESS_STATE        VARCHAR2(12 BYTE),
  constraint PK_LP_ACCEPTANCE_SUBFLOW primary key (ID)
);
CREATE SEQUENCE SEQ_LP_ACCEPTANCE_SUBFLOW_ID;

--光缆附加表
create table REPEATERSECTION_ADDONE  (
   ID                   VARCHAR2(12)                    NOT NULL,
   KID                  VARCHAR2(12),
   IS_TD                VARCHAR2(2),
   CABLELINE_ID         VARCHAR2(12),
   TR_LENGTH_1          VARCHAR2(12),
   TRUNKMENT_LENGTH_0   VARCHAR2(12),
   TRUNKMENT_LENGTH_1   VARCHAR2(12),
   TRUNKMENT_LENGTH_2   VARCHAR2(12),
   TRUNKMENT_LENGTH_3   VARCHAR2(12),
   PIPE_LENGTH_0        VARCHAR2(12),
   PIPE_LENGTH_1        VARCHAR2(12),
   PIPE_LENGTH_2        VARCHAR2(12),
   PIPE_LENGTH_3        VARCHAR2(12),
   PIPE_LENGTH_4        VARCHAR2(12),
   E1_LENGTH            VARCHAR2(12),
   E1_NUMBER            VARCHAR2(12),
   E2_LENGTH            VARCHAR2(12),
   E2_NUMBER            VARCHAR2(12),
   E3_LENGTH            VARCHAR2(12),
   E3_NUMBER            VARCHAR2(12),
   T1_LENGTH            VARCHAR2(12),
   T1_NUMBER            VARCHAR2(12),
   T2_LENGTH            VARCHAR2(12),
   T2_NUMBER            VARCHAR2(12),
   T3_LENGTH            VARCHAR2(12),
   T3_NUMBER            VARCHAR2(12),
   J_NUMBER             VARCHAR2(12),
   OTHER1               VARCHAR2(12),
   OTHER1_NUMBER        VARCHAR2(12),
   OTHER2               VARCHAR2(12),
   OTHER2_NUMBER        VARCHAR2(12),
   constraint PK_REPEATERSECTION_ADDONE primary key (ID)
);
CREATE SEQUENCE SEQ_REPEATERSECTION_ADDONE_ID;
--管道附加表
CREATE TABLE PIPE_ADDONE
(
  ID                    VARCHAR2(12 BYTE)       NOT NULL,
  PIP_ID                VARCHAR2(12 BYTE),
  DIRECT_SMALL_NUMBER   VARCHAR2(12 BYTE),
  DIRECT_MIDDLE_NUMBER  VARCHAR2(12 BYTE),
  DIRECT_LARGE_NUMBER   VARCHAR2(12 BYTE),
  THREE_SMALL_NUMBER    VARCHAR2(12 BYTE),
  THREE_MIDDLE_NUMBER   VARCHAR2(12 BYTE),
  THREE_LARGE_NUMBER    VARCHAR2(12 BYTE),
  FOUR_SMALL_NUMBER     VARCHAR2(12 BYTE),
  FOUR_MIDDLE_NUMBER    VARCHAR2(12 BYTE),
  FOUR_LARGEL_NUMBER    VARCHAR2(12 BYTE),
  FORNT_SMALL_NUMBER    VARCHAR2(12 BYTE),
  FORNT_MIDDLE_NUMBER   VARCHAR2(12 BYTE),
  FORNT_LARGE_NUMBER    VARCHAR2(12 BYTE),
  OTHER_SMALL_NUMBER    VARCHAR2(12 BYTE),
  OTHER_MIDDLE_NUMBER   VARCHAR2(12 BYTE),
  OTHER_LARGE_NUMBER    VARCHAR2(12 BYTE),
  SMALL_LENGTH_0        VARCHAR2(12 BYTE),
  SMALL_LENGTH_1        VARCHAR2(12 BYTE),
  MIDDLE_LENGTH_0       VARCHAR2(12 BYTE),
  MIDDLE_LENGTH_1       VARCHAR2(12 BYTE),
  LARGE_LENGTH_0        VARCHAR2(12 BYTE),
  LARGE_LENGTH_1        VARCHAR2(12 BYTE),
  OTHER_LENGTH_0        VARCHAR2(12 BYTE),
  OTHER_LENGTH_1        VARCHAR2(12 BYTE),
  STEEL_HOLE_NUMBER     VARCHAR2(12 BYTE),
  STEEL_LENGTH          VARCHAR2(12 BYTE),
  PLASTIC_HOLE_NUMBER   VARCHAR2(12 BYTE),
  PLASTIC_LENGTH        VARCHAR2(12 BYTE),
  CEMENT_HOLE_NUMBER    VARCHAR2(12 BYTE),
  CEMENT_LENGTH         VARCHAR2(12 BYTE),
  OTHER_HOLE_NUMBER     VARCHAR2(12 BYTE),
  OTHER_LENGTH          VARCHAR2(12 BYTE),
  constraint PK_PIPE_ADDONE primary key (ID)
);
CREATE SEQUENCE SEQ_PIPE_ADDONE_ID;

create table PIPELINE
(
  ID                   VARCHAR2(12) not null,
  WORK_NAME            VARCHAR2(500),
  PIPE_ADDRESS         VARCHAR2(500),
  PIPE_LINE            VARCHAR2(500),
  PIPE_LENGTH_CHANNEL  FLOAT default 0,
  PIPE_LENGTH_HOLE     FLOAT default 0,
  PIPE_TYPE            VARCHAR2(12),
  ROUTE_RES            VARCHAR2(12),
  PICTURE              VARCHAR2(12),
  MOBILE_SCARE_CHANNEL FLOAT default 0,
  MOBILE_SCARE_HOLE    FLOAT default 0,
  PRINCIPLE            VARCHAR2(100),
  FINISHTIME           VARCHAR2(50),
  SCETION              VARCHAR2(5),
  MAINTENANCE_ID       VARCHAR2(12),
  CHECK_REMARK         VARCHAR2(2048),
  ISSUENUMBER          VARCHAR2(10),
  ISCHECKOUT           VARCHAR2(2),
  constraint PK_PIPE primary key (ID)
)
CREATE SEQUENCE SEQ_PIPELINE_ID;


--2010年3月22日修改，增加管道和光缆修改历史表
CREATE TABLE REPEATERSECTION_HISTORY
(
  ID               VARCHAR2(12 BYTE)            NOT NULL,
  KID              VARCHAR2(12 BYTE)            NOT NULL,
  ASSETNO          VARCHAR2(40 BYTE),
  SEGMENTID        VARCHAR2(20 BYTE)            NOT NULL,
  SEGMENTNAME      VARCHAR2(240 BYTE),
  PLACE            VARCHAR2(500 BYTE),
  CABLE_LEVEL      VARCHAR2(10 BYTE),
  POINTA           VARCHAR2(240 BYTE),
  POINTAODF        VARCHAR2(240 BYTE),
  POINTAPORT       VARCHAR2(240 BYTE),
  POINTZ           VARCHAR2(240 BYTE),
  POINTZODF        VARCHAR2(240 BYTE),
  POINTZPORT       VARCHAR2(240 BYTE),
  FIBERTYPE        VARCHAR2(100 BYTE),
  CORENUMBER       NUMBER,
  PRODUCER         VARCHAR2(240 BYTE),
  CURRENT_STATE    VARCHAR2(4 BYTE),
  LAYTYPE          VARCHAR2(24 BYTE),
  GROSSLENGTH      FLOAT(126)                   DEFAULT 0,
  RESERVEDLENGTH   FLOAT(126)                   DEFAULT 0,
  OWNER            VARCHAR2(240 BYTE),
  BUILDER          VARCHAR2(240 BYTE),
  CABLETYPE        VARCHAR2(24 BYTE),
  CROSSINFO        VARCHAR2(480 BYTE),
  FINISHTIME       VARCHAR2(24 BYTE),
  ISCHECKOUT       VARCHAR2(2 BYTE),
  REFACTIVE_INDEX  VARCHAR2(5 BYTE),
  HAVE_PICTURE     VARCHAR2(4 BYTE),
  REMARK           VARCHAR2(240 BYTE),
  IS_MAINTENANCE   VARCHAR2(5 CHAR),
  MAINTENANCE_ID   VARCHAR2(12 BYTE),
  PROJECT_NAME     VARCHAR2(500 BYTE),
  SCETION          VARCHAR2(5 BYTE),
  REGION           VARCHAR2(50 BYTE),
  ISSUENUMBER      VARCHAR2(10 BYTE),
  constraint PK_REPEATERSECTION_HISTORY primary key (ID)
);
CREATE SEQUENCE SEQ_REPEATERSECTION_HISTORY_ID;
  
CREATE TABLE PIPELINE_HISTORY
(
  ID                    VARCHAR2(12 BYTE)       NOT NULL,
  PID                   VARCHAR2(12 BYTE)       NOT NULL,
  WORK_NAME             VARCHAR2(500 BYTE),
  PIPE_ADDRESS          VARCHAR2(500 BYTE),
  PIPE_LINE             VARCHAR2(500 BYTE),
  PIPE_LENGTH_CHANNEL   FLOAT(126)              DEFAULT 0,
  PIPE_LENGTH_HOLE      FLOAT(126)              DEFAULT 0,
  PIPE_TYPE             VARCHAR2(12 BYTE),
  ROUTE_RES             VARCHAR2(12 BYTE),
  PICTURE               VARCHAR2(12 BYTE),
  MOBILE_SCARE_CHANNEL  FLOAT(126)              DEFAULT 0,
  MOBILE_SCARE_HOLE     FLOAT(126)              DEFAULT 0,
  PRINCIPLE             VARCHAR2(100 BYTE),
  FINISHTIME            VARCHAR2(50 BYTE),
  SCETION               VARCHAR2(5 BYTE),
  CHECK_REMARK          VARCHAR2(2048 BYTE),
  MAINTENANCE_ID        VARCHAR2(12 BYTE),
  IS_MAINTENANCE        VARCHAR2(1 BYTE),
  ASSETNO               VARCHAR2(50 BYTE),
  ISSUENUMBER           VARCHAR2(10 BYTE),
  ISCHECKOUT            VARCHAR2(2 BYTE),
  constraint PK_PIPELINE_HISTORY primary key (ID)
);

CREATE SEQUENCE SEQ_PIPELINE_HISTORY_ID;

  
-- 2010-3-27：杨隽：新增保存短信发送定时任务记录表
create table SEND_SM_JOB_INFO  (
   ID                   VARCHAR2(12)                    not null,
   SIM_ID               VARCHAR2(1024),
   SEND_CONTENT         VARCHAR2(1024),
   SEND_TIME_TYPE       VARCHAR2(3),
   SEND_TYPE            VARCHAR2(2),
   FIRST_SEND_TIME      DATE,
   LAST_SEND_TIME       DATE,
   SEND_TIME_SPACE      NUMBER,
   SEND_STATE           VARCHAR2(3),
   SCHEDULAR_NAME       VARCHAR2(1024),
   SEND_OBJECT_ID       VARCHAR2(20),
   CREATE_USER_ID       VARCHAR2(20),
   CREATE_DATE          DATE DEFAULT SYSDATE,
   constraint PK_SEND_SM_JOB_INFO primary key (ID)
);
CREATE SEQUENCE  SEQ_SEND_SM_JOB_INFO_ID;

ALTER TABLE SEND_SM_JOB_INFO MODIFY (SCHEDULAR_NAME VARCHAR2(1024));


---3月30日更新 2010U002  新增变更写在下面

create table appconfig  (
   id                   varchar2(12)                    not null,
   label                varchar2(500),
   key                  varchar2(100),
   value                varchar2(500),
   remark               varchar2(1024),
   constraint pk_appconfig primary key (id)
);
CREATE SEQUENCE  SEQ_APPCONFIG_ID;
comment on column appconfig.label is '标签说明';
comment on column appconfig.key is '关键字';
comment on column appconfig.value is '值';
comment on column appconfig.remark is '说明';




/*==============================================================*/
/* 20100409 故障指标 表结构                                 */
/*==============================================================*/
create table LP_TROUBLE_NORM_GUIDE  (
   ID                   VARCHAR2(12)                    not null,
   GUIDE_type           VARCHAR2(2),
   GUIDE_name           VARCHAR2(100),
   interdiction_times_dare_value number(8,2),
   interdiction_times_norm_value NUMBER(8,2),
   interdiction_time_norm_value NUMBER(8,2),
   interdiction_time_dare_value NUMBER(8,2),
   RTR_TIME_NORM_VALUE  NUMBER(8,2),
   RTR_TIME_DARE_VALUE  NUMBER(8,2),
   SINGLE_RTR_TIME_NORM_VALUE NUMBER(8,2),
   constraint PK_LP_TROUBLE_NORM_GUIDE primary key (ID)
);

CREATE SEQUENCE SEQ_LP_TROUBLE_NORM_GUIDE_ID;

create table LP_TROUBLE_GUIDE_MONTH  (
   ID                   VARCHAR2(12)                    not null,
   guide_type           VARCHAR2(2),
   contractor_ID        VARCHAR2(12),
   stat_month           DATE,
   maintenance_length   NUMBER(8,2),
   interdiction_norm_times NUMBER(8,2),
   interdiction_dare_times NUMBER(8,2),
   trouble_times        INTEGER,
   revise_trouble_times INTEGER,
   interdiction_norm_time NUMBER(8,2),
   interdiction_dare_time NUMBER(8,2),
   interdiction_time    NUMBER(8,2),
   revise_interdiction_time NUMBER(8,2),
   RTR_in_time          NUMBER(8,2),
   feedback_in_time     NUMBER(8,2),
   constraint PK_LP_TROUBLE_GUIDE_MONTH primary key (ID)
);
CREATE SEQUENCE SEQ_LP_TROUBLE_GUIDE_MONTH_ID;

--问卷调查start
--问卷类型
create table QUEST_TYPE  (
   ID                   VARCHAR2(12)                    not null,
   TYPE                 VARCHAR2(50),
   REMARK               VARCHAR2(500),
   constraint PK_QUEST_TYPE primary key (ID)
);
create sequence SEQ_QUEST_TYPE_ID;
--指标类别
create table QUEST_GUIDELINE_CLASS  (
   ID                   VARCHAR2(12)                    not null,
   TYPE_ID              VARCHAR2(12),
   CLASS                VARCHAR2(50),
   REMARK               VARCHAR2(500),
   constraint PK_QUEST_GUIDELINE_CLASS primary key (ID)
);
create sequence SEQ_QUEST_GUIDELINE_CLASS_ID;
--指标分类
create table QUEST_GUIDELINE_SORT  (
   ID                   VARCHAR2(12)                    not null,
   CLASS_ID             VARCHAR2(12),
   SORT                 VARCHAR2(50),
   REMARK               VARCHAR2(500),
   constraint PK_QUEST_GUIDELINE_SORT primary key (ID)
);
create sequence SEQ_QUEST_GUIDELINE_SORT_ID;
--指标细项
create table QUEST_GUIDELINE_ITEM  (
   ID                   VARCHAR2(12)                    not null,
   SORT_ID              VARCHAR2(12),
   ITEM                 VARCHAR2(1000),
   weight_value       NUMBER(8,0),
   REMARK               VARCHAR2(1024),
   options            VARCHAR2(2),
   constraint PK_QUEST_GUIDELINE_ITEM primary key (ID)
);
create sequence SEQ_QUEST_GUIDELINE_ITEM_ID;
--评分项
create table QUEST_GRADE_RULE  (
   ID                   VARCHAR2(12)                    not null,
   ITEM_ID              VARCHAR2(12),
   GRADE_EXPLAIN        VARCHAR2(1000),
   mark               NUMBER(8,0),
   constraint PK_QUEST_GRADE_RULE primary key (ID)
);
create sequence SEQ_QUEST_GRADE_RULE_ID;
--参评对象
create table QUEST_REVIEWOBJECT  (
   ID                   VARCHAR2(12),
   Object             VARCHAR2(50)
);
create sequence SEQ_QUEST_REVIEWOBJECT_ID;
--问卷发布
create table QUEST_ISSUE  (
   ID                   VARCHAR2(12)                    not null,
   QUESTIONNAIRE_NAME   VARCHAR2(150),
   QUESTIONNAIRE_TYPE   VARCHAR2(12),
   REMARK               VARCHAR2(1200),
   STATE                VARCHAR2(2),
   constraint PK_QUEST_ISSUE primary key (ID)
);
create sequence SEQ_QUEST_ISSUE_ID;
--问卷参评部门
create table QUEST_CONTRACTOR  (
   ID                   VARCHAR2(12)                    not null,
   QUE_ID               VARCHAR2(12),
   CONTRACTOR_ID        VARCHAR2(12),
   STATE                VARCHAR2(2),
   constraint PK_QUEST_CONTRACTOR primary key (ID)
);
create sequence SEQ_QUEST_CONTRACTOR_ID;
--问卷参评对象
create table QUEST_ISSUE_REVIEWOBJ  (
   ID                   VARCHAR2(12)                    not null,
   QUEST_ID             VARCHAR2(12),
   REVIEWOBJECT_ID      VARCHAR2(12),
   constraint PK_QUEST_ISSUE_REVIEWOBJ primary key (ID)
);
create sequence SEQ_QUEST_ISSUE_REVIEWOBJ_ID;
--问卷评分项
create table QUEST_ISSUE_GRADEITEM  (
   ID                   VARCHAR2(12)                    not null,
   ITEM_ID              VARCHAR2(12),
   QUESTIONNAIRE_ID     VARCHAR2(12),
   constraint PK_QUEST_ISSUE_GRADEITEM primary key (ID)
);
create sequence SEQ_QUEST_ISSUE_GRADEITEM_ID;
--调查结果
create table QUEST_ISSUE_RESULT  (
   ID                   VARCHAR2(12)                    not null,
   SCORE                VARCHAR2(50),
   REVIEW_OBJECT_ID     VARCHAR2(12),
   ITEM_ID              VARCHAR2(12),
   USERID               VARCHAR2(12),
   constraint PK_QUEST_ISSUE_RESULT primary key (ID)
);
create sequence SEQ_QUEST_ISSUE_RESULT_ID;
--问卷调查end

-- 2010-4-15：朱枫：添加大修项目表
create table LP_OVERHAUL  (
   ID                   VARCHAR2(12)                    not null,
   PROJECT_NAME         VARCHAR2(1024),
   START_TIME           DATE,
   END_TIME             DATE,
   PROJECT_CREATOR      VARCHAR2(200),
   BUDGET_FEE           NUMBER,
   PROJECT_REMARK       VARCHAR2(1024),
   CREATOR              VARCHAR2(50),
   CREATE_TIME          DATE,
   PROCESSINSTANCEID    VARCHAR2(50),
   STATE                VARCHAR2(3),
   constraint PK_LP_OVERHAUL primary key (ID)
);

CREATE SEQUENCE SEQ_LP_OVERHAUL_ID;

create table LP_OVERHAUL_APPLY  (
   ID                   VARCHAR2(12)                    not null,
   TASK_ID              VARCHAR2(12),
   CONTRACTORID         VARCHAR2(100),
   APPLICANT            VARCHAR2(100),
   FEE                  NUMBER(12,2),
   CREATOR              VARCHAR2(100),
   CREATE_TIME          DATE,
   PROCESSINSTANCEID    VARCHAR2(50),
   STATE                VARCHAR2(3),
   constraint PK_LP_OVERHAUL_APPLY primary key (ID)
);

CREATE SEQUENCE SEQ_LP_OVERHAUL_APPLY_ID;
  
create table LP_OVERHAUL_CON  (
   ID                   VARCHAR2(12)                    not null,
   TASK_ID              VARCHAR2(12),
   CONTRACTOR_ID        VARCHAR2(30),
   constraint PK_LP_OVERHAUL_CON primary key (ID)
);

CREATE SEQUENCE SEQ_LP_OVERHAUL_CON_ID;
  
create table LP_OVERHAUL_CUT  (
   ID                   VARCHAR2(12)                    not null,
   APPLY_ID             VARCHAR2(12),
   CUT_ID               VARCHAR2(12),
   CUT_NAME             VARCHAR2(1024),
   CUT_REF_FEE          NUMBER(11,2),
   CUT_FEE              NUMBER(11,2),
   constraint PK_LP_OVERHAUL_CUT primary key (ID)
);

CREATE SEQUENCE SEQ_LP_OVERHAUL_CUT_ID;
  
create table LP_OVERHAUL_PROJECT  (
   ID                   VARCHAR2(12)                    not null,
   APPLY_ID             VARCHAR2(12),
   PROJECT_ID           VARCHAR2(12),
   PROJECT_NAME         VARCHAR2(1024),
   PROJECT_REF_FEE      NUMBER(11,2),
   PROJECT_FEE          NUMBER(11,2),
   constraint PK_LP_OVERHAUL_PROJECT primary key (ID)
);

CREATE SEQUENCE SEQ_LP_OVERHAUL_PROJECT_ID;



--费用模块 20100426 by fjj

create table LP_EXPENSE_GRADEFACTOR  (
   ID                   VARCHAR2(12)                    not null,
   FACTOR               NUMBER(5,2),
   expense_type         VARCHAR2(12),
   Contractor_id        VARCHAR2(12),
   EXPLAIN              VARCHAR2(256),
   GRADE1               INT,
   GRADE2               int,
   constraint PK_LP_EXPENSE_GRADEFACTOR primary key (ID)
);
create table LP_EXPENSE_UNITPRICE  (
   ID                   VARCHAR2(12)                    not null,
   explan               VARCHAR2(256),
   expense_type         VARCHAR2(12),
   contractor_id        VARCHAR2(12 ),
   cable_level          VARCHAR2(12 ),
   unit_price           NUMBER(8,3),
   constraint PK_LP_EXPENSE_UNITPRICE primary key (ID)
);
create table LP_EXPENSE_MONTH  (
   ID                   VARCHAR2(12)                    not null,
   CONTRACTOR_ID        VARCHAR2(12),
   YEARMONTH            DATE,
   expense_type         VARCHAR2(12),
   CABLE_NUM            INTEGER,
   CABLE_LENGTH         NUMBER(10,3),
   MONTH_PRICE          NUMBER(10,2),
   rectify_MONEY        NUMBER(10,2),
   deduction_MONEY      NUMBER(10,2),
   constraint PK_LP_EXPENSE_MONTH primary key (ID)
);
create table LP_EXPENSE_GRADEM  (
   ID                   VARCHAR2(12)                    not null,
   GRADE_FACTOR_ID      VARCHAR2(12),
   UNIT_PRICE_ID        VARCHAR2(12),
   unit_price           NUMBER(10,3),
   MAINTENANCE_LENGTH   NUMBER(10,3),
   MAINTENANCE_NUM      INTEGER,
   expense_ID           VARCHAR2(12),
   constraint PK_LP_EXPENSE_GRADEM primary key (ID)
);
create table LP_EXPENSE_CABLE  (
   ID                   VARCHAR2(12)                    not null,
   CABLE_ID             VARCHAR2(12),
   GRADEM_ID            VARCHAR2(12),
   constraint PK_LP_EXPENSE_CABLE primary key (ID)
);
create table LP_EXPENSE_AFFIRM  (
   ID                   VARCHAR2(12)                    not null,
   CONTRACTOR_ID        VARCHAR2(12),
   BUDGET_ID            VARCHAR2(12),
   START_MONTH          DATE,
   END_MONTH            DATE,
   deduction_price      NUMBER(10,2),
   balance_price        NUMBER(10,2),
   constraint PK_LP_EXPENSE_AFFIRM primary key (ID)
);
create table LP_EXPENSE_BUDGET  (
   ID                   VARCHAR2(12)                    not null,
   CONTRACTOR_ID        VARCHAR2(12),
   EXPENSE_TYPE         VARCHAR2(2),
   YEAR                 VARCHAR2(12),
   BUDGET_MONEY         NUMBER(10,2),
   PAY_MONEY            NUMBER(10,2),
   constraint PK_LP_EXPENSE_BUDGET primary key (ID)
);

CREATE SEQUENCE SEQ_LP_EXPENSE_AFFIRM_ID;     
CREATE SEQUENCE SEQ_LP_EXPENSE_BUDGET_ID; 
CREATE SEQUENCE SEQ_LP_LP_EXPENSE_CABLE_ID;     
CREATE SEQUENCE SEQ_LP_EXPENSE_GRADEFACTOR_ID;        
CREATE SEQUENCE SEQ_LP_EXPENSE_GRADEM_ID;      
CREATE SEQUENCE SEQ_LP_EXPENSE_MONTH_ID;       
CREATE SEQUENCE SEQ_LP_EXPENSE_UNITPRICE_ID;
  --费用end


--构建匹配临时缓冲区
create table temp_matched_points_spec(
  distance number,
  pointid varchar2(24),
  simid varchar2(11),
  terminalid varchar2(20),
  gpstime date,
  sublineid varchar2(16),
  gpscoordinate varchar2(17),
  planid varchar2(26)
);
create index ind_matched_points_spec_d on temp_matched_points_spec(distance);
create index ind_matched_points_spec_sl on temp_matched_points_spec(sublineid);
create index ind_matched_points_spec_p on temp_matched_points_spec(pointid);

create table temp_points_watch_spec(
   PLANID VARCHAR2(40),
   AREAID VARCHAR2(40),
   EXECUTETIME DATE,
   SIMID VARCHAR2(11),
   TERMINALID VARCHAR2(20),
   ISMATCHED VARCHAR2(2)
);
CREATE INDEX IND_TEMP_POINTS_WATCH_AREAID ON temp_points_watch_spec(AREAID);
CREATE INDEX IND_TEMP_POINTS_WATCH_TIME ON temp_points_watch_spec(EXECUTETIME);
CREATE INDEX IND_TEMP_POINTS_WATCH_SIMID ON temp_points_watch_spec(SIMID);
CREATE INDEX IND_TEMP_POINTS_WATCH_TID ON temp_points_watch_spec(TERMINALID);
CREATE INDEX IND_TEMP_POINTS_WATCH_MATCHED ON temp_points_watch_spec(ISMATCHED);

CREATE TABLE TEMP_MATCHED_POINTS_SPEC_DUMP(
  SEQID VARCHAR2(40),
  POINTID VARCHAR2(24),
  GPSTIME DATE,
  SUBLINEID VARCHAR2(16),
  PLANID VARCHAR2(26),
  SIMID VARCHAR2(16),
  CONSTRAINT PK_MATCHED_POINTS_SPEC_DUMP_ID PRIMARY KEY (SEQID)
);
CREATE INDEX IND_MATCHED_POINTS_SPEC_DUMP ON TEMP_MATCHED_POINTS_SPEC_DUMP(SUBLINEID);

--创建巡检和盯防的短信执行表
create table LP_SPEC_TEMP_WATCH  (
   KEYID                VARCHAR2(30)                    not null,
   PLANID               VARCHAR2(12),
   TERMINALID           VARCHAR2(20),
   SIMID                VARCHAR2(20),
   ARRTIME              DATE,
   GPSTIME              DATE,
   X                    NUMBER(10,6),
   Y                    NUMBER(9,6),
   STATE                NUMBER,
   GEOLOC               MDSYS.SDO_Geometry,
   constraint PK_LP_SPEC_TEMP_WATCH primary key (KEYID)
);

comment on table LP_SPEC_TEMP_WATCH is
'建议保留2-3个月的短信执行信息';

create table LP_SPEC_TEMP_PATROL  (
   KEYID                VARCHAR2(30)                    not null,
   PLANID               VARCHAR2(12),
   TERMINALID           VARCHAR2(20),
   SIMID                VARCHAR2(20),
   ARRTIME              DATE,
   GPSTIME              DATE,
   X                    NUMBER(10,6),
   Y                    NUMBER(9,6),
   SPEED                NUMBER(4,1),
   COURSE               NUMBER(4,1),
   PATROLTYPE           NUMBER,
   STATE                NUMBER,
   GEOLOC               MDSYS.SDO_Geometry,
   constraint PK_LP_SPEC_TEMP_PATROL primary key (KEYID)
);

comment on table LP_SPEC_TEMP_PATROL is
'建议保留2-3个月的短信执行信息';

comment on column LP_SPEC_TEMP_PATROL.PATROLTYPE is
'巡检类型';


--创建巡检和盯防的空间索引
INSERT INTO USER_SDO_GEOM_METADATA
        VALUES (
        'LP_SPEC_TEMP_PATROL',
        'geoloc',
        MDSYS.SDO_DIM_ARRAY(
        MDSYS.SDO_DIM_ELEMENT('X', -180, 180, 0.0011119487),
        MDSYS.SDO_DIM_ELEMENT('Y', -90, 90, 0.0011119487)
        ),
        '8307'
);
INSERT INTO USER_SDO_GEOM_METADATA
        VALUES (
        'LP_SPEC_TEMP_WATCH',
        'geoloc',
        MDSYS.SDO_DIM_ARRAY(
        MDSYS.SDO_DIM_ELEMENT('X', -180, 180, 0.0011119487),
        MDSYS.SDO_DIM_ELEMENT('Y', -90, 90, 0.0011119487)
        ),
        '8307'
);
CREATE INDEX IND_GEO_SPEC_PATROL
ON LP_SPEC_TEMP_PATROL(geoloc)
INDEXTYPE IS MDSYS.SPATIAL_INDEX;
CREATE INDEX IND_GEO_SPEC_WATCH
ON LP_SPEC_TEMP_WATCH(geoloc)
INDEXTYPE IS MDSYS.SPATIAL_INDEX;
commit;

--创建特巡计划的统计结果表
create table LP_SPEC_PATROL_DETAIL  (
   id                 VARCHAR2(20)                    not null,
   spec_plan_id       VARCHAR2(12),
   point_id           VARCHAR2(12),
   subline_id         VARCHAR2(12),
   sim_id             VARCHAR2(12),
   terminal_id        VARCHAR2(20),
   create_date        DATE DEFAULT sysdate,
   patrol_time        DATE,
   constraint PK_LP_SPEC_PATROL_DETAIL primary key (id)
);

create table LP_SPEC_LEAK_DETAIL  (
   id                 VARCHAR2(20)                    not null,
   spec_plan_id       VARCHAR2(12),
   point_id           VARCHAR2(12),
   subline_id         VARCHAR2(12),
   create_date        DATE DEFAULT sysdate,
   plan_patrol_times  NUMBER,
   fact_patrol_times  NUMBER,
   leak_patrol_times  NUMBER,
   constraint PK_LP_SPEC_LEAK_DETAIL primary key (id)
);

create table LP_SPEC_SUBLINE_STAT  (
   id                 VARCHAR2(20)                    not null,
   spec_plan_id       VARCHAR2(12),
   subline_id         VARCHAR2(12),
   subline_level      VARCHAR2(12),
   patrol_group_id    VARCHAR2(12),
   fact_date          DATE,
   create_date        DATE DEFAULT sysdate,
   plan_point_number  NUMBER,
   plan_point_times   NUMBER,
   fact_point_number  NUMBER,
   fact_point_times   NUMBER,
   patrol_ratio       NUMBER(5,2),
   plan_patrol_mileage NUMBER,
   fact_patrol_mileage NUMBER,
   constraint PK_LP_SPEC_SUBLINE_STAT primary key (id)
);

create table LP_SPEC_VALID_WATCH  (
   id                 VARCHAR2(20)                    not null,
   spec_plan_id       VARCHAR2(12),
   watch_area_id      VARCHAR2(12),
   sim_id             VARCHAR2(12),
   terminal_id        VARCHAR2(20),
   create_date        DATE DEFAULT sysdate,
   valid_watch_gps_time DATE,
   constraint PK_LP_SPEC_VALID_WATCH primary key (id)
);

create table LP_SPEC_INVALID_WATCH  (
   id                 VARCHAR2(20)                    not null,
   spec_plan_id       VARCHAR2(12),
   watch_area_id      VARCHAR2(12),
   terminal_id        VARCHAR2(20),
   sim_id             VARCHAR2(12),
   create_date        DATE DEFAULT sysdate,
   invalid_watch_gps_time DATE,
   invalid_watch_type VARCHAR2(3),
   constraint PK_LP_SPEC_INVALID_WATCH primary key (id)
);

create table LP_SPEC_AREA_STAT  (
   id                 VARCHAR2(20)                    not null,
   spec_plan_id       VARCHAR2(12),
   watch_area_id      VARCHAR2(12),
   patrol_group_id    VARCHAR2(12),
   fact_date          DATE,
   create_date        DATE DEFAULT sysdate,
   plan_watch_number  NUMBER,
   fact_watch_number  NUMBER,
   watch_ratio        NUMBER(5,2),
   constraint PK_LP_SPEC_AREA_STAT primary key (id)
);

create table LP_SPEC_PLAN_STAT  (
   id                 VARCHAR2(12)                    not null,
   spec_plan_id       VARCHAR2(12),
   spec_plan_name     VARCHAR2(1024),
   spec_plan_type     VARCHAR2(5),
   fact_date          DATE,
   create_date        DATE DEFAULT sysdate,
   plan_point_number  NUMBER,
   plan_point_times   NUMBER,
   plan_watch_number  NUMBER,
   fact_point_number  NUMBER,
   fact_point_times   NUMBER,
   fact_watch_number  NUMBER,
   patrol_ratio       NUMBER(5,2),
   watch_ratio        NUMBER(5,2),
   plan_patrol_mileage NUMBER,
   fact_patrol_mileage NUMBER,
   plan_subline_number NUMBER,
   no_patrol_subline_number NUMBER,
   no_complete_subline_number NUMBER,
   complete_subline_number NUMBER,
   plan_watch_area_number NUMBER,
   no_watch_area_number NUMBER,
   no_complete_area_number NUMBER,
   complete_area_number NUMBER,
   patrol_stat_state  VARCHAR2(2),
   constraint PK_LP_SPEC_PLAN_STAT primary key (id)
);

comment on column LP_SPEC_PLAN_STAT.patrol_stat_state is
'0表示为正在进行中的特巡计划统计，1表示为到期的特巡计划统计。';
-- 20100112 北京后台统计系统添加特巡计划统计 END

--add on 2011-03-22
--中标合同表
create table CONTRACT  (
   ID                VARCHAR2(10)     not null,
   CONTRACTOR_ID     VARCHAR2(10),
   YEAR              VARCHAR2(5),
   CONTRACT_NO     VARCHAR2(500),
   constraint PK_CONTRACT primary key (ID)
);
--创建中标合同表序列
create sequence SEQ_CONTRACT_ID;

--代维公司日常考核表
create table LP_APPRAISE_DAILY  (
   ID                VARCHAR2(10)     not null,
   TABLE_ID          VARCHAR2(200),
   CONTRACTOR_ID     VARCHAR2(10),
   CONTRACT_NO       VARCHAR2(30),
   BUSINESS_MODULE   VARCHAR2(40),
   BUSINESS_ID       VARCHAR2(20),
   APPRAISE_DATE     DATE DEFAULT SYSDATE,
   constraint PK_APPRAISE_DAILY primary key (ID)
);
--创建代维公司日常考核表序列
create sequence SEQ_APPRAISE_DAILY_ID;

--日常考核扣分情况
create table LP_APPRAISE_DAILY_MARK  (
   ID                VARCHAR2(10)     not null,
   DAILY_ID          VARCHAR2(200),
   RULE_ID           VARCHAR2(10),
   MARK_DEDUCTIONS   VARCHAR2(10),
   REMARK            VARCHAR2(200),
   constraint PK_APPRAISE_DAILY_MARK primary key (ID)
);
--创建日常考核扣分情况表序列
create sequence SEQ_APPRAISE_DAILY_MARK_ID;


--考核表建表语句 2011-3-22 zhangyh
CREATE TABLE lp_appraise_table
(
  id          VARCHAR2(10 BYTE),
  TABLE_NAME  VARCHAR2(200 CHAR),
  Type        VARCHAR2(1 CHAR),
  YEAR        VARCHAR2(5 CHAR),
  START_DATE  DATE,
  END_DATE    DATE,
  creater  VARCHAR2(20),
  create_date  DATE,
  constraint PK_lp_appraise_table primary key (ID)
);


CREATE TABLE lp_appraise_item
(
  id         VARCHAR2(10 BYTE),
  TABLE_ID   VARCHAR2(10 BYTE),
  ITEM_NAME  VARCHAR2(100 CHAR),
  WEIGHT     INTEGER,
  constraint PK_lp_appraise_item primary key (ID)
);

CREATE TABLE lp_appraise_content
(
  id                   VARCHAR2(10 BYTE),
  ITEM_ID              VARCHAR2(10 BYTE),
  CONTENT_DESCRIPTION  VARCHAR2(300 CHAR),
  WEIGHT               INTEGER,
  constraint PK_lp_appraise_content primary key (ID)
);

CREATE TABLE lp_appraise_rule
(
  id                    VARCHAR2(10 BYTE),
  CONTENT_ID            VARCHAR2(10 BYTE),
  RULE_DESCRIPTION   VARCHAR2(500 CHAR),
  WEIGHT                INTEGER,
  GRADE_DESCRIPTION  VARCHAR2(500 CHAR),
  constraint PK_lp_appraise_rule primary key (ID)
);

CREATE SEQUENCE SEQ_LP_APPRAISE_TABLE_ID;

CREATE SEQUENCE SEQ_LP_APPRAISE_ITEM_ID;

CREATE SEQUENCE SEQ_LP_APPRAISE_CONTENT_ID;

CREATE SEQUENCE SEQ_LP_APPRAISE_RULE_ID ;

--月考核建表语句 2011-04-20 zhangyh
CREATE TABLE LP_APPRAISE_RESULT
(
  id              VARCHAR2(10) not null,
  CONTRACTOR_ID   VARCHAR2(10),
  CONTRACT_NO     VARCHAR2(30),
  APPRAISE_MONTH  DATE,
  START_DATE      DATE,
  END_DATE        DATE,
  TABLE_ID        VARCHAR2(20),
  RESULT          FLOAT,
  APPRAISE_DATE   DATE,
  APPRAISER       VARCHAR2(50),
constraint PK_LP_APPRAISE_RESULT primary key (ID)
);

CREATE TABLE LP_APPRAISE_RULE_RESULT
(
  id         VARCHAR2(10) not null,
  RESULT_ID  VARCHAR2(10),
  RULE_ID    VARCHAR2(10),
  RESULT     FLOAT,
  REMARK     VARCHAR2(500),
constraint PK_LP_APPRAISE_RULE_RESULT primary key (ID)
);

CREATE SEQUENCE SEQ_LP_APPRAISE_RESULT_ID;

CREATE SEQUENCE SEQ_LP_APPRAISE_RULE_RESULT_ID;

commit;

--新增年故障指标数据表，为年统计专题图准备  20110428
CREATE TABLE LP_TROUBLE_GUIDE_YEAR
(
  id          VARCHAR2(10),
  guide_type  VARCHAR2(2),
  YEAR        VARCHAR2(5),
  maintenance_length NUMBER(8,2) DEFAULT 0,
  interdiction_norm_time NUMBER(8,2) DEFAULT 0,
  interdiction_dare_time NUMBER(8,2) DEFAULT 0,
  interdiction_time NUMBER(8,2) DEFAULT 0,
  interdiction_norm_times NUMBER(8,2) DEFAULT 0,
  interdiction_dare_times NUMBER(8,2) DEFAULT 0,
  trouble_times NUMBER(8,2) DEFAULT 0,
  RTR_TIME_NORM_VALUE NUMBER(8,2) DEFAULT 0,
  RTR_TIME_DARE_VALUE NUMBER(8,2) DEFAULT 0,
  RTR_TIME_FINISH_VALUE NUMBER(8,2) DEFAULT 0
);
CREATE SEQUENCE SEQ_LP_TROUBLE_GUIDE_YEAR_ID;

comment on column LP_TROUBLE_GUIDE_YEAR.maintenance_length     is '维护长度（公里数）';
comment on column LP_TROUBLE_GUIDE_YEAR.interdiction_norm_time is '千公里阻断时长指标基准值';
comment on column LP_TROUBLE_GUIDE_YEAR.interdiction_dare_time is '千公里阻断时长指标调整值';
comment on column LP_TROUBLE_GUIDE_YEAR.interdiction_time      is '千公里阻断时长指标完成值';
comment on column LP_TROUBLE_GUIDE_YEAR.interdiction_norm_times is '千公里阻断次数指标基准值';
comment on column LP_TROUBLE_GUIDE_YEAR.interdiction_dare_times is '千公里阻断次数指标调整值';
comment on column LP_TROUBLE_GUIDE_YEAR.trouble_times       is '千公里阻断次数指标完成值';
comment on column LP_TROUBLE_GUIDE_YEAR.RTR_TIME_NORM_VALUE is '光缆抢修平均时长指标基准值';
comment on column LP_TROUBLE_GUIDE_YEAR.RTR_TIME_DARE_VALUE is '光缆抢修平均时长指标挑战值';
comment on column LP_TROUBLE_GUIDE_YEAR.RTR_TIME_FINISH_VALUE is '光缆抢修平均时长指标完成值';

--验收交维验收资源分配日志记录
create table LP_ACCEPTANCE_allotlog
(
  id                  VARCHAR2(12) not null primary key,
  applyId             VARCHAR2(12),
  ResourceID          VARCHAR2(12),
  old_contractor_Id   VARCHAR2(12),
  new_contractor_Id   VARCHAR2(12),
  create_date       DATE,
  alloter           VARCHAR2(20)
);
create sequence SEQ_ACCEPTANCE_ALLOTLOG_ID;

--2011-07-05 张亚辉     考核评分

CREATE TABLE lp_Appraise_CONFIRM_RESULT
(
  id              VARCHAR2(12) PRIMARY KEY,
  result_id        VARCHAR2(12),
  contractor_id   VARCHAR2(10),
  confirm_result  VARCHAR2(2),
  result          VARCHAR2(2048),
  confirm_date    DATE,
  confirmer       VARCHAR2(100)
);

CREATE SEQUENCE SEQ_LP_APPRAISE_CON_RESULT_ID ;

CREATE TABLE lp_appraise_year_result
(
  id              VARCHAR2(12) PRIMARY KEY,
  contractor_id   VARCHAR2(10),
  year            VARCHAR2(4),
  contract_no     VARCHAR2(30),
  month           FLOAT,
  month_weight    FLOAT,
  month_result    FLOAT,
  special         FLOAT,
  special_weight  FLOAT,
  special_result  FLOAT,
  trouble         FLOAT,
  trouble_weight  FLOAT,
  trouble_result  FLOAT,
  yearend         FLOAT,
  yearend_weight  FLOAT,
  yearend_result  FLOAT,
  result          FLOAT,
  appraise_date   DATE,
  appraiser       VARCHAR2(50),
  confirm_result  VARCHAR2(2)
);
CREATE SEQUENCE SEQ_LP_APPRAISE_year_RESULT_ID;

create table RESOURCES
(
  code           INTEGER not null,
  resourcename   VARCHAR2(40),
  productenabled INTEGER default 1
);
comment on column RESOURCES.resourcename
  is '系统包括的业务模块，现在包括线路和基站资源';
comment on column RESOURCES.productenabled
  is '标志某项业务是否被激活 1：表示未激活 ；2：表示激活 ;3:公共模块资源';
alter table RESOURCES
  add constraint RESOURCES_PK primary key (CODE);
  
--2011-08-24 杨隽 添加LP_ACCEPTANCE_ALLOTLOG表和SEQ_ACCEPTANCE_ALLOTLOG_ID序列
create table LP_ACCEPTANCE_allotlog
(
  id                  VARCHAR2(12) not null primary key,
  applyId             VARCHAR2(12),
  ResourceID          VARCHAR2(12),
  old_contractor_Id   VARCHAR2(12),
  new_contractor_Id   VARCHAR2(12),
  create_date       DATE,
  alloter           VARCHAR2(20)
);
create sequence SEQ_ACCEPTANCE_ALLOTLOG_ID;
