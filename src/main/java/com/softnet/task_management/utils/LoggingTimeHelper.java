package com.softnet.task_management.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggingTimeHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingTimeHelper.class);


  public static int formatTime(String time) {

    try {
      String[] arr = time.split(" ");
      int hours = Integer.parseInt(arr[0].substring(0, arr[0].length() - 1));
      int mins = Integer.parseInt(arr[1].substring(0, arr[1].length() - 1));

      return (hours * 60) + mins;
    } catch (NumberFormatException ex) {
      LOGGER.error("Error passing integers: {}", ex.getMessage());
    }

    return -1;
  }

}
