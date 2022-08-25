# 下面的sql语句用于 插入数据库中的具体信息

use oj_database;

# 设置题目的信息

insert into oj_table values(0,'两数之和','简单','给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值target的那两个整数，并返回它们的数组下标。\n\n你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n\n你可以按任意顺序返回答案。\n\n 示例 1：\n\n 输入：nums = [2,7,11,15], target = 9\n输出：[0,1]\n解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]。\n\n示例 2：\n\n输入：nums = [3,2,4], target = 6\n输出：[1,2]\n\n示例 3：\n\n输入：nums = [3,3], target = 6\n输出：[0,1]\n\n\n来源：力扣（LeetCode）\n\n链接：https://leetcode.cn/problems/two-sum\n\n著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。',
                            'class Solution {
    public int[] twoSum(int[] nums, int target) {

    }
}','public static void main(String[] args) {
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
    }');

# 设置一个管理员账户
#默认密码是 123456，盐值为 abcdefg

insert into user(id,username,password,isAdmin,salt) values (0,'admin','87AD2B76937B34C58891913DED4C6DAC',1,'abcdefg');