package gdut.imis.test;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QuickSort {

    public static int[] quick_sort(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        int x = nums[i];
        System.out.println(Arrays.toString(nums));
        System.out.println("————————————————————");

        while (i < j) {
            if (nums[j] < x) {
                nums[i] = nums[j];
                i++;
                System.out.println("————————后往前填坑———————");
                System.out.println(Arrays.toString(nums));
            } else if (nums[j] >= x) {
                j--;
                System.out.println("————————后比x大———————");
                System.out.println(Arrays.toString(nums));
            }

            if (nums[i] >= x) {
                nums[j] = nums[i];
                j--;
                System.out.println("————————前往后填坑———————");
                System.out.println(Arrays.toString(nums));
            } else if (nums[i] < x) {
                i++;
                System.out.println("————————前比x小———————");
                System.out.println(Arrays.toString(nums));
            }
        }
        nums[i] = x;
        System.out.println(Arrays.toString(nums));
        return nums;


    }

    public static int maxSubArray(int[] nums) {
        int ans = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum < 0) {
                sum = num;
            } else {
                sum += num;
            }
            ans = Math.max(ans, sum);
        }
        System.out.println(ans);
        return ans;
    }


    public static double myPow(double x, int n) {
        if(n == 0 || x == 1) return 1;
        if(n < 0){
            x = 1/x;
            System.out.println(n);
            System.out.println(-n);
            System.out.println(n*-1);
            n = -n;
        }
        double y = x;
        for(int i=1; i<n; i++){
            x *= y;
        }
        System.out.println(x);
        return x;
    }


    int x = 1;
    int y;




    public static void main(String[] args) {
        String s = "你好！";
        char z = '你';
        System.out.println(z);

        double d1=-0.5;
        System.out.println("Ceil d1="+Math.ceil(d1));

    }
}
