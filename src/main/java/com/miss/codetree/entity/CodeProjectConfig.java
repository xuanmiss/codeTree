package com.miss.codetree.entity;

import java.io.Serial;
import java.io.Serializable;

public class CodeProjectConfig implements Serializable {


    @Serial
    private static final long serialVersionUID;

    static {
        serialVersionUID = -8700267968194398094L;
    }

    private CodeProject codeProject;

    private String selectedProjecatCode;

}
