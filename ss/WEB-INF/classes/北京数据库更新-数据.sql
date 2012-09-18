----更新日期 
--日期： 更新人 ：说明
-- 2010-3-19：杨隽：添加派单已办任务菜单信息
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('11004','已办工作','11','1021');

INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1100401','已办工作','11004','/WebApp/dispatchtask/dispatch_task.do?method=queryForFinishHandledDispatchTask','11,12,21,22');
UPDATE SUBMENU SET SHOWNO='103' WHERE ID='11004';
UPDATE SUBMENU SET SHOWNO='104' WHERE ID='11003';
COMMIT;

-- 2010-3-23：杨隽：添加材料已办任务菜单信息
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('832','已办工作','8','108');

INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('83201','已办工作','832','/WebApp/material_apply.do?method=queryFinishHandledMaterialApplyList','11,12,21,22');

-- 2010-3-23：杨隽：调整材料菜单排序
UPDATE SUBMENU SET SHOWNO='120' WHERE ID='810';
UPDATE SUBMENU SET SHOWNO='121' WHERE ID='819';
UPDATE SUBMENU SET SHOWNO='122' WHERE ID='818';
UPDATE SUBMENU SET SHOWNO='123' WHERE ID='817';
COMMIT;
-- 2010-3-24：杨隽：屏蔽材料年统计和月统计三级菜单
UPDATE SONMENU SET TYPE='9' WHERE ID='81802';
UPDATE SONMENU SET TYPE='9' WHERE ID='81803';
-- 2010-3-25：杨隽：调整任务派单类型的排序
update dictionary_formitem set sort=code where assortment_id='dispatch_task' or assortment_id='dispatch_task_con';
COMMIT;
--故障模块 start  
--20100317  fjj 故障单临时保存，增加查看临时保存的列表
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210202','派单列表','2102','/WebApp/troubleInfoAction.do?method=getTempSaveTrouble','11,12,21,22');
--故障派单暂时不需要临时保存，隐藏此菜单 20100322
update   sonmenu set type='9' where id='210202';
--20100321  fjj  增加数据字典，故障反馈单时填写的影响业务类型
insert into dictionary_assortment values('impress_type','impress_type','影响业务类型');
insert into  dictionary_formitem values('150','1','基站','impress_type','1','');
insert into  dictionary_formitem values('151','2','系统','impress_type','2','');
insert into  dictionary_formitem values('152','3','集团客户','impress_type','3','');
insert into  dictionary_formitem values('153','4','无','impress_type','4','');
COMMIT;
--20100325  fjj  增加菜单已办工作
insert into submenu(id,lablename,parentid,showno) values('2105','已办工作','21','2');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210501','已办工作','2105','/WebApp/troubleReplyAction.do?method=getFinishHandledWork','11,12,21,22');
COMMIT;


---3月30日更新 2010U002  新增变更写在下面

--20100401 zhufeng 增加已办工作
insert into submenu(id,lablename,parentid,showno) values('2204','已办工作','22','3');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('220401','已办工作','2204','/WebApp/hiddangerAction.do?method=getFinishedWork','11,12,21,22');
COMMIT;

insert into submenu(id,lablename,parentid,showno) values('2704','已办工作','27','3');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('270401','已办工作','2704','/WebApp/acceptanceAction.do?method=getFinishedWork','11,12,21,22');
COMMIT;

--故障模块 end 

--技术维护

--20100401 fjj 技术维护增加菜单已办工作
insert into submenu(id,lablename,parentid,showno) values('2507','已办工作','25','2')
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('250701','已办工作','2507','/WebApp/testPlanAction.do?method=getFinishHandledWork','11,12,21,22')
----20100401 fjj 技术维护增加菜单已处理问题光缆
update sonmenu set lablename='待处理问题光缆' where id='250501'
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('250502','已处理问题光缆','2505','/WebApp/problemCableAction.do?method=getFinishedProblemCables','11,12,21,22')
COMMIT;


--资料维护
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('72106','审核中继段','721','/WebApp/resAction.do?method=approveList','11,12,21,22');

--割接已办工作20100401
insert into submenu values('2305','已办工作','','23','','','3','');
insert into sonmenu values('230501','已办工作','2305','/WebApp/cutAction.do?method=queryFinishHandledCutApplyList','','','11,12,21,22');
--演练已办工作
insert into submenu values('2405','已办工作','','24','','','3','');
insert into sonmenu values('240501','已办工作','2405','/WebApp/drillTaskAction.do?method=queryFinishHandledDrillList','','','11,12,21,22');
--保障已办工作
insert into submenu values('2604','已办工作','','26','','','3','');
insert into sonmenu values('260401','已办工作','2604','/WebApp/safeguardTaskAction.do?method=queryFinishHandledSafeguardList','','','11,12,21,22');

--保障临时任务
insert into sonmenu values('260202','临时任务','2602','/WebApp/safeguardTaskAction.do?method=getPerfectList','','','11,12,21,22');

-- 2010-4-2 杨隽： 创建“定时任务管理”的二级和三级菜单
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('751','定时任务管理','7','21');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('75101','查询定时任务','751','/WebApp/schedule.do?method=queryForm','11,12,21,22');
commit;

-- 2010-4-6 杨隽：创建“材料出入库管理”的三级菜单
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('81809','材料出入库查询','818','/WebApp/material_stock_record.do?method=queryMaterialStockRecord','11,12,21,22');
COMMIT;

insert into appconfig values('1','审核人，抄送人设置','approverGroupId','00025', '设置审核人抄送人过滤，只有特定用户组的人能作为审核人抄送人');
insert into appconfig values('2','巡检人员在线时长设置','online_period','10', '通过设置该项能够调整制定时间内的巡检人员');

commit;

INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('752','系统设置','7','12');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('75201','参数设置','752','/WebApp/appconfig.do?method=loadAppConfig','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('75202','字典设置','752','/WebApp/dictionary.do?method=queryDictionary','11,12,21,22');
commit;


--20100406 fjj 增加故障指标菜单 
  
insert into submenu(id,lablename,parentid,showno) values('2106','故障指标','21','2');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210601','故障基准指标','2106','/WebApp/troubleQuotaAction.do?method=troubleQuotaForm','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210602','统计月故障指标','2106','/WebApp/troubleQuotaAction.do?method=createTroubleQuotaForm','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210603','统计年故障指标','2106','/WebApp/troubleQuotaAction.do?method=yearTroubleQuotaForm','11,12,21,22');
commit;

--维护资料
insert into submenu values('1021','维护资料','','10','','','33','');
insert into sonmenu values('102101','添加维护资料','1021','/WebApp/linepatrol/resource/datumAdd.jsp','','','');
insert into sonmenu values('102102','维护维护资料','1021','/WebApp/datumAction.do?method=list','','','');
commit;

--问卷调查
insert into mainmodulemenu values('29','问卷调查','../images/iconmenu/xjsjcjsz.gif','','','20');
insert into submenu values('2901','问卷发布','','29','','','1','');
insert into sonmenu values('290101','问卷发布','2901','/WebApp/questAction.do?method=addQuestForm','','','11,12,21,22');
insert into sonmenu values('290102','完善问卷','2901','/WebApp/questAction.do?method=perfectIssueList','','','11,12,21,22');
insert into submenu values('2902','调查问卷','','29','','','2','');
insert into sonmenu values('290201','调查问卷','2902','/WebApp/questAction.do?method=getIssueFeedbackList','','','11,12,21,22');

-- 2010-04-17：杨隽：添加巡检分析功能菜单
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('12001','单个代维公司','12','0');
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('12002','多个代维公司','12','1');
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('12003','全网呈现','12','2');

UPDATE SUBMENU SET TYPE='9' WHERE ID='1202';

INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200101','月统计','12001','/WebApp/PlanMonthlyStatAction.do?method=queryConMonthlyStat','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200102','年统计','12001','/WebApp/contractor_year_stat.do?method=queryYearStatForm','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200201','月统计','12002','/WebApp/MonthlyStatCityMobileAction.do?method=queryMonthlyStat','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200202','年统计','12002','/WebApp/city_mobile_year_stat.do?method=queryYearStatForm','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200301','月统计','12003','/WebApp/whole_net_stat.do?method=queryMonthStatForm','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200302','年统计','12003','/WebApp/whole_net_stat.do?method=queryYearStatForm','11,12,21,22');
COMMIT;

-- 工程管理
insert into mainmodulemenu values('28','工程管理','','','','','');
insert into submenu values('2801','工程申请','','28','','','1','');
insert into submenu values('2802','待办工作','','28','','','2','');
insert into submenu values('2803','已办工作','','28','','','3','');
insert into submenu values('2804','查询统计','','28','','','4','');
insert into sonmenu values('280101','工程申请','2801','/WebApp/project/remedy_apply.do?method=insertApplyForm','','','');
insert into sonmenu values('280202','待办工作','2802','/WebApp/project/remedy_apply.do?method=toDoWork','','','');
insert into sonmenu values('280301','已办工作','2803','/WebApp/project/remedy_apply.do?method=getFinishedWork','','','');
insert into sonmenu values('280401','查询统计','2804','/WebApp/project/remedy_apply.do?method=queryApplyForm','','','');
commit;

-- 2010-4-19：杨隽：调整巡检分析的菜单
UPDATE SUBMENU SET TYPE='9' WHERE ID='1203';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1204';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1205';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1206';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1207';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1208';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1209';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1210';
UPDATE SUBMENU SET TYPE='9' WHERE ID='1211';

UPDATE SUBMENU SET PARENTID='12' WHERE ID='214';
UPDATE SUBMENU SET PARENTID='12' WHERE ID='219';

UPDATE SUBMENU SET SHOWNO=1 WHERE ID='219';
UPDATE SUBMENU SET SHOWNO=2 WHERE ID='214';
UPDATE SUBMENU SET SHOWNO=3 WHERE ID='12001';
UPDATE SUBMENU SET SHOWNO=4 WHERE ID='12002';
UPDATE SUBMENU SET SHOWNO=5 WHERE ID='12003';

COMMIT;

-- 2010-04-20 问卷调查：问卷汇总菜单
insert into submenu values('2903','问卷汇总','','29','','','3','');
insert into sonmenu values('290301','问卷汇总','2903','/WebApp/questAction.do?method=questFeedbackStatList','','','11,12,21,22');

-- 大修项目
insert into mainmodulemenu values('31','大修项目','','','','','');
insert into submenu values('3131','大修任务','','31','','','1','');
insert into submenu values('3132','待办工作','','31','','','2','');
insert into submenu values('3133','已办工作','','31','','','3','');
insert into submenu values('3134','查询统计','','31','','','4','');
insert into sonmenu values('313101','大修任务','3131','/WebApp/overHaulAction.do?method=addTaskForm','','','');
insert into sonmenu values('313201','待办工作','3132','/WebApp/overHaulAction.do?method=toDoWork','','','');
insert into sonmenu values('313301','已办工作','3133','/WebApp/overHaulAction.do?method=finishedWork','','','');
insert into sonmenu values('313401','查询统计','3134','/WebApp/overHaulAction.do?method=query','','','');
commit;

-- 问卷调查：问卷项管理菜单
insert into submenu values('2904','问卷项管理','','29','','','4','');
insert into sonmenu values('290401','参评对象管理','2904','/WebApp/questAction.do?method=questComManagerList','','','11,12,21,22');
insert into sonmenu values('290402','类型管理','2904','/WebApp/questAction.do?method=questTypeManagerList','','','11,12,21,22');
insert into sonmenu values('290403','类别管理','2904','/WebApp/questAction.do?method=questClassManagerList','','','11,12,21,22');
insert into sonmenu values('290404','分类管理','2904','/WebApp/questAction.do?method=questSortManagerList','','','11,12,21,22');
insert into sonmenu values('290405','细则管理','2904','/WebApp/questAction.do?method=questItemManagerList','','','11,12,21,22');
commit;



-- 费用模块  20100426 by fjj
insert into mainmodulemenu values('30','费用核实','../images/iconmenu/xlqggl.gif','','','20','1');

insert into submenu(id,lablename,parentid,showno) values('3001','取费系数','30','1');
insert into submenu(id,lablename,parentid,showno) values('3002','维护单价','30','1');
insert into submenu(id,lablename,parentid,showno) values('3003','维护费用','30','1');
insert into submenu(id,lablename,parentid,showno) values('3004','预算管理','30','1');
insert into submenu(id,lablename,parentid,showno) values('3005','费用确认','30','1');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300101','取费系数','3001','/WebApp/expensesFactorAction.do?method=cableGradeFactor','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300102','系数列表','3001','/WebApp/expensesFactorAction.do?method=cableGradeFactorList','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300201','维护单价','3002','/WebApp/expensesPriceAction.do?method=cableUnitPrice','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300202','单价列表','3002','/WebApp/expensesPriceAction.do?method=cableUnitPriceList','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300301','费用生成','3003','/WebApp/expensesAction.do?method=createExpenseForm','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300501','费用确认','3005','/WebApp/expensesAction.do?method=queryExpenseForm','11,12,21,22')
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300502','费用确认列表','3005','/WebApp/expensesAction.do?method=affirmExepenseList','11,12,21,22')

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300401','增加预算','3004','/WebApp/budgeExpensesAction.do?method=addBudgetForm','11,12,21,22')
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300402','预算列表','3004','/WebApp/budgeExpensesAction.do?method=getBudgetExpense','11,12,21,22')
commit;

-- 问卷调查
update sonmenu t set t.lablename='指标项管理' where t.id='290405';
commit;

--2010-04-29：杨隽：添加故障指标的查询功能菜单
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('210604','指标查询','2106','/WebApp/troubleQuotaAction.do?method=queryTroubleQuotaMonthForm','11,12,21,22');
--INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('210605','年指标查询','2106','/WebApp/troubleQuotaAction.do?method=queryTroubleQuotaYearForm','11,12,21,22');
COMMIT;
-- 2010-04-30 资料管理：维护资料-->资料分类  张亚辉
INSERT INTO SONMENU ( ID, LABLENAME, PARENTID, HREFURL, REMARK, TYPE ) VALUES ('102103', '添加资料分类', '1021', '/WebApp/linepatrol/resource/datumTypeAdd.jsp', NULL, NULL);

--问卷调查
insert into submenu values('2905','问卷查询','','29','','','5','');
insert into sonmenu values('290501','问卷查询','2905','/WebApp/questAction.do?method=queryIssueByConditionForm','','','11,12,21,22');

--技术维护增加菜单查询光缆数据录入信息 20100514 fjj   
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('250403','查询光缆','2504','/WebApp/testPlanQueryStatAction.do?method=queryCableForm','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('270302','中继段查询','2703','/WebApp/linepatrol/acceptance/queryCable.jsp','');


-- 2010-06-10 系统管理  在系统管理中添加附件管理   张亚辉
INSERT INTO SUBMENU ( ID, LABLENAME, HREFURL, PARENTID, REMARK, REGIONID, SHOWNO,TYPE ) VALUES ('771', '附件管理', NULL, '7', NULL, NULL, 71, NULL); 
INSERT INTO SONMENU ( ID, LABLENAME, PARENTID, HREFURL, REMARK, TYPE ) VALUES ('77103', '附件管理', '771', '/WebApp/AnnexAction.do?method=queryAnnexForm', NULL, NULL);
commit;
-- 2010-6-29 杨隽 添加反馈时限（一般故障 2天+6小时）到数据字典信息表
INSERT INTO dictionary_formitem (id,code,lable,assortment_id,sort,parentid,regionid) VALUES(seq_dictionary_formitem_id.nextval,'0','54','trouble_deadline','1','','110000');
-- 2010-6-29 杨隽 添加反馈时限（重大故障 1天+1小时）到数据字典信息表
INSERT INTO dictionary_formitem (id,code,lable,assortment_id,sort,parentid,regionid) VALUES(seq_dictionary_formitem_id.nextval,'1','25','trouble_deadline','1','','110000');
COMMIT;
insert into dictionary_formitem values(seq_dictionary_formitem_id.nextval,'5','其他联建','property_right',2,'','110000');
insert into dictionary_formitem values(seq_dictionary_formitem_id.nextval,'6','牵头合建','property_right',5,'','110000');
insert into dictionary_formitem values(seq_dictionary_formitem_id.nextval,'7','其他合建','property_right',6,'','110000');
commit;

INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('2705','验收资源调配','27','6');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('270501','资源调配','2705','/WebApp/resourceBlendAction.do?method=searchForm','12');
commit;

-- 2010-12-28 张亚辉 删除变更方案和变更列表
delete from sonmenu where lablename='变更方案' and id='240102';
delete from sonmenu where lablename='变更列表' and id='240103';
commit;


-- 2011-03-10 增加光缆、管道重新分配，管道统计功能
--添加光缆、管道重新分配菜单
insert into sonmenu values('72503','光缆分配','725','/WebApp/resAction.do?method=cableAssignOne','','','11,12,21,22');
insert into sonmenu values('72504','管道分配','725','/WebApp/pipeAction.do?method=pipeAssignOne','','','11,12,21,22');
commit;

--添加管道统计菜单
insert into sonmenu values('270303','管道统计','2703','/WebApp/acceptancePipesAction.do?method=forwardPipeStatPage','','','11,12,21,22');
update sonmenu set hrefurl='/WebApp/acceptancePipeStatAction.do?method=forwardPipeStatPage' where id='270303';
commit;

--添加验收交维计划查询菜单
insert into sonmenu values('270103','交维计划查询','2701','/WebApp/acceptancePlanQueryAction.do?method=forwardPlanQueryPage','','','11,12,21,22');
commit;

--添加月考核  2011-03-25 张亚辉
insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('32','月考核','','','','90','1');

insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type)  values('3201','考核表管理','','32','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320102','导入月考核表','3201','/WebApp/linepatrol/appraise/importMonthExcel.jsp','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320101','考核表查询','3201','/WebApp/appraiseTableMonthAction.do?method=appraiseTableList','','','11,12,21,22');
commit;
--月考核 2011--4-20 张亚辉
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3202','月考核评分','','32','','','2','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320201','月考核表生成','3202','/WebApp/appraiseMonthAction.do?method=createMonthTableForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320202','月考核表查询','3202','/WebApp/appraiseMonthAction.do?method=queryAppraiseMonthListForm','','','11,12,21,22');

--insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320203','月考核表统计','3202','/WebApp/appraiseMonthAction.do?method=queryAppraiseMonthStatForm','','','11,12,21,22');
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3203','其他日常考核','','32','','','3','');	
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320301','其他日常考核','3203','/WebApp/appraiseDailyOtherAction.do?method=appraiseDailyOtherForm','','','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320302','查询其他日常考核','3203','/WebApp/appraiseDailyOtherAction.do?method=queryAppraiseDailyOtherListForm','','','11,12,21,22');
commit;

--增加派单考核菜单
SET DEFINE OFF;
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('11005','派单考核','','11','','','10','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('1100501','未考核','11005','/WebApp/dispatchExamAction.do?method=examList&examFlag=unexam','','','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('1100502','已考核','11005','/WebApp/dispatchExamAction.do?method=examList&examFlag=examed','','','11,12,21,22');
commit;

--增加大修项目考核菜单
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3135','大修考核','','31','','','35','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('313501','待考核','3135','/WebApp/overHaulExamAction.do?method=examList','','','11,12,21,22');
commit;

--增加工程管理考核菜单
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('2805','工程考核','','28','','','28','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('280501','待考核','2805','/WebApp/remedyExamAction.do?method=examList','','','11,12,21,22');
commit;

--月考核添加月考核统计菜单
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3203','查询统计','','32','','','2','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320301','月考核统计','3203','/WebApp/appraiseMonthStatAction.do?method=appraiseMonthStat&init=init','','','11,12,21,22');
commit;

--修改点击菜单不查询数据
update sonmenu set hrefurl='/WebApp/dispatchExamAction.do?method=examList&examFlag=unexam&init=init' where id='1100501';
update sonmenu set hrefurl='/WebApp/dispatchExamAction.do?method=examList&examFlag=examed&init=init' where id='1100502';
update sonmenu set hrefurl='/WebApp/overHaulExamAction.do?method=examList&init=init' where id='313501';
update sonmenu set hrefurl='/WebApp/remedyExamAction.do?method=examList&init=init' where id='280501';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('210605','自定义年指标','2106','/WebApp/customTroubleQuotaActon.do?method=querycustomYearTroubleQuota','','','11,12,21,22');
commit;

--专项考核 2011-05-30 张亚辉
insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('33','专项考核','','','','91','1');

insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3301','专项考核表管理','','33','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330101','专项考核表查询','3301','/WebApp/appraiseTableSpecialAction.do?method=appraiseTableList','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330102','专项考核表导入','3301','/WebApp/linepatrol/appraise/importSpecialExcel.jsp','','','11,12,21,22');

commit;

update mainmodulemenu set lablename='月度考核' where id='32';
commit;
--年终检查
insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('34','年终检查','','','','92','1');
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3401','年终检查表管理','','34','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340101','年终检查表查询','3401','/WebApp/appraiseTableYearEndAction.do?method=appraiseTableList','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340102','年终检查表导入','3401','/WebApp/linepatrol/appraise/importYearEndExcel.jsp','','','11,12,21,22');

commit;
--专项考核评分
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3302','专项考核评分','','33','','','2','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330201','专项考核表评分','3302','/WebApp/appraiseSpecialAction.do?method=createSpecialTableForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330202','专项考核表查询','3302','/WebApp/appraiseSpecialAction.do?method=queryAppraiseSpecialListForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330203','专项考核表统计','3302','/WebApp/appraiseSpecialAction.do?method=queryAppraiseSpecialStateForm','','','11,12,21,22');

commit;
--年终检查打分
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3402','年终检查打分','','34','','','2','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340201','年终检查打分','3402','/WebApp/appraiseDailyYearEndAction.do?method=appraiseDailyYearEndForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340202','年终检查打分查询','3402','/WebApp/appraiseDailyYearEndAction.do?method=queryAppraiseDailyYearEndListForm','','','11,12,21,22');
--年终检查汇总
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3403','年终检查汇总','','34','','','3','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340301','年终检查汇总','3403','/WebApp/appraiseYearEndAction.do?method=createAppraiseYearEndTableForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340302','年终检查汇总查询','3403','/WebApp/appraiseYearEndAction.do?method=queryAppraiseYearEndListForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340303','年终检查汇总统计','3403','/WebApp/appraiseYearEndAction.do?method=queryAppraiseYearEndStatForm','','','11,12,21,22');

--月考核确认
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3204','代维确认','','32','','','4','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320401','下发考核结果','3204','/WebApp/appraiseMonthAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320402','考核结果确认','3204','/WebApp/appraiseMonthAction.do?method=verifyAppraiseList&type=1','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320403','代维确认结果','3204','/WebApp/appraiseMonthAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320404','确认结果查询','3204','/WebApp/appraiseMonthAction.do?method=queryVerifyResultForm','','','11,12,21,22');
commit;

--专项考核确认
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3303','代维确认','','33','','','3','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330301','下发考核结果','3303','/WebApp/appraiseSpecialAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330302','考核结果确认','3303','/WebApp/appraiseSpecialAction.do?method=verifyAppraiseList&type=2','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330303','代维确认结果','3303','/WebApp/appraiseSpecialAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330304','确认结果查询','3303','/WebApp/appraiseSpecialAction.do?method=queryVerifyResultForm','','','11,12,21,22');

commit;
--年终检查确认
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3404','代维确认','','34','','','4','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340401','下发考核结果','3404','/WebApp/appraiseYearEndAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340402','考核结果确认','3404','/WebApp/appraiseYearEndAction.do?method=verifyAppraiseList&type=4','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340403','代维确认结果','3404','/WebApp/appraiseYearEndAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340404','确认结果查询','3404','/WebApp/appraiseYearEndAction.do?method=queryVerifyResultForm','','','11,12,21,22');

commit;

--年度考核确认
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3502','代维确认','','35','','','3','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350201','下发考核结果','3502','/WebApp/appraiseYearAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350202','考核结果确认','3502','/WebApp/appraiseYearAction.do?method=verifyAppraiseList&type=3','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350203','代维确认结果','3502','/WebApp/appraiseYearAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350204','确认结果查询','3502','/WebApp/appraiseYearAction.do?method=queryVerifyResultForm','','','11,12,21,22');


commit;

--年度考核

insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('35','年度考核','/images/icon/menu/ndkh.png','','','93','1');
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3501','年度考核','','35','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350101','年度考核生成','3501','/WebApp/appraiseYearAction.do?method=createAppraiseYearForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350102','年度考核查询','3501','/WebApp/appraiseYearAction.do?method=queryAppraiseYearForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350103','年度考核统计','3501','/WebApp/appraiseYearAction.do?method=queryAppraiseYearStatForm','','','11,12,21,22');

-- 2011-07-08 张亚辉

update sonmenu set hrefurl='/WebApp/resAction.do?method=approveCableList' where id='72106';

-- 2011-07-11 张亚辉
update mainmodulemenu set imgurl='/images/icon/menu/nzjc.png' where id='34';
update mainmodulemenu set imgurl='/images/icon/menu/zxkh.png' where id='33';
commit;

update mainmodulemenu set businesstype='1';
update mainmodulemenu set businesstype='9' where id='7';
update mainmodulemenu set businesstype='7' where id='10';
commit;

insert into RESOURCES (code, resourcename, productenabled)
values (1, '线路巡检', 2);
insert into RESOURCES (code, resourcename, productenabled)
values (2, '基站巡检', 1);
insert into RESOURCES (code, resourcename, productenabled)
values (9, '系统管理', 3);
insert into RESOURCES (code, resourcename, productenabled)
values (7, '基础资料', 3);
commit;

--2011-07-28 张亚辉
update sonmenu set lablename='月考核评分查询' where id='320202';
update sonmenu set lablename='月考核评分统计' where id='320203';

update sonmenu set lablename='其他日常考核查询' where id='320302';

update sonmenu set lablename='专项考核评分' where id='330201'; 
update sonmenu set lablename='专项考核查询' where id='330202'; 
update sonmenu set lablename='专项考核统计' where id='330203'; 


--月度
update sonmenu set id='320405' where id='320404';
update sonmenu set id='320404' where id='320403';
update sonmenu set id='320403' where id='320402';
update sonmenu set id='320402' where id='320401';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320401','待办工作','3204','/WebApp/appraiseMonthAction.do?method=waitToDoWork','','','11,12,21,22');
--专项
update sonmenu set id='330305' where id='330304';
update sonmenu set id='330304' where id='330303';
update sonmenu set id='330303' where id='330302';
update sonmenu set id='330302' where id='330301';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330301','待办工作','3303','/WebApp/appraiseSpecialAction.do?method=waitToDoWork','','','11,12,21,22');
--年终
update sonmenu set id='340405' where id='340404';
update sonmenu set id='340404' where id='340403';
update sonmenu set id='340403' where id='340402';
update sonmenu set id='340402' where id='340401';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340401','待办工作','3404','/WebApp/appraiseYearEndAction.do?method=waitToDoWork','','','11,12,21,22');
--年度
update sonmenu set id='350205' where id='350204';
update sonmenu set id='350204' where id='350203';
update sonmenu set id='350203' where id='350202';
update sonmenu set id='350202' where id='350201';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350201','待办工作','3502','/WebApp/appraiseYearAction.do?method=waitToDoWork','','','11,12,21,22');

commit;

insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3205','其他日常考核','','32','','','3','');
delete from sonmenu where id='320301';
delete from sonmenu where id='320302';
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320501','其他日常考核','3205','/WebApp/appraiseDailyOtherAction.do?method=appraiseDailyOtherForm','','','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320502','查询其他日常考核','3205','/WebApp/appraiseDailyOtherAction.do?method=queryAppraiseDailyOtherListForm','','','11,12,21,22');
commit;
--月考核添加月考核统计菜单
delete from sonmenu where id='3203';
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3203','查询统计','','32','','','2','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320301','月考核统计','3203','/WebApp/appraiseMonthStatAction.do?method=appraiseMonthStat&init=init','','','11,12,21,22');
commit;
update DICTIONARY_FORMITEM set lable='牵头联建' where assortment_id='property_right' and code='2';

update mainmodulemenu set businesstype='1' where id in ('34','35','33');
commit;

--2011-08-22 杨隽 修改POINTINFO的ISFOCUS历史数据值
UPDATE POINTINFO SET ISFOCUS='0';
COMMIT;

--2011-08-22 杨隽 修改“审核中继段”的菜单路径
UPDATE sonmenu SET hrefurl='/WebApp/resAction.do?method=approveCableList' WHERE id='72106';
COMMIT;

--2011-08-22 杨隽 屏蔽“添加资料分类”菜单
UPDATE sonmenu SET type='9' WHERE id='102103';
COMMIT;

--在数据字典表中添加deadline数据 张亚辉 2011-08-11
insert into dictionary_formitem (id,code,lable,ASSORTMENT_ID) values(seq_dictionary_formitem_id.nextval,'0','52','trouble_deadline');
insert into dictionary_formitem (id,code,lable,ASSORTMENT_ID) values(seq_dictionary_formitem_id.nextval,'1','25','trouble_deadline');
commit;

--更改考核评分菜单中考核评分的lablename 张亚辉 2011-08-29
update sonmenu set lablename='月考核评分' where id='320201';
update sonmenu set lablename='年度考核' where id='350101';
commit;

--更改专项考核中的lablename 张亚辉 2011-09-13
update sonmenu set lablename='专项考核评分' where id='330201';
update sonmenu set lablename='专项考核查询' where id='330202';
update sonmenu set lablename='专项考核统计' where id='330203';
commit;

--更改id=330203的链接地址 张亚辉 2011-09-15
update sonmenu set hrefurl='/WebApp/appraiseSpecialAction.do?method=queryAppraiseSpecialStatForm' where id='330203';
commit;
