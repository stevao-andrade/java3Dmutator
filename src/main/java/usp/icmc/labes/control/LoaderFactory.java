//
//    This file is part of the Java 3D tutorial from daltonfilho.com
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
//


package usp.icmc.labes.control;


import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;
import com.sun.j3d.loaders.objectfile.ObjectFile; 


/**
 * Creates loaders for different model types.
 *
 * @author Dalton Filho
 */
public class LoaderFactory {

    
    
    public static final Loader getLightwaveLoader() {
        return new Lw3dLoader();
    }
    

    public static final Loader getWavefrontLoader() {
        return new ObjectFile(ObjectFile.RESIZE); 
    }
    
    /**
     * Returns a loader for the model on the given <code>path</code>.
     *
     * @param path the path of the model
     * @throws UnsupportedOperationException if the model type is not supported
     * @return a loader for the model on the given <code>path</code>
     */
    public static final Loader getLoaderForModel(String path) 
        throws UnsupportedOperationException {
        
        if (path.endsWith("obj")) {
            return new ObjectFile(ObjectFile.RESIZE); 
        } else 
        if (path.endsWith("lwo")) {
            return new Lw3dLoader(); 
        }
        
        throw new UnsupportedOperationException("Unknown model type");
    }
    
    
}
