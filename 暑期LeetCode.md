## 暑期LeetCode

1. LeetCode [1010. 总持续时间可被 60 整除的歌曲](https://leetcode-cn.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/) 简单

```java
/*
    模60剩余系。
    注意特殊处理30的情况。
*/
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        int len=time.length;
        int[] a = new int[60];
        int ans=0;
        for(int r : time)  a[r%60]++;
        for(int i=1;i<=29;i++) ans+=a[i]*a[60-i];
        if(a[0]>1){
            ans+=a[0]*(a[0]-1)/2;
        }
        if(a[30]>1){
            ans+=a[30]*(a[30]-1)/2;
        }
        return ans;
    }
}
```



2. LeetCode [1. 两数之和](https://leetcode-cn.com/problems/two-sum/) 简单

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        int temp[] = new int[2];
        for(int i=0;i<n;i++) for(int j=i+1;j<n;j++) {
            if(nums[i]+nums[j]==target) {
                temp[0]=i; temp[1]=j;
            }
        }
        return temp;
    }
}
```



3. LeetCode [42. 接雨水](https://leetcode-cn.com/problems/trapping-rain-water/) 困难

```java
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
```



4. LeetCode [200. 岛屿数量](https://leetcode-cn.com/problems/number-of-islands/) 中等

```java
/*
红果果的dfs题
*/
class Solution {

    void dfs(char[][] grid, int r, int c) {
        int maxI = grid.length;
        int maxJ = grid[0].length;
        if (r < 0 || c < 0 || r >= maxI || c >= maxJ || grid[r][c] == '0') {
            return;
        }
        grid[r][c] = '0';        
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);        
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int maxI = grid.length;
        int maxJ = grid[0].length;
        int num = 0;
        for (int i = 0; i < maxI; i++) {
            for (int j = 0; j < maxJ; j++) {
                if (grid[i][j] == '1') {
                    ++num;
                    dfs(grid, i, j);
                }
            }
        }
        return num;
    }
}
```



5. LeetCode [130. 被围绕的区域](https://leetcode-cn.com/problems/surrounded-regions/) 中等

```java
class Solution {
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return;
        int row = board.length;
        int col = board[0].length;
        for (int j = 0; j < col; j++) {
            if (board[0][j] == 'O') dfs(0, j, board, row, col);
            if (board[row - 1][j] == 'O') dfs(row - 1, j, board, row, col);
        }

        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O') dfs(i, 0, board, row, col);
            if (board[i][col - 1] == 'O') dfs(i, col - 1, board, row, col);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                if (board[i][j] == 'B') board[i][j] = 'O';
            }
        }

    }

    private void dfs(int i, int j, char[][] board, int row, int col) {
        board[i][j] = 'B';
        for (int[] dir : dirs) {
            int tmp_i = dir[0] + i;
            int tmp_j = dir[1] + j;
            if (tmp_i < 0 || tmp_i >= row || tmp_j < 0 || tmp_j >= col || board[tmp_i][tmp_j] != 'O') continue;
            dfs(tmp_i, tmp_j, board, row, col);
        }
    }
}
```



6. LeetCode [11. 盛最多水的容器](https://leetcode-cn.com/problems/container-with-most-water/) 中等

```java
/*
Tow Points
最大化(j-i)*min(height[j],height[i])
每次小的向内收缩，正确性显然。
*/
public class Solution {
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int ans = 0;
        while (l < r) {
            int area = Math.min(height[l], height[r]) * (r - l);
            ans = Math.max(ans, area);
            if (height[l] <= height[r]) {
                ++l;
            }
            else {
                --r;
            }
        }
        return ans;
    }
}
```

 