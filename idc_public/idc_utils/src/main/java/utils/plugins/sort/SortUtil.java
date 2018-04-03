//package utils.plugins.sort;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.beanutils.BeanComparator;
//import org.apache.commons.collections.ComparatorUtils;
//import org.apache.commons.collections.comparators.ComparableComparator;
//import org.apache.commons.collections.comparators.ComparatorChain;
//
//import utils.plugins.sort.model.SortModel;
///**
// * 单例  安全
// * @author Administrator
// *
// */
//public class SortUtil {
//
//	private static SortUtil instance;
//
//	private SortUtil(){};
//
//	public static synchronized SortUtil getInstance(){
//
//		if(instance == null){
//			instance = new SortUtil();
//		}
//
//		return instance;
//	}
//
//
//
//
//
//	/**
//	 * 对list进行排序
//	 * @param sortList 需要排序的list
//	 * @param param1   排序的参数名称
//	 * @param orderType 排序类型：正序-asc；倒序-desc
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static List sort(List sortList, String param1, String orderType){
//
//		Comparator mycmp1 = ComparableComparator.getInstance ();
//
//
//		if("desc".equals(orderType)){
//			mycmp1 = ComparatorUtils. reversedComparator(mycmp1); //逆序（默认为正序）
//		}
//
//		ArrayList<Object> sortFields = new ArrayList<Object>();
//		sortFields.add( new BeanComparator(param1 , mycmp1)); //主排序（第一排序）
//
//		ComparatorChain multiSort = new ComparatorChain(sortFields);
//		Collections.sort (sortList , multiSort);
//
//		return sortList;
//	}
//
//	/**
//	 * 对list进行排序
//	 * @param sortList 需要排序的list
//	 * @param paramMap   排序的参数名称:参数=orderType【正序-asc，倒序-desc】
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static List sort(List sortList, List<SortModel> sortRule){
//		// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
//		ArrayList<Object> sortFields = new ArrayList<Object>();
//
//		for(SortModel sortModel : sortRule){
//			Comparator comparator = ComparableComparator.getInstance ();
//			if("desc".equalsIgnoreCase(sortModel.getOrderType())){
//				//逆序排列
//				comparator = ComparatorUtils.reversedComparator(comparator); //逆序
//			}
//
//			if (sortModel.getIsNullHighComparator()) {
//				//允许nul
//				comparator = ComparatorUtils. nullHighComparator(comparator); //允许nul
//			}
//
//			sortFields.add( new BeanComparator( sortModel.getName(), comparator)); //主排序（第一排序）
//		}
//		// 创建一个排序链
//		ComparatorChain multiSort = new ComparatorChain(sortFields);
//		// 开始真正的排序，按照先主，后副的规则
//		Collections.sort (sortList , multiSort);
//
//		return sortList;
//	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static List testMapSort(){
//		List sortList = new ArrayList();
//
//		Map map = new HashMap();
//		map.put("name", "1");
//		map.put("age", "1");
//
//		Map map2 = new HashMap();
//		map2.put("name", "2");
//		map2.put("age", "13");
//
//		Map map1 = new HashMap();
//		map1.put("name", "2");
//		map1.put("age", "12");
//
//		List list = new ArrayList();
//		list.add(map);
//		list.add(map1);
//		list.add(map2);
//
//		List<SortModel> sortListParam = new ArrayList<SortModel>();
//		SortModel e = new SortModel();
//		e.setIsNullHighComparator(true);
//		e.setName("name");
//		e.setOrderType("desc");
//
//		SortModel e1 = new SortModel();
//		e1.setIsNullHighComparator(true);
//		e1.setName("age");
//		e1.setOrderType("desc");
//
//		sortListParam.add(e1);
//		sortListParam.add(e);
//		return sort(list, sortListParam);
//	}
//	public static void main(String[] args) {
//		System.out.println(testMapSort());
//	}
//
//}
