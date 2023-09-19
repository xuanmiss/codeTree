package com.miss.codetree.constant;

import com.miss.codetree.CodeTreeApplication;
import javafx.scene.image.Image;

import java.util.Objects;

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

    public static final Image addImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/add.png")));

    public static final Image pressedAddImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/pressedAdd.png")));

    public static final Image foldImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/foldImage.png")));

    public static final Image expandImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/expandImage.png")));

    public static final Image projectImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/project.png")));

    public static final Image openDirImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/opendirImage.png")));

    public static final Image presOpenDirImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/presOpenDirImage.png")));

    public static final Image expandTreeImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/expandTree.png")));

    public static final Image packupTreeImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/packupTree.png")));

    public static final Image clearTreeImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/clear.png")));

    public static final Image codeTreeImage = new Image(Objects.requireNonNull(CodeTreeApplication.class.getResourceAsStream("/icon/codeTree-blue.png")));
}
