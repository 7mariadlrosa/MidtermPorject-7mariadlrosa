package classes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Utils {

    public static Integer calculateYears(LocalDate date){
        Period age = Period.between(date, LocalDate.now());
        return age.getYears();
    }

    public static Integer calculateMonths(LocalDate lastUpdate){
        Period age = Period.between(lastUpdate, LocalDate.now());
        return age.getMonths();
    }
}
