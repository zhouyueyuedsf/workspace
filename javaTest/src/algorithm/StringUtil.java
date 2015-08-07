package algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtil {
	/**
	  * 求两个字符串数组的交集
	  * @param max 字符串1
	  * @param min 字符串2
	  * @return  两个字符串的交集
	  */
	 public static String intersect(String max, String min) {
		 if (max.length() < min.length())
			       {
			              String s = max;
			              max = min;
			              min = s;
			          }
			          String subStr = min;
			          for (int begin = 0, end = min.length(), i = 1; !max.contains(subStr); subStr = min.substring(begin, end))
			         {
			              if (end == min.length())
			              {
			                 begin = 0;
			                  end = (min.length()) - (i++);
			              }
			              else
			              {
			                  begin++;
			                  end++;
			             }
			         }
			          return subStr;
	 }
}
