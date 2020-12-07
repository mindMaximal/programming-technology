package sample.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JSONModel {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url, String obj) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();

        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String jsonText = readAll(rd);

        JSONObject jsonObject = new JSONObject(jsonText);

        return (JSONArray) jsonObject.get(obj);
    }

    public static ArrayList<JSONObject> load(String url, String obj) throws IOException, JSONException {
        ArrayList<JSONObject> res = new ArrayList<>();

        JSONArray jsonArray = readJsonFromUrl(url, obj);

        for (int i = 0; i < jsonArray.length(); i++) {
            res.add(jsonArray.getJSONObject(i));
        }

        return res;
    }

    public static Movie getDataFromJson(JSONObject el) {
        String name = (String) el.get("title");

        Double rating = Double.parseDouble((String) el.get("rating_kinopoisk"));

        if (el.get("type").equals("movie")) {

            JSONObject collapse = (JSONObject) el.get("collapse");
            StringBuilder timingBuilder = new StringBuilder();

            if (!collapse.isNull("duration")) {
                JSONArray collapseArray = (JSONArray) collapse.get("duration");
                for (int i = 0; i < collapseArray.length(); i++) {
                    timingBuilder.append(collapseArray.get(i));
                }
            }

            Double timing = null;
            String timingString = timingBuilder.toString();
            if (timingString.length() != 0) {
                timing = Double.parseDouble(timingString.split(" ")[0]);
            }

            Film.Type type;

            JSONArray genres = (JSONArray) el.get("genres");

            String genresString = (String) genres.get(0);
            genresString = genresString.toLowerCase();

            System.out.println(genresString);

            switch (genresString) {
                case "документальный":
                    type = Film.Type.documentary;
                    break;
                case "фантастика":
                    type = Film.Type.fiction;
                    break;
                case "научный":
                    type = Film.Type.scientific;
                    break;
                case "музыка":
                    type = Film.Type.music;
                    break;
                case "мюзикл":
                    type = Film.Type.musical;
                    break;
                case "концерт":
                    type = Film.Type.concert;
                    break;
                case "исторический":
                    type = Film.Type.historical;
                default:
                    type = Film.Type.none;
                    break;
            }

            return new Film(name, rating, timing, 3 , type);
        } else if (el.get("type").equals("tv-series")) {

            int seasons = (int) el.get("seasons");
            int series = (5 + (int) (Math.random() * 10)) * seasons;

            return new Serial(name, rating, series, seasons);
        }

        return null;
    }
}