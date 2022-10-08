package KAKAO_BLIND_2022;

import org.json.simple.parser.ParseException;

import java.util.ArrayList;

import static KAKAO_BLIND_2022.MatchAPI.matchApi;
import static KAKAO_BLIND_2022.StartAPI.startApi;
import static KAKAO_BLIND_2022.WaitingLineAPI.waitingLineApi;

public class Solution {
    static String AUTH_KEY;
    public static void main(String[] args) throws ParseException {
        AUTH_KEY = startApi("start", "POST", 1);
        for (int i = 0; i <= 100; i++) {
            // 0 턴의 경우
            if (i == 0) {
                ArrayList<ArrayList<Integer>> pairs = new ArrayList<>();
                System.out.println(matchApi(pairs));
                continue;
            }
            ArrayList<ArrayList<Long>> waitLine = waitingLineApi();
            for (ArrayList<Long> a : waitLine) {
                System.out.println(a.get(0)+" "+a.get(1));
            }

            // 매칭 알고리즘
            ArrayList<ArrayList<Integer>> matchList = new ArrayList<>();
            for (int j = 0; j < waitLine.size() / 2; j++) {
                ArrayList<Integer> temp = new ArrayList<>();
                int user1 = waitLine.get(2*j).get(0).intValue();
                int user2 = waitLine.get(2*j+1).get(0).intValue();
                temp.add(user1);
                temp.add(user2);
                matchList.add(temp);
            }
            for (ArrayList<Integer> b : matchList) {
                System.out.println(b.get(0)+" "+b.get(1));
            }
            System.out.println(matchApi(matchList));
        }



    }
}
