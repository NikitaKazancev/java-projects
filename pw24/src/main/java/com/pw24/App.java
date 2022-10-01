package com.pw24;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

   private static String urlPath = "https://www.mirea.ru";

   public static void main(String[] args) {
      try {
         Document doc = Jsoup.connect(urlPath).get();
         Elements imgs = doc.select("img");
         Pattern pattern = Pattern.compile("[\\w-]+.\\w+$");
         Matcher matcher;

         String src;
         URL url;
         InputStream in;
         for (Element img : imgs) {
            src = img.attr("abs:src");

            if (
               src != "" &&
               (
                  src.contains("jpg") ||
                  src.contains("jpeg") ||
                  src.contains("png") ||
                  src.contains("svg") ||
                  src.contains("webp") ||
                  src.contains("gif")
               )
            ) {
               System.out.println(src);
               url = new URL(src);
               in = url.openStream();

               matcher = pattern.matcher(src);
               matcher.find();

               Files.copy(
                  in,
                  Paths.get(
                     "C:/Users/NIKE/Desktop/university/JavaProjects/pw24/images/" +
                     matcher.group()
                  ),
                  StandardCopyOption.REPLACE_EXISTING
               );

               in.close();
            }
         }
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
