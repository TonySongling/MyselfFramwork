package cn.migu.chapter2.test;

import cn.migu.chapter2.helper.DatabaseHelper;
import cn.migu.chapter2.model.Customer;
import cn.migu.chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/11/23.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    @Before
    public void init() throws Exception{
        DatabaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    @Test
    public void getCustomerListTest(){
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(2, customerList.size());
    }

    @Test
    public void getCustomerTest(){
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void getCustomerMapListTest(){
        List<Map<String, Object>> customerMapList = customerService.getCustomerMapList();
        Assert.assertNotNull(customerMapList);
    }

    @Test
    public void createCustomerTest(){
        Map<String, Object> filedMap = new HashMap<String, Object>();
        filedMap.put("name", "customer3");
        filedMap.put("contact", "john");
        filedMap.put("telephone", "15226019921");
        filedMap.put("email", "jhon@gmail.com");
        boolean reslt = customerService.createCustomer(filedMap);
        Assert.assertTrue(reslt);
    }

    @Test
    public void deleteCustomerTest(){
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest(){
        long id = 1;
        Map<String, Object> filedMap = new HashMap<String, Object>();
        filedMap.put("email", "jack@migu.com");
        boolean reslt = customerService.updateCustomer(id, filedMap);
        Assert.assertTrue(reslt);
    }
}
