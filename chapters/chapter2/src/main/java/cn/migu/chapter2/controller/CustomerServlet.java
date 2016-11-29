package cn.migu.chapter2.controller;

import cn.migu.chapter2.model.Customer;
import cn.migu.chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Song on 2016/11/23.
 */
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet{

    private CustomerService customerService;
    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customerList = customerService.getCustomerList();
        req.setAttribute("customerList", customerList);
        req.getRequestDispatcher("/WEB-INF/View/customer.jsp").forward(req, resp);
    }
}
