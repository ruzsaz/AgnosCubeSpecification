package hu.agnos.cube.specification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author parisek
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LevelSpecification {

    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private String uniqueName;
    
    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private int depth;
    
    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private String codeColumnName;
    
//    @JacksonXmlProperty(isAttribute = true)
//    private String codeColumnSourceName;
//    
    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private String nameColumnName;

//    public String getCodeColumnSourceName() {
//        return codeColumnSourceName == null ? codeColumnName : codeColumnSourceName;
//    }   
//    
    public String getColumnListToOfflineClculatedSelectStatement(boolean isInOfflineCalculatedGroupSelection) {
        String result;
        if (!isInOfflineCalculatedGroupSelection) {
            if (!codeColumnName.equals(nameColumnName)) {
                result = " 'All' as " + codeColumnName + ", 'All' as " + nameColumnName;
            } else {
                result = " 'All' as " + codeColumnName;
            }
        } else {
            result = this.codeColumnName;
            if (!codeColumnName.equals(nameColumnName)) {
                result = result + ", " + nameColumnName;
            }
        }
        return result;
    }

    public String getColumnListToOLAPGroupByStatement(boolean isInOfflineCalculatedGroupSelection) {
        String result = "";
        if (isInOfflineCalculatedGroupSelection) {
            result = this.codeColumnName;
            if (!codeColumnName.equals(nameColumnName)) {
                result = result + ", " + nameColumnName;
            }
        }
        return result;
    }

    @JsonIgnore    
    public List<String> getColumnListToOLAPBuilding() {
        List<String> result = new ArrayList<>();
        result.add(codeColumnName);
        if (!codeColumnName.equals(nameColumnName)) {
            result.add(nameColumnName);
        }
        return result;
    }

    
    
}
