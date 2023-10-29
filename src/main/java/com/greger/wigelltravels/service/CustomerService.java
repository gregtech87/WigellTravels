package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllCustomers();
    Customer findCustomerById(int id);
    Customer saveCustomer(Customer customer);
    void deleteCustomerById(int id);
    Customer updateCustomer(int id, Customer customer);
}
