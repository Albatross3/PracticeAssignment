package KAKAO_BLIND_2022;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

// POST METHOD
public class StartAPI {
    static final String BASE_URL = "https://huqeyhi95c.execute-api.ap-northeast-2.amazonaws.com/prod";
    static final String X_AUTH_TOKEN = "8b5184fa970e1c731427877b9a78544e";
    public static String startApi(String path, String method, int numProblem) throws ParseException {
        URL url;
        HttpURLConnection connection;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(BASE_URL + "/" + path);

            connection = (HttpURLConnection) url.openConnection();

            // Header 의 method 설정
            connection.setRequestMethod(method);
            // Header 의 속성 값 설정
            connection.setRequestProperty("X-Auth-Token", X_AUTH_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");
            // 출력 가능한 형태로 설정
            connection.setDoOutput(true);

            // parameter 로  전송
            // JSON 데이터
            JSONObject parameter = new JSONObject();
            parameter.put("problem", numProblem);

            // 파라미터 데이터 출력 스트림에 입력
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            bw.write(parameter.toString());
            bw.flush();
            bw.close();

            // 200 (OK) 응답오면 JSON 형태를 String 으로 읽어들이기
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // JSON 데이터 파싱
        // response 는 key:value 를 map 형식으로 저장
        JSONObject response = (JSONObject) new JSONParser().parse(sb.toString());

        return (String) response.get("auth_key");
    }
}
