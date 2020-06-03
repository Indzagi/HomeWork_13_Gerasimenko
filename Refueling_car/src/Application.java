import java.util.Arrays;
import java.util.Scanner;

/**
 * 1. Создать класс
 * a. Автомобиль. Состоит из приватных полей (Модель, Тип двигателя,
 * Уровень топлива, Уровень масла в двигателе) и методов (Ехать,
 * Стоп, Добавить топливо, Добавить масло)
 * b. Сервисный центр. Состоит из поля (Колонка для масла, Колонка для
 * топлива) и методов (залить топливо, залить масло в двигателе)
 * 2. У всех автомобилей могут быть разные показатели расхода топлива (от 5
 * до 10 за поездку) и масла (от 1 до 2 за поездку). Это значение известно на
 * момент создания автомобиля и в ходе эксплуатации автомобиля будет
 * неизменно
 * 3. Автомобиль не может ехать, если у него нулевой уровень масла или
 * топлива
 * 4. В сервисном центре может быть разное количество колонок с разным
 * показателем количества (от 0 до 100)
 * 5. Сервисный центр может заправлять топливо или менять масло если
 * количество ненулевое. Для этого в качестве параметра надо передать
 * объект-автомобиль и количество требуемого топлива или масла
 */

/**
 * Tesla Model S - с электродвигателем
 * Subaru Impreza WRX STI - бензиновый двигатель
 * Chevrolet Camaro - бензиновый двигатель
 * Renault Logan - дизельный двигатель
 * Daewoo Lanos - газовый двигатель
 *
 *
 * На заправке будет 2 колонки для заправки электрокаров
 * 3 бензиновые колонки
 * 2 дизельные колонки
 * 1 газовая колока
 * 2 колонки заправки маслом
 */

public class Application {
    public static String[] modelCar = {"Tesla Model S", "Subaru Impreza WRX STI",
            "Chevrolet Camaro", "Renault Logan", "Daewoo Lanos"};

    public static String[] enginesType = {"Электро", "бензиновый",
            "дизельный", "газовый"};

    public static Car[] carParking = new Car[5];

    public static int fuelLevelStart;
    public static int engineOilLevelStart;

    public static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        mainMethod();
    }


    static void mainMethod() {
        Car selectCar;

        ServiceCenter serviceCenter;

        autoInitialization(modelCar, enginesType, carParking);

        serviceCenter = serviceCentreInitialization();

        goodDay();

        selectCar = carSelectionMain(carParking, carSelection());

        fuelLevelStart = selectCar.getFuelLevel();
        engineOilLevelStart = selectCar.getEngineOilLevel();

        controlCar(selectCar, serviceCenter);
    }


    static void goodDay() {
        System.out.println(String.format("%nДобрый день, Вас преветствует программа" +
                        " выбора проката авто, вы можете выбрать" +
                        " один из 5 авто,%n если хотите выбрать %s нажниме - 1" +
                        ",%n для выбора %s нажмите - 2, " +
                        "%n для %s - 3,%n для %s - 4,%n для %s - 5.",
                modelCar[0], modelCar[1], modelCar[2], modelCar[3], modelCar[4]));
    }


    static void controlCar(Car selectCar, ServiceCenter serviceCenter) {
        boolean tempbool = true;
        String where;

        System.out.println("*Совет:Для заправки авто введите - \"на заправку\"," +
                " для выхода из авто и окончания поездки введите - \"выйти\"*");

        while (tempbool) {
            System.out.println("Куда поедем?");

            Scanner scanner = new Scanner(System.in);
            where = scanner.nextLine();

            if (where.equals("Выйти") || where.equals("выйти")) {

                System.out.println("Вы вышли из авто, до свидания!)");
                tempbool = false;

            } else if (where.equals("на заправку") || where.equals("На заправку")) {

                refuelSelectCar(selectCar, serviceCenter);

            } else {
                selectCar.go(where);
            }
        }
    }


    static void refuelSelectCar(Car selectCar, ServiceCenter serviceCenter) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите - \"топливо\", чтобы заправится," +
                " или - \"масло\", чтобы долить масла.");

        String whatDoYouWantToDo = scanner.nextLine();

        if (whatDoYouWantToDo.equals("топливо")) {

            selectCar.refillFuel(serviceCenter, selectCar);

            System.out.println(String.format("Мы заправили авто до уровня топлива %d",
                    selectCar.getFuelLevel()));

            DoYouWantToAddOil(serviceCenter, selectCar);

        } else if (whatDoYouWantToDo.equals("масло")) {

            selectCar.refillOil(serviceCenter, selectCar);

            System.out.println(String.format("Мы долили масла в авто до уровня %d",
                    selectCar.getEngineOilLevel()));

            DoYouWantToAddFuel(serviceCenter, selectCar);
        } else {
            System.out.println("Вы введи неправильный тип жидкости.... ");

            refuelSelectCar(selectCar, serviceCenter);
        }
    }


    static void DoYouWantToAddOil(ServiceCenter serviceCenter, Car selectCar) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Хотите долить масла?");
        String oilWant = scanner.nextLine();

        if (oilWant.equals("Да") || oilWant.equals("да")) {

            selectCar.refillOil(serviceCenter, selectCar);

            System.out.println(String.format("Мы долили масла в авто до уровня %d",
                    selectCar.getEngineOilLevel()));
        }
    }


    static void DoYouWantToAddFuel(ServiceCenter serviceCenter, Car selectCar) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Хотите долить топлива?");
        String fuelWant = scanner.nextLine();

        if (fuelWant.equals("Да") || fuelWant.equals("да")) {

            selectCar.refillFuel(serviceCenter, selectCar);

            System.out.println(String.format("Мы заправили авто до уровня топлива %d",
                    selectCar.getFuelLevel()));
        }
    }


    static Car carSelectionMain(Car[] carParking, int numberOfCar) {

        Car selectCar = carParking[numberOfCar - 1];
        System.out.println(String.format("Для поездки вы выбрали авто %s," +
                " приятной поездки...", modelCar[numberOfCar - 1]));
        return selectCar;
    }


    static int carSelection() {

        Scanner scanner = new Scanner(System.in);
        boolean tempBool = true;
        int numberOfCar = 0;
        while (tempBool) {

            System.out.println('\n' + "Введите номер машины от 1 до 5");
            numberOfCar = scanner.nextInt();

            if (numberOfCar > 5) {
                System.out.println("Вы ввели не верное число, повторите ввод");

                tempBool = true;
            } else {
                tempBool = false;
            }
        }
        return numberOfCar;
    }


    static ServiceCenter serviceCentreInitialization() {
        System.out.println('\n' + "Заправляем колонки");
        ServiceCenter serviceCenter = new ServiceCenter();

        for (int i = 0; i < serviceCenter.electroColumn.length; i++) {
            serviceCenter.electroColumn[i] =
                    randomNumberOfFuel();
        }
        for (int i = 0; i < serviceCenter.benzineColumn.length; i++) {
            serviceCenter.benzineColumn[i] =
                    randomNumberOfFuel();
        }
        for (int i = 0; i < serviceCenter.dieselColumn.length; i++) {
            serviceCenter.dieselColumn[i] =
                    randomNumberOfFuel();
        }
        for (int i = 0; i < serviceCenter.gasColumn.length; i++) {
            serviceCenter.gasColumn[i] =
                    randomNumberOfFuel();
        }
        for (int i = 0; i < serviceCenter.oilColumn.length; i++) {
            serviceCenter.oilColumn[i] =
                    randomNumberOfFuel();
        }
        System.out.println(Arrays.toString(serviceCenter.electroColumn));
        System.out.println(Arrays.toString(serviceCenter.benzineColumn));
        System.out.println(Arrays.toString(serviceCenter.dieselColumn));
        System.out.println(Arrays.toString(serviceCenter.gasColumn));
        System.out.println(Arrays.toString(serviceCenter.oilColumn));
        return serviceCenter;
    }


    static int randomNumberOfFuel() {
        int typeOfFuel;
        return (int) (Math.random() * 100);
    }


    static Car[] autoInitialization(String[] modelCar, String[] enginesType,
                                    Car[] carParking) {

        for (int i = 0; i < carParking.length; i++) {

            if (modelCar[i].equals(modelCar[0])) {

                carParking[i] = new Car(modelCar[i], enginesType[0], 100,
                        20, 10, 2);

            } else if (modelCar[i].equals(modelCar[1])) {

                carParking[i] = new Car(modelCar[i], enginesType[1], 80,
                        20, 8, 2);

            } else if (modelCar[i].equals(modelCar[2])) {

                carParking[i] = new Car(modelCar[i], enginesType[1], 70,
                        20, 10, 2);

            } else if (modelCar[i].equals(modelCar[3])) {

                carParking[i] = new Car(modelCar[i], enginesType[2], 50,
                        10, 7, 1);

            } else if (modelCar[i].equals(modelCar[4])) {

                carParking[i] = new Car(modelCar[i], enginesType[3], 40,
                        6, 6, 1);
            }
        }
        return carParking;
    }
}
