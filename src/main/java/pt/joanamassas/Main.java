package pt.joanamassas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        //Let's get a random word from cambridge english dictionary and its meaning!

        Random rand = new Random();
        //starting off in the english dictionary webpage : we establish a connection and then with the .get() method we get the document object
        Document docw = Jsoup.connect("https://dictionary.cambridge.org/dictionary/english/").get(); //docw is an object with the html atributes (head,body, title, ...)
        System.out.println("Title: " + docw.title()); //title of the page (as in <title> in the <head> part of the html page)

        //Searching for elements with the class "bhb hao hdib hbr hbr-20 htc lp-5 lpl-10 lpr-10 lmin-40 lmr-5 lmb-10"
        //and storing them in an Elements object (array of Element), from the Jsoup lib
        Elements letters = docw.getElementsByClass("bhb hao hdib hbr hbr-20 htc lp-5 lpl-10 lpr-10 lmin-40 lmr-5 lmb-10");

        //now we choose a random element, between 0 and the size of the array - the letters in the alphabet (& numbers that appear on the website)
        int r = rand.nextInt(letters.size());

        //by parts:
        // now we get the element chosen randomly out of the array
        Element letterChosen = letters.get(r);

        //This html element is a reference <a>, therefore it has attributes like a title and a reference to the page it leads to (href)
        //<a class="bhb hao hdib hbr hbr-20 htc lp-5 lpl-10 lpr-10 lmin-40 lmr-5 lmb-10" href="https://dictionary.cambridge.org/browse/english/c/" title="Browse words starting with &quot;c&quot;">c</a>
        letterChosen.attr("title"); //would be the title of the <a> element: Browse words starting with &quot;c&quot;
        letterChosen.absUrl("href"); //would be the link to the page linked to the element : https://dictionary.cambridge.org/browse/english/c/
        letterChosen.text(); //would be the text inside the element: c

        //knowing that we can navigate to the page the element links and get the html doc object like this:
        Document doc = Jsoup.connect(letters.get(r).absUrl("href")).get();
        System.out.println("Title: " + doc.title());

        //In the letter page we have a listing of words, like a real dictionary page
        //it has a list of words between the 1st and last

        //now we retrieve the elements in the choosen letter page:  in this case we want the words listings & reference links
        Elements allByClass = doc.getElementsByClass("hlh32 hdb dil tcbd"); //array of all elements

        for (Element element : allByClass) { //for each element retrieved...
            //... we will show the element title and its reference link:
            System.out.println(element.attr("title") + " " + element.absUrl("href"));
        }

        // we print the number of elements we got
        System.out.println("\nRetrieved " + allByClass.size() + " references.");

        // now we choose a random list of words to go get our word! (between 0 and the nr of elements found)
        r = rand.nextInt(allByClass.size());

        // lets show the title of what we got :)
        System.out.println("Random ref [" + r +"]: " + allByClass.get(r).attr("title"));

        //Lets navigate to its page (the same way)
        System.out.println("Entering \"" + allByClass.get(r).attr("title") + "\" page...");

        //you know the drill...
        String enterpageUrl = allByClass.get(r).absUrl("href"); //get the link
        Document docEnter = Jsoup.connect(enterpageUrl).get(); // enter and get the html page object
        System.out.println("title: " + docEnter.title()); // show the title

        // Now that we enter a page with all the words in that section:
        // The page has a list of words

        //Lets retrieve the list of all the elements containing the words
        Elements words = docEnter.getElementsByClass("hlh32 han");

        //Show how many words we found..
        System.out.println("\n" + words.size() +  " WORDS & meanings urls");

        //for each word element...
        for(Element e : words){
            //... we get the 1st word of the element and show it
            String word = e.child(0).attr("title").split(" ")[0];

            //and the reference to the dictionary page!
            System.out.println(word + ": " + e.child(0).absUrl("href"));
        }
        //note: why use child() when we could get the child class?
        //the child class was used on other elements, not just the words.. so we would be collecting
        //extra, useless, data.. I found that the parent element had a class used only by them so I
        //chose to retrieve that and avoid extra data scanning (also a good opportunity to showcase
        // the child() method! :) )

        //next we choose a random word
        r = rand.nextInt(words.size());
        //and show the choice..
        System.out.println("\nRandom word [" + r +"]: " + words.get(r).child(0).attr("title").split(" ")[0]);

        //navigate to its reference link...
        Document wordDoc = Jsoup.connect(words.get(r).child(0).absUrl("href")).get();

        // Finnaly, we are in the random word page
        // Wich has the word, the type of word, meaning and examples:

        //We get all elements describing this word (noun, verb, adjective,..)
        Elements types_desc = wordDoc.getElementsByClass("posgram dpos-g hdib lmr-5").get(0).children();
        String typeofWord = new String(" - ");
        for(Element e : types_desc)
            typeofWord = typeofWord.concat(e.text() + " ");

        //and the word meaning..
        String desc = wordDoc.getElementsByClass("def ddef_d db").get(0).text();

        System.out.println(words.get(r).child(0).attr("title").split(" ")[0] +
                "\n" + typeofWord + "\n" + desc);

        //and examples of usage
        Elements exs = wordDoc.getElementsByClass("eg deg");
        for(Element e : exs)
            System.out.println(" > " + e.text());


        // And many, many more data can be collected in this way if you explore the many possibilities
        //of this library, this was a little example of one of the many uses!
        // I hope you enjoyed learning with me :)
        // - Joana
    }
}