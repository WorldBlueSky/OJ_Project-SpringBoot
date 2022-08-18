package com.example.demo;

import com.example.demo.mapper.ProblemMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Problem;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@MapperScan("com.example.demo.mappers")
class DemoApplicationTests {

    @Autowired
    public ProblemMapper problemMapper;

    @Autowired
    public UserMapper userMapper;

    @Test
    void contextLoads() {
        Problem problem = new Problem();
        problem.setId(0);
        problem.setTitle("两数之和");
        problem.setLevel("简单");
        problem.setDescription("给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。\n" +
                "\n" +
                "你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n" +
                "\n" +
                "你可以按任意顺序返回答案。\n" +
                "\n" +
                " \n" +
                "\n" +
                "示例 1：\n" +
                "\n" +
                "输入：nums = [2,7,11,15], target = 9\n" +
                "输出：[0,1]\n" +
                "解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。\n" +
                "示例 2：\n" +
                "\n" +
                "输入：nums = [3,2,4], target = 6\n" +
                "输出：[1,2]\n" +
                "示例 3：\n" +
                "\n" +
                "输入：nums = [3,3], target = 6\n" +
                "输出：[0,1]\n" +
                "\n" +
                "来源：力扣（LeetCode）\n" +
                "链接：https://leetcode.cn/problems/two-sum\n" +
                "著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。");

        problem.setTemplate("class Solution {\n" +
                "    public int[] twoSum(int[] nums, int target) {\n" +
                "\n" +
                "    }\n" +
                "}");

        problem.setTestCode("  public static void main(String[] args) {\n" +
                "        Solution solution = new Solution();\n" +
                "\n" +
                "\n" +
                "        //testcase1\n" +
                "        //nums = [2,7,11,15], target = 9\n" +
                "        //[0,1]\n" +
                "        int[] nums1 = {2,7,11,15};\n" +
                "        int target = 9;\n" +
                "        int[] result1 = solution.twoSum(nums1,target);\n" +
                "        if(result1.length==2 && result1[0]==0 && result1[1]==1){\n" +
                "            System.out.println(\"testcase1 OK!\");\n" +
                "        }else{\n" +
                "            System.out.println(\"testcase1 Failed!\");\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        //nums = [3,2,4], target = 6\n" +
                "        //[1,2]\n" +
                "        int[] nums2 = {3,2,4};\n" +
                "        target=6;\n" +
                "        int[] result2 = solution.twoSum(nums2,target);\n" +
                "        if(result2.length==2 && result2[0]==1 && result2[1]==2){\n" +
                "            System.out.println(\"testcase2 OK!\");\n" +
                "        }else{\n" +
                "            System.out.println(\"testcase2 Failed!\");\n" +
                "        }\n" +
                "        \n" +
                "        //输入：nums = [3,3], target = 6\n" +
                "        //输出：[0,1]\n" +
                "        int[] nums3 = {3,3};\n" +
                "        target=6;\n" +
                "        int[] result3 = solution.twoSum(nums3,target);\n" +
                "        if(result3.length==2 && result3[0]==0 && result3[1]==1){\n" +
                "            System.out.println(\"testcase3 OK!\");\n" +
                "        }else{\n" +
                "            System.out.println(\"testcase3 Failed!\");\n" +
                "        }\n" +
                "    }");

//        problemMapper.insert(problem);
//           problemMapper.delete(2);
        problemMapper.insert(problem);
    }

}
