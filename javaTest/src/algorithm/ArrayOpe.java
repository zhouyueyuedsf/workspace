package algorithm;

import java.util.Arrays;

public class ArrayOpe {
	public static void main(String[] args) {
		Integer[][] bool = { { 0, 1, 0, 1 }, { 1, 1, 1, 1 }, { 1, 1, 0, 0 },
				{ 0, 1, 1, 1 } };
		String result = StringUtil.intersect(Arrays.toString(bool[1]).trim(),
				Arrays.toString(bool[2]).trim());
		System.out.println(result);
		// Integer[][] result = removeSame(bool,1);
	}

	/**
	 * 适用条件:矩阵的行和列相等
	 * 目的:将矩阵移出相同行相同列
	 * @param bool
	 *            矩阵
	 * @param i
	 *            最小值为0
	 * @return 移出后的矩阵
	 */
	@SuppressWarnings("unused")
	private Integer[][] removeSame(Integer[][] bool, int row) {
		int len = bool.length;
		Integer[][] temp = new Integer[len - 1][len - 1];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (i == row || j == row) {
					continue;
				} else if (i < row && j < row) {
					temp[i][j] = bool[i][j];
				} else if (i < row && j > row) {
					temp[i][j - 1] = bool[i][j];
				} else if (i > row && j < row) {
					temp[i - 1][j] = bool[i][j];
				} else if (i > row && j > row) {
					temp[i - 1][j - 1] = bool[i][j];
				}
			}
		}
		return temp;
	}

}
