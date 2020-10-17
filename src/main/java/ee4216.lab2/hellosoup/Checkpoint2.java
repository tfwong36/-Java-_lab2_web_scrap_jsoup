
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
public class Checkpoint2 {
    public static void main(String[] args) throws IOException {
        String url = "https://www.cityu.edu.hk/directories/people/academic";
        int total_number = 0;
        Document doc;
        Element next;
        Elements names;
        do {
            doc = Jsoup
                    .connect(url)
                    .get();
            names = doc.select("div.views-row");
            for(Element name:names){
                try{
                    System.out.print(name.selectFirst("div.name > div.en").text() + ", ");
                    System.out.println(name.select("div.email").text());
                    total_number += 1;
                }catch(Exception e){
                    System.out.print("Something went wrong! (PRINT)");
                }
            }
            next = doc.selectFirst("a[rel='next']");
            if (next != null){
                url = next.attr("abs:href");
            }
        } while(next!=null);
        System.out.println("Total number of people: " + Integer.toString(total_number));
    }
}
