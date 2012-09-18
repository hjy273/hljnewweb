--割接基本信息表
create table lp_cut  (
   ID                   INTEGER                         not null,
   WORKORDER_ID         VARCHAR2(32 BYTE),
   CUT_NAME             VARCHAR2(30 BYTE),
   CUT_LEVEL            VARCHAR2(10 BYTE),
   BUILDER              VARCHAR2(200 BYTE),
   CHARGE_MAN           VARCHAR2(32 BYTE),
   BEGINTIME            DATE,
   ENDTIME              DATE,
   BUDGET               FLOAT,
   ISCOMPENSATION       VARCHAR(1),
   COMPENSATION_COMPANY VARCHAR2(100 BYTE),
   BEFORE_LENGTH        FLOAT,
   CUT_CAUSE            VARCHAR2(200 BYTE),
   STATE                VARCHAR2(2 BYTE),
   CUT_PLACE            VARCHAR2(30 BYTE),
   UNAPPROVE_NUMBER     INTEGER,
   REPLY_BEGINTIME      DATE,
   REPLY_ENDTIME        DATE,
   constraint PK_LP_CUT primary key (ID)
);
--实施反馈信息表
create table lp_cut_feedback  (
   ID                   INTEGER                         not null,
   CUT_ID               INTEGER,
   ISINTERRUPT          VARCHAR(1),
   TD                   NUMBER,
   BS                   NUMBER,
   BEGINTIME            DATE,
   ENDTIME              DATE,
   CUTTIME              FLOAT,
   AFTER_LENGTH         FLOAT,
   FLAG                 VARCHAR(1),
   BEFORE_WASTAGE       FLOAT,
   AFTER_WASTAGE        FLOAT,
   FIBERCORENUMBER      NUMBER,
   LIVE_CHARGEMAN       VARCHAR2(20 BYTE),
   MOBILE_CHARGEMAN     VARCHAR2(20 BYTE),
   ISTIMEOUT            VARCHAR(1),
   TIMEOUT_CAUSE        VARCHAR2(300 BYTE),
   IMPLEMENTATION       VARCHAR2(300 BYTE),
   LEGACY_QUESTION      VARCHAR2(300 BYTE),
   UNAPPROVE_NUMBER     INTEGER,
   constraint PK_LP_CUT_FEEDBACK primary key (ID)
);
--割接审批表
create table cut_approve  (
   ID                   INTEGER,
   "Object_ID"          INTEGER,
   OBJECT_TYPE          VARCHAR2(30),
   AUDIT_STATE          CHAR(1),
   AUDIT_SUGGESTION     VARCHAR2(200 BYTE),
   APPROVER             VARCHAR2(30 BYTE),
   APPROVER_TIME        DATE
);
--割接验收表
create table lp_cut_acceptance  (
   ID                   INTEGER                         not null,
   CUT_ID               INTEGER,
   ACTUAL_VALUE         FLOAT,
   ISUPDATE             CHAR(1),
   UNAPPROVE_NUMBER     INTEGER,
   constraint PK_LP_CUT_ACCEPTANCE primary key (ID)
);
--割接与中继段关联表
create table lp_cut_subline  (
   ID                   INTEGER                         not null,
   CUT_ID               INTEGER,
   SUBLINE_ID           VARCHAR(20),
   constraint PK_LP_CUT_SUBLINE primary key (ID)
);

