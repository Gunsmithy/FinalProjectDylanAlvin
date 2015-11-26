package com.example.dylan.finalprojectdylanalvin;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DownloadPlacesTask extends AsyncTask<String, Void, String> {
    private PlacesListener listener = null;
    private Exception exception = null;
    private String[] places;
    private String[] addresses;
    private double[] lats;
    private double[] longs;

    public DownloadPlacesTask(PlacesListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            // parse out the data
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Log.d("FinalProject", "url: " + params[0]);
            URL url = new URL(params[0]);
            Document document = docBuilder.parse(url.openStream());
            document.getDocumentElement().normalize();

            // look for <WordDefinition> tags
            NodeList main = document.getElementsByTagName("PlaceSearchResponse");
            if ((main.getLength() > 0) && (main.item(0).getNodeType() == Node.ELEMENT_NODE)) {
                Element definitions = (Element)main.item(0);
                NodeList placesTags = definitions.getElementsByTagName("result");
                places = new String[placesTags.getLength()];
                addresses = new String[placesTags.getLength()];
                lats = new double[placesTags.getLength()];
                longs = new double[placesTags.getLength()];
                for (int i = 0; i < placesTags.getLength(); i++){
                    places[i] = "";
                    addresses[i] = "";
                    lats[i] = 0;
                    longs[i] = 0;
                }
                for (int i = 0; i < placesTags.getLength(); i++) {
                    //Node def = defTags.item(i);
                    Element place = (Element)placesTags.item(i);

                    NodeList name = place.getElementsByTagName("name");
                    Node temp = name.item(0);
                    places[i] = temp.getTextContent();

                    NodeList address = place.getElementsByTagName("vicinity");
                    Node temp2 = address.item(0);
                    addresses[i] = temp2.getTextContent();

                    NodeList lat = place.getElementsByTagName("lat");
                    Node temp3 = lat.item(0);
                    lats[i] = Double.valueOf(temp3.getTextContent());

                    NodeList longi = place.getElementsByTagName("lng");
                    Node temp4 = longi.item(0);
                    longs[i] = Double.valueOf(temp4.getTextContent());
                    //places[i] += def.getTextContent() + "\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        /*URLConnection feedUrl;
        try {
            feedUrl = new URL(params[0]).openConnection();
            InputStream is = feedUrl.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "");
            }
            is.close();

            places = sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }*/

        return "SUCCESS";
    }

    private String stripTags(String code) {
        return code; // for now
    }

    @Override
    protected void onPostExecute(String result) {
        if (exception != null) {
            exception.printStackTrace();
            return;
        }

        listener.showPlaces(places, addresses, lats, longs);
    }
}