import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class test {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		List<Double> a=new ArrayList<Double>();
		HashMap<String, List<Double>> b=new HashMap<String, List<Double>>();
		String[] c={"a","b","c"};
		
		a.add((double) 0);
		a.add((double) 1);
		b.put("a", a);

		a.clear();
		a.add((double) 10);
		a.add((double) 11);
		b.put("b", a);

		a.clear();
		a.add((double) 20);
		a.add((double) 21);
		b.put("c", a);
		
		Iterator it = b.keySet().iterator();   
		while(it.hasNext()){
			String d=(String)it.next();
			System.out.println(d);
			System.out.println(b.get(d).get(0)+" "+b.get(d).get(1));
			System.out.println("-----------------------------------");
		}
	}

}
