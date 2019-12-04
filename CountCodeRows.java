package ***;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : qw
 * @ Project: ***
 * @ Package: ***
 * @ Title 计算项目的代码行数
 * @ Description: 描述（简要描述类的职责、实现方式、使用注意事项等）
 * @ CreateDate: 2019/11/25 14:51
 * @ Version: 1.0
 * @ Copyright: Copyright (c) 2019
 * @ History: 修订历史（历次修订内容、修订人、修订时间等）
 */
public class CountCodeRows {


    List<File> list = new ArrayList<>();
    private int linenumber = 0;

    private FileReader fr = null;
    private BufferedReader br = null;

    void counter(String projectName) {
//        String path = System.getProperty("user.dir");
        String path = CodeRowCount.class.getResource("/").getPath();  // 同下个path
        path = path.substring(0, path.length() - 16) + "/src";
        System.out.println(path);
        File file = new File(path);
        File files[] = null;
        files = file.listFiles();
        addFile(files);
        isDirectory(files);
        readLinePerFile();
        System.out.println("Total:" + linenumber + "行");
    }

    // 判断是否是目录
    void isDirectory(File[] files) {
        for (File s : files) {
            if (s.isDirectory()) {
                File file[] = s.listFiles();
                addFile(file);
                isDirectory(file);
                continue;
            }
        }
    }

    //将src下所有文件组织成list
    void addFile(File file[]) {
        for (int index = 0; index < file.length; index++) {
            list.add(file[index]);
            // System.out.println(list.size());
        }
    }

    //读取非空白行
    void readLinePerFile() {
        try {
            for (File s : list) {
                int yuan = linenumber;
                if (s.isDirectory()) {
                    continue;
                }
                fr = new FileReader(s);
                br = new BufferedReader(fr);
                String i = "";
                while ((i = br.readLine()) != null) {
                    if (isBlankLine(i)){
                        linenumber++;
                    }
                }
                System.out.print(s.getName());
                System.out.println("\t\t有" + (linenumber - yuan) + "行");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                }
            }
        }
    }

    //是否是空行
    boolean isBlankLine(String i) {
        if (i.trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String args[]) {
        CodeRowCount lc = new CodeRowCount();
        String projectName = "projectName";     //这里传入你的项目名称
        lc.counter(projectName);
    }


}
