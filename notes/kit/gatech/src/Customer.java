package edu.gatech.streamingwars;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Date;

public class Customer//should tie to the Stream class and demo class somehow
{
    //variables
    private int accountNumber = 0;
    private String belongsToDemo = "";

    //constructor
    public Customer(){}

    //methods

    //add a new customer. renamed method
    public void addNewCustomer(int accountNumber, String belongsToDemo)//assuming 1 account = 1 demo
    {

    }

    //update customer info
    public void updateCustomerInfo(int accountNumber, String belongsToDemo)//assuming 1 account = 1 demo
    {

    }

    //get the customer info
    public Customer getCustomerInfo(int accountNumber)//removed 1 arg
    {
        return new Customer();//todo
    }

}//end class
