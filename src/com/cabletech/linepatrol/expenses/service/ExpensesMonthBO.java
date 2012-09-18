package com.cabletech.linepatrol.expenses.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.expenses.beans.ExpenseAffirmBean;
import com.cabletech.linepatrol.expenses.dao.BudgeExpeseDAO;
import com.cabletech.linepatrol.expenses.dao.ExpenseAffirmDAO;
import com.cabletech.linepatrol.expenses.dao.ExpenseCabelDAO;
import com.cabletech.linepatrol.expenses.dao.ExpenseGradeFactorDAO;
import com.cabletech.linepatrol.expenses.dao.ExpenseGradmDAO;
import com.cabletech.linepatrol.expenses.dao.ExpenseMonthDAO;
import com.cabletech.linepatrol.expenses.dao.ExpenseUnitPriceDAO;
import com.cabletech.linepatrol.expenses.dao.ExpensesDAO;
import com.cabletech.linepatrol.expenses.model.ExpenseAffirm;
import com.cabletech.linepatrol.expenses.model.ExpenseBudget;
import com.cabletech.linepatrol.expenses.model.ExpenseCable;
import com.cabletech.linepatrol.expenses.model.ExpenseGradeFactor;
import com.cabletech.linepatrol.expenses.model.ExpenseGradem;
import com.cabletech.linepatrol.expenses.model.ExpenseMonth;
import com.cabletech.linepatrol.expenses.model.ExpenseUnitPrice;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

@Service
@Transactional
public class ExpensesMonthBO {
	@Resource(name = "expensesDAO")
	private ExpensesDAO dao;
	@Resource(name = "expenseUnitPriceDAO")
	private ExpenseUnitPriceDAO pricedao;
	@Resource(name = "expenseGradeFactorDAO")
	private ExpenseGradeFactorDAO factordao;
	@Resource(name = "expenseGradmDAO")
	private ExpenseGradmDAO gradmDAO;
	@Resource(name = "expenseMonthDAO")
	private ExpenseMonthDAO monthDAO;
	@Resource(name = "expenseCabelDAO")
	private ExpenseCabelDAO expenseCableDAO;
	@Resource(name = "budgeExpeseDAO")
	private BudgeExpeseDAO budgeExpeseDAO;
	@Resource(name = "expenseAffirmDAO")
	private ExpenseAffirmDAO expenseAffirmDAO;

	/**
	 * �޸��·���
	 * 
	 * @param id
	 * @param deductionMoney
	 *            �ۼ�����
	 */
	public void editMonthExpense(String id, String deductionMoney,String remark) {
		double money = Double.parseDouble(deductionMoney);
		ExpenseMonth month = monthDAO.get(id);
		month.setRemark(remark);
		double rectifyMoney = month.getMonthPrice() - money;// ��������
		month.setDeductionMoney(money);
		month.setRectifyMoney(rectifyMoney);
		monthDAO.updateExpenseMonth(month);
	}

	public List<ExpenseGradeFactor> getGradeFactors(String contractorid) {
		return factordao.getGradeFactors(contractorid);
	}

	public List<ExpenseUnitPrice> getPrices(String contractorid,String expenseType) {
		return pricedao.getPrices(contractorid,expenseType);
	}

	/**
	 * �ƶ���ѯ��ά
	 * 
	 * @param deptid
	 * @return
	 */
	public List<Contractor> getConstractorByDeptId(String regionid) {
		return dao.getConstractorByDeptId(regionid);
	}

	/**
	 * ���ɹܵ��ķ��� 
	 * �ܵ����ü���ֻ���ܵ��׹��ﵥ�������
	 */
	public void createPipeExpense(UserInfo user, String yearmonth,String expenseType){
		String regionid = user.getRegionid();
		List<Contractor> contractors = dao.getConstractorByDeptId(regionid);
		ExpenseMonth month;
		ExpenseGradem eg;
		for (Contractor contra : contractors) {// ����άͳ���·���
			String contractorid = contra.getContractorID();
			double pipelength = dao.getPipeLength(contractorid, yearmonth);
			List<Pipe> pipes = dao.getPipe(contractorid, yearmonth);
			int pipenum = dao.getPipeNum(contractorid, yearmonth);
			List<ExpenseUnitPrice> prices = pricedao.getPrices(contractorid, expenseType);
			if(prices!=null && prices.size()>0){
				Set<ExpenseGradem> setgradems = new HashSet<ExpenseGradem>();
				Set<ExpenseCable> expenseCables = new HashSet<ExpenseCable>();
				month = new ExpenseMonth();
				ExpenseUnitPrice price = prices.get(0);
				double unitprice = price.getUnitPrice();
				eg = new ExpenseGradem();
				for(Pipe pipe:pipes){
					ExpenseCable cable = new ExpenseCable();
					cable.setCableId(pipe.getId());
					cable.setExpenseGradem(eg);
					expenseCables.add(cable);
				}
				eg.setMaintenanceLength(pipelength);
				eg.setMaintenanceNum(pipenum);
				eg.setUnitPrice(unitprice);
				eg.setUnitPriceId(price.getId());
				eg.setExpenseMonth(month);
				eg.setExpenseCables(expenseCables);
				setgradems.add(eg);
				month.setCableLength(pipelength);
				month.setGradems(setgradems);
				month.setCableNum(pipenum);
				month.setContractorId(contractorid);
				month.setExpenseType(ExpenseConstant.EXPENSE_TYPE_PL);
				month.setMonthPrice(pipelength*unitprice);
				month.setRectifyMoney(pipelength*unitprice);
				month.setYearmonth(DateUtil.parseDate(yearmonth, "yyyy/MM"));
				monthDAO.save(month);
			}
		}
	}



	/**
	 * 
	 * �����·��� ��ά��˾��ǰ��ά��ά�����°��ս�ά���������ۼƹ��³��ȣ�
	 *  ����ﵽ1000�����ϵ��Ϊ1���������ֵ�ϵ��Ϊ0.95���Դ����ơ�
	 * �������Ϊ���������������ؽ��м��㡣��ά��ά��˾ά��������أ�
	 *  �ֱ����������ص�ά�����ã�Ȼ�������͡�
	 * 
	 * @param user
	 * @param month
	 */
	public void createExpense(UserInfo user, String month) {
		String regionid = user.getRegionid();
		List<Contractor> contractors = dao.getConstractorByDeptId(regionid);
		for (Contractor contra : contractors) {// ����άͳ�ƹ����·���
			List monthExpense = new ArrayList();
			String contractorid = contra.getContractorID();
			//String contractorid = "0000000010";
			//	String contractorid = "0000000009";
			//	String contractorid = "0000000001";
			List<ExpenseGradeFactor> factors = factordao
			.getGradeFactors(contractorid);
			List<RepeaterSection> contraCables = dao.getCablesByContractorid(
					contractorid, month);// ��ȡ��άά������������Ϣ
			// Map<Object,Map<String,ExpenseGradem>> �����map��keyΪ�ּ�ȡ��ϵ����ϵ����������������
			// value��һ��map��keyΪά�����۵�����,value���Ƿּ�ά�����õĶ���
			Map<Object, Map<String, ExpenseGradem>> gradems = calcnOneAreaExpense(
					contraCables, factors, contractorid);
			monthExpense.add(gradems);
			List<String> places = dao.getCablePlaces(contractorid,month);//��ά�µ�����
			for(int i = 0;places!=null && i<places.size();i++){
				String place = places.get(i); 
				List<RepeaterSection> cablesPlaces =
					dao.getCableByPlace(contractorid, month, place);//��ȡ�����µĹ�����Ϣ
				Map<Object,Map<String,ExpenseGradem>> g =
					calcnOneAreaExpense(cablesPlaces,factors,contractorid);
				monthExpense.add(g);
			}
			ExpenseMonth expenseMonth = saveExpense(monthExpense, contractorid,
					month);
		}
	}

	/**
	 * ������ά������
	 * 
	 * @param gradems
	 */
	public ExpenseMonth saveExpense(List gradems, String contractorid,
			String yearmonth) {
		ExpenseMonth month = new ExpenseMonth();
		month.setContractorId(contractorid);
		month.setYearmonth(DateUtil.parseDate(yearmonth, "yyyy/MM"));
		Set<ExpenseGradem> setgradems = new HashSet<ExpenseGradem>();
		for (int i = 0; gradems != null && i < gradems.size(); i++) {
			Map<Object, Map<String, ExpenseGradem>> expenseGradems = (Map<Object, Map<String, ExpenseGradem>>) gradems
			.get(i);
			if (expenseGradems != null && expenseGradems.size() > 0) {
				Set set = expenseGradems.keySet();
				Iterator iteartor = set.iterator();
				while (iteartor.hasNext()) {
					Object key = iteartor.next();
					Map<String, ExpenseGradem> expendseGradem = expenseGradems
					.get(key);
					Set setgradem = expendseGradem.keySet();
					Iterator ite = setgradem.iterator();
					while (ite.hasNext()) {
						Object k = ite.next();
						ExpenseGradem eg = expendseGradem.get(k);
						eg.setExpenseMonth(month);
						String factorid = eg.getGradeFactorId();
						if (factorid == null) {
							eg.setGradeFactorId("0");
						}
						if (setgradems.size() == 0) {
							setgradems.add(eg);
						} else {
							setgradems = mergeAreaExpenseGradem(setgradems, eg);
						}
					}
				}
			}
		}
		month = getCableLengthAndNumAndPrice(setgradems, month);
		month.setExpenseType(ExpenseConstant.EXPENSE_TYPE_CABLE);
		monthDAO.save(month);
		return month;
	}

	/**
	 *�ۼӹ��³������������ܷ���
	 * @param setgradems
	 * @param month
	 * @return
	 */
	public ExpenseMonth getCableLengthAndNumAndPrice(
			Set<ExpenseGradem> setgradems, ExpenseMonth month) {
		double cableLength = 0;
		int cableNum = 0;
		double monthPrice = 0;
		for (ExpenseGradem eg : setgradems) {
			ExpenseGradeFactor f = factordao.getGradeFactor(eg.getGradeFactorId());
			ExpenseUnitPrice p = pricedao.getUnitPriceById(eg.getUnitPriceId());
			Set<ExpenseCable> cablesets = eg.getExpenseCables();
			if (cablesets != null && cablesets.size() > 0) {// �ּ�ά�����������������hibernate�໥������ӳ���ϵ
				for (Iterator ite = cablesets.iterator(); ite.hasNext();) {
					ExpenseCable cable = (ExpenseCable) ite.next();
					cable.setExpenseGradem(eg);
				}
			}
			eg.setUnitPrice(f.getFactor()*p.getUnitPrice());
			cableNum += eg.getMaintenanceNum();
			cableLength += eg.getMaintenanceLength();
			monthPrice += sumPrice(eg.getUnitPrice(),
					eg.getMaintenanceLength(),1);
		}
		month.setCableLength(cableLength);
		month.setCableNum(cableNum);
		month.setMonthPrice(monthPrice);
		month.setRectifyMoney(monthPrice);// Ĭ�Ͻ���������ͳ��һ��
		month.setGradems(setgradems);
		return month;

	}

	/**
	 * �ϲ������������ķּ�ϵ��ά������
	 * @param setgradems
	 */
	public Set<ExpenseGradem> mergeAreaExpenseGradem(
			Set<ExpenseGradem> setgradems, ExpenseGradem gradem) {
		String factorID = gradem.getGradeFactorId();
		String priceID = gradem.getUnitPriceId();
		Set<ExpenseCable> cableSets = gradem.getExpenseCables();
		ExpenseGradem removeObject = null;
		ExpenseGradem addObject = null;
		int num = 0;
		for (Iterator it = setgradems.iterator(); it.hasNext();) {
			ExpenseGradem eg = (ExpenseGradem) it.next();
			String factorid = eg.getGradeFactorId();
			String priceid = eg.getUnitPriceId();
			// �жϼ������Ƿ��Ѿ�������ͬϵ���뵥�۵Ķ������������ͬϵ���뵥�۵Ķ�����ϲ�
			if (factorID.equals(factorid) && priceID.equals(priceid)) {
				gradem.setMaintenanceLength(eg.getMaintenanceLength()
						+ gradem.getMaintenanceLength());
				gradem.setMaintenanceNum(eg.getMaintenanceNum()
						+ gradem.getMaintenanceNum());
				Set<ExpenseCable> cables = eg.getExpenseCables();
				cableSets.addAll(cables);
				gradem.setExpenseCables(cableSets);
				removeObject = eg;
				addObject = gradem;
				num++;
			}
		}
		if (num == 0) {// ������û���ظ���Ԫ��ֱ�Ӽ��뼯��
			setgradems.add(gradem);
		} else {// set����ʱ��������ɾ���������Ҫ����Ҫɾ�������ӵı����ѭ�����������
			setgradems.remove(removeObject);
			setgradems.add(addObject);
		}
		return setgradems;
	}

	/**
	 * ���㵥������ķ���
	 * 
	 * @param contraCables
	 * @return
	 */
	public Map<Object, Map<String, ExpenseGradem>> calcnOneAreaExpense(
			List<RepeaterSection> contraCables,
			List<ExpenseGradeFactor> factors, String contractorid) {
		Map<Object, Map<String, ExpenseGradem>> map = new HashMap<Object, Map<String, ExpenseGradem>>();
		int seq = 0;// ȡϵ������������0��ʼ��
		float seqlen = 0;// ��ǰϵ���µĳ���
		Map<String, ExpenseUnitPrice> pricesMap = pricedao
		.getPricesMap(contractorid);
		ExpenseGradeFactor f = factors.get(seq);
		int grade2 = f.getGrade2();//grade2=0 ��ʾ���һ��ϵ��
		Map<String, ExpenseGradem> grademMap = initGrademMap(pricesMap, f);// ��ʼ����һ��ϵ������Ҫ�ķּ�ά�����õĶ���
		for (int i = 0; contraCables!=null && i < contraCables.size(); i++) {
			RepeaterSection repeat = contraCables.get(i);
			seqlen += repeat.getGrossLength();
			if (seqlen <= calcnValue(f) && grade2 != 0) {
				initGradem(grademMap, pricesMap, repeat, f);
				if (i == contraCables.size() - 1) {// û����һ��ϵ��,����ͳ�ƽ���,Ҫ�����ϵ���·ֽ�ά��������Ϣ
					map.put(f.getFactor(), grademMap);
				}
			}
			if (seqlen > calcnValue(f) && grade2 != 0) {// ���Ƚ�����һ��ϵ��,δ�����һ��ϵ��
				map.put(f.getFactor(), grademMap);//�����ϸ�ϵ������Ϣ
				if (seq == factors.size() - 2) {// ���³��ȳ��������ڶ���ϵ������󳤶ȣ��������һ��ϵ��
					seq++;
					f = factors.get(seq);
					grademMap = initGrademMap(pricesMap, f);// ��ʼ�����һ��ϵ���ķּ�ά�����ö���
					grade2 = f.getGrade2();
				}else{
					seq++;
					f = factors.get(seq);
					grade2 = f.getGrade2();
					grademMap = initGrademMap(pricesMap, f);
					initGradem(grademMap, pricesMap, repeat, f);
				}
				seqlen = repeat.getGrossLength();
			}
			if (grade2 == 0) {
				initGradem(grademMap, pricesMap, repeat, f);
				if (i == contraCables.size() - 1) {
					map.put(f.getFactor(), grademMap);
				}
			}
		}
		return map;
	}

	/**
	 * ���ݵ�ǰϵ����ʼ���ּ�ά�����õĶ���
	 * 
	 * @return
	 */
	public Map<String, ExpenseGradem> initGrademMap(
			Map<String, ExpenseUnitPrice> pricesMap, ExpenseGradeFactor factor) {
		String gradeFactorId = factor.getId();
		Map<String, ExpenseGradem> grademMap = new HashMap<String, ExpenseGradem>();
		Set set = pricesMap.keySet();
		Iterator ite = set.iterator();
		while (ite.hasNext()) {
			Object key = ite.next();
			ExpenseUnitPrice price = pricesMap.get(key);
			ExpenseGradem eg = new ExpenseGradem();
			eg.setGradeFactorId(gradeFactorId);
			eg.setUnitPriceId(price.getId());
			eg.setUnitPrice(price.getUnitPrice()*factor.getFactor());
			eg.setMaintenanceLength(Double.parseDouble("0"));
			eg.setMaintenanceNum(0);
			eg.setExpenseCables(new HashSet<ExpenseCable>());
			grademMap.put(price.getId(), eg);
		}
		return grademMap;
	}

	/**
	 * ��ʼ�� �ּ�ά������
	 * 
	 * @param map
	 *            string:�����������ּ�ϵ��
	 * @param pricesMap
	 *            ά������
	 * @param repeat
	 *            ������Ϣ
	 */
	public void initGradem(Map<String, ExpenseGradem> map,
			Map<String, ExpenseUnitPrice> pricesMap, RepeaterSection repeat,
			ExpenseGradeFactor f) {
		ExpenseUnitPrice price;
		String cablelevel = repeat.getCableLevel();
		if (cablelevel.equals("1")) {// һ��
			price = pricesMap.get(cablelevel);// ���¼����Ӧ�ĵ���
			initExpenseGradem(map, price, repeat, f);
		}
		if (cablelevel.equals("2")) {// �Ǹ�
			price = pricesMap.get(cablelevel);
			initExpenseGradem(map, price, repeat, f);
		}
		if (cablelevel.equals("3")) {// ���
			price = pricesMap.get(cablelevel);
			initExpenseGradem(map, price, repeat, f);
		}
		if (cablelevel.equals("4")) {// ����
			int coreNum= 0;
			String num = repeat.getCoreNumber();
			if(num!=null){
				coreNum = Integer.parseInt(num);
			}
			if (coreNum <= 144) {
				price = pricesMap.get(cablelevel);
				initExpenseGradem(map, price, repeat, f);
			} else {
				price = pricesMap.get(cablelevel + "A");// 4A��ʾ��������144о��
				initExpenseGradem(map, price, repeat, f);
			}
		}
	}

	/**
	 * ��ʼ���ּ�ά���������� ͬһϵ���Ĺ������������³���
	 * 
	 * @param exp
	 * @param repeat
	 */
	public void initExpenseGradem(Map<String, ExpenseGradem> map,
			ExpenseUnitPrice price, RepeaterSection repeat, ExpenseGradeFactor f) {
		ExpenseGradem exp = map.get(price.getId());
		ExpenseCable cable = new ExpenseCable();
		map.get(price.getId()).setGradeFactorId(f.getId());
		map.get(price.getId()).setMaintenanceLength(
				exp.getMaintenanceLength() + repeat.getGrossLength());
		map.get(price.getId()).setMaintenanceNum(exp.getMaintenanceNum() + 1);
		map.get(price.getId()).getExpenseCables().add(cable);
		cable.setCableId(repeat.getKid());
	}

	/**
	 * ά�����Ȳ���ֵ
	 * 
	 * @param factor
	 * @return
	 */
	public int calcnValue(ExpenseGradeFactor factor) {
		int grade1 = factor.getGrade1();
		int grade2 = factor.getGrade2();
		// int grade = Math.abs(grade2-grade1);
		return grade2 - grade1;
	}

	public double sumPrice(double price, double len, double factor) {
		return price * len * factor;
	}

	/**
	 * ��ѯ��ά������
	 * 
	 * @param month
	 * @return
	 */
	public List getMonthExpenses(String month,String expenseType) {
		return monthDAO.getExpenses(month,expenseType);
	}

	public ExpenseMonth getExpenseMonthById(String id) {
		return monthDAO.get(id);
	}

	/**
	 * ���ݲ������ѯ���� �Ѿ�ͳ�Ƶ���ά�����õ��·�
	 * 
	 * @param month
	 *            ��
	 * @return
	 */
	public List getExpenseMonthByYear(String month,String expenseType) {
		String year = month.substring(0, 4);
		return monthDAO.getExpenseMonthByYear(year,expenseType);
	}

	/**
	 * ��ѯά������
	 * 
	 * @param yearmonth
	 * @return
	 */
	public Map getExpenses(UserInfo user, String conid, String yearmonth) {
		String regionid = user.getRegionid();
		Map<String, Map> expenseMap = new HashMap<String, Map>();
		if (conid == null || "".equals(conid)) {// ��ѯ���д�ά
			List<Contractor> contrators = dao.getConstractorByDeptId(regionid);
			for (Contractor c : contrators) {
				String contractorid = c.getContractorID();
				String contractorName = c.getContractorName();
				Map map = getExpenseByContractorId(contractorid, yearmonth);
				expenseMap.put(contractorName, map);
			}
		} else {// ֻ��ѯһ����ά
			Contractor c = dao.getContractorById(conid);
			String contractorName = c.getContractorName();
			Map map = getExpenseByContractorId(conid, yearmonth);
			expenseMap.put(contractorName, map);
		}
		return expenseMap;
	}

	/**
	 * ��ѯ�ܵ�ά������
	 * @param yearmonth
	 * @return
	 */
	public Map getPipeExpenses(UserInfo user, String conid, String yearmonth) {
		String expenseType = ExpenseConstant.EXPENSE_TYPE_PL;
		String regionid = user.getRegionid();
		Map<String,Object> expenseMap = new HashMap<String,Object>();
		if (conid == null || "".equals(conid)) {// ��ѯ���д�ά
			List<Contractor> contrators = dao.getConstractorByDeptId(regionid);
			for (Contractor c : contrators) {
				String contractorid = c.getContractorID();
				String contractorName = c.getContractorName();
				List<Object> months = monthDAO.getExpenseMonth(yearmonth, contractorid, expenseType);
				if(months!=null && months.size()>0){
					Object month = months.get(0);
					expenseMap.put(contractorName, month);
				}
			}
		} else {// ֻ��ѯһ����ά
			Contractor c = dao.getContractorById(conid);
			String cid = c.getContractorID();
			String contractorName = c.getContractorName();
			List<Object> months = monthDAO.getExpenseMonth(yearmonth, cid, expenseType);
			if(months!=null && months.size()>0){
				Object month = months.get(0);
				expenseMap.put(contractorName, month);
			}
		}
		return expenseMap;
	}


	/**
	 * ���ݴ�ά��ѯÿ��ϵ���µ����й��·���
	 * 
	 * @param contractorid
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public Map getExpenseByContractorId(String contractorid, String yearmonth) {
		Map<String, List> map = new LinkedHashMap<String, List>();
		List<ExpenseGradeFactor> factors = factordao
		.getGradeFactors(contractorid);
		for (ExpenseGradeFactor f : factors) {
			String factorid = f.getId();
			double factor = f.getFactor();
			List expenses = dao.getExpensesByFactorid(contractorid,factorid, yearmonth);
			if (expenses == null || expenses.size() == 0) {// ���Ϊ��˵����ϵ��û�ж�Ӧ���£�
				// �����²�ѯÿ����������Ӧ�ĵ���.
				expenses = dao.getUnitPrice(contractorid, factor);
			}
			map.put(f.getFactor() + "", expenses);
		}
		List list = dao.getExpenseAmount(contractorid, yearmonth);
		map.put("�ϼ�", list);
		map.put("�·���", list);
		return map;
	}

	/**
	 * ά�����ý���
	 * 
	 * @param yearmonth
	 * @return
	 */
	public Map getSettlementEexpenses(UserInfo user, String conid,
			String beginMonth, String endMonth) {
		String regionid = user.getRegionid();
		Date beginDate = DateUtil
		.Str2UtilDate(beginMonth + "/01", "yyyy/MM/dd");
		Date endDate = DateUtil.Str2UtilDate(endMonth + "/01", "yyyy/MM/dd");
		Calendar calendar = GregorianCalendar.getInstance();
		Map<String, Map> expenseMap = new HashMap<String, Map>();
		Map<String, List> monthResultMap = new LinkedHashMap<String, List>();
		if (conid == null || "".equals(conid)) {// ��ѯ���д�ά
			List<Contractor> contrators = dao.getConstractorByDeptId(regionid);
			for (Contractor c : contrators) {
				String contractorid = c.getContractorID();
				String contractorName = c.getContractorName();
				calendar.setTime(beginDate);
				monthResultMap = new LinkedHashMap<String, List>();
				while (calendar.getTime().compareTo(endDate) <= 0) {
					String date = DateUtil.DateToString(calendar.getTime(),
					"yyyy/MM");
					//List list = dao.getExpenses(contractorid, date, date);
					List list  = dao.getExpenseAmount(contractorid, date);
					monthResultMap.put(DateUtil.DateToString(
							calendar.getTime(), "yyyy��MM��"), list);
					calendar.add(GregorianCalendar.MONTH, 1);
				}
				// Double moneySum=dao.getExpensesSum(contractorid, beginMonth,
				// endMonth);
				expenseMap.put(contractorName, monthResultMap);
			}
		} else {// ֻ��ѯһ����ά
			Contractor c = dao.getContractorById(conid);
			String contractorName = c.getContractorName();
			monthResultMap = new LinkedHashMap<String, List>();
			calendar.setTime(beginDate);
			while (calendar.getTime().compareTo(endDate) <= 0) {
				String date = DateUtil.DateToString(calendar.getTime(),
				"yyyy/MM");
				//List list = dao.getExpenses(conid, date, date);
				List list  = dao.getExpenseAmount(conid, date);
				monthResultMap.put(DateUtil.DateToString(calendar.getTime(),
				"yyyy��MM��"), list);
				calendar.add(GregorianCalendar.MONTH, 1);
			}
			// Double moneySum=dao.getExpensesSum(conid, beginMonth, endMonth);
			expenseMap.put(contractorName, monthResultMap);
		}
		return expenseMap;
	}

	/**
	 * ��ѯ�ܵ����ý���
	 * @param yearmonth
	 * @return
	 */
	public Map getPipeSettlementEexpenses(UserInfo user, String conid,
			String beginMonth, String endMonth) {
		Date beginDate = DateUtil
		.Str2UtilDate(beginMonth + "/01", "yyyy/MM/dd");
		Date endDate = DateUtil.Str2UtilDate(endMonth + "/01", "yyyy/MM/dd");
		Calendar calendar = GregorianCalendar.getInstance();
		Map<String, Object> expenseMap = new HashMap<String, Object>();
		calendar.setTime(beginDate);
		while(calendar.getTime().compareTo(endDate) <= 0) {
			String date = DateUtil.DateToString(calendar.getTime(),
			"yyyy/MM");
			List<Object> months = monthDAO.getExpenseMonth(date, conid,"1");
			if(months!=null && months.size()>0){
				Object month = months.get(0);
				expenseMap.put(DateUtil.DateToString(calendar.getTime(),
				"yyyy��MM��"), month);
			}
			calendar.add(GregorianCalendar.MONTH, 1);
		}
		return expenseMap;
	}


	public String getBudgetId(String contractorid, String beginMonth,
			String expenseType) {
		String year = beginMonth.substring(0, beginMonth.indexOf("/"));
		List<ExpenseBudget> list = budgeExpeseDAO.judgeIsHaveBudget(
				contractorid, year, expenseType, "");
		if (list != null && !list.isEmpty()) {
			return list.get(0).getId();
		}
		return "";
	}

	public Double getExpensesSum(String contractorid, String beginMonth,
			String endMonth, String expenseType) {
		Double moneySum = dao.getExpensesSum(contractorid, beginMonth,
				endMonth, expenseType);
		return moneySum;
	}

	public String judgeIsSettled(String contractorid, String beginMonth,
			String endMonth, String expenseType) {
		// TODO Auto-generated method stub
		Date beginDate = DateUtil
		.Str2UtilDate(beginMonth + "/01", "yyyy/MM/dd");
		Date endDate = DateUtil.Str2UtilDate(endMonth + "/01", "yyyy/MM/dd");
		Calendar calendar = GregorianCalendar.getInstance();
		// �Ƿ����Ԥ��
		String year = beginMonth.substring(0, beginMonth.indexOf("/"));
		List list = budgeExpeseDAO.judgeIsHaveBudget(contractorid, year,
				expenseType, "");
		if (list == null || list.isEmpty()) {
			return "E_F001";
		}
		String budgetId = ((ExpenseBudget) list.get(0)).getId();
		// �Ƿ��ʵ�е�ÿ���¶������˷�������
		calendar.setTime(beginDate);
		while (calendar.getTime().compareTo(endDate) <= 0) {
			String date = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
			list = monthDAO.getExpenses(date,expenseType);
			if (list == null || list.isEmpty()) {
				return "E_F002";
			}
			calendar.add(GregorianCalendar.MONTH, 1);
		}
		// �Ƿ������ȫ��ͬ�ķ��ú�ʵ
		list = expenseAffirmDAO.judgeIsHaveAffirm(contractorid, beginMonth,
				endMonth, budgetId, "");
		if (list != null && !list.isEmpty()) {
			return "E_F003";
		}
		// �Ƿ���ڽ���ķ��ú�ʵ
		list = expenseAffirmDAO.judgeIsExistSameMonthAffirm(contractorid,
				beginMonth, endMonth, budgetId);
		if (list != null && !list.isEmpty()) {
			return "E_F004";
		}
		return "E_S";
	}

	public void saveExpenseAffirm(ExpenseAffirmBean bean, UserInfo user) {
		// TODO Auto-generated method stub
		ExpenseAffirm affirm = new ExpenseAffirm();
		BeanUtil.copyProperties(bean, affirm);
		expenseAffirmDAO.save(affirm);
		ExpenseBudget expenseBudget = budgeExpeseDAO.get(bean.getBudgetId());
		Double payMoney = expenseBudget.getPayMoney();
		double payNewMoney = payMoney.doubleValue()
		+ affirm.getBalancePrice().doubleValue();
		payMoney = new Double(payNewMoney);
		expenseBudget.setPayMoney(payMoney);
		budgeExpeseDAO.save(expenseBudget);
	}

	/**
	 * ��ȡ�Ѿ�ȷ�ϵķ���
	 * @param expensetype
	 * @return
	 */
	public List getExpenseAffrims(){
		return expenseAffirmDAO.getExpenseAffrims();
	}

	public ExpenseAffirm getAffirm(String id){
		return expenseAffirmDAO.getAffirm(id);
	}

	public ExpenseBudget getExpenseBudget(String id){
		return budgeExpeseDAO.get(id);
	}

	/**
	 * ����һ�������жϸ����Ƿ��Ѿ�����
	 * @param contractorid
	 * @param yearmonth
	 * @return
	 */
	public boolean judgeIsExistAffirm(String contractorid,
			String yearmonth) {
		List<ExpenseAffirm> affrim = expenseAffirmDAO.judgeIsExistAffirm(contractorid, yearmonth);
		if(affrim!=null && affrim.size()>0){
			return true;
		}
		return false;
	}

	public Contractor getContractorById(String contractorID){
		return dao.getContractorById(contractorID);
	}

}
