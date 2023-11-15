/**
 * 
 */
/**
 * 
 */
module ExamDeinEv1_MarcosVazquez {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml;
	opens model to javafx.base;
	opens dao to javafx.base;
}