package com.pw27_28;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class App {

   static String dir =
      "C:\\Users\\NIKE\\Desktop\\university\\JavaProjects\\pw27_28\\";

   public static void reduceImage(String distFolder, File file) {
      BufferedImage image;
      try {
         image = ImageIO.read(file);
         if (image == null) {
            return;
         }

         int newWidth = image.getWidth() / 2;
         int newHeight = (int) Math.round(
            image.getHeight() / (image.getWidth() / (double) newWidth)
         );
         BufferedImage newImage = new BufferedImage(
            newWidth,
            newHeight,
            BufferedImage.TYPE_INT_RGB
         );

         int widthStep = image.getWidth() / newWidth;
         int heightStep = image.getHeight() / newHeight;

         int rgb;
         for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
               rgb = image.getRGB(x * widthStep, y * heightStep);
               newImage.setRGB(x, y, rgb);
            }
         }

         File newFile = new File(distFolder + "/" + file.getName());
         ImageIO.write(newImage, "jpg", newFile);
      } catch (IOException e) {}
   }

   public static void reduceImages(String srcFolder, final String distFolder) {
      File srcDir = new File(srcFolder);
      File[] files = srcDir.listFiles();

      try {
         if (!Files.exists(Paths.get(distFolder))) {
            Files.createDirectories(Paths.get(distFolder));
         }

         for (final File file : files) {
            if (Runtime.getRuntime().availableProcessors() != 0) {
               new Thread(
                  new Runnable() {
                     @Override
                     public void run() {
                        reduceImage(distFolder, file);
                     }
                  }
               )
                  .start();
            } else {
               reduceImage(distFolder, file);
            }
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static void main(String[] args) {
      long start = System.currentTimeMillis();

      reduceImages(dir + "src\\main\\img", dir + "target\\img");

      System.out.println("Duration: " + (System.currentTimeMillis() - start));
   }
}
