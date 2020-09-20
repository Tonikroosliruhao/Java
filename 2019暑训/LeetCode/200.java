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
