package ee4216.lab2.hellosoup;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Jason
 */
public class Checkpoint1 {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup
                .connect("https://www.cityu.edu.hk/directories/people/academic")
                .get();
        //System.out.println(doc.title());
        Elements names = doc.select("div.views-row");
        for(Element name:names){
            try{
                System.out.print(name.selectFirst("div.name > div.en").text() + ", ");
                System.out.println(name.select("div.email").text());
            } catch(Exception e){
                System.out.print("Something went wrong!");
            }
        }
    }
}