package com.cabletech.linepatrol.quest.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.drill.module.DrillConstant;
import com.cabletech.linepatrol.quest.beans.Item;
import com.cabletech.linepatrol.quest.beans.QuestionTable;
import com.cabletech.linepatrol.quest.beans.Type;
import com.cabletech.linepatrol.quest.dao.QuestContractorDao;
import com.cabletech.linepatrol.quest.dao.QuestDao;
import com.cabletech.linepatrol.quest.dao.QuestGradeRuleDao;
import com.cabletech.linepatrol.quest.dao.QuestGuidelineClassDao;
import com.cabletech.linepatrol.quest.dao.QuestGuidelineItemDao;
import com.cabletech.linepatrol.quest.dao.QuestGuidelineSortDao;
import com.cabletech.linepatrol.quest.dao.QuestIssueDao;
import com.cabletech.linepatrol.quest.dao.QuestIssueGradeItemDao;
import com.cabletech.linepatrol.quest.dao.QuestIssueResultDao;
import com.cabletech.linepatrol.quest.dao.QuestIssueReviewObjDao;
import com.cabletech.linepatrol.quest.dao.QuestReviewObjectDao;
import com.cabletech.linepatrol.quest.dao.QuestTypeDao;
import com.cabletech.linepatrol.quest.module.QuestContractor;
import com.cabletech.linepatrol.quest.module.QuestGradeRule;
import com.cabletech.linepatrol.quest.module.QuestGuidelineClass;
import com.cabletech.linepatrol.quest.module.QuestGuidelineItem;
import com.cabletech.linepatrol.quest.module.QuestGuidelineSort;
import com.cabletech.linepatrol.quest.module.QuestIssue;
import com.cabletech.linepatrol.quest.module.QuestIssueGradeItem;
import com.cabletech.linepatrol.quest.module.QuestIssueResult;
import com.cabletech.linepatrol.quest.module.QuestIssueReviewObj;
import com.cabletech.linepatrol.quest.module.QuestReviewObject;
import com.cabletech.linepatrol.quest.module.QuestType;

@Service
@Transactional
public class QuestBo extends EntityManager<QuestIssue, String> {

	private static Logger logger = Logger.getLogger(QuestBo.class.getName());

	@Resource(name = "questDao")
	private QuestDao questDao;
	
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "questTypeDao")
	private QuestTypeDao questTypeDao;
	
	@Resource(name = "questReviewObjectDao")
	private QuestReviewObjectDao questReviewObjectDao;
	
	@Resource(name = "questGuidelineClassDao")
	private QuestGuidelineClassDao questGuidelineClassDao;
	
	@Resource(name = "questGuidelineSortDao")
	private QuestGuidelineSortDao questGuidelineSortDao;
	
	@Resource(name = "questGuidelineItemDao")
	private QuestGuidelineItemDao questGuidelineItemDao;
	
	@Resource(name = "questIssueGradeItemDao")
	private QuestIssueGradeItemDao questIssueGradeItemDao;
	
	@Resource(name = "questIssueDao")
	private QuestIssueDao questIssueDao;
	
	@Resource(name = "questContractorDao")
	private QuestContractorDao questContractorDao;
	
	@Resource(name = "questIssueReviewObjDao")
	private QuestIssueReviewObjDao questIssueReviewObjDao;
	
	@Resource(name = "questGradeRuleDao")
	private QuestGradeRuleDao questGradeRuleDao;
	
	@Resource(name = "questIssueResultDao")
	private QuestIssueResultDao questIssueResultDao;
	
	@Override
	protected HibernateDao<QuestIssue, String> getEntityDao() {
		return questDao;
	}
	
	/**
	 * ����ʾ������Ϣ
	 * @return
	 */
	public Map addQuestForm(){
		Map map = new HashMap();
		List questTypes = questTypeDao.find("from QuestType");
		List questReviewObjects = questReviewObjectDao.getAllCompanyList();
		/*//����һ���ʾ�
		QuestIssue questIssue = new QuestIssue();
		QuestIssue questIssueSave = questIssueDao.saveQuestIssue(questIssue);
		String issueId = questIssueSave.getId();*/
		map.put("questTypes", questTypes);
		map.put("questReviewObjects", questReviewObjects);
		//map.put("issueId", issueId);
		return map;
	}
	
	//�����ʱ������ʾ��б�
	public List perfectIssueList(){
		List list = questIssueDao.perfectIssueList();
		return list;
	}
	
	public void deleteQuest(String issueId) throws ServiceException{
		//ɾ���ʾ�
		questIssueDao.delete(issueId);
		//ɾ���ʾ����ά��˾��ϵ����
		List<QuestContractor> issueConList = questContractorDao.findByProperty("questId", issueId);
		if(issueConList != null && issueConList.size() > 0){
			for(QuestContractor questCon:issueConList){
				questContractorDao.delete(questCon);
			}
		}
		//ɾ���ʾ�����������ϵ
		List<QuestIssueReviewObj> issueRevObjList = questIssueReviewObjDao.findByProperty("questId", issueId);
		if(issueRevObjList != null && issueRevObjList.size() > 0){
			for(QuestIssueReviewObj issueReviewObj:issueRevObjList){
				questIssueReviewObjDao.delete(issueReviewObj);
			}
		}
		//ɾ���ʾ�������
		List<QuestIssueGradeItem> issueGradeItemList = questIssueGradeItemDao.findByProperty("questId", issueId);
		if(issueGradeItemList != null && issueGradeItemList.size() > 0){
			for(QuestIssueGradeItem gradeItem:issueGradeItemList){
				questIssueGradeItemDao.delete(gradeItem);
			}
		}
	}
	
	//��ʱ����
	public void tempSave(String creator, String queryType, String issueId, String issueName, 
			String conId, String companyId, String remark, String state, String[] itemIdArray) throws ServiceException{
		QuestIssue questIssue = null;
		if(issueId != null && !"".equals(issueId)){
			questIssue = questIssueDao.findByUnique("id", issueId);
		}else{
			questIssue = new QuestIssue();
		}
		questIssue.setName(issueName);
		questIssue.setState(state);
		questIssue.setType(queryType);
		questIssue.setRemark(remark);
		questIssue.setCreator(creator);
		questIssue.setCreateDate(new Date());
		QuestIssue questIssueSave = questIssueDao.saveQuestIssue(questIssue);
		String questId = questIssueSave.getId();
		questIssueGradeItemDao.deleteGradeItemByIssueId(questId);
		questIssueReviewObjDao.deleteReviewObjByIssueId(questId);
		if(conId != null && !"".equals(conId)){
			questContractorDao.deleteQuestConByIssueId(questId);
			String[] cons = conId.split(",");
			for (int i = 0; i < cons.length; i++) {
				QuestContractor questContractor = new QuestContractor();
				questContractor.setQuestId(questId);
				questContractor.setConId(cons[i]);
				questContractorDao.save(questContractor);
			}
		}
		if(companyId != null && companyId.length() > 0){
			String[] coms = companyId.split(",");
			for (int i = 0; i < coms.length; i++) {
				QuestIssueReviewObj questIssueReviewObj = new QuestIssueReviewObj();
				questIssueReviewObj.setQuestId(questId);
				questIssueReviewObj.setReviewId(coms[i]);
				questIssueReviewObjDao.save(questIssueReviewObj);
			}
		}
		if(itemIdArray != null && !"".equals(itemIdArray)){
			for(int i = 0; i < itemIdArray.length; i++){
				QuestIssueGradeItem gitem = new QuestIssueGradeItem();
				gitem.setItemId(itemIdArray[i]);
				gitem.setQuestId(questId);
				questIssueGradeItemDao.save(gitem);
			}
		}
	}
	
	//�����ʾ�
	public void addQuest(String creator, String typeId, String issueId, String issueName, String conId, 
			String companyId, String remark, String[] itemIdArray, String mobileId) throws ServiceException {
		//QuestIssue questIssue = questIssueDao.findByUnique("id", issueId);
		QuestIssue questIssue = null;
		if(issueId != null && !"".equals(issueId)){
			questIssue = questIssueDao.findByUnique("id", issueId);
		}else{
			questIssue = new QuestIssue();
		}
		questIssue.setName(issueName);
		questIssue.setType(typeId);
		questIssue.setRemark(remark);
		questIssue.setState(QuestIssue.SAVE);
		questIssue.setCreator(creator);
		questIssue.setCreateDate(new Date());
		QuestIssue questIssueSave = questIssueDao.saveQuestIssue(questIssue);
		String questId = questIssueSave.getId();
		questIssueGradeItemDao.deleteGradeItemByIssueId(questId);
		questIssueReviewObjDao.deleteReviewObjByIssueId(questId);
		if(conId != null && !"".equals(conId)){
			questContractorDao.deleteQuestConByIssueId(questId);
			String[] cons = conId.split(",");
			for (int i = 0; i < cons.length; i++) {
				QuestContractor questContractor = new QuestContractor();
				questContractor.setQuestId(questId);
				questContractor.setConId(cons[i]);
				questContractor.setState("1");
				questContractorDao.save(questContractor);
			}
		}
		if(companyId != null && companyId.length() > 0){
			String[] coms = companyId.split(",");
			for (int i = 0; i < coms.length; i++) {
				QuestIssueReviewObj questIssueReviewObj = new QuestIssueReviewObj();
				questIssueReviewObj.setQuestId(questId);
				questIssueReviewObj.setReviewId(coms[i]);
				questIssueReviewObjDao.save(questIssueReviewObj);
			}
		}
		if(itemIdArray != null && !"".equals(itemIdArray)){
			for(int i = 0; i < itemIdArray.length; i++){
				QuestIssueGradeItem gitem = new QuestIssueGradeItem();
				gitem.setItemId(itemIdArray[i]);
				gitem.setQuestId(questId);
				questIssueGradeItemDao.save(gitem);
			}
		}
		
		//���Ͷ���
		try{
		String[] mobileIds = mobileId.split(",");
		for (int i = 0; i < mobileIds.length; i++) {
			String content = "����һ����Ϊ��" + issueName + "�����ʾ����ȴ������д��";
			sendMessage(content, mobileIds[i]);
			saveMessage(content, mobileIds[i], questId, QuestIssue.QUESTCONTRACTOR,
					ModuleCatalog.QUEST);
		}
		}catch(Exception ex){
			
		}
		
		/*if(conId != null && conId.length() > 0){
			String[] cons = conId.split(",");
			String content = "����һ����Ϊ��" + issueName + "�����ʾ����ȴ������д��";
			for (int i = 0; i < cons.length; i++) {
				String questConId = questContractorDao.getIdByIssueIdAndConId(questId, cons[i]);
				String phone = questIssueDao.getPhoneByConId(cons[i]);
				sendMessage(content, phone);
				saveMessage(content, phone, questConId, QuestIssue.QUESTCONTRACTOR,
						ModuleCatalog.QUEST);
			}
		}*/
	}
	
	/**
	 * ���Ͷ���
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * ������ż�¼
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ������
	 * @param entityModule��ʵ��ģ��
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			for (String mobile : mobileList) {
				SMHistory history = new SMHistory();
				history.setSimIds(mobile);
				history.setSendContent(content);
				history.setSendTime(new Date());
				history.setSmType(entityType);
				history.setObjectId(entityId);
				history.setBusinessModule(entityModule);
				historyDAO.save(history);
			}
		}
	}
	
	//���ʾ�������ʾ����
	public List querySortList(String classId){
		List list = questGuidelineSortDao.getSortListByClassId(classId);
		return list;
	}
	
	//����������б�
	public List queryItemList(String sortId, String issueId){
		List list = questGuidelineItemDao.getItemListBySortId(sortId, issueId);
		return list;
	}
	
	//���������
	public void addItem(String items, String issueId){
		//questIssueGradeItemDao.deleteGradeItemByIssueId(issueId);
		questIssueGradeItemDao.addItem(items,issueId);
	}
	
	//���ʾ�ID��ѯ�ʾ����б�
	public List queryItemListByIssueId(String issueId){
		List list = questGuidelineItemDao.queryItemListByIssueId(issueId);
		return list;
	}
	
	//ɾ���ʾ���
	public void deleteItemByIssueItemId(String issueId, String issueItemId){
		questGuidelineItemDao.deleteItemByIssueItemId(issueItemId);
	}
	
	//��ñ����鹫˾��Ϣ�б�
	public List getCompanyInfoList(){
		List list = questReviewObjectDao.getAllCompanyList();
		return list;
	}
	
	//��ɴ洢�����鹫˾��Ϣ�����ر��ʾ�˾ID��Name
	public Map addCompanyFinish(String[] companyIds){
		Map map = new HashMap();
		String companyNames = questReviewObjectDao.getCompanyNames(companyIds);
		String comIds = "";
		if(companyIds != null && companyIds.length > 0){
			for (int i = 0; i < companyIds.length; i++) {
				comIds += companyIds[i] + ",";
			}
		}
		if(!"".equals(comIds)){
			comIds = comIds.substring(0, comIds.length() - 1);
		}
		map.put("comIds", comIds);
		map.put("companyNames", companyNames);
		return map;
	}
	
	//�༭�ʾ������Ϣ
	public Map editQuestForm(String issueId){
		Map map = new HashMap();
		QuestIssue questIssue = questIssueDao.findByUnique("id", issueId);
		String comIds = questIssueReviewObjDao.getCompIdsByIssueId(issueId);
		String comNames = questReviewObjectDao.getComNamesByComIds(comIds);
		List questTypes = questTypeDao.find("from QuestType");
		String[] userIds = questContractorDao.getConUserIdsByTaskId(issueId);
		map.put("questIssue", questIssue);
		map.put("comIds", comIds);
		map.put("comNames", comNames);
		map.put("questTypes", questTypes);
		map.put("userIds", userIds);
		return map;
	}
	
	public List getIssueFeedbackList(String conId){
		List list = questIssueDao.getIssueFeedbackList(conId);
		return list;
	}
	
	public Map addFeedbackIssueForm1(String issueId, String conId) {
		Map map = new HashMap();
		QuestIssue issue = questIssueDao.findByUnique("id", issueId);
		String state = questContractorDao.getStateByIssueIdAndConId(issueId, conId);
		String comIds = "";
		if(QuestContractor.ISSUE.equals(state)){
			comIds = questIssueReviewObjDao.getCompIdsByIssueId(issueId);
		}else{
			comIds = questIssueResultDao.getComIdByIssueId(issueId, conId);
		}
		String comNames = questReviewObjectDao.getComNamesByComIds(comIds);
		map.put("issue", issue);
		map.put("comIds", comIds);
		map.put("comNames", comNames);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map addFeedbackIssueForm(String issueId, String comIds) throws ServiceException  {
		QuestIssue issue = questIssueDao.findByUnique("id", issueId);
		String issueName = issue.getName();
		String issueType = questIssueDao.getTypeNameByIsssueId(issueId);
		Map map = new HashMap();
		String itemIds = "";// ���������IDs
		HashMap<String, Type> type1Map = new HashMap<String, Type>();
		HashMap<String, Type> type2Map = new HashMap<String, Type>();

		ArrayList<Item> itemList = new ArrayList();

		List<DynaBean> lst = (List<DynaBean>) questIssueGradeItemDao
				.getTableItems(issueId);
		String type1Key, type2Key, type1Name, type2Name;
		Item item;
		Type type1, type2;
		String itemId, itemName, itemRemark;
		int itemScore;
		if (lst != null && lst.size() > 0) {
			for (DynaBean bean : lst) {
				itemId = bean.get("id") == null ? "" : bean.get("id")
						.toString();
				itemName = bean.get("item") == null ? "" : bean.get("item")
						.toString();
				itemRemark = bean.get("remark") == null ? "" : bean.get(
						"remark").toString();
				itemScore = Integer.parseInt(bean.get("score") == null ? "" : bean.get(
						"score").toString());

				itemIds += itemId + ",";
				item = new Item(itemId, itemName, itemRemark, itemScore);

				type1Key = bean.get("cid").toString();
				type1Name = bean.get("type1").toString();
				
				if (type1Map.containsKey(type1Key)) {
					type1Map.get(type1Key).itemCountInc();
					int scoreAdd = (type1Map.get(type1Key)).getScore() + itemScore;
					(type1Map.get(type1Key)).setScore(scoreAdd);
				} else {
					type1 = new Type(type1Key, type1Name);
					type1.itemCountInc();
					type1.setScore(itemScore);
					type1Map.put(type1Key, type1);
					item.setParentParent(type1);
				}

				type2Key = bean.get("sid").toString();
				type2Name = bean.get("type2").toString();

				if (type2Map.containsKey(type2Key)) {
					type2Map.get(type2Key).itemCountInc();
					int scoreAdd = (type2Map.get(type2Key)).getScore() + itemScore;
					(type2Map.get(type2Key)).setScore(scoreAdd);
				} else {
					type2 = new Type(type2Key, type2Name);
					type2.itemCountInc();
					type2.setScore(itemScore);
					type2Map.put(type2Key, type2);
					item.setParent(type2);
				}
				itemList.add(item);
			}
		}
		List manufactorList = new ArrayList();

		if (comIds != null && !"".equals(comIds)) {
			String[] coms = comIds.split(",");
			for (int i = 0; i < coms.length; i++) {
				String comId = coms[i];
				QuestReviewObject questReviewObject = questReviewObjectDao
						.findByUnique("id", comId);
				manufactorList.add(questReviewObject);
			}
		}
		if(!"".equals(itemIds)){
			itemIds = itemIds.substring(0, itemIds.length() - 1);
		}
		QuestionTable table = new QuestionTable();
		table.setManufactorList(manufactorList);
		table.setItemList(itemList);
		map.put("issueName", issueName);
		map.put("issueType", issueType);
		map.put("table", table);
		map.put("itemIds", itemIds);
		return map;
	}
	
	/**
	 * �ɲ�����ID�������ϸ���б�Ͳ��������
	 * @param itemId��������ID
	 * @return
	 */
	public Map getRuleInfo(String itemId){
		Map map = new HashMap();
		List list = questGradeRuleDao.findByProperty("itemId", itemId);
		QuestGuidelineItem questitem = questGuidelineItemDao.findByUnique("id", itemId);
		map.put("list", list);
		map.put("questitem", questitem);
		return map;
	}
	
	//�����ʾ���
	public void saveIssueResult(String issueId, String score, String comId, 
			String itemId, String conId) throws ServiceException {
		QuestIssueResult questIssueResult = new QuestIssueResult();
		questIssueResult.setItemId(itemId);
		questIssueResult.setReviewId(comId);
		questIssueResult.setScore(score);
		questIssueResult.setUserId(conId);
		questIssueResultDao.save(questIssueResult);
	}
	
	public void deleteIssueResultByIssueId(String issueId, String conId) throws ServiceException {
		//ɾ����ǰ���ʾ���
		questIssueResultDao.deleteIssueResultByIssueId(issueId, conId);
	}
	
	public void changeStateByIssueIdAndConId(String issueId, 
			String conId, String state, UserInfo userInfo, String saveflag) throws ServiceException {
		//�޸�״̬
		questContractorDao.changeStateByIssueIdAndConId(issueId, conId, state);
		
		QuestIssue issue = questIssueDao.findByUnique("id", issueId);
		String deptName = userInfo.getDeptName();
		String creator = issue.getCreator();
		String phone = getPhoneByUserId(creator);
		String questConId = questContractorDao.getIdByIssueIdAndConId(issueId, conId);
		if(QuestContractor.SAVE.equals(saveflag)){
		//���Ͷ���
			if(conId != null && conId.length() > 0){
				String content = deptName + "��ά��˾�ύ��һ����Ϊ��" + issue.getName() + "�����ʾ���飡";
				sendMessage(content, phone);
				saveMessage(content, phone, questConId, QuestIssue.QUESTCONTRACTOR,
							ModuleCatalog.QUEST);
			}
		}
	}
	
	public void tempSaveFeedbackIssue(String issueId, String score, String comId, 
			String itemId, String conId) throws ServiceException {
		QuestIssueResult questIssueResult = new QuestIssueResult();
		questIssueResult.setItemId(itemId);
		questIssueResult.setReviewId(comId);
		questIssueResult.setScore(score);
		questIssueResult.setUserId(conId);
		questIssueResultDao.save(questIssueResult);
	}
	
	/**
	 * �����ʾ�ʱ����������
	 * @param issueId���ʾ�ID
	 * @param comIds���ʾ����ID
	 * @param conId����ά��˾ID
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Map editFeedbackIssueForm(String issueId, 
			String comIds, String conId) throws ServiceException {
		QuestIssue issue = questIssueDao.findByUnique("id", issueId);
		String issueName = issue.getName();
		String issueType = questIssueDao.getTypeNameByIsssueId(issueId);
		Map map = new HashMap();
		String itemIds = "";// ���������IDs
		HashMap<String, Type> type1Map = new HashMap<String, Type>();
		HashMap<String, Type> type2Map = new HashMap<String, Type>();

		//�����洢û��ָ������ϸ��Ϣ
		ArrayList<Item> itemList = new ArrayList();

		//���ĳһ�ʾ���������ʾ��Ϣ
		List<DynaBean> lst = (List<DynaBean>) questIssueGradeItemDao
				.getTableItems(issueId);
		//type1Key�����ID type1Name���������  type2Key������ID type2Name����������
		String type1Key, type2Key, type1Name, type2Name;
		Item item;
		Type type1, type2;
		String itemId, itemName, itemRemark;
		int itemScore;
		if (lst != null && lst.size() > 0) {
			for (DynaBean bean : lst) {
				itemId = bean.get("id") == null ? "" : bean.get("id")
						.toString();
				itemName = bean.get("item") == null ? "" : bean.get("item")
						.toString();
				itemRemark = bean.get("remark") == null ? "" : bean.get(
						"remark").toString();
				itemScore = Integer.parseInt(bean.get("score") == null ? "" : bean.get(
						"score").toString());

				itemIds += itemId + ",";
				//����һ��ָ���� itemId��ָ����ID itemName��ָ�������� itemRemard��ָ���ע itemScore��ָ�������
				item = new Item(itemId, itemName, itemRemark, itemScore);

				//��������Ϣ
				type1Key = bean.get("cid").toString();
				type1Name = bean.get("type1").toString();
				
				if (type1Map.containsKey(type1Key)) {
					type1Map.get(type1Key).itemCountInc();
					//scoreAdd����¼����ܷ���
					int scoreAdd = (type1Map.get(type1Key)).getScore() + itemScore;
					(type1Map.get(type1Key)).setScore(scoreAdd);
				} else {
					type1 = new Type(type1Key, type1Name);
					type1.itemCountInc();
					type1.setScore(itemScore);
					type1Map.put(type1Key, type1);
					item.setParentParent(type1);
				}

				//��ӷ�����Ϣ
				type2Key = bean.get("sid").toString();
				type2Name = bean.get("type2").toString();

				if (type2Map.containsKey(type2Key)) {
					type2Map.get(type2Key).itemCountInc();
					//scoreAdd����¼�����ܷ���
					int scoreAdd = (type2Map.get(type2Key)).getScore() + itemScore;
					(type2Map.get(type2Key)).setScore(scoreAdd);
				} else {
					type2 = new Type(type2Key, type2Name);
					type2.itemCountInc();
					type2.setScore(itemScore);
					type2Map.put(type2Key, type2);
					item.setParent(type2);
				}
				itemList.add(item);
			}
		}
		//��Ӳ���������Ϣ
		List manufactorList = new ArrayList();

		if (comIds != null && !"".equals(comIds)) {
			String[] coms = comIds.split(",");
			for (int i = 0; i < coms.length; i++) {
				String comId = coms[i];
				QuestReviewObject questReviewObject = questReviewObjectDao
						.findByUnique("id", comId);
				manufactorList.add(questReviewObject);
			}
		}
		//�����е��ʾ���IDƴ������
		if(!"".equals(itemIds)){
			itemIds = itemIds.substring(0, itemIds.length() - 1);
		}
		//����һ���ʾ�Table
		QuestionTable table = new QuestionTable();
		table.setManufactorList(manufactorList);
		table.setItemList(itemList);
		map.put("issueName", issueName);
		map.put("issueType", issueType);
		map.put("table", table);
		map.put("itemIds", itemIds);
		
		//��Ϊ��ʱ����ʱ�������ʱ���������
		Map issueResultMap = questIssueResultDao.getExistsValueByIssueId(issueId, conId);
		map.put("issueResultMap", issueResultMap);
		return map;
	}
	
	public List questFeedbackStatList(){
		List list = questIssueDao.questFeedbackStatList();
		return list;
	}
	
	public Map questFeedbackStat(String issueId){
		Map map = questIssueDao.questFeedbackStat(issueId);
		return map;
	}
	
	public String getGItemIdByIssueIdAndItemId(String issueId, String itemId){
		return questIssueGradeItemDao.getGItemIdByIssueIdAndItemId(issueId, itemId);
	}
	
	public List questComManagerList() {
		List list = questReviewObjectDao.questComManagerList();
		return list;
	}
	
	public String judgeComExists(String comName, String comId){
		return questReviewObjectDao.judgeComExists(comName, comId);
	}
	
	public QuestReviewObject editComForm(String comId) throws ServiceException {
		QuestReviewObject comInfo = questReviewObjectDao.findByUnique("id", comId);
		return comInfo;
	}
	
	public void editCom(String comId, String comName) throws ServiceException {
		QuestReviewObject comInfo = null;
		if(comId == null || "".equals(comId)){
			comInfo = new QuestReviewObject();
		}else{
			comInfo = questReviewObjectDao.findByUnique("id", comId);
		}
		comInfo.setObject(comName);
		questReviewObjectDao.save(comInfo);
	}
	
	public void deleteCom(String comId) throws ServiceException {
		QuestReviewObject comInfo = questReviewObjectDao.findByUnique("id", comId);
		questReviewObjectDao.delete(comInfo);
	}
	
	public List questTypeManagerList() {
		List list = questTypeDao.questTypeManagerList();
		return list;
	}
	
	public String judgeTypeExists(String typeName, String typeId){
		return questTypeDao.judgeTypeExists(typeName, typeId);
	}
	
	public QuestType editTypeForm(String typeId) throws ServiceException {
		QuestType typeInfo = questTypeDao.findByUnique("id", typeId);
		return typeInfo;
	}
	
	public void editType(String typeId, String typeName, String remark) throws ServiceException {
		QuestType typeInfo = null;
		if(typeId == null || "".equals(typeId)){
			typeInfo = new QuestType();
		}else{
			typeInfo = questTypeDao.findByUnique("id", typeId);
		}
		typeInfo.setType(typeName);
		typeInfo.setRemark(remark);
		questTypeDao.save(typeInfo);
	}
	
	public List questClassManagerList() {
		List list = questGuidelineClassDao.questClassManagerList();
		return list;
	}
	
	public List addClassForm(){
		List types = questTypeDao.getAllQuestType();
		return types;
	}
	
	public Map editClassForm(String classId){
		Map map = new HashMap();
		List types = questTypeDao.getAllQuestType();
		QuestGuidelineClass gclass = questGuidelineClassDao.findByUnique("id", classId);
		map.put("types", types);
		map.put("gclass", gclass);
		return map;
	}
	
	public void editClass(String id, String typeId, String className, String remark) throws ServiceException {
		QuestGuidelineClass qclass = null;
		if(id == null || "".equals(id)){
			qclass = new QuestGuidelineClass();
		}else{
			qclass = questGuidelineClassDao.findByUnique("id", id);
		}
		qclass.setTypeId(typeId);
		qclass.setClassName(className);
		qclass.setRemark(remark);
		questGuidelineClassDao.save(qclass);
	}
	
	public String judgeClassExists(String typeId, String className, String classId){
		return questGuidelineClassDao.judgeClassExists(typeId, className, classId);
	}
	
	public List questSortManagerList() {
		List list = questGuidelineSortDao.questSortManagerList();
		return list;
	}
	
	public String judgeSortExists(String classId, String sortName, String sortId){
		return questGuidelineSortDao.judgeSortExists(classId, sortName, sortId);
	}
	
	public List addSortForm(){
		List qclasses = questGuidelineClassDao.getAllQuestClass();
		return qclasses;
	}
	
	public Map editSortForm(String sortId){
		Map map = new HashMap();
		List qclasses = questGuidelineClassDao.getAllQuestClass();
		QuestGuidelineSort sort = questGuidelineSortDao.findByUnique("id", sortId);
		map.put("qclasses", qclasses);
		map.put("sort", sort);
		return map;
	}
	
	public void editSort(String id, String classId, String sortName, String remark){
		QuestGuidelineSort sort = null;
		if(id == null || "".equals(id)){
			sort = new QuestGuidelineSort();
		}else{
			sort = questGuidelineSortDao.findByUnique("id", id);
		}
		sort.setClassId(classId);
		sort.setSort(sortName);
		sort.setRemark(remark);
		questGuidelineSortDao.save(sort);
	}
	
	public List questItemManagerList() {
		List list = questGuidelineItemDao.questItemManagerList();
		return list;
	}
	
	public String judgeItemExists(String sortId, String itemName, String itemId){
		return questGuidelineItemDao.judgeItemExists(sortId, itemName, itemId);
	}
	
	public List addItemForm(){
		List sorts = questGuidelineSortDao.getAllQuestSort();
		return sorts;
	}
	
	public Map editItemForm(String itemId){
		Map map = new HashMap();
		List sorts = questGuidelineSortDao.getAllQuestSort();
		QuestGuidelineItem item = questGuidelineItemDao.findByUnique("id", itemId);
		List<QuestGradeRule> rules = questGradeRuleDao.findByProperty("itemId", itemId);
		map.put("sorts", sorts);
		map.put("item", item);
		map.put("rules", rules);
		return map;
	}
	
	public void editItem(String id, String sortId, String itemName, String options, String remark){
		QuestGuidelineItem item = null;
		if(id == null || "".equals(id)){
			item = new QuestGuidelineItem();
		}else{
			item = questGuidelineItemDao.findByUnique("id", id);
		}
		item.setSortId(sortId);
		item.setItem(itemName);
		item.setOptions(options);
		item.setRemark(remark);
		item.setWeightVal(0);
		questGuidelineItemDao.save(item);
	}
	
	public String addRuleForm(String itemId, String sortId, String itemName, String options, String remark){
		QuestGuidelineItem item = null;
		if(itemId == null || "".equals(itemId)){
			item = new QuestGuidelineItem();
		}else{
			item = questGuidelineItemDao.findByUnique("id", itemId);
		}
		item.setItem(itemName);
		item.setOptions(options);
		item.setRemark(remark);
		item.setSortId(sortId);
		item.setWeightVal(999);
		QuestGuidelineItem itemSave = questGuidelineItemDao.saveQuestGuidelineItem(item);
		return itemSave.getId();
	}
	
	public List addRule(String itemId, String ruleName, String mark){
		QuestGradeRule rule = new QuestGradeRule();
		rule.setItemId(itemId);
		rule.setGradeExplain(ruleName);
		rule.setMark(Integer.parseInt(mark));
		questGradeRuleDao.save(rule);
		List rules = questGradeRuleDao.findByProperty("itemId", itemId);
		return rules;
	}
	
	public String deleteItemByItemId(String itemId) throws ServiceException {
		//ɾ��ָ��ϸ��
		String flag = questGuidelineItemDao.deleteItemByItemId(itemId);
		if("success".equals(flag)){
			List<QuestGradeRule> list = questGradeRuleDao.findByProperty("itemId", itemId);
			if(list != null && list.size() > 0){
				for(QuestGradeRule rule:list){
					questGradeRuleDao.delete(rule);
				}
			}
		}
		return flag;
	}
	
	public String deleteSortBySortId(String sortId) throws ServiceException {
		String flag = "";
		List<QuestGuidelineItem> items = questGuidelineItemDao.findByProperty("sortId", sortId);
		if(items != null && items.size() > 0){
			for(QuestGuidelineItem item:items){
				String itemId = item.getId();
				flag = deleteItemByItemId(itemId);
				if("failure".equals(flag)){
					break;
				}
			}
		}else{
			flag = "success";
		}
		//ָ��ϸ���ܹ�ɾ����ɾ��ָ�����
		if("success".equals(flag)){
			QuestGuidelineSort sort = questGuidelineSortDao.findByUnique("id", sortId);
			questGuidelineSortDao.delete(sort);
		}
		return flag;
	}
	
	public String deleteClassByClassId(String classId) throws ServiceException {
		String flag = "";
		List<QuestGuidelineSort> sorts = questGuidelineSortDao.findByProperty("classId", classId);
		if(sorts != null && sorts.size() > 0){
			for(QuestGuidelineSort sort:sorts){
				String sortId = sort.getId();
				flag = deleteSortBySortId(sortId);
				if("failure".equals(flag)){
					break;
				}
			}
		}else{
			flag = "success";
		}
		if("success".equals(flag)){
			QuestGuidelineClass qclass = questGuidelineClassDao.findByUnique("id", classId);
			questGuidelineClassDao.delete(qclass);
		}
		return flag;
	}
	
	public String deleteType(String typeId) throws ServiceException {
		String flag = "";
		List<QuestGuidelineClass> classes = questGuidelineClassDao.findByProperty("typeId", typeId);
		if(classes != null && classes.size() > 0){
			for(QuestGuidelineClass cl:classes){
				String classId = cl.getId();
				flag = deleteClassByClassId(classId);
				if("failure".equals(flag)){
					break;
				}
			}
		}else{
			flag = "success";
		}
		if("success".equals(flag)){
			QuestType typeInfo = questTypeDao.findByUnique("id", typeId);
			questTypeDao.delete(typeInfo);
		}
		return flag;
	}
	
	public void addManagerItem(String id, String sortId, String itemName, String options, String remark, String[] ruleAddName){
		QuestGuidelineItem item = null;
		if(id == null || "".equals(id)){
			item = new QuestGuidelineItem();
		}else{
			item = questGuidelineItemDao.findByUnique("id", id);
		}
		item.setSortId(sortId);
		item.setItem(itemName);
		item.setOptions(options);
		item.setRemark(remark);
		item.setWeightVal(0);
		QuestGuidelineItem itemSave = questGuidelineItemDao.saveQuestGuidelineItem(item);
		String itemId = itemSave.getId();
		questGradeRuleDao.saveQuestGradeRule(itemId, ruleAddName);
	}
	
	public List queryRuleListByItemId(String itemId){
		List list = questGradeRuleDao.findByProperty("itemId", itemId);
		return list;
	}
	
	public void deleteManagerRule(String ruleId){
		QuestGradeRule rule = questGradeRuleDao.findByUnique("id", ruleId);
		questGradeRuleDao.delete(rule);
	}
	
	public void addManagerRule(String itemId, String ruleName){
		QuestGradeRule rule = new QuestGradeRule();
		rule.setItemId(itemId);
		rule.setGradeExplain(ruleName);
		rule.setMark(0);
		questGradeRuleDao.save(rule);
	}
	
	public QuestIssue getQuestIssueByIssueId(String issueId){
		QuestIssue issue = questIssueDao.findByUnique("id", issueId);
		return issue;
	}
	
	/**
	 * ͨ���û�ID��ѯ�û����ֻ�����
	 * 
	 * @param userId���û�ID
	 * @return�������û��ֻ�����
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = questIssueDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	/**
	 * ��ʾ���е��ʾ���
	 * @param userInfo���û���Ϣ
	 * @param queryType������
	 * @param existItemIds�����ڵ��ʾ���ID
	 * @return��JsonText
	 */
	public String showAllQuestIssueItem(UserInfo userInfo, String queryType, String existItemIds) {
		String jsonText = "";
		String[] existItemIdArray = null;//����Ѵ��ڵ��ʾ���ID
		if(existItemIds != null && !"".equals(existItemIds)){
			existItemIds = existItemIds.substring(0, existItemIds.length() - 1);
			existItemIdArray = existItemIds.split(",");
		}
		//�����ʾ����ͻ�����е��ʾ����
		List classList = questGuidelineClassDao.getClassByTypeId(queryType);
		List sortList = null;
		List itemList = null;

		JSONObject root = new JSONObject();
		root.put("id", "0");
		JSONObject classNode;//ָ�����ڵ�
		JSONObject sortNode;//ָ�����ڵ�
		JSONObject itemNode;//������ڵ�
		JSONArray classArray;
		JSONArray sortArray;
		JSONArray itemArray;
		classArray = new JSONArray();
		for(int i = 0; classList != null && i < classList.size(); i++){
			QuestGuidelineClass gclass = (QuestGuidelineClass)classList.get(i);
			classNode = new JSONObject();
			classNode.put("id", "C"+gclass.getId());//id�û���̨��õ�����
			classNode.put("text", gclass.getClassName());//text�û�ǰ̨��ʾ����
			sortArray = new JSONArray();
			//��ָ����������е�ָ�����
			sortList = questGuidelineSortDao.getSortListByClassId(gclass.getId());
			for(int j = 0; sortList != null && j < sortList.size(); j++){
				QuestGuidelineSort gsort = (QuestGuidelineSort)sortList.get(j);
				sortNode = new JSONObject();
				sortNode.put("id", "S"+gsort.getId());
				sortNode.put("text", gsort.getSort());
				//��ָ����������еĲ������б�
				itemList = questGuidelineItemDao.getItemListBySortId(gsort.getId());
				itemArray = new JSONArray();
				for(int k = 0; itemList != null && k < itemList.size(); k++){
					QuestGuidelineItem gitem = (QuestGuidelineItem)itemList.get(k);
					itemNode = new JSONObject();
					itemNode.put("id", "I"+gitem.getId());
					itemNode.put("text", gitem.getItem());
					//����Ĭ��ֵ
					if(existItemIdArray != null){
						for(int m = 0; m < existItemIdArray.length; m++){
							if(existItemIdArray[m].equals(gitem.getId())){
								itemNode.put("checked", "true");//����ѡ���Ѵ��ڵĲ�����
								break;
							}
						}
					}
					
					itemArray.add(itemNode);
				}
				sortNode.put("item", itemArray);
				sortArray.add(sortNode);
			}
			classNode.put("item", sortArray);
			classArray.add(classNode);
		}
		root.put("item", classArray);
		if (root != null) {
			jsonText = root.toString();
		}
		return jsonText;
	}
	
	/**
	 * �û�ǰ̨ѡ����ύ��̨��������
	 * @param selectValue��ǰ̨������IDͨ��"~"�ָ�
	 * @return
	 */
	public Map getQuestIssueIds(String selectValue){
		String itemIds = "";
		List<BasicDynaBean> itemList = new ArrayList();
		if(selectValue != null && !"".equals(selectValue)){
			//ͨ��"~"����ǰ̨������������
			String[] selectValueArray = selectValue.split("~");
			for (int i = 0; i < selectValueArray.length; i++) {
				//������в�����IDS
				if(selectValueArray[i].indexOf("I") != -1){
					String itemId = selectValueArray[i].substring(1);
					itemIds += itemId + ",";
					BasicDynaBean bean = questGuidelineItemDao.getItemBeanByItemId(itemId);
					itemList.add(bean);
				}
			}
		}
		if(itemIds != ""){
			itemIds = itemIds.substring(0, itemIds.length() - 1);
		}
		Map map = new HashMap();
		map.put("itemIds", itemIds);
		map.put("itemList", itemList);
		return map;
	}
	
	public List<BasicDynaBean> queryItemBeanListByIssueId(String issueId){
		return questGuidelineItemDao.queryItemBeanListByIssueId(issueId);
	}
	
	/**
	 * �ɲ�ѯ������ѯ�ʾ��б�
	 * @param issueName���ʾ�����
	 * @param beginTime����ʼʱ��
	 * @param endTime������ʱ��
	 * @return���ʾ��б�
	 */
	public List queryIssueByCondition(String issueName, String beginTime, String endTime){
		return questIssueDao.queryIssueByCondition(issueName, beginTime, endTime);
	}
	
	/**
	 * ���ʾ�ID����ʾ����ϸ��Ϣ
	 * @param issueId���ʾ�Id
	 * @return
	 */
	public Map viewQuestIssue(String issueId){
		BasicDynaBean bean = questIssueDao.getIssueByIssueId(issueId);//�ʾ���ϸ
		String compNames = questIssueReviewObjDao.getCompNamesByIssueId(issueId);//�ʾ��������
		List list = questGuidelineItemDao.queryItemListByIssueId(issueId);//���ʾ�������ʾ���
		String conNames = questIssueDao.getConNameByIssueId(issueId);//�ʾ�Ĵ�ά��˾
		Map map = new HashMap();
		map.put("bean", bean);
		map.put("compNames", compNames);
		map.put("list", list);
		map.put("conNames", conNames);
		return map;
	}
}
