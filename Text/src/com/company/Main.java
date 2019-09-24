package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String num1 = sc.nextLine();
        String num2 = sc.nextLine();
        if (num1.equals("0") || num2.equals("0"))
            System.out.println( "0");
        else {
            int len1 = num1.length(), len2 = num2.length();
            int[][] arr = new int[len2][len1 + len2];
            for (int j = len2 - 1; j >= 0; j--) {
                StringBuilder s = new StringBuilder();
                int temp = 0;
                for (int i = len1 - 1; i >= 0; i--) {
                    temp += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                    s.append(temp % 10);
                    temp /= 10;
                }
                if (temp != 0)
                    s.append(temp);
                int k = 0;
                int index = arr[0].length - 1 - (len2 - 1 - j);
                while (k < s.length()) {
                    arr[len2 - 1 - j][index--] = s.charAt(k) - '0';
                    k++;
                }
            }
            StringBuilder s = new StringBuilder();
            int temp = 0;
            for (int k = arr[0].length - 1; k >= 0; k--) {
                for (int i = 0; i < arr.length; i++) {
                    temp += arr[i][k];
                }
                s.append(temp % 10);
                temp /= 10;
            }
            String res = s.reverse().toString();
            int i = 0;
            while (res.charAt(i) == '0')
                i++;
            System.out.println(res.substring(i));
        }
    }
}
