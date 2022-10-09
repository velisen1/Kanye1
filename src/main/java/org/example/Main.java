package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import java.util.ArrayList;




public class Main {

    static ArrayList<String> quotesList = new ArrayList<String>(); //ArrayList in which already seen Kanye quotes are stored
    public static void getQuote(){
        /*
         * This method calls callQuote method and checks if the quote was already printed. If true then Kanye's quote is called once again.
         * If false then quote is printed and stored in quotesList.
         * The problems occur when an API is running out of new quotes.
         */
        String quote = callQuote();
        if(quotesList.contains(quote)){
            getQuote();
        }
        else{
            System.out.println(quote);
            System.out.println("");
            System.out.println("Type next to get another inspirational Kanye West quote!");
            quotesList.add(quote);
        }

    }

    public static String callQuote(){
        try {
            /*
             * This method calls https://api.kanye.rest to get Kanye's quote from website and returns it.
             */


            URL url = new URL("https://api.kanye.rest");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();
            //Checking response
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                //Converting API response into string
                String string = informationString.toString();

                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(string);

                String quote = (String) json.get("quote");
                return quote;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        //When "next" is typed in the console then Kanye's quote is printed
        System.out.println("Type next to get inspirational Kanye West quote!");

        while(true) {
            Scanner input = new Scanner(System.in);

            String userInput = input.nextLine();
            if (userInput.equals("next")) {
                getQuote();
            }

        }
    }

}