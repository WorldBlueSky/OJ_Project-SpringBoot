package com.example.demo;
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] a = new int[2];
        for(int i=0;i<nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i]+nums[j]==target){
                    a[0]=i;
                    a[1]=j;
                    return a;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();


        //testcase1
        //nums = [2,7,11,15], target = 9
        //[0,1]
        int[] nums1 = {2,7,11,15};
        int target = 9;
        int[] result1 = solution.twoSum(nums1,target);
        if(result1.length==2 && result1[0]==0 && result1[1]==1){
            System.out.println("testcase1 OK!");
        }else{
            System.out.println("testcase1 Failed!");
        }


        //nums = [3,2,4], target = 6
        //[1,2]
        int[] nums2 = {3,2,4};
        target=6;
        int[] result2 = solution.twoSum(nums2,target);
        if(result2.length==2 && result2[0]==1 && result2[1]==2){
            System.out.println("testcase2 OK!");
        }else{
            System.out.println("testcase2 Failed!");
        }

        //输入：nums = [3,3], target = 6
        //输出：[0,1]
        int[] nums3 = {3,3};
        target=6;
        int[] result3 = solution.twoSum(nums3,target);
        if(result3.length==2 && result3[0]==0 && result3[1]==1){
            System.out.println("testcase3 OK!");
        }else{
            System.out.println("testcase3 Failed!");
        }
    }

}