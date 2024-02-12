package pt.joanamassas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import javax.print.Doc;
import java.io.IOException;
import java.util.Random;

public class V2 {

    private static boolean isItALetterLink(Element e){
        for(int i=0; i<26; i++)
            if(e.text().trim().toUpperCase().equals( String.valueOf((char)('A'+i)))) return true;

        return false;

    }

    public static void main(String[] args) throws IOException{
        Document docw = Jsoup.connect("https://dictionary.cambridge.org/dictionary/english/").get(); //docw is an object with the html atributes (head,body, title, ...)
        Elements allLinks = docw.select("a[href]");

        System.out.println("Welcome to " + docw.title());

        Random r = new Random();
        char chosenLetter =(char) ('A' + r.nextInt(27));
        String chosenLetterUrl = new String("");

        System.out.println("\nLETTERS & LINKS");
        for (Element linkElement : allLinks){
            if(isItALetterLink(linkElement))
                System.out.println(linkElement.text() + ": " +linkElement.absUrl("href"));

            if(linkElement.text().trim().toUpperCase().equals(String.valueOf(chosenLetter)))
                chosenLetterUrl = linkElement.absUrl("href");
        }



        System.out.println("\nI chose (randomly) the letter: " + chosenLetter + " [" + chosenLetterUrl +"]");

        Document letterDoc = Jsoup.connect(chosenLetterUrl).get();
        Elements allLetterLinks = letterDoc.select("a[href]");

        Integer nCategories = 0;

        System.out.println("\nWORD CATEGORIES & LINKS");
        for(Element link : allLetterLinks){
            if(link.text().contains("...")) {
                System.out.println(link.text() + " " + link.absUrl("href"));
                nCategories++;
            }
        }

        Integer chosenCategory = r.nextInt(nCategories);
        Element chosenCategoryElement = null;
        Integer i = 0;

        for(Element link : allLetterLinks)
            if(link.text().contains("..."))
                if(++i == chosenCategory)
                    chosenCategoryElement = link;


        System.out.println("\nI chose (randomly) the category \"" + chosenCategoryElement.text() + "\" [" + chosenCategoryElement.absUrl("href") + "]");



        Document categoryDoc = Jsoup.connect(chosenCategoryElement.absUrl("href")).get();
        Elements allCategoryLinks = categoryDoc.select("a[href]");

        //sintax: starter ... finisher
        String startingWord = chosenCategoryElement.text().split(" ")[0];
        String finishingWord = chosenCategoryElement.text().split(" ")[2];

        boolean flagValidLink = false;
        Integer wordCount = 0;

        for(Element link: allCategoryLinks) {
            if (link.text().contains(startingWord)) flagValidLink = true;
            if(flagValidLink) {
                System.out.println(link.text() + " " + link.absUrl("href"));
                wordCount++;
            }

            if(link.text().contains(finishingWord)) flagValidLink = false;
        }

        Integer chosenWord = r.nextInt(wordCount);
        Element chosenWordElement = null;

        wordCount = 0;

        for(Element link: allCategoryLinks) {
            if (link.text().contains(startingWord)) flagValidLink = true;
            if(flagValidLink)
                wordCount++;

            if(wordCount == chosenWord)
                chosenWordElement = link;


            if(link.text().contains(finishingWord)) flagValidLink = false;
        }

        System.out.println("\nOut of the " + wordCount + " words, I chose \"" + chosenWordElement.text() + "\" [" + chosenWordElement.absUrl("href") + "]");
    }
}
