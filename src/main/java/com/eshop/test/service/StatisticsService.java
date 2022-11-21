package com.eshop.service;

import com.eshop.dto.MonthlyDataDTO;
import com.eshop.dto.SummeryDTO;
import com.eshop.model.OrderApp;
import com.eshop.model.Product;
import com.eshop.model.UserApp;
import com.eshop.repository.BrandRepository;
import com.eshop.repository.OrderRepository;
import com.eshop.repository.ProductRepository;
import com.eshop.repository.UserAppRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserAppRepository userAppRepository;
    private final BrandRepository brandRepository;
    final String[] monthsArray = {"Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};



    public StatisticsService(ProductRepository productRepository, OrderRepository orderRepository,
                             UserAppRepository userAppRepository, BrandRepository brandRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userAppRepository = userAppRepository;
        this.brandRepository = brandRepository;
    }

    public SummeryDTO getModelSummery(){
        long products = productRepository.findAll().stream().mapToLong(Product::getQuantityInDepot).sum();
        long orders = orderRepository.findAll().stream().mapToLong(OrderApp::getQuantity).sum();
        int users = userAppRepository.findAll().size();
        int categories = 5;
        int brands = brandRepository.findAll().size();
        return new SummeryDTO(products, orders, categories, users, brands);
    }

    public Collection<OrderApp> getAllOrders(){
        return orderRepository.findAll();
    }

    public Collection<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Collection<UserApp> getAllUsers(){
        return userAppRepository.findAll();
    }

    public MonthlyDataDTO totalOrdersMonthly(){
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();
        int year = LocalDate.now().getYear(); // current year

        // current month
        String lastMonth = findCurrentMonth();
        months.add(lastMonth);
        // calculate the total orders of the current month
        int currentYear = year;
        int dataOfLastMonth = getAllOrders().stream().filter(item ->
                item.getOrderDate().getYear() == currentYear && item.getOrderDate().getMonth().toString().toLowerCase().startsWith(lastMonth))
                .collect(Collectors.toList())
                .stream().mapToInt(OrderApp::getQuantity).sum();

        // add the total orders of the current month into data array
        data.add(dataOfLastMonth);

        // find the current month index in the monthsArray
        int index = findIndexOfCurrentMonth(lastMonth);

        // get the name of the last 11 months with their data
        for(int i = 1; i <= 11; i++){
            index--;
            if(index < 0){
                index = 11;
                year--;
            }
            int tempIndex = index;
            int tempCurrentYear = year;
            int totalOrders = getAllOrders().stream().filter(item -> item.getOrderDate().getYear() == tempCurrentYear &&
                    item.getOrderDate().getMonth().toString()
                            .toLowerCase().startsWith(monthsArray[tempIndex].toLowerCase()))
                    .mapToInt(OrderApp::getQuantity).sum();
            // add the total orders of the month to data array
            data.add(totalOrders);
            // add the month name to the month array
            months.add(monthsArray[index].substring(0, 3));


        }
        return new MonthlyDataDTO(months, data);
    }

    public MonthlyDataDTO totalJoinedUserMonthly(){
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();
        int year = LocalDate.now().getYear(); // current year

        // current month
        String lastMonth = findCurrentMonth();
        months.add(lastMonth);

        // calculate the total joined users of the current month
        int currentYear = year;
        int dataOfLastMonth = (int) getAllUsers().stream().filter(item ->
                item.getJoinedDate().getYear() == currentYear && item.getJoinedDate().getMonth().toString().toLowerCase().startsWith(lastMonth)).count();

        // add the total orders of the current month into data array
        data.add(dataOfLastMonth);

        // find the current month index in the monthsArray
        int index = findIndexOfCurrentMonth(lastMonth);

        // get the name of the last 11 months with their data
        for(int i = 1; i <= 11; i++){
            index--;
            if(index < 0){
                index = 11;
                year--;
            }
            int tempIndex = index;
            int tempCurrentYear = year;
            int totalUsers = (int) getAllUsers().stream().filter(item -> item.getJoinedDate().getYear() == tempCurrentYear &&
                    item.getJoinedDate().getMonth().toString()
                            .toLowerCase().startsWith(monthsArray[tempIndex].toLowerCase())).count();
            // add the total orders of the month to data array
            data.add(totalUsers);
            // add the month name to the month array
            months.add(monthsArray[index].substring(0, 3));

        }
        return new MonthlyDataDTO(months, data);

    }

    public MonthlyDataDTO totalAddedProductsMonthly(){
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        // current month
        String lastMonth = findCurrentMonth();
        // current year
        int year = LocalDate.now().getYear();
        months.add(lastMonth);

        // calculate the total joined users of the current month
        int currentYear = year;
        int dataOfLastMonth = (int) getAllProducts().stream().filter(item ->
                item.getUpdateInDepot().getYear() == currentYear && item.getUpdateInDepot().getMonth()
                        .toString().toLowerCase().startsWith(lastMonth)).mapToLong(Product::getQuantityInDepot).sum();

        // add the total added products of the current month into data array
        data.add(dataOfLastMonth);

        // find the current month index in the monthsArray
        int index = findIndexOfCurrentMonth(lastMonth);

        // get the name of the last 11 months with their data
        for(int i = 1; i <= 11; i++){
            index--;
            if(index < 0){
                index = 11;
                year--;
            }
            int tempIndex = index;
            int tempCurrentYear = year;
            int totalAddedProducts = (int) getAllProducts().stream().filter(item -> item.getUpdateInDepot().getYear() == tempCurrentYear &&
                         item.getUpdateInDepot().getMonth().toString()
                                .toLowerCase().startsWith(monthsArray[tempIndex].toLowerCase()))
                    .mapToLong(Product::getQuantityInDepot).sum();
            // add the total added products of the month to data array
            data.add(totalAddedProducts);

            // add the month name to the month array
            months.add(monthsArray[index].substring(0, 3));
        }
        return new MonthlyDataDTO(months, data);
    }


    /**********************************
    *          Utility methods        *
    ***********************************/

    public int findIndexOfCurrentMonth(String month){
        int index = -1;
        for(int i =0; i<monthsArray.length; i++){
            if(monthsArray[i].toLowerCase().startsWith(month.toLowerCase())){
                index = i;
                break;
            }
        }
        return index;
    }

    // find the current month name and add it to the month list
    public String findCurrentMonth(){
        return LocalDate.now().getMonth().toString().toLowerCase().substring(0, 3);
    }
}
