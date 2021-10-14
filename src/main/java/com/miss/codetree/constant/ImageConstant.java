package com.miss.codetree.constant;

import com.miss.codetree.CodeTreeApplication;
import javafx.scene.image.Image;

/**
 * @project: CodeTree
 * @package: com.miss.codetree.constant
 * @author: miss
 * @date: 2021/9/13 15:45
 * @since:
 * @history: 1.2021/9/13 created by miss
 */
public class ImageConstant {


    public ImageConstant() {
        throw new RuntimeException("can't init this class");
    }

    public static final Image addImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/add.png"));

    public static final Image pressedAddImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/pressedAdd.png"));

    public static final Image foldImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/foldImage.png"));

    public static final Image expandImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/expandImage.png"));

    public static final Image projectImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/project.png"));

    public static final Image openDirImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/opendirImage.png"));

    public static final Image presOpenDirImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/presOpenDirImage.png"));

    public static final Image expandTreeImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/expandTree.png"));

    public static final Image packupTreeImage = new Image(CodeTreeApplication.class.getResourceAsStream("/icon/packupTree.png"));
}
