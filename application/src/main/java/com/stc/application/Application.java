package com.stc.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.LinkedList;
import java.util.Stack;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


        String input1 = "abd(jnb)asdf";
        System.out.println(reverseSubstrings(input1)); // Output: abd(bnj)asdf

        String input2 = "abdjnbasdf";
        System.out.println(reverseSubstrings(input2)); // Output: abdjnbasdf

        String input3 = "dd(df)a(ghhh)";
        System.out.println(reverseSubstrings(input3)); // Output: dd(fd)a(hhhg)
    }

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

    static public String reverseString2(String word) {
        Stack<Character> characterStack = new Stack<>();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ')') {
                LinkedList<Character> linkedList = new LinkedList<>();
                while (characterStack.peek() != '(') {
                    linkedList.add(characterStack.pop());
                }
                characterStack.pop();
                while (linkedList.size() != 0) {
                    characterStack.push(linkedList.remove());
                }
            } else {
                characterStack.push(word.charAt(i));
            }
        }
        char[] charsArray = new char[characterStack.size()];
        int i = charsArray.length - 1;
        while (characterStack.size() > 0) {
            charsArray[i] = characterStack.pop();
            i--;
        }

        return new String(charsArray);
    }

}
