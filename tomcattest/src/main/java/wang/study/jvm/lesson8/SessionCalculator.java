package wang.study.jvm.lesson8;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class SessionCalculator {
    private Map<String,Integer> counts = new TreeMap<>() ;
    private void init() throws IOException {
        List<String> items = this.parseFromFile();
        this.append(items);
    }
    public List<String> parseFromFile() throws IOException {
        String content = IOUtils.toString(new ClassPathResource("static/session.txt").getInputStream(), Charset.defaultCharset());
        List<String> items = new ArrayList<String>();
        String[] values = content.split("\n");

        for(String value : values){
            if(StringUtils.hasText(value)){
                items.add(value.trim());
            }
        }
        return items;
    }

    public void append(List<String> items){
        for(String item : items){
            long time = Long.parseLong(item)/1000;
            int count = counts.containsKey(time+"")  ? counts.get(time+"") : 0;
            count++;
            counts.put(time+"",count);
        }

    }

    public void pringResult(){
        List<String> keys = new ArrayList<>();
        keys.addAll(counts.keySet());
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                long c1 = counts.get(o1);
                long c2 = counts.get(o2);
                return -1*Long.compare(c1,c2);
            }
        });
        for(String key :keys){
            System.out.println(key+"   "+counts.get(key));
        }

    }

    public static void main(String[] args) throws IOException {
        SessionCalculator calculator = new SessionCalculator();
        calculator.init();
        calculator.pringResult();
    }



}
