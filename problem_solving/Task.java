import java.util.*;


public static String reverseSubstrings(String s) {
        Stack<Integer> stack = new Stack<>();
        char[] arr = s.toCharArray();

        // Traverse the string characters
        for (int i = 0; i < arr.length; i++) {
        if (arr[i] == '(') {
        stack.push(i); // Push the index of opening parenthesis onto the stack
        } else if (arr[i] == ')') {
        // Pop the index of the corresponding opening parenthesis from the stack
        int start = stack.pop() +1;
        int end = i-1  ;

        // Reverse the substring between opening and closing parentheses
        while (start < end) {
        char temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
        start++;
        end--;
        }
        }
        }

        StringBuilder sb = new StringBuilder();
        // Construct the resulting string by appending characters
        for (char c : arr) {
        sb.append(c);
        }

        return sb.toString();
 }

    public static void main(String[] args) {
        String input1 = "abd(jnb)asdf";
        System.out.println(reverseSubstrings(input1)); // Output: abd(bnj)asdf

        String input2 = "abdjnbasdf";
        System.out.println(reverseSubstrings(input2)); // Output: abdjnbasdf

        String input3 = "dd(df)a(ghhh)";
        System.out.println(reverseSubstrings(input3)); // Output: dd(fd)a(hhhg)
    }
}