/*
Here we used the Kandane in both way increasing and decreasing both
Just like we do maxSum = 0, whenever we encouter a negative sum
Do similar thing while calculating minimum. Make minSum zero when it goes positive
Because ABS will be max when the groups are helping to make positive max (5, 2, 1)
or negative max (-5, -1, -4)
* */



class Solution {
    public int maxAbsoluteSum(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int minSum = Integer.MAX_VALUE;
        int sumForMin = 0;
        int sumForMax = 0;
        for(int num: nums){
            sumForMin = sumForMin + num;
            //System.out.println("min sum" + sumForMin);
            minSum = Math.min(sumForMin, minSum);
            if(sumForMin > 0){
                sumForMin = 0;
            }

            sumForMax = sumForMax + num;
            //System.out.println("max sum" + sumForMax);
            maxSum = Math.max(sumForMax, maxSum);
            if(sumForMax < 0){
                sumForMax = 0;
            }

        }
        return Math.max(Math.abs(minSum), maxSum);
    }
}