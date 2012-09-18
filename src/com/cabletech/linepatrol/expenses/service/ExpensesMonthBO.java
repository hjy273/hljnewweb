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
	 * 修改月费用
	 * 
	 * @param id
	 * @param deductionMoney
	 *            扣减费用
	 */
	public void editMonthExpense(String id, String deductionMoney,String remark) {
		double money = Double.parseDouble(deductionMoney);
		ExpenseMonth month = monthDAO.get(id);
		month.setRemark(remark);
		double rectifyMoney = month.getMonthPrice() - money;// 矫正费用
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
	 * 移动查询代维
	 * 
	 * @param deptid
	 * @return
	 */
	public List<Contractor> getConstractorByDeptId(String regionid) {
		return dao.getConstractorByDeptId(regionid);
	}

	/**
	 * 生成管道的费用 
	 * 管道费用计算只跟管道孔公里单价想关联
	 */
	public void createPipeExpense(UserInfo user, String yearmonth,String expenseType){
		String regionid = user.getRegionid();
		List<Contractor> contractors = dao.getConstractorByDeptId(regionid);
		ExpenseMonth month;
		ExpenseGradem eg;
		for (Contractor contra : contractors) {// 按代维统计月费用
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
	 * 生成月费用 代维公司当前交维的维护光缆按照交维日期排序累计光缆长度，
	 *  如果达到1000公里的系数为1，超出部分的系数为0.95，以此类推。
	 * 如果光缆为郊区光缆则按照区县进行计算。交维代维公司维护多个区县；
	 *  分别计算各个区县的维护费用，然后进行求和。
	 * 
	 * @param user
	 * @param month
	 */
	public void createExpense(UserInfo user, String month) {
		String regionid = user.getRegionid();
		List<Contractor> contractors = dao.getConstractorByDeptId(regionid);
		for (Contractor contra : contractors) {// 按代维统计光缆月费用
			List monthExpense = new ArrayList();
			String contractorid = contra.getContractorID();
			//String contractorid = "0000000010";
			//	String contractorid = "0000000009";
			//	String contractorid = "0000000001";
			List<ExpenseGradeFactor> factors = factordao
			.getGradeFactors(contractorid);
			List<RepeaterSection> contraCables = dao.getCablesByContractorid(
					contractorid, month);// 获取代维维护城区光缆信息
			// Map<Object,Map<String,ExpenseGradem>> 最外层map的key为分级取费系数的系数（不是主键），
			// value是一个map，key为维护单价的主键,value即是分级维护费用的对象
			Map<Object, Map<String, ExpenseGradem>> gradems = calcnOneAreaExpense(
					contraCables, factors, contractorid);
			monthExpense.add(gradems);
			List<String> places = dao.getCablePlaces(contractorid,month);//代维下的区县
			for(int i = 0;places!=null && i<places.size();i++){
				String place = places.get(i); 
				List<RepeaterSection> cablesPlaces =
					dao.getCableByPlace(contractorid, month, place);//获取区县下的光缆信息
				Map<Object,Map<String,ExpenseGradem>> g =
					calcnOneAreaExpense(cablesPlaces,factors,contractorid);
				monthExpense.add(g);
			}
			ExpenseMonth expenseMonth = saveExpense(monthExpense, contractorid,
					month);
		}
	}

	/**
	 * 保存月维护费用
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
	 *累加光缆长度与总数与总费用
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
			if (cablesets != null && cablesets.size() > 0) {// 分级维护费用与关联光缆在hibernate相互关联的映射关系
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
		month.setRectifyMoney(monthPrice);// 默认矫正数据与统计一致
		month.setGradems(setgradems);
		return month;

	}

	/**
	 * 合并区县与市区的分级系数维护费用
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
			// 判断集合中是否已经存在相同系数与单价的对象，如果存在相同系数与单价的对象则合并
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
		if (num == 0) {// 集合中没有重复的元素直接加入集合
			setgradems.add(gradem);
		} else {// set迭代时不能做增删操作，因此要把需要删除的增加的保存后，循环结束后操作
			setgradems.remove(removeObject);
			setgradems.add(addObject);
		}
		return setgradems;
	}

	/**
	 * 计算单个区域的费用
	 * 
	 * @param contraCables
	 * @return
	 */
	public Map<Object, Map<String, ExpenseGradem>> calcnOneAreaExpense(
			List<RepeaterSection> contraCables,
			List<ExpenseGradeFactor> factors, String contractorid) {
		Map<Object, Map<String, ExpenseGradem>> map = new HashMap<Object, Map<String, ExpenseGradem>>();
		int seq = 0;// 取系数的索引，从0开始。
		float seqlen = 0;// 当前系数下的长度
		Map<String, ExpenseUnitPrice> pricesMap = pricedao
		.getPricesMap(contractorid);
		ExpenseGradeFactor f = factors.get(seq);
		int grade2 = f.getGrade2();//grade2=0 表示最后一个系数
		Map<String, ExpenseGradem> grademMap = initGrademMap(pricesMap, f);// 初始化第一个系数所需要的分级维护费用的对象
		for (int i = 0; contraCables!=null && i < contraCables.size(); i++) {
			RepeaterSection repeat = contraCables.get(i);
			seqlen += repeat.getGrossLength();
			if (seqlen <= calcnValue(f) && grade2 != 0) {
				initGradem(grademMap, pricesMap, repeat, f);
				if (i == contraCables.size() - 1) {// 没到下一个系数,光缆统计结束,要保存此系数下分解维护费用信息
					map.put(f.getFactor(), grademMap);
				}
			}
			if (seqlen > calcnValue(f) && grade2 != 0) {// 长度进入下一个系数,未到最后一个系数
				map.put(f.getFactor(), grademMap);//保存上个系数的信息
				if (seq == factors.size() - 2) {// 光缆长度超过倒数第二个系数的最大长度，进入最后一个系数
					seq++;
					f = factors.get(seq);
					grademMap = initGrademMap(pricesMap, f);// 初始化最后一个系数的分级维护费用对象
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
	 * 根据当前系数初始化分级维护费用的对象
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
	 * 初始化 分级维护费用
	 * 
	 * @param map
	 *            string:单价主键，分级系数
	 * @param pricesMap
	 *            维护单价
	 * @param repeat
	 *            光缆信息
	 */
	public void initGradem(Map<String, ExpenseGradem> map,
			Map<String, ExpenseUnitPrice> pricesMap, RepeaterSection repeat,
			ExpenseGradeFactor f) {
		ExpenseUnitPrice price;
		String cablelevel = repeat.getCableLevel();
		if (cablelevel.equals("1")) {// 一干
			price = pricesMap.get(cablelevel);// 光缆级别对应的单价
			initExpenseGradem(map, price, repeat, f);
		}
		if (cablelevel.equals("2")) {// 骨干
			price = pricesMap.get(cablelevel);
			initExpenseGradem(map, price, repeat, f);
		}
		if (cablelevel.equals("3")) {// 汇聚
			price = pricesMap.get(cablelevel);
			initExpenseGradem(map, price, repeat, f);
		}
		if (cablelevel.equals("4")) {// 接入
			int coreNum= 0;
			String num = repeat.getCoreNumber();
			if(num!=null){
				coreNum = Integer.parseInt(num);
			}
			if (coreNum <= 144) {
				price = pricesMap.get(cablelevel);
				initExpenseGradem(map, price, repeat, f);
			} else {
				price = pricesMap.get(cablelevel + "A");// 4A表示接入层大于144芯的
				initExpenseGradem(map, price, repeat, f);
			}
		}
	}

	/**
	 * 初始化分级维护费用数据 同一系数的光缆条数，光缆长度
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
	 * 维护长度参数值
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
	 * 查询月维护费用
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
	 * 根据参数年查询改年 已经统计的月维护费用的月份
	 * 
	 * @param month
	 *            月
	 * @return
	 */
	public List getExpenseMonthByYear(String month,String expenseType) {
		String year = month.substring(0, 4);
		return monthDAO.getExpenseMonthByYear(year,expenseType);
	}

	/**
	 * 查询维护费用
	 * 
	 * @param yearmonth
	 * @return
	 */
	public Map getExpenses(UserInfo user, String conid, String yearmonth) {
		String regionid = user.getRegionid();
		Map<String, Map> expenseMap = new HashMap<String, Map>();
		if (conid == null || "".equals(conid)) {// 查询所有代维
			List<Contractor> contrators = dao.getConstractorByDeptId(regionid);
			for (Contractor c : contrators) {
				String contractorid = c.getContractorID();
				String contractorName = c.getContractorName();
				Map map = getExpenseByContractorId(contractorid, yearmonth);
				expenseMap.put(contractorName, map);
			}
		} else {// 只查询一个代维
			Contractor c = dao.getContractorById(conid);
			String contractorName = c.getContractorName();
			Map map = getExpenseByContractorId(conid, yearmonth);
			expenseMap.put(contractorName, map);
		}
		return expenseMap;
	}

	/**
	 * 查询管道维护费用
	 * @param yearmonth
	 * @return
	 */
	public Map getPipeExpenses(UserInfo user, String conid, String yearmonth) {
		String expenseType = ExpenseConstant.EXPENSE_TYPE_PL;
		String regionid = user.getRegionid();
		Map<String,Object> expenseMap = new HashMap<String,Object>();
		if (conid == null || "".equals(conid)) {// 查询所有代维
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
		} else {// 只查询一个代维
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
	 * 根据代维查询每个系数下的所有光缆费用
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
			if (expenses == null || expenses.size() == 0) {// 如果为空说明此系数没有对应光缆，
				// 则重新查询每个光缆所对应的单价.
				expenses = dao.getUnitPrice(contractorid, factor);
			}
			map.put(f.getFactor() + "", expenses);
		}
		List list = dao.getExpenseAmount(contractorid, yearmonth);
		map.put("合计", list);
		map.put("月费用", list);
		return map;
	}

	/**
	 * 维护费用结算
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
		if (conid == null || "".equals(conid)) {// 查询所有代维
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
							calendar.getTime(), "yyyy年MM月"), list);
					calendar.add(GregorianCalendar.MONTH, 1);
				}
				// Double moneySum=dao.getExpensesSum(contractorid, beginMonth,
				// endMonth);
				expenseMap.put(contractorName, monthResultMap);
			}
		} else {// 只查询一个代维
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
				"yyyy年MM月"), list);
				calendar.add(GregorianCalendar.MONTH, 1);
			}
			// Double moneySum=dao.getExpensesSum(conid, beginMonth, endMonth);
			expenseMap.put(contractorName, monthResultMap);
		}
		return expenseMap;
	}

	/**
	 * 查询管道费用结算
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
				"yyyy年MM月"), month);
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
		// 是否存在预算
		String year = beginMonth.substring(0, beginMonth.indexOf("/"));
		List list = budgeExpeseDAO.judgeIsHaveBudget(contractorid, year,
				expenseType, "");
		if (list == null || list.isEmpty()) {
			return "E_F001";
		}
		String budgetId = ((ExpenseBudget) list.get(0)).getId();
		// 是否核实中的每个月都进行了费用生成
		calendar.setTime(beginDate);
		while (calendar.getTime().compareTo(endDate) <= 0) {
			String date = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
			list = monthDAO.getExpenses(date,expenseType);
			if (list == null || list.isEmpty()) {
				return "E_F002";
			}
			calendar.add(GregorianCalendar.MONTH, 1);
		}
		// 是否存在完全相同的费用核实
		list = expenseAffirmDAO.judgeIsHaveAffirm(contractorid, beginMonth,
				endMonth, budgetId, "");
		if (list != null && !list.isEmpty()) {
			return "E_F003";
		}
		// 是否存在交叉的费用核实
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
	 * 获取已经确认的费用
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
	 * 给定一个日期判断该月是否已经付费
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
