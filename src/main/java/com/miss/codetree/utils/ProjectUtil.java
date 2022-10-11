package com.miss.codetree.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miss.codetree.constant.CodeProjectConstant;
import com.miss.codetree.entity.CodeProject;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * @author xuanmiss
 */
public class ProjectUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void generateInTreeProject(TreeItem<CodeProject> parentProject) {
        File f = new File(parentProject.getValue().getProjectDir());
        if (f.isDirectory()) {
            findSubDir(f, parentProject);
        }else {
            throw new RuntimeException("this is not a dir!!!");
        }
    }

    private static void findSubDir(File rootDir, TreeItem<CodeProject> parentProject) {
        File[] files = rootDir.listFiles();
        for(int i = 0; i < files.length; i++ ) {
            if (!files[i].isDirectory() || files[i].getName().startsWith(".")) {
                continue;
            }
            CodeProject codeProject = new CodeProject();
            codeProject.setParentProjectCode(parentProject.getValue().getProjectCode());
            codeProject.setProjectName(files[i].getName());
            codeProject.setProjectDir(files[i].getAbsolutePath());
            codeProject.setProjectCode(UUID.randomUUID().toString().replace("-", ""));
            codeProject.setSubProjectList(new LinkedList<>());
            TreeItem<CodeProject> treeItem = new TreeItem<>(codeProject);
            if (isGitDir(files[i])) {
                codeProject.setProjectType(CodeProjectConstant.PROJECT_TYPE_PROJECT);
                parentProject.getValue().getSubProjectList().add(codeProject);
                parentProject.getChildren().add(treeItem);
//                dirList.add(files[i]);
                continue;
            }
            if (files[i].isDirectory()) {
                codeProject.setProjectType(CodeProjectConstant.PROJECT_TYPE_CATALOG);
                parentProject.getValue().getSubProjectList().add(codeProject);
                parentProject.getChildren().add(treeItem);
                findSubDir(files[i], treeItem);
            }

        }
    }

    private static boolean isGitDir(File f) {
        return f.isDirectory() && Arrays.asList(f.list()).contains(".git");
    }
}
