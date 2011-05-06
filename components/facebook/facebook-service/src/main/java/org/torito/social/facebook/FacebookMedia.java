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
package org.torito.social.facebook;

import java.io.File;

/**
 * @author torito
 *
 */
public interface FacebookMedia {
	/**
	 * Creates an album.
	 * 
	 * @param name
	 *            The album name.
	 * @param location
	 *            The album location (optional).
	 * @param description
	 *            The album description (optional).
	 * @return id
	 * 			  The new album Id
	 */
	public long createAlbum( String name, String description, String location ) throws FacebookException;
	/**
	 * Uploads a photo to Facebook.
	 * 
	 * @param photo
	 *            an image file
	 * @param caption
	 *            a description of the image contents
	 * @param albumId
	 *            the album into which the photo should be uploaded
	 */
	public void upload( File photo, String caption, String albumId ) throws FacebookException;
}
