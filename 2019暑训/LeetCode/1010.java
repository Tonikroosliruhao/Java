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
