package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;


public class Main extends Application {
	private double width = 365;
	private double height = 220;
	
	private Color backgroundColor = Color.rgb(192, 192, 192);
	private Color bodyColor = Color.rgb(128, 0, 255);
	private Color eyeColor = Color.rgb(255, 255, 0);
	private Color earsColor = Color.rgb(0, 0, 255);
	
	private double cy;
	private double cx;
	
	private Group root;
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		root = new Group();
		scene = new Scene(root, width, height);
		scene.setFill(backgroundColor);
		
		cx = width * 0.39;
		cy = height / 7;
						
		appendRectangle(cx, cy, width / 5.9, height / 5.5, bodyColor);
								
		appendRectangle(cx + width / 25, cy + height / 18.5, width / 38, height / 35, eyeColor);		
		appendRectangle(cx + width / 25 + width / 38 + width / 25, cy + height / 21, width / 38, height / 35, eyeColor);
		
		appendPolygon(new Double[] {
				cx, cy,
				cx, 2.0,
				cx - width / 10, cy / 2.5
			});

		cx += width / 5.9;
			
		appendPolygon(new Double[] {
				cx - 2, cy,
				cx + cx / 35, 4.0,
				cx + width / 8, cy
			});
		
		cy = height / 7 + height / 5.5 - 1;
	
		appendRectangle(width / 4, cy, width / 2.2, height / 3.6, bodyColor);
		
		cy += height / 3.6 - 1;
		appendRectangle(width / 3, cy, width / 3.4, height / 10, bodyColor);
		
		cy += height / 10 - 1;
		appendRectangle(width / 3, cy, width / 9, height / 5.5, bodyColor);
		
		cx = width / 3 + 1.9 * width / 9;
		appendRectangle(cx, cy, width / 3 + width / 3.4 - cx, height / 5.5, bodyColor);
		
		cy += height / 5.5 - 1;
		appendRectangle(width / 3, cy, width / 6.5, height / 10, bodyColor);
		appendRectangle(cx, cy, width / 8.5, height / 11, bodyColor);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void appendRectangle(double x, double y, double width, double height, Color color) {
		Rectangle rect = new Rectangle(x, y, width, height);
		rect.setFill(color);
		root.getChildren().add(rect);
	}
	
	private void appendPolygon(Double[] points) {
		Polygon polygon = new Polygon(); 
		polygon.getPoints().addAll(points);
		polygon.setFill(earsColor);
		root.getChildren().add(polygon);
	}
}
