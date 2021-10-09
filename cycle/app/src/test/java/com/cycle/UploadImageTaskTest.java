package com.cycle;

import org.jsoup.Connection;
import org.junit.Test;
import com.cycle.data.task.UploadImageTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author cycle.member
 * @date 2019-05-12
 */
public class UploadImageTaskTest {
    @Test
    public void doPostFileRequestTest() {
        File file = new File("/Users/cycle/Downloads/0.jpeg");
        try {
            FileInputStream is = new FileInputStream(file);
            Connection.Response response = UploadImageTask.doPostFileRequest(UploadImageTask.URL, UploadImageTask.KEY, is);
            System.out.println(response.body());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        File file1 = new File("/Users/cycle/Downloads/0.jpeg");
//
//        try {
//            FileInputStream fs1 = new FileInputStream(file1);
//            UploadImageTask.trustEveryone();
//            Connection.Response response = Jsoup.connect(UploadImageTask.URL)
//                    .data("smfile", "0.jpeg", fs1)
//                    .method(Connection.Method.POST)
//                    .ignoreContentType(true)
//                    .execute();
//            System.out.print(response.body());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
