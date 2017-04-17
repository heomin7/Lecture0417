package naversearch.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danawacomputer on 2017-04-17.
 */
public class NaverSearchService {
    public static String CLIENT_ID = "VWtXBb7UmmwjlqeqW77G";//애플리케이션 클라이언트 아이디값";
    public static String CLIENT_SECRET = "zzevTF61eO";//애플리케이션 클라이언트 시크릿값";

    public static List<String> searchAndReturnJson(String keyword) {
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray result = (JSONArray)jsonObject.get("items");

            List<String> bloggerlink = new ArrayList<>();
            int maxIdx = (int)jsonObject.get("display");

            for(int idx = 0; idx < maxIdx;idx++){
                bloggerlink.add(result.getJSONObject(idx).getString("bloggerlink"));
            }



            return bloggerlink;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static List<String> convertDisplay(String keyword, int count){
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display="+ count; // json 결과
            System.out.println(apiURL);
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray result = (JSONArray)jsonObject.get("items");

            List<String> bloggerName = new ArrayList<>();
            int maxIdx = (int)jsonObject.get("display");

            for(int idx = 0; idx < maxIdx;idx++){
                bloggerName.add(result.getJSONObject(idx).getString("bloggername"));
            }

            return bloggerName;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static String tranlate(String source, String target, String text) {

        try {
            String reqeustText = URLEncoder.encode(text, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            // post request
            String postParams =
                    "source=" + source + "&target="
                            + target + "&text=" + reqeustText;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //3. 결과 JSON을 파싱하여 콘솔에 출력한다.
            JSONObject obj = new JSONObject(response.toString());
            String result = obj.getJSONObject("message")
                    .getJSONObject("result")
                    .getString("translatedText");

            return result;

        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }
}
