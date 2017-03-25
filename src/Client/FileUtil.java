package Client;

import java.io.*;

public class FileUtil {
    public static void writeText(String fileName, String text){
        try {
            File path = new File("D:/chatroomlog");
            if(!path.exists()){
                path.mkdirs();
            }
            File file = new File(path, fileName);
            PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
            pw.write(text);
            pw.write("\n");
            pw.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static String readText(String fileName){
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File("D:/chatroomlog/", fileName);
            Reader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);

            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
