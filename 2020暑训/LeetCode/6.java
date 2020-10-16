/*
大模拟
模拟画Z的过程加字符
StringBuilder
*/
class Solution {
	public String convert(String s, int numRows) {
		if(s==null || numRows<=1) {
			return s;
		}
		int n = s.length();
		int rows = Math.min(n,numRows);
		StringBuilder[] arr = new StringBuilder[rows];
		for(int i=0;i<arr.length;++i) {
			arr[i] = new StringBuilder();
		}
		int j = 0;
		boolean isDown = false;
		StringBuilder res = arr[0];
		for(int i=0;i<n;++i) {
			arr[j].append(s.charAt(i));
			if(j==0 || j==numRows-1) {
				isDown = !isDown;
			}
			if(isDown) {
				++j;
			} else {
				--j;
			}
		}
		for(int i=1;i<arr.length;++i) {
			res.append(arr[i]);
		}
		return res.toString();
	}
}