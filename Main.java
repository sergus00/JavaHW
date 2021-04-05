package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.lang.System.*;

enum CodeCar {
    C100,
    C200,
    C300,
    C400
}

class Car {
    CodeCar code_car;
    int number;
    int mileage;
    int additional_param;

    static String get_type_name(CodeCar cc) {
        return switch (cc) {
            case C100 -> "легковой авто";
            case C200 -> "грузовой авто";
            case C300 -> "пассажирский транспорт";
            case C400 -> "тяжелая техника (краны)";
        };
    }

    private static double get_fuel_cost(CodeCar cc) {
        return switch (cc) {
            case C100 -> 46.1;
            case C300 -> 47.5;
            default -> 48.9;
        };
    }

    private static double get_fuel_consumption(CodeCar cc) {
        return switch (cc) {
            case C100 -> 12.5;
            case C200 -> 12;
            case C300 -> 11.5;
            case C400 -> 20;
        };
    }

    double get_unit_fuel_cost() {
        return get_unit_fuel_cost(code_car);
    }

    static double get_unit_fuel_cost(CodeCar cc) {
        return get_fuel_cost(cc) * get_fuel_consumption(cc);
    }
}

class Main {

    private static final List<Car> cars = new ArrayList<>();

    private static List<Car> car_info(CodeCar code_car) {
        List<Car> needed_cars = new ArrayList<>();

        for (Car car : cars) if (car.code_car == code_car) needed_cars.add(car);

        needed_cars.sort(Comparator.comparing(Car -> Car.mileage));
        needed_cars.sort(Comparator.comparing(Car -> Car.additional_param));

        return needed_cars;
    }

    private static Car parse_car_str(String str_car) {
        Car car = new Car();

        String[] tmp = str_car.split("_");
        car.code_car = CodeCar.valueOf(tmp[0]);

        tmp = tmp[1].split("-");

        car.number = Integer.parseInt(tmp[0]);
        car.mileage = Integer.parseInt(tmp[1]);

        try {
            car.additional_param = Integer.parseInt(tmp[2]);
        } catch (Exception ignored) {
        }

        return car;
    }

    public static void main(String[] args) {
        String[] input_cars = {"C100_1-100", "C200_1-120-1200", "C300_1-120-30", "C400_1-80-20", "C100_2-50", "C200_2-40-1000", "C300_2-200-45", "C400_2-10-20", "C100_3-10", "C200_3-170-1100", "C300_3-150-29", "C400_3-100-28", "C100_1-300", "C200_1-100-750", "C300_1-32-15"};

        double[] fuel_cost = new double[4];

        for (String input_car : input_cars) {
            Car car = parse_car_str(input_car);
            cars.add(car);

            switch (car.code_car) {
                case C100 -> fuel_cost[0] += car.get_unit_fuel_cost();
                case C200 -> fuel_cost[1] += car.get_unit_fuel_cost();
                case C300 -> fuel_cost[2] += car.get_unit_fuel_cost();
                case C400 -> fuel_cost[3] += car.get_unit_fuel_cost();
            }
        }

        double total_fuel_cost = Arrays.stream(fuel_cost).sum();

        out.println("Общая стоимость топлива = " + total_fuel_cost);

        for (int i = 0; i < CodeCar.values().length; i++)
            out.println("Общая стоимость топлива в классе " +
                        Car.get_type_name(CodeCar.values()[i]) +
                        " = " +
                        fuel_cost[i]
            );

        double min_costs = Double.POSITIVE_INFINITY;
        double max_costs = 0;
        CodeCar min_costs_Car_type = null;
        CodeCar max_costs_Car_type = null;

        for (CodeCar t : CodeCar.values()) {
            double unit_fuel_cost = Car.get_unit_fuel_cost(t);

            if (unit_fuel_cost < min_costs) {
                min_costs = unit_fuel_cost;
                min_costs_Car_type = t;
            } else if (unit_fuel_cost > max_costs) {
                max_costs = unit_fuel_cost;
                max_costs_Car_type = t;
            }

            var needed_cars = car_info(t);

            out.println("Детализация по типу " + Car.get_type_name(t));

            for (Car car : needed_cars) {
                String ap = "отсутствует";
                if (car.additional_param != 0) ap = Integer.toString(car.additional_param);

                out.println("Гос номер: " + car.number +
                            ", пробег: " + car.mileage +
                            " доп параметр: " + ap
                );
            }
        }

        out.println("Наиболее дешёвый в обслуживании тип транспорта - " + Car.get_type_name(min_costs_Car_type));
        out.println("Наиболее дорогой в обслуживании тип транспорта - " + Car.get_type_name(max_costs_Car_type));
    }
}
