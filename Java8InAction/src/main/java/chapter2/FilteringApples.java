package chapter2;


import java.util.*;

/**
 * 行为参数化可以轻松应对不断变化的需求, 这种模式可以把一个行为封装起来,
 * 并通过传递和使用创建的行为将方法的行为参数化. 类似策略设计模式.
 */

public class FilteringApples{

	public static void main(String ... args){

		List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));	


		// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
		List<Apple> greenApples = filterApplesByColor(inventory, "green");
		System.out.println(greenApples);

		// [Apple{color='red', weight=120}]
		List<Apple> redApples = filterApplesByColor(inventory, "red");
		System.out.println(redApples);

		// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
		List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());
		System.out.println(greenApples2);

		// [Apple{color='green', weight=155}]
		List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
		System.out.println(heavyApples);

		// []
		List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
		System.out.println(redAndHeavyApples);

		/**
		 * 创建匿名类来实现ApplePredicate接口,
		 *
		 * 方案5: 使用匿名类.
		 */
		// [Apple{color='red', weight=120}]
		List<Apple> redApples2 = filter(inventory, new ApplePredicate() {
			public boolean test(Apple a){
				return a.getColor().equals("red"); 
			}
		});


		System.out.println(redApples2);




		/**
		 * lambda表达式的实现
		 * 方案6: 使用lambda表达式.
		 */
		List<Apple> myinventory = Arrays.asList(new Apple(80,"green"),
				new Apple(155, "green"),
				new Apple(120, "red"));

		/**
		 * *****这里用的是传递ApplePredicate的实现.*****
		 */
		List<Apple> apples = filter(myinventory, (Apple a) -> "red".equals(a.getColor()));
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
	}

	public static List<Apple> filterGreenApples(List<Apple> inventory){
		List<Apple> result = new ArrayList<>();
		for(Apple apple: inventory){
			if("green".equals(apple.getColor())){
				result.add(apple);
			}
		}
		return result;
	}

	/**
	 * 完全违背了DRY原则, 本方法和下面的方法处理逻辑上完全相同.
	 *
	 * 方案2: 颜色作为参数.
	 * 方案3: 传递多个属性作为参数.
	 * @param inventory
	 * @param color
	 * @return
	 */
	public static List<Apple> filterApplesByColor(List<Apple> inventory, String color){
		List<Apple> result = new ArrayList<>();
		for(Apple apple: inventory){
			if(apple.getColor().equals(color)){
				result.add(apple);
			}
		}
		return result;
	}

	public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight){
		List<Apple> result = new ArrayList<>();
		for(Apple apple: inventory){
			if(apple.getWeight() > weight){
				result.add(apple);
			}
		}
		return result;
	}


	/**
	 * 提供了类似策略模式的处理方案.　把本方法的行为参数化了－－执行传入代码块的逻辑.
	 *
	 * 策略模式: 定义一簇算法, 把它们封装为策略. 然后在运行时选择一个算法.
	 *
	 * 方案4: 行为参数化. 根据抽象条件进行选择.
	 * @param inventory
	 * @param p
	 * @return
	 */
	public static List<Apple> filter(List<Apple> inventory, ApplePredicate p){
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory){
			if(p.test(apple)){
				result.add(apple);
			}
		}
		return result;
	}       

	public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color){
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String toString() {
			return "Apple{" +
					"color='" + color + '\'' +
					", weight=" + weight +
					'}';
		}
	}

	/**
	 * 谓词. 根据传入参数的某些属性来对返回一个boolean值.
	 */
	interface ApplePredicate{
		public boolean test(Apple a);
	}

	/**
	 * 三个不同的策略
	 * 当使用这种策略的时候,相当于是通过一个实现了test()方法的对象来传递boolean表达式.
	 * 所以该
	 */
	static class AppleWeightPredicate implements ApplePredicate{
		public boolean test(Apple apple){
			return apple.getWeight() > 150; 
		}
	}
	static class AppleColorPredicate implements ApplePredicate{
		public boolean test(Apple apple){
			return "green".equals(apple.getColor());
		}
	}

	static class AppleRedAndHeavyPredicate implements ApplePredicate{
		public boolean test(Apple apple){
			return "red".equals(apple.getColor()) 
					&& apple.getWeight() > 150; 
		}
	}


	/**
	 * 另一种策略簇.
	 */
	interface AppleFormatter {
		String accept(Apple apple);
	}

	static class AppleFancyFormatter implements AppleFormatter {

		@Override
		public String accept(Apple apple) {
			String s = apple.getWeight() > 150 ? "heavy" : "light";
			return "A " + s + " " + apple.getColor() + " Apple.";
		}
	}

	static class AppleSimpleFormatter implements AppleFormatter {
		@Override
		public String accept(Apple apple) {
			return "An apple of " + apple.getWeight() + "g";
		}
	}

	/**
	 * 泛化的策略
	 */

	interface Predicate<T> {
		boolean test(T t);
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
		List<T> result = new LinkedList<>();
		for (T t : list) {
			if (predicate.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
}