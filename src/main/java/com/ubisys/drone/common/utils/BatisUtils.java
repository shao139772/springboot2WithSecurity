package com.ubisys.drone.common.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * mybaitis工具类
 * 
 * @author cw
 *
 */
public class BatisUtils {

	/**
	 * 用于判断增删改是否成功(影响行数)
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isSuccess(int num) {
		if (num != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 用于判断是否存在
	 * @param num
	 * @return
	 */
	public static boolean isExist(int num) {
		if (num > 0) {
			return true;
		} else {
			return false;
		}
	}
	

	/**
	 * 根据页面大小和当前页的页码，获取起始行数
	 * 
	 * @param pageNum
	 *            当前页的页码
	 * @param pageSize
	 *            页面大小
	 * @return
	 */
	public static long getStartRow(int pageNum, int pageSize) {
		return (pageNum - 1) * pageSize;
	}

	/**
	 * 根据页面大小和总记录数获取总的页面数
	 * 
	 * @param countRow
	 * @param pageSize
	 * @return
	 */
	public static long getPageCount(long countRow, int pageSize) {
		
		long	pageCount = countRow%pageSize == 0 ? countRow/pageSize : countRow/pageSize + 1;
		return pageCount;
	}
	
	
	
	/**
	 * string 数组  To List
	 * @param ids   String  数组
	 * @return
	 */
	public static List<Integer>  stringArraysToInt(String[] ids){
		List<Integer> ints=new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			ints.add(Integer.valueOf(ids[i]));
		}
		return ints;
	}

	/**
	 * 计算百分比
	 * @param dividend 除数
	 * @param divisor 被除数
	 * @return
	 */
	public static String calculatedPercentage(String dividend, String divisor){
		if(divisor.equals("0")){
			divisor = "1";
		}
		String result="";//接受百分比的值
		double tempresult = Long.parseLong(dividend) / Long.parseLong(divisor);
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		result = numberFormat.format((double) Long.parseLong(dividend) / (double) Long.parseLong(divisor) * 100) + "%";

		System.out.println("num1和num2的百分比为:" + result + "%");
		return result;
	}

	
}
