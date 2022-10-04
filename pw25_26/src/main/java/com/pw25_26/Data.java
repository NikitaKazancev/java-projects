package com.pw25_26;

import java.util.ArrayList;
import java.util.Map;

public class Data {

   ArrayList<Line> lines;
   Map<String, ArrayList<String>> stations;

   public Data(ArrayList<Line> lines, Map<String, ArrayList<String>> stations) {
      this.lines = lines;
      this.stations = stations;
   }
}
