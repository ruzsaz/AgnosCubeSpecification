package hu.agnos.cube.specification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.agnos.cube.specification.util.InfixToPostfixConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author parisek
 */

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class MeasureSpecification {

    @Getter
    @Setter
    @JacksonXmlProperty(isAttribute = true)
    private String uniqueName;

    @Getter
    @Setter
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    
    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private String calculatedFormula;

    @Getter
    @Setter
    @JacksonXmlProperty(isAttribute = true, localName = "isHidden")
    private boolean hidden;   

    
    public void setCalculatedFormula(String calculatedFormula) {
        this.calculatedFormula = InfixToPostfixConverter.convert(calculatedFormula);
    }     
    
     public void changeCalculatedFormula(String calculatedFormula) {
        this.calculatedFormula = calculatedFormula;
    }     
    
    @JsonIgnore    
    public boolean isCalculatedMeasure(){
        return this.calculatedFormula != null;
    } 
    
    @JsonIgnore    
    public boolean isClassical(){
        if(this.calculatedFormula != null){
            return false;
        }
        if(this.type != null && !this.type.equals("Classical")){
            return false;
        }
        return true;
    }
   
}
