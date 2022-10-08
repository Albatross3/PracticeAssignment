package KAKAO_BLIND_2022;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static KAKAO_BLIND_2022.Solution.AUTH_KEY;


// PUT METHOD
public class MatchAPI {
    static final String BASE_URL = "https://huqeyhi95c.execute-api.ap-northeast-2.amazonaws.com/prod/match";
    public static String matchApi(ArrayList<ArrayList<Integer>> pairs) {
        URL url;
        HttpURLConnection connection;
        StringBuilder sb = new StringBuilder();
        try{
            url = new URL(BASE_URL);

            connection = (HttpURLConnection) url.openConnection();

            // Header 의 method 설정
            connection.setRequestMethod("PUT");
            // Header 의 속성 값 설정
            connection.setRequestProperty("Authorization", AUTH_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            // 출력 가능한 형태로 설정
            connection.setDoOutput(true);

            // parameter 로  전송
            // JSON 데이터
            JSONObject parameter = new JSONObject();
            parameter.put("pairs", pairs);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
            bw.write(parameter.toString());
            bw.flush();
            bw.close();

            // 200 (OK) 응답오면 JSON 형태를 String 으로 읽어들이기
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
