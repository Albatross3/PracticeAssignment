package KAKAO_BLIND_2022;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static KAKAO_BLIND_2022.Solution.AUTH_KEY;


public class WaitingLineAPI {
    static final String BASE_URL = "https://huqeyhi95c.execute-api.ap-northeast-2.amazonaws.com/prod/waiting_line";
    public static ArrayList<ArrayList<Long>> waitingLineApi() throws ParseException {
        URL url;
        HttpURLConnection connection;
        StringBuilder sb = new StringBuilder();
        try{
            url = new URL(BASE_URL);

            connection = (HttpURLConnection) url.openConnection();

            // Header 의 method 설정
            connection.setRequestMethod("GET");
            // Header 의 속성 값 설정
            connection.setRequestProperty("Authorization", AUTH_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            // 출력 가능한 형태로 설정
            connection.setDoOutput(true);


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

        // 반환할 ArrayList<ArrayList<Integer>> 생성
        ArrayList<ArrayList<Long>> result = new ArrayList<>();
        // JSON 파싱
        JSONObject response = (JSONObject) new JSONParser().parse(sb.toString());
        // JSONObject 에서 Array 데이터 를 get 하여 JSONArray 에 저장한다.
        JSONArray jsonArray = (JSONArray) response.get("reservations_info");

        JSONObject line;
        for (int i = 0; i < jsonArray.size(); i++) {
            line = (JSONObject) jsonArray.get(i);
            ArrayList<Long> temp = new ArrayList<>();
            long id = (long) line.get("id");
            long amount = (long) line.get("amount");
            long check_in_date = (long) line.get("check_in_date");
            long check_out_date = (long) line.get("check_out_date");
            temp.add(id);
            temp.add(amount);
            temp.add(check_in_date);
            temp.add(check_out_date);
            result.add(temp);
        }
        return result;
    }

}
