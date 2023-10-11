package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.MeasureSpecification;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author parisek
 */
public class MeasureNameValidator {
    
    public static String isValid (List<MeasureSpecification> measures){
        String result = "";
        Set tmp=new HashSet();
        for(MeasureSpecification m : measures){
            String meauserName = m.getUniqueName();
            if(tmp.contains(meauserName)){
                result = meauserName;
                break;
            }
            tmp.add(meauserName);
        }
        return result;        
    }    
}
