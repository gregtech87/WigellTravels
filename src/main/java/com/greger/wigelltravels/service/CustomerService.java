package com.greger.wigelltravels.service;

import com.greger.wigelltravels.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> findAllCustomers();
    void deleteCustomerById(int customerId);
    Customer findCustomerById(int id);
    //    Customer updateCustomer(Customer customers, int id);
    Customer updateCustomer(int id, Customer customers);
//    List<Customer> findCustomersByAddressId(int addressId);

}
