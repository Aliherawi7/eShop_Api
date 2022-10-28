package com.eshop.service;

import com.eshop.dto.MonthlyDataDTO;
import com.eshop.dto.SummeryDTO;
import com.eshop.model.OrderApp;
import com.eshop.model.Product;
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
        int products = productRepository.findAll().size();
        int orders = orderRepository.findAll().size();
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

    public MonthlyDataDTO totalOrdersMonthly(){
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();
        int year = LocalDate.now().getYear(); // current year

        // find the current month name and add it to the month list
        String lastMonth = LocalDate.now().getMonth().toString().toLowerCase().substring(0, 3);
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
        int index = -1;
        for(int i =0; i<monthsArray.length; i++){
            if(monthsArray[i].toLowerCase().startsWith(lastMonth.toLowerCase())){
                index = i;
                break;
            }
        }

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

}
