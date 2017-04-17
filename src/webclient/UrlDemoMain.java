package webclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danawacomputer on 2017-04-17.
 */
public class UrlDemoMain {
    public static void main(String[] args){
        try {
            URL url = new URL("https://api.github.com/users/soongon");


            BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String line ="";
            List<String > wordList = new ArrayList<>();
            while((line = br.readLine()) != null){
//                System.out.println(line);
                String[] word = line.split(" ");

                for(String e: word){
                    wordList.add(e);
                }
            }

            //분석
            int count = 0;
            for(String e: wordList){
                if(!e.trim().equals("")){
                    count++;
                }
            }
            System.out.println(count);

            System.out.println("count: " + wordList.size());

            System.out.println(wordList.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
