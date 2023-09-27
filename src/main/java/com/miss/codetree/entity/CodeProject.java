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

    // 项目描述
    private String projectAbstract;

    // 项目分支
    private String projectBranch;

    // 项目远程仓库地址
    private String projectRemote;

    // project|catalog
    private String projectType;

    // 子项目列表，仅catalog有此值
    private LinkedList<CodeProject> subProjectList;

    // 父项目编码
    private String parentProjectCode;

    // 展开状态
    private boolean expandStatus;

    // 选中状态
    private boolean selectedStatus;

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

    public boolean isExpandStatus() {
        return expandStatus;
    }

    public void setExpandStatus(boolean expandStatus) {
        this.expandStatus = expandStatus;
    }

    public boolean isSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(boolean selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public LinkedList<CodeProject> getSubProjectList() {
        if (subProjectList == null) {
            subProjectList = new LinkedList<>();
        }
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
        return this.projectName;
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
