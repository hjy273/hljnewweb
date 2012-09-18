--20100323 新增菜单 数据分析  by fjj
insert into submenu(id,lablename,parentid,showno) values('2507','数据分析','25','2')
insert into sonmenu(id,lablename,parentid,hrefurl,power) values('250701','纤芯数据分析','2507','/WebApp/testYearPlanAction.do?method=queryYearPlanForm','11,12,21,22')

--20100324 数据分析增加序列号
 CREATE SEQUENCE SEQ_LP_TEST_CONNECTORWASTE_ID
  INCREMENT BY 1 
          START WITH 1 
          NOMAXVALUE
          NOCYCLE 
          NOCACHE 

CREATE SEQUENCE SEQ_LP_TEST_COREDATA_ID
  INCREMENT BY 1 
          START WITH 1 
          NOMAXVALUE
          NOCYCLE 
          NOCACHE 

CREATE SEQUENCE SEQ_LP_TEST_CORELENGTH_ID
  INCREMENT BY 1 
          START WITH 1 
          NOMAXVALUE
          NOCYCLE 
          NOCACHE       

CREATE SEQUENCE SEQ_LP_TEST_DECAYCONSTANT_ID
  INCREMENT BY 1 
          START WITH 1 
          NOMAXVALUE
          NOCYCLE 
          NOCACHE 
          
CREATE SEQUENCE SEQ_LP_TEST_ENDWASTE_ID
  INCREMENT BY 1 
          START WITH 1 
          NOMAXVALUE
          NOCYCLE 
          NOCACHE 
                 
CREATE SEQUENCE SEQ_LP_TEST_OTHERANALYSE_ID
  INCREMENT BY 1 
          START WITH 1 
          NOMAXVALUE
          NOCYCLE 
          NOCACHE 
          
CREATE SEQUENCE SEQ_LP_TEST_EXCEPTIONEVENT_ID
  INCREMENT BY 1 
          START WITH 1 
          NOMAXVALUE
          NOCYCLE 
          NOCACHE       
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          