package cn.migu.framwork.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Song on 2016/11/30.
 */
public final class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     * @param inputStream
     * @return
     */
    public static String getString(ServletInputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        String line;
        if (inputStream != null){
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            try {
                while ((line = br.readLine()) != null){
                    sb.append(line);
                }
            } catch (IOException e) {
                LOGGER.error("get string failure", e);
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }
}
