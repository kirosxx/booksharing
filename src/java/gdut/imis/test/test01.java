package gdut.imis.test;

import java.util.*;
import java.util.stream.Collectors;

public class test01 {


    public static void main(String[] args) {
        int[] nums = {1,1,1,1,1};
        System.out.println(findTargetSumWays(nums,3));
    }

    static int count = 0;
    public static int findTargetSumWays(int[] nums, int S) {
        forSum(nums,S,0);
        return count;
    }

    public static void forSum(int[] nums,int S,int i){
        if(S == 0 && i == nums.length - 1){
            count ++;
            return;
        }

        if(i > nums.length - 1) return;

        forSum(nums,S + nums[i],i + 1);
        forSum(nums,S - nums[i],i + 1);
    }

}
