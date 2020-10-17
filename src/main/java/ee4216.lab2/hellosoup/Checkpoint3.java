package ee4216.lab2.hellosoup;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author FungFung
 */
public class Checkpoint3 implements Runnable {

    public static boolean terminate = false;
    private final int page;
    private int count;
    private final List<String> result;

    public Checkpoint3(int page) {
        this.page = page;
        this.count = 0;
        result = new ArrayList<>();
    }

    public int getCount() {
        return this.count;
    }
    
    public List getResult() {
        return this.result;
    }

    @Override
    public void run() //read only one page of staff contact
    {
        String url = "https://www.cityu.edu.hk/directories/people/academic?keyword=&page=" + this.page;
        Document doc;
        try {
            doc = Jsoup
                    .connect(url)
                    .get();
            if (doc.selectFirst("a[rel='next']") == null) {
                Checkpoint3.terminate = true; //can't find the 'next button'
            }
        } catch (IOException ex) {
            System.out.print("Can't Load this page!");
            Checkpoint3.terminate = true;
            return;
        }
        Elements names = doc.select("div.views-row");
        for (Element name : names) {
            try {
                this.count++;
                this.result.add(name.selectFirst("div.name > div.en").text() + ", " + name.select("div.email").text());
            } catch (Exception e) {
                System.out.print("Something went wrong!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        int thread_pool_size = 70; //Only accept Positive number, higher = faster
        List<Checkpoint3> myObjects = new ArrayList<>();
        List<Thread> myThreads = new ArrayList<>();
        for (int i = 0; !Checkpoint3.terminate; i++) { //!Checkpoint3.terminate
            Checkpoint3 r = new Checkpoint3(i);
            Thread thread = new Thread(r);
            thread.start();
            myObjects.add(r);
            myThreads.add(thread);
            if (i > 0 && i % thread_pool_size == 0) {
                for (int j = myThreads.size() - 1; j >= 0; j--) {
                    myThreads.get(j).join();
                    myThreads.remove(j);
                }
            }
        }
        List result;
        for (int i = 0; i < myObjects.size(); i++) {
            count += myObjects.get(i).getCount();
            result = myObjects.get(i).getResult();
            for (int j = 0; j < result.size(); j++){
                System.out.println(result.get(j));
            }
        }
        System.out.println("Total number of people: " + Integer.toString(count));
    }
}
