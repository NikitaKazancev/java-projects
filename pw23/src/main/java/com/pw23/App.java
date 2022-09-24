package com.pw23;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class App {

   public static void main(String[] args) {
      try {
         Reader reader = new BufferedReader(
            new FileReader(
               "C:\\Users\\NIKE\\Desktop\\university\\JavaProjects\\pw23\\src\\main\\csv\\test.csv"
            )
         );

         HeaderColumnNameTranslateMappingStrategy<MonthTransaction> mappingStrategy = new HeaderColumnNameTranslateMappingStrategy<>();
         mappingStrategy.setColumnMapping(
            new HashMap<String, String>() {
               {
                  put("id", "id");
                  put("date", "date");
                  put("starting_balance", "startingBalance");
                  put("income", "income");
                  put("expenses", "expenses");
                  put("percents", "percents");
                  put("cashback", "cashback");
                  put("final_balance", "finalBalance");
               }
            }
         );
         mappingStrategy.setType(MonthTransaction.class);

         CsvToBean<MonthTransaction> csvReader = new CsvToBeanBuilder<MonthTransaction>(
            reader
         )
            .withType(MonthTransaction.class)
            .withSeparator(',')
            .withIgnoreLeadingWhiteSpace(true)
            .withIgnoreEmptyLine(true)
            .withMappingStrategy(mappingStrategy)
            .build();

         List<MonthTransaction> results = csvReader.parse();
         render(results);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   }

   public static void render(List<MonthTransaction> arr) {
      float allExpenses = 0;
      float allIncome = 0;
      float percentsByLastYear = 0;
      float cashbackByLastYear = 0;

      String date;
      for (MonthTransaction monthTransaction : arr) {
         allExpenses += monthTransaction.getExpenses();
         allIncome += monthTransaction.getIncome();

         date = monthTransaction.getDate();
         if (
            date.charAt(date.length() - 1) == '2' &&
            date.charAt(date.length() - 2) == '2'
         ) {
            percentsByLastYear += monthTransaction.getPercents();
            cashbackByLastYear += monthTransaction.getCashback();
         }
      }

      System.out.println(
         "Сумма расходов: " +
         allExpenses +
         " руб.\n" +
         "Сумма доходов: " +
         allIncome +
         " руб.\n\n" +
         "Накопленные проценты за 2022 год: " +
         percentsByLastYear +
         " руб.\n" +
         "Накопленный кэшбэк за 2022 год: " +
         cashbackByLastYear +
         " руб."
      );
   }
}
