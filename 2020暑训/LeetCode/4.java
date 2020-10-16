/*
直接排序复杂度nlogn，没有用到序列有序这个条件
问题可以转化为两个序列分别画出分界线
使分界线左边所有元素严格小于等于分界线右边所有元素
并且分界线左右两边所有元素的个数相同或差1
之后中位数只与分界线两侧有关，这就用到了有序的条件
二分第一个区间，计算第二个区间，找到分界线。
使
*/
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0) nums1 = nums2; 
        if (nums2.length == 0) nums2 = nums1; 
        if (nums1.length == 1 && nums2.length == 1) return (nums1[0] + nums2[0]) /2.0; 
        
        int len = nums1.length + nums2.length; 
        int mid = len / 2 + 1,i = 0, j = 0;
        int[] ary = new int[2]; 

        while(i < nums1.length && j < nums2.length){ 
            if(nums1[i] < nums2[j]) ary[(i + j )%2] = nums1[i++]; 
            else ary[(i + j)%2] = nums2[j++];
            if(i + j == mid) break; 
        }

        while (i + j < mid && i == nums1.length) ary[(i + j)%2] = nums2[j++];
        while (i + j < mid && j == nums2.length) ary[(i + j)%2] = nums1[i++];
        
        if (len % 2 != 0) return ary[(i + j - 1)%2]; 
        else return (ary[0] + ary[1]) / 2.0; 
    }
}
