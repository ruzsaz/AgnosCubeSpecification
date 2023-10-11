package hu.agnos.cube.specification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author parisek
 */
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Cube")
public class CubeSpecification {

    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private String cubeUniqueName;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBDriver")
    private String sourceDBDriver;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBURL")
    private String sourceDBURL;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBUser")
    private String sourceDBUser;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBPassword")
    private String sourceDBPassword;

    @JacksonXmlProperty(isAttribute = true)
    private boolean isValid;

    @Getter
    @JacksonXmlElementWrapper(localName = "Measures")
    @JacksonXmlProperty(localName = "Measure")
    private List<MeasureSpecification> measures;

    @Getter
    @JacksonXmlElementWrapper(localName = "Hierarchies")
    @JacksonXmlProperty(localName = "Hierarchy")
    private final List<HierarchySpecification> hierarchies;

    @Getter
    @JacksonXmlElementWrapper(localName = "Aggregations")
    @JacksonXmlProperty(localName = "Aggregation")
    private final List<AggregationSpecification> aggregations;

    public CubeSpecification() {
        this.measures = new ArrayList<>();
        this.hierarchies = new ArrayList<>();
        this.aggregations = new ArrayList<>();
    }

    public void addHierarchy(HierarchySpecification hierarchy) {
        this.hierarchies.add(hierarchy);
    }

    public void addMeasure(MeasureSpecification measure) {
        this.measures.add(measure);
    }

    public HierarchySpecification getHierarchyByName(String hierarchyName) {
        HierarchySpecification result = null;
        for (HierarchySpecification hier : this.hierarchies) {
            if (hier.getUniqueName().equals(hierarchyName)) {
                result = hier;
                break;
            }
        }
        return result;
    }

    public void addAggregation(AggregationSpecification aggregation) {
        this.aggregations.add(aggregation);
    }

    public AggregationSpecification getAggregationByName(String hierarchyName, String measureName) {
        AggregationSpecification result = null;
        for (AggregationSpecification a : this.aggregations) {
            if (a.getHierarchyName().equals(hierarchyName) && a.getMeasureName().equals(measureName)) {
                result = a;
                break;
            }
        }
        return result;
    }

    public List<AggregationSpecification> getAggregationsByName(String hierarchyName) {
        List<AggregationSpecification> result = new ArrayList<>();
        for (AggregationSpecification a : this.aggregations) {
            if (a.getHierarchyName().equals(hierarchyName)) {
                result.add(a);
            }
        }
        return result;
    }

    public String getAggregationFunctionByName(String hierarchyName, String measureName) {
        AggregationSpecification agg = getAggregationByName(hierarchyName, measureName);
        if (agg != null) {
            return agg.getAggregationFunction();
        } else {
            return "sum";
        }
    }

    @JsonIgnore
    public List<String> getIsOfflineCalculatedHierarchyList() {
        List<String> result = new ArrayList<>();

        for (HierarchySpecification hier : this.hierarchies) {
            if (hier.isOfflineCalculated()) {
                if (!result.contains(hier.getUniqueName())) {
                    result.add(hier.getUniqueName());
                }
            }
        }

        for (AggregationSpecification agg : this.aggregations) {
            if (!agg.getAggregationFunction().equalsIgnoreCase("sum")) {
                if (!result.contains(agg.getHierarchyName())) {
                    result.add(agg.getHierarchyName());
                }
            }
        }
        return result;
    }

    @JsonIgnore
    public List<String> getDistinctHierarchyColumnList() {
        List<String> result = new ArrayList<>();
        for (HierarchySpecification hier : this.getHierarchies()) {
            for (LevelSpecification level : hier.getLevels()) {
                if (!result.contains(level.getCodeColumnSourceName())) {
                    result.add(level.getCodeColumnSourceName());
                }
                if (!level.getNameColumnName().equals(level.getCodeColumnSourceName()) && !result.contains(level.getNameColumnName())) {
                    result.add(level.getNameColumnName());
                }
            }
        }
        return result;
    }

    @JsonIgnore
    public List<String> getDistinctMeasureColumnList() {
        List<String> result = new ArrayList<>();
        for (MeasureSpecification measure : getMeasures()) {
            if (!result.contains(measure.getUniqueName()) && !measure.isCalculatedMeasure()) {
                result.add(measure.getUniqueName());
            }
        }
        return result;
    }

    public void modifyCalculatedFormula(String uniqueName, String calculatedFormula) {
        for (MeasureSpecification m : this.measures) {
            if (m.getUniqueName().equals(uniqueName)) {
                m.changeCalculatedFormula(calculatedFormula);
            }
        }
    }

    @Override
    public String toString() {
        return "CubeSpecification{" + "cubeUniqueName=" + cubeUniqueName + ", sourceDBDriver=" + sourceDBDriver + ", sourceDBURL=" + sourceDBURL + ", sourceDBUser=" + sourceDBUser + ", sourceDBPassword=" + sourceDBPassword + ", isValid=" + isValid + ", measures=" + measures + ", hierarchies=" + hierarchies + ", aggregations=" + aggregations + '}';
    }


}
