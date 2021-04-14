package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

import static java.lang.System.out;

enum CodeCar {
    C100,
    C200,
    C300,
    C400
}

// Не хочу делать несколько файлов, поэтому класс Car без модификатора public
class Car {
    private CodeCar code_car;
    private int number;
    private int mileage;
    private int additional_param;

    public Car(String str_car) {
        String[] tmp = str_car.split("_");
        this.code_car = CodeCar.valueOf(tmp[0]);

        tmp = tmp[1].split("-");

        this.number = Integer.parseInt(tmp[0]);
        this.mileage = Integer.parseInt(tmp[1]);

        try {
            this.additional_param = Integer.parseInt(tmp[2]);
        } catch (Exception exception) {
            this.additional_param = -1;
        }
    }

    public CodeCar get_code_car() { return code_car; }

    public int get_number() { return number; }

    public int get_mileage() { return mileage; }

    public int get_additional_param() { return additional_param; }

    private double get_fuel_cost() {
        return switch (code_car) {
            case C100 -> 46.1;
            case C300 -> 47.5;
            default -> 48.9;
        };
    }

    private double get_fuel_consumption() {
        return switch (code_car) {
            case C100 -> 12.5;
            case C200 -> 12.;
            case C300 -> 11.5;
            case C400 -> 20.;
        };
    }

    public double get_full_fuel_cost() { return mileage / 100. * get_fuel_cost() * get_fuel_consumption(); }
}

class Main {

    public static void main(String[] args) {
        String[] input_cars = { "C100_1-100",   "C200_1-120-1200",  "C300_1-120-30",    "C400_1-80-20",
                                "C100_2-50",    "C200_2-40-1000",   "C300_2-200-45",    "C400_2-10-20",
                                "C100_3-10",    "C200_3-170-1100",  "C300_3-150-29",    "C400_3-100-28",
                                "C100_1-300",   "C200_1-100-750",   "C300_1-32-15" };

        List<Car> all_cars = new ArrayList<>();
        double[] fuel_cost = new double[4];

        for (String input_car : input_cars) {
            Car car = new Car(input_car);
            all_cars.add(car);

            switch (car.get_code_car()) {
                case C100 -> fuel_cost[0] += car.get_full_fuel_cost();
                case C200 -> fuel_cost[1] += car.get_full_fuel_cost();
                case C300 -> fuel_cost[2] += car.get_full_fuel_cost();
                case C400 -> fuel_cost[3] += car.get_full_fuel_cost();
            }
        }
        
        out.println("Общая стоимость топлива = " + Arrays.stream(fuel_cost).sum());

        for (int i = 0; i < CodeCar.values().length; i++)
            out.println(
                    "Общая стоимость топлива в классе " + get_type_name(CodeCar.values()[i]) + " = " + fuel_cost[i]
            );


        int fuel_cost_max_at = 0;
        int fuel_cost_min_at = 0;

        for (int i = 0; i < fuel_cost.length; i++) {
            fuel_cost_max_at = fuel_cost[i] > fuel_cost[fuel_cost_max_at] ? i : fuel_cost_max_at;
            fuel_cost_min_at = fuel_cost[i] < fuel_cost[fuel_cost_min_at] ? i : fuel_cost_min_at;
        }

        out.println("Наиболее дорогой в обслуживании тип транспорта - это " +
                get_type_name(CodeCar.values()[fuel_cost_max_at]));
        out.println("Наиболее дешёвый в обслуживании тип транспорта - это " +
                get_type_name(CodeCar.values()[fuel_cost_min_at]));

        var all_cars_dict = get_cars_dict(all_cars);
        for (CodeCar code_car : CodeCar.values()) {
            out.println("Детализация по типу " + get_type_name(code_car));

            var needed_cars = all_cars_dict.get(code_car);
            needed_cars.sort(Comparator.comparing(Car::get_mileage).thenComparing((Car::get_additional_param)));

            for (Car car : needed_cars) {
                String additional_param = "отсутствует";
                if (car.get_additional_param() != -1)
                    additional_param = Integer.toString(car.get_additional_param());

                out.println("Гос номер: "       + car.get_number() +
                            ", пробег: "        + car.get_mileage() +
                            ", доп параметр: "  + additional_param
                );
            }
        }
    }

    public static String get_type_name(CodeCar code_car) {
        return switch (code_car) {
            case C100 -> "легковой авто";
            case C200 -> "грузовой авто";
            case C300 -> "пассажирский транспорт";
            case C400 -> "тяжелая техника (краны)";
        };
    }

    public static Map<CodeCar, List<Car>> get_cars_dict(List<Car> all_cars) {
        Map<CodeCar, List<Car>> all_cars_dict = new HashMap<>();
        for (CodeCar code_car: CodeCar.values())
            all_cars_dict.computeIfAbsent(code_car, ignored -> new ArrayList<>());

        for (Car car : all_cars)
            all_cars_dict.get(car.get_code_car()).add(car);

        return all_cars_dict;
    }
}
