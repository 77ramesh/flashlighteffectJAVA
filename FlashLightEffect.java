package application;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FlashLightEffect {
	
	static BufferedImage src, src2;
	static File file;
	static Point mousePos;
	static int mouseX, mouseY;
	static JFrame frame;
	static BufferedImage srcOut;

	public static void main(String[] args) throws IOException{
		
		System.out.println("Hello, image processing");
		
		frame = new JFrame("Mix images");
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(mix()));
		label.setSize(500, 500);
		
		frame.add(label);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setResizable(false);
		
		printPos();
	}
	
	private static BufferedImage plainBlackImage() {
		
		BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR);
		
		int R = 0, G = 0, B = 0;
		int width = img.getWidth(), height = img.getHeight();
		
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				
				img.setRGB(x,  y, R+G+B/3);
			}
		}
		
		try {
			ImageIO.write(img, "jpg", new File("C:\\\\Users\\\\Ramesh\\\\Desktop\\\\black.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	private static BufferedImage mix() throws IOException{
		
		file = new File("C:\\Users\\Ramesh\\Desktop\\opencv\\test_img.jpg");
		src = ImageIO.read(file);
		src2 = ImageIO.read(new File("C:\\\\Users\\\\Ramesh\\\\Desktop\\\\opencv\\\\test_img.jpg"));
		
		srcOut = new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR);
		
		for(int x=0;x<500;x++) {
			for(int y=0;y<500;y++) {
				
//				if(x%2==0) {
//					srcOut.setRGB(x, y, (int) (src.getRGB(x, y) * 1.0));
//				}
//				else
					srcOut.setRGB(x, y, (int) (src.getRGB(x, y) * 0));
				
				//srcOut.setRGB(x, y, (int) (plainBlackImage().getRGB(x, y)));
			}
		}
		
		return srcOut;
	}

	private static void printPos() {
		
		while(true) {
			
			mousePos = MouseInfo.getPointerInfo().getLocation();
			mouseX = mousePos.x - frame.getLocationOnScreen().x;
            mouseY = mousePos.y - frame.getLocationOnScreen().y;
            
			try {
				TimeUnit.SECONDS.sleep(1 / 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if ((mouseX > 0 && mouseX < 500 ) && (mouseY > 0 && mouseY < 500)) {
                if (mouseX != MouseInfo.getPointerInfo().getLocation().x && mouseY != MouseInfo.getPointerInfo().getLocation().y) {
                   
                    int x = mouseX, y = mouseY;
                    
                	for(int posX=x;posX<200;posX++) {
                		for(int posY=y;posY<200;posY++) {
                			
                			srcOut.setRGB(posX, posY, (int) (src2.getRGB(posX, posY)));
                		}
                	}
                	try {
						ImageIO.write(srcOut, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\flashlighteffect.jpg"));
					} catch (IOException e) {
						e.printStackTrace();
					}
              
                    System.out.println("Mouse Pos: " + mouseX + " " + mouseY);
                    System.out.println("RGB: " + srcOut.getRGB(x, y));
                    
                    mouseX = mousePos.x;
                    mouseY = mousePos.y;
                }
            }
		}
	}
}
