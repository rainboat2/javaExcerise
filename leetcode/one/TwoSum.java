package one;

import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {

    public static void main(String[] args){
        int[] nums = new int[]{3,3};
        int target = 6;
        System.out.println("test");
        TwoSum t = new TwoSum();
        System.out.println(Arrays.toString(t.twoSum(nums, target)));
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++){
            int complement = target - nums[i];
            if (map.containsKey(complement)){
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("no two sum");
    }
}
