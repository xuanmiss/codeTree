package com.miss.codetree.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author xuanmiss
 */
public class FileUtil {

    public static void main(String[] args) {
        listDirAndCodeProject("/Users/xuanmiss/code_space/ideaProjects");
    }

    private static void listDirAndCodeProject(String s) {
        File f = new File(s);
        LinkedList<File> dirList = new LinkedList<>();
        if (f.isDirectory()) {
            findSubDir(f, dirList);
        }else {
            throw new RuntimeException("this is not a dir!!!");
        }
        Iterator<File> iterable = dirList.iterator();
        while (iterable.hasNext()) {
            System.out.println(iterable.next().getAbsolutePath());
        }
    }

    private static void findSubDir(File rootDir, LinkedList<File> dirList) {
        File[] files = rootDir.listFiles();
        for(int i = 0; i < files.length; i++ ) {
            File f = files[i];
            if (isGitDir(f)) {
                dirList.add(f);
                continue;
            }
            if (files[i].isDirectory()) {
                findSubDir(files[i], dirList);
            }

        }
    }

    private static boolean isGitDir(File f) {
        return f.isDirectory() && Arrays.asList(f.list()).contains(".git");
    }

}
