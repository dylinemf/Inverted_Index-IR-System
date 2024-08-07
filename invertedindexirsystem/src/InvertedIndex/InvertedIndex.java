package InvertedIndex;

// @author dyline

import java.io.*;
import java.util.*;

class Index {

    Map<Integer, String> sumber;
    HashMap<String, HashSet<Integer>> index;

    Index() {
        sumber = new HashMap<Integer, String>();
        index = new HashMap<String, HashSet<Integer>>();
    }

    public void buatIndex(String[] files) {
        int i = 0;
        for (String namaFile : files) {

            try (BufferedReader file = new BufferedReader(new FileReader("..\\invertedindexirsystem\\Koleksi\\"
                    + namaFile))) {
                BufferedReader stopwords = new BufferedReader(
                        new FileReader("..\\invertedindexirsystem\\stopwordsin.txt"));
                sumber.put(i, namaFile);
                String ln;
                String readStopwords;
                String cleanTerms = "";
                while ((ln = file.readLine()) != null) {
                    readStopwords = stopwords.readLine();
                    String[] terms = ln.split("\\W+");
                    String[] stopword = readStopwords.split("\\W+");

                    for (String term : terms) {
                        term = term.toLowerCase();
                        if (!term.equals(stopword)) {
                            cleanTerms = term;
                        }
                        if (!index.containsKey(cleanTerms)) {
                            index.put(cleanTerms, new HashSet<Integer>());
                        }
                        index.get(cleanTerms).add(i);
                    }
                }
            } catch (IOException e) {
                System.out.println("File " + namaFile + " tidak ditemukan. skip");
            }
            i++;
        }
        index.remove("");
        System.out.println(sumber.entrySet());
        System.out.println(index.entrySet());
    }

    public void search(String key) {
        String[] keywords = key.split("\\W+");
        HashSet<Integer> res = new HashSet<Integer>(index.get(keywords[0].toLowerCase()));
        for (String kw : keywords) {
            res.retainAll(index.get(kw));
        }

        if (res.size() == 0) {
            System.out.println("Tidak ditemukan");
            return;
        }
        System.out.println("Ditemukan di: ");
        for (int num : res) {
            System.out.println("\t" + sumber.get(num));
        }
    }
}

public class InvertedIndex {

    public static void main(String args[]) throws IOException {
        Index index = new Index();
        String path = "..\\invertedindexirsystem\\Koleksi";
        File name = new File(path);
        if (name.exists()) {
            if (name.isDirectory()) {
                String directory[] = name.list();
                index.buatIndex(directory);
                System.out.println("kata kunci: ");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String keywords = in.readLine();

                index.search(keywords);
            }
        }
    }
}
//        Scanner input;
//        String directory[];
//        index.buildIndex(new String[]{"testfile1.txt", "testfile2.txt"});
//        System.out.println("cek1");
//            System.out.println("cek2");
//                System.out.println(directory[0] + "cek");
