----�������� 
--���ڣ� ������ ��˵��
-- 2010-3-19������������ɵ��Ѱ�����˵���Ϣ
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('11004','�Ѱ칤��','11','1021');

INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1100401','�Ѱ칤��','11004','/WebApp/dispatchtask/dispatch_task.do?method=queryForFinishHandledDispatchTask','11,12,21,22');
UPDATE SUBMENU SET SHOWNO='103' WHERE ID='11004';
UPDATE SUBMENU SET SHOWNO='104' WHERE ID='11003';
COMMIT;

-- 2010-3-23����������Ӳ����Ѱ�����˵���Ϣ
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('832','�Ѱ칤��','8','108');

INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('83201','�Ѱ칤��','832','/WebApp/material_apply.do?method=queryFinishHandledMaterialApplyList','11,12,21,22');

-- 2010-3-23���������������ϲ˵�����
UPDATE SUBMENU SET SHOWNO='120' WHERE ID='810';
UPDATE SUBMENU SET SHOWNO='121' WHERE ID='819';
UPDATE SUBMENU SET SHOWNO='122' WHERE ID='818';
UPDATE SUBMENU SET SHOWNO='123' WHERE ID='817';
COMMIT;
-- 2010-3-24�����������β�����ͳ�ƺ���ͳ�������˵�
UPDATE SONMENU SET TYPE='9' WHERE ID='81802';
UPDATE SONMENU SET TYPE='9' WHERE ID='81803';
-- 2010-3-25�����������������ɵ����͵�����
update dictionary_formitem set sort=code where assortment_id='dispatch_task' or assortment_id='dispatch_task_con';
COMMIT;
--����ģ�� start  
--20100317  fjj ���ϵ���ʱ���棬���Ӳ鿴��ʱ������б�
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210202','�ɵ��б�','2102','/WebApp/troubleInfoAction.do?method=getTempSaveTrouble','11,12,21,22');
--�����ɵ���ʱ����Ҫ��ʱ���棬���ش˲˵� 20100322
update   sonmenu set type='9' where id='210202';
--20100321  fjj  ���������ֵ䣬���Ϸ�����ʱ��д��Ӱ��ҵ������
insert into dictionary_assortment values('impress_type','impress_type','Ӱ��ҵ������');
insert into  dictionary_formitem values('150','1','��վ','impress_type','1','');
insert into  dictionary_formitem values('151','2','ϵͳ','impress_type','2','');
insert into  dictionary_formitem values('152','3','���ſͻ�','impress_type','3','');
insert into  dictionary_formitem values('153','4','��','impress_type','4','');
COMMIT;
--20100325  fjj  ���Ӳ˵��Ѱ칤��
insert into submenu(id,lablename,parentid,showno) values('2105','�Ѱ칤��','21','2');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210501','�Ѱ칤��','2105','/WebApp/troubleReplyAction.do?method=getFinishHandledWork','11,12,21,22');
COMMIT;


---3��30�ո��� 2010U002  �������д������

--20100401 zhufeng �����Ѱ칤��
insert into submenu(id,lablename,parentid,showno) values('2204','�Ѱ칤��','22','3');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('220401','�Ѱ칤��','2204','/WebApp/hiddangerAction.do?method=getFinishedWork','11,12,21,22');
COMMIT;

insert into submenu(id,lablename,parentid,showno) values('2704','�Ѱ칤��','27','3');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('270401','�Ѱ칤��','2704','/WebApp/acceptanceAction.do?method=getFinishedWork','11,12,21,22');
COMMIT;

--����ģ�� end 

--����ά��

--20100401 fjj ����ά�����Ӳ˵��Ѱ칤��
insert into submenu(id,lablename,parentid,showno) values('2507','�Ѱ칤��','25','2')
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('250701','�Ѱ칤��','2507','/WebApp/testPlanAction.do?method=getFinishHandledWork','11,12,21,22')
----20100401 fjj ����ά�����Ӳ˵��Ѵ����������
update sonmenu set lablename='�������������' where id='250501'
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('250502','�Ѵ����������','2505','/WebApp/problemCableAction.do?method=getFinishedProblemCables','11,12,21,22')
COMMIT;


--����ά��
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('72106','����м̶�','721','/WebApp/resAction.do?method=approveList','11,12,21,22');

--����Ѱ칤��20100401
insert into submenu values('2305','�Ѱ칤��','','23','','','3','');
insert into sonmenu values('230501','�Ѱ칤��','2305','/WebApp/cutAction.do?method=queryFinishHandledCutApplyList','','','11,12,21,22');
--�����Ѱ칤��
insert into submenu values('2405','�Ѱ칤��','','24','','','3','');
insert into sonmenu values('240501','�Ѱ칤��','2405','/WebApp/drillTaskAction.do?method=queryFinishHandledDrillList','','','11,12,21,22');
--�����Ѱ칤��
insert into submenu values('2604','�Ѱ칤��','','26','','','3','');
insert into sonmenu values('260401','�Ѱ칤��','2604','/WebApp/safeguardTaskAction.do?method=queryFinishHandledSafeguardList','','','11,12,21,22');

--������ʱ����
insert into sonmenu values('260202','��ʱ����','2602','/WebApp/safeguardTaskAction.do?method=getPerfectList','','','11,12,21,22');

-- 2010-4-2 ������ ��������ʱ��������Ķ����������˵�
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('751','��ʱ�������','7','21');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('75101','��ѯ��ʱ����','751','/WebApp/schedule.do?method=queryForm','11,12,21,22');
commit;

-- 2010-4-6 ���������������ϳ��������������˵�
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('81809','���ϳ�����ѯ','818','/WebApp/material_stock_record.do?method=queryMaterialStockRecord','11,12,21,22');
COMMIT;

insert into appconfig values('1','����ˣ�����������','approverGroupId','00025', '��������˳����˹��ˣ�ֻ���ض��û����������Ϊ����˳�����');
insert into appconfig values('2','Ѳ����Ա����ʱ������','online_period','10', 'ͨ�����ø����ܹ������ƶ�ʱ���ڵ�Ѳ����Ա');

commit;

INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('752','ϵͳ����','7','12');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('75201','��������','752','/WebApp/appconfig.do?method=loadAppConfig','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('75202','�ֵ�����','752','/WebApp/dictionary.do?method=queryDictionary','11,12,21,22');
commit;


--20100406 fjj ���ӹ���ָ��˵� 
  
insert into submenu(id,lablename,parentid,showno) values('2106','����ָ��','21','2');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210601','���ϻ�׼ָ��','2106','/WebApp/troubleQuotaAction.do?method=troubleQuotaForm','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210602','ͳ���¹���ָ��','2106','/WebApp/troubleQuotaAction.do?method=createTroubleQuotaForm','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('210603','ͳ�������ָ��','2106','/WebApp/troubleQuotaAction.do?method=yearTroubleQuotaForm','11,12,21,22');
commit;

--ά������
insert into submenu values('1021','ά������','','10','','','33','');
insert into sonmenu values('102101','���ά������','1021','/WebApp/linepatrol/resource/datumAdd.jsp','','','');
insert into sonmenu values('102102','ά��ά������','1021','/WebApp/datumAction.do?method=list','','','');
commit;

--�ʾ����
insert into mainmodulemenu values('29','�ʾ����','../images/iconmenu/xjsjcjsz.gif','','','20');
insert into submenu values('2901','�ʾ���','','29','','','1','');
insert into sonmenu values('290101','�ʾ���','2901','/WebApp/questAction.do?method=addQuestForm','','','11,12,21,22');
insert into sonmenu values('290102','�����ʾ�','2901','/WebApp/questAction.do?method=perfectIssueList','','','11,12,21,22');
insert into submenu values('2902','�����ʾ�','','29','','','2','');
insert into sonmenu values('290201','�����ʾ�','2902','/WebApp/questAction.do?method=getIssueFeedbackList','','','11,12,21,22');

-- 2010-04-17�����������Ѳ��������ܲ˵�
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('12001','������ά��˾','12','0');
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('12002','�����ά��˾','12','1');
INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('12003','ȫ������','12','2');

UPDATE SUBMENU SET TYPE='9' WHERE ID='1202';

INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200101','��ͳ��','12001','/WebApp/PlanMonthlyStatAction.do?method=queryConMonthlyStat','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200102','��ͳ��','12001','/WebApp/contractor_year_stat.do?method=queryYearStatForm','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200201','��ͳ��','12002','/WebApp/MonthlyStatCityMobileAction.do?method=queryMonthlyStat','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200202','��ͳ��','12002','/WebApp/city_mobile_year_stat.do?method=queryYearStatForm','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200301','��ͳ��','12003','/WebApp/whole_net_stat.do?method=queryMonthStatForm','11,12,21,22');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('1200302','��ͳ��','12003','/WebApp/whole_net_stat.do?method=queryYearStatForm','11,12,21,22');
COMMIT;

-- ���̹���
insert into mainmodulemenu values('28','���̹���','','','','','');
insert into submenu values('2801','��������','','28','','','1','');
insert into submenu values('2802','���칤��','','28','','','2','');
insert into submenu values('2803','�Ѱ칤��','','28','','','3','');
insert into submenu values('2804','��ѯͳ��','','28','','','4','');
insert into sonmenu values('280101','��������','2801','/WebApp/project/remedy_apply.do?method=insertApplyForm','','','');
insert into sonmenu values('280202','���칤��','2802','/WebApp/project/remedy_apply.do?method=toDoWork','','','');
insert into sonmenu values('280301','�Ѱ칤��','2803','/WebApp/project/remedy_apply.do?method=getFinishedWork','','','');
insert into sonmenu values('280401','��ѯͳ��','2804','/WebApp/project/remedy_apply.do?method=queryApplyForm','','','');
commit;

-- 2010-4-19������������Ѳ������Ĳ˵�
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

-- 2010-04-20 �ʾ���飺�ʾ���ܲ˵�
insert into submenu values('2903','�ʾ����','','29','','','3','');
insert into sonmenu values('290301','�ʾ����','2903','/WebApp/questAction.do?method=questFeedbackStatList','','','11,12,21,22');

-- ������Ŀ
insert into mainmodulemenu values('31','������Ŀ','','','','','');
insert into submenu values('3131','��������','','31','','','1','');
insert into submenu values('3132','���칤��','','31','','','2','');
insert into submenu values('3133','�Ѱ칤��','','31','','','3','');
insert into submenu values('3134','��ѯͳ��','','31','','','4','');
insert into sonmenu values('313101','��������','3131','/WebApp/overHaulAction.do?method=addTaskForm','','','');
insert into sonmenu values('313201','���칤��','3132','/WebApp/overHaulAction.do?method=toDoWork','','','');
insert into sonmenu values('313301','�Ѱ칤��','3133','/WebApp/overHaulAction.do?method=finishedWork','','','');
insert into sonmenu values('313401','��ѯͳ��','3134','/WebApp/overHaulAction.do?method=query','','','');
commit;

-- �ʾ���飺�ʾ������˵�
insert into submenu values('2904','�ʾ������','','29','','','4','');
insert into sonmenu values('290401','�����������','2904','/WebApp/questAction.do?method=questComManagerList','','','11,12,21,22');
insert into sonmenu values('290402','���͹���','2904','/WebApp/questAction.do?method=questTypeManagerList','','','11,12,21,22');
insert into sonmenu values('290403','������','2904','/WebApp/questAction.do?method=questClassManagerList','','','11,12,21,22');
insert into sonmenu values('290404','�������','2904','/WebApp/questAction.do?method=questSortManagerList','','','11,12,21,22');
insert into sonmenu values('290405','ϸ�����','2904','/WebApp/questAction.do?method=questItemManagerList','','','11,12,21,22');
commit;



-- ����ģ��  20100426 by fjj
insert into mainmodulemenu values('30','���ú�ʵ','../images/iconmenu/xlqggl.gif','','','20','1');

insert into submenu(id,lablename,parentid,showno) values('3001','ȡ��ϵ��','30','1');
insert into submenu(id,lablename,parentid,showno) values('3002','ά������','30','1');
insert into submenu(id,lablename,parentid,showno) values('3003','ά������','30','1');
insert into submenu(id,lablename,parentid,showno) values('3004','Ԥ�����','30','1');
insert into submenu(id,lablename,parentid,showno) values('3005','����ȷ��','30','1');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300101','ȡ��ϵ��','3001','/WebApp/expensesFactorAction.do?method=cableGradeFactor','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300102','ϵ���б�','3001','/WebApp/expensesFactorAction.do?method=cableGradeFactorList','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300201','ά������','3002','/WebApp/expensesPriceAction.do?method=cableUnitPrice','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300202','�����б�','3002','/WebApp/expensesPriceAction.do?method=cableUnitPriceList','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300301','��������','3003','/WebApp/expensesAction.do?method=createExpenseForm','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300501','����ȷ��','3005','/WebApp/expensesAction.do?method=queryExpenseForm','11,12,21,22')
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300502','����ȷ���б�','3005','/WebApp/expensesAction.do?method=affirmExepenseList','11,12,21,22')

insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300401','����Ԥ��','3004','/WebApp/budgeExpensesAction.do?method=addBudgetForm','11,12,21,22')
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('300402','Ԥ���б�','3004','/WebApp/budgeExpensesAction.do?method=getBudgetExpense','11,12,21,22')
commit;

-- �ʾ����
update sonmenu t set t.lablename='ָ�������' where t.id='290405';
commit;

--2010-04-29����������ӹ���ָ��Ĳ�ѯ���ܲ˵�
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('210604','ָ���ѯ','2106','/WebApp/troubleQuotaAction.do?method=queryTroubleQuotaMonthForm','11,12,21,22');
--INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('210605','��ָ���ѯ','2106','/WebApp/troubleQuotaAction.do?method=queryTroubleQuotaYearForm','11,12,21,22');
COMMIT;
-- 2010-04-30 ���Ϲ���ά������-->���Ϸ���  ���ǻ�
INSERT INTO SONMENU ( ID, LABLENAME, PARENTID, HREFURL, REMARK, TYPE ) VALUES ('102103', '������Ϸ���', '1021', '/WebApp/linepatrol/resource/datumTypeAdd.jsp', NULL, NULL);

--�ʾ����
insert into submenu values('2905','�ʾ��ѯ','','29','','','5','');
insert into sonmenu values('290501','�ʾ��ѯ','2905','/WebApp/questAction.do?method=queryIssueByConditionForm','','','11,12,21,22');

--����ά�����Ӳ˵���ѯ��������¼����Ϣ 20100514 fjj   
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('250403','��ѯ����','2504','/WebApp/testPlanQueryStatAction.do?method=queryCableForm','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('270302','�м̶β�ѯ','2703','/WebApp/linepatrol/acceptance/queryCable.jsp','');


-- 2010-06-10 ϵͳ����  ��ϵͳ��������Ӹ�������   ���ǻ�
INSERT INTO SUBMENU ( ID, LABLENAME, HREFURL, PARENTID, REMARK, REGIONID, SHOWNO,TYPE ) VALUES ('771', '��������', NULL, '7', NULL, NULL, 71, NULL); 
INSERT INTO SONMENU ( ID, LABLENAME, PARENTID, HREFURL, REMARK, TYPE ) VALUES ('77103', '��������', '771', '/WebApp/AnnexAction.do?method=queryAnnexForm', NULL, NULL);
commit;
-- 2010-6-29 ���� ��ӷ���ʱ�ޣ�һ����� 2��+6Сʱ���������ֵ���Ϣ��
INSERT INTO dictionary_formitem (id,code,lable,assortment_id,sort,parentid,regionid) VALUES(seq_dictionary_formitem_id.nextval,'0','54','trouble_deadline','1','','110000');
-- 2010-6-29 ���� ��ӷ���ʱ�ޣ��ش���� 1��+1Сʱ���������ֵ���Ϣ��
INSERT INTO dictionary_formitem (id,code,lable,assortment_id,sort,parentid,regionid) VALUES(seq_dictionary_formitem_id.nextval,'1','25','trouble_deadline','1','','110000');
COMMIT;
insert into dictionary_formitem values(seq_dictionary_formitem_id.nextval,'5','��������','property_right',2,'','110000');
insert into dictionary_formitem values(seq_dictionary_formitem_id.nextval,'6','ǣͷ�Ͻ�','property_right',5,'','110000');
insert into dictionary_formitem values(seq_dictionary_formitem_id.nextval,'7','�����Ͻ�','property_right',6,'','110000');
commit;

INSERT INTO SUBMENU(id,lablename,parentid,showno) VALUES('2705','������Դ����','27','6');
INSERT INTO SONMENU(id,lablename,parentid,hrefurl,power) VALUES('270501','��Դ����','2705','/WebApp/resourceBlendAction.do?method=searchForm','12');
commit;

-- 2010-12-28 ���ǻ� ɾ����������ͱ���б�
delete from sonmenu where lablename='�������' and id='240102';
delete from sonmenu where lablename='����б�' and id='240103';
commit;


-- 2011-03-10 ���ӹ��¡��ܵ����·��䣬�ܵ�ͳ�ƹ���
--��ӹ��¡��ܵ����·���˵�
insert into sonmenu values('72503','���·���','725','/WebApp/resAction.do?method=cableAssignOne','','','11,12,21,22');
insert into sonmenu values('72504','�ܵ�����','725','/WebApp/pipeAction.do?method=pipeAssignOne','','','11,12,21,22');
commit;

--��ӹܵ�ͳ�Ʋ˵�
insert into sonmenu values('270303','�ܵ�ͳ��','2703','/WebApp/acceptancePipesAction.do?method=forwardPipeStatPage','','','11,12,21,22');
update sonmenu set hrefurl='/WebApp/acceptancePipeStatAction.do?method=forwardPipeStatPage' where id='270303';
commit;

--������ս�ά�ƻ���ѯ�˵�
insert into sonmenu values('270103','��ά�ƻ���ѯ','2701','/WebApp/acceptancePlanQueryAction.do?method=forwardPlanQueryPage','','','11,12,21,22');
commit;

--����¿���  2011-03-25 ���ǻ�
insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('32','�¿���','','','','90','1');

insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type)  values('3201','���˱����','','32','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320102','�����¿��˱�','3201','/WebApp/linepatrol/appraise/importMonthExcel.jsp','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320101','���˱��ѯ','3201','/WebApp/appraiseTableMonthAction.do?method=appraiseTableList','','','11,12,21,22');
commit;
--�¿��� 2011--4-20 ���ǻ�
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3202','�¿�������','','32','','','2','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320201','�¿��˱�����','3202','/WebApp/appraiseMonthAction.do?method=createMonthTableForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320202','�¿��˱��ѯ','3202','/WebApp/appraiseMonthAction.do?method=queryAppraiseMonthListForm','','','11,12,21,22');

--insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320203','�¿��˱�ͳ��','3202','/WebApp/appraiseMonthAction.do?method=queryAppraiseMonthStatForm','','','11,12,21,22');
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3203','�����ճ�����','','32','','','3','');	
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320301','�����ճ�����','3203','/WebApp/appraiseDailyOtherAction.do?method=appraiseDailyOtherForm','','','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320302','��ѯ�����ճ�����','3203','/WebApp/appraiseDailyOtherAction.do?method=queryAppraiseDailyOtherListForm','','','11,12,21,22');
commit;

--�����ɵ����˲˵�
SET DEFINE OFF;
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('11005','�ɵ�����','','11','','','10','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('1100501','δ����','11005','/WebApp/dispatchExamAction.do?method=examList&examFlag=unexam','','','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('1100502','�ѿ���','11005','/WebApp/dispatchExamAction.do?method=examList&examFlag=examed','','','11,12,21,22');
commit;

--���Ӵ�����Ŀ���˲˵�
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3135','���޿���','','31','','','35','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('313501','������','3135','/WebApp/overHaulExamAction.do?method=examList','','','11,12,21,22');
commit;

--���ӹ��̹����˲˵�
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('2805','���̿���','','28','','','28','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('280501','������','2805','/WebApp/remedyExamAction.do?method=examList','','','11,12,21,22');
commit;

--�¿�������¿���ͳ�Ʋ˵�
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3203','��ѯͳ��','','32','','','2','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320301','�¿���ͳ��','3203','/WebApp/appraiseMonthStatAction.do?method=appraiseMonthStat&init=init','','','11,12,21,22');
commit;

--�޸ĵ���˵�����ѯ����
update sonmenu set hrefurl='/WebApp/dispatchExamAction.do?method=examList&examFlag=unexam&init=init' where id='1100501';
update sonmenu set hrefurl='/WebApp/dispatchExamAction.do?method=examList&examFlag=examed&init=init' where id='1100502';
update sonmenu set hrefurl='/WebApp/overHaulExamAction.do?method=examList&init=init' where id='313501';
update sonmenu set hrefurl='/WebApp/remedyExamAction.do?method=examList&init=init' where id='280501';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('210605','�Զ�����ָ��','2106','/WebApp/customTroubleQuotaActon.do?method=querycustomYearTroubleQuota','','','11,12,21,22');
commit;

--ר��� 2011-05-30 ���ǻ�
insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('33','ר���','','','','91','1');

insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3301','ר��˱����','','33','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330101','ר��˱��ѯ','3301','/WebApp/appraiseTableSpecialAction.do?method=appraiseTableList','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330102','ר��˱���','3301','/WebApp/linepatrol/appraise/importSpecialExcel.jsp','','','11,12,21,22');

commit;

update mainmodulemenu set lablename='�¶ȿ���' where id='32';
commit;
--���ռ��
insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('34','���ռ��','','','','92','1');
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3401','���ռ������','','34','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340101','���ռ����ѯ','3401','/WebApp/appraiseTableYearEndAction.do?method=appraiseTableList','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340102','���ռ�����','3401','/WebApp/linepatrol/appraise/importYearEndExcel.jsp','','','11,12,21,22');

commit;
--ר�������
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3302','ר�������','','33','','','2','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330201','ר��˱�����','3302','/WebApp/appraiseSpecialAction.do?method=createSpecialTableForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330202','ר��˱��ѯ','3302','/WebApp/appraiseSpecialAction.do?method=queryAppraiseSpecialListForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330203','ר��˱�ͳ��','3302','/WebApp/appraiseSpecialAction.do?method=queryAppraiseSpecialStateForm','','','11,12,21,22');

commit;
--���ռ����
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3402','���ռ����','','34','','','2','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340201','���ռ����','3402','/WebApp/appraiseDailyYearEndAction.do?method=appraiseDailyYearEndForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340202','���ռ���ֲ�ѯ','3402','/WebApp/appraiseDailyYearEndAction.do?method=queryAppraiseDailyYearEndListForm','','','11,12,21,22');
--���ռ�����
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3403','���ռ�����','','34','','','3','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340301','���ռ�����','3403','/WebApp/appraiseYearEndAction.do?method=createAppraiseYearEndTableForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340302','���ռ����ܲ�ѯ','3403','/WebApp/appraiseYearEndAction.do?method=queryAppraiseYearEndListForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340303','���ռ�����ͳ��','3403','/WebApp/appraiseYearEndAction.do?method=queryAppraiseYearEndStatForm','','','11,12,21,22');

--�¿���ȷ��
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3204','��άȷ��','','32','','','4','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320401','�·����˽��','3204','/WebApp/appraiseMonthAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320402','���˽��ȷ��','3204','/WebApp/appraiseMonthAction.do?method=verifyAppraiseList&type=1','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320403','��άȷ�Ͻ��','3204','/WebApp/appraiseMonthAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320404','ȷ�Ͻ����ѯ','3204','/WebApp/appraiseMonthAction.do?method=queryVerifyResultForm','','','11,12,21,22');
commit;

--ר���ȷ��
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3303','��άȷ��','','33','','','3','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330301','�·����˽��','3303','/WebApp/appraiseSpecialAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330302','���˽��ȷ��','3303','/WebApp/appraiseSpecialAction.do?method=verifyAppraiseList&type=2','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330303','��άȷ�Ͻ��','3303','/WebApp/appraiseSpecialAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330304','ȷ�Ͻ����ѯ','3303','/WebApp/appraiseSpecialAction.do?method=queryVerifyResultForm','','','11,12,21,22');

commit;
--���ռ��ȷ��
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3404','��άȷ��','','34','','','4','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340401','�·����˽��','3404','/WebApp/appraiseYearEndAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340402','���˽��ȷ��','3404','/WebApp/appraiseYearEndAction.do?method=verifyAppraiseList&type=4','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340403','��άȷ�Ͻ��','3404','/WebApp/appraiseYearEndAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340404','ȷ�Ͻ����ѯ','3404','/WebApp/appraiseYearEndAction.do?method=queryVerifyResultForm','','','11,12,21,22');

commit;

--��ȿ���ȷ��
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3502','��άȷ��','','35','','','3','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350201','�·����˽��','3502','/WebApp/appraiseYearAction.do?method=querySendAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350202','���˽��ȷ��','3502','/WebApp/appraiseYearAction.do?method=verifyAppraiseList&type=3','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350203','��άȷ�Ͻ��','3502','/WebApp/appraiseYearAction.do?method=queryVerifyResultAppraiseForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350204','ȷ�Ͻ����ѯ','3502','/WebApp/appraiseYearAction.do?method=queryVerifyResultForm','','','11,12,21,22');


commit;

--��ȿ���

insert into mainmodulemenu(id,lablename,imgurl,hrefurl,remark,showno,state) values('35','��ȿ���','/images/icon/menu/ndkh.png','','','93','1');
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3501','��ȿ���','','35','','','1','');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350101','��ȿ�������','3501','/WebApp/appraiseYearAction.do?method=createAppraiseYearForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350102','��ȿ��˲�ѯ','3501','/WebApp/appraiseYearAction.do?method=queryAppraiseYearForm','','','11,12,21,22');

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350103','��ȿ���ͳ��','3501','/WebApp/appraiseYearAction.do?method=queryAppraiseYearStatForm','','','11,12,21,22');

-- 2011-07-08 ���ǻ�

update sonmenu set hrefurl='/WebApp/resAction.do?method=approveCableList' where id='72106';

-- 2011-07-11 ���ǻ�
update mainmodulemenu set imgurl='/images/icon/menu/nzjc.png' where id='34';
update mainmodulemenu set imgurl='/images/icon/menu/zxkh.png' where id='33';
commit;

update mainmodulemenu set businesstype='1';
update mainmodulemenu set businesstype='9' where id='7';
update mainmodulemenu set businesstype='7' where id='10';
commit;

insert into RESOURCES (code, resourcename, productenabled)
values (1, '��·Ѳ��', 2);
insert into RESOURCES (code, resourcename, productenabled)
values (2, '��վѲ��', 1);
insert into RESOURCES (code, resourcename, productenabled)
values (9, 'ϵͳ����', 3);
insert into RESOURCES (code, resourcename, productenabled)
values (7, '��������', 3);
commit;

--2011-07-28 ���ǻ�
update sonmenu set lablename='�¿������ֲ�ѯ' where id='320202';
update sonmenu set lablename='�¿�������ͳ��' where id='320203';

update sonmenu set lablename='�����ճ����˲�ѯ' where id='320302';

update sonmenu set lablename='ר�������' where id='330201'; 
update sonmenu set lablename='ר��˲�ѯ' where id='330202'; 
update sonmenu set lablename='ר���ͳ��' where id='330203'; 


--�¶�
update sonmenu set id='320405' where id='320404';
update sonmenu set id='320404' where id='320403';
update sonmenu set id='320403' where id='320402';
update sonmenu set id='320402' where id='320401';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320401','���칤��','3204','/WebApp/appraiseMonthAction.do?method=waitToDoWork','','','11,12,21,22');
--ר��
update sonmenu set id='330305' where id='330304';
update sonmenu set id='330304' where id='330303';
update sonmenu set id='330303' where id='330302';
update sonmenu set id='330302' where id='330301';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('330301','���칤��','3303','/WebApp/appraiseSpecialAction.do?method=waitToDoWork','','','11,12,21,22');
--����
update sonmenu set id='340405' where id='340404';
update sonmenu set id='340404' where id='340403';
update sonmenu set id='340403' where id='340402';
update sonmenu set id='340402' where id='340401';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('340401','���칤��','3404','/WebApp/appraiseYearEndAction.do?method=waitToDoWork','','','11,12,21,22');
--���
update sonmenu set id='350205' where id='350204';
update sonmenu set id='350204' where id='350203';
update sonmenu set id='350203' where id='350202';
update sonmenu set id='350202' where id='350201';

insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('350201','���칤��','3502','/WebApp/appraiseYearAction.do?method=waitToDoWork','','','11,12,21,22');

commit;

insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3205','�����ճ�����','','32','','','3','');
delete from sonmenu where id='320301';
delete from sonmenu where id='320302';
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320501','�����ճ�����','3205','/WebApp/appraiseDailyOtherAction.do?method=appraiseDailyOtherForm','','','11,12,21,22');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320502','��ѯ�����ճ�����','3205','/WebApp/appraiseDailyOtherAction.do?method=queryAppraiseDailyOtherListForm','','','11,12,21,22');
commit;
--�¿�������¿���ͳ�Ʋ˵�
delete from sonmenu where id='3203';
insert into submenu(id,lablename,hrefurl,parentid,remark,regionid,showno,type) values('3203','��ѯͳ��','','32','','','2','');
insert into sonmenu(id,lablename,parentid,hrefurl,remark,type,power) values('320301','�¿���ͳ��','3203','/WebApp/appraiseMonthStatAction.do?method=appraiseMonthStat&init=init','','','11,12,21,22');
commit;
update DICTIONARY_FORMITEM set lable='ǣͷ����' where assortment_id='property_right' and code='2';

update mainmodulemenu set businesstype='1' where id in ('34','35','33');
commit;

--2011-08-22 ���� �޸�POINTINFO��ISFOCUS��ʷ����ֵ
UPDATE POINTINFO SET ISFOCUS='0';
COMMIT;

--2011-08-22 ���� �޸ġ�����м̶Ρ��Ĳ˵�·��
UPDATE sonmenu SET hrefurl='/WebApp/resAction.do?method=approveCableList' WHERE id='72106';
COMMIT;

--2011-08-22 ���� ���Ρ�������Ϸ��ࡱ�˵�
UPDATE sonmenu SET type='9' WHERE id='102103';
COMMIT;

--�������ֵ�������deadline���� ���ǻ� 2011-08-11
insert into dictionary_formitem (id,code,lable,ASSORTMENT_ID) values(seq_dictionary_formitem_id.nextval,'0','52','trouble_deadline');
insert into dictionary_formitem (id,code,lable,ASSORTMENT_ID) values(seq_dictionary_formitem_id.nextval,'1','25','trouble_deadline');
commit;

--���Ŀ������ֲ˵��п������ֵ�lablename ���ǻ� 2011-08-29
update sonmenu set lablename='�¿�������' where id='320201';
update sonmenu set lablename='��ȿ���' where id='350101';
commit;

--����ר����е�lablename ���ǻ� 2011-09-13
update sonmenu set lablename='ר�������' where id='330201';
update sonmenu set lablename='ר��˲�ѯ' where id='330202';
update sonmenu set lablename='ר���ͳ��' where id='330203';
commit;

--����id=330203�����ӵ�ַ ���ǻ� 2011-09-15
update sonmenu set hrefurl='/WebApp/appraiseSpecialAction.do?method=queryAppraiseSpecialStatForm' where id='330203';
commit;
