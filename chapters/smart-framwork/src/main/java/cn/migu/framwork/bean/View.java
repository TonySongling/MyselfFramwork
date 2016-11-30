package cn.migu.framwork.bean;

import java.util.Map;
import java.util.HashMap;
/**
 * 返回视图对象
 * Created by Song on 2016/11/30.
 */
public class View {

    //视图路径
    private String path;

    //模型数据
    private Map<String, Object> model;

    public View(String path){
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value){
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
