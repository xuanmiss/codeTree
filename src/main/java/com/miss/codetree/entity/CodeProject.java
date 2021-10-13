package com.miss.codetree.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @project: CodeTree
 * @package: com.miss.codetree.entity
 * @author: miss
 * @date: 2021/9/10 11:44
 * @since:
 * @history: 1.2021/9/10 created by miss
 */
public class CodeProject implements Serializable {





    @Serial
    private static final long serialVersionUID;

    static {
        serialVersionUID = -2748788859786288250L;
    }

    // 唯一的编码
    private String projectCode;

    private String projectName;

    private String projectDir;

    private String projectAbstract;

    private String projectBranch;

    private String projectRemote;

    // project|catalog
    private String projectType;

    private LinkedList<CodeProject> subProjectList;

    private String parentProjectCode;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    public String getProjectAbstract() {
        return projectAbstract;
    }

    public void setProjectAbstract(String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }

    public String getProjectBranch() {
        return projectBranch;
    }

    public void setProjectBranch(String projectBranch) {
        this.projectBranch = projectBranch;
    }

    public String getProjectRemote() {
        return projectRemote;
    }

    public void setProjectRemote(String projectRemote) {
        this.projectRemote = projectRemote;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public LinkedList<CodeProject> getSubProjectList() {
        return subProjectList;
    }

    public void setSubProjectList(LinkedList<CodeProject> subProjectList) {
        this.subProjectList = subProjectList;
    }

    public String getParentProjectCode() {
        return parentProjectCode;
    }

    public void setParentProjectCode(String parentProjectCode) {
        this.parentProjectCode = parentProjectCode;
    }

    public CodeProject() {
    }

    public CodeProject(String projectCode, String projectName, String projectDir, String projectType, LinkedList<CodeProject> subProjectList) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.projectDir = projectDir;
        this.projectType = projectType;
        this.subProjectList = subProjectList;
    }

    @Override
    public String toString() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeProject)) return false;
        CodeProject that = (CodeProject) o;
        return Objects.equals(projectCode, that.projectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode);
    }
}
