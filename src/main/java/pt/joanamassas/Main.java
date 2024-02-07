package pt.joanamassas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://dictionary.cambridge.org/browse/english/u/").get();
        System.out.println("Title: " + doc.title() + "\n");
        Elements allByClass = doc.getElementsByClass("hlh32 hdb dil tcbd");
        int c = 0;
        for (Element element : allByClass) {
            System.out.println(element.attr("title") + " " + element.absUrl("href"));
            c++;
        }

        System.out.println("I got " + allByClass.size() + " refs"); // v/

        //Random rand = new Random();
        //int x = rand.nextInt(allByClass.size());
        //System.out.println("Random ref [" + x +"]: " + allByClass.get(x).attr("title"));

        System.out.println("\nentering \"" + allByClass.get(0).attr("title") + "\" page...");
        String enterpageUrl = allByClass.get(0).absUrl("href");
        Document docEnter = Jsoup.connect(enterpageUrl).get();
        System.out.println("title: " + docEnter.title());
        Elements words = docEnter.getElementsByClass("hlh32 han");
        //for(Element e : words){
        //    System.out.println(e.child(0).attr("title") + " " + e.child(0).absUrl("href"));
        //}

        System.out.println("\n" + words.size() +  " WORDS & meanings urls");
        for(Element e : words){
            String word = e.child(0).attr("title").split(" ")[0];
            System.out.println(word + ": " + e.child(0).absUrl("href"));
        }

        String url5thword = words.get(4).child(0).absUrl("href");
        Document doc5word = Jsoup.connect(url5thword).get();

        Elements types_desc = doc5word.getElementsByClass("posgram dpos-g hdib lmr-5").get(0).children();
        String typeofWord = new String("");
        for(Element e : types_desc)
            typeofWord = typeofWord.concat(e.text() + " ");


        String desc = doc5word.getElementsByClass("def ddef_d db").get(0).text();

        System.out.println("\n\n" +
                words.get(4).child(0).attr("title").split(" ")[0] +
                "\n" + typeofWord + "\n" + desc);

    }
}