package com.cabletech.linechange.dao;

import java.util.*;

import com.cabletech.linechange.domainobjects.*;

public interface ChangeSurveyDao extends Dao{
    public void saveChangeSurvey(ChangeSurvey surveyinfo,ChangeInfo changeinfo);
    public void updateChangeSurvey(ChangeSurvey surveyinfo,ChangeInfo changeinfo);
    public void delChangeSurvey();
    public ChangeSurvey getChangeSurvey(String id);
    public List getChangeSurveyList(String hql);
    public ChangeSurvey getChangeSurveyForChangeID( String hql, String changeid );
}
