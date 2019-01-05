package com.zs.various.util;

public class VersionUtil {


    public static boolean compareVersion(String version1 , String version2){
        boolean flag = false;
        String[] array1 = version1.split("\\.");
        String[] array2 = version2.split("\\.");
        loop:for (int i = 0 ; i< array1.length ; i++){
            if (array2.length < i + 1){
                flag = true;
                break;
            }else{
                String num1 = array1[i];
                String num2 = array2[i];
                for (int j = 0 ; j < num1.length();j++){
                    if (num2.length() < j + 1){
                        flag = true;
                        break loop;
                    }else{
                        if (num1.charAt(j) > num2.charAt(j)){
                            flag = true;
                            break loop;
                        }else if (num1.charAt(j) < num2.charAt(j)){
                            flag = false;
                            break loop;
                        }
                    }
                }

            }
        }
        return flag;
    }

}
