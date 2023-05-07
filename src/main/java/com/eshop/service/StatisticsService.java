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
    private final String[] monthsArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserAppRepository userAppRepository;
    private final BrandRepository brandRepository;


    public StatisticsService(ProductRepository productRepository, OrderRepository orderRepository,
                             UserAppRepository userAppRepository, BrandRepository brandRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userAppRepository = userAppRepository;
        this.brandRepository = brandRepository;
    }

    public SummeryDTO getModelSummery() {
        long products = productRepository.totalNumberOfProductInDepot();
        long orders = orderRepository.totalNumberOfOrderedProducts();
        long users = userAppRepository.count();
        int categories = 5; // for test purpose
        long brands = brandRepository.count();
        return new SummeryDTO(products, orders, categories, users, brands);
    }

    public Collection<OrderApp> getAllOrders() {
        return orderRepository.findAll();
    }

    public Collection<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Collection<UserApp> getAllUsers() {
        return userAppRepository.findAll();
    }

    public MonthlyDataDTO totalOrdersMonthly() {
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Long> data = new ArrayList<>();
        int currentYear = LocalDate.now().getYear(); // current year

        // current month
        String lastMonth = findCurrentMonth();

        // find the current month index in the monthsArray
        int indexOfCurrentMonth = findIndexOfCurrentMonth(lastMonth);
        int remainedMonths = 11 - indexOfCurrentMonth;
        // if index is greater than zero it means there are remained months to complete the 12 months
        if(remainedMonths > 0){
            for(int i = indexOfCurrentMonth; i >= 0; i--){
                // add the total orders of the current month into data array
                lastMonth = monthsArray[i];
                months.add(lastMonth);
                // fetch the total ordered product in this month
                Long dataOfLastMonth = orderRepository
                        .totalNumberOfOrderedProductInEachMonth(currentYear, lastMonth+"%");
                // add the total number of ordered product in this month
                data.add(dataOfLastMonth);
                System.out.println(currentYear +" : "+ lastMonth);
            }
            currentYear -= 1;
            int counter = 11;
            while(remainedMonths > 0){
                lastMonth = monthsArray[counter];
                System.out.println("while "+currentYear +" : "+ lastMonth);
                months.add(monthsArray[counter--]);
                Long dataOfLastMonth = orderRepository
                        .totalNumberOfOrderedProductInEachMonth(currentYear, lastMonth+"%");
                // add the total number of ordered product in this month
                data.add(dataOfLastMonth);
                remainedMonths--;
            }
        }else {
            for(int i = indexOfCurrentMonth; i >=0; i--){
                // add the total orders of the current month into data array
                lastMonth = monthsArray[i];
                months.add(lastMonth);
                // fetch the total ordered product in this month
                long dataOfLastMonth = orderRepository
                        .totalNumberOfOrderedProductInEachMonth(currentYear, lastMonth+"%");
                // add the total number of ordered product in this month
                data.add(dataOfLastMonth);
            }
        }
        return new MonthlyDataDTO(months, data);
    }

    public MonthlyDataDTO totalJoinedUserMonthly() {
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Long> data = new ArrayList<>();
        int year = LocalDate.now().getYear(); // current year

        // current month
        String lastMonth = findCurrentMonth();
        months.add(lastMonth);

        // calculate the total joined users of the current month
        int currentYear = year;
        long dataOfLastMonth = (int) getAllUsers().stream().filter(item ->
                item.getJoinedDate().getYear() == currentYear && item.getJoinedDate().getMonth().toString().toLowerCase().startsWith(lastMonth)).count();

        // add the total orders of the current month into data array
        data.add(dataOfLastMonth);

        // find the current month index in the monthsArray
        int index = findIndexOfCurrentMonth(lastMonth);

        // get the name of the last 11 months with their data
        for (int i = 1; i <= 11; i++) {
            index--;
            if (index < 0) {
                index = 11;
                year--;
            }
            int tempIndex = index;
            int tempCurrentYear = year;
            long totalUsers = (int) getAllUsers().stream().filter(item -> item.getJoinedDate().getYear() == tempCurrentYear &&
                    item.getJoinedDate().getMonth().toString()
                            .toLowerCase().startsWith(monthsArray[tempIndex].toLowerCase())).count();
            // add the total orders of the month to data array
            data.add(totalUsers);
            // add the month name to the month array
            months.add(monthsArray[index].substring(0, 3));

        }
        return new MonthlyDataDTO(months, data);

    }

    public MonthlyDataDTO totalAddedProductsMonthly() {
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Long> data = new ArrayList<>();

        // current month
        String lastMonth = findCurrentMonth();
        // current year
        int year = LocalDate.now().getYear();
        months.add(lastMonth);

        // calculate the total joined users of the current month
        int currentYear = year;
        long dataOfLastMonth = (int) getAllProducts().stream().filter(item ->
                item.getUpdateInDepot().getYear() == currentYear && item.getUpdateInDepot().getMonth()
                        .toString().toLowerCase().startsWith(lastMonth)).mapToLong(Product::getQuantityInDepot).sum();

        // add the total added products of the current month into data array
        data.add(dataOfLastMonth);

        // find the current month index in the monthsArray
        int index = findIndexOfCurrentMonth(lastMonth);

        // get the name of the last 11 months with their data
        for (int i = 1; i <= 11; i++) {
            index--;
            if (index < 0) {
                index = 11;
                year--;
            }
            int tempIndex = index;
            int tempCurrentYear = year;
            long totalAddedProducts = (int) getAllProducts().stream().filter(item -> item.getUpdateInDepot().getYear() == tempCurrentYear &&
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

    public int findIndexOfCurrentMonth(String month) {
        int index = -1;
        for (int i = 0; i < monthsArray.length; i++) {
            if (monthsArray[i].toLowerCase().startsWith(month.toLowerCase())) {
                index = i;
                break;
            }
        }
        return index;
    }

    // find the current month name and add it to the month list
    public String findCurrentMonth() {
        return LocalDate.now().getMonth().toString().toLowerCase().substring(0, 3);
    }
}
