import java.util.Scanner;

/**
 * a. Автомобиль. Состоит из приватных полей (Модель, Тип двигателя,
 * Уровень топлива, Уровень масла в двигателе) и методов (Ехать,
 * Стоп, Добавить топливо, Добавить масло)
 * <p>
 * 2. У всех автомобилей могут быть разные показатели расхода топлива (от 5
 * * до 10 за поездку) и масла (от 1 до 2 за поездку). Это значение известно на
 * * момент создания автомобиля и в ходе эксплуатации автомобиля будет
 * * неизменно
 * * 3. Автомобиль не может ехать, если у него нулевой уровень масла или
 * * топлива
 */

public class Car {
    private String modelCar;
    private String enginesType;
    private int fuelLevel;
    private int engineOilLevel;
    private int fuelConsumption;
    private int oilConsumption;


    public Car(String modelCar, String enginesType, int fuelLevel, int engineOilLevel,
               int fuelConsumption, int oilConsumption) {

        System.out.println("Конструируем автомобиль....");
        this.modelCar = modelCar;
        this.enginesType = enginesType;
        this.fuelLevel = fuelLevel;
        this.engineOilLevel = engineOilLevel;
        this.fuelConsumption = fuelConsumption;
        this.oilConsumption = oilConsumption;

        System.out.println(String.format("Создано авто модели %s, тип двигателя = %s," +
                        " с уровнем топлива %d, и уровнем масла %d," +
                        " а так же известен расход топлива %d и " +
                        "расход масла %d на поездку", modelCar, enginesType,
                fuelLevel, engineOilLevel, fuelConsumption, oilConsumption));

    }


    public void go(String where) {

        if (fuelLevel >= fuelConsumption && engineOilLevel >= oilConsumption) {

            System.out.println(String.format("Автомобиль модели %s двигаеться %s ", modelCar, where));

            fuelLevel -= fuelConsumption;
            engineOilLevel -= oilConsumption;

            System.out.println(String.format("Уровень топлива снизился до %d", fuelLevel));
            System.out.println(String.format("Уровень масла снизился до %d", engineOilLevel));
            stop(where);

        } else if (fuelLevel == 0) {

            System.out.println(String.format("Автомобиль модели %s не может" +
                    " двигаеться %s из за отсутствия топлива," +
                    " отправляйтесь на заправку...", modelCar, where));
        } else if (engineOilLevel == 0) {

            System.out.println(String.format("Автомобиль модели %s не может" +
                    " двигаеться %s из за отсутствия масла в двигателе," +
                    " отправляйтесь на заправку...", modelCar, where));
        } else if (fuelLevel < fuelConsumption && fuelLevel > 0) {

            System.out.println(String.format("Автомобиль модели %s не может" +
                    " двигаеться %s из за нехватки топлива," +
                    " отправляйтесь на заправку...", modelCar, where));
        } else if (engineOilLevel < oilConsumption && engineOilLevel > 0) {

            System.out.println(String.format("Автомобиль модели %s не может" +
                    " двигаеться %s из за нехватки масла в двигателе," +
                    " отправляйтесь на заправку...", modelCar, where));
        }
    }


    public String getEnginesType() {
        return enginesType;
    }


    public int getFuelLevel() {
        return fuelLevel;
    }


    public int getEngineOilLevel() {
        return engineOilLevel;
    }


    public void stop(String stopPlace) {
        System.out.println(String.format("Мы приехали %s, остановили авто", stopPlace));
    }


    public Car refillFuel(ServiceCenter serviceCenter, Car selectCar) {

        int[] fuelColumn = serviceCenter.refuel(selectCar);
        int howFuelRefillCar = fuelRequest();
        int i = 0;
        boolean tempBoll = true;

        checkForFuelRequest(howFuelRefillCar, serviceCenter, selectCar);


        while (tempBoll && i < fuelColumn.length) {

            boolean tempBoll2 = true;
            if (fuelColumn[i] > 0 && howFuelRefillCar < fuelColumn[i] &&
                    fuelLevel + howFuelRefillCar <= Application.fuelLevelStart) {

                fuelColumn[i] -= howFuelRefillCar;
                fuelLevel += howFuelRefillCar;

                howFuelRefillCar = 0;

                System.out.println(String.format("Мы заправили авто из колонки под номером %d", i + 1));
                tempBoll = false;
                return selectCar;


            } else if (fuelColumn[i] > 0 && howFuelRefillCar > fuelColumn[i] &&
                    fuelLevel + fuelColumn[i] <= Application.fuelLevelStart) {

                fuelLevel += fuelColumn[i];

                howFuelRefillCar -= fuelColumn[i];

                fuelColumn[i] = 0;

                System.out.println(String.format("Мы заправили авто из колонки" +
                        " под номером %d, но в колонке не хватает топлива для " +
                        "%n заправки до запрашиваемого уровня, проверим другие колонки...", i + 1));


                if (i < fuelColumn.length - 1) {
                    tempBoll2 = false;
                    i++;
                }

            } else if (fuelColumn[i] == 0 && tempBoll2 == true && howFuelRefillCar > 0) {

                int sum = 0;

                for (int j = 0; j < fuelColumn.length; j++) {
                    sum += fuelColumn[j];
                }
                if (sum == 0) {

                    System.out.println(String.format("На заправке ни в" +
                            "одной из колонок не осталось топлива под %s тип двигателя", enginesType));
                    break;

                } else if (sum > 0) {
                    System.out.println("Посмотрим топливо в других колонках");
                    i++;
                }
            }
        }
        return selectCar;
    }


    public int fuelRequest() {

        System.out.println(String.format("Введите количество топлива, " +
                "которое вы хотите залить, напомним, максимальный " +
                "объем бака с топливом = %d", Application.fuelLevelStart));
        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }


    public void checkForFuelRequest(int howFuelRefillCar, ServiceCenter serviceCenter, Car selectCar) {
        if (fuelLevel + howFuelRefillCar > Application.fuelLevelStart) {
            System.out.println("Вы хотите залить больше, чем вмещает бак," +
                    " выберите обьем поменьше...");
            refillFuel(serviceCenter, selectCar);
        }
    }


    public Car refillOil(ServiceCenter serviceCenter, Car selectCar) {

        int[] oilColumn = serviceCenter.addOilToTheEngine(selectCar);
        int howOilRefillCar = oilRequest();
        int i = 0;
        boolean tempBoll = true;

        checkForOilRequest(howOilRefillCar, serviceCenter, selectCar);


        while (tempBoll && i < oilColumn.length) {
            boolean tempBoll2 = true;
            if (oilColumn[i] > 0 && howOilRefillCar < oilColumn[i] &&
                    engineOilLevel + howOilRefillCar <= Application.fuelLevelStart) {

                oilColumn[i] -= howOilRefillCar;
                engineOilLevel += howOilRefillCar;

                howOilRefillCar = 0;

                System.out.println(String.format("Мы залили масло в авто из колонки под номером %d", i + 1));
                tempBoll = false;
                return selectCar;

            } else if (oilColumn[i] > 0 && howOilRefillCar > oilColumn[i] &&
                    engineOilLevel + oilColumn[i] <= Application.fuelLevelStart) {

                engineOilLevel += oilColumn[i];
                howOilRefillCar -= oilColumn[i];
                oilColumn[i] = 0;

                System.out.println(String.format("Мы залили масло в  авто из колонки" +
                        " под номером %d, но в колонке не хватает масла для заливки\" +\n" +
                        "\" до запрашиваемого уровня, проверим другие колонки...", i + 1));

                if (i < oilColumn.length - 1) {
                    tempBoll2 = false;
                    i++;
                }

            } else if (oilColumn[i] == 0 && tempBoll2 == true && howOilRefillCar > 0) {

                int sum = 0;

                for (int j = 0; j < oilColumn.length; j++) {
                    sum += oilColumn[j];
                }
                if (sum == 0) {

                    System.out.println("На заправке ни в из колонок не осталось масла");
                    break;

                } else if (sum > 0) {
                    System.out.println("Посмотрим масло в других колонках");
                    i++;
                }
            }
        }
        return selectCar;
    }


    public int oilRequest() {

        System.out.println(String.format("Введите количество масла, " +
                "которое вы хотите залить, напомним, максимальный " +
                "объем бака масла = %d", Application.engineOilLevelStart));

        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }


    public void checkForOilRequest(int howOilRefillCar, ServiceCenter serviceCenter, Car selectCar) {
        if (engineOilLevel + howOilRefillCar > Application.engineOilLevelStart) {
            System.out.println("Вы хотите залить больше, чем вмещает банк для масла," +
                    " выберите обьем поменьше...");
            refillOil(serviceCenter, selectCar);
        }
    }

}

