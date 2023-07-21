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
    public static final int BALL = 5;
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
                match.setPlayer_of_match(field[PLAYER_OF_MATCH]);

                matches.add(match);

            }
        } catch (Exception e) {

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
                deliveries.add(delivery);

            }
        } catch (Exception e) {

        }
        return deliveries;
    }

    public static void findNumberOfMatchesPlayedPerTeam(List<Match> matches) {
        HashMap<String, Integer> totalMatch = new HashMap<>();
        Match match;
        for (int i = 0; i < matches.size(); i++) {
            match = matches.get(i);
            String key = match.getSeason();
            if (totalMatch.containsKey(key)) {
                int val = totalMatch.get(key);
                totalMatch.put(key, ++val);
            } else {
                totalMatch.put(key, 1);
            }
        }
        System.out.println(totalMatch);
    }

    public static void findNumberOfMatchesWinnerPerTeam (List < Match > matches) {
        HashMap<String, Integer> winnerTeam = new HashMap<>();
        Match match;
        for (int i = 0; i < matches.size(); i++) {
            match=matches.get(i);
            String key=match.getWinner();
            if (winnerTeam.containsKey(key)) {
                int val = winnerTeam.get(key);
                winnerTeam.put(key, ++val);
            } else {
                winnerTeam.put(key, 1);
            }
        }
        System.out.println(winnerTeam);
    }
    public static void in2016GetTheExtraRunConcededPerTeam(List<Match> matches, List<Delivery> deliveries){
        HashMap<String,Integer> team=new HashMap<>();
        Match match;
        Delivery delivery;
        int startIndex=0;
        int endIndex=0;
        boolean isFirst=true;
        for(int i=0;i<matches.size();i++){
            match=matches.get(i);
            String key=match.getSeason();
            if(key.equals("2016") && isFirst==true){
                startIndex=Integer.parseInt(match.getId());
                isFirst=false;
            }
            if(key.equals("2016")){
                endIndex=Integer.parseInt(match.getId());
            }
        }
        System.out.println(startIndex+" "+endIndex);
        for(int i=1;i<deliveries.size();i++){
            delivery=deliveries.get(i);
            String key= delivery.getBatting_team();
            int value=Integer.parseInt(delivery.getExtra_runs());
            if(startIndex<=Integer.parseInt(delivery.getMatch_id()) && Integer.parseInt(delivery.getMatch_id())<=endIndex) {
                if (team.containsKey(key)) {
                    int val = team.get(key);
                    team.put(key, val + value);
                } else {
                    team.put(key, value);
                }
            }
        }
        System.out.println(team);
    }

    public static void in2015TopEconomicalBowler(List<Match> matches,List<Delivery> deliveries){
            HashMap<String, Integer> over = new HashMap<>();
            HashMap<String, Integer> run = new HashMap<>();
            int startIndex = 0;
            int endIndex = 0;
            boolean isFirst = true;
            for (int i = 0; i < matches.size(); i++) {
                Match match;
                match = matches.get(i);
                String key = match.getSeason();
                if (key.equals("2015") && isFirst == true) {
                    startIndex = Integer.parseInt(match.getId());
                    isFirst = false;
                }
                if (key.equals("2015")) {
                    endIndex = Integer.parseInt(match.getId());
                }
            }

            for (int i = 1; i < deliveries.size(); i++) {
                Delivery delivery;
                delivery = deliveries.get(i);
                String key = delivery.getBowler();
                int total=Integer.parseInt(delivery.getTotal_runs());
                if (startIndex <= Integer.parseInt(delivery.getMatch_id()) && Integer.parseInt(delivery.getMatch_id()) <= endIndex) {
                    if (run.containsKey(key)) {
                        int val = run.get(key);
                        run.put(key, val + total);
                    } else {
                        run.put(key, total);
                    }
                }
            }
            for(int i=1;i<deliveries.size();i++){
                Delivery delivery;
                delivery=deliveries.get(i);
                String key=delivery.getBowler();
                if(startIndex<=Integer.parseInt(delivery.getMatch_id()) && Integer.parseInt(delivery.getMatch_id())<=endIndex){
                    if(over.containsKey(key)){
                        int val=over.get(key);
                        if(Integer.parseInt(delivery.getBall())<=6){
                            over.put(key,++val);
                        }
                    }
                    else{
                        over.put(key,1);
                    }
                }
            }
            float top[]=new float[over.size()];
            int len=0;
            HashMap<String,Float> economic=new HashMap<>();
            for(String str:over.keySet()){
                int val=over.get(str);
                float f=val;
                f/=6;
                if(run.containsKey(str)){
                    int count=run.get(str);
                    economic.put(str,count/f);
                    top[len++]=count/f;
                }
            }
            Arrays.sort(top);
            for(int i=0;i<10;i++) {
                for (Map.Entry<String, Float> entry : economic.entrySet()) {
                    if (entry.getValue()==top[i]){
                        System.out.println(entry.getKey()+" "+top[i]);
                        break;
                    }
                }
            }
        }
        public static void maxPlayerOfTheMatch20141516(List<Match> matches){
            HashMap<String,Integer> playerOfTheMatch=new HashMap<>();
            for(int i=0;i<matches.size();i++) {
                Match match;
                match = matches.get(i);
                String player = match.getPlayer_of_match();
                if (match.getSeason().equals(year2014) || match.getSeason().equals(year2015) || match.getSeason().equals(year2016)){
                    if (playerOfTheMatch.containsKey(player)) {
                        int value = playerOfTheMatch.get(player);
                        playerOfTheMatch.put(player, ++value);
                    } else {
                        playerOfTheMatch.put(player, 1);
                    }
                }
            }
            System.out.println(playerOfTheMatch);
        }
    public static void main (String[]args){
        List<Match> matches;
        List<Delivery> deliveries;
        matches = getMatchesData();
        deliveries=getDeliveriesData();
        findNumberOfMatchesPlayedPerTeam(matches);
        findNumberOfMatchesWinnerPerTeam(matches);
        in2016GetTheExtraRunConcededPerTeam(matches,deliveries);
        in2015TopEconomicalBowler(matches,deliveries);
        maxPlayerOfTheMatch20141516(matches);

    }
}