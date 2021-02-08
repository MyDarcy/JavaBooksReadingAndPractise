package com.darcy.java8.c95;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *  Instant：表示时刻，不直接对应年月日信息，需要通过时区转换；
 *  LocalDateTime：表示与时区无关的日期和时间，不直接对应时刻，需要通过时区转换；
 *  ZoneId/ZoneOffset：表示时区；
 *  LocalDate：表示与时区无关的日期，与LocalDateTime相比，只有日期，没有时间信息；
 *  LocalTime：表示与时区无关的时间，与LocalDateTime相比，只有时间，没有日期信息；
 *  ZonedDateTime：表示特定时区的日期和时间。
 *
 *  1. Instant表示时刻
 *  Instant.now()
 *  Instant.ofEpochMilli(System.currentTimeMillis())
 *  Date也表示时刻，Instant和Date可以通过纪元时相互转换
 *
 *  2. LocalDateTime表示与时区无关的日期和时间
 *
 *  3. ZoneId/ZoneOffset
 *  LocalDateTime不能直接转为时刻Instant，转换需要一个参数ZoneOffset,ZoneOffset表示相对于格林尼治的时区差，北京是+08:00
 *  给定一个时刻，使用不同时区解读，日历信息是不同的，Instant有方法根据时区返回一个ZonedDateTime
 *  ZoneOffset是ZoneId的子类，可以根据时区差构造。
 *
 *  4. 可以认为LocalDateTime由两部分组成，一部分是日期LocalDate，另一部分是时间LocalTime
 *
 *  5. ZonedDateTime表示特定时区的日期和时间
 *  Local-DateTime内部不会记录时区信息，只会单纯记录年月日时分秒等信息，而ZonedDateTime除了记录日历信息，还会记录时区，它的其他大部分构建方法都需要显式传递时区
 *  ZonedDateTime可以直接转换为Instant
 *
 *  6. 修改时期和时间有两种方式，一种是直接设置绝对值，另一种是在现有值的基础上进行相对增减操作，
 *  Java 8的大部分类都支持这两种方式。
 *  另外，Java 8的大部分类都是不可变类，修改操作是通过创建并返回新对象来实现的，原对象本身不会变。
 *
 *
 */
public class DateUtils {

	public static final DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void createInstant() {
		// instant创建方式
		Instant instant1 = Instant.now();
		Instant instant2 = Instant.ofEpochMilli(System.currentTimeMillis());
		System.out.println(instant1);
		System.out.println(instant2);
		// instant & date 互转
		Instant.ofEpochMilli(new Date().getTime());
		new Date(instant1.toEpochMilli());
	}

	public static void createLocalDate() {
		// LocalDate创建方式
		LocalDate localDate1 = LocalDate.now();
		LocalDate localDate2 = LocalDate.of(2021, 2, 7);
		System.out.println(localDate1);
		System.out.println(localDate2);
		// get
		System.out.println(localDate1.getYear());
		System.out.println(localDate1.getMonthValue());
		System.out.println(localDate1.getMonth()); // Month
		System.out.println(localDate1.getDayOfMonth());
		System.out.println(localDate1.getDayOfWeek()); // DayOfWeek
		System.out.println(localDate1.getDayOfYear());
	}

	public static void createZone() {
		// 转换一个LocalDateTime为北京的时刻
		Instant instantFromBeijingNow = LocalDateTime.now().toInstant(ZoneOffset.of("+08:00"));
		System.out.println(instantFromBeijingNow);
		ZoneId beijingZoneId = ZoneId.of("GMT+08:00");
		// 给定一个时刻，使用不同时区解读，日历信息是不同的
		System.out.println(instantFromBeijingNow.atZone(ZoneId.of("GMT+00:00")));
		System.out.println(instantFromBeijingNow.atZone(ZoneId.of("GMT+01:00")));
	}

	public static void crateZonedDateTime() {
		ZonedDateTime zonedDateTime1 = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
		System.out.println(zonedDateTime1);

		Instant zonedDateTime2Instant = ZonedDateTime.now().toInstant();
		System.out.println(zonedDateTime2Instant);
	}

	public static void localDateTimeDemo(){
		LocalDateTime ldt3 = LocalDateTime.of(2017, 7, 11, 20, 45, 5);
		LocalDateTime ldt2 = LocalDateTime.now();
		
		LocalDateTime ldt = LocalDateTime.of(2017, 7, 11, 20, 45, 5);
		LocalDate ld = ldt.toLocalDate(); //2017-07-11
		LocalTime lt = ldt.toLocalTime(); // 20:45:05
	}
	
	public static void localDateDemo(){
	    //表示2017年7月11日
	    LocalDate ld = LocalDate.of(2017, 7, 11);


	    //当前时刻按系统默认时区解读的日期
	    LocalDate now = LocalDate.now();

	  //LocalDate加上时间，结果为2017-07-11 21:18:39
	    LocalDateTime ldt2 = ld.atTime(21, 18, 39);
	}
	
	public static void localTimeDemo(){
		 //表示21点10分34秒
	    LocalTime lt = LocalTime.of(21, 10, 34);

	    //当前时刻按系统默认时区解读的时间
	    LocalTime time = LocalTime.now();
	    
	  //LocalTime加上日期，结果为2016-03-24 20:45:05
	    LocalDateTime ldt3 = lt.atDate(LocalDate.of(2016, 3, 24));
	}
	
	public static void formatDemo(){
		LocalDateTime ldt = LocalDateTime.of(2016,8,18,14,20,45);
		System.out.println(datetimeFormatter.format(ldt));
		
		
	}
	
	public static void parseDemo(){
		String str = "2016-08-18 14:20:45";
		LocalDateTime ldt = LocalDateTime.parse(str, datetimeFormatter);
	}
	
	public static void adjustDateTime1(){
//		调整时间为下午3点20
	    LocalDateTime ldt = LocalDateTime.now();
	    ldt = ldt.withHour(15).withMinute(20).withSecond(0).withNano(0);
	    
//	    LocalDateTime ldt = LocalDateTime.now();
//	    ldt = ldt.toLocalDate().atTime(15, 20);

	}
	
	public static void adjustDateTime2(){
//		三小时五分钟后
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.plusHours(3).plusMinutes(5);
	}
	
	public static void adjustDateTime3(){
//		今天0点
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.with(ChronoField.MILLI_OF_DAY, 0);     
		
//		LocalDateTime ldt = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
//		LocalDateTime ldt = LocalDate.now().atTime(0, 0);
	}
	
//	下周二上午10点整
	public static void adjustDateTime4(){
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.plusWeeks(1).with(ChronoField.DAY_OF_WEEK, 2)
		    .with(ChronoField.MILLI_OF_DAY, 0).withHour(10);
	}
	
//	下一个周二上午10点整
	public static void adjustDateTime5(){
		LocalDate ld = LocalDate.now();
		LocalDateTime ldt = ld.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(10, 0);
		
//		LocalDate ld = LocalDate.now();
//		if(!ld.getDayOfWeek().equals(DayOfWeek.MONDAY)){
//		    ld = ld.plusWeeks(1);
//		}
//		LocalDateTime ldt = ld.with(ChronoField.DAY_OF_WEEK, 2).atTime(10, 0);
	}
	
	public static void adjustDateTime6(){
//		明天最后一刻
		LocalDateTime ldt = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MAX);
		System.out.println(ldt.format(datetimeFormatter));
//		LocalDateTime ldt = LocalTime.MAX.atDate(LocalDate.now().plusDays(1));
	}
	
//	本月最后一天最后一刻
	public static void adjustDateTime7(){
		LocalDateTime ldt =  LocalDate.now()
		        .with(TemporalAdjusters.lastDayOfMonth())
		        .atTime(LocalTime.MAX);
	}
	
//	下个月第一个周一的下午5点整
	public static void adjustDateTime8(){
		LocalDateTime ldt = LocalDate.now()
		        .plusMonths(1)
		        .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))
		        .atTime(17, 0);
	}
	
	public static void periodDemo(){
		LocalDate ld1 = LocalDate.of(2016, 3, 24);
		LocalDate ld2 = LocalDate.of(2017, 7, 12);
		Period period = Period.between(ld1, ld2);
		System.out.println(period.getYears() + "年"
		        + period.getMonths() + "月" + period.getDays() + "天");
	}
	
	public static void ageByBorn(){
		LocalDate born = LocalDate.of(1990,06,20);
		int year = Period.between(born, LocalDate.now()).getYears();
	}
	
	
	public static void lateMinutes(){
	    long lateMinutes = Duration.between(
	            LocalTime.of(9,0),
	            LocalTime.now()).toMinutes();
	}
	
	public static void zonedDateTimeDemo(){
		ZonedDateTime ldt = ZonedDateTime.now();
		Instant now = ldt.toInstant();
		
	}
	
	public static Instant toInstant(Date date) {
	    return Instant.ofEpochMilli(date.getTime());
	}
	
	public static Date toDate(Instant instant) {
	    return new Date(instant.toEpochMilli());
	}
	
	public static Instant toBeijingInstant(LocalDateTime ldt) {
	    return ldt.toInstant(ZoneOffset.of("+08:00"));
	}
	
	public static Date toDate(LocalDateTime ldt){
	    return new Date(ldt.atZone(ZoneId.systemDefault())
	            .toInstant().toEpochMilli());
	}
	
	public static Calendar toCalendar(ZonedDateTime zdt) {
	    TimeZone tz = TimeZone.getTimeZone(zdt.getZone());
	    Calendar calendar = Calendar.getInstance(tz);
	    calendar.setTimeInMillis(zdt.toInstant().toEpochMilli());
	    return calendar;
	}
	
	public static LocalDateTime toLocalDateTime(Date date) {
	    return LocalDateTime.ofInstant(
	            Instant.ofEpochMilli(date.getTime()),
	            ZoneId.systemDefault());
	}
	
	public static ZonedDateTime toZonedDateTime(Calendar calendar) {
	    ZonedDateTime zdt = ZonedDateTime.ofInstant(
	            Instant.ofEpochMilli(calendar.getTimeInMillis()),
	            calendar.getTimeZone().toZoneId());
	    return zdt;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		adjustDateTime6();
		/**
		 * 2021-02-07T05:30:12.875Z
		 * 2021-02-07
		 * 2021-02-07T13:30:12.876+08:00[Asia/Shanghai]
		 */
		System.out.println();
		System.out.println(Instant.now());
		System.out.println(LocalDate.now());
		System.out.println(ZonedDateTime.now());

		System.out.println();
		createInstant();
		System.out.println();
		createLocalDate();
		System.out.println();
		createZone();
		System.out.println();
		crateZonedDateTime();
	}

}
