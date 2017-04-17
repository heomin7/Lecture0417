package naversearch;

import naversearch.service.NaverSearchService;

import java.util.List;

/**
 * Created by danawacomputer on 2017-04-17.
 */
public class NaverSearchMain {
    public static void main(String[] args){
        String keyword = "자바라";

        List<String> bloggerLinks = NaverSearchService.searchAndReturnJson(keyword);

        System.out.println("블로그 링크 리스트: ");
        for(String e: bloggerLinks){
            System.out.println(e);
        }
        System.out.println();

        List<String> bloggerNames = NaverSearchService.convertDisplay(keyword, 100);

        System.out.println("블로그 이름 리스트:");
        for(String e: bloggerNames){
            System.out.println(e);
        }

 //       System.out.println(json);
    }
}
