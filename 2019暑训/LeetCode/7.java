class Solution {
    public int reverse(int x) {
        int res = 0 ;
        while(x != 0){
            int temp = x % 10 + res * 10;
            if((temp - x % 10) / 10 != res){ 
                return 0 ;
            }
            res = temp ;
            x /= 10 ;
        }
        return res ;
    }
}