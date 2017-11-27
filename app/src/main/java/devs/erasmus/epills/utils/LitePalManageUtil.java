package devs.erasmus.epills.utils;

import android.util.Log;
import android.widget.Toast;

import org.litepal.LitePalApplication;
import org.litepal.LitePalBase;
import org.litepal.LitePalDB;
import org.litepal.crud.DataSupport;

import java.util.List;

import devs.erasmus.epills.model.IntakeMoment;
import devs.erasmus.epills.model.Medicine;

/**
 * Created by Lenovo-PC on 23/11/2017.
 */

public class LitePalManageUtil {

    static public void cancelMedicineFromDatabase(Medicine medicine){
        List<Medicine> medicines = DataSupport.where("name = ?", medicine.getName()).find(Medicine.class);

        for(int i=0;i<medicines.size();i++){
            deleteIntakesByMedicine(medicines.get(i));

            medicines.get(i).delete();
        }
    }


    static public void deleteIntakesByMedicine(Medicine medicine){
        List<IntakeMoment> intakes = DataSupport.where("medicineId = " +String.valueOf(medicine.getId())).find(IntakeMoment.class);

        //remove intakes from db
        intakesDelete(intakes);
    }

    static public void deleteIntakesByAlarmId(int alarmId){
        List<IntakeMoment> intakes = DataSupport.where("alarmRequestCode = ?",String.valueOf(alarmId)).find(IntakeMoment.class);

        //remove intakes from db
        intakesDelete(intakes);
    }

    static public void intakesDelete(List<IntakeMoment> intakes){
        for(IntakeMoment intakeMoment : intakes) {
            intakeMoment.delete();
        }
    }
}
