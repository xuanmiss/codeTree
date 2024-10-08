package com.miss.codetree.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class CodeProjectConfig implements Serializable {


    @Serial
    private static final long serialVersionUID;

    static {
        serialVersionUID = -8700267968194398094L;
    }

    private CodeProject codeProject;

    private String selectedProjectCode;


    public CodeProject getCodeProject() {
        return codeProject;
    }

    public void setCodeProject(CodeProject codeProject) {
        this.codeProject = codeProject;
    }

    public String getSelectedProjectCode() {
        return selectedProjectCode;
    }

    public void setSelectedProjectCode(String selectedProjectCode) {
        this.selectedProjectCode = selectedProjectCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeProjectConfig that = (CodeProjectConfig) o;
        return Objects.equals(codeProject, that.codeProject) && Objects.equals(selectedProjectCode, that.selectedProjectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeProject, selectedProjectCode);
    }

    @Override
    public String toString() {
        return "CodeProjectConfig{" +
                "codeProject=" + codeProject +
                ", selectedProjecatCode='" + selectedProjectCode + '\'' +
                '}';
    }
}
