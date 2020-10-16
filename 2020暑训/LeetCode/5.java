/*
Manacher忘记了，写dp
dp[i][j]表示i~j这段子串是否回文
*/
class Solution {
    public String longestPalindrome(String s) {
        if(s == null || s.equals("")){
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        int[] result = new int[2];
        for(int i = 0; i<s.length(); i++) dp[i][i] = true;
        for(int i = s.length()-1; i>=0; i--){
            for(int j = i+1; j<s.length(); j++){
                if(s.charAt(i) == s.charAt(j)) {
                        if(j-i == 1){
                            dp[i][j] = true;
                        }
                        else{
                            dp[i][j] = dp[i+1][j-1]; 
                        }
                }else{
                    dp[i][j] = false;
                }
                if(dp[i][j]){
                        if(result[1]-result[0] <= j - i){
                            result[0] = i;
                            result[1] = j;
                        }
                }
            }
        }
        return s.substring(result[0],result[1]+1);
        
    }
}
