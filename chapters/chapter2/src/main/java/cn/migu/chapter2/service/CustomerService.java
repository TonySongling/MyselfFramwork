package cn.migu.chapter2.service;

import cn.migu.chapter2.helper.DatabaseHelper;
import cn.migu.chapter2.model.Customer;
import cn.migu.chapter2.util.PropsUtil;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/11/23.
 */
public class CustomerService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PropsUtil.class);

    public List<Customer> getCustomerList() {
        //Connection conn = null;
        //List<Customer> customerList = new ArrayList<Customer>();
            //conn = DatabaseHelper.getConnection();
            /*PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelephonr(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }*/
            //customerList = DatabaseHelper.QueryEntityList(conn, Customer.class, sql);
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.QueryEntityList(Customer.class, sql);
    }

    public Customer getCustomer(long id){
        String sql = "select * from customer where id = " + id;
        return DatabaseHelper.QueryEntity(Customer.class, sql);
    }

    public List<Map<String, Object>> getCustomerMapList(){
        String sql = "select * from customer";
        return DatabaseHelper.executeQuery(sql);
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap){
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(long id){
        return  DatabaseHelper.deleteEntity(Customer.class, id);
    }

    public boolean createCustomer(Map<String, Object> filedMap){
        return DatabaseHelper.insertEntity(Customer.class, filedMap);
    }
}
