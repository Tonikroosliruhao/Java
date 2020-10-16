class Solution {
    public int searchInsert(int[] nums, int target) {
        int n = nums.length;
        int L = 0, R = n - 1, ans = n;
        while (L <= R) {
            int mid = ((R - L) / 2) + L;
            if (target <= nums[mid]) {
                ans = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return ans;
    }
}
