package learningCalender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateExample {

		private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		public static void main(String[] args) {
			
			Date currentDate = new Date();
			System.out.println(dateFormat.format(currentDate));
			
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			
			c.add(Calendar.MONTH, -1);
			
			Date currentDatePlusOne = c.getTime();
			
			System.out.println(dateFormat.format(currentDatePlusOne));
		}
}
