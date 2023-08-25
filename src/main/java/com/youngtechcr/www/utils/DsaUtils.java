package com.youngtechcr.www.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DsaUtils {

    public static String spaceSeparated(Set<String> set) {
        Iterator iterator = set.iterator();
        String rs = "";
        while (iterator.hasNext()) {
            rs += iterator.next() + " ";
        }
        return rs;
    }

    // quick main method because to lazy for a proper unit test
//    public static void main(String[] args) {
//        Set<String> roles = new HashSet<>();
//        roles.add("ROLE_USER");
//        roles.add("ROLE_CUSTOMER");
//        roles.add("ROLE_EMPLOYEE");
//        roles.add("ROLE_ADMIN");
//        String rs = spaceSeparated(roles);
//        System.out.println(rs);
//    }


}
