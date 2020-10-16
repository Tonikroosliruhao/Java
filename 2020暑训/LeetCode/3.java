class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        int R = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                occ.remove(s.charAt(i - 1));
            }
            while (R + 1 < n && !occ.contains(s.charAt(R + 1))) {
                occ.add(s.charAt(R + 1));
                ++R;
            }
            ans = Math.max(ans, R - i + 1);
        }
        return ans;
    }
}
