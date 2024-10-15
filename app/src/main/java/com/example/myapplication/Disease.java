package com.example.myapplication;

public class Disease {
    private String d_name;
    private String d_definition;
    private String d_symptoms;
    private String d_eat;
    private String d_medical_department;

    // Getter와 Setter 메서드
    public String getName() { return d_name; }
    public void setName(String d_name) { this.d_name = d_name; }

    public String getDefinition() { return d_definition; }
    public void setDefinition(String d_definition) { this.d_definition = d_definition; }

    public String getSymptoms() { return d_symptoms; }
    public void setSymptoms(String d_symptoms) { this.d_symptoms = d_symptoms; }

    public String getEat() { return d_eat; }
    public void setEat(String d_eat) { this.d_eat = d_eat; }

    public String getMedicalDepartment() { return d_medical_department; }
    public void setMedicalDepartment(String d_medical_department) { this.d_medical_department = d_medical_department; }
}
