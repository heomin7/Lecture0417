import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by danawacomputer on 2017-04-17.
 */
public class CloserParser2 {
    public static void main(String[] args){

        String clientId = "VWtXBb7UmmwjlqeqW77G";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "zzevTF61eO";//애플리케이션 클라이언트 시크릿값";
        try {
            BufferedReader brs = new BufferedReader(new FileReader("closer.txt"));

            String line = "";
            String results = "";

            StringBuffer sb = new StringBuffer();
            while((line = brs.readLine()) != null){

                if(line.equals("")) continue;
                if(!line.substring(line.length()-1).equals(".")){

                    sb.append(line).append(" ");
                    continue;
                }
                else{
                    sb.append(line);
                }

                String text = URLEncoder.encode(line, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/language/translate";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=en&target=ko&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
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

                String jsonData = response.toString();
                JSONObject obj = new JSONObject(jsonData);

                String result = obj.getJSONObject("message").
                        getJSONObject("result").getString("translatedText");

                System.out.println(result);
                sb.setLength(0);

            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
