package org.claros.intouch.calendar.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.claros.intouch.calendar.models.CalendarObjectWrap;

/**
 * @author Umut Gokbayrak
 */
public class Utility {
	/**
	 * Input olarak gelen tarihi parametreye gore ait oldugu gunun basina veya
	 * sonuna gonderir. Ornegin  haftanin ilk gununu buldugumuzda calendar objemizi 
	 * o gunun ilk salisesi 00:00.000 saatine cevirir. Bunun icin isBegin parametresi
	 * true olmalidir.  
	 * @param cal
	 * @param isBegin
	 * @return
	 */
	public static Timestamp getLimitTimestampFromCalendar(Calendar cal, boolean isBegin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		SimpleDateFormat sdfBegin = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");
		SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd 23:59:59.999");

		Calendar calTmp = Calendar.getInstance();
		if (isBegin) {
			Timestamp begin = Timestamp.valueOf(sdfBegin.format(cal.getTime()));
			calTmp.setTime(new Date(begin.getTime()));
			calTmp.add(Calendar.MILLISECOND, -1);
		} else {
			Timestamp end = Timestamp.valueOf(sdfEnd.format(cal.getTime()));
			calTmp.setTime(new Date(end.getTime()));
			calTmp.add(Calendar.MILLISECOND, 1);
		}
		return Timestamp.valueOf(sdf.format(calTmp.getTime()));
	}

	/**
	 * 
	 * @param cal
	 * @param timeVar
	 * @return
	 */
	public static ArrayList getBeginEndTimestamps(Calendar cal, int timeVar) {
		Timestamp begin = null;
		Timestamp end = null;
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		
		switch (timeVar) {
			case Constants.DAY : 
				begin = getLimitTimestampFromCalendar(cal, true);
				end = getLimitTimestampFromCalendar(cal, false);
				break;
			case Constants.WEEK :
				// Eger buraya gelmis olan gun pazartesi degilse pazartesi yapmaliyiz.
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				while (dayOfWeek != Calendar.SUNDAY) {
					cal.add(Calendar.DATE, -1);
					dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				}
				
				Calendar sunday = (Calendar)cal.clone();
				sunday.add(Calendar.DATE, 6);
				begin = getLimitTimestampFromCalendar(cal, true);
				end = getLimitTimestampFromCalendar(sunday, false);
				sunday = null;
				break;
			case Constants.MONTH : 
				cal.set(Calendar.DAY_OF_MONTH, 1);

				Calendar lastDay = (Calendar)cal.clone();
				int l = lastDay.getActualMaximum(Calendar.DAY_OF_MONTH);
				lastDay.set(Calendar.DAY_OF_MONTH, l);

				begin = getLimitTimestampFromCalendar(cal, true);
				end = getLimitTimestampFromCalendar(lastDay, false);
				lastDay = null;
				break;
		}
		
		ArrayList out = new ArrayList();
		out.add(begin);
		out.add(end);
		return out;
	}

	/**
	 * Tekrarlanan olaylar�n belirtilen zaman aral���nda (begin -> end) ger�ekle�ip 
	 * ger�ekle�medi�ini anlamak i�in o ajanda kayd� belirtilen kriter ile 
	 * end tarihinden k���k oldu�u s�rece ileriye do�ru sarmak gereklidir. Bu 
	 * metod bu i�lemi yapar.  
	 * @param tmpApp			ajanda kayd�
	 * @param timeCriteria		ileriye do�ru sar�lan kriter. gun/hafta/ay/y�l vs...
	 * @param ascend			ka�ar ka�ar artt�r�lmal� roll ederken. 
	 * @param begin				hangi zaman aral���na kadar sar�yoruz. ba�lang�� parametresi
	 * @param end				hangi zaman aral���na kadar sar�yoruz. biti� parametresi
	 * @return					sar�lm�� halde ajanda kayd�n� d�nd�r�r
	 */
	public static CalendarObjectWrap rollAppointment(CalendarObjectWrap tmpApp, int timeCriteria, int ascend, Timestamp begin, Timestamp end) {
		Timestamp tmpDate = tmpApp.getRecordDate();

		while (tmpDate.before(begin)) {
			Calendar tmpCal = Calendar.getInstance();
			tmpCal.setTime(new Date(tmpDate.getTime()));
			tmpCal.add(timeCriteria, ascend);
			tmpDate = new Timestamp(tmpCal.getTime().getTime());
		}
		if (tmpDate.before(end)) {
			// tekrarlanan olaylarda olay�n tekrarland��� ve o hafta/ay/y�l i�inde yer ald��� tarihi burada buluyoruz.
			tmpApp.setOccuringDate(tmpDate);
			return tmpApp;
		}
		return null;
	}

	public static CalendarObjectWrap rollAppointmentOnce(CalendarObjectWrap tmpApp, int timeCriteria, int ascend, Timestamp end) {
		Timestamp tmpDate = tmpApp.getOccuringDate();

		Calendar tmpCal = Calendar.getInstance();
		tmpCal.setTime(new Date(tmpDate.getTime()));
		tmpCal.add(timeCriteria, ascend);
		tmpDate = new Timestamp(tmpCal.getTime().getTime());

		if (tmpDate.before(end)) {
			tmpApp.setOccuringDate(tmpDate);
			return tmpApp;
		}
		return null;
	}

	/**
	 * @param cal
	 * @param b
	 * @return
	 */
	public static Timestamp getHourLimitTimestampFromCalendar(Calendar cal, boolean isBegin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		SimpleDateFormat sdfBegin = new SimpleDateFormat("yyyy-MM-dd HH:00:00.000");
		SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd HH:59:59.999");

		Calendar calTmp = Calendar.getInstance();
		if (isBegin) {
			Timestamp begin = Timestamp.valueOf(sdfBegin.format(cal.getTime()));
			calTmp.setTime(new Date(begin.getTime()));
			calTmp.add(Calendar.MILLISECOND, -1);
		} else {
			Timestamp end = Timestamp.valueOf(sdfEnd.format(cal.getTime()));
			calTmp.setTime(new Date(end.getTime()));
			calTmp.add(Calendar.MILLISECOND, 1);
		}
		return Timestamp.valueOf(sdf.format(calTmp.getTime()));
	}
}
