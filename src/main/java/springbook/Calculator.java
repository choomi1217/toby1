package springbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer sum(String filePath) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine())!=null){
                sum += Integer.valueOf(line);
            }
            return sum;
        }catch (IOException e){
            throw e;
        }finally {
            if(br != null){
                try {
                    br.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }
}
