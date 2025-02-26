/*
https://leetcode.com/problems/build-array-where-you-can-find-the-maximum-exactly-k-comparisons/

In this question intially I wrote brute force using recursion which simply add element to a list
when hit length calculate the cost and then backtrack the list. It's very enfficient approach

I was not able to think that we can do this cost calculation while traversal too like while crating that list
We can maintain the parameters in the calls itself.

Also LIS was hidden in this question we are doing nothing but find LIS of array which matches K
Please draw tree diagarm if following any issue with intution

Here function will look like solve(int idx, int searchCost, int maxSoFar)
                             solve(0, 0, -1)
So while doing loop from 1 to m, if we encounter that i is greater than maxSoFar we will simply increase cost
change maxSoFar and make idx+1

The approach helped us to find serachCost within the recursion stacks only which helped us doing meomoization
which was not possible in brute-force approach.

* */


//Recursive Code (Wrong Approach) You can't do dp here
class Solution {
    int count = 0;
    public int numOfArrays(int n, int m ,int k){
        List<Integer> list = new ArrayList<>();
        solve(list, n, m, k);
        return count;
    }
    public void solve(List<Integer> list, int n, int m, int k){
        if(list.size() == n){
            if(searchCost(list) == k){
                count++;
            }
            return;
        }
        for(int i = 1; i <= m; i++){
            list.add(i);
            solve(list, n, m, k);
            list.remove(list.size() - 1);
        }
    }
    public int searchCost(List <Integer> list){
        int max_val = -1;
        int search_cost = 0;
        int n = list.size();
        for(int i = 0; i < n; i++){
            if(max_val < list.get(i)){
                max_val = list.get(i);
                search_cost = search_cost + 1;
            }
        }
        return search_cost;
    }
}


//Recrusive Code (Correct Approach)
class Solution {
    public int numOfArrays(int n, int m, int k) {
        return solve(0, 0, -1, n, m, k);
    }
    public int solve(int idx, int searchCost, int maxSoFar, int n, int m, int k){
        if(idx == n){
            if(searchCost == k){
                return 1;
            }
            return 0;
        }
        int result = 0;
        for(int i = 1; i <= m; i++){
            if(i > maxSoFar){
                //as we got a higher max we increased the cost and changed maxSoFar
                result = result + solve(idx + 1, searchCost + 1, i, n, m, k);
            }else{
                result = result + solve(idx + 1, searchCost, maxSoFar, n, m, k);
            }
        }
        return result;
    }
}


//DP Memoization on above code
class Solution {
    //took 102 to avoid outofbound error, as stroing maxSoFar + 1
    int[][][] dp = new int[102][102][102];
    final int MOD = 1000000007;
    public int numOfArrays(int n, int m, int k) {
        for(int i = 0; i < 102; i++){
            for(int j = 0; j < 102; j++){
                Arrays.fill(dp[i][j], -1);
            }
        }
        return solve(0, 0, -1, n, m, k);
    }
    public int solve(int idx, int searchCost, int maxSoFar, int n, int m, int k){
        if(idx == n){
            if(searchCost == k){
                return 1;
            }
            return 0;
        }
        if(maxSoFar >= 0 && dp[idx][searchCost][maxSoFar+1] != -1){
            return dp[idx][searchCost][maxSoFar+1];
        }
        int result = 0;
        for(int i = 1; i <= m; i++){
            if(i > maxSoFar){
                result = (result + solve(idx + 1, searchCost + 1, i, n, m, k)) % MOD;
            }else{
                result = (result + solve(idx + 1, searchCost, maxSoFar, n, m, k)) % MOD;
            }
        }
        if(maxSoFar < 0) return result;//this can be skipped
        //storing maxSoFar + 1, so to avoid negative indexing as in parameter we took -1 as start
        return dp[idx][searchCost][maxSoFar+1] = result;
    }
}

