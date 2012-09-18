/*==============================================================*/
/* Table: lp_special_plan                                       */
/*==============================================================*/
create table lp_special_plan  (
   ID                   VARCHAR(12)                     not null,
   plan_name            VARCHAR2(50),
   plan_Type            VARCHAR2(10),
   start_date           DATE,
   end_date             DATE,
   constraint PK_LP_SPECIAL_PLAN primary key (ID)
);

comment on table lp_special_plan is
'��Ѳ�ƻ���Ҫ���ڱ��ϣ������ȣ�Ҳ���Ե�������';

comment on column lp_special_plan.plan_Type is
'���������ϣ�����';

create table lp_special_circuit  (
   ID                   VARCHAR(12)                     not null,
   plan_id              VARCHAR(12),
   task_name            VARCHAR2(30),
   line_level           VARCHAR2(10),
   patrol_Num           NUMBER(2),
   constraint PK_LP_SPECIAL_CIRCUIT primary key (ID)
);

comment on table lp_special_circuit is
'������·�Ĳ�ͬ����Ϊ����Թ��½���ָ��������Ѳ��';

create table lp_special_taskroute  (
   ID                   VARCHAR(12)                     not null,
   task_ID              VARCHAR(12),
   subline_id           VARCHAR2(20),
   patrol_group_id      VARCHAR2(15),
   constraint PK_LP_SPECIAL_TASKROUTE primary key (ID)
);

comment on table lp_special_taskroute is
'��ͬѲ�������Ѳ�������漰��·�ɶΣ��߶Σ�';


create table lp_special_watch  (
   ID                   VARCHAR(12)                     not null,
   plan_id              VARCHAR(12),
   start_time           CHAR(5),
   end_time             CHAR(5),
   space                NUMBER(3),
   area_Name            VARCHAR2(200),
   patrol_group_id      VARCHAR2(15),
   area_id              VARCHAR(20),
   constraint PK_LP_SPECIAL_WATCH primary key (ID)
);

comment on table lp_special_watch is
'���������ǶԹ̶��ص�̶�ʱ����ڵ������Ѳ��';

comment on column lp_special_watch.area_id is
'���Զ����������й���';

create sequence SEQ_LP_SPECIAL_CIRCUIT_ID;
create sequence SEQ_LP_SPECIAL_PLAN_ID;
create sequence SEQ_LP_SPECIAL_TASKROUTE_ID;
create sequence SEQ_LP_SPECIAL_WATCH_ID;