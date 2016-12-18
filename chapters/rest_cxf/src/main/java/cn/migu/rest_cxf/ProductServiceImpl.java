package cn.migu.rest_cxf;

import javax.ws.rs.FormParam;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/12/15.
 */
public class ProductServiceImpl implements ProductService{

    public List<Product> retrieveAllProduct() {
        return null;
    }

    public Product retrieveProductById(long id) {
        return null;
    }

    public List<Product> retrieveProductsByName(@FormParam("name") String name) {
        return null;
    }

    public Product createProduct(Product product) {
        return null;
    }

    public Product updateProductById(long id, Map<String, Object> fieldMap) {
        return null;
    }

    public Product deleteProductById(long id) {
        return null;
    }
}
