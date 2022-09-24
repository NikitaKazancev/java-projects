package com.pw23;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonthTransaction {

   private String id;
   private String date;
   private String startingBalance;
   private String income;
   private String expenses;
   private String percents;
   private String cashback;
   private String finalBalance;

   private float getNumber(String str) {
      Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?");
      Matcher matcher = pattern.matcher(str);
      matcher.find();
      return Float.valueOf(matcher.group());
   }

   public String getDate() {
      return date;
   }

   public float getExpenses() {
      return getNumber(expenses);
   }

   public float getPercents() {
      return getNumber(percents);
   }

   public float getIncome() {
      return getNumber(income);
   }

   public float getCashback() {
      return getNumber(cashback);
   }

   @Override
   public String toString() {
      return (
         "MonthTransaction [cashback=" +
         cashback +
         ", date=" +
         date +
         ", expenses=" +
         expenses +
         ", finalBalance=" +
         finalBalance +
         ", id=" +
         id +
         ", income=" +
         income +
         ", percents=" +
         percents +
         ", startingBalance=" +
         startingBalance +
         "]"
      );
   }
}
