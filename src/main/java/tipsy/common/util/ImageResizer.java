package tipsy.common.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.AdvancedResizeOp.UnsharpenMask;
import com.mortennobel.imagescaling.MultiStepRescaleOp;

public class ImageResizer {

	public static void resize(File inputFile, File outputFile, int scaledWidth) throws IOException {
		// reads input image
		// File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);

		int orgWidth = inputImage.getWidth();
		int orgHeight = inputImage.getHeight();

		double ratio = (double) orgHeight / (double) orgWidth;
		int scaledHeight = (int) (scaledWidth * ratio);
		if (scaledHeight == 0) {
			scaledHeight = 1;
		}

		MultiStepRescaleOp rescale = new MultiStepRescaleOp(scaledWidth, scaledHeight);
		rescale.setUnsharpenMask(UnsharpenMask.Soft);
		BufferedImage outputImage = rescale.filter(inputImage, null);

		// writes to output file
		ImageIO.write(outputImage, "png", outputFile);
	}

	public static void resizeOld(File inputFile, File outputFile, int scaledWidth) throws IOException {
		// reads input image
		//File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);
		
		int orgWidth = inputImage.getWidth();
		int orgHeight = inputImage.getHeight();
		
		double ratio = (double)orgHeight/(double)orgWidth;
        int scaledHeight = (int)(scaledWidth * ratio);
        if(scaledHeight == 0) {
        	scaledHeight = 1;
        }
        
		
		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		// extracts extension of output file
		//String outputImagePath = outputFile.getName();
		//String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

		// writes to output file
		ImageIO.write(outputImage, "png", outputFile);
	}

}
