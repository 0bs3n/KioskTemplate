package net.androidengineer.kiosktemplate.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVFile {
    InputStream inputStream;

    private ArrayList<ProductItem> productItemArrayList = new ArrayList<>();

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ArrayList<String> readSimpleList() {
        ArrayList<String> resultList = new ArrayList<>();
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

    public ArrayList<ProductItem> readProductArray() {
        productItemArrayList.clear();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String csvLine;
        try {
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                productItemArrayList.add(new ProductItem(row[0], row[1], row[2], row[3], row[4], row[5]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productItemArrayList;
    }


}
