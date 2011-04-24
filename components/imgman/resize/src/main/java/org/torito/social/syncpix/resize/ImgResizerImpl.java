/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.torito.social.syncpix.resize;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;





/**
 * @author torito
 *
 */
public class ImgResizerImpl {

	/* (non-Javadoc)
	 * @see org.torito.social.syncpix.resize.ImgResizer#resize(java.io.InputStream, int, int)
	 */
	public InputStream resize(InputStream is, int h, int w) throws IOException {
		Image image = ImageIO.read(is);
		BufferedImage scaledBI = createResizedCopy(image, h, w);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(scaledBI, "jpg", os);
		InputStream ris = new ByteArrayInputStream(os.toByteArray());
		return ris;
	}

	/* (non-Javadoc)
	 * @see org.torito.social.syncpix.resize.ImgResizer#resize(java.io.File, int, int)
	 */
	public InputStream resize(File is, int h, int w) throws IOException {
		Image image = ImageIO.read(is);
		BufferedImage scaledBI = createResizedCopy(image, h, w);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(scaledBI, "jpg", os);
		InputStream ris = new ByteArrayInputStream(os.toByteArray());
		return ris;
	}

	BufferedImage createResizedCopy(Image originalImage, 
			int scaledWidth, int scaledHeight)	{
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = scaledBI.createGraphics();
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
		g.dispose();
		return scaledBI;
	}

}
