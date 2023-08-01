package com.project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static final int MATCH_ID = 0;
    public static final int SEASON = 1;
    public static final int MATCH_TEAM1 = 4;
    public static final int MATCH_TEAM2 = 5;
    public static final int WINNER = 10;
    public static final int PLAYER_OF_MATCH = 13;
    public static final int BATTING_TEAM = 2;
    public static final int OVER = 4;
    public static final int BOWLING_TEAM=3;
    public static final int BALL = 5;
    public static final int BATSMAN=6;
    public static final int BOWLER = 8;
    public static final int EXTRA_RUNS = 16;
    public static final int TOTAL_RUNS = 17;
    public static final String year2014="2014";
    public static final String year2015="2015";
    public static final String year2016="2016";

    private static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();
        try {
            FileReader file = new FileReader("matches.csv");
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null) {
                String field[] = line.split(",");

                Match match = new Match();
                match.setId(field[MATCH_ID]);
                match.setSeason(field[SEASON]);
                match.setTeam1(field[MATCH_TEAM1]);
                match.setTeam2(field[MATCH_TEAM2]);
                match.setWinner(field[WINNER]);
                match.setPlayerOfMatch(field[PLAYER_OF_MATCH]);
                matches.add(match);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return matches;
    }
    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList<>();
        try {
            FileReader file = new FileReader("deliveries.csv");
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null) {
                String field[] = line.split(",");
                Delivery delivery = new Delivery();

                delivery.setMatch_id(field[MATCH_ID]);
                delivery.setBall(field[BALL]);
                delivery.setBatting_team(field[BATTING_TEAM]);
                delivery.setOver(field[OVER]);
                delivery.setBowler(field[BOWLER]);
                delivery.setExtra_runs(field[EXTRA_RUNS]);
                delivery.setTotal_runs(field[TOTAL_RUNS]);
                delivery.setBatsman(field[BATSMAN]);
                delivery.setBowling_team(field[BOWLING_TEAM]);
                deliveries.add(delivery);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return deliveries;
    }

    private static void findNumberOfMatchesPlayedPerTeam(List<Match> matches) {
        HashMap<String, Integer> totalMatchPerTeamMap = new HashMap<>();
        Match match;
        for (int i = 0; i < matches.size(); i++) {
            match = matches.get(i);
            String key = match.getSeason();
            if (totalMatchPerTeamMap.containsKey(key)) {
                int val = totalMatchPerTeamMap.get(key);
                totalMatchPerTeamMap.put(key, ++val);
            } else {
                totalMatchPerTeamMap.put(key, 1);
            }
        }
        System.out.println(totalMatchPerTeamMap);
    }

    private static void findNumberOfMatchesWinnerPerTeam (List < Match > matches) {
        HashMap<String, Integer> winnerTeamMap = new HashMap<>();
        Match match;
        for (int i = 0; i < matches.size(); i++) {
            match=matches.get(i);
            String mapKey=match.getWinner();
            if (winnerTeamMap.containsKey(mapKey)) {
                int mapVal =winnerTeamMap.get(mapKey);
                winnerTeamMap.put(mapKey, ++mapVal);
            } else {
                winnerTeamMap.put(mapKey, 1);
            }
        }
        System.out.println(winnerTeamMap);
    }
    private static void findExtraRunConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries){
        HashMap<String,Integer> teamRunMap=new HashMap<>();
        Match match;
        Delivery delivery;
        int startIndexOf2016=0;
        int endIndexOf2016=0;
        boolean isFirst=true;
        for(int i=0;i<matches.size();i++){
            match=matches.get(i);
            String season=match.getSeason();
            if(season.equals(year2016) && isFirst==true){
                startIndexOf2016=Integer.parseInt(match.getId());
                isFirst=false;
            }
            if(season.equals(year2016)){
                endIndexOf2016=Integer.parseInt(match.getId());
            }
        }
        for(int i=1;i<deliveries.size();i++){
            delivery=deliveries.get(i);
            String key= delivery.getBatting_team();
            int value=Integer.parseInt(delivery.getExtra_runs());
            if(startIndexOf2016<=Integer.parseInt(delivery.getMatch_id()) && Integer.parseInt(delivery.getMatch_id())<=endIndexOf2016) {
                if (teamRunMap.containsKey(key)) {
                    int val = teamRunMap.get(key);
                    teamRunMap.put(key, val + value);
                } else {
                    teamRunMap.put(key, value);
                }
            }
        }
        System.out.println(teamRunMap);
    }

    private static void findTopEconomicalBowlerIn2015(List<Match> matches,List<Delivery> deliveries){
            HashMap<String, Integer> bowlerOverMap = new HashMap<>();
            HashMap<String, Integer> bowlerRunMap = new HashMap<>();
            int startIndexOf2015 = 0;
            int endIndexOf2015 = 0;
            boolean isFirst = true;
            for (int i = 0; i < matches.size(); i++) {
                Match match;
                match = matches.get(i);
                String season = match.getSeason();
                if (season.equals(year2015) && isFirst == true) {
                    startIndexOf2015 = Integer.parseInt(match.getId());
                    isFirst = false;
                }
                if (season.equals(year2015)) {
                    endIndexOf2015 = Integer.parseInt(match.getId());
                }
            }

            for (int i = 1; i < deliveries.size(); i++) {
                Delivery delivery;
                delivery = deliveries.get(i);
                String mapKey = delivery.getBowler();
                int totalRun=Integer.parseInt(delivery.getTotal_runs());
                if (startIndexOf2015 <= Integer.parseInt(delivery.getMatch_id()) && Integer.parseInt(delivery.getMatch_id()) <= endIndexOf2015) {
                    if (bowlerRunMap.containsKey(mapKey)) {
                        int mapVal = bowlerRunMap.get(mapKey);
                        bowlerRunMap.put(mapKey, mapVal + totalRun);
                    } else {
                        bowlerRunMap.put(mapKey, totalRun);
                    }
                }
            }
            for(int i=1;i<deliveries.size();i++){
                Delivery delivery;
                delivery=deliveries.get(i);
                String mapKey=delivery.getBowler();
                if(startIndexOf2015<=Integer.parseInt(delivery.getMatch_id()) && Integer.parseInt(delivery.getMatch_id())<=endIndexOf2015){
                    if(bowlerOverMap.containsKey(mapKey)){
                        int mapVal=bowlerOverMap.get(mapKey);
                        if(Integer.parseInt(delivery.getBall())<=6){
                            bowlerOverMap.put(mapKey,++mapVal);
                        }
                    }
                    else{
                        bowlerOverMap.put(mapKey,1);
                    }
                }
            }
            float top[]=new float[bowlerOverMap.size()];
            int len=0;
            HashMap<String,Float> economyMap=new HashMap<>();
            for(String str:bowlerOverMap.keySet()){
                int mapVal=bowlerOverMap.get(str);
                float f=mapVal;
                f/=6;
                if(bowlerRunMap.containsKey(str)){
                    int mapCount=bowlerRunMap.get(str);
                    economyMap.put(str,mapCount/f);
                    top[len++]=mapCount/f;
                }
            }
            Arrays.sort(top);
            for(int i=0;i<10;i++) {
                for (Map.Entry<String, Float> entry : economyMap.entrySet()) {
                    if (entry.getValue()==top[i]){
                        System.out.println(entry.getKey()+" "+top[i]);
                        break;
                    }
                }
            }
        }
        private static void maxPlayerOfTheMatch20141516(List<Match> matches){
            HashMap<String,Integer> playerOfTheMatchMap=new HashMap<>();
            for(int i=0;i<matches.size();i++) {
                Match match;
                match = matches.get(i);
                String player = match.getPlayerOfMatch();
                if (match.getSeason().equals(year2014) || match.getSeason().equals(year2015) || match.getSeason().equals(year2016)){
                    if (playerOfTheMatchMap.containsKey(player)) {
                        int mapVal = playerOfTheMatchMap.get(player);
                        playerOfTheMatchMap.put(player, ++mapVal);
                    } else {
                        playerOfTheMatchMap.put(player, 1);
                    }
                }
            }
            System.out.println(playerOfTheMatchMap);
        }

    public static void main (String[]args){
        List<Match> matches;
        List<Delivery> deliveries;
        matches = getMatchesData();
        deliveries=getDeliveriesData();
        findNumberOfMatchesPlayedPerTeam(matches);
        findNumberOfMatchesWinnerPerTeam(matches);
        findExtraRunConcededPerTeamIn2016(matches,deliveries);
        findTopEconomicalBowlerIn2015(matches,deliveries);
        maxPlayerOfTheMatch20141516(matches);
    }
}