package cn.migu.rest_cxf;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import java.util.List;
import java.util.ArrayList;
/**
 * Created by Song on 2016/12/16.
 */
public class JAXRSClient {

    public static void main(String[] args){
        String baseAddress = "http://localhost:8080/ws/rest";

        List<Object> providerList = new ArrayList<Object>();
        providerList.add(new JacksonJsonProvider());

        ProductService productService = JAXRSClientFactory.create(baseAddress, ProductService.class, providerList);
        List<Product> productList = productService.retrieveAllProduct();
        for (Product product : productList){
            System.out.println(product);
        }
    }
}
