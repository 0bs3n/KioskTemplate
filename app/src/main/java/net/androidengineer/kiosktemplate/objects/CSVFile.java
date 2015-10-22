package net.androidengineer.kiosktemplate.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVFile {
    InputStream inputStream;

    private ArrayList<ArtesianBlend> artesianBlendArrayList = new ArrayList<>();
    private ArrayList<PremiumJuice> premiumJuiceArrayList = new ArrayList<>();

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ArrayList<String> readSimpleList() {
        ArrayList<String> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row[0]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    public ArrayList<ArtesianBlend> readCategory1Array() {
        artesianBlendArrayList.clear();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String csvLine;
        try {
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                artesianBlendArrayList.add(new ArtesianBlend(row[0], row[1], row[2], row[3], row[4], row[5]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return artesianBlendArrayList;
    }

    public ArrayList<PremiumJuice> readCategory2Array() {
        premiumJuiceArrayList.clear();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String csvLine;
        try {
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                premiumJuiceArrayList.add(new PremiumJuice(row[0], row[1], row[2], row[3], row[4], row[5]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return premiumJuiceArrayList;
    }
}
