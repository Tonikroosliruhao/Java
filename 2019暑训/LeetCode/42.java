/*
单调栈
维护递减序列，当遇到大于栈顶的元素时更新答案
栈顶元素和当前元素相等的情况处理一下
*/
public class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<Integer>();
        int ans = 0,i = 0;
        int flow = 0;
        for (i = 0; i < height.length; i++) {
            while(!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int preNumber = stack.pop();
                while (!stack.isEmpty() && height[stack.peek()] == height[preNumber]) {
                    stack.pop();
                }
                if (!stack.isEmpty()) {
                    int stackTop = stack.peek();
                    ans += (Math.min(height[stackTop], height[i]) - height[preNumber]) * (i - stackTop - 1);
                }
            }
            stack.add(i);
        }
        return ans;
    }
}
